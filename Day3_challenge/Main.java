import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Input{
    public List<String> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day3_challenge\\.idea\\input3.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<String>input = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            input.add(line);

        }
        return input;
    }
}

class Solution1{
    public Long getMultiplication(List<String> input){
        Long ans = 0L;
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        for(String line : input){
            Matcher matcher = pattern.matcher(line);
            while(matcher.find()){
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                ans += x * y;
            }
        }
        return ans;
    }

}
class Solution2{
    public Long getEnabledMultiplication(List<String> input){
        String s ="";
        for(String line : input){
            s+= line;
        }
        Long sum =0L;
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)");
        Matcher matcher = pattern.matcher(s);
        boolean isMulEnabled = true;
        while (matcher.find()) {
            if (matcher.group().equals("do()")) {
                isMulEnabled = true;
            } else if (matcher.group().equals("don't()")) {
                isMulEnabled = false;
            } else if (matcher.group(1) != null && matcher.group(2) != null) {
                if (isMulEnabled) {
                    int a = Integer.parseInt(matcher.group(1));
                    int b = Integer.parseInt(matcher.group(2));
                    sum += a * b;
                }
            }
        }
        return sum;
    }
}
public class Main {
    public static void main(String[] args) throws IOException {
        Input I = new Input();
        List<String> input = I.getInput();
        Solution1 s1 = new Solution1();
        //System.out.println("The Multiplication result is :-" + " " + s1.getMultiplication(input));
        Solution2 s2 = new Solution2();
        System.out.println("The enabled multiplication is :-" + " " + s2.getEnabledMultiplication(input));

    }

}
