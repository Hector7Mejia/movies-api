package main;

import com.google.gson.Gson;
import data.movies.InMemoryMoviesDao;
import data.movies.Movie;
import data.movies.MySqlMoviesDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name="MovieServlet", urlPatterns="/movies/*")
public class MovieServlet extends HttpServlet {
//    private InMemoryMoviesDao dao = new InMemoryMoviesDao();
    private MySqlMoviesDao dao = new MySqlMoviesDao();
//fetches data
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            // something that
            // might go wrong
            PrintWriter out = response.getWriter();
            // changes movies Arraylist to movies array
            //Gson converts movies array to movieString String
            String movieString = new Gson().toJson(dao.all().toArray());
            out.println(movieString);

            Movie movie1 = new Movie("Gary", 10, "Gary invents air", 2023, "Horror", "David Attenborough", "Gary is out to create air","SpongeBob, Gary", 1);
        } catch (Exception e) {
            // handle the error
            e.printStackTrace();
        }
//        out.println("<h1>Hello, world!</h1>");


    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        BufferedReader br = request.getReader();

        Movie[] newMovies = new Gson().fromJson(br, Movie[].class);


        try {
            dao.insertAll(newMovies);
            PrintWriter out = response.getWriter();
            out.println("Movie added");
        } catch(IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String [] uriParts = request.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);


        Movie newMovie = new Gson().fromJson(request.getReader(),Movie.class);
        newMovie.setId(targetId);
        PrintWriter out = response.getWriter();

        try {
            dao.update(newMovie);
            out.println("Movie updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("application/json");

        String [] uriParts = request.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);

        try {
            dao.delete(targetId);
            PrintWriter out = response.getWriter();
            out.println("Movie deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

//Movie foundMovie = null;
//        for (Movie movie: movies) {
//            int grabbedId = movie.getId();
//            if (grabbedId == targetId) {
//foundMovie = movie;
//            }
//        }
//if(foundMovie != null) {
//    movies.remove(foundMovie);
//}
    }

//    @Override
//    public void destroy() {
//        super.destroy();
//        dao.cleanUp();
//    }
}
