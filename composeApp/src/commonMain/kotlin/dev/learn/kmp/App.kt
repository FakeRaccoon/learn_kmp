package dev.learn.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage

@Composable
fun App() {
    Navigator(screen = NavigatorPage())
}

class NavigatorPage : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val vm: MovieViewModel = viewModel()

        MaterialTheme {
            Scaffold(
                topBar = { TopAppBar(
                    title = {
                        Text("Movie Catalog")
                    }
                ) },
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        navigator.push(AddEditMovieScreen(vm))
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Movie")
                    }
                }
            ) { padding ->
                Surface(
                    modifier = Modifier.fillMaxSize().padding(padding)
                ) {
                    when {
                        vm.isLoading -> LoadingScreen()
                        vm.hasError -> ErrorScreen()
                        else -> MovieGridScreen(
                            movies = vm.movies,
                            onNavigate = { navigator.push(MovieDetailView(it, vm)) }
                        )
                    }
                }
            }
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
fun ErrorScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Failed to load movies.")
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
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.count()) { index ->
            val movie = movies[index]
            Card(onClick = { onNavigate(movie.id) }) {
                Column {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = null,
                        modifier = Modifier.aspectRatio(0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(movie.title, style = MaterialTheme.typography.body1)
                        Text("⭐ ${movie.rating}", style = MaterialTheme.typography.body1)
                    }
                }
            }
        }
    }
}