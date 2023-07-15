package com.example.pruebaxd.data.jikan

import com.example.pruebaxd.data.jikan.Items

data class Pagination(
    val current_page: Int,
    val has_next_page: Boolean,
    val items: Items,
    val last_visible_page: Int
)