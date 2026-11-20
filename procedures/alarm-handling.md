# Arbeitsanweisung: Alarmbearbeitung (Lab)

> **Simulierte SOP** — keine verbindliche Vorgabe eines Netzbetreibers.

## Workflow

1. **Erfassen:** Alarm-ID, Zeit, Standort, Netzelement, Technologie und Schweregrad.
2. **Bewerten:** Gibt es einen bestätigten Ausfall oder nur eine mögliche Beeinträchtigung?
3. **Korrelieren:** Folgealarme desselben Standorts und Zeitfensters zusammenführen.
4. **Dokumentieren:** Incident-ID, Beobachtung, Hypothesen und Untersuchungen festhalten.
5. **Eskalieren:** Bei vermutetem physischem oder übergreifendem Fehler zuständiges Team einbinden.
6. **Verifizieren:** Status nach Entstörung erneut prüfen.
7. **Abschließen / Übergeben:** Abschlussbeleg oder nächster Schritt mit Verantwortlichkeit dokumentieren.

## Beispielhafte Priorisierung

| Lab-Priorität | Beschreibung | Aktion |
|---|---|---|
| P1 | Dienst oder Zelle nicht verfügbar | Sofort bearbeiten und eskalieren |
| P2 | Teilweise Beeinträchtigung | Zeitnah analysieren |
| P3 | Degradation ohne bestätigten Ausfall | Beobachten und dokumentieren |

SLA, Prioritäten und Fristen sind in realen Organisationen abweichend geregelt.

## Qualitätskriterien

Jedes Ticket enthält Zeitstempel, Fakten, Unsicherheiten, Besitzer, Status und nächsten Schritt. Keine sensiblen Netz- oder Kundendaten speichern.
