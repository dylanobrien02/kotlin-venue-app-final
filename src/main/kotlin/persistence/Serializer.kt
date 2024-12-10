package persistence

/**
 * An interface defining the contract for serialization and deserializing objects
 */
interface Serializer {

    /**
     * Serializes and writes an object
     * @param obj The object to serialize and store. It can be null
     * @throws Exception If an error occurs
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Reads and deserializes an object
     * @return The deserialized object, or null if reading fails or no object is present
     * @throws Exception if an error occurs
     */
    @Throws(Exception::class)
    fun read(): Any?
}