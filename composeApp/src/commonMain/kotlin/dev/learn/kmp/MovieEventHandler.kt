package dev.learn.kmp

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope

class MovieEventHandler : EventHandler<
        MovieContract.Inputs,
        MovieContract.Events,
        MovieContract.State> {
    override suspend fun EventHandlerScope<
            MovieContract.Inputs,
            MovieContract.Events,
            MovieContract.State>.handleEvent(
        event: MovieContract.Events
    ) = when (event) {
        is MovieContract.Events.MovieCreated -> {
            TODO("Show snackbar with created movie")
        }
        is MovieContract.Events.MovieUpdated -> {
            TODO("Show snackbar with updated movie")
        }
        else -> {
            TODO("Not yet implemented")
        }
    }
}