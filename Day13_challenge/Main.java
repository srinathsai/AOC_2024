import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

class Machine {
    Map<Character, int[]> buttons;
    int[] target;

    public Machine() {
        buttons = new HashMap<>();
        target = new int[2];
    }
}

class Input {
    public List<Machine> getInput() throws IOException {
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day13_challenge\\.idea\\input13.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<String> lines = new ArrayList<>();

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.contains("Button A") || line.contains("Button B") || line.contains("Prize")) {
                String[] parts = line.split("(?=Button|Prize)");
                lines.addAll(Arrays.asList(parts));
            }
        }

        List<Machine> machines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            if (i + 2 >= lines.size()) break;

            String A = lines.get(i).trim();
            String B = lines.get(i + 1).trim();
            String C = lines.get(i + 2).trim();

            Machine m = new Machine();
            m.buttons.put('A', parseCoordinates(A, "\\+"));
            m.buttons.put('B', parseCoordinates(B, "\\+"));
            m.target = parseCoordinates(C, "=");
            machines.add(m);
        }

        return machines;
    }

    private int[] parseCoordinates(String input, String delimiter) {

        String[] parts = input.split(":");
        String[] coordinates = parts[1].trim().split(",\\s*");

        int x = Integer.parseInt(coordinates[0].split(delimiter)[1]);
        int y = Integer.parseInt(coordinates[1].split(delimiter)[1]);
        return new int[]{x, y};

    }
}

class Solution{
    public Long getTokens(List<Machine> machines, int max){
        Long ans =0L;
        for(Machine m : machines){
            long moves = movesToReachDestination(m, max);
            if(moves!=-1){
                ans += moves;
            }

        }
        return ans;
    }

    private long movesToReachDestination(Machine m, int max){
        int[] A_moment = m.buttons.get('A');
        int[] B_moment = m.buttons.get('B');
        int[] target = m.target;
        for(int i =0;i<=100;i++){
            for(int j=0;j<=100;j++){
                int X = i*A_moment[0] + j*B_moment[0];
                int Y = i*A_moment[1] + j*B_moment[1];
                if(X == target[0] && Y == target[1]){

                    int cost = 3*i + j;
                    return (long) (cost);


                }
            }
        }
        return -1;
    }
}

class ModifiedMachine{
    Map<Character, int[]> buttons;
    Long[] target;

    public ModifiedMachine() {
        buttons = new HashMap<>();
        target = new Long[2];
    }
}
class Solution2 {
    public List<ModifiedMachine> getModifiedInput() throws IOException {
        //String path = "C:\\Users\\Srinath Sai\\Downloads\\Day13_challenge\\.idea\\input13.txt";
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Srinath Sai\\Downloads\\Day13_challenge\\.idea\\input13.txt"));
        String line;
        List<ModifiedMachine> machines = new ArrayList<>();


        while ((line = br.readLine()) != null) {
            if (line.startsWith("Button A")) {
                int ax = Integer.parseInt(line.split("\\+")[1].split(",")[0].trim());
                int ay = Integer.parseInt(line.split("\\+")[2].trim());
                line = br.readLine();
                int bx = Integer.parseInt(line.split("\\+")[1].split(",")[0].trim());
                int by = Integer.parseInt(line.split("\\+")[2].trim());
                line = br.readLine();
                Long px = Integer.parseInt(line.split("=")[1].split(",")[0].trim()) + 10_000_000_000_000L;
                Long py = Integer.parseInt(line.split("=")[2].trim()) + 10_000_000_000_000L;
                ModifiedMachine m = new ModifiedMachine();
                m.buttons.put('A', new int[]{ax, ay});
                m.buttons.put('B', new int[]{bx, by});
                m.target[0] = px;
                m.target[1] = py;
                machines.add(m);

            }


        }
        return machines;

    }

    public Long getTokens(List<ModifiedMachine> machines){
        Long ans =0L;
        for(ModifiedMachine m : machines){
            long moves = movesToReachDestination(m);
            if(moves!=-1){
                ans += moves;
            }

        }
        return ans;
    }

    private long movesToReachDestination(ModifiedMachine m){
        int[] A_moment = m.buttons.get('A');
        int[] B_moment = m.buttons.get('B');
        Long[] target = m.target;
        long gcdX = gcd(A_moment[0], B_moment[0]);
        long gcdY = gcd(A_moment[1], B_moment[1]);

        int ax = A_moment[0], ay = A_moment[1];
        int bx = B_moment[0], by = B_moment[1];
        long px = target[0], py = target[1];

        int scaleX = (int) (px / gcdX);
        int scaleY = (int) (py / gcdY);

        for (long aPresses = 0; aPresses <= 1000; aPresses++) {
            long remainingX = scaleX - aPresses * (ax / gcdX);
            long remainingY = scaleY - aPresses * (ay / gcdY);

            if (remainingX >= 0 && remainingY >= 0 && remainingX % (bx / gcdX) == 0 && remainingY % (by / gcdY) == 0) {
                long bPressesX = remainingX / (bx / gcdX);
                long bPressesY = remainingY / (by / gcdY);

                if (bPressesX == bPressesY) {
                    long cost = aPresses * 3 + bPressesX;
                    return cost;
                }
            }
        }
        return -1;
    }
    private static long gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


}

public class Main {
    public static void main(String[] args) throws IOException {
        Input I = new Input();
        List<Machine> machines = I.getInput();
        int max = Integer.MIN_VALUE;
        for (Machine m : machines) {
            max = Math.max(m.buttons.get('A')[0],m.buttons.get('A')[1]);
            max = Math.max(max,Math.max(m.buttons.get('B')[0],m.buttons.get('B')[1]));

        }
        System.out.println("The minimum moves to reach prize for all machines is" + " " +  new Solution().getTokens(machines,max));
        List<ModifiedMachine> modifiedMachines = new Solution2().getModifiedInput();
        System.out.println("The minimum moves to reach prize for all Modified machines is" + " " +  new Solution2().getTokens(modifiedMachines));
    }
}
