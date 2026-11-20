# Lab 06 — Incident Triage Decision Tree im NOC

> **Wichtig:** Dieses Dokument ist ein **vollständig fiktives Lern- und Dokumentationslab**. Alle Standorte, Zeitpunkte, Netzereignisse, Prioritäten und Rollen in den Beispielen sind erfunden. Es beschreibt **keine** internen Vorgaben von Ucell, Nokia oder einem anderen Netzbetreiber. Es wurden keine produktiven Monitoring-Systeme bedient und keine realen Diagnosen durchgeführt.

## Lernziel

Im simulierten NOC einen Alarm strukturiert **triagieren**: Auswirkungen einschätzen, verwandte Alarme korrelieren, ein Ticket dokumentieren und gegebenenfalls an das richtige Team eskalieren.

**Vorwissen:** [Alarmbearbeitung](../procedures/alarm-handling.md), [Eskalationsprozess](../procedures/escalation-process.md) und [Alarmanalyse](../reports/alarm-analysis.md).

## 1. Entscheidungsbaum

```text
                  [Neuer Alarm / Alarm-Eintrag]
                               |
                     [Daten vollständig?]
                         /           \
                       Nein           Ja
                        |              |
           [Fehlende Daten prüfen]  [Duplikat oder Folgealarm?]
                                       /            \
                                      Ja             Nein
                                      |               |
                           [Bestehenden Incident]  [Neuen Incident]
                           [ergänzen / korrelieren] [anlegen]
                                      \               /
                                       [Auswirkung bekannt?]
                                           /      \
                                         Nein      Ja
                                          |         |
                               [Impact ermitteln] [Priorität gemäß
                               [und dokumentieren]  Lab-Matrix einstufen]
                                          \        /
                                       [Nächste Maßnahme]
                                                |
                               [Eigene Analyse ausreichend?]
                                      /                 \
                                    Ja                   Nein
                                    |                     |
                           [Status verifizieren]   [Zuständiges Team
                           [und dokumentieren]     eskalieren]
                                    |                     |
                              [Behoben?]           [Update verfolgen]
                               /     \                    |
                             Ja      Nein                  |
                             |        +--------------------+
                       [Schließen]        |
                                 [Offen mit Owner, Status,
                                  Zeitpunkt und Folgeschritt
                                  an Folgeschicht übergeben]
```

**Grundsatz:** Der angezeigte **Alarm-Schweregrad (Severity)** ist nicht automatisch identisch mit der **Incident-Priorität**. Für eine reale Priorisierung sind bestätigter Impact, Dringlichkeit, Betreiberregeln und SLA maßgeblich. Hier wird nur eine vereinfachte Übungsmatrix verwendet.

## 2. Vereinfachte Prioritätsmatrix (nur für dieses Lab)

| Bestätigte Auswirkung im Szenario | Dringlichkeit | Lab-Priorität | Beispielaktion |
|---|---|---|---|
| Vollständiger Dienst-/Zellausfall | Hoch | P1 | Sofortiges Incident-Ticket und Eskalationsprüfung |
| Teilweise Dienstbeeinträchtigung | Mittel | P2 | Zeitnahe technische Analyse und Update |
| Keine bestätigte Dienstunterbrechung; Degradation | Niedrig | P3 | Beobachtung, Dokumentation und Wiedervorlage |
| Auswirkung noch unbekannt | Unbekannt | Vorläufig / ungeklärt | Impact ermitteln, nicht blind P1 oder P3 annehmen |

*Die Matrix ist nicht als allgemeingültige Branchenregel zu verstehen.*

## 3. Triage-Checkliste

1. **Alarm erfassen:** ID, Timestamp, Standort, Technologie, Netzelement und Severity.
2. **Kontext prüfen:** Gibt es weitere Alarme am selben Standort oder mit zeitlichem Zusammenhang?
3. **Impact feststellen:** Welche Dienste sind nachweislich beeinträchtigt? Was ist noch unbekannt?
4. **Priorität vorläufig festlegen:** Auf Impact und Dringlichkeit statt nur auf Alarmtext stützen.
5. **Incident referenzieren:** Bestehenden Incident aktualisieren oder neuen anlegen.
6. **Hypothesen trennen:** Beobachtetes Ereignis und mögliche Ursache ausdrücklich unterscheiden.
7. **Zuständigkeit zuweisen:** NOC Second Line, Field Engineers oder Transport-Support je nach Hypothese und Zugriffsgrenzen.
8. **Status nachverfolgen:** Rückmeldungen und nächste Aktion festhalten.
9. **Abschluss prüfen:** Alarmstatus, beobachteter Dienststatus und mögliche Restprobleme dokumentieren.
10. **Schichtwechsel:** Offene Incidents mit Owner, Priorität, Status und nächstem Update übergeben.

