import persistence.JSONSerializer
import java.io.File
import controllers.VenueAPI
import ie.setu.models.Venue
import ie.setu.models.Artist
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

/**
* The main instance of VenueAPI for managing venue data.
 * @constructor Creates and initializes the venue app with a JSON serializer for venue persistence
 */
private val venueAPI = VenueAPI(JSONSerializer(File("venue.json")))

/**
 * Function to launch app and display menu
 */
fun main() = runMenu()

/**
 * Displays the main menu and handles inputs to perform tasks
 */
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
            13 -> println("Number of Indoor Venues: ${venueAPI.numberOfIndoorVenues()}")
            14 -> println("Number of Outdoor Venues: ${venueAPI.numberOfOutdoorVenues()}")
            15 -> toggleVenueType()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

/**
 * Saves venue data to venue.json
 */
fun save() {
    try {
        venueAPI.store()
        println("Venues successfully saved to file")
    } catch (e: Exception) {
        System.err.println("Error Writing to file: $e")
    }
}

/**
 * Loads venue data from venue.json
 */
fun load() {
    try {
        venueAPI.load()
        println("Venues successfully loaded from file")
    } catch (e: Exception) {
        System.err.println("Error reading venues from file: $e")
    }
}

/**
 * Displays the main menu option and reads user inputs.
 * @return the user's selected menu option as an Int
 */
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
         > -----------------------------------------------------
         > | SAVE/LOAD MENU                                    |
         > |   11) Save Venues to File                         |
         > |   12) Load Venues from File                       |
         > -----------------------------------------------------
         > |  SHOW NUMBER OF VENUES                            |
         > |   13) Show Number of Indoor Venues                |
         > |   14) Show Number of Outdoor Venues               |
         > -----------------------------------------------------
         > |  OTHER                                            |
         > |   15) Toggle Venue Type                           |
         > -----------------------------------------------------
         > | EXIT VENUE APP                                    |
         > |   0) Exit the application                         |
         > -----------------------------------------------------
         > ==>> """.trimMargin(">")
)

/**
 * Adds a new venue
 * Asks the user for details about the venue (Title, Capacity, Rating, Address and whether the venue is indoor or outdoor).
 */
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

/**
 * Lists all venues based on users choice (All, Indoor or Outdoor)
 */
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

/**
 * Lists all venues
 */
fun listAllVenues() = println(venueAPI.listAllVenues())

/**
 * Lists all indoor venues
 */
fun listIndoorVenues() = println(venueAPI.listIndoorVenues())

/**
 * lists all outdoor venues
 */
fun listOutdoorVenues() = println(venueAPI.listOutdoorVenues())

/**
 * Updates the details of an existing venue
 * Shows a list of venues and asks user to choose a specific venue to update
 * Validates users choice and updates the venue details if the ID exists
 */
fun updateVenue() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
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

/**
 * Deletes a venue
 * Shows a list of venues and asks user to select a venue to be deleted
 */
fun deleteVenue() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
        val id = readNextInt("Enter the id of the venue to delete: ")
        val venueToDelete = venueAPI.delete(id)
        if (venueToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

/**
 * Toggles the venue type for a specific venue ID
 */
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

/**
 * Adds an artist to a specific venue
 * Asks user for artist details and associates the artist with the venue picked
 */
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

/**
 * lists all artists associated with a specific venue ID
 * Asks the user for a venue ID and displays the artists associated with that venue
 */
fun listArtistsForVenue(){
    val venueId = readNextInt("Enter the ID of the venue: ")
    println(venueAPI.listArtistsForVenue(venueId))
}

/**
 * Updates the details of an artist within a specific venue
 * Asks user for the venue and artist IDs and the updated artist details
 */
fun updateArtistInVenue() {
    val venueId = readNextInt("Enter the ID of the venue: ")
    val artistId = readNextInt("Enter the ID of the artist to update: ")

    val updatedName = readNextLine("Enter the updated name of the artist: ")
    val updatedGenre = readNextLine("Enter the updated genre of the artist: ")
    val updatedPerformanceDate = readNextLine("Enter the updated performance date of the artist: ")

    val isUpdated = venueAPI.updateArtistInVenue(venueId, artistId, Artist(name = updatedName, genre = updatedGenre, performanceDate = updatedPerformanceDate))
    if (isUpdated) {
        println("Artist updated successfully!")
    } else {
        println("failed to update artist. Venue ID not found.")
    }
}

/**
 * Deletes an artist from a specific venue
 * Asks user for the venue and artist IDs and removes the artist from the venue
 */
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

/**
 * Toggles the type of venue (Indoor or Outdoor) by its ID
 * Asks the user for a venue ID and switches its type
 */
fun toggleVenueTypeFromMenu(){
    val id = readNextInt("Enter the ID of the venue to toggle its type (Indoor or Outdoor): ")
    val isToggled = venueAPI.toggleVenueType(id)
    if (isToggled) {
        println("Venue type toggled successfully!")
    } else {
        println("Venue was not found.")
    }
}

/**
 * Search for venues by their titles
 * Asks user for a search and displays matching venues
 */
fun searchVenues() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = venueAPI.searchVenuesByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No venues found")
    } else {
        println(searchResults)
    }
}

/**
 * Exits the Venue App
 */
fun exitApp() {
    println("Thank You For Visiting the Venue App")
    exitProcess(0)
}