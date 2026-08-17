import java.io.File

fun main() {
//    val missingStrings = Utils.findMissingStrings(
//        Utils.getMainFile(),
//        Utils.getFile(LanguageCode.SPANISH)
//    )

//    println("Missing strings: ${missingStrings.size}")
//    missingStrings.forEach { (key, value) ->
//        println("""<string name="$key">$value</string>""")
//    }

    val path = File("C:\\Users\\Admin\\Downloads\\strings 1.xml")
    val outputPath = File("C:\\Users\\Admin\\Downloads\\corrected.xml")

    Utils.removeDuplicateKeys(path, outputPath)

}