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

    fun toggleVenueType(id: Int): Boolean {
        val foundVenue = findVenue(id)
        if (foundVenue != null) {
            foundVenue.isIndoor = !foundVenue.isIndoor
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

    fun listIndoorVenues() =
        if (venues.none { it.isIndoor}) "No Indoor venues stored"
        else formatListString(venues)

    fun listOutdoorVenues() =
        if (venues.none { !it.isIndoor }) "No Outdoor venues stored"
        else formatListString(venues.filter {!it.isIndoor })
    // ----------------------------------------------
    //  COUNTING METHODS FOR VENUE ArrayList
    // ----------------------------------------------
    fun numberOfVenues() = venues.size
    fun numberOfOutdoorVenues(): Int = venues.count { venue: Venue -> !venue.isIndoor}
    fun numberOfIndoorVenues(): Int = venues.count { venue: Venue -> venue.isIndoor}

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