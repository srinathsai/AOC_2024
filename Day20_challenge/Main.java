import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static class GridData {
        Map<Point, Character> grid;
        Point start;
        Point goal;

        public GridData(Map<Point, Character> grid, Point start, Point goal) {
            this.grid = grid;
            this.start = start;
            this.goal = goal;
        }
    }

    public static class Point {
        int x, y;

        public Point(int x, int y) {
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

    public static GridData parseInput(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line.trim());
        }
        reader.close();

        Map<Point, Character> grid = new HashMap<>();
        Point start = null, goal = null;
        int xLim = lines.get(0).length();
        int yLim = lines.size();

        for (int y = 0; y < yLim; y++) {
            for (int x = 0; x < xLim; x++) {
                char value = lines.get(y).charAt(x);
                Point point = new Point(x, y);
                grid.put(point, value);
                if (value == 'S') start = point;
                if (value == 'E') goal = point;
            }
        }

        return new GridData(grid, start, goal);
    }

    public static List<Point> bfs(Map<Point, Character> grid, Point start, Point goal) {
        Queue<List<Point>> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>();

        visited.add(start);
        queue.add(Collections.singletonList(start));

        while (!queue.isEmpty()) {
            List<Point> path = queue.poll();
            Point node = path.get(path.size() - 1);

            if (node.equals(goal)) {
                return path;
            }

            for (Point neighbor : getCircle(node, 1)) {
                if (!visited.contains(neighbor) && grid.getOrDefault(neighbor, '#') != '#') {
                    visited.add(neighbor);
                    List<Point> newPath = new ArrayList<>(path);
                    newPath.add(neighbor);
                    queue.add(newPath);
                }
            }
        }

        return new ArrayList<>();
    }

    public static List<Point> getCircle(Point pos, int radius) {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i <= radius; i++) {
            int x = i, y = radius - i;
            points.add(new Point(pos.x + x, pos.y + y));
            points.add(new Point(pos.x - x, pos.y - y));
            points.add(new Point(pos.x + x, pos.y - y));
            points.add(new Point(pos.x - x, pos.y + y));
        }
        return points;
    }

    public static void p1(Map<Point, Character> grid, Point start, Point goal) {
        List<Point> pathList = bfs(grid, start, goal);
        Map<Point, Integer> path = new HashMap<>();
        for (int i = 0; i < pathList.size(); i++) {
            path.put(pathList.get(i), i);
        }

        int count = 0;
        int lim = pathList.size() == 85 ? 2 : 100;
        for (int i = 0; i < pathList.size(); i++) {
            Point pos = pathList.get(i);
            for (Point neighbor : getCircle(pos, 2)) {
                if (path.containsKey(neighbor) && path.get(neighbor) - i - 2 >= lim) {
                    count++;
                }
            }
        }

        System.out.println(count);
    }

    public static void p2(List<Point> pathList, Map<Point, Integer> path) {
        int count = 0;
        int lim = pathList.size() == 85 ? 50 : 100;
        for (int i = 0; i < pathList.size(); i++) {
            Point pos = pathList.get(i);
            for (int steps = 2; steps <= 20; steps++) {
                for (Point neighbor : getCircle(pos, steps)) {
                    if (path.containsKey(neighbor) && path.get(neighbor) - i - steps >= lim) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);
    }

    public static void main(String[] args) {
        try {
            GridData gridData = parseInput("C:\\Users\\Srinath Sai\\Downloads\\Day20_challenge\\.idea\\Input20.txt");
            List<Point> pathList = bfs(gridData.grid, gridData.start, gridData.goal);
            Map<Point, Integer> path = new HashMap<>();
            for (int i = 0; i < pathList.size(); i++) {
                path.put(pathList.get(i), i);
            }
            p1(gridData.grid, gridData.start, gridData.goal);
            p2(pathList, path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
