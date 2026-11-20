# Linux Lab 03 — SNMP-Grundlagen

> **Konzeptlabor:** Kein SNMP-Poll ausgeführt; keine echten Community-Strings, Geräteadressen oder Betreiberkonfigurationen.

## Was ist SNMP?

Das **Simple Network Management Protocol** kann Netzwerkgeräte überwachen. Ein Manager fragt Informationen eines Agents ab; über Traps/Notifications können Geräte auch Ereignisse melden.

| Begriff | Bedeutung |
|---|---|
| Manager | Monitoring-System, das Daten anfordert/empfängt |
| Agent | SNMP-Komponente am überwachten Gerät |
| OID | Objektkennung für einen bestimmten Messwert |
| MIB | Beschreibung von verwalteten Objekten |
| Polling | Regelmäßige Abfrage |
| Trap | Vom Agent ausgelöste Meldung |

## Sicherheit

SNMPv3 unterstützt Authentifizierung und Verschlüsselung und ist für sichere Umgebungen häufig die sinnvollere Wahl. Keine Standardpasswörter, Community-Strings oder Credentials ins GitHub-Repository eintragen.

## Nur als Befehlsform, NICHT ausgeführt

```bash
# Syntax-Beispiel; die Platzhalter sind absichtlich nicht lauffähig:
# snmpget -v3 -l authPriv -u <LAB_USER> -a SHA -A <LAB_AUTH> -x AES -X <LAB_PRIV> <LAB_HOST> <OID>
```

**Niemals ohne Einwilligung fremde IP-Bereiche oder Geräte abfragen.**

## Übung

1. Unterschied zwischen Polling und Trap erklären.
2. Warum SNMPv3 gegenüber ungeschütztem SNMPv2c vorteilhaft sein kann?
3. Was muss bei unbestätigter Interface-Störung im Incident-Ticket stehen?

<details><summary>Antwortideen</summary>

1. Polling = Manager fragt ab; Trap = Agent sendet Ereignis.
2. SNMPv3 bietet geeignete Sicherheitsmechanismen, sofern korrekt konfiguriert.
3. Beobachtung, Zeitpunkt, hypothetische Ursachen, betroffene Komponenten und nächsten Prüfschritt dokumentieren; keine erfundene Root Cause.

</details>
