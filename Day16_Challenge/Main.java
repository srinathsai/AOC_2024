import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    String path = "C:\\Users\\Srinath Sai\\Downloads\\Day16_Challenge\\.idea\\input16.txt";
    public char[][] getInput() throws IOException {
        String dimensions  = getDimensionsofInput();
        String[] sizes = dimensions.split(",");


        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());

        char[][] input = new char[Integer.parseInt(sizes[0])][Integer.parseInt(sizes[1])];
        int index =0;
        while(sc.hasNextLine()){
            String row = sc.nextLine();
            for(int j=0;j<row.length();j++){
                input[index][j] = row.charAt(j);
            }
            index++;
        }
        return input;
    }
    private String getDimensionsofInput() throws IOException{

        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        int lineCount = 0;
        int columns =0;
        while(sc.hasNextLine()){
            columns = sc.nextLine().length();
            lineCount++;
        }
        return String.valueOf(lineCount) + ',' + String.valueOf(columns);
    }

    public void printGrid(char[][] grid){
        for(int i=0;i<grid.length;i++){
            System.out.println(Arrays.toString(grid[i]));
        }
    }
}


class Solution {
    private final char[][] grid;
    private final int height;
    private final int width;
    private final Coord start;
    private final Coord end;
    private final Set<Coord> bestPathPoints = new HashSet<>();
    private final Map<Raindeer, Integer> seen = new HashMap<>();
    private int minPrice = Integer.MAX_VALUE;
    public Solution() throws IOException{
        grid = new Input().getInput();
        height = grid.length;
        width = grid[0].length;
        Coord s = null;
        Coord e = null;
        for (int y = 0; y < height & (s == null || e == null); y++) {
            for (int x = 0; x < width & (s == null || e == null); x++) {
                if (grid[y][x] == 'S') {
                    s = new Coord(x, y);
                } else if (grid[y][x] == 'E') {
                    e = new Coord(x, y);
                }
            }
        }
        start = s;
        end = e;
        findPaths();
    }

    public Integer part1(){
        return minPrice;
    }

    public Integer part2(){
        return bestPathPoints.size();
    }




    private void findPaths() {
        Queue<State> priorityQueue = new PriorityQueue<>(State.PRICE_COMPARATOR);
        Map<Coord, Set<Coord>> bests = new HashMap<>();
        Raindeer startDeer = new Raindeer(start, Coord.EAST);
        seen.put(startDeer, 0);
        priorityQueue.add(new State(startDeer, 0, new HashSet<>(List.of(start))));
        boolean weAreOverBestPaths = false;
        while (!priorityQueue.isEmpty() && !weAreOverBestPaths) {
            State state = priorityQueue.poll();
            for (State next : state.next()) {
                if (isFree(next.raindeer.position) && next.price <= seen.getOrDefault(next.raindeer, Integer.MAX_VALUE)) {
                    if (next.price < seen.getOrDefault(next.raindeer, Integer.MAX_VALUE)) {
                        priorityQueue.add(next);
                        seen.put(next.raindeer, next.price);
                        if (end.equals(next.raindeer.position)) {
                            if (minPrice < next.price) {
                                weAreOverBestPaths = true;
                            }
                            minPrice = Math.min(minPrice, next.price);
                            bestPathPoints.addAll(next.visited);
                        }
                        bests.put(next.raindeer.position, next.visited);
                    } else {
                        bests.get(next.raindeer.position).addAll(next.visited);
                    }
                }
            }
        }
    }

    private boolean isFree(Coord position) {
        return position.x >= 0 && position.x < width && position.y >= 0 && position.y < height && grid[position.y][position.x] != '#';
    }


    private record Coord(int x, int y) {
        static final Coord EAST = new Coord(1, 0);

        Coord add(Coord other) {
            return new Coord(this.x + other.x, this.y + other.y);
        }
    }

    private record Raindeer(Coord position, Coord orientation) {
        Raindeer turnLeft() {
            return new Raindeer(position, new Coord(-orientation.y, orientation.x));
        }

        Raindeer turnRight() {
            return new Raindeer(position, new Coord(orientation.y, -orientation.x));
        }

        Raindeer step() {
            return new Raindeer(position.add(orientation), orientation);
        }
    }

    private record State(Raindeer raindeer, int price, Set<Coord> visited) {
        static Comparator<State> PRICE_COMPARATOR = Comparator.comparing(State::price);

        State turnLeft() {
            return new State(raindeer.turnLeft(), price + 1000, visited);
        }

        State turnRight() {
            return new State(raindeer.turnRight(), price + 1000, visited);
        }

        State step() {
            Raindeer step = raindeer.step();
            var v = new HashSet<>(visited);
            v.add(step.position);
            return new State(step, price + 1, v);
        }

        List<State> next() {
            return List.of(turnLeft(), turnRight(), step());
        }


    }
}


public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        char[][] grid = I.getInput();
        //I.printGrid(grid);
        Solution S = new Solution();
        System.out.println("The minPrice is :- + " + " " + S.part1());
        System.out.println("The best spots to seat are  :- + " + " " + S.part2());
    }
}
