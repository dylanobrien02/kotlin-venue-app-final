import persistence.XMLSerializer
import persistence.JSONSerializer
import java.io.File
import controllers.VenueAPI
import ie.setu.models.Venue
import ie.setu.models.Artist
import utils.readNextChar
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val venueAPI = VenueAPI(JSONSerializer(File("venue.json")))

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addVenue()
            2 -> listVenues()
            3 -> updateVenue()
            4 -> deleteVenue()
            5 -> toggleVenueTypeFromMenu()
            6 -> addArtistToVenue()
            7 -> updateArtistInVenue()
            8 -> deleteArtistFromVenue()
            9 -> listArtistsForVenue()
            10 -> searchVenues()
            11 -> save()
            12 -> load()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun save() {
    try {
        venueAPI.store()
        println("Venues successfully saved to file")
    } catch (e: Exception) {
        System.err.println("Error Writing to file: $e")
    }
}

fun load() {
    try {
        venueAPI.load()
        println("Venues successfully loaded from file")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------
         > |                  VENUE MANAGEMENT APP             |
         > -----------------------------------------------------
         > | VENUE MENU                                        |
         > |   1) Add a venue                                  |
         > |   2) List venues (All/Indoor/Outdoor)             |
         > |   3) Update venue details                         |
         > |   4) Delete a venue                               |
         > |   5) Toggle venue type (Indoor/Outdoor)           |
         > -----------------------------------------------------
         > | ARTIST MENU                                       |
         > |   6) Add artist to a venue                        |
         > |   7) Update artist details in a venue             |
         > |   8) Delete artist from a venue                   |
         > |   9) List all artists in a specific venue         |
         > -----------------------------------------------------
         > | SEARCH MENU                                       |
         > |   10) Search venues by title                      |
         > |   11) Feature Coming Soon                         |
         > -----------------------------------------------------
         > | SAVE/LOAD MENU                                    |
         > |   12) Save Venues to File                         |
         > |   13) Load Venues from File                       |
         > -----------------------------------------------------
         > | EXIT VENUE APP                                    |
         > |   0) Exit the application                         |
         > -----------------------------------------------------
         > ==>> """.trimMargin(">")
)

//------------------------------------
//VENUE MENU
//------------------------------------
fun addVenue() {
    val venueTitle = readNextLine("Enter a title for the venue: ")
    val venueCapacity = readNextInt("Enter the capacity for the venue: ")

    if (venueCapacity <= 0) {
        println("Capacity must be greater than 0!")
        return
    }
    val venueRating = readNextInt("Enter the rating for the venue: ")
    val venueAddress = readNextLine("Enter the address for the venue: ")

    val isIndoor = readNextLine("Is the venue indoor or outdoor? (Enter 'indoor or 'outdoor'): ").lowercase() == "indoor"

    val isAdded = venueAPI.add(
        Venue(
            venueTitle = venueTitle,
            venueCapacity = venueCapacity,
            venueRating = venueRating,
            venueAddress = venueAddress,
            isIndoor = isIndoor
        )
    )

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listVenues() {
    if (venueAPI.numberOfVenues() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL venues         |
                  > |   2) View INDOOR venues      |
                  > |   3) View OUTDOOR venues    |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllVenues()
            2 -> listIndoorVenues()
            3 -> listOutdoorVenues()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No venues stored")
    }
}

fun listAllVenues() = println(venueAPI.listAllVenues())
fun listIndoorVenues() = println(venueAPI.listIndoorVenues())
fun listOutdoorVenues() = println(venueAPI.listOutdoorVenues())

fun updateVenue() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
        // only ask the user to choose the venue if venues exist
        val id = readNextInt("Enter the id of the venue to update: ")
        if (venueAPI.findVenue(id) != null) {
            val venueTitle = readNextLine("Enter a title for the venue: ")
            val venueCapacity = readNextInt("Enter the new capacity for the venue: ")

            if (venueCapacity <= 0) {
                println("Capacity must be greater than 0!")
                return
            }

            val venueRating = readNextInt("Enter the new rating for the venue (1-low, 5-high): ")
            val venueAddress = readNextLine("Enter the new address for the venue: ")

            // pass the index of the venue and the new venue details to VenueAPI for updating and check for success.
            if (venueAPI.update(id, Venue(0, venueTitle, venueCapacity, venueRating, venueAddress, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no venues for this index number")
        }
    }
}

fun deleteVenue() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
        // only ask the user to choose the venue to delete if venues exist
        val id = readNextInt("Enter the id of the venue to delete: ")
        // pass the index of the venue to VenueAPI for deleting and check for success.
        val venueToDelete = venueAPI.delete(id)
        if (venueToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun toggleVenueType() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
        val id = readNextInt("Enter the id of the venue to toggle: ")

        if (venueAPI.toggleVenueType(id)) {
            println("Venue type toggled successfully!")
        } else {
            println("Toggle Not Successful. Venue ID not found.")
        }
    } else {
        println("No venues available to toggle.")
    }
}

//-------------------------------------------
//ARTIST MENU (only available for active venues)
//-------------------------------------------

//TODO

//------------------------------------
//VENUE REPORTS MENU
//------------------------------------

fun addArtistToVenue() {
    val venueId = readNextInt("Enter the Id of the venue: ")
    val artistName = readNextLine("Enter the name of the artist: ")
    val artistGenre = readNextLine("Enter the genre of the artist:")
    val performanceDate = readNextLine("Enter the performance date of the artist: ")

    val isAdded = venueAPI.addArtistToVenue(venueId, Artist(name = artistName, genre = artistGenre, performanceDate = performanceDate))
    if (isAdded) {
        println("Artist added Successfully!")
    } else {
        println("Failed to add artist. Venue ID not found.")
    }
}

fun listArtistsForVenue(){
    val venueId = readNextInt("Enter the ID of the venue: ")
    println(venueAPI.listArtistsForVenue(venueId))
}

fun updateArtistInVenue() {
    val venueId = readNextInt("Enter the ID of the venue: ")
    val artistId = readNextInt("Enter the ID of the artist to update: ")

    val updatedName = readNextLine("Enter the updated name of the artist: ")
    val updatedGenre = readNextLine("Enter the updated genre of the artist: ")
    val updatedPerformanceDate = readNextLine("Enter the updated performance date of the artist: ")

    val isUpdated = venueAPI.updateArtistInVenue(venueId, artistId, Artist(name = updatedName, genre = updatedGenre, performanceDate = updatedPerformanceDate))
}

fun deleteArtistFromVenue() {
    val venueId = readNextInt("Enter the ID of the venue: ")
    val artistId = readNextInt("Enter the ID of the artist to delete: ")

    val isDeleted = venueAPI.deleteArtistFromVenue(venueId, artistId)
    if (isDeleted) {
        println("Artist deleted successfully!")
    } else {
        println("Failed to delete artist. Check venue and artist IDs.")
    }
}

fun toggleVenueTypeFromMenu(){
    val id = readNextInt("Enter the ID of the venue to toggle its type (Indoor or Outdoor): ")
    val isToggled = venueAPI.toggleVenueType(id)
    if (isToggled) {
        println("Venue type toggled successfully!")
    } else {
        println("Venue not found.")
    }
}

fun searchVenues() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = venueAPI.searchVenuesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No venues found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ARTIST REPORTS MENU
//------------------------------------

//------------------------------------
// Exit App
//------------------------------------

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}