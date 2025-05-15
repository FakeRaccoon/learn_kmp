package dev.learn.kmp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.skydoves.landscapist.coil3.CoilImage

data class MovieDetailView(
    private val movieId: Int,

) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val vm = LocalMovieViewModel.current
        val vmState by vm.observeStates().collectAsState()

        vm.getMovie(movieId)

        val movie by remember {
            derivedStateOf { vmState.movie }
        }

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
                    backgroundColor = Color.Transparent,
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
                                TODO()
                            }
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Share Movie")
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CoilImage(
                    imageModel = { "https://image.tmdb.org/t/p/original${movie!!.backdropPath}" },
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("${movie!!.runtime} min", )
                        Spacer(
                            modifier = Modifier.width(20.dp)
                        )
                        Text(movie!!.releaseDate.split("-")[0])
                        Spacer(
                            modifier = Modifier.width(20.dp)
                        )
                        Text("${movie!!.voteAverage}")
                    }

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text("${movie!!.overview}")
                }
            }
        }
    }
}
