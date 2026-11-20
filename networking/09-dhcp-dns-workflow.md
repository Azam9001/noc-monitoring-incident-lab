# Lab 09 — DHCP und DNS: Diagnoseablauf

> **Fiktives Modell**, keine aktiven Netzwerkdienste.

## Einordnung

- **DHCP**: Automatische IP-Konfiguration, typischerweise IP-Adresse, Maske, Gateway und DNS-Server.
- **DNS**: Namensauflösung; IP-Erreichbarkeit und Namensauflösung sind getrennt zu prüfen.
- **APIPA**: Eine Windows-Adresse aus `169.254.0.0/16` kann auf fehlgeschlagenen DHCP-Bezug hinweisen; das ist kein Beweis für einen Serverausfall.

## Entscheidungsfolge

1. `ipconfig /all`: Ist eine erwartete IP-Adresse vorhanden?
2. Wenn nicht: DHCP-Konfiguration, Adapter, VLAN und DHCP-Erreichbarkeit als Hypothesen prüfen.
3. Wenn ja: Routing und Gateway separat untersuchen.
4. Wenn IP-Verbindung funktioniert, Name aber nicht: `nslookup` und DNS-Einstellungen prüfen.
5. Ergebnisse mit Zeitstempel und nächster Aktion ins Ticket schreiben.

## Befehle (nur lesend, autorisiertes Testnetz)

```powershell
ipconfig /all
route print -4
nslookup example.com
Get-DnsClientServerAddress -AddressFamily IPv4
```

Die Befehle dürfen echte lokale Einstellungen anzeigen. Diese Datei behauptet **keine** ausgeführten Tests.

## Mini-Fall

**Symptom:** `10.50.10.10/27` und Gateway sind im Szenario korrekt, aber `admin.lab.example` lässt sich nicht auflösen.

**Aufgabe:** Drei mögliche Ursachen nennen.

<details><summary>Musterlösung</summary>

Falscher DNS-Server, fehlender DNS-Eintrag, DNS-Erreichbarkeits-/Firewallproblem. Die tatsächliche Ursache bleibt unbestätigt.

</details>
