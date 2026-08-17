import java.io.File
import javax.xml.parsers.DocumentBuilderFactory


object Utils {
    fun getStringResources(file: File): Map<String, String> {
        val document = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(file)

        return document
            .getElementsByTagName("string")
            .let { nodes ->
                (0 until nodes.length).associate { index ->
                    val node = nodes.item(index)

                    val key = node.attributes
                        .getNamedItem("name")
                        .nodeValue

                    val value = node.textContent

                    key to value
                }
            }
    }

    fun getMainFile() =
        File("D:\\repo\\tf-executive-android-version_2.77_notification\\app\\src\\main\\res\\values\\strings.xml")

    fun getFile(language: LanguageCode): File {
        return File("D:\\repo\\tf-executive-android-version_2.77_notification\\app\\src\\main\\res\\values-${language.code}\\strings.xml")
    }

    fun findMissingStrings(
        englishFile: File,
        spanishFile: File
    ): Map<String, String> {

        val englishStrings = getStringResources(englishFile)
        val spanishStrings = getStringResources(spanishFile)

        return englishStrings.filterKeys { key ->
            key !in spanishStrings
        }
    }
}