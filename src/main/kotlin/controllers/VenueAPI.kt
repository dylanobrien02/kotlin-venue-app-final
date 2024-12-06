package controllers
import ie.setu.models.Artist
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

    fun addArtistToVenue(venueId: Int, artist: Artist): Boolean {
        val venue = findVenue(venueId)
        return if (venue != null) {
            artist.artistId = venue.artists.size + 1
            venue.artists.add(artist)
            true
        } else {
            false
        }
    }

    fun listArtistsForVenue(venueId: Int): String {
        val venue = findVenue(venueId)
        return if (venue != null && venue.artists.isNotEmpty()) {
            venue.artists.joinToString("\n") { artist ->
                "Artist ID: ${artist.artistId}, Name: ${artist.name}, Genre: ${artist.genre}, Performance Date: ${artist.performanceDate} "
            }
        } else {
        "No artist found"
        }
    }

    fun updateArtistInVenue(venueId: Int, artistId: Int, updatedArtist: Artist): Boolean {
        val venue = findVenue(venueId)
        val artist = venue?.artists?.find { it.artistId == artistId}
        return if (artist != null) {
            artist.name = updatedArtist.name
            artist.genre = updatedArtist.genre
            artist.performanceDate = updatedArtist.performanceDate
            true
        } else {
            false
        }
    }

    fun deleteArtistFromVenue(venueId: Int, artistId: Int): Boolean {
        val venue = findVenue(venueId)
        return venue?.artists?.removeIf { it.artistId == artistId } ?: false
    }
}