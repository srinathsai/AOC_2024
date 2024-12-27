import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    String path = "C:\\Users\\Srinath Sai\\Downloads\\Day18_challenge\\.idea\\input18.txt";
    char[][] grid ;
    List<int[]>corrupted_coordinates = new ArrayList<>();
    public Input(){
        grid = new char[71][71];
        for(int i=0;i<grid.length;i++){
            Arrays.fill(grid[i],'.');
        }
    }
    public void getInput() throws IOException{

        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] numbers = line.split(",");
            int[] pair = new int[] {Integer.parseInt(numbers[0]),Integer.parseInt(numbers[1])};
            corrupted_coordinates.add(pair);
        }
        List<int[]> required_corrupted = new ArrayList<>();
        for(int i=0;i<1024;i++){
            required_corrupted.add(corrupted_coordinates.get(i));
        }

        makeGrid(required_corrupted);
        //System.out.println(required_corrupted.size());
        //printGrid();

    }

    private void makeGrid(List<int[]> required_corrupted){
        required_corrupted.forEach(a->this.grid[a[0]][a[1]]='#');

    }

    private void printGrid(){
        for(int i=0;i<this.grid.length;i++){
            System.out.println(Arrays.toString(this.grid[i]));
        }


    }
}

class Solution{
    char[][] grid = new char[71][71];
    //List<int[]> next;
    List<int[]> corrupted;
    public Solution() throws IOException{
        Input I = new Input();
        I.getInput();
        this.grid = I.grid;
        //this.next = I.next_corrupted;
        this.corrupted =new ArrayList<>(I.corrupted_coordinates);
    }
    int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};
    //List<int[]> path = new ArrayList<>();
    public Long getMinmoves(){
        Long moves =0L;
        Queue<int[]> q= new LinkedList<>();
        boolean[][] visited = new boolean[this.grid.length][this.grid[0].length];
        visited[0][0] = true;
        q.add(new int[]{0,0});
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                int[] parent = q.poll();
                if(parent[0]==this.grid.length-1 && parent[1]==this.grid[0].length-1){
                    return moves;
                }

                for(int[] dir : directions){
                    int r = parent[0] + dir[0];
                    int c = parent[1] + dir[1];
                    if(r>=0 && r<this.grid.length && c>=0 && c<this.grid[0].length){
                        if(visited[r][c]==false && this.grid[r][c]=='.'){
                            visited[r][c] = true;
                            q.add(new int[]{r,c});
                            //path.add(new int[]{r,c});
                        }
                    }
                }
            }
            moves++;

        }
        return moves;
    }


    public int[] getFirstBlock(){
        for(int i=1024;i<corrupted.size();i++){
            int[] point = corrupted.get(i);
            this.grid[point[0]][point[1]] ='#';
            if(canReachEnd()==false){
                return point;
            }
            //this.grid[point[0]][point[1]] = '.';
        }
        return null;
    }
    private boolean canReachEnd(){
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[this.grid.length][this.grid[0].length];
        q.add(new int[] {0,0});
        visited[0][0] = true;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                int[] parent = q.poll();
                if(parent[0]==grid.length-1 && parent[1]==grid[0].length-1){
                    return true;
                }

                for(int[] dir : directions){
                    int r = dir[0] + parent[0];
                    int c= parent[1] + dir[1];
                    if(r>=0 && c>=0 && r< grid.length && c<grid[0].length){
                        if(visited[r][c] == false && grid[r][c]=='.'){
                            visited[r][c] = true;
                            q.add(new int[]{r,c});
                        }
                    }
                }
            }
        }
        return false;
    }
}









public class Main {
    public static void main(String[] args) throws IOException{
        Solution S = new Solution();
        System.out.println("the minimum moves is :-" + " " + S.getMinmoves());
        S.getMinmoves();
        System.out.println("The first blocking will be :-" + " " + Arrays.toString(S.getFirstBlock()));
    }
}
