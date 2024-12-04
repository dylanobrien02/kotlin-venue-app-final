package ie.setu.models

data class Venue(var noteId: Int = 0,
                 var noteTitle: String,
                 var notePriority: Int,
                 var noteCategory: String,
                 var isNoteArchived: Boolean = false)