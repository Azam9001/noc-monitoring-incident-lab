# Lab 08 — IPv4-Routing und Diagnose

> **Fiktives Lernlabor:** Keine realen Router und keine tatsächlich ausgeführten Tests.

## Szenario

Monitoring-PC `10.50.10.10/27` nutzt Gateway `10.50.10.1`. Admin-PC `10.50.10.40/27` nutzt Gateway `10.50.10.33`. Ein angenommenes Layer-3-Gerät verbindet die VLANs.

## Lernfragen

1. Ist `10.50.10.40` aus Sicht von `10.50.10.10/27` direkt im selben Subnetz?
2. Welche Route wird verwendet, wenn kein spezifischerer Eintrag existiert?
3. Was bedeutet ein fehlender Hop bei `tracert`?

<details><summary>Antworten</summary>

1. **Nein**, die Netze lauten `10.50.10.0/27` und `10.50.10.32/27`.
2. Die **Default Route**, sofern vorhanden und zulässig.
3. ICMP kann gefiltert werden; ein Stern beweist nicht automatisch einen vollständigen Netzwerkausfall.

</details>

## Windows-Diagnose (nur auf eigenem/freigegebenem PC)

```powershell
ipconfig /all
route print -4
tracert 10.50.10.40
```

Die privaten Lab-Adressen sind nicht als tatsächlich erreichbare Ziele eingerichtet. Erwartete Ausgaben werden **nicht** als Messungen ausgegeben.

## Ticket-Notiz (Simulation)

**Symptom:** Verbindung zwischen VLAN 10 und 20 ungeklärt.  
**Geprüft:** Adressplan rechnerisch plausibel.  
**Noch zu prüfen:** Routen, Gateway, VLAN-Trunk, ACL, Zielhost.  
**Status:** Offen, keine Ursache bestätigt.
