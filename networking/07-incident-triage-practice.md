# Lab 07 — Incident-Triage-Übungsfälle

> **Alle Daten sind fiktiv.** Die Prioritäten sind didaktische Entscheidungen, keine Vorgaben eines realen Betreibers.

## Entscheidungsregel

Erst **Impact und Dringlichkeit** prüfen, danach priorisieren. Ein Severity-Feld allein bestimmt nicht die Incident-Priorität. Verwandte Alarme korrelieren, Beobachtungen von Hypothesen trennen und für jedes offene Ticket einen Owner sowie die nächste Aktion notieren.

## Übung 1: Standort und Transport

**Ereignis:** Um 10:00 Uhr tritt für den erfundenen Standort SITE-D04 „Transport Link Down“ auf, um 10:02 Uhr „Node Unreachable“. Die Erreichbarkeit des Dienstes ist im Übungsszenario ausgefallen.

**Aufgaben:** (a) Anzahl der möglichen Incidents? (b) Welche Information ist gesichert? (c) Welches Team wird eingebunden? (d) Welche Lab-Priorität passt?

<details><summary>Musterantwort</summary>

Ein korrelierter Incident ist plausibel. Gesichert sind nur die zwei Alarme und der im Szenario angenommene Ausfall; die eigentliche Ursache muss überprüft werden. **P1** nach der vereinfachten Lab-Matrix; Transport-Support einbeziehen und nächstes Update dokumentieren. Kein tatsächlicher Netzausfall wurde gemessen.

</details>

## Übung 2: Einzelner Minor-Alarm

**Ereignis:** SITE-E05 meldet „Performance Degraded“, Auswirkungen unbekannt. Keine weiteren Alarme liegen im Beispiel vor.

**Aufgaben:** Soll das Ticket sofort geschlossen werden? Welche Informationen fehlen?

<details><summary>Musterantwort</summary>

Nein. Impact, Dauer, betroffene Komponenten und Trend erst ermitteln. Eine vorläufige Priorisierung ist möglich, bleibt aber bis zur Klärung dokumentiert. Ursache nicht erfinden.

</details>

## Übung 3: Offener Fall beim Schichtwechsel

**Ereignis:** SITE-F06 hat weiterhin „Cell Unavailable“. Eine Rückmeldung vom fiktiven Field-Team steht aus.

**Aufgabe:** Verfasse eine Schichtübergabe mit Incident-ID, Alarm, Status, zuständigem Team, letzter Aktivität und nächstem Prüfschritt.

<details><summary>Beispielübergabe</summary>

**LAB-INC-007** | SITE-F06 | Cell Unavailable | Offen, Ursache nicht bestätigt | Field-Team angefragt | Letzte Aktion: Eskalation gesendet | Nächster Schritt: Rückmeldung einholen und Alarmstatus prüfen. Zeit des nächsten Updates muss im realen Prozess vereinbart werden.

</details>

## Erfolgskriterien

- [ ] Jede Priorität kann anhand des Szenario-Impacts begründet werden.
- [ ] Kein hypothetischer Root Cause wird als bewiesen ausgegeben.
- [ ] Jedes offene Ticket hat einen nächsten Schritt.

**Nächster Schritt:** Die [Ticketvorlage](../incidents/TEMPLATE.md) für eigene fiktive Übungsfälle verwenden.
