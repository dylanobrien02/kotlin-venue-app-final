package utils

import ie.setu.models.Artist
import ie.setu.models.Venue

/**
 * Formats a list of venue objects into a string
 * @param venuesToFormat The list of venue objects to format
 * @return A formatted string where each venue is on a new line
 */
fun formatListString(venuesToFormat: List<Venue>): String =
    venuesToFormat
        .joinToString(separator = "\n") { venue -> "$venue" }

/**
 * Formats a set of artist objects into a string representation
 * @param artistsToFormat The set of Artist objects to format
 * @return A formatted string where each artist is on a new line, prefixed by a tab character
 */
fun formatSetString(artistsToFormat: Set<Artist>): String =
    artistsToFormat
        .joinToString(separator = "\n") { artist -> "\t$artist" }


