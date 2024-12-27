import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    public List<Long> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day22_challenge\\.idea\\Input22.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<Long> nums = new ArrayList<>();
        while(sc.hasNextLine()){
            nums.add(Long.parseLong(sc.nextLine()));
        }
        return nums;
    }
}


class Solution{
    List<List<Long>> digits = new ArrayList<>();
    List<Map<Long,Integer>>digits_frequency = new ArrayList<>();
    public Long getSecret(List<Long> nums){
        Long ans =0L;
        for(Long num : nums){
            Long value = transform(num);
            ans+= value;

        }
        //System.out.println("The total elements are :-" + " " + digits.size());
        //System.out.println("Every row is of size :-" + " " + digits.get(0).size());
        //digits.forEach(a->System.out.println(a.toString()));
        //fill_digits_frequency();
        //digits_frequency.forEach(a->System.out.println(a.toString()));
        return ans;
    }

    private Long transform(Long num){
        List<Long> row = new ArrayList<>();
        for(int i=0;i<2000;i++){

            Long newValue = num*64;
            num = num^newValue;
            num = num%(16777216);
            newValue = num/32;
            num = num^newValue;
            num = num%(16777216);
            newValue = num*2048;
            num = num^newValue;
            num = num%(16777216);
            row.add(num%10);
        }
        digits.add(row);
        Map<Long,Integer>map = new HashMap<>();
        row.forEach((x)->map.put(x,map.getOrDefault(x,0)+1));
        digits_frequency.add(map);
        return num;
    }

   

}



public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        List<Long> nums = I.getInput();

        System.out.println("The secrets are :-" + " " + new Solution().getSecret(nums));
    }
}
