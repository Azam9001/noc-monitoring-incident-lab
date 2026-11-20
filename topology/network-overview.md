# Fiktive Mobilfunk-Topologie

> **Simuliertes Lab:** Alle Standorte und Komponenten sind frei erfunden.

```text
                [NOC Monitoring / OSS]
                          |
                 [Transportnetz]
                   /    |    \
         [SITE-A01] [SITE-B02] [SITE-C03]
           4G/LTE     3G/UMTS    2G/GSM
```

| Standort | Technologie | Lab-Netzelement | Beispielstörung |
|---|---|---|---|
| SITE-A01 | LTE | A01-L1 | Cell Unavailable |
| SITE-B02 | UMTS | B02-U1 | Transport Link Down |
| SITE-C03 | GSM | C03-G1 | TRX Performance Degraded |

Diese vereinfachte Lernskizze ist keine vollständige Netzarchitektur. Keine realen IP-Adressen, OSS-Exporte, Kundendaten oder Betreiberkonfigurationen.
