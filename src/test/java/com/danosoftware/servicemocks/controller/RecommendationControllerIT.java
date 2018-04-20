package com.danosoftware.servicemocks.controller;

import com.danosoftware.servicemocks.dto.Movie;
import com.danosoftware.servicemocks.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.danosoftware.servicemocks.helpers.MovieHelper.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Tests to confirm responses from the @Autowired recommendation controller.
 *
 * Since we are only testing the controller, we want to mock the service layer.
 *
 * @MockBean replaces the normal MovieService instance created by Spring with a mock.
 * This gets injected into the controller instead.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecommendationControllerIT {

    @MockBean
    private MovieService movieService;

    @Autowired
    private RecommendationController controller;

    @Test
    public void shouldReturnRecommendedMoviesFromController() {

        given(movieService.recommend(any(Optional.class), any(Optional.class))).willReturn(allMovies());

        List<Movie> myMovies = controller.recommend(null, 0);

        assertThat(myMovies.size(), equalTo(3));
        assertThat(myMovies.get(0), equalTo(movieStarWars()));
        assertThat(myMovies.get(1), equalTo(movieGodfather()));
        assertThat(myMovies.get(2), equalTo(movieSolaris()));
    }
}
