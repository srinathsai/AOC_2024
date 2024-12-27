import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input {
    public Map<Long,String[]> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day7_challenge\\.idea\\input7.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        Map<Long,String[]> map = new HashMap<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] row =line.split(":");
            Long key = Long.parseLong(row[0]);
            String[] elements = row[1].trim().split(" ");
            map.put(key,elements);


        }
        return map;
    }
}

class Solution1{
    public Long getCalibration(Map<Long,String[]> map){
        Long ans = 0L;
        for(Long key : map.keySet()){
            Long[] numbers = new Long[map.get(key).length];
            for(int i=0;i<map.get(key).length;i++){
                numbers[i] = Long.parseLong(map.get(key)[i]);
            }
            if(isSatisfy(key,numbers)){
                ans += key;
            }
        }
        return ans;
    }
    private boolean isSatisfy(Long value, Long[] numbers){
        int operatorCount = numbers.length - 1;
        List<String[]> combinations = generateOperatorCombinations(operatorCount);

        for (String[] combination : combinations) {

            if (evaluateExpression(numbers, combination).equals(value)) {
                return true;
            }
        }
        return false;
    }
    private Long evaluateExpression(Long[] numbers,String[] operators){
        Long result = numbers[0];
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].equals("+")) {
                result += numbers[i + 1];
            } else {
                result *= numbers[i + 1];
            }
        }
        return result;
    }
    private  List<String[]> generateOperatorCombinations(int length) {
        List<String[]> combinations = new ArrayList<>();
        int total = (int) Math.pow(2, length);
        for (int i = 0; i < total; i++) {
            String[] combination = new String[length];
            for (int j = 0; j < length; j++) {
                combination[j] = ((i >> j) & 1) == 0 ? "+" : "*";
            }
            combinations.add(combination);
        }
        return combinations;
    }
}


class Solution2{
    public Long getCalibration2(Map<Long,String[]> map){
        Long ans =0L;
        for(Long key:map.keySet()){
            Long[] numbers = new Long[map.get(key).length];
            for(int i=0;i<numbers.length;i++){
                numbers[i] = Long.parseLong(map.get(key)[i]);
            }
            if(isSatisfy(key,numbers)){
                ans += key;
            }
        }
        return ans;
    }
    private boolean isSatisfy(Long value,Long[] numbers){
        int operatorCount = numbers.length - 1;
        List<String[]> combinations = generateOperatorCombinations(operatorCount);

        for (String[] combination : combinations) {

            if (evaluateExpression(numbers, combination).equals(value)) {
                return true;
            }
        }
        return false;
    }
    private static List<String[]> generateOperatorCombinations(int length) {
        List<String[]> combinations = new ArrayList<>();
        int total = (int) Math.pow(3, length);
        for (int i = 0; i < total; i++) {
            String[] combination = new String[length];
            int num = i;
            for (int j = 0; j < length; j++) {
                int op = num % 3;
                if (op == 0) combination[j] = "+";
                else if (op == 1) combination[j] = "*";
                else combination[j] = "||";
                num /= 3;
            }
            combinations.add(combination);
        }
        return combinations;
    }
    private static Long evaluateExpression(Long[] numbers, String[] operators) {
        Long result = numbers[0];
        for (int i = 0; i < operators.length; i++) {
            if (operators[i].equals("+")) {
                result += numbers[i + 1];
            } else if (operators[i].equals("*")) {
                result *= numbers[i + 1];
            } else if (operators[i].equals("||")) {
                String concatenated = Long.toString(result) + numbers[i + 1];
                result = Long.parseLong(concatenated);
            }
        }
        return result;
    }

}



public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        Map<Long,String[]> map = I.getInput();
        Solution1 s1 = new Solution1();
        //System.out.println("The total calibration results are :-" + " " + s1.getCalibration(map));
        Solution2 s2 = new Solution2();
        System.out.println("The calibration with extra concatenation operator added is :-" + " " + s2.getCalibration2(map));

    }
}
