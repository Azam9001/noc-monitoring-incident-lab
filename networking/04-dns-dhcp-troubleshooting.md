# Lab 04 — DNS- und DHCP-Troubleshooting unter Windows

> **Vollständig fiktives Lernlabor:** Alle Geräte, Namen, IP-Adressen, Diagnosen, Zeitpunkte und Ticket-Ereignisse sind erfunden. Es wird **kein** Netzwerk eines Arbeitgebers untersucht oder verändert. Beispielausgaben sind **nicht real gemessen**. Keine vertraulichen Ucell- oder Nokia-Daten.

## Lernziele

Nach diesem Lab kannst du:

1. die Aufgaben von **DHCP** und **DNS** unterscheiden,
2. Windows-IP-Konfiguration und Namensauflösung mit Bordmitteln prüfen,
3. eine fehlende DHCP-Lease von einem DNS-Problem unterscheiden,
4. Beobachtungen, Hypothesen und nächste Prüfschritte in einem NOC-Ticket dokumentieren.

**Voraussetzung:** [Lab 01 — IPv4-Subnetting](01-ipv4-subnetting.md), [Lab 02 — VLAN](02-vlan-segmentation.md), [Lab 03 — Routing](03-inter-vlan-routing.md).

## 1. Simulierte Netzwerkumgebung

Wir betrachten **VLAN 10 / NOC-MONITORING** aus den vorherigen Labs.

| Parameter | Fiktiver Wert |
|---|---|
| Subnetz | `10.50.10.0/27` |
| Subnetzmaske | `255.255.255.224` |
| Default Gateway | `10.50.10.1` |
| DHCP-Server | `10.50.10.2` |
| DNS-Server | `10.50.10.3` |
| Beispiel-Client | `10.50.10.10` |
| DHCP-Pool (Lab-Annahme) | `10.50.10.10`–`10.50.10.25` |
| Interner Testname | `monitor.lab.example` |
| Erwartete DNS-Antwort (nur Szenario) | `10.50.10.20` |

**Hinweis:** `lab.example` ist ein fiktiver Namensraum für diese Übung. Eine DNS-Zone oder ein DHCP-Dienst wird in diesem Repository **nicht** tatsächlich betrieben.

```text
 [Windows-Client: 10.50.10.10/27]
                |
          VLAN 10 / Switch
                |
       +--------+---------+
       |                  |
 [DHCP: 10.50.10.2] [DNS: 10.50.10.3]
                |
       [Gateway: 10.50.10.1]
```

## 2. Grundwissen

**DHCP (Dynamic Host Configuration Protocol)** stellt einem Client typischerweise die IP-Adresse, Subnetzmaske, Gateway-Adresse und DNS-Serverinformationen zur Verfügung.

**DNS (Domain Name System)** löst Namen in IP-Adressen auf. Eine funktionierende IP-Verbindung bedeutet deshalb nicht automatisch, dass auch die DNS-Namensauflösung funktioniert.

Eine IPv4-Adresse aus **169.254.0.0/16** kann bei Windows auf eine automatische Link-Local-Adresse (APIPA) hinweisen, zum Beispiel wenn keine gültige DHCP-Konfiguration bezogen werden konnte. **Sie beweist allein nicht**, dass der DHCP-Server ausgefallen ist.

## 3. Diagnosebefehle (Windows PowerShell oder Eingabeaufforderung)

Führe die **lesenden** Befehle nur auf einem eigenen PC bzw. in einem ausdrücklich freigegebenen Testnetz aus:

```powershell
ipconfig /all
route print -4
ping 10.50.10.1
nslookup monitor.lab.example 10.50.10.3
Get-DnsClientServerAddress -AddressFamily IPv4
```

**Was du damit prüfst:**

| Befehl | Interpretation |
|---|---|
| `ipconfig /all` | IPv4-Adresse, Subnetzmaske, DHCP-Status, Gateway, DNS-Server und Lease-Informationen |
| `route print -4` | Lokale IPv4-Routingtabelle / Default Route |
| `ping 10.50.10.1` | Ob der Gateway auf ICMP reagiert; ein Fehlschlag allein beweist keine Unerreichbarkeit |
| `nslookup monitor.lab.example 10.50.10.3` | DNS-Abfrage direkt beim angegebenen Server; ohne eingerichtete Lab-Zone wird sie wahrscheinlich nicht erfolgreich sein |
| `Get-DnsClientServerAddress -AddressFamily IPv4` | In Windows konfigurierte DNS-Server je Interface |

**Wichtig:** Das Beispielnetz `10.50.10.0/27` ist hier nicht tatsächlich aufgebaut. Diese Befehle liefern auf deinem normalen Heim-/Arbeitsnetz **andere Ergebnisse**. Nutze sie nur, um die Syntax zu verstehen; keine echten Netzprobleme anhand der fiktiven Adressen behaupten.

**Nicht ausführen, ohne Zustimmung und Folgen zu kennen:** `ipconfig /release`, `ipconfig /renew`, DNS-Cache-Änderungen oder Netzwerkkonfigurationsbefehle können bestehende Verbindungen stören. Sie sind **nicht Teil dieses Labs**.

