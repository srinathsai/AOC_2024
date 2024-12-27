import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    String path = "C:\\Users\\Srinath Sai\\Downloads\\Day12_challenge\\.idea\\Sample-inpu1.txt";
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
}


class Solution{
    int[][] directions = {{0,1},{0,-1},{-1,0},{1,0}};
    boolean[][] visited;
    public Long FencingCost(char[][] grid){
        Long ans = 0L;
        visited = new boolean[grid.length][grid[0].length];
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(visited[i][j] == false){
                    Long[] pair = getAreaAndPermeter(grid,i,j,grid[i][j]);
                    ans += pair[0]*pair[1];
                }
            }
        }
        return ans;

    }
    private Long[] getAreaAndPermeter(char[][] grid,int i, int j,char ch){
        Long area =1L;
        Long perimeter = 0L;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i,j});
        visited[i][j] = true;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i1=0;i1<size;i1++){
                int[] parent = q.poll();
                perimeter += getperimeter(grid,parent,ch);
                for(int[] dir : directions){
                    int r = parent[0] + dir[0];
                    int c = parent[1] + dir[1];
                    if(r>=0 && c>=0 && r<grid.length && c<grid[0].length){
                        if(visited[r][c]==false && grid[r][c]==ch){
                            visited[r][c] = true;
                            q.add(new int[]{r,c});
                            area++;
                        }
                    }
                }
            }
        }
        return new Long[]{area,perimeter};
    }
    private Long getperimeter(char[][] grid,int[] source,char ch){
        Long ans =0L;
        for(int[] dir : directions){
            int r = source[0] + dir[0];
            int c = source[1] + dir[1];
            if(r<0 || c<0 || r>=grid.length || c>=grid[0].length){
                ans++;
            }
            else{
                if(grid[r][c]!=ch){
                    ans++;
                }
            }
        }
        return ans;
    }


}







public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        char[][] grid = I.getInput();
        Solution s = new Solution();
        System.out.println("The cost of fencing is " + " " + s.FencingCost(grid));
        //Solution2 s2 = new Solution2();
        //System.out.println("The cost of fencing with sides is " + " " + s2.FencingCostwithSides(grid));
    }
}
