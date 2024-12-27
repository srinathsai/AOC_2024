import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Input{
    public char[][] getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day4_challenge\\.idea\\input4.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        //List<String>input = new ArrayList<>();
        char[][] input = new char[140][140];
        int index =0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for(int j=0;j<line.length();j++){
                input[index][j] = line.charAt(j);
            }

            //input.add(line);
            index++;

        }
        //System.out.println("The rows are" + " " +count);
        return input;
    }

}

class Solution1{
    public Long getCount(char[][] input){
        Long Totalcount =0L;
        Long rightCount = getRightCount(input, "XMAS");
        //System.out.println("The rightside count is :-" + " " + rightCount);
        Long downCount = getDownCount(input,"XMAS");
        //System.out.println("The downcount is :-" + " " + downCount);
        Long downRightCount = getDownRightCount(input, "XMAS");
        //System.out.println("The downright count is :-" + " " + downRightCount);
        Long upRightCount = getUpRightCount(input,"XMAS");
        //System.out.println("The UpRightCount is :-" + " "+ upLeftCount);
        Long upCount = getUpCount(input, "XMAS");
        //System.out.println("The UpCount is :-" + " "+ upCount);
        Long leftCount = getLeftCount(input,"XMAS");
        //System.out.println("The leftCount is :-" + " "+ leftCount);
        Long upLeftCount = getUpLeftCount(input,"XMAS");
        //System.out.println("The UpLeftCount is :-" + " "+ upLeftCount);
        Long downLeftCount = getDownLeftCount(input,"XMAS");
        //System.out.println("The downLeftCount is :-" + " "+ downLeftCount);
        Totalcount = rightCount + downCount + downLeftCount + downRightCount + upCount + upRightCount +leftCount +upLeftCount;
        return Totalcount;
    }

    private Long getRightCount(char[][] input, String word){
        Long count =0L;
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j]=='X'){
                    String S = "";
                    for(int k=j;k<=j+3 && k<input[0].length;k++){
                        S += input[i][k];
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Long getDownCount(char[][] input, String word){
        Long count =0L;
        for(int j=0;j<input[0].length;j++){
            for(int i=0;i<input.length;i++){
                if(input[i][j] == 'X'){
                    String S ="";
                    for(int k=i;k<=i+3 && k<input.length;k++){
                        S += input[k][j];
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }

        return count;

    }
    private Long getDownRightCount(char[][] input, String word){
        Long count =0L;
        for(int i=0;i<input.length;i++){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j]=='X'){
                    String S = "";
                    int r =i;
                    int c =j;
                    while(r<=i+3 && c<=j+3 && r<input.length && c<input[0].length){
                        S += input[r][c];
                        r++;
                        c++;
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public Long getUpRightCount(char[][]input, String word){
        Long count =0L;
        for(int i= input.length-1;i>=0;i--){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j]=='X'){
                    String s = "";
                    int r = i;
                    int c = j;
                    while(r>=i-3 && c<=j+3 && r>=0 && c<input[0].length){
                        s += input[r][c];
                        r--;
                        c++;
                    }
                    if(s.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public Long getUpCount(char[][] input,String word){
        Long count =0L;
        for(int i=input.length-1;i>=0;i--){
            for(int j=0;j<input[0].length;j++){
                if(input[i][j]=='X'){
                    String S = "";
                    for(int k=i;k>=i-3 && k>=0;k--){
                        S += input[k][j];
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Long getLeftCount(char[][] input, String word){
        Long count = 0L;
        for(int i=0;i<input.length;i++){
            for(int j=input[0].length-1;j>=0;j--){
                if(input[i][j] == 'X'){
                    String S ="";
                    for(int k=j;k>=j-3 && k>=0;k--){
                         S += input[i][k];
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private Long getUpLeftCount(char[][]input,String word){
        Long count =0L;
        for(int i=input.length-1;i>=0;i--){
            for(int j=input[0].length-1;j>=0;j--){
                if(input[i][j]=='X'){
                    String S = "";
                    int r = i;
                    int c = j;
                    while(r>=i-3 && c>=j-3 && r>=0 && c>=0){
                        S += input[r][c];
                        r--;
                        c--;
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;

    }

    private Long getDownLeftCount(char[][] input, String word){
        Long count = 0L;
        for(int i=0;i<input.length;i++){
            for(int j=input[0].length-1;j>=0;j--){
                if(input[i][j] == 'X'){
                    String S = "";
                    int r = i;
                    int c = j;
                    while(r<=i+3 && c>=j-3 && r<input.length && c>=0){
                        S += input[r][c];
                        r++;
                        c--;
                    }
                    if(S.equals(word)){
                        count++;
                    }
                }
            }
        }
        return count;

    }
}

class Solution2{
    public Long getMASCount(char[][] input){
        Long count =0L;
        for(int i=1;i<input.length-1;i++){
            for(int j=1;j<input[0].length-1;j++){
                if(isForwardXMas(input, i, j) || isReverseXMas(input, i, j) || isMixedXMas(input, i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private  boolean isForwardXMas(char[][] grid, int x, int y) {

        return grid[x - 1][y - 1] == 'M' && grid[x][y] == 'A' && grid[x + 1][y + 1] == 'S' &&
                grid[x - 1][y + 1] == 'M' && grid[x + 1][y - 1] == 'S';
    }

    private boolean isReverseXMas(char[][] grid, int x, int y) {

        return grid[x - 1][y - 1] == 'S' && grid[x][y] == 'A' && grid[x + 1][y + 1] == 'M' &&
                grid[x - 1][y + 1] == 'S' && grid[x + 1][y - 1] == 'M';
    }

    private boolean isMixedXMas(char[][] grid, int x, int y) {

        return (grid[x - 1][y - 1] == 'M' && grid[x][y] == 'A' && grid[x + 1][y + 1] == 'S' &&
                grid[x - 1][y + 1] == 'S' && grid[x + 1][y - 1] == 'M') ||
                (grid[x - 1][y - 1] == 'S' && grid[x][y] == 'A' && grid[x + 1][y + 1] == 'M' &&
                        grid[x - 1][y + 1] == 'M' && grid[x + 1][y - 1] == 'S');
    }
}








public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        char[][] input = I.getInput();
        Solution1 s1 = new Solution1();
        //System.out.println("The total counts are :-" + " " + s1.getCount(input));
        Solution2 s2 = new Solution2();
        System.out.println("The MAS in X shape count is :-" + " " + s2.getMASCount(input));

    }
}