## 4. Aufgabe A — DHCP-Fehler einordnen

**Fiktives Symptom:** Ein Windows-PC meldet `169.254.18.27` mit Maske `255.255.0.0`, obwohl für VLAN 10 eine Adresse aus `10.50.10.0/27` vorgesehen ist.

**Fragen:**

1. Ist `169.254.18.27` eine gültige Adresse aus dem geplanten VLAN-10-Subnetz?
2. Welches Problem **könnte** vorliegen?
3. Welche drei Prüfungen führst du zuerst durch?

<details>
<summary>Musterlösung A anzeigen</summary>

1. **Nein.** Die Adresse liegt im IPv4-Link-Local-Bereich, nicht in `10.50.10.0/27`.
2. Eine gültige DHCP-Lease könnte fehlen; die Ursache kann z. B. fehlende VLAN-Konnektivität, eine fehlerhafte DHCP-Serverkonfiguration oder eine Unterbrechung auf dem Weg zum DHCP-Server sein.
3. a) Physische Verbindung und Adapterstatus prüfen; b) VLAN-/Portzuordnung und DHCP-Einstellungen anhand der freigegebenen Dokumentation prüfen; c) Erreichbarkeit des DHCP-Dienstes bzw. DHCP-Relay durch das zuständige Team verifizieren. Keine ungeprüfte Root-Cause-Behauptung.

</details>

## 5. Aufgabe B — DNS-Problem eingrenzen

**Fiktives Symptom:** Der Windows-PC besitzt die korrekte Adresse `10.50.10.10/27`. Das Gateway `10.50.10.1` reagiert auf den vereinbarten Test. Der interne Name `monitor.lab.example` kann aber nicht aufgelöst werden.

**Fragen:**

1. Ist dies ein sicherer Beweis, dass DHCP ausgefallen ist?
2. Welche DNS-bezogenen Einstellungen oder Komponenten solltest du prüfen?
3. Was könnte ein erfolgreicher IP-Test bei gleichzeitig fehlgeschlagener Namensauflösung nahelegen?

<details>
<summary>Musterlösung B anzeigen</summary>

1. **Nein.** Eine korrekte IPv4-Konfiguration sagt allein nichts über den aktuellen DNS-Dienst aus.
2. Konfigurierte DNS-Serveradresse, Erreichbarkeit und Portzugriff auf den DNS-Server, Existenz und Zustand der internen Zone bzw. des Eintrags, Suchsuffix und eventuelle Filterregeln.
3. Ein Problem mit Namensauflösung oder DNS-Konfiguration ist plausibel. Andere Ursachen bleiben möglich und müssen geprüft werden.

</details>

## 6. Simuliertes Incident-Ticket

| Feld | Fiktiver Eintrag |
|---|---|
| Ticket-ID | LAB-NET-004 |
| Standort | NOC-LAB-VLAN10 |
| Zeitpunkt | 21.11.2026, 09:15 Uhr (simuliert) |
| Kategorie | DNS / Namensauflösung |
| Symptom | `monitor.lab.example` kann nicht aufgelöst werden |
| Bekannte Fakten | Client-Adresse in `10.50.10.0/27`; Gateway-Test im Szenario erfolgreich |
| Arbeitshypothesen | DNS-Server nicht erreichbar, DNS-Eintrag fehlt, DNS-Konfiguration fehlerhaft |
| Noch nicht bestätigt | Tatsächliche Ursache |
| Status | **Offen — nur Dokumentationsübung** |
| Nächster Schritt | DNS-Serverkonfiguration und Testabfrage in autorisierter Lab-Umgebung verifizieren |

## 7. NOC-Checkliste

- [ ] DHCP und DNS in eigenen Worten erklären
- [ ] Den Unterschied zwischen `10.50.10.10/27` und `169.254.18.27/16` erkennen
- [ ] `ipconfig /all` interpretieren können
- [ ] Den Zweck von `nslookup` erklären können
- [ ] Hypothesen und gesicherte Feststellungen im Ticket trennen
- [ ] Keine echten Netzänderungen ohne Autorisierung ausführen

## 8. Ergebnis überprüfen

1. Vergleiche beide Musterlösungen mit deinen Antworten.
2. Prüfe, dass VLAN 10 und Gateway zu [Lab 03](03-inter-vlan-routing.md) passen.
3. Kontrolliere, dass das Beispiel-Ticket **nicht** als gelöster realer Incident dargestellt wird.

## Nächster kleiner Schritt

**Lab 05 — Windows-Netzwerkdiagnose:** Ein strukturiertes Troubleshooting-Playbook mit `ipconfig`, `ping`, `tracert`, `nslookup` und Beispiel-Ticket für die Bewerbung als NOC Engineer erstellen.

---

*Fiktives Lehrmaterial für ein deutsches Network-Operations-Portfolio — keine produktive Überwachung und keine realen Arbeitgeberdaten.*
