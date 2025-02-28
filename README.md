# MSG zu EML Konverter

Dieses Tool konvertiert Outlook `.msg`-Dateien in standardkonforme `.eml`-Dateien. Die `.eml`-Dateien entsprechen dem RFC 822-Format und sind mit allen gängigen E-Mail-Clients (z. B. Thunderbird, Apple Mail, Outlook, etc.) kompatibel.

## 📦 Voraussetzungen

- Java 21 oder höher
- Maven 3.x

## 🔧 Build

Das Projekt wird mit Maven gebaut:

```sh
mvn clean package
```

Nach dem erfolgreichen Build findest du eine ausführbare JAR-Datei im Verzeichnis `target`:
```
target/msg-to-eml-converter-1.0.0-shaded.jar
```

## ▶️ Nutzung

Die Konvertierung wird über die Kommandozeile ausgeführt:

```sh
java -jar target/msg-to-eml-converter-1.0.0-shaded.jar <input.msg> <output.eml>
```

Beispiel:
```sh
java -jar target/msg-to-eml-converter-1.0.0-shaded.jar meine-email.msg meine-email.eml
```

## 🔗 Abhängigkeiten

Dieses Projekt verwendet die folgende Bibliothek:

- [outlook-message-parser](https://github.com/bbottema/outlook-message-parser) 

## ⚠️ Hinweis

- Alle Header aus der Originalnachricht werden übernommen.
- Anhänge werden korrekt eingebunden.
- Der Body wird als HTML oder Plaintext übernommen (je nach Inhalt der Originalnachricht).

## 📜 Lizenz

APL 2.0. Siehe [LICENSE](LICENSE).
