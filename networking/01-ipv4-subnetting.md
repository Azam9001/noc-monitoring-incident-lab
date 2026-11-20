# Lab 01 — IPv4-Subnetting für ein simuliertes NOC

> **Ausschließlich Lernsimulation:** Alle Netzsegmente, Geräte und Anforderungen sind erfunden. Es werden keine echten IP-Adressen, Netzpläne oder Konfigurationen eines Arbeitgebers verwendet.

## Lernziel

Ein IPv4-Subnetz berechnen, um in einem fiktiven NOC die Adressierung eines Monitoring-Netzes nachvollziehbar zu dokumentieren.

**Vorkenntnisse:** Grundbegriffe IPv4, Netzwerkmaske und Hostadresse.

## Szenario

Ein fiktives Monitoring-Segment benötigt **mindestens 20 nutzbare IPv4-Hostadressen**. Der verfügbare private Adressbereich lautet `10.50.10.0/24`.

**Aufgabe:** Wähle ein möglichst kleines passendes Subnetz und bestimme Netzadresse, Subnetzmaske, erste und letzte nutzbare Hostadresse sowie Broadcast-Adresse.

## Aufgabe A — Selbst lösen

Vor dem Öffnen der Lösung:

1. Wie viele Host-Bits müssen für mindestens 20 nutzbare Hosts vorhanden sein?
2. Welches ist das kleinste passende CIDR-Präfix?
3. Wie lautet die Subnetzmaske?
4. Wie viele nutzbare IPv4-Hostadressen bietet das Subnetz?
5. Wie lauten Netzwerk-, erste Host-, letzte Host- und Broadcast-Adresse, wenn das Subnetz bei `10.50.10.0` beginnt?

## Lösung

| Größe | Ergebnis |
|---|---|
| Benötigte nutzbare Hosts | 20 |
| Host-Bits | 5 |
| Präfix | **/27** |
| Subnetzmaske | **255.255.255.224** |
| Gesamtadressen | **32** |
| Nutzbare Hostadressen (klassisches Subnetz) | **30** |
| Netzwerkadresse | **10.50.10.0** |
| Erste Hostadresse | **10.50.10.1** |
| Letzte Hostadresse | **10.50.10.30** |
| Broadcast-Adresse | **10.50.10.31** |
| Nächstes /27-Subnetz | **10.50.10.32/27** |

### Rechenweg

Bei einem /27-Präfix bleiben `32 - 27 = 5` Host-Bits.

- Gesamtadressen: `2^5 = 32`
- Nutzbare Adressen bei klassischer IPv4-Subnetzierung: `32 - 2 = 30`
- Blockgröße im letzten Oktett: `256 - 224 = 32`

Ein /28-Subnetz hätte nur `2^4 - 2 = 14` nutzbare Hosts und wäre somit zu klein.

## Aufgabe B — Zweites Subnetz prüfen

Berechne das **nächste /27-Subnetz** im Bereich `10.50.10.0/24`.

<details>
<summary>Lösung anzeigen</summary>

- Netzwerkadresse: `10.50.10.32/27`
- Subnetzmaske: `255.255.255.224`
- Erste Hostadresse: `10.50.10.33`
- Letzte Hostadresse: `10.50.10.62`
- Broadcast: `10.50.10.63`
- Nutzbare Hosts: `30`

</details>

## Optional: Lokal mit Python 3 kontrollieren

Dieser Befehl funktioniert ohne zusätzliche Bibliotheken; er benötigt **keinen** Zugriff auf reale Netzwerkgeräte:

```bash
python3 -c "import ipaddress; n=ipaddress.ip_network('10.50.10.0/27'); print('Netz:', n.network_address); print('Maske:', n.netmask); print('Erster Host:', n.network_address+1); print('Letzter Host:', n.broadcast_address-1); print('Broadcast:', n.broadcast_address); print('Nutzbare Hosts:', n.num_addresses-2)"
```

Erwartet werden die Werte aus der Lösungstabelle.

## Bezug zur NOC-Tätigkeit

Bei Störungen im Netzwerkbetrieb hilft die sichere Interpretation von IP-Adressen und Subnetzmasken, einen betroffenen Adressbereich richtig einzuordnen. Dieses Lab demonstriert ausschließlich das **Rechnen und Dokumentieren** — keine praktische Konfiguration von Routern oder Switches.

## Erfolgskriterien

- [ ] /27 und die Subnetzmaske selbstständig erklären können
- [ ] 30 nutzbare Hostadressen korrekt berechnen
- [ ] Den Bereich `10.50.10.0` bis `10.50.10.31` abgrenzen
- [ ] Das nächste Subnetz `10.50.10.32/27` korrekt bestimmen

**Nächster kleiner Schritt:** Separates Repository `network-infrastructure-lab` anlegen und dieses Lab anschließend dorthin übernehmen; danach VLAN-Grundlagen ergänzen.
