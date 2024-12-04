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
    //  CRUD METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun add(venue: Venue): Boolean {
        venue.noteId = getId()
        return venues.add(venue)
    }

    fun delete(id: Int) = venues.removeIf { note -> note.noteId == id }

    fun update(id: Int, venue: Venue?): Boolean {
        // find the note object by the index number
        val foundNote = findNote(id)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (venue != null)) {
            foundNote.noteTitle = venue.noteTitle
            foundNote.notePriority = venue.notePriority
            foundNote.noteCategory = venue.noteCategory
            return true
        }

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveNote(id: Int): Boolean {
        val foundNote = findNote(id)
        if (( foundNote != null) && (!foundNote.isNoteArchived)
          //  && ( foundNote.checkNoteCompletionStatus())
        ){
            foundNote.isNoteArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun listAllNotes() =
        if (venues.isEmpty()) "No notes stored"
        else formatListString(venues)

    fun listActiveNotes() =
        if (numberOfActiveNotes() == 0) "No active notes stored"
        else formatListString(venues.filter { note -> !note.isNoteArchived })

    fun listArchivedNotes() =
        if (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString(venues.filter { note -> note.isNoteArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun numberOfNotes() = venues.size
    fun numberOfArchivedNotes(): Int = venues.count { venue: Venue -> venue.isNoteArchived }
    fun numberOfActiveNotes(): Int = venues.count { venue: Venue -> !venue.isNoteArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findNote(noteId : Int) =  venues.find{ note -> note.noteId == noteId }

    fun searchNotesByTitle(searchString: String) =
        formatListString(venues.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true) })

}