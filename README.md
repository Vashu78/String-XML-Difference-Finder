# Android `strings.xml` Difference Finder

A simple Kotlin utility for comparing Android `strings.xml` files,
finding missing string resources, detecting duplicate keys, and
generating a cleaned `strings.xml` file.

## ✨ Features

-   Compare a main `strings.xml` with another language `strings.xml`
-   Find string keys that exist in the main file but are missing from
    the other file
-   Return missing strings together with their values
-   Detect duplicate `string` resource keys in the same XML file
-   Show how many times each duplicate key occurs
-   Remove duplicate keys while keeping the first occurrence
-   Generate a new cleaned XML file without modifying the original file
-   Work directly with `java.io.File`

## 📁 Project Structure

A typical Android project can have:

``` text
app/
└── src/
    └── main/
        └── res/
            ├── values/
            │   └── strings.xml
            └── values-es/
                └── strings.xml
```

The utility does not require the files to be located in a specific
Android resource directory. You can provide any valid file path.

## 🛠️ Utils

The main functionality is contained in the `Utils` object.

### Read String Resources

``` kotlin
fun getStringResources(file: File): Map<String, String>
```

Reads all `<string>` resources from the supplied XML file and returns
them as a map:

``` text
string key → string value
```

For example:

``` xml
<string name="login">Login</string>
<string name="logout">Logout</string>
```

becomes:

``` text
login  → Login
logout → Logout
```

### Get a File

``` kotlin
fun getFile(path: String) = File(path)
```

Creates a `File` object from the supplied path.

Example:

``` kotlin
val file = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values\strings.xml"""
)
```

> On Windows, using a raw Kotlin string (`"""..."""`) is convenient when
> the path contains backslashes.

## 🔍 Find Missing Strings

Use:

``` kotlin
fun findMissingStrings(
    mainFile: File,
    otherFile: File
): Map<String, String>
```

This compares the keys in the main `strings.xml` with the keys in
another language file.

Only keys that exist in the main file and do not exist in the other file
are returned.

### Example

#### Main `strings.xml`

``` xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome">Welcome</string>
    <string name="login">Login</string>
    <string name="logout">Logout</string>
</resources>
```

#### Spanish `strings.xml`

``` xml
<resources>
    <string name="app_name">Mi aplicación</string>
    <string name="welcome">Bienvenido</string>
</resources>
```

### Usage

``` kotlin
val mainFile = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values\strings.xml"""
)

val spanishFile = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values-es\strings.xml"""
)

val missingStrings = Utils.findMissingStrings(
    mainFile,
    spanishFile
)

missingStrings.forEach { (key, value) ->
    println("""<string name="$key">$value</string>""")
}
```

### Output

``` xml
<string name="login">Login</string>
<string name="logout">Logout</string>
```

The returned `Map<String, String>` contains both the missing key and its
value from the main file.

## 🔁 Change the Language File

The utility does not contain language-specific logic. You simply provide
a different XML file.

For example:

``` kotlin
val hindiFile = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values-hi\strings.xml"""
)

val missingStrings = Utils.findMissingStrings(
    mainFile,
    hindiFile
)
```

The same method can be used for Spanish, Hindi, French, or any other
language file.

## 🔎 Find Duplicate Keys

Use:

``` kotlin
fun findDuplicateKeys(file: File): List<String>
```

This scans all `<string>` elements in a single `strings.xml` file and
finds keys that appear more than once.

### Example

``` xml
<resources>
    <string name="app_name">My App</string>
    <string name="login">Login</string>
    <string name="logout">Logout</string>
    <string name="login">Sign In</string>
    <string name="logout">Sign Out</string>
</resources>
```

### Usage

``` kotlin
val file = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values\strings.xml"""
)

val duplicateKeys = Utils.findDuplicateKeys(file)

duplicateKeys.forEach {
    println(it)
}
```

### Output

``` text
login (2 times)
logout (2 times)
```

The returned list contains the key and the number of times it appears.

## 🧹 Remove Duplicate Keys

Use:

``` kotlin
fun removeDuplicateKeys(
    inputFile: File,
    outputFile: File
)
```

This removes duplicate `<string>` resources from the input XML and
creates a new XML file.

### Important Behavior

The utility:

1.  Reads the input XML.
2.  Checks every `string` key.
3.  Keeps the first occurrence of each key.
4.  Removes subsequent occurrences.
5.  Writes the cleaned XML to the output file.
6.  Does not modify the original input file.

### Example

Input:

``` xml
<resources>
    <string name="app_name">My App</string>
    <string name="login">Login</string>
    <string name="logout">Logout</string>
    <string name="login">Sign In</string>
    <string name="settings">Settings</string>
    <string name="logout">Sign Out</string>
</resources>
```

Usage:

``` kotlin
val inputFile = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values\strings.xml"""
)

val outputFile = Utils.getFile(
    """D:\repo\DiffFinder\app\src\main\res\values\strings_cleaned.xml"""
)

Utils.removeDuplicateKeys(
    inputFile = inputFile,
    outputFile = outputFile
)
```

The generated file contains:

``` xml
<resources>
    <string name="app_name">My App</string>
    <string name="login">Login</string>
    <string name="logout">Logout</string>
    <string name="settings">Settings</string>
</resources>
```

The first `login` and first `logout` entries are retained.

## 🔄 Recommended Workflow

You can use the utility as a simple `strings.xml` validation workflow:

``` text
Main strings.xml
       │
       ├───────────────────────────┐
       │                           │
       ▼                           ▼
Compare with language       Check duplicate keys
       │                           │
       ▼                           ▼
Find missing strings        Find duplicate keys
       │                           │
       ▼                           ▼
Print missing key/value     Remove duplicate keys
                                   │
                                   ▼
                         Create cleaned XML file
```

## 📋 Complete Example

``` kotlin
fun main() {

    val mainFile = Utils.getFile(
        """D:\repo\DiffFinder\app\src\main\res\values\strings.xml"""
    )

    val spanishFile = Utils.getFile(
        """D:\repo\DiffFinder\app\src\main\res\values-es\strings.xml"""
    )

    // Find strings missing from Spanish
    val missingStrings = Utils.findMissingStrings(
        mainFile,
        spanishFile
    )

    println("Missing strings: ${missingStrings.size}")

    missingStrings.forEach { (key, value) ->
        println("""<string name="$key">$value</string>""")
    }

    // Find duplicate keys
    val duplicateKeys = Utils.findDuplicateKeys(mainFile)

    println("\nDuplicate keys: ${duplicateKeys.size}")

    duplicateKeys.forEach {
        println(it)
    }

    // Create a cleaned XML file
    val cleanedFile = Utils.getFile(
        """D:\repo\DiffFinder\app\src\main\res\values\strings_cleaned.xml"""
    )

    Utils.removeDuplicateKeys(
        inputFile = mainFile,
        outputFile = cleanedFile
    )
}
```

## ⚠️ Notes

-   The utility currently processes `<string>` resources.
-   `findMissingStrings()` compares resource keys, not translated
    values.
-   `findDuplicateKeys()` reports duplicate `name` attributes.
-   `removeDuplicateKeys()` keeps the first occurrence of a duplicate
    key.
-   The original input file is not modified when removing duplicates.
-   XML parsing is performed using `DocumentBuilderFactory`.
-   The cleaned XML is written using `TransformerFactory`.

## 🤝 Contributing

Contributions and improvements are welcome.

Feel free to open an issue or submit a pull request.

## 📄 License

Add your project's license information here.

------------------------------------------------------------------------

⭐ If this project helps you manage Android translations and
`strings.xml` cleanup, consider giving the repository a star!
