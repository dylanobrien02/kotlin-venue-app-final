VENUE MANAGEMENT APP 


OVERVIEW 

The Venue Management App is a console based app built in Kotlin. It allows users to manage venues
and their associated artists, supporting opperations like adding, updating, deleting and searching 
for venues and artists. The app supports saving and laoding data using JSON peristance 

FEATURES 

VENUE MANAGEMENT 
1. Add Venue: Create a new venue with attributes like capacity, rating, and address.
2. Update Venue: Modify details of existing venues.
3. Delete Venue: Remove a venue from the app.
4. List Venues: Lists Venues from All Venues, Indoor Venues and Outdoor Venues.
5. Toggle Venue Type: Switch a venue between Indoor and Outdoor.
6. Search Venue: Find Venues by Title.

ARTIST MANAGEMENT 
1. Add Artist to Venue: Assign an artist to a specific venue.
2. Update Artist: Modify details of an already existing artist
3. Delete Artist: Remove an artist from a venue.
4. List Artists: Display all artists associated with a venue.

FILE PERSISTANCE
1. Save venues and artists to a JSON File.
2. Load venues and artists from a JSON File.

REPORTS
1. Display the number of indoor and outdoor venues.


TECHNOLIGIES USED 
1. Language: Kotlin
2. Persistance: JSONserializer
3. Testing: JUnit 5
4. Build Tool: Gradle with Kotlin
5. Documentation: KDoc


USAGE 

When you run the app, you'll be presented with a menu where youy can choose options to
manage venues and artists. 

MENU
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


Kotin Venue App
Assignment 2 
By Dylan O'Brien (20094139)
BSc (Hons) Creative Computing 
