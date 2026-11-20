import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Auswertung ausschließlich synthetischer NOC-Alarm-Ereignisse aus einer lokalen CSV-Datei.
 * Kein Netzwerkzugriff, keine produktiven Monitoring-Daten.
 *
 * Der CSV-Leser ist bewusst auf die einfache, synthetische Beispieldatei ohne
 * Anführungszeichen oder eingebettete Kommas beschränkt, kein allgemeiner CSV-Parser.
 */
public final class AlarmAnalysis {
    private AlarmAnalysis() {}

    public record Result(
            int events,
            int incidents,
            int sites,
            Map<String, Integer> severity,
            Map<String, Integer> status,
            Map<String, Integer> technology) {}

    public static Result analyze(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("CSV-Datei ist leer");
        }

        String header = lines.get(0).replace("\uFEFF", "");
        String[] columns = header.split(",", -1);
        Map<String, Integer> indices = new HashMap<>();
        for (int i = 0; i < columns.length; i++) {
            indices.put(columns[i].trim(), i);
        }

        String[] required = {
            "alarm_id", "site_id", "technology", "severity", "status", "incident_id"
        };
        for (String field : required) {
            if (!indices.containsKey(field)) {
                throw new IllegalArgumentException("Fehlende CSV-Spalte: " + field);
            }
        }

        int events = 0;
        Set<String> incidents = new HashSet<>();
        Set<String> sites = new HashSet<>();
        Map<String, Integer> severities = new TreeMap<>();
        Map<String, Integer> statuses = new TreeMap<>();
        Map<String, Integer> technologies = new TreeMap<>();

        for (int line = 1; line < lines.size(); line++) {
            if (lines.get(line).isBlank()) {
                continue;
            }
            String[] values = lines.get(line).split(",", -1);
            if (values.length != columns.length) {
                throw new IllegalArgumentException("Ungültige CSV-Zeile: " + (line + 1));
            }

            String incident = field(values, indices, "incident_id");
            String site = field(values, indices, "site_id");
            if (incident.isBlank() || site.isBlank()) {
                throw new IllegalArgumentException("Incident-ID oder Standort fehlt in Zeile " + (line + 1));
            }

            events++;
            incidents.add(incident);
            sites.add(site);
            severities.merge(field(values, indices, "severity"), 1, Integer::sum);
            statuses.merge(field(values, indices, "status"), 1, Integer::sum);
            technologies.merge(field(values, indices, "technology"), 1, Integer::sum);
        }

        return new Result(events, incidents.size(), sites.size(),
                Map.copyOf(severities), Map.copyOf(statuses), Map.copyOf(technologies));
    }

    private static String field(String[] values, Map<String, Integer> indices, String name) {
        return values[indices.get(name)].trim();
    }

    public static void main(String[] args) throws IOException {
        Path path = args.length > 0
                ? Path.of(args[0])
                : Path.of("alarms", "sample-alarms.csv");
        Result result = analyze(path);
        System.out.println("Simulierte NOC-Alarmstatistik — keine Live-Daten");
        System.out.println("Ereigniseinträge: " + result.events());
        System.out.println("Eindeutige Incidents: " + result.incidents());
        System.out.println("Fiktive Standorte: " + result.sites());
        show("Severity", result.severity());
        show("Status", result.status());
        show("Technologie", result.technology());
    }

    private static void show(String label, Map<String, Integer> items) {
        System.out.println(label + ":");
        new TreeMap<>(items).forEach((key, count) -> System.out.println("  " + key + ": " + count));
    }
}
