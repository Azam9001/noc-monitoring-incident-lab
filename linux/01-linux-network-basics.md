# Linux Lab 01 — Netzwerkdiagnose

> **Nur eigene oder freigegebene Linux-Lab-Systeme.** Keine realen Ergebnisse und keine produktiven Systeme.

## Lernziele

Interface-Status, IP-Konfiguration, Routen, DNS-Einstellungen und lokale Socket-Informationen lesend prüfen.

## Befehle

```bash
ip -br address
ip -br link
ip route
ip route get 1.1.1.1
ss -tuln
cat /etc/resolv.conf
getent hosts example.com
```

| Befehl | Zweck |
|---|---|
| `ip -br address` | Interface-IP-Adressen anzeigen |
| `ip -br link` | Link-Zustand ansehen |
| `ip route` | Routingtabelle anzeigen |
| `ip route get 1.1.1.1` | Lokale Routenentscheidung ermitteln (kein Nachweis einer externen Verbindung) |
| `ss -tuln` | Lokal lauschende TCP-/UDP-Sockets auflisten |
| `cat /etc/resolv.conf` | Konfigurierte Resolver-Hinweise ausgeben (können durch lokale Dienste verwaltet werden) |
| `getent hosts example.com` | Namensauflösung gemäß Systemkonfiguration abfragen |

## Übung

Welcher Befehl hilft beim Finden eines falschen Gateways? **Antwort:** `ip route`. Welcher Befehl zeigt eine lokale IP-Adresse? **Antwort:** `ip -br address`.

**Erfolg:** Jeden Befehl erklären können. Screenshots erst nach echtem autorisierten Test hinzufügen. **Keine Beispielausgaben als echte Messungen ausgeben.**
