package utils

/**
 * Prompts the user with a message and reads a valid integer input
 * @param prompt A string message displayed to user before input
 * @return The integer value entered by the user
 */
fun readNextInt(prompt: String?): Int {
    do {
        try {
            print(prompt)
            return readln().toInt()
        } catch (e: NumberFormatException) {
            System.err.println("\tEnter a number please.")
        }
    } while (true)
}

/**
 * Prompts user with a message and reads a string input
 * @param prompt A string message displayed to the user before input
 * @return The string value entered by the user
 */
fun readNextLine(prompt: String?): String {
    print(prompt)
    return readln()
}