import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input {
    public char[][] getInput() throws IOException {
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day6_challenge\\.idea\\input6.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        int index = 0;
        char[][] grid = new char[130][130];
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            //System.out.println(line.length());
            for(int i=0;i<line.length();i++){
                grid[index][i] = line.charAt(i);
            }
            index++;


        }

        return grid;
    }


}

class Solution1{
    Map<Character,int[]>directionMap;
    Map<Character,Character>rotationMap;

    //Long count;
    Set<String> distinctSquares;
    public Solution1(){
        this.directionMap = new HashMap<>();
        this.rotationMap = new HashMap<>();
        //this.direction = 'U';
        directionMap.put('U', new int[]{-1,0});
        directionMap.put('R', new int[]{0,1});
        directionMap.put('D', new int[]{1,0});
        directionMap.put('L',new int[] {0,-1});
        rotationMap.put('U','R');
        rotationMap.put('R','D');
        rotationMap.put('D','L');
        rotationMap.put('L','U');
        //count = 0L;
        /*for(char ch : directionMap.keySet()){
            System.out.println(ch + " " + ":-" + Arrays.toString(directionMap.get(ch)));
        }*/
        //System.out.println(rotationMap.toString());
        distinctSquares = new HashSet<>();
    }
    public int getMoves(char[][] grid, int[] start){
         distinctSquares.add(Arrays.toString(start));
         dfs(grid,start,directionMap.get('U'),'U');
         return distinctSquares.size();
    }
    private void dfs(char[][] grid, int[] startCoordinate, int[] startProgression, Character startDirection) {
        Stack<State> stack = new Stack<>();
        stack.push(new State(startCoordinate, startProgression, startDirection));

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            int[] coordinate = currentState.coordinate;
            int[] progression = currentState.progression;
            Character direction = currentState.direction;


            if (coordinate[0] < 0 || coordinate[1] < 0 || coordinate[0] >= grid.length || coordinate[1] >= grid[0].length) {
                continue;
            }

            if (grid[coordinate[0]][coordinate[1]] == '#') {
                coordinate[0] -= progression[0];
                coordinate[1] -= progression[1];
                direction = rotationMap.get(direction);

                int[] newProgression = directionMap.get(direction);
                int[] newCoordinate = {coordinate[0] + newProgression[0], coordinate[1] + newProgression[1]};
                String square = Arrays.toString(newCoordinate);

                if (!distinctSquares.contains(square)) {
                    distinctSquares.add(square);
                    //count++;
                }

                stack.push(new State(newCoordinate, newProgression, direction));
            } else {
                int[] newCoordinate = {coordinate[0] + progression[0], coordinate[1] + progression[1]};
                String square = Arrays.toString(newCoordinate);

                if (newCoordinate[0] >= 0 && newCoordinate[0] < grid.length &&
                        newCoordinate[1] >= 0 && newCoordinate[1] < grid[0].length) {
                    if (!distinctSquares.contains(square) && grid[newCoordinate[0]][newCoordinate[1]] != '#') {
                        distinctSquares.add(square);
                        //count++;
                    }
                }

                stack.push(new State(newCoordinate, progression, direction));
            }
        }
    }


    static class State {
        int[] coordinate;
        int[] progression;
        Character direction;

        State(int[] coordinate, int[] progression, Character direction) {
            this.coordinate = coordinate.clone();
            this.progression = progression.clone();
            this.direction = direction;
        }
    }

}

public class Main {
     public static void main(String[] args) throws IOException{
         Input I = new Input();
         char[][] grid = I.getInput();
         int[] start = getStart(grid);
         Solution1 s1 = new Solution1();
         System.out.println("The count of unique squares travelled is :-" + " " + s1.getMoves(grid,start));

     }
     private static int[] getStart(char[][] grid){
         int[] start = new int[2];
         for(int i=0;i<grid.length;i++){
             for(int j=0;j<grid[0].length;j++){
                 if(grid[i][j]=='^'){
                     start[0] = i;
                     start[1] = j;
                     break;
                 }
             }
         }
         return start;
     }
}

