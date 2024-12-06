package controllers

import ie.setu.models.Venue
import utils.formatListString
import java.util.ArrayList

class VenueAPI() {

    private var venues = ArrayList<Venue>()

    // ----------------------------------------------
    //  For Managing the id internally in the program
    // ----------------------------------------------
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR VENUE ArrayList
    // ----------------------------------------------
    fun add(venue: Venue): Boolean {
        venue.venueId = getId()
        return venues.add(venue)
    }

    fun delete(id: Int) = venues.removeIf { venue -> venue.venueId == id }

    fun update(id: Int, venue: Venue?): Boolean {
        // find the venue object by the index number
        val foundVenue = findVenue(id)

        // if the venue exists, use the venue details passed as parameters to update the found venue in the ArrayList.
        if ((foundVenue != null) && (venue != null)) {
            foundVenue.venueTitle = venue.venueTitle
            foundVenue.venueCapacity = venue.venueCapacity
            foundVenue.venueRating = venue.venueRating
            foundVenue.venueAddress = venue.venueAddress
            return true
        }

        // if the venue was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveVenue(id: Int): Boolean {
        val foundVenue = findVenue(id)
        if (( foundVenue != null) && (!foundVenue.isVenueArchived)
          //  && ( foundVenue.checkVenueCompletionStatus())
        ){
            foundVenue.isVenueArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR VENUE ArrayList
    // ----------------------------------------------
    fun listAllVenues() =
        if (venues.isEmpty()) "No venues stored"
        else formatListString(venues)

    fun listActiveVenues() =
        if (numberOfActiveVenues() == 0) "No active venues stored"
        else formatListString(venues.filter { venue -> !venue.isVenueArchived })

    fun listArchivedVenues() =
        if (numberOfArchivedVenues() == 0) "No archived venues stored"
        else formatListString(venues.filter { venue -> venue.isVenueArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR VENUE ArrayList
    // ----------------------------------------------
    fun numberOfVenues() = venues.size
    fun numberOfArchivedVenues(): Int = venues.count { venue: Venue -> venue.isVenueArchived }
    fun numberOfActiveVenues(): Int = venues.count { venue:Venue -> !venue.isVenueArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findVenue(venueId : Int) =  venues.find{ venue -> venue.venueId == venueId }

    fun searchVenuesByTitle(searchString: String) =
        formatListString(venues.filter { venue -> venue.venueTitle.contains(searchString, ignoreCase = true) })

}