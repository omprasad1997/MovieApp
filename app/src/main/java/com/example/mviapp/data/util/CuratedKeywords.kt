package com.example.mviapp.data.util

object CuratedKeywords {

    val trending = listOf("Batman", "Avengers", "Mission Impossible")
    val popular = listOf("Marvel", "DC", "Star Wars")
    val recommended = listOf("Harry Potter", "Lord of the Rings")

    fun all() = listOf(
        "Trending Now" to trending.random(),
        "Popular Movies" to popular.random(),
        "Recommended For You" to recommended.random()
    )
}
