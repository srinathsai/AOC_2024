import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;

class Pair{
    List<Long>list1;
    List<Long>list2;
    public Pair(List<Long> list1, List<Long>list2){
      this.list1 = list1;
      this.list2 = list2;
    }

}

class Input{
    public Pair getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\challenge\\.idea\\input_task1.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        //int count =0;
        List<Long> list1 = new ArrayList<>();
        List<Long> list2 = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] cache = line.split(" ");
            //System.out.println(cache[0] + " " + cache[3]);
            list1.add(Long.parseLong(cache[0]));
            list2.add(Long.parseLong(cache[3]));
            //count++;
        }
        return new Pair(list1,list2);
    }
}


class Solution1 {
    public Long calculate(List<Long> list1, List<Long> list2){
        Collections.sort(list1);
        Collections.sort(list2);
        Long ans = 0L;
        for(int i=0;i<list1.size();i++){
            ans += Math.abs(list1.get(i) - list2.get(i));
        }
        return ans;
    }
}

public class Main{
    public static void main(String[] ags) throws IOException {
        Input I = new Input();
        Solution1 s = new Solution1();
        System.out.println("The value is :-" + " " + s.calculate(I.getInput().list1, I.getInput().list2) );
    }

}

class Solution2{
    public Long calculate_Similarity(List<Long> list1, List<Long> list2){
        Map<Long,Long>map = new HashMap<>();
        for(Long num : list2){
            map.put(num, map.getOrDefault(num,0L) +1);
        }
        //System.out.println(map.toString());
        Long score =0L;
        for(Long id : list1){
            if(map.containsKey(id)){
                score += (id*map.get(id));
            }
        }
        return score;
    }
}

class Main2{
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        Solution2 s2 = new Solution2();
        System.out.println("The similarity score is " + " " + s2.calculate_Similarity(I.getInput().list1,I.getInput().list2));
    }
}

