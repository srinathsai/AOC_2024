import java.io.*;
import java.util.*;

public class Solution2 {
    static final int SZ = 130;
    static final Map<Character, int[]> DIRECTION_MAP = Map.of(
            '^', new int[]{0, -1},
            '>', new int[]{1, 0},
            'v', new int[]{0, 1},
            '<', new int[]{-1, 0}
    );
    static final char[] DIRECTIONS = {'^', '>', 'v', '<'};

    static Map<Point, Character> field = new HashMap<>();
    static Set<Point> visited = new HashSet<>();

    public static void main(String[] args) throws IOException {
        List<String> input = readInput("C:\\Users\\Srinath Sai\\Downloads\\Day6_challenge\\.idea\\input6.txt");

        System.out.println("Part 1: " + star1(input));
        System.out.println("Part 2: " + star2(input));
    }

    static List<String> readInput(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    static Point prep(List<String> input) {
        field.clear();
        Point guardStart = null;

        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                Point p = new Point(j, i);
                field.put(p, c == '#' ? '#' : '.');
                if (c == '^') {
                    guardStart = p;
                }
            }
        }

        return guardStart;
    }

    static int star1(List<String> input) {
        visited.clear();
        Point guard = prep(input);
        int guardDir = 0;

        visited.add(guard);
        while (true) {
            int[] dt = DIRECTION_MAP.get(DIRECTIONS[guardDir]);
            Point next = new Point(guard.x + dt[0], guard.y + dt[1]);

            if (next.x < 0 || next.x >= SZ || next.y < 0 || next.y >= SZ) break;

            if (field.getOrDefault(next, '#') == '#') {
                guardDir = (guardDir + 1) % 4;
            } else {
                guard = next;
            }

            visited.add(guard);
        }

        return visited.size();
    }

    static int star2(List<String> input) {
        Point guardStart = prep(input);
        int total = 0;

        for (Point p : visited) {
            if (field.get(p) == '#' || p.equals(guardStart)) continue;

            Map<Point, Character> tempField = new HashMap<>(field);
            tempField.put(p, '#');

            boolean trapped = false;
            Point guard = guardStart;
            int guardDir = 0;
            Set<Point> localVisited = new HashSet<>(Collections.singleton(guard));

            Map<Point, Set<Point>> assoc = new HashMap<>();
            Set<Point> watch = null;

            while (true) {
                int[] dt = DIRECTION_MAP.get(DIRECTIONS[guardDir]);
                Point next = new Point(guard.x + dt[0], guard.y + dt[1]);

                if (next.x < 0 || next.x >= SZ || next.y < 0 || next.y >= SZ) break;

                if (tempField.getOrDefault(next, '#') == '#') {
                    guardDir = (guardDir + 1) % 4;
                } else {
                    assoc.computeIfAbsent(guard, k -> new HashSet<>()).add(next);
                    guard = next;
                }

                if (watch != null && watch.contains(guard)) {
                    trapped = true;
                    break;
                } else {
                    watch = null;
                }

                if (assoc.containsKey(guard)) {
                    watch = new HashSet<>(assoc.get(guard));
                }

                localVisited.add(guard);
            }

            if (trapped) total++;
        }

        return total;
    }

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
}
