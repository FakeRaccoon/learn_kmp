import dev.learn.kmp.Movie
import dev.learn.kmp.MovieDetail

object MovieContract {
    sealed interface Inputs {
        data object FetchMovies : Inputs
        data class FetchMovieDetail(val movieId: Int) : Inputs
        data class CreateMovie(val movie: Movie) : Inputs
        data class UpdateMovie(val movie: Movie) : Inputs
        data class DeleteMovie(val movieId: Int) : Inputs
    }

    sealed interface Events {
        data class MoviesLoaded(val movies: List<Movie>) : Events
        data class MovieCreated(val movie: Movie) : Events
        data class MovieUpdated(val movie: Movie) : Events
        data class MovieDeleted(val movieId: Int) : Events
        data class ErrorOccurred(val message: String) : Events
    }

    data class State(
        val movies: List<Movie> = emptyList(),
        val movie: MovieDetail? = null,
        val isLoading: Boolean = false,
        val error: String? = null
    )
}