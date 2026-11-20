# Lab 05 — Windows Network Troubleshooting Playbook

> **Fiktives NOC-Lernlabor.** Alle IP-Adressen, Geräte, Tickets, Zeitangaben, Symptome und Diagnosen sind erfunden. Es werden keine echten Systeme geprüft, keine Netzwerke verändert und keine vertraulichen Daten eines Arbeitgebers verwendet. Die in diesem Dokument beschriebenen Ergebnisse sind **angenommene Lab-Beobachtungen**, keine tatsächlich gemessenen Ergebnisse.

## Ziel

Ein reproduzierbares Vorgehen für die Erstdiagnose von Verbindungsproblemen unter Windows dokumentieren — vom Client über das lokale Netz und das Gateway bis zu DNS und einem Zielsystem.

**Vorkenntnisse:** [Subnetting](01-ipv4-subnetting.md), [VLAN](02-vlan-segmentation.md), [Inter-VLAN-Routing](03-inter-vlan-routing.md), [DNS/DHCP](04-dns-dhcp-troubleshooting.md).

## 1. Fiktive Ausgangslage

| Parameter | Annahme im Lab |
|---|---|
| Client | NOC-PC-01 |
| Client-IP | `10.50.10.10/27` |
| VLAN | 10 — Monitoring |
| Default Gateway | `10.50.10.1` |
| DNS-Server | `10.50.10.3` |
| Testziel in VLAN 20 | `10.50.10.40/27` |
| Zielname | `admin.lab.example` (rein fiktiv) |
| Erwarteter DNS-Eintrag | `admin.lab.example → 10.50.10.40` (nur Lab-Annahme) |
| Symptom | Die Anwendung meldet „Ziel nicht erreichbar“ (fiktiv) |

Die IP-Adressierung stimmt mit den vorherigen Labs überein. Zwischen VLAN 10 und VLAN 20 wird ein freigegebenes Layer-3-Gateway angenommen; ob Kommunikation erlaubt ist, hängt von den Sicherheitsregeln ab.

## 2. Diagnoseprinzip

**Von lokal nach entfernt — Fakten vor Hypothesen:**

1. **Symptom eingrenzen:** Welcher Dienst, welcher Client, seit wann, welche Auswirkungen?
2. **Netzwerkadapter und IP-Konfiguration prüfen:** Ist der Adapter aktiv? Stimmen Adresse, Maske, Gateway und DNS?
3. **Lokale Route prüfen:** Gibt es eine passende Route bzw. Default Route?
4. **Gateway testen:** Ist eine ICMP-Antwort möglich? Wenn nicht: Ursache offenlassen.
5. **Ziel-IP testen:** Besteht IP-Konnektivität? Ein fehlgeschlagener Ping beweist keinen vollständigen Ausfall.
6. **DNS getrennt prüfen:** Lässt sich der Zielname auflösen?
7. **Ergebnisse korrelieren:** VLAN, Gateway, Routing, ACL/Firewall, Host und Anwendung als mögliche Ursachen berücksichtigen.
8. **Ticket aktualisieren:** Beobachtungen von Vermutungen trennen, Verantwortlichkeit und nächsten Schritt festhalten.

## 3. Windows-Befehle (nur lesend / Diagnose)

Die Beispiele dürfen nur auf dem **eigenen oder ausdrücklich freigegebenen** Rechner und Netzwerk eingesetzt werden.

```powershell
ipconfig /all
route print -4
ping 10.50.10.1
ping 10.50.10.40
tracert 10.50.10.40
nslookup admin.lab.example 10.50.10.3
Get-DnsClientServerAddress -AddressFamily IPv4
```

| Befehl | Was wird geprüft? | Wichtige Einschränkung |
|---|---|---|
| `ipconfig /all` | Adapter, IP, Gateway, DHCP/DNS | Zeigt Konfiguration, keine End-to-End-Garantie |
| `route print -4` | IPv4-Routen | Route kann vorhanden sein, obwohl Verkehr blockiert wird |
| `ping <Gateway>` | ICMP-Antwort des Gateways | ICMP kann gefiltert werden |
| `ping <Ziel-IP>` | ICMP-Antwort vom Ziel | Fehlende Antwort beweist nicht, dass alle Dienste ausfallen |
| `tracert <Ziel-IP>` | Sichtbare Zwischenstationen | Hops können ICMP unterdrücken |
| `nslookup <Name> <DNS>` | DNS-Antwort des angegebenen Servers | Interne Lab-Namen funktionieren ohne eingerichtete Zone nicht |
| `Get-DnsClientServerAddress` | Konfigurierte DNS-Adressen | Kein Beweis für funktionierende DNS-Auflösung |

**Keine aktiven Netzänderungen in diesem Lab:** `ipconfig /release`, `ipconfig /renew`, Adapter-Neustarts sowie Änderungen an Firewall, Routing oder DNS gehören nicht zum Übungsablauf.

