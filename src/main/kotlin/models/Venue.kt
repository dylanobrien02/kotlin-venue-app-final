package ie.setu.models

data class Venue(var venueId: Int = 0,
                 var venueTitle: String,
                 var venuePriority: Int,
                 var venueCategory: String,
                 var isVenueArchived: Boolean = false)