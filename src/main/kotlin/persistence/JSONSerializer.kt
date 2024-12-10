package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import ie.setu.models.Venue
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * Serializer that handles reading from and writing to venue.json
 * @constructor Initializes the serializer with a file (venue.json) to read from and write to
 * @property file The file used for storing and loading serialized data in JSON format
 */
class JSONSerializer(private val file: File) : Serializer {

    /**
     * Reads and deserializes an object from the specified JSON file
     * @return The deserialized object from the file
     * @throws Exception If an error occurs
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Venue::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Serializes and writes an object to the specified JSON file
     * @param obj the object to serialize and write to the file
     * @throws Exception If an error occurs
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}