package ie.setu.models

data class Venue(var venueId: Int = 0,
                 var venueTitle: String,
                 var venueCapacity: Int,
                 var venueRating : Int,
                 var venueAddress: String,
                 var isVenueArchived: Boolean = false,
                 val artists: MutableList<Artist> = mutableListOf()
    )