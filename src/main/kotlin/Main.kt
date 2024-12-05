import ie.setu.models.Venue
import controllers.VenueAPI
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val venueAPI = VenueAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addVenue()
            2 -> listVenues()
            3 -> updateVenue()
            4 -> deleteVenue()
            5 -> archiveVenue()
            //6 -> addArtistToVenue()
            //7 -> updateArtistContentsInVenue()
            //8 -> deleteAnArtist()
            //9 -> markArtistStatus()
            10 -> searchVenues()
            //15 -> searchArtists()
            //16 -> listToDoArtists()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  VENUE APP                        |
         > -----------------------------------------------------  
         > | VENUE MENU                                        |
         > |   1) Add a venue                                  |
         > |   2) List venues                                  |
         > |   3) Update a venue                               |
         > |   4) Delete a venue                               |
         > |   5) Archive a venue                              |
         > -----------------------------------------------------  
         > | ARTIST MENU                                       | 
         > |   6) Add artist to a venue                        |
         > |   7) Update artist contents on a venue            |
         > |   8) Delete artist from a venue                   |
         > -----------------------------------------------------  
         > | REPORT MENU FOR VENUES                            | 
         > |   10) Search for all venues                       |
         > |   11) .....                                       |
         > |   12) .....                                       |
         > |   13) .....                                       |
         > |   14) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ARTISTS                           |                                
         > |   15) Search for all artists                      |
         > |   16) .....                                       |
         > |   17) .....                                       |
         > |   18) .....                                       |
         > |   19) .....                                       |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//VENUE MENU
//------------------------------------
fun addVenue() {
    val venueTitle = readNextLine("Enter a title for the venue: ")
    val venuePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val venueCategory = readNextLine("Enter a category for the venue: ")
    val isAdded = venueAPI.add(Venue(venueTitle = venueTitle, venuePriority = venuePriority, venueCategory = venueCategory))

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
                  > |   2) View ACTIVE venues      |
                  > |   3) View ARCHIVED venues    |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllVenues()
            2 -> listActiveVenues()
            3 -> listArchivedVenues()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No venues stored")
    }
}

fun listAllVenues() = println(venueAPI.listAllVenues())
fun listActiveVenues() = println(venueAPI.listActiveVenues())
fun listArchivedVenues() = println(venueAPI.listArchivedVenues())

fun updateVenue() {
    listVenues()
    if (venueAPI.numberOfVenues() > 0) {
        // only ask the user to choose the venue if venues exist
        val id = readNextInt("Enter the id of the venue to update: ")
        if (venueAPI.findVenue(id) != null) {
            val venueTitle = readNextLine("Enter a title for the venue: ")
            val venuePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val venueCategory = readNextLine("Enter a category for the venue: ")

            // pass the index of the venue and the new venue details to VenueAPI for updating and check for success.
            if (venueAPI.update(id, Venue(0, venueTitle, venuePriority, venueCategory, false))){
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

fun archiveVenue() {
    listActiveVenues()
    if (venueAPI.numberOfActiveVenues() > 0) {
        // only ask the user to choose the venue to archive if active venues exist
        val id = readNextInt("Enter the id of the venue to archive: ")
        // pass the index of the venue to VenueAPI for archiving and check for success.
        if (venueAPI.archiveVenue(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

//-------------------------------------------
//ARTIST MENU (only available for active venues)
//-------------------------------------------

//TODO

//------------------------------------
//VENUE REPORTS MENU
//------------------------------------
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

//TODO

//------------------------------------
// Exit App
//------------------------------------
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}

//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseActiveVenue(): Venue? {
    listActiveVenues()
    if (venueAPI.numberOfActiveVenues() > 0) {
        val venue = venueAPI.findVenue(readNextInt("\nEnter the id of the venue: "))
        if (venue != venue) {
            if (venue!!.isVenueArchived) {
                println("Venue is NOT Active, it is Archived")
            } else {
                return venue //chosen venue is active
            }
        } else {
            println("Venue id is not valid")
        }
    }
    return null //selected venue is not active
}