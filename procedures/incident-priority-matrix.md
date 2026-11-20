# Incident-Priorität — Lernmatrix

> **Fiktive Lernregel:** Keine echte SLA-, OLA- oder Betreiberregel. Severity eines Alarms und Incident-Priorität unterscheiden sich.

| Impact | Dringlichkeit | Beispielpriorität | Reaktion |
|---|---|---|---|
| Vollständiger Ausfall eines Dienstes im Lab | Hoch | P1 | Sofort triagieren und Eskalationsbedarf prüfen |
| Teilweise Beeinträchtigung | Mittel | P2 | Zeitnah analysieren |
| Degradation ohne bestätigten Ausfall | Niedrig | P3 | Dokumentieren und beobachten |
| Unbekannt | Unbekannt | Vorläufig | Erst Auswirkung bestimmen |

## Wann eskalieren?

- Nachvollziehbare Hinweise auf physische Störung: fiktives Field-Team.
- Mögliche Transportabhängigkeit: Transport-Support.
- Weiterführende technische Analyse: NOC Second Line.
- Unklare Zuständigkeit: Owner und Eskalationsweg dokumentieren.

**Keine festen Reaktionszeiten:** Sie hängen in echten Organisationen von Verträgen und Prozessen ab.

## Qualitätssicherung

Vor jedem Abschluss prüfen: Alarmstatus, Dienststatus (soweit nachweisbar), letzte Rückmeldung, offene Restprobleme, Root-Cause-Status und Schichtübergabe.
