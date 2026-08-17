# String XML Difference Finder

A simple tool to compare Android `strings.xml` files and identify differences between them.

This project helps Android developers quickly find **missing, added, modified, or unchanged string resources** when comparing two `strings.xml` files.

## ✨ Features

* 🔍 Compare two `strings.xml` files
* ➕ Find newly added strings
* ➖ Find missing strings
* ✏️ Detect modified string values
* ✅ Identify matching strings
* 📋 Generate an easy-to-read difference report
* 🚀 Useful for checking translations and keeping Android string resources synchronized

## 📂 Example

Suppose you have two files:

**File 1 – `strings.xml`**

```xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome">Welcome</string>
    <string name="login">Login</string>
</resources>
```

**File 2 – `strings.xml`**

```xml
<resources>
    <string name="app_name">My App</string>
    <string name="welcome">Hello</string>
    <string name="logout">Logout</string>
</resources>
```

The tool can identify:

```text
Modified:
  welcome
  File 1: Welcome
  File 2: Hello

Missing:
  login

Added:
  logout

Unchanged:
  app_name
```

## 🛠️ Use Cases

This tool can be useful when:

* Comparing `strings.xml` between two Android projects
* Checking translation files
* Finding missing localization keys
* Verifying changes between app versions
* Migrating or merging Android projects
* Reviewing string-resource changes before a release

## 🚀 Getting Started

### Clone the repository

```bash
git clone https://github.com/<your-username>/<your-repository>.git
cd <your-repository>
```

### Run the project

Add the command required by your implementation here.

For example:

```bash
python main.py file1.xml file2.xml
```

> Replace the command above with the actual command used by your project.

## 📊 Difference Types

| Difference    | Description                                                |
| ------------- | ---------------------------------------------------------- |
| **Added**     | String exists in the second file but not the first         |
| **Missing**   | String exists in the first file but not the second         |
| **Modified**  | String key exists in both files but the value is different |
| **Unchanged** | String key and value are identical in both files           |

## 📁 Project Structure

```text
.
├── README.md
├── ...
└── ...
```

Update this section according to your project's actual structure.

## 🤝 Contributing

Contributions are welcome!

1. Fork the repository
2. Create a new branch

```bash
git checkout -b feature/my-feature
```

3. Make your changes
4. Commit your changes

```bash
git commit -m "Add my feature"
```

5. Push the branch

```bash
git push origin feature/my-feature
```

6. Open a Pull Request

## 🐛 Issues

If you find a bug or have a feature request, please open an issue in the GitHub repository.

When reporting a bug, include:

* Input files or a minimal reproducible example
* Expected result
* Actual result
* Steps to reproduce

## 📄 License

This project is licensed under the **MIT License**.

See the `LICENSE` file for more information.

---

⭐ If this project is useful to you, consider giving the repository a star!
