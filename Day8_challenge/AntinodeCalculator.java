import java.io.*;
import java.util.*;

public class AntinodeCalculator {


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

        String fileName = "C:\\Users\\Srinath Sai\\Downloads\\Day8_challenge\\.idea\\input8.txt";
        List<String> map = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                map.add(line);
            }
        }


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


        Set<Point> antinodes = new HashSet<>();
        for (Map.Entry<Character, List<Point>> entry : antennas.entrySet()) {
            List<Point> points = entry.getValue();
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);


                    int dx = p2.x - p1.x;
                    int dy = p2.y - p1.y;


                    Point mid1 = new Point(p1.x - dx, p1.y - dy);
                    Point mid2 = new Point(p2.x + dx, p2.y + dy);

                    if (isValid(mid1, map)) antinodes.add(mid1);
                    if (isValid(mid2, map)) antinodes.add(mid2);
                }
            }
        }


        System.out.println("Total unique antinodes: " + antinodes.size());
    }

    private static boolean isValid(Point p, List<String> map) {
        return p.x >= 0 && p.x < map.get(0).length() && p.y >= 0 && p.y < map.size();
    }
}
