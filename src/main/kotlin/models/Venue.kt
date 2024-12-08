package ie.setu.models

/**
 * Data class that represents a venue
 * @property venueId The unique identifier for the venue
 * @property venueTitle The title of the venue
 * @property venueCapacity The capacity of the venue
 * @property venueRating The rating of the venue
 * @property isIndoor Whether the venue is indoors or outdoors
 * @property artists A list of artists associated with the venue
 */
data class Venue(var venueId: Int = 0,
                 var venueTitle: String,
                 var venueCapacity: Int,
                 var venueRating : Int,
                 var venueAddress: String,
                 var isIndoor: Boolean,
                 val artists: MutableList<Artist> = mutableListOf()
    )

