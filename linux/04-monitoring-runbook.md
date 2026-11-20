# Monitoring-Runbook — Linux, Zabbix und SNMP (Lab)

> **Fiktive Ereignisse:** Diese Beispiele sind kein Ergebnis eines laufenden Zabbix- oder SNMP-Systems.

## 1. Neue Meldung

- Zeit, Host, Monitoring-Quelle und Item ermitteln.
- Monitoring-Trigger von bestätigtem Dienstimpact unterscheiden.
- Wiederholungen und korrelierte Ereignisse prüfen.

## 2. Erstdiagnose

- **Linux-Host:** Interface, IP, Route und lokale Dienste in einem autorisierten Lab prüfen.
- **Zabbix:** Auslösendes Item, zeitlichen Verlauf, Host-Verfügbarkeit und Trigger-Logik prüfen (nur als geplante Arbeitsschritte).
- **SNMP:** Manager/Agent-Verbindung und Berechtigungen prüfen; keine echten Zugangsdaten speichern.

## 3. Fiktives Beispiel

**LAB-MON-001:** CPU-Auslastung von LINUX-LAB-01 um 10:00 Uhr als 88 % eingetragen und um 10:06 Uhr mit 51 % als erholt dokumentiert.

**Beobachtung:** Zwei synthetische Werte. **Keine** Behauptung über real gemessene CPU-Auslastung.

**LAB-MON-002:** Interface-Down- und Up-Einträge zu NET-LAB-01. Ursache unbekannt, Ereignis nur im Datensatz beschrieben.

## 4. Eskalations- und Abschlussnotiz

Incident-ID, letzte Beobachtung, bestätigter Impact, Arbeitshypothesen, Status, zuständiges Team und nächster Schritt. Eine Recovery-Meldung beweist nicht automatisch, dass jede Dienstfunktion wiederhergestellt wurde.

## 5. Schichtübergabe

Offene Probleme mit Status und Verantwortlichkeit übergeben. Kein fiktives Ereignis als real ausgeführten Betrieb bezeichnen.
