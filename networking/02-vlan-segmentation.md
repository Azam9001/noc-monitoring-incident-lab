# Lab 02 — VLAN-Segmentierung im simulierten NOC

> **Reines Lernlabor:** Sämtliche Geräte, Netze, Ports, VLANs und Kommunikationsanforderungen sind frei erfunden. Keine produktiven Switches, keine Ucell-Netzdaten und keine echten Betreiberkonfigurationen. Die Darstellung ist herstellerneutral; es werden keine Änderungen an realen Geräten vorgenommen.

## Lernziele

Nach dieser Übung kannst du:

- erklären, wozu ein **VLAN (Virtual Local Area Network)** dient,
- **Access-Port** und **Trunk-Port** unterscheiden,
- zwei IPv4-Subnetze unterschiedlichen VLANs zuordnen,
- begründen, warum unterschiedliche VLANs ohne Layer-3-Routing nicht direkt miteinander kommunizieren.

## Ausgangslage

Im fiktiven NOC werden Monitoring-Geräte und Verwaltungsgeräte logisch getrennt. Ein Switch verbindet beide Bereiche.

```text
  [Monitoring-PC]                    [Admin-PC]
       VLAN 10                          VLAN 20
     Access-Port                      Access-Port
          |                                |
          +-----------[Switch]-------------+
                         |
                 Trunk (VLAN 10,20)
                         |
                [Weiterer Switch]
```

**Wichtig:** Ein Trunk transportiert hier mehrere VLANs. Er stellt **kein Routing** zwischen VLANs bereit.

## Adressplan

| VLAN | Bezeichnung | Fiktives Subnetz | Nutzbare Hosts | Beispielgerät |
|---|---|---|---:|---|
| 10 | NOC-MONITORING | `10.50.10.0/27` | 30 | Monitoring-PC: `10.50.10.10` |
| 20 | NOC-ADMIN | `10.50.10.32/27` | 30 | Admin-PC: `10.50.10.40` |

**Verbindung zu [Lab 01](01-ipv4-subnetting.md):** Beide /27-Netze wurden aus dem privaten Übungsbereich `10.50.10.0/24` gebildet.

## Aufgabe A — Netzwerkverständnis

Beantworte diese Fragen, bevor du die Lösung liest:

1. Welchem VLAN gehört der Monitoring-PC an?
2. In welchem Subnetz liegt die Adresse `10.50.10.40`?
3. Welche Art von Port nutzt ein normaler Monitoring-PC?
4. Kann der Monitoring-PC allein durch einen VLAN-Trunk mit dem Admin-PC in einem anderen VLAN kommunizieren?
5. Welche zusätzliche Netzwerkfunktion ist für Kommunikation zwischen VLAN 10 und VLAN 20 notwendig?

<details>
<summary>Lösungen zu Aufgabe A anzeigen</summary>

1. VLAN 10 (NOC-MONITORING).
2. `10.50.10.32/27`, also VLAN 20.
3. Ein Access-Port in VLAN 10.
4. **Nein.** Ein Trunk transportiert VLANs zwischen Switches, verbindet aber nicht deren IP-Netze miteinander.
5. **Inter-VLAN-Routing**, beispielsweise durch einen Layer-3-Switch oder Router, mit passenden Gateway-Adressen und erlaubter Kommunikation.

</details>

## Aufgabe B — Subnetz- und VLAN-Zuordnung

Ordne die folgenden Adressen zu. Kennzeichne auch Netzwerk- und Broadcast-Adressen als **keine normalen Hostadressen**.

| Testadresse | Deine Zuordnung |
|---|---|
| `10.50.10.1` | ? |
| `10.50.10.31` | ? |
| `10.50.10.33` | ? |
| `10.50.10.62` | ? |
| `10.50.10.63` | ? |

<details>
<summary>Lösungen zu Aufgabe B anzeigen</summary>

| Adresse | Zuordnung | Erklärung |
|---|---|---|
| `10.50.10.1` | VLAN 10 | Nutzbarer Host |
| `10.50.10.31` | VLAN 10 | Broadcast, nicht als Host verwendbar |
| `10.50.10.33` | VLAN 20 | Nutzbarer Host |
| `10.50.10.62` | VLAN 20 | Nutzbarer Host |
| `10.50.10.63` | VLAN 20 | Broadcast, nicht als Host verwendbar |

Die Netzwerkadressen der beiden Subnetze lauten `10.50.10.0` und `10.50.10.32`; diese können ebenfalls nicht als gewöhnliche Hostadressen verwendet werden.

</details>

## Beispiel: Port-Dokumentation (kein Gerätekonfigurationsbefehl)

| Port (fiktiv) | Typ | Zugeordnete VLANs | Zweck |
|---|---|---|---|
| SW1-Port1 | Access | 10 | Monitoring-PC |
| SW1-Port2 | Access | 20 | Admin-PC |
| SW1-Port24 | Trunk | 10, 20 | Verbindung zu SW2 |

**Hinweis:** Reale Switch-Befehle unterscheiden sich nach Hersteller und Modell. Diese Tabelle dokumentiert nur die logische Planung, nicht die Konfiguration.

## Bezug zu Network Operations

Bei einem Netzwerkalarm können VLAN-Zuordnung, Portmodus und IP-Subnetz Hinweise auf mögliche Erreichbarkeitsprobleme geben. Ein NOC-Team dokumentiert die betroffenen Komponenten, prüft die Zuständigkeit und eskaliert bei Bedarf an Network Engineering. Aus dieser Simulation darf keine praktische Erfahrung mit produktiven Switch-Konfigurationen abgeleitet werden.

## Erfolgskriterien

- [ ] Ich kann den Unterschied zwischen Access und Trunk erklären.
- [ ] Ich kann beide VLANs den richtigen /27-Subnetzen zuordnen.
- [ ] Ich erkenne Broadcast-Adressen als nicht nutzbare Hostadressen.
- [ ] Ich weiß, dass für die Kommunikation zwischen den VLANs Inter-VLAN-Routing erforderlich ist.

## Nächster Schritt

**Lab 03 — Inter-VLAN-Routing:** Gateway-Adressen und erlaubte Kommunikation zwischen VLAN 10 und VLAN 20 entwerfen.

---

*Dieses Dokument ist ausschließlich ein fiktives, herstellerneutrales Lernbeispiel.*
