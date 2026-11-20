"""Lokale Tests für synthetische CSV-Daten (keine Netzwerkkommunikation)."""
import sys
import unittest
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "scripts"))
from analyze_alarms import analyze  # noqa: E402


class AlarmAnalysisTest(unittest.TestCase):
    def test_sample_counts(self):
        result = analyze()
        self.assertEqual(result["events"], 6)
        self.assertEqual(result["incidents"], 3)
        self.assertEqual(result["sites"], 3)
        self.assertEqual(result["severity"], {"Critical": 2, "Major": 2, "Minor": 2})
        self.assertEqual(result["status"], {"Cleared": 5, "Monitoring": 1})
        self.assertEqual(result["technology"], {"2G/GSM": 2, "3G/UMTS": 2, "4G/LTE": 2})


if __name__ == "__main__":
    unittest.main()
