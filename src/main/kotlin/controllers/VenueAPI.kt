package controllers
import ie.setu.models.Artist
import ie.setu.models.Venue
import utils.formatListString
import persistence.Serializer
import java.util.ArrayList

/**
 * This class provides an API to manage a collection of venues and their associated artists
 * @property serializer The serializer used for saving and loading venues to and from venue.json
 * @constructor Creates a VenueAPI object with the specified serializer
 * @params serializer instance to handle saving and loading venues to/from venue.json
 */
class VenueAPI(private val serializer: Serializer) {

    private var venues = ArrayList<Venue>()

    /**
     * The last assigned ID for venues. This makes sure IDS are generated
     */
    private var lastId = 0

    /**
     * Generates a unique ID for venue
     */
    private fun getId() = lastId++

    /**
     * Loads venues from venue.json using the serializer
     * @throws Exception there is an error reading this file
     */
    @Throws(Exception::class)
    fun load() {
        venues = serializer.read() as ArrayList<Venue>
    }

    /**
     * Saves venues to venue.json using the serializer
     * @throws Exception If there is an error writing this file
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(venues)
    }

    /**
     * Adds a venue to the collection
     * @param venue venue to add.
     * @return True if the venue was added successfully, false if it was not
     */
    fun add(venue: Venue): Boolean {
        venue.venueId = getId()
        return venues.add(venue)
    }

    /**
     * Deletes a venue by its ID
     * @param id ID of the venue to delete
     * @return True if the venue is deleted successfully, false if it was not
     */
    fun delete(id: Int) = venues.removeIf { venue -> venue.venueId == id }

    /**
     * Updates a venues details
     * @param id The ID of the venue to update
     * @param venue The updated venue details
     * @return True if the update was successful, false if it was not
     */
    fun update(id: Int, venue: Venue?): Boolean {

        val foundVenue = findVenue(id)

        if ((foundVenue != null) && (venue != null)) {
            foundVenue.venueTitle = venue.venueTitle
            foundVenue.venueCapacity = venue.venueCapacity
            foundVenue.venueRating = venue.venueRating
            foundVenue.venueAddress = venue.venueAddress
            return true
        }

        return false
    }

    /**
     * Toggles a venues type between indoor and outdoor
     * @param id The ID of the venue to toggle
     * @return True if the toggle was successful, false if it was not
     */
    fun toggleVenueType(id: Int): Boolean {
        val foundVenue = findVenue(id)
        if (foundVenue != null) {
            foundVenue.isIndoor = !foundVenue.isIndoor
            return true
        }
        return false
    }

    /**
     * Lists all stored venues
     * @return A formatted string of all venues, or a message if no venues and stored
     */
    fun listAllVenues() =
        if (venues.isEmpty()) "No venues stored"
        else formatListString(venues)

    /**
     * Lists all indoor venues
     * @return A formatted string of indoor venues, or a message if no indoor venues are stored
     */
    fun listIndoorVenues() =
        if (venues.none { it.isIndoor}) "No Indoor venues stored"
        else formatListString(venues)

    /**
     * Lists all outdoor venues
     * @return A formatted string of outdoor venues, or a message if no outdoor venues are stored
     */
    fun listOutdoorVenues() =
        if (venues.none { !it.isIndoor }) "No Outdoor venues stored"
        else formatListString(venues.filter {!it.isIndoor })

    /**
     * Counts all venues whether they are indoor or outdoor
     */
    fun numberOfVenues() = venues.size

    /**
     * Counts all outdoor venues
     */
    fun numberOfOutdoorVenues(): Int = venues.count { venue: Venue -> !venue.isIndoor}

    /**
     * Counts all Indoor venues
     */
    fun numberOfIndoorVenues(): Int = venues.count { venue: Venue -> venue.isIndoor}

    /**
     * Finds a venue by its ID
     * @param venueId The ID of the venue to find
     * @return The venue if found, null if not found
     */
    fun findVenue(venueId : Int) =  venues.find{ venue -> venue.venueId == venueId }

    /**
     * Searches for venues by title
     * @param searchString The title to search for
     * @return A formatted string of matching venues
     */
    fun searchVenuesByTitle(searchString: String) =
        formatListString(venues.filter { venue -> venue.venueTitle.contains(searchString, ignoreCase = true) })

    /**
     * Adds an artist to a specific venue
     * @param venueId The ID of the venue to add the artist to
     * @param artist The Artist to add
     * @return True if the artist was added, false if not
     */
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

    /**
     * Lists all artists for a specific venue
     * @param venueId The ID of the venue
     * @return A formatted string of artists, or a message if no artists are found
     */
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

    /**
     * Updates artists details to a specific venue
     * @param venueId The ID of the venue containing the artist
     * @param artistId The ID of the artist to update
     * @param updatedArtist The Updated artist details
     * @return True if the update was successful, false if the update was not successful
     */
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

    /**
     * Deletes an artist from a specific venue
     * @param venueId The ID of the venue
     * @param artistId The ID of the artist to delete
     * @return True if the artist was deleted successfully, false if the artist was not deleted successfully
     */
    fun deleteArtistFromVenue(venueId: Int, artistId: Int): Boolean {
        val venue = findVenue(venueId)
        return venue?.artists?.removeIf { it.artistId == artistId } ?: false
    }
}