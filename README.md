# Android `strings.xml` Difference Finder

A simple Kotlin utility to compare the main Android `strings.xml` file with a language-specific `strings.xml` file and find the **missing strings**.

You provide:

1. The **main `strings.xml` file**
2. The **language code** (for example, Spanish)

The tool compares both files and prints the strings that are present in the main file but missing from the selected language file.

## ✨ Features

* Compare the main `strings.xml` with any supported language
* Find missing string resources
* Pass a language code instead of manually providing the language file path
* Print missing strings in Android XML format
* Useful for checking translation/localization completeness

## 🚀 How It Works

The main `strings.xml` contains all the application's strings.

For example:

### Main `strings.xml`

```xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome">Welcome</string>
    <string name="login">Login</string>
</resources>
```

The Spanish `strings.xml` contains:

```xml
<resources>
    <string name="app_name">My App</string>
</resources>
```

When you run the tool with the Spanish language code, it compares the two files.

### Output

The tool identifies the strings that are available in the main `strings.xml` but missing from the Spanish file:

```xml
<string name="welcome">Welcome</string>
<string name="login">Login</string>
```

So the output tells you exactly which strings need to be added to the Spanish translation file.

## 🛠️ Usage

The language file is selected using `LanguageCode`.

For example, to compare the main file with Spanish:

```kotlin
fun main() {
    val missingStrings = Utils.findMissingStrings(
        Utils.getMainFile(),
        Utils.getFile(LanguageCode.SPANISH)
    )

    println("Missing strings: ${missingStrings.size}")

    missingStrings.forEach { (key, value) ->
        println("""<string name="$key">$value</string>""")
    }
}
```

### Change the Language

You only need to change the language code:

```kotlin
LanguageCode.SPANISH
```

For example:

```kotlin
LanguageCode.HINDI
```

or another language supported by the project.

The corresponding language file path is handled internally by:

```kotlin
Utils.getFile(LanguageCode)
```

## 📊 Example

### Input 1 — Main `strings.xml`

```xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome">Welcome</string>
    <string name="login">Login</string>
</resources>
```

### Input 2 — Spanish `strings.xml`

```xml
<resources>
    <string name="app_name">My App</string>
</resources>
```

### Result

```text
Missing strings: 2
```

```xml
<string name="welcome">Welcome</string>
<string name="login">Login</string>
```

These strings are missing from the Spanish `strings.xml` and can be added to complete the translation file.

## 📁 File Selection

The main file is obtained using:

```kotlin
Utils.getMainFile()
```

The language-specific file is obtained using:

```kotlin
Utils.getFile(LanguageCode.SPANISH)
```

Therefore, you don't need to manually pass the path of the Spanish `strings.xml` every time.

## 🔄 Comparison Flow

```text
Main strings.xml
       │
       │
       ▼
Utils.getMainFile()
       │
       │
       ├──────────────┐
       │              │
       ▼              ▼
   Compare      Spanish strings.xml
       │              ▲
       │              │
       │      Utils.getFile(
       │        LanguageCode.SPANISH
       │      )
       │
       ▼
Find missing strings
       │
       ▼
Print missing XML entries
```

## 🎯 Use Case

This tool is especially useful for Android applications that support multiple languages.

Whenever new strings are added to the main `strings.xml`, you can run this utility for each supported language to quickly find which strings have not yet been translated or added.

For example:

```text
Main strings.xml
       ↓
Spanish
       ↓
Find missing strings

Main strings.xml
       ↓
Hindi
       ↓
Find missing strings

Main strings.xml
       ↓
French
       ↓
Find missing strings
```

## 🤝 Contributing

Contributions and improvements are welcome.

Feel free to open an issue or submit a pull request.

## 📄 License

Add your project's license information here.

---

⭐ If this project helps you manage Android translations, consider giving the repository a star!
