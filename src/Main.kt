
fun main() {
    val missingStrings = Utils.findMissingStrings(
        Utils.getMainFile(),
        Utils.getFile(LanguageCode.HINDI)
    )

    println("Missing strings: ${missingStrings.size}")
    missingStrings.forEach { (key, value) ->
        println("""<string name="$key">$value</string>""")
    }
}