## 4. Aufgabe A — Symptome zuordnen

Ordne jedem Symptom die sinnvollste **erste** Prüfung zu:

| Nr. | Fiktive Beobachtung | Erste Prüfung (selbst ausfüllen) |
|---|---|---|
| A1 | Windows zeigt `169.254.18.27` | ? |
| A2 | Ziel-IP erreichbar, Name wird nicht aufgelöst | ? |
| A3 | Falsches Default Gateway eingetragen | ? |
| A4 | Gateway antwortet, Ziel-IP antwortet nicht | ? |

<details>
<summary>Musterlösung zu Aufgabe A</summary>

- **A1:** `ipconfig /all` und DHCP-/Adapter-/VLAN-Zustand prüfen. APIPA allein belegt keine konkrete Ursache.
- **A2:** `nslookup` sowie konfigurierte DNS-Server und DNS-Zone prüfen.
- **A3:** `ipconfig /all` und `route print -4` auswerten; korrekte Netzparameter dokumentieren.
- **A4:** Routing, Zielhost, VLAN/Trunk und Firewall-/ACL-Regeln prüfen. Eine fehlende Ping-Antwort reicht nicht zur Ursachenbestimmung.

</details>

## 5. Aufgabe B — Ticket nach einer simulierten Diagnose

**Fiktive Lab-Beobachtungen:**

- NOC-PC-01 hat die erwartete IP `10.50.10.10/27` und das Gateway `10.50.10.1`.
- Der Gateway-Ping liefert im **angenommenen Szenario** eine Antwort.
- Die direkte IP-Verbindung zu `10.50.10.40` wird im Szenario als erfolgreich angenommen.
- Die DNS-Abfrage nach `admin.lab.example` schlägt im Szenario fehl.
- Es gibt **keine bestätigte Root Cause**.

**Aufgabe:** Schreibe eine kurze Ticketnotiz mit *Symptom*, *gesicherten Lab-Beobachtungen*, *Hypothese*, *nächster Aktion*.

<details>
<summary>Muster-Ticket anzeigen</summary>

**Ticket:** LAB-NET-005 (fiktiv)  
**Typ:** Incident / Name Resolution  
**Priorität:** P3 (nur Lab-Annahme, kein Betreiber-SLA)  
**Status:** Offen — Diagnoseübung  

**Symptom:** Anwendung kann `admin.lab.example` nicht erreichen.

**Beobachtungen (simuliert):** Client-IP und Gateway entsprechen dem Adressplan; im angenommenen Test antworten Gateway und Ziel-IP, aber die DNS-Namensauflösung schlägt fehl.

**Hypothesen:** DNS-Eintrag fehlt, DNS-Zone fehlerhaft, Client nutzt falschen DNS-Server oder DNS-Anfragen werden gefiltert. Keine Ursache bestätigt.

**Nächster Schritt:** Im autorisierten Testnetz DNS-Servereinstellungen, Zone und direkte DNS-Abfrage mit dem zuständigen Team untersuchen.

**Wichtig:** Diese Notiz beschreibt nur ein ausgedachtes Szenario. Es gibt kein reales Testprotokoll und keine tatsächliche Behebung.

</details>

## 6. NOC-Troubleshooting-Checkliste

- [ ] Betroffene Anwendung und Beginn erfasst
- [ ] Client-IP und Subnetzmaske korrekt eingeordnet
- [ ] Gateway und IPv4-Routing geprüft
- [ ] IP-Verbindung und DNS getrennt bewertet
- [ ] ICMP-Fehlschläge nicht überinterpretiert
- [ ] Beobachtungen und Hypothesen getrennt dokumentiert
- [ ] Verantwortliches Team und nächster Schritt im Ticket angegeben

## 7. Selbstkontrolle

Beantworte ohne die Lösungen zu öffnen:

1. Warum reicht ein erfolgreicher Gateway-Ping nicht zum Nachweis einer funktionierenden DNS-Auflösung?
2. Was zeigt `route print -4`?
3. Welche zwei möglichen Ursachen gibt es, wenn nur der Name, aber nicht die IP-Verbindung fehlschlägt?
4. Warum darfst du bei einem Ping-Timeout nicht automatisch „Netzwerk komplett ausgefallen“ schreiben?

**Erfolgskriterium:** Du kannst alle vier Fragen beantworten und das fiktive Ticket strukturiert ausfüllen.

## Nächster Schritt

**Lab 06 — Incident-Triage-Entscheidungsbaum:** Beobachtungen, Priorität, Korrelation und Eskalation in einer klaren Entscheidungshilfe verbinden.

---

*Diese Dokumentation ist eine simulierte Übung für das Bewerbungsportfolio im Bereich Network Operations. Sie ist weder ein Auszug aus produktiven Tickets noch ein Nachweis real durchgeführter Messungen.*
