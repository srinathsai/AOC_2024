import java.io.*;
import java.util.*;

public class ResonantHarmonics {

    // A class to represent coordinates of antennas
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) throws IOException {
        // Read the input file
        String fileName = "C:\\Users\\Srinath Sai\\Downloads\\Day8_challenge\\.idea\\input8.txt"; // Replace with your file path if running locally
        List<String> map = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                map.add(line);
            }
        }

        // Parse antenna locations
        Map<Character, List<Point>> antennas = new HashMap<>();
        for (int y = 0; y < map.size(); y++) {
            String row = map.get(y);
            for (int x = 0; x < row.length(); x++) {
                char ch = row.charAt(x);
                if (Character.isLetterOrDigit(ch)) {
                    antennas.computeIfAbsent(ch, k -> new ArrayList<>()).add(new Point(x, y));
                }
            }
        }

        // Calculate antinodes
        Set<Point> antinodes = new HashSet<>();
        for (Map.Entry<Character, List<Point>> entry : antennas.entrySet()) {
            List<Point> points = entry.getValue();
            int size = points.size();

            if (size < 2) continue; // No antinodes possible with fewer than 2 antennas of the same frequency

            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);

                    // Add both antennas as antinodes
                    antinodes.add(p1);
                    antinodes.add(p2);

                    // Determine all points collinear with p1 and p2
                    int dx = p2.x - p1.x;
                    int dy = p2.y - p1.y;

                    // Use greatest common divisor to normalize direction
                    int gcd = gcd(Math.abs(dx), Math.abs(dy));
                    dx /= gcd;
                    dy /= gcd;

                    // Generate collinear points in both directions
                    Point current = new Point(p1.x - dx, p1.y - dy);
                    while (isValid(current, map)) {
                        antinodes.add(current);
                        current = new Point(current.x - dx, current.y - dy);
                    }

                    current = new Point(p2.x + dx, p2.y + dy);
                    while (isValid(current, map)) {
                        antinodes.add(current);
                        current = new Point(current.x + dx, current.y + dy);
                    }
                }
            }
        }

        // Output the result
        System.out.println("Total unique antinodes: " + antinodes.size());
    }

    // Check if the point is within bounds of the map
    private static boolean isValid(Point p, List<String> map) {
        return p.x >= 0 && p.x < map.get(0).length() && p.y >= 0 && p.y < map.size();
    }

    // Calculate greatest common divisor
    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
