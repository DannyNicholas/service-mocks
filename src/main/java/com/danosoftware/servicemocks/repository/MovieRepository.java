package com.danosoftware.servicemocks.repository;

import com.danosoftware.servicemocks.dto.Movie;

import java.util.List;

public interface MovieRepository {

    /**
     * Return a list of recommended movies.
     */
    List<Movie> recommend();

    /**
     * Add a new movie.
     * Return the assigned id.
     */
    Long addMovie(Movie movie);

    /**
     * Get the wanted movie using supplied id.
     */
    Movie getMovie(Long id);
}
