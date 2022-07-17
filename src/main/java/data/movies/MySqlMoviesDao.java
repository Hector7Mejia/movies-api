package data.movies;

import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlMoviesDao implements MoviesDao {
//"jdbc:mysql://"
    private Connection connection;
//"https://agreeable-sumptuous-lilac.glitch.me/movies"
    public MySqlMoviesDao() {
        // make the connection
        try {
            DriverManager.registerDriver(new Driver());
            connection = DriverManager.getConnection(
                    "jdbc:mysql://" + Config.DB_HOST + ":3306/hector?allowPublicKeyRetrieval=true&useSSL=false",
                    Config.DB_USER,
                    Config.DB_PW
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Movie> all() throws SQLException {
        // TODO: Get ALL the movies
        ArrayList<Movie> movies = new ArrayList<>();
        PreparedStatement st = connection.prepareStatement("select * from movies");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            //make a movie object from the table record
            Movie movie = new Movie();
            movie.setId(rs.getInt("id"));
            movie.setTitle(rs.getString("title"));
            movie.setRating(rs.getInt("rating"));
            movie.setPoster(rs.getString("poster"));
            movie.setYear(rs.getInt("year"));
            movie.setGenre(rs.getString("genre"));
            movie.setPlot(rs.getString("plot"));
            movie.setDirector(rs.getString("director"));
            movie.setActors(rs.getString("actors"));
movies.add(movie);
        }
        rs.close();
        st.close();
        return movies;
    }

    @Override
    public Movie findOne(int id) {
        Movie findMovie = null;
        try {
            Statement statement = connection.createStatement();

            ResultSet rs = null;
            rs = statement.executeQuery("SELECT * FROM movies WHERE id = " + id);
            rs.next();

            findMovie = new Movie(
                    rs.getString("title"),
                    rs.getInt("rating"),
                    rs.getString("poster"),
                    rs.getInt("year"),
                    rs.getString("genre"),
                    rs.getString("director"),
                    rs.getString("plot"),
                    rs.getString("actors"),
                    rs.getInt("id")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return findMovie;
    }

    @Override
    public void insert(Movie movie) {
        // TODO: Insert one movie
        try {
            PreparedStatement ps = connection.prepareStatement("insert into movies " +
                    " (title, rating, poster, year, genre, director, plot, actors)" +
                    " values(?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getRating());
            ps.setString(3, movie.getPoster());
            ps.setInt(4, movie.getYear());
            ps.setString(5, movie.getGenre());
            ps.setString(6, movie.getDirector());
            ps.setString(7, movie.getPlot());
            ps.setString(8, movie.getActors());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertAll(Movie[] movies) throws SQLException {
        // TODO: Insert all the movies!
        for (Movie movie : movies) {
            insert(movie);
        }
    }

    @Override
    public void update(Movie movie) throws SQLException {
        // assumption: movie only has the fields that we need to update
        String query = "update movies set";
        Movie changeMovie = findOne(movie.getId());

        if (movie.getTitle() != null) {
            changeMovie.setTitle(movie.getTitle());
        }
        if (movie.getPoster() != null) {
            changeMovie.setPoster(movie.getPoster());
        }
        if (movie.getYear() != null) {
            changeMovie.setYear(movie.getYear());
        }
        if (movie.getRating() != null) {
            changeMovie.setRating(movie.getRating());
        }
        if (movie.getGenre() != null) {
            changeMovie.setGenre(movie.getGenre());
        }
        if (movie.getDirector() != null) {
            changeMovie.setDirector(movie.getDirector());
        }
        if (movie.getActors() != null) {
            changeMovie.setActors(movie.getActors());
        }
        if (movie.getPlot() != null) {
            changeMovie.setPlot(movie.getPlot());
        }

        StringBuilder sql = new StringBuilder("UPDATE movies SET title = ?, poster = ?, year = ?, rating = ?, genre = ?, plot = ?, director = ?, actors = ? WHERE id = ? ");


        PreparedStatement statement = connection.prepareStatement((sql.toString()));
        statement.setString(1, changeMovie.getTitle());
        statement.setString(2, changeMovie.getPoster());
        statement.setInt(3, changeMovie.getYear());
        statement.setInt(4, changeMovie.getRating());
        statement.setString(5, changeMovie.getGenre());
        statement.setString(6, changeMovie.getPlot());
        statement.setString(7, changeMovie.getDirector());
        statement.setString(8, changeMovie.getActors());
        statement.setInt(9, changeMovie.getId());

        statement.executeUpdate();
    }
    @Override
    public void delete(int id) throws SQLException {
        //TODO: Annihilate a movie
            PreparedStatement st = connection.prepareStatement("delete from movies where id = ?");
            st.setInt(1, id);
st.executeUpdate();
}

    public void cleanUp() throws SQLException {
        System.out.println("Calling clean up.....");

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

