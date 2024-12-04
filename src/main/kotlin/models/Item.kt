package ie.setu.models

data class Item (var itemId: Int = 0,
                 var itemContents : String,
                 var isItemComplete: Boolean = false)