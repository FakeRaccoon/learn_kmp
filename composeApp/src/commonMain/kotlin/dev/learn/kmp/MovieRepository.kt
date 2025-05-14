package dev.learn.kmp

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class MovieRepository {
    private val client = HttpClient() {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getMovies(): MovieResponse {

        val response = client.get("https://api.themoviedb.org/3/movie/popular", {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NzI3N2UxNjgxNzE0NWFmMGMzMmE2ZWQ2Njg0MjBkYiIsIm5iZiI6MS43NDY3NzQxMjYyMDk5OTk4ZSs5LCJzdWIiOiI2ODFkYTg2ZTllYmEyMjQ3MTI5M2IyNmUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.Rp9gNIEwEpSSGpzP7ygkwxI5QYUc_vZGF-zwXR6zvRU"
                )
            }
            url {
                parameters.append("language", "en-US")
                parameters.append("page", "1")
            }
        })

        print("REQUEST HEADER ${response.headers}")

        return response.body<MovieResponse>()
    }

    suspend fun getMovie(movieId: Int): MovieDetail {

        val response = client.get("https://api.themoviedb.org/3/movie/$movieId", {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NzI3N2UxNjgxNzE0NWFmMGMzMmE2ZWQ2Njg0MjBkYiIsIm5iZiI6MS43NDY3NzQxMjYyMDk5OTk4ZSs5LCJzdWIiOiI2ODFkYTg2ZTllYmEyMjQ3MTI5M2IyNmUiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.Rp9gNIEwEpSSGpzP7ygkwxI5QYUc_vZGF-zwXR6zvRU"
                )
            }
            url {
                parameters.append("language", "en-US")
            }
        })


        return response.body<MovieDetail>()
    }
}
