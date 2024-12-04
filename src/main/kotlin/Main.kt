import controllers.VenueAPI
import ie.setu.models.Venue
import utils.readNextInt
import utils.readNextLine
import kotlin.system.exitProcess

private val venueAPI = VenueAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> runVenueMenu()
            2 -> runArtistMenu()
            3 -> assignArtistToVenue()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > ----------------------------------
         > |       VENUE ARTIST APP         |
         > ----------------------------------
         > | MAIN MENU                      |
         > |   1) Manage Venues             |
         > |   2) Manage Artists            |
         > |   3) Assign Artist to Venue    |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">")
)

// Venue Menu
fun runVenueMenu() {
    do {
        when (val option = venueMenu()) {
            1 -> addVenue()
            2 -> listVenues()
            3 -> updateVenue()
            4 -> deleteVenue()
            0 -> return
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun venueMenu() = readNextInt(
    """ 
         > ----------------------------------
         > |       MANAGE VENUES            |
         > ----------------------------------
         > |   1) Add Venue                 |
         > |   2) List Venues               |
         > |   3) Update Venue              |
         > |   4) Delete Venue              |
         > ----------------------------------
         > |   0) Return to Main Menu       |
         > ----------------------------------
         > ==>> """.trimMargin(">")
)