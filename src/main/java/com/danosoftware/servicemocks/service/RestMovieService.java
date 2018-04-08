package com.danosoftware.servicemocks.service;

import com.danosoftware.servicemocks.dto.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * Movie Service implementation that retrieves movie
 * information from an external REST service.
 */
@Service
public class RestMovieService implements MovieService {

    private final RestTemplate restTemplate;
    private final String host;

    public RestMovieService(
            @Autowired RestTemplate restTemplate,
            @Autowired @Qualifier("movieServiceHost") String host) {
        this.restTemplate = restTemplate;
        this.host = host;
    }

    @Override
    public List<Movie> recommend(Optional<String> genre, Optional<Integer> year) {

        ParameterizedTypeReference<List<Movie>> responseType = new ParameterizedTypeReference<List<Movie>>() {};

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/recommend");
        genre.ifPresent(g -> uriBuilder.queryParam("genre", g));
        year.ifPresent(y -> uriBuilder.queryParam("year", y));
        URI uri = uriBuilder.build().toUri();

        ResponseEntity<List<Movie>> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, responseType);

        return response.getBody();
    }

    @Override
    public Long addMovie(Movie movie) {

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/add")
                .build()
                .toUri();

        HttpEntity<Movie> entity = new HttpEntity<>(movie);

        ResponseEntity<Long> response = restTemplate
                .exchange(uri, HttpMethod.POST, entity, Long.class);

        return response.getBody();
    }

    @Override
    public Movie getMovie(Long id) {

        URI uri = UriComponentsBuilder
                .fromUriString(host)
                .path("/api/movies-service/search/{id}")
                .buildAndExpand(id)
                .toUri();

        ResponseEntity<Movie> response = restTemplate
                .exchange(uri, HttpMethod.GET, null, Movie.class);

        return response.getBody();
    }
}
