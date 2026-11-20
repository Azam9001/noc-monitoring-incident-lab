# NOC Monitoring & Network Operations Portfolio

**Deutschsprachiges GitHub-Portfolio mit fiktiven NOC-Fällen, Netzwerk-Grundlagen und Monitoring-Konzepten.**

> **Transparenz:** Sämtliche Site-IDs, IP-Pläne, Alarme, Incidents, Zeitstempel und Monitoring-Ereignisse in diesem Repository sind **simuliert**. Keine echten Ucell-/Nokia-Exporte, Betreiberkonfigurationen, Kundeninformationen oder vertraulichen Betriebsdaten. **Kein Live-NOC, kein laufender Zabbix-Server und keine tatsächlichen SNMP-Messungen** werden hier behauptet.

## Einstieg für Recruiter und technische Interviewer

Das Repository dokumentiert drei miteinander verbundene Lernbereiche:

1. **NOC Monitoring & Incident Management:** Alarmkorrelation, Fault Analysis, Priorisierung, Eskalation und Schichtübergabe.
2. **Network Infrastructure:** IPv4-Subnetting, VLANs, Routing, DNS/DHCP und Windows-Diagnose.
3. **Linux & Monitoring:** Linux-Netzwerkdiagnose sowie Entwürfe für Zabbix-/SNMP-Monitoring.

**Nachvollziehbarer technischer Teil:** Ein kleines, lokales Python-Skript zählt synthetische Alarm-Einträge aus CSV; ein Unit-Test überprüft die erwarteten Ergebnisse. Es findet **kein Netzwerkzugriff** statt.

## 1. NOC Monitoring & Incident Management

| Modul | Inhalt |
|---|---|
| [Fiktive Topologie](topology/network-overview.md) | Vereinfachte Mobilfunknetzübersicht |
| [Alarmdaten (CSV)](alarms/sample-alarms.csv) | 6 synthetische Alarm-Einträge |
| [INC-001 – LTE](incidents/INC-001.md) | Cell Unavailable / Alarmkorrelation |
| [INC-002 – UMTS](incidents/INC-002.md) | Transport Link Down / Folgealarm |
| [INC-003 – GSM](incidents/INC-003.md) | Performance Degraded / Ursache unbestätigt |
| [Ticketvorlage](incidents/TEMPLATE.md) | Einheitliche Dokumentation fiktiver Fälle |
| [Alarmbearbeitung](procedures/alarm-handling.md) | Beispielhafter SOP-Ablauf |
| [Eskalationsprozess](procedures/escalation-process.md) | Fiktive Rollen und Übergaben |
| [Prioritätsmatrix](procedures/incident-priority-matrix.md) | Severity, Impact, Dringlichkeit |
| [Alarmanalyse](reports/alarm-analysis.md) | Auswertung der CSV-Daten |
| [Schichtübergabe](reports/shift-handover.md) | Simuliertes Übergabeprotokoll |

## 2. Network Infrastructure

| Lab | Inhalt |
|---|---|
| [01 – IPv4-Subnetting](networking/01-ipv4-subnetting.md) | /27 berechnen |
| [02 – VLAN-Segmentierung](networking/02-vlan-segmentation.md) | Access und Trunk |
| [03 – Inter-VLAN-Routing](networking/03-inter-vlan-routing.md) | Default Gateways |
| [04 – DNS/DHCP](networking/04-dns-dhcp-troubleshooting.md) | Windows-Diagnose |
| [05 – Windows Troubleshooting](networking/05-windows-network-troubleshooting.md) | Diagnose-Playbook |
| [06 – Incident Triage](networking/06-incident-triage-decision-tree.md) | Entscheidungsbaum |
| [07 – Triage-Übungen](networking/07-incident-triage-practice.md) | Selbsttest |
| [08 – Routingdiagnose](networking/08-routing-diagnostics.md) | Route und Traceroute |
| [09 – DHCP/DNS-Workflow](networking/09-dhcp-dns-workflow.md) | Fehlerisolation |

## 3. Linux, Zabbix & SNMP

| Lab | Inhalt |
|---|---|
| [Linux-Lab Übersicht](linux/README.md) | Lernziele, Status und Grenzen |
| [Linux-Netzwerkbefehle](linux/01-linux-network-basics.md) | ip, ss, getent |
| [Zabbix-Konzept](linux/02-zabbix-monitoring-design.md) | Geplante Items und Trigger |
| [SNMP-Grundlagen](linux/03-snmp-monitoring.md) | Agent, Manager, OID, Sicherheit |
| [Monitoring-Runbook](linux/04-monitoring-runbook.md) | Triage und Übergabe |
| [Fiktive Monitoring-Ereignisse](linux/sample-monitoring-events.csv) | Synthetische CSV-Datei |

## 4. Lokale CSV-Auswertung und Tests

**Voraussetzung:** Python 3.9+; ausschließlich Standardbibliothek.

```bash
python3 scripts/analyze_alarms.py
python3 -m unittest discover -s tests -v
```

Unter Windows kann stattdessen `py -3` verwendet werden.

- [Skript](scripts/analyze_alarms.py)
- [Unit-Test](tests/test_alarm_analysis.py)
- [Ausführungsanleitung](scripts/README.md)
- [GitHub-Actions-Workflow](.github/workflows/check-alarm-data.yml)

**Erwartete Beispielstatistik:** 6 CSV-Ereigniseinträge, 3 eindeutige Incidents und 3 fiktive Standorte; Severity: Critical 2, Major 2, Minor 2. Diese Werte sind **kein Live-Monitoring**.

## Beruflicher Hintergrund

Drei Jahre NOC-Erfahrung im Telekommunikationsbereich bei Ucell: Nokia BTS, Nokia NMS/OSS und NetAct, GSM/UMTS/LTE, Alarmüberwachung, Fehleranalyse, BTS-Konfiguration, Ticketbearbeitung und Zusammenarbeit mit Field Engineers.

Die **beruflichen Tätigkeiten** sind von den hier selbst erstellten **fiktiven Lern- und Dokumentationsfällen** ausdrücklich getrennt.

## Portfolio-Status und Bewerbungen

- **Vorhanden:** Dokumentierte Lab-Szenarien, Checklisten, Diagramme in Textform, lokale CSV-Auswertung und automatisierter Test-Workflow.
- **Nicht vorhanden:** Live-Zabbix, echte SNMP-Abfragen, betriebliche Netzwerkmessungen oder real konfigurierte Testgeräte.
- [Kurzbeschreibung für Bewerbungen und ehrliche Projektabgrenzung](docs/bewerbung-und-projektstatus.md)

**Nächster praktischer Ausbau:** Eigene autorisierte Linux-VM mit echter Zabbix-Testinstanz aufsetzen und nachvollziehbare **selbst gemessene** Ergebnisse ergänzen.
