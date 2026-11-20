# Lab 03 — Inter-VLAN-Routing und Default Gateway

> **Reines, fiktives Lernlabor:** Alle Geräte, VLANs, IP-Adressen und Tests sind simuliert. Keine echte Nokia-/Ucell-Infrastruktur, keine produktiven Konfigurationen oder Kundendaten. Dies ist ein **Planungs- und Diagnosebeispiel**, keine praktische Geräteinstallation.

## Lernziel

In diesem Lab lernst du, warum Geräte aus verschiedenen VLANs ein **Layer-3-Gateway** brauchen, wie ein Paket zwischen VLAN 10 und VLAN 20 weitergeleitet wird und welche Prüfungen ein NOC bei einem Verbindungsproblem dokumentiert.

**Voraussetzung:** [Lab 01 – IPv4-Subnetting](01-ipv4-subnetting.md) und [Lab 02 – VLAN-Segmentierung](02-vlan-segmentation.md).

## 1. Ausgangssituation

Ein fiktives NOC hat zwei getrennte Netze:

| Bereich | VLAN | Subnetz | Beispiel-PC | Default Gateway |
|---|---:|---|---|---|
| Monitoring | 10 | `10.50.10.0/27` | `10.50.10.10` | `10.50.10.1` |
| Administration | 20 | `10.50.10.32/27` | `10.50.10.40` | `10.50.10.33` |

**Subnetzmaske für beide VLANs:** `255.255.255.224`.

```text
[Monitoring-PC]                 [Admin-PC]
10.50.10.10/27                  10.50.10.40/27
GW 10.50.10.1                   GW 10.50.10.33
      | Access VLAN 10               | Access VLAN 20
      +---------------[L2-Switch]----+
                           |
                 Trunk: VLAN 10, 20
                           |
                   [Layer-3-Gateway]
                   VLAN 10: .1/27
                   VLAN 20: .33/27
```

**Annahme:** Das Gateway besitzt eine logische Layer-3-Schnittstelle pro VLAN (z. B. Subinterfaces oder Switch Virtual Interfaces). Die Trunk-Anbindung ist für beide VLANs freigegeben.

## 2. Warum wird ein Gateway benötigt?

Der Monitoring-PC erkennt anhand seiner eigenen Adresse und Maske, dass `10.50.10.40` **nicht** zu seinem lokalen Subnetz `10.50.10.0/27` gehört. Deshalb sendet er das IP-Paket an sein Default Gateway `10.50.10.1` (nach lokaler Adressauflösung).

Das Layer-3-Gerät kennt beide direkt angeschlossenen Netze und kann das Paket — **sofern die Kommunikation durch die Sicherheitsregeln erlaubt ist** — an VLAN 20 weiterleiten. Die Antwort des Admin-PCs nimmt entsprechend den Weg über `10.50.10.33`.

Ein Trunk allein führt **kein** Routing aus.

## 3. Aufgabe A — Ohne Lösungsvorgabe beantworten

1. Welches Default Gateway benutzt der Monitoring-PC `10.50.10.10/27`?
2. Warum darf der Monitoring-PC `10.50.10.40` nicht einfach als direkt erreichbaren Host in VLAN 10 behandeln?
3. Welches Default Gateway benötigt der Admin-PC?
4. Reicht eine Trunk-Verbindung zwischen zwei Layer-2-Switches für Inter-VLAN-Kommunikation aus?
5. Kann eine Firewall-Regel die Verbindung trotz korrekt konfiguriertem Routing blockieren?

<details>
<summary>Lösungen zu Aufgabe A anzeigen</summary>

1. `10.50.10.1`.
2. `10.50.10.40` liegt in `10.50.10.32/27` und damit außerhalb von `10.50.10.0/27`.
3. `10.50.10.33`.
4. **Nein.** Dafür ist eine Layer-3-Routing-Funktion nötig.
5. **Ja.** Die Gateway-/Routing-Konfiguration garantiert nicht, dass eine ACL oder Firewall den Verkehr zulässt.

</details>

## 4. Aufgabe B — Fehleranalyse

