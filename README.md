# MSG to EML Converter

This tool converts Outlook `.msg` files to standard-compliant `.eml` files. The `.eml` files conform to the RFC 822 format and are compatible with all common email clients (e.g., Thunderbird, Apple Mail, Outlook, etc.).

## 📦 Prerequisites

- Java 21 or higher
- Maven 3.x

## 🔧 Build

The project is built with Maven:

```sh
mvn clean package
```

After a successful build, you will find an executable JAR file in the `target` directory:
```
target/msg-to-eml-converter-1.0.0-shaded.jar
```

## ▶️ Usage

The conversion is performed via the command line:

```sh
java -jar target/msg-to-eml-converter-1.0.0-shaded.jar <input.msg> <output.eml>
```

Example:
```sh
java -jar target/msg-to-eml-converter-1.0.0-shaded.jar my-email.msg my-email.eml
```

## 🔗 Dependencies

This project uses the following library:

- [outlook-message-parser](https://github.com/bbottema/outlook-message-parser) 

## ⚠️ Note

- All headers from the original message are preserved.
- Attachments are correctly included.
- The body is preserved as HTML or plain text (depending on the content of the original message).

## 📜 License

APL 2.0. See [LICENSE](LICENSE).
