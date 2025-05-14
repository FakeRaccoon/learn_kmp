package dev.learn.kmp

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    val overview: String? = null,
    @SerialName("poster_path") val posterPath: String? = null
)

@Serializable
data class MovieResponse(
    val page: Int,
    @SerialName("results") val results: List<Movie>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)

@Serializable
data class MovieDetail(
    val title: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("vote_average") val voteAverage: Double,
    val overview: String? = null,
    val runtime: Int? = null,
    @SerialName("poster_path") val posterPath: String? = null,
    @SerialName("backdrop_path") val backdropPath: String? = null,

)