## 4. Anwendung auf die bestehenden fiktiven Fälle

| Incident | Eingangssignale | Bewertung im Lab | Triage-Entscheidung |
|---|---|---|---|
| [INC-001](../incidents/INC-001.md) | LTE Cell Unavailable + Remote Unit Communication Failure | Im Szenario nicht verfügbare LTE-Zelle | P1; Alarme korrelieren, fiktives Field-Team einbeziehen |
| [INC-002](../incidents/INC-002.md) | Transport Link Down + NodeB Unreachable | Im Szenario Standort über Transport nicht erreichbar | P1; mögliche Transportursache prüfen, simuliert an Transport-Support eskalieren |
| [INC-003](../incidents/INC-003.md) | TRX Performance Degraded mit späterem Cleared-Eintrag | Keine bestätigte vollständige Dienstunterbrechung | P3; Verlauf beobachten, Ursache offenlassen |

Die Zuordnungen beruhen auf **ausgedachten Lab-Annahmen**, nicht auf Kundenmeldungen, echten KPIs oder einer Produktivmessung.

## 5. Aufgabe A — Welche Entscheidung triffst du?

**Fall A (fiktiv):** Für SITE-A01 werden zwei Alarme innerhalb von zwei Minuten gemeldet. Für den ersten existiert bereits ein Incident. Was sollte mit dem zweiten Alarm passieren?

**Fall B (fiktiv):** Ein Minor-Alarm zeigt „Performance Degraded“, aber die tatsächliche Kundenauswirkung ist noch unbekannt. Darf der Incident ohne weitere Prüfung als P3 mit „kein Impact“ geschlossen werden?

**Fall C (fiktiv):** Ein Alarm ist weiterhin aktiv. Das Field-Team hat eine Diagnose angefordert, aber es gibt noch keine Rückmeldung. Was gehört in die Schichtübergabe?

<details>
<summary>Musterlösungen anzeigen</summary>

- **Fall A:** Prüfen, ob es ein Folgealarm desselben Fehlers sein könnte; bei nachvollziehbarer Korrelation im vorhandenen Incident dokumentieren, statt automatisch einen zweiten unabhängigen Fall anzulegen.
- **Fall B:** **Nein.** Erst Impact und Dringlichkeit einschätzen und Unbekanntes sichtbar dokumentieren. Alarm-Severity allein ist kein Beweis für geringen Impact.
- **Fall C:** Incident-ID, Standort, aktiver Alarm, aktueller Kenntnisstand, zuständiges Team, ausstehende Rückmeldung, Priorität, letzte Aktion und nächster Prüftermin. Nicht als behoben deklarieren.

</details>

## 6. Aufgabe B — Beispiel für eine saubere Ticketnotiz

**Fiktiver Eingang:** `SITE-B02`, Alarme `ALM-1003` und `ALM-1004`; `Transport Link Down` trat vor `NodeB Unreachable` auf.

<details>
<summary>Beispielnotiz öffnen</summary>

**Incident:** INC-002 (Simulation)  
**Status zu Beginn:** In Bearbeitung  
**Feststellung:** Zwei zeitnahe Alarme betreffen das gleiche simulierte UMTS-Netzelement.  
**Hypothese:** Eine Transportstörung könnte den Erreichbarkeitsalarm verursachen.  
**Impact:** Im Lab-Szenario ist der Standort nicht erreichbar; keine reale Messung.  
**Priorität:** P1 nach vereinfachter Lab-Matrix.  
**Aktion:** Korrelierte Alarm-IDs dokumentieren; fiktives Transport-Team informieren.  
**Nächster Schritt:** Rückmeldung zur Transportstrecke einholen und Alarmstatus nach Wiederherstellung verifizieren.  
**Root Cause:** Zu diesem Zeitpunkt nicht bestätigt.

</details>

## 7. Erfolgskriterien

- [ ] Ich kann **Severity** von **Incident-Priorität** unterscheiden.
- [ ] Ich erkenne zusammengehörige Alarme als möglichen gemeinsamen Incident.
- [ ] Ich dokumentiere unbekannten Impact, statt ihn zu erfinden.
- [ ] Ich benenne eine sinnvolle Eskalationsrolle.
- [ ] Ich erstelle eine Übergabe mit eindeutigem nächsten Schritt.

## Nächster kleiner Schritt

**Lab 07 — Incident-Triage-Übungsfälle:** Weitere unabhängige fiktive Alarme selbst priorisieren und die Entscheidungen schriftlich begründen.

---

*Portfolio-Dokumentation für Network Operations in Deutschland; kein Nachweis einer produktiven Monitoring-Umgebung oder real durchgeführter Einsätze.*
