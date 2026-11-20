import java.nio.file.Path;
import java.util.Map;

/**
 * Einfache selbstständig ausführbare Prüfungen für die synthetische CSV-Datei.
 * Kein Test-Framework und keine externen Abhängigkeiten nötig.
 */
public final class AlarmAnalysisTest {
    private AlarmAnalysisTest() {}

    public static void main(String[] args) throws Exception {
        AlarmAnalysis.Result r = AlarmAnalysis.analyze(
                Path.of("alarms", "sample-alarms.csv"));

        check(r.events() == 6, "6 Ereignisse erwartet");
        check(r.incidents() == 3, "3 Incidents erwartet");
        check(r.sites() == 3, "3 Standorte erwartet");
        check(r.severity().equals(Map.of("Critical", 2, "Major", 2, "Minor", 2)),
                "Severity-Verteilung stimmt nicht");
        check(r.status().equals(Map.of("Cleared", 5, "Monitoring", 1)),
                "Status-Verteilung stimmt nicht");
        check(r.technology().equals(Map.of(
                        "2G/GSM", 2, "3G/UMTS", 2, "4G/LTE", 2)),
                "Technologie-Verteilung stimmt nicht");

        System.out.println("Alle Java-Tests bestanden — ausschließlich synthetische Lab-Daten.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