**Simulierter Fehler:** Der Monitoring-PC erreicht sein Gateway `10.50.10.1`, aber nicht den Admin-PC `10.50.10.40`. Nenne mindestens drei mögliche Ursachen und eine sinnvolle Reihenfolge zur Eingrenzung.

<details>
<summary>Beispielantwort anzeigen</summary>

**Mögliche Ursachen:**

- Die Layer-3-Schnittstelle für VLAN 20 ist nicht verfügbar.
- VLAN 20 wird auf dem benötigten Trunk nicht transportiert.
- Der Admin-PC hat eine falsche Netzmaske oder ein falsches Default Gateway.
- Eine ACL/Firewall blockiert den Testverkehr.
- Der Admin-PC ist ausgeschaltet oder sein Port ist nicht aktiv.
- Der Admin-PC verwirft ICMP-Anfragen, obwohl andere Dienste erreichbar sein könnten.

**Diagnosereihenfolge im Lab:**

1. IP-Konfiguration von Monitoring-PC und Admin-PC dokumentieren.
2. Lokale Erreichbarkeit zum jeweiligen Gateway separat prüfen.
3. Port- und VLAN-Zuordnung sowie Trunk-Freigaben mit dem zuständigen Team prüfen.
4. Layer-3-Schnittstellen, Routingtabelle und ACL-/Firewall-Regeln prüfen.
5. Ergebnis und Unsicherheiten im Ticket dokumentieren und bei Bedarf eskalieren.

**Wichtig:** Ein fehlgeschlagener Ping beweist **nicht automatisch**, dass der Zielhost oder das gesamte Routing ausgefallen ist.

</details>

## 5. Prüfkommandos für einen Windows-PC

Diese Befehle sind **Lernbeispiele für die lokale Diagnose**. Sie dürfen nur in einer eigenen oder ausdrücklich freigegebenen Testumgebung ausgeführt werden:

```powershell
ipconfig /all
route print -4
ping 10.50.10.1
tracert 10.50.10.40
```

- `ipconfig /all`: IP-Adresse, Maske und Default Gateway prüfen.
- `route print -4`: lokale IPv4-Routen ansehen.
- `ping`: ICMP-Erreichbarkeit testen (ein negativer Test ist nicht eindeutig).
- `tracert`: sichtbaren Pfad untersuchen (Firewall-Filter können Hops verbergen).

**Keine erfundenen Testergebnisse:** Da dieses Repository kein laufendes Lab-Netz bereitstellt, werden hier **keine** tatsächlichen Ping- oder Traceroute-Ausgaben behauptet.

## 6. Beispiel eines Incident-Eintrags

| Feld | Eintrag |
|---|---|
| Ticket | LAB-NET-003 (fiktiv) |
| Kategorie | Inter-VLAN Connectivity |
| Quelle | Monitoring-PC / VLAN 10 |
| Ziel | Admin-PC / VLAN 20 |
| Symptom | Ziel nicht erreichbar (nur Szenario) |
| Bereits geprüft | IP-Adressierung und Subnetzzuordnung rechnerisch geprüft |
| Noch zu prüfen | Gateway, VLAN-Trunk, Routing, ACL/Firewall, Zielhost |
| Status | Offen — Diagnoseübung, keine echte Entstörung |
| Nächster Schritt | Hypothesen im eigenen Testnetz verifizieren |

## 7. Erfolgskriterien

- [ ] Ich kann beiden PCs das korrekte Default Gateway zuordnen.
- [ ] Ich kann den Paketweg zwischen VLAN 10 und VLAN 20 erklären.
- [ ] Ich weiß, weshalb Trunk und Routing unterschiedliche Aufgaben haben.
- [ ] Ich kann mindestens drei Fehlerursachen für fehlende Inter-VLAN-Konnektivität nennen.
- [ ] Ich kann erklären, warum ein erfolgloser Ping allein keine Ursache beweist.

## Nächster kleiner Schritt

**Lab 04 — DNS und DHCP:** IP-Vergabe, DNS-Auflösung und typische NOC-Fehlersymptome in einer fiktiven Büroumgebung nachvollziehen.

---

*Portfolio für den Einstieg in Network Operations in Deutschland. Vollständig fiktives Dokumentations- und Verständnislabor.*
