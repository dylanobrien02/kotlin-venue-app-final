package ie.setu.models

/**
 * Data class that represents an artist performing at a venue
 * @property artistId The unique ID of the artist
 * @property name The name of the artist
 * @property performanceDate The date when the artist performs
 *
 * @constructor Creates an artist object
 */
data class Artist(var artistId: Int = 0,
                   var name: String,
                   var genre: String,
                   var performanceDate: String
)
