package dev.learn.kmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val rating: Double,
    val posterUrl: String
)

class MovieViewModel : ViewModel() {

    private val _movies = mutableStateListOf<Movie>()
    val movies: List<Movie> = _movies

    var isLoading by mutableStateOf(true)
        private set
    var hasError by mutableStateOf(false)
        private set

    private val seeds = listOf(
        Movie(
            id = 1,
            title = "Inception",
            description = "A thief who steals corporate secrets through dream-sharing technology.",
            releaseDate = "2010-07-16",
            rating = 8.8,
            posterUrl = "https://upload.wikimedia.org/wikipedia/id/9/91/Inception_poster.jpg"
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "Batman faces the Joker, a criminal mastermind who plunges Gotham into chaos.",
            releaseDate = "2008-07-18",
            rating = 9.0,
            posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
        ),
        Movie(
            id = 3,
            title = "Interstellar",
            description = "A team travels through a wormhole in space to ensure humanity's survival.",
            releaseDate = "2014-11-07",
            rating = 8.6,
            posterUrl = "https://m.media-amazon.com/images/M/MV5BYzdjMDAxZGItMjI2My00ODA1LTlkNzItOWFjMDU5ZDJlYWY3XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg"
        ),
        Movie(
            id = 4,
            title = "Parasite",
            description = "A poor family schemes to become employed by a wealthy household.",
            releaseDate = "2019-05-30",
            rating = 8.6,
            posterUrl = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg"
        ),
        Movie(
            id = 5,
            title = "The Shawshank Redemption",
            description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption.",
            releaseDate = "1994-09-23",
            rating = 9.3,
            posterUrl = "https://image.tmdb.org/t/p/w500/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg"
        ),
        Movie(
            id = 6,
            title = "Pulp Fiction",
            description = "The lives of two mob hitmen, a boxer, and others intertwine in a tale of violence and redemption.",
            releaseDate = "1994-10-14",
            rating = 8.9,
            posterUrl = "https://static.posters.cz/image/750/1288.jpg"
        ),
        Movie(
            id = 7,
            title = "Fight Club",
            description = "An insomniac office worker forms an underground fight club with a soap maker.",
            releaseDate = "1999-10-15",
            rating = 8.8,
            posterUrl = "https://image.tmdb.org/t/p/w500/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg"
        ),
        Movie(
            id = 8,
            title = "Forrest Gump",
            description = "The presidencies of Kennedy and Johnson, the Vietnam War, and more through the eyes of Forrest.",
            releaseDate = "1994-07-06",
            rating = 8.8,
            posterUrl = "https://image.tmdb.org/t/p/w500/saHP97rTPS5eLmrLQEcANmKrsFl.jpg"
        ),
        Movie(
            id = 9,
            title = "The Matrix",
            description = "A computer hacker learns the true nature of reality and his role in the war against its controllers.",
            releaseDate = "1999-03-31",
            rating = 8.7,
            posterUrl = "https://image.tmdb.org/t/p/w500/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg"
        ),
        Movie(
            id = 10,
            title = "Gladiator",
            description = "A former Roman General seeks revenge for the murder of his family and betrayal by a corrupt emperor.",
            releaseDate = "2000-05-05",
            rating = 8.5,
            posterUrl = "https://image.tmdb.org/t/p/w500/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg"
        )
    )

    init {
        viewModelScope.launch {
            delay(1000)
            try {
                _movies.addAll(seeds)
                isLoading = false
            } catch (e: Exception) {
                hasError = true
            }
        }
    }

    fun addMovie(title: String, description: String) {
        val newMovie = Movie(
            id = _movies.size + 1,
            title = title,
            description = description,
            releaseDate = "2024-01-01",
            rating = 7.5,
            posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg"
        )
        _movies.add(newMovie)
    }

    fun updateMovie(updated: Movie) {
        val index = _movies.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            _movies[index] = updated
        }
    }

    fun removeMovie(movie: Movie) {
        _movies.remove(movie)
    }
}