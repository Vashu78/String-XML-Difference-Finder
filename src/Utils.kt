import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult


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

    fun findDuplicateKeys(file: File): List<String> {
        val document = DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse(file)

        val keys = mutableListOf<String>()

        val nodes = document.getElementsByTagName("string")

        for (i in 0..<nodes.length) {
            val key = nodes.item(i)
                .attributes
                .getNamedItem("name")
                .nodeValue

            keys.add(key)
        }

        return keys
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .map { (key, count) ->
                "$key ($count times)"
            }
    }

    fun removeDuplicateKeys(
        inputFile: File,
        outputFile: File
    ) {
        val factory = DocumentBuilderFactory.newInstance()
        val document = factory
            .newDocumentBuilder()
            .parse(inputFile)

        val resources = document.documentElement
        val nodes = document.getElementsByTagName("string")

        val seenKeys = mutableSetOf<String>()

        // Store nodes that need to be removed
        val duplicateNodes = mutableListOf<org.w3c.dom.Node>()

        for (i in 0 until nodes.length) {
            val node = nodes.item(i)

            val key = node.attributes
                .getNamedItem("name")
                .nodeValue

            if (!seenKeys.add(key)) {
                duplicateNodes.add(node)
            }
        }

        // Remove duplicate nodes
        duplicateNodes.forEach { node ->
            resources.removeChild(node)
        }

        // Create new XML file
        val transformer = TransformerFactory
            .newInstance()
            .newTransformer()

        transformer.setOutputProperty(
            OutputKeys.INDENT,
            "yes"
        )

        transformer.setOutputProperty(
            OutputKeys.ENCODING,
            "UTF-8"
        )

        transformer.transform(
            DOMSource(document),
            StreamResult(outputFile)
        )

        println("Cleaned file created:")
        println(outputFile.absolutePath)
    }
}