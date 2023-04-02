package app.futured.androidprojecttemplate

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.util.Properties

/**
 * Get value of specified property.
 * @param path Path to the properties file in project structure. E.g.: ./properties/debug.properties
 * @param propertyName Name of the property from the file.
 * @return String value by the property name or throw Exception.
 * */
@Throws(FileNotFoundException::class, IOException::class)
fun getProperty(path: String, propertyName: String): String {
    val file = File(path)
    if (!file.exists()) {
        throw FileNotFoundException("File ${file.absoluteFile} was not found")
    }

    val props = Properties()
    props.load(file.reader())
    return props.getProperty(propertyName) ?: throw NullPointerException("Property: $propertyName was not found")
}

/**
 * Get Properties object with all properties in the specified file in parameter.
 * @param path Path to the properties file in project structure. E.g.: ./properties/debug.properties
 * @return Properties object or throw Exception.
 * */
@Throws(FileNotFoundException::class, IOException::class)
fun getProperties(path: String): Properties {
    val file = File(path)
    if (!file.exists()) {
        throw FileNotFoundException("File ${file.absoluteFile} was not found")
    }

    val props = Properties()
    props.load(file.reader())
    return props
}
