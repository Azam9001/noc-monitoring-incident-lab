"""Analyse synthetischer NOC-Alarmdaten; kein Zugriff auf reale Systeme."""
from collections import Counter
import csv
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
DATA = ROOT / "alarms" / "sample-alarms.csv"
REQUIRED = {"alarm_id", "site_id", "technology", "severity", "status", "incident_id"}


def analyze(path=DATA):
    """CSV-Einträge zählen; jede Zeile ist ein Ereignis, kein unabhängiger Incident."""
    with Path(path).open("r", encoding="utf-8-sig", newline="") as handle:
        reader = csv.DictReader(handle)
        if not REQUIRED.issubset(set(reader.fieldnames or [])):
            missing = sorted(REQUIRED - set(reader.fieldnames or []))
            raise ValueError(f"Fehlende CSV-Spalten: {', '.join(missing)}")
        rows = list(reader)
    if any(not row["incident_id"].strip() for row in rows):
        raise ValueError("Jeder Eintrag benötigt eine Incident-ID.")
    return {
        "events": len(rows),
        "incidents": len({row["incident_id"] for row in rows}),
        "sites": len({row["site_id"] for row in rows}),
        "severity": dict(sorted(Counter(row["severity"] for row in rows).items())),
        "status": dict(sorted(Counter(row["status"] for row in rows).items())),
        "technology": dict(sorted(Counter(row["technology"] for row in rows).items())),
    }


def main():
    report = analyze()
    print("Simulierte NOC-Alarmstatistik — keine Live-Daten")
    print(f"Ereigniseinträge: {report['events']}")
    print(f"Eindeutige Incidents: {report['incidents']}")
    print(f"Fiktive Standorte: {report['sites']}")
    for heading in ("severity", "status", "technology"):
        print(f"{heading}:")
        for key, count in report[heading].items():
            print(f"  {key}: {count}")


if __name__ == "__main__":
    main()
