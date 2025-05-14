import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.withViewModel
import dev.learn.kmp.Movie
import dev.learn.kmp.MovieEventHandler
import dev.learn.kmp.MovieRepository
import kotlinx.coroutines.CoroutineScope

class MovieViewModel(
    coroutineScope: CoroutineScope,
    private val repository: MovieRepository
) : BasicViewModel<
        MovieContract.Inputs,
        MovieContract.Events,
        MovieContract.State>(
    config = BallastViewModelConfiguration.Builder()
        .withViewModel(
            initialState = MovieContract.State(),
            inputHandler = MovieInputHandler(repository),
        )
        .build(),
    eventHandler = MovieEventHandler(),
    coroutineScope = coroutineScope,
) {

    init {
        trySend(MovieContract.Inputs.FetchMovies)
    }

    fun getMovie(movieId: Int) {
        trySend(MovieContract.Inputs.FetchMovieDetail(movieId))
    }

}