import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

class Input{
    public  List<List<Integer>> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day2_challenge\\.idea\\input2.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<List<Integer>>input = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] cache = line.split(" ");
            List<Integer> row = new ArrayList<>();
            for(String num : cache){
                row.add(Integer.parseInt(num));
            }
            input.add(row);

        }
        return input;
    }
}

class Solution1{
    public int safeCount(List<List<Integer>> input){
        int count =0;
        for(List<Integer> row : input){
            if(isIncreasing(row) || isDecreasing(row)){
                if(checkDifference(row)){
                    count++;
                }
            }
        }
        return count;
    }
    public boolean isSafe(List<Integer> row){
        return ((isIncreasing(row) || isDecreasing(row)) && checkDifference(row)==true)?true : false;
    }
    private boolean isIncreasing(List<Integer> row){
        for(int i=1;i<row.size();i++){
            if(row.get(i)<=row.get(i-1)){
                return false;
            }
        }
        return true;
    }
    private boolean isDecreasing(List<Integer> row){
        for(int i=1;i<row.size();i++){
            if(row.get(i)>=row.get(i-1)){
                return false;
            }
        }
        return true;
    }
    private boolean checkDifference(List<Integer> row){
        for(int i=1;i<row.size();i++){
            int difference = Math.abs(row.get(i) - row.get(i-1));
            if(difference ==0 || difference>3){
                return false;
            }
        }
        return true;
    }

}

class Solution2{
    public int newSafeCount(List<List<Integer>>input){
        int count =0;
        Solution1 s1 = new Solution1();
        for(List<Integer> row : input){
            if(s1.isSafe(row)){
                count++;
            }
            else{
                for(int i=0;i<row.size();i++){
                    List<Integer> newRow = new ArrayList<>();
                    for(int j=0;j<row.size();j++){
                        if(j!=i){
                            newRow.add(row.get(j));
                        }
                    }
                    if(s1.isSafe(newRow)==true){
                        count++;
                        break;
                    }
                }
            }

        }
        return count;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        Input I = new Input();
        List<List<Integer>> input =I.getInput();
        Solution1 s1 = new Solution1();
        System.out.println("The safe count is :- " + " " + s1.safeCount(input));
        Solution2 s2 = new Solution2();
        System.out.println("The new Safe count is :-" + " " + s2.newSafeCount(input));

    }
}
