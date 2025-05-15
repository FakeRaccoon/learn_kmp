import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import dev.learn.kmp.MovieRepository

class MovieInputHandler(
    private val repository: MovieRepository,
) :
    InputHandler<
            MovieContract.Inputs,
            MovieContract.Events,
            MovieContract.State> {
    override suspend fun InputHandlerScope<
            MovieContract.Inputs,
            MovieContract.Events,
            MovieContract.State>.handleInput(
        input: MovieContract.Inputs
    ) = when (input) {
        is MovieContract.Inputs.CreateMovie -> {

            val movieState = updateStateAndGet {
                it.copy(
                    movies = it.movies + input.movie
                )
            }

            println("MOVIES COUNT ${movieState.movies.count()}")

            postEvent(MovieContract.Events.MovieCreated(input.movie))
        }

        is MovieContract.Inputs.FetchMovies -> {
            try {
                val response = repository.getMovies()

                println("MOVIES RESPONSE ${response.results}")

                val state = updateStateAndGet { state ->
                    state.copy(
                        movies = response.results.toList(),
                    )
                }

                print("MOVIES STATE ${state.movies.count()}")

            } catch (t: Throwable) {
                println("Error fetching movies: ${t.message}")
            }
        }

        is MovieContract.Inputs.UpdateMovie -> {
            updateState { state ->
                state.copy(
                    movies = state.movies.map { m ->
                        if (m.id == input.movie.id) input.movie else m
                    }
                )
            }
            postEvent(MovieContract.Events.MovieUpdated(input.movie))
        }

        is MovieContract.Inputs.DeleteMovie -> {
            TODO("To be implemented")
        }

        is MovieContract.Inputs.FetchMovieDetail -> {
            try {

                val response = repository.getMovie(input.movieId)

                updateState { state ->
                    state.copy(
                        movie = response,
                    )
                }

            } catch (t: Throwable) {
                println("Error fetching movies: ${t.message}")
            }
        }

        else -> {
            TODO("To be implemented")
        }
    }
}