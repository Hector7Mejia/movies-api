package main;

import com.google.gson.Gson;
import data.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name="MovieServlet", urlPatterns="/movies/*")
public class MovieServlet extends HttpServlet {

    ArrayList<Movie> movies = new ArrayList<>();
    int nextId = 1;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try {
            // something that
            // might go wrong
            PrintWriter out = response.getWriter();
            String movieString = new Gson().toJson(movies.toArray());
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
        for (Movie movie : newMovies) {
            movie.setId(nextId++);
            movies.add(movie);
        }

        try {
            PrintWriter out = response.getWriter();
            out.println("Movie(s) added");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String [] uriParts = request.getRequestURI().split("/");
        int targetId = Integer.parseInt(uriParts[uriParts.length - 1]);


        String newMovie = new Gson().toJson(movies.toArray());

        for (int i = 0; i < movies.size(); i++) {
            int grabbedId = movies.get(i).getId();
            if(grabbedId == targetId) {
//                newMovie =
            }
        }
    }
}
