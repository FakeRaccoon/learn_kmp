package dev.learn.kmp

import MovieContract
import MovieViewModel
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage

val LocalMovieViewModel = staticCompositionLocalOf<MovieViewModel> {
    error("No MovieViewModel provided")
}

@Composable
fun App() {

    val coroutineScope = rememberCoroutineScope()
    // vm will only ever be created once
    val vm = remember { MovieViewModel(coroutineScope, repository = MovieRepository()) }

    CompositionLocalProvider(
        LocalMovieViewModel provides vm,
    ) {
        MaterialTheme(
            colors = darkColors(
                primary = Color(0xFFBB86FC),
                primaryVariant = Color(0xFF3700B3),
                secondary = Color(0xFF03DAC6)
            )
        ) {
            Navigator(screen = NavigatorPage())
        }
    }
}

class NavigatorPage : Screen {
    @Composable
    override fun Content() {

        val vm = LocalMovieViewModel.current
        val vmState by vm.observeStates().collectAsState()

        HomeScreen(vmState) { vm.trySend(it) }

    }
}

@Composable
fun HomeScreen(
    vmState: MovieContract.State,
    postInput: (MovieContract.Inputs) -> Unit = {},
) {

    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movies Catalog") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigator.push(MovieCreateEditView())
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        when {
            vmState.isLoading -> LoadingScreen()
            vmState.error != null -> ErrorScreen(error = vmState.error)
            else -> MovieGridScreen(movies = vmState.movies, onNavigate = { movieId ->
                run {
                    navigator.push(MovieDetailView(movieId))
                }
            })
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Failed to load movies $error.")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieGridScreen(
    movies: List<Movie>,
    onNavigate: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.count()) { index ->
            val movie = movies[index]
            Card(
                onClick = { onNavigate(movie.id) },
                shape = RoundedCornerShape(16),
                elevation = 0.dp,
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/original${movie.posterPath}",
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}