# Java — Auswertung synthetischer Alarmdaten

> **Dieses Java-Programm verarbeitet ausschließlich lokale, fiktive CSV-Lab-Daten.** Es stellt keine Verbindung zu Nokia NetAct, Zabbix, SNMP oder Netzwerkgeräten her.

## Voraussetzungen

**Java JDK 17 oder neuer** mit `javac` und `java`. Keine externen Bibliotheken, kein Maven und kein Gradle erforderlich.

## Ausführen

Im Hauptverzeichnis des Repositorys (Windows PowerShell, CMD oder Terminal):

```bash
javac -d java/out java/src/AlarmAnalysis.java java/test/AlarmAnalysisTest.java
java -cp java/out AlarmAnalysis
java -cp java/out AlarmAnalysisTest
```

Unter Windows funktionieren dieselben Befehle, wenn das JDK im `PATH` installiert ist.

**Erwarteter Wert:** 6 synthetische Alarm-Einträge, 3 Incident-IDs, 3 fiktive Standorte, Severity Critical/Major/Minor jeweils 2.

## Aufbau

- [AlarmAnalysis.java](src/AlarmAnalysis.java) — lokale CSV-Auswertung mit Java-Standardbibliothek.
- [AlarmAnalysisTest.java](test/AlarmAnalysisTest.java) — einfache überprüfbare Assertions ohne externe Test-Bibliothek.
- [CSV-Quelle](../alarms/sample-alarms.csv) — fiktive Beispiele.
- [GitHub Actions](../.github/workflows/check-alarm-data.yml) — automatische Kompilierung und Prüfung.

## Technische Grenzen

Der einfache CSV-Leser unterstützt die mitgelieferte Beispieldatei mit einfachen durch Komma getrennten Feldern; er ist **kein allgemeiner RFC-4180-Parser** für Felder mit Kommas oder Anführungszeichen.

Die Ergebnisse sind reproduzierbare Berechnungen, **keine** realen Messwerte und **kein** Nachweis von Java-Erfahrung in einer produktiven NOC-Anwendung.
