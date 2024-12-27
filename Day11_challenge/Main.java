import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Pair{
    Long number;
    String n;
    public Pair(Long number, String n){
        this.number = number;
        this.n = n;
    }
    @Override
    public String toString() {
        return "{number ='" + this.number + "', number in String format is :- " + this.n;
    }
}
class Input{
    public List<Pair> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day11_challenge\\.idea\\input11.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<Pair> elements = new ArrayList<>();
        while(sc.hasNextLine()){
            String s = sc.nextLine();
            String[] cache = s.split(" ");
            for(String element : cache){
                elements.add(new Pair(Long.parseLong(element),element));
            }
        }
        //System.out.println(elements.toString());

        return elements;
    }
}

class Solution{

    int times ;
    Map<String,Long> dp;
    public Solution(int times){
        this.times = times;
        dp = new HashMap<>();
    }
    public Long getStoneCount(List<Pair> elements){
        Long ans =0L;
        for(Pair element : elements){
            ans +=reccursion(element,0);
        }
        return ans;
    }



    private Long reccursion(Pair element, int iterations){
        if(iterations>=this.times){
            return 1L;
        }
        String key = element.n + "," + iterations;
        if(dp.containsKey(key)){
            return dp.get(key);
        }
        Long ans =0L;
        if(element.number.equals(0L)){
            Pair newElement = new Pair(1L,"1");
            ans += reccursion(newElement,iterations+1);

        }
        else if(element.n.length()%2==0){
            String s = element.n;
            String firstHalf = s.substring(0, s.length() / 2);
            String secondHalf = s.substring(s.length() / 2);
            Long first = Long.parseLong(firstHalf);
            Long second = Long.parseLong(secondHalf);

            Pair firstElement = new Pair(first,first.toString());
            ans += reccursion(firstElement,iterations+1);

            Pair SecondElement = new Pair(second,second.toString());
            ans += reccursion(SecondElement,iterations+1);

        }
        else{
            Long newNumber = element.number * 2024;
            Pair newElement = new Pair(newNumber,newNumber.toString());
            ans += reccursion(newElement,iterations+1);

        }

        dp.put(key,ans);
        return ans;
    }


}


public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        List<Pair> elements = I.getInput();
        //System.out.println(new Solution(3).dp.toString());
        System.out.println("The stone count for part 1 is :-" + new Solution(75).getStoneCount(elements));

    }
}
