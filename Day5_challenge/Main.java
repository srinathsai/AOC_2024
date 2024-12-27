import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Input{
    public Map<String, Set<String>> input1() throws IOException {
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day5_challenge\\.idea\\input5_part1.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        //List<String>input = new ArrayList<>();
        Map<String,Set<String>> map = new HashMap<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            String num1 ="";
            String num2 = "";
            int i=0;
            while(i<line.length() && line.charAt(i)!='|'){
                i++;
            }
            num1 = line.substring(0,i);
            num2 = line.substring(i+1,line.length());
            //System.out.println(line);
            //System.out.println(num1);
            //System.out.println(num2);
            if(!map.containsKey(num1)){
                map.put(num1, new HashSet<>());
            }

            map.get(num1).add(num2);



        }
        //System.out.println(map.toString());
        return map;
    }

    public List<Map<String,Integer>> input2() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day5_challenge\\.idea\\input5_part2.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<Map<String,Integer>> rows = new ArrayList<>();
        while(sc.hasNextLine()){
            String[] cache = sc.nextLine().split(",");
            Map<String, Integer> map = new LinkedHashMap<>();
            for(int i=0;i<cache.length;i++){
                map.put(cache[i],i);
            }
            rows.add(map);
        }
        //System.out.println(rows.toString());
        return rows;
    }

}

class Solution1{
    public Long getMiddleNumber(Map<String,Set<String>> map, List<Map<String,Integer>> rows){
        Long ans = 0L;
        for(Map<String,Integer> row :rows){
            if(isOrder(map,row)){
                List<String> elements = new ArrayList<>();
                for(String key : row.keySet()){
                    elements.add(key);
                }
                int l =0;
                int h = elements.size()-1;
                int m = (l + (h-l)/2);
                ans += Long.parseLong(elements.get(m));
            }

        }
        return ans;
    }

    public List<Map<String,Integer>> getInorderList(Map<String,Set<String>> map, List<Map<String,Integer>> rows){
        List<Map<String,Integer>> Inorder = new ArrayList<>();
        for(Map<String,Integer> row :rows){
            if(isOrder(map,row) == false){
                Inorder.add(row);
            }

        }
        return Inorder;
    }

    private boolean isOrder(Map<String,Set<String>> map,Map<String,Integer> row){
        for(String key : row.keySet()){
            int index = row.get(key);
            if(map.containsKey(key)){
                for(String next : map.get(key)){
                    if(row.containsKey(next)){
                        if(row.get(next)<index){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}

class Solution2{
    Set<String> pages;
    public Long getUnorderMiddleCount(Map<String,Set<String>> map, List<Map<String,Integer>> UnorderList){
        Long ans =0L;
        //int count =0;
        for(Map<String,Integer> row : UnorderList){
            Map<String,Set<String>> subGraph = getSubgraph(map,row);
            //System.out.println(subGraph.size());
            Map<String,Integer> indegree = getIndegree(subGraph);
            //System.out.println(indegree.toString());
            List<String> elements = Travel(subGraph,indegree);
            ans += Long.parseLong(elements.get(elements.size()/2));
        }
        return ans;
    }

    private Map<String,Set<String>> getSubgraph(Map<String,Set<String>> map,Map<String,Integer> row){
        Map<String,Set<String>> subGraph = new HashMap<>();
        for(String key : row.keySet()){
            if(map.containsKey(key)){
                if(!subGraph.containsKey(key)){
                    subGraph.put(key,new HashSet<>());
                }
                for(String child : map.get(key)){
                    if(row.containsKey(child)){
                        subGraph.get(key).add(child);
                    }
                }
            }
        }
        return subGraph;
    }
    private Map<String,Integer> getIndegree(Map<String, Set<String>> subGraph){
        Map<String,Integer> Indegree = new HashMap<>();
        for(String key : subGraph.keySet()){
            Indegree.put(key,0);
        }
        for(String key: subGraph.keySet()){
            for(String child : subGraph.get(key)){
                Indegree.put(child,Indegree.get(child)+1);
            }
        }
        return Indegree;
    }

    private List<String> Travel(Map<String,Set<String>> subGraph,Map<String,Integer> indegree){
        Queue<String> q = new LinkedList<>();
        String source = "";
        for(String key : indegree.keySet()){
            if(indegree.get(key)==0){
                source = key;
                break;
            }
        }
        List<String> pageOrder = new ArrayList<>();
        q.add(source);

        while(!q.isEmpty()){
            int size = q.size();
            for(int i=0;i<size;i++){
                String parent = q.poll();
                pageOrder.add(parent);
                for(String child : subGraph.get(parent)){
                    indegree.put(child, indegree.get(child)-1);
                    if(indegree.get(child)==0){
                        q.add(child);
                    }
                }
            }
        }
        return pageOrder;
    }


}

public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        Map<String,Set<String>> map = I.input1();
        List<Map<String,Integer>> rows = I.input2();

        Solution1 s1 = new Solution1();
        //System.out.println("The Sum of mid values of all ordered pages is :-" + " " + s1.getMiddleNumber(map,rows));
        List<Map<String,Integer>> UnorderList = s1.getInorderList(map,rows);
        //System.out.println(rows.size());
        //System.out.println(UnorderList.size());
        Solution2 s2 = new Solution2();
        System.out.println("The wrongly arranged elements mid sums are :- " + " " +s2.getUnorderMiddleCount(map,UnorderList));

    }
}
