package utils

import ie.setu.models.Artist
import ie.setu.models.Venue


fun formatListString(notesToFormat: List<Venue>): String =
    notesToFormat
        .joinToString(separator = "\n") { note -> "$note" }

fun formatSetString(itemsToFormat: Set<Artist>): String =
    itemsToFormat
        .joinToString(separator = "\n") { item -> "\t$item" }


