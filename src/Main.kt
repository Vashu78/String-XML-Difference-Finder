import java.io.File

fun main() {
    val path = File("C:\\Users\\Admin\\Downloads\\strings 1.xml")
    val outputPath = File("C:\\Users\\Admin\\Downloads\\corrected.xml")

    Utils.removeDuplicateKeys(path, outputPath)

}