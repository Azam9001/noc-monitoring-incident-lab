# Alarm Analysis Report — NOC-Lab

> **Wichtig:** Dieser Bericht wertet ausschließlich **synthetische, fiktive Alarmdaten** aus. Standorte, Zeitstempel, Alarmnamen und Incident-Verläufe sind frei erfunden. Es bestehen keine Verbindungen zu produktiven Systemen oder Kundendaten.

## 1. Datengrundlage

- **Quelldatei:** [sample-alarms.csv](../alarms/sample-alarms.csv)
- **Simulierter Tag:** 18.11.2026
- **Datensatz:** 6 Alarm-Ereigniseinträge, 3 fiktive Standorte, 3 Incident-IDs
- **Technologien:** 2G/GSM, 3G/UMTS, 4G/LTE

**Zählweise:** Jede CSV-Zeile zählt als **ein Ereigniseintrag**, nicht zwingend als unabhängige Störung. Die beiden Einträge zu ALM-1005 und ALM-1006 bilden im Lab einen Statusverlauf innerhalb desselben Incidents ab. Die Statusangaben sind Momentaufnahmen der jeweiligen Einträge, keine Live-Zustände.

## 2. Verteilung nach Schweregrad

| Schweregrad | Einträge | Anteil |
|---|---:|---:|
| Critical | 2 | 33,3 % |
| Major | 2 | 33,3 % |
| Minor | 2 | 33,3 % |
| **Gesamt** | **6** | **100 %** |

*Anteile gerundet.*

## 3. Verteilung nach Technologie und Standort

| Standort (fiktiv) | Technologie | Alarm-Einträge | Zugeordneter Incident |
|---|---|---:|---|
| SITE-A01 | 4G/LTE | 2 | [INC-001](../incidents/INC-001.md) |
| SITE-B02 | 3G/UMTS | 2 | [INC-002](../incidents/INC-002.md) |
| SITE-C03 | 2G/GSM | 2 | [INC-003](../incidents/INC-003.md) |
| **Gesamt** | | **6** | **3 eindeutige Incidents** |

## 4. Status im Quelldatensatz

| Statusfeld | Anzahl der CSV-Einträge |
|---|---:|
| Cleared | 5 |
| Monitoring | 1 |

**Interpretation:** Der Eintrag ALM-1005 steht auf „Monitoring“ (11:20 Uhr); der spätere Eintrag ALM-1006 steht auf „Cleared“ (11:45 Uhr). Daraus folgt **nicht**, dass am Ende der simulierten Schicht ein Incident offen ist. Für den Abschlussstatus ist zusätzlich die [Schichtübergabe](shift-handover.md) maßgeblich: Alle drei Lab-Incidents wurden dort als geschlossen dokumentiert.

## 5. Alarmkorrelation und mögliche Ursachen

### INC-001 — LTE

- **ALM-1001:** Cell Unavailable, 08:10 Uhr
- **ALM-1002:** Remote Unit Communication Failure, 08:12 Uhr
- **Lab-Hypothese:** Gemeinsame Störung; die fiktive Fallbeschreibung nimmt eine lokale Unterbrechung der Stromversorgung als Ursache an.
- **Lernziel:** Mehrere Alarme zu einem Incident korrelieren, statt zwei unabhängige Tickets anzulegen.

### INC-002 — UMTS

- **ALM-1003:** Transport Link Down, 09:05 Uhr
- **ALM-1004:** NodeB Unreachable, 09:07 Uhr
- **Lab-Hypothese:** Transportausfall mit Folgealarm „NodeB Unreachable“.
- **Lernziel:** Zeitliche Reihenfolge und technische Abhängigkeit berücksichtigen.

### INC-003 — GSM

- **ALM-1005:** TRX Performance Degraded, 11:20 Uhr, Monitoring
- **ALM-1006:** TRX Performance Degraded, 11:45 Uhr, Cleared
- **Ursache:** Nicht nachgewiesen.
- **Lernziel:** Statusänderungen verfolgen und keine unbelegte Root Cause behaupten.

## 6. Erkenntnisse für den NOC-Betrieb

1. **Alarmanzahl ≠ Incident-Anzahl:** 6 Einträge wurden im Lab 3 Incidents zugeordnet.
2. **Korrelation:** Zwei zeitnah auftretende Alarme am selben Netzelement können eine gemeinsame Ursache haben.
3. **Schichtübergabe:** Der letzte dokumentierte Incident-Status ist wichtiger als eine isolierte historische CSV-Zeile.
4. **Fehleranalyse:** Hypothesen von bestätigten Ursachen trennen.
5. **Priorisierung:** Kritische Alarme zuerst bearbeiten; die konkrete Prioritätsmatrix ist in echten NOCs vom Betreiber und SLA abhängig.

## 7. Manuelle Prüfschritte

1. [CSV öffnen](../alarms/sample-alarms.csv) und die **6** Datenzeilen (ohne Header) zählen.
2. In der Spalte `severity` jeweils **2 × Critical**, **2 × Major** und **2 × Minor** prüfen.
3. In der Spalte `incident_id` die **3** verschiedenen Incident-IDs prüfen.
4. In der Spalte `status` **5 × Cleared** und **1 × Monitoring** finden.
5. Den simulierten Endstatus mit der [Schichtübergabe](shift-handover.md) abgleichen.

---

**Portfolio-Kontext:** Dokumentations- und Analyseübung für Network Operations, **kein Live-Monitoring**, keine originale Nokia-NetAct-Auswertung und kein reales Ereignis bei Ucell.
