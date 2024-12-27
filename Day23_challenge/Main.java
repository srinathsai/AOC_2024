import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

class Input{
    public Map<String, Set<String>> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day23_challenge\\.idea\\Input23.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        Map<String,Set<String>> graph = new HashMap<>();
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] nodes = line.split("-");
            if(!graph.containsKey(nodes[0])){
                graph.put(nodes[0],new HashSet<>());
            }
            if(!graph.containsKey(nodes[1])){
                graph.put(nodes[1],new HashSet<>());
            }
            graph.get(nodes[0]).add(nodes[1]);
            graph.get(nodes[1]).add(nodes[0]);
        }

        printGraph(graph);
        //System.out.println(graph.size());
        /*graph.keySet().forEach((x)-> {
            System.out.println("The key is :-" +" " + x + " "+ "The values are :-" + " " + graph.get(x).toString());
        });*/

        return graph;
    }

    private void printGraph(Map<String,Set<String>> graph){
        System.out.println(graph.toString());
    }
}

class Solution{
    public Long getConnected(Map<String,Set<String>> graph){
        Map<String,Set<String>> subgraph =graph.entrySet().stream().filter((x)->x.getKey().charAt(0)=='t').collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        //System.out.println(subgraph.toString());
        Long ans =0L;
        Set<String>covered = new HashSet<>();
        for(String source : subgraph.keySet()){
            for(String destination : subgraph.get(source)){
                if(graph.containsKey(destination)){
                    for(String child : graph.get(destination)){
                        if(!child.equals(source)){
                            if(graph.containsKey(child) && graph.get(child).contains(source)){
                                String[] cache = new String[]{source,destination,child};
                                Arrays.sort(cache);
                                String triplet = Arrays.toString(cache);
                                if(!covered.contains(triplet)){
                                    covered.add(triplet);
                                    ans++;
                                }

                            }
                        }
                    }
                }
            }
        }
        return ans;
    }

    public String getPassword(Map<String, Set<String>> graph) {

        Set<String> largestClique = new HashSet<>();


        for (String node : graph.keySet()) {
            Set<String> r = new HashSet<>();
            Set<String> p = new HashSet<>(graph.keySet());
            Set<String> x = new HashSet<>();
            findCliques(r, p, x, graph, largestClique);
        }


        List<String> passwordList = new ArrayList<>(largestClique);
        Collections.sort(passwordList);


        return String.join(",", passwordList);
    }


    private void findCliques(Set<String> r, Set<String> p, Set<String> x, Map<String, Set<String>> graph, Set<String> largestClique) {
        if (p.isEmpty() && x.isEmpty()) {

            if (r.size() > largestClique.size()) {
                largestClique.clear();
                largestClique.addAll(r);
            }
            return;
        }

        String pivot = p.isEmpty() ? x.iterator().next() : p.iterator().next();
        Set<String> pivotNeighbors = graph.getOrDefault(pivot, Collections.emptySet());
        Set<String> candidates = new HashSet<>(p);
        candidates.removeAll(pivotNeighbors);

        for (String v : candidates) {
            Set<String> newR = new HashSet<>(r);
            newR.add(v);

            findCliques(
                    newR,
                    intersect(p, graph.get(v)),
                    intersect(x, graph.get(v)),
                    graph,
                    largestClique
            );

            p.remove(v);
            x.add(v);
        }
    }


    private Set<String> intersect(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }



}

public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        Solution S = new Solution();
        Map<String,Set<String>> graph =I.getInput();
        System.out.println("The part2 answer is :-" + " " + S.getPassword(graph));
        //System.out.println("The part1 answer is :-" + " " + S.getConnected(I.getInput()));
    }
}
