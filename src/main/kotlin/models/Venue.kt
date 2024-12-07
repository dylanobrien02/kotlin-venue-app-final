package ie.setu.models

data class Venue(var venueId: Int = 0,
                 var venueTitle: String,
                 var venueCapacity: Int,
                 var venueRating : Int,
                 var venueAddress: String,
                 var isIndoor: Boolean, //true for indoor and false for outdoor
                 val artists: MutableList<Artist> = mutableListOf()
    )