import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    public int[][] getInput() throws IOException {
        String dimensions  = getDimensionsofInput();
        String[] sizes = dimensions.split(",");

        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day10_challenge\\.idea\\input10.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());

        int[][] input = new int[Integer.parseInt(sizes[0])][Integer.parseInt(sizes[1])];
        int index =0;
        while(sc.hasNextLine()){
            String row = sc.nextLine();
            for(int j=0;j<row.length();j++){
                input[index][j] = Character.getNumericValue(row.charAt(j));
            }
            index++;
        }
        return input;
    }
    private String getDimensionsofInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day10_challenge\\.idea\\input10.txt";
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



class Solution1{
    int[][] directions = {{0,1},{0,-1},{1,0},{-1,0}};

    public Long getTrialCount(int[][] input){
        Long count =0L;
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j]==0){
                    count += hike(input,i,j);
                }
            }
        }
        return count;
    }

    private Long hike(int[][] input, int i, int j){
        Long ans = 0L;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{i, j});
        Set<String> visited = new HashSet<>();
        visited.add(i + "," + j);
        Set<String> endPoints = new HashSet<>();

        while (!q.isEmpty()) {
            int[] parent = q.poll();

            for (int[] dir : directions) {
                int r = parent[0] + dir[0];
                int c = parent[1] + dir[1];
                if (r >= 0 && r < input.length && c >= 0 && c < input[0].length) {
                    if (input[r][c] - input[parent[0]][parent[1]] == 1) {
                        if (input[r][c] == 9) {

                            String point = r + "," + c;
                            if (!endPoints.contains(point)) {
                                endPoints.add(point);
                                ans++;
                            }
                        } else {
                            String nextPoint = r + "," + c;
                            if (!visited.contains(nextPoint)) {
                                visited.add(nextPoint);
                                q.add(new int[]{r, c});
                            }
                        }
                    }
                }
            }
        }

        return ans;
    }
}

class Solution2{
    int[][] directions = {{0,1},{-1,0},{0,-1},{1,0}};
    boolean[][] visited;
    Map<String,Long> dp;
    public Long getHikeRating(int[][] input){
        Long ans =0L;
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                visited = new boolean[input.length][input[0].length];
                dp = new HashMap<>();
                if(input[i][j]==0){
                    visited[i][j] = true;
                    ans +=recurssion(input,i,j);
                }
            }
        }
        return ans;
    }

    private Long recurssion(int[][] input,int i, int j){
        if(i<0 || j<0 || i>=input.length || j>input[0].length){
            return 0L;
        }

        if(input[i][j]==9){
            return 1L;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(',');
        sb.append(j);
        String s = sb.toString();
        if(dp.containsKey(s)){
            return dp.get(s);
        }

        Long count =0L;
        for(int[] dir : directions){
            int r = i + dir[0];
            int c = j + dir[1];
            if(r>=0 && r<input.length && c>=0 && c<input[0].length){
                if(input[r][c] - input[i][j]==1){
                    if(visited[r][c]==false){
                        visited[r][c] = true;
                        count += recurssion(input,r,c);
                        visited[r][c] = false;
                    }
                }
            }
        }

        dp.put(s,count);
        return count;

    }
}




public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        int[][] input = I.getInput();
        //Solution1 s1 = new Solution1();
        //System.out.println("The hike count of part 1 is :-" + " " + String.valueOf(s1.getTrialCount(input)));
        Solution2 s2 = new Solution2();
        System.out.println("The rating of hikes are :-" + " " + s2.getHikeRating(input));
    }
}
