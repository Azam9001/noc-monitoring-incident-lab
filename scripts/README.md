# Auswertungs-Skripte

Dieses Verzeichnis enthält kleine, vollständig lokal ausführbare **Python-Standardbibliothek**-Beispiele zur Auswertung synthetischer CSV-Dateien.

**Voraussetzung:** Python 3.9 oder neuer. Keine externen Pakete erforderlich.

## Ausführen

Im Repository-Hauptverzeichnis:

```bash
python3 scripts/analyze_alarms.py
python3 -m unittest discover -s tests -v
```

Unter Windows kann statt `python3` auch `py -3` verwendet werden.

## Daten- und Sicherheitsgrenzen

Das Skript liest nur `alarms/sample-alarms.csv`. Es verbindet sich nicht mit Netzgeräten, Nokia NetAct, Zabbix, SNMP oder externen APIs. Die Ausgabe ist eine **berechnete Statistik fiktiver Beispieldaten**, keine tatsächliche Betriebsmetrik.
