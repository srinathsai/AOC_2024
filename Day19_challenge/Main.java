import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    Set<String>towelPatterns = new HashSet<>();
    List<String>towels = new ArrayList<>();
    public void getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day19_challenge\\.idea\\input19.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<String>lines = new ArrayList<>();
        while(sc.hasNext()){
            lines.add(sc.nextLine());
        }
        String[] patterns = lines.get(0).split(",");
        Arrays.stream(patterns).forEach((x)->towelPatterns.add(x.trim()));
        for(int i=2;i<lines.size();i++){
            towels.add(lines.get(i).trim());
        }

    }

    public void printInput(){
        System.out.println("The patterns are :-" + " " + towelPatterns.toString());
        System.out.println("The towels are :-" + " " + towels.toString());
    }
}

class Solution{
    public Long getDesignCount(Set<String> patterns, List<String> towels){
        Long count =0L;
        for(String towel : towels){
            if(canBeFormed(towel,patterns,0, new HashMap<>())){
                count++;
            }
        }
        return count;
    }

    private boolean canBeFormed(String towel, Set<String> patterns, int index, Map<Integer,Boolean> dp){
        if(index>=towel.length()){
            return true;
        }
        if(dp.containsKey(index)){
            return dp.get(index);
        }
        boolean flag = false;
        for(int i=index;i<towel.length();i++){
            String sub = towel.substring(index,i+1);
            //System.out.println(sub);
            if(patterns.contains(sub) && canBeFormed(towel,patterns,i+1,dp)){
                flag = true;
            }
        }
        dp.put(index,flag);
        return flag;
    }

    public Long getCombinations(Set<String> patterns, List<String>towels){
        Long ans =0L;
        for(String towel:towels){
            ans += combinationCount(towel,patterns,0,new HashMap<>());
        }
        return ans;
    }

    private Long combinationCount(String towel,Set<String> patterns,int index, Map<Integer,Long> dp){
        if(index>=towel.length()){
            return 1L;
        }
        if(dp.containsKey(index)){
            return dp.get(index);
        }
        Long ans =0L;
        for(int i=index;i<towel.length();i++){
            String sub = towel.substring(index,i+1);
            if(patterns.contains(sub)){
                ans += combinationCount(towel,patterns,i+1,dp);
            }
        }
        dp.put(index,ans);
        return ans;
    }
}











public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        I.getInput();
        Solution S = new Solution();
        System.out.println("The design count is :-" + " " + S.getDesignCount(I.towelPatterns,I.towels));
        System.out.println("The combination Count is :-" + " " + S.getCombinations(I.towelPatterns,I.towels));

    }
}
