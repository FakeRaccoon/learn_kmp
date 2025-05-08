package dev.learn.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

data class MovieDetailView(
    private val movieId: Int,
    private val vm: MovieViewModel,
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val movie by remember { derivedStateOf { vm.movies.find { it.id == movieId } } }

        if (movie == null) {
            // Movie was removed or not found
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Movie not found.")
            }
            return
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    title = { Text(movie!!.title) },
                    actions = {
                        IconButton(
                            onClick = {
                                navigator.push(AddEditMovieScreen(vm, movie))
                            }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Movie")
                        }

                        IconButton(
                            onClick = {
                                vm.removeMovie(movie!!)
                                navigator.pop()
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Movie")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    AsyncImage(
                        model = movie!!.posterUrl,
                        contentDescription = "${movie!!.title} poster",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(text = "⭐ ${movie!!.rating}", modifier = Modifier.align(Alignment.Start))
                Text(
                    text = "Release Date: ${movie!!.releaseDate}",
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie!!.description,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
