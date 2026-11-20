# Linux Lab 02 — Zabbix-Monitoring-Design

> **Entwurf, kein laufender Zabbix-Server.** Hostnamen, Items, Grenzwerte und Status sind fiktiv. Es wurde keine Verbindung zu einer Monitoring-Plattform hergestellt.

## Fiktive Architektur

```text
[LINUX-LAB-01] --Agent/Passive Checks--> [Zabbix Server (geplant)]
[NET-LAB-01] ---SNMP (geplant)-------> [Zabbix Server (geplant)]
                                       |
                                [Dashboard / Trigger]
```

## Beispielhafte Monitoring-Items

| Host | Item/Kennzahl | Prüfidee | Einschränkung |
|---|---|---|---|
| LINUX-LAB-01 | CPU-Auslastung | Trend und Grenzwert | Kein echter Messwert |
| LINUX-LAB-01 | Speicherplatz frei | Warnschwelle | Keine echte Konfiguration |
| LINUX-LAB-01 | Agent-Verfügbarkeit | Verbindungsstatus | Agent nicht installiert |
| NET-LAB-01 | Interface-Status | Änderung von up/down | SNMP nicht eingerichtet |

## Fiktive Trigger-Logik (Pseudoregeln)

- **Warnung:** CPU-Auslastung über 85 % über einen zusammenhängenden Zeitraum von 5 Minuten.
- **Kritisch:** Ein wichtiges Interface ist mindestens 3 Minuten nicht verfügbar.
- **Störmeldung:** Zabbix-Agent ist für einen vorab festgelegten Zeitraum nicht erreichbar.

**Wichtig:** Das sind textliche Entwürfe, keine ausführbaren Zabbix-Trigger-Expressions. Details hängen von Template, Item-Key, Versionsstand und Messintervall ab. Zahlen sind didaktische Beispielschwellen.

## Triage

1. Trigger lesen, Zeitbereich eingrenzen.
2. Prüfen, ob dieselbe Ursache mehrere Items beeinflussen könnte.
3. Impact beurteilen und Ticket aktualisieren.
4. Ursachenhypothese von gesicherter Beobachtung unterscheiden.
5. Zuständiges Team einbeziehen.

## Spätere echte Umsetzung (separat nachweisen)

- Eigene Linux-VM und Zabbix-Instanz bereitstellen.
- Agent auf einem selbst kontrollierten Testhost einrichten.
- Item und Trigger anlegen und **im eigenen Lab** auslösen.
- Versionen, anonymisierte Screenshots und Messergebnisse dokumentieren.

**Status:** Nur Planung und Übung.
