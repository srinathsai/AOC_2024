import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Input{
    List<Integer>instructions;
    Long register_A;
    Long register_B;
    Long register_C;
    public Input(){
        this.instructions = new ArrayList<>();
        this.register_A = 0L;
        this.register_B = 0L;
        this.register_C = 0L;

    }
    public void getInput() throws IOException{
        List<String> lines = new ArrayList<>();
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day17_challenge\\.idea\\input17.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        //System.out.println(lines.toString());
        //System.out.println(lines.size());
        String InstructionPart = lines.get(lines.size()-1);
        String[] cache =InstructionPart.split(":");
        String[] Instructions_Numbers = cache[1].trim().split(",");
        Arrays.stream(Instructions_Numbers).forEach(instruction -> this.instructions.add(Integer.parseInt(instruction)));
        register_A = Long.parseLong(lines.get(0).split(":")[1].trim());
        register_B = Long.parseLong(lines.get(1).split(":")[1].trim());
        register_C = Long.parseLong(lines.get(2).split(":")[1].trim());


    }
}

 class Computer {
    Long A;
    Long B;
    Long C;
    List<Integer> opcodes;
    List<Integer> out = new ArrayList<>();
    int ip =0;

     Computer (long registerA, long registerB, long registerC, List<Integer> opcodes) {
         A=registerA;
         B=registerB;
         C=registerC;
         this.opcodes=opcodes;
         ip=0;
     }


    void execute() {
        execute(false, List.of());
    }

    void execute(boolean earlyStop, List<Integer> expected) {
        while (ip < opcodes.size()) {
            int litOp = opcodes.get(ip+1);
            long combOp = getCombo(opcodes.get(ip+1));
            boolean skipIncrease = false;
            if(opcodes.get(ip)==0){
                long den = (long)Math.pow(2, combOp);
                A = A / den;
            }
            else if(opcodes.get(ip)==1){
                B = B ^ ((long)litOp);
            }
            else if(opcodes.get(ip)==2) {
                B = combOp % 8;
            }
            else if(opcodes.get(ip)==3) {
                if(A != 0) {
                    ip = litOp;
                    skipIncrease = true;
                    if(earlyStop && !outPutMatches(expected)) {
                        return;
                    }
                }
            }
            else if(opcodes.get(ip)==4) {
                B = B^C;
            }
            else if(opcodes.get(ip)==5) {
                out.add((int) (combOp % 8L));
            }
            else if(opcodes.get(ip)==6) {
                long den = (long)Math.pow(2, combOp);
                B = A / den;
            }

            else if(opcodes.get(ip)==7) {
                long den = (long)Math.pow(2, combOp);
                C = A / den;
            }

            if(!skipIncrease) {
                ip += 2;
            }
        }
    }

    boolean outPutMatches(List<Integer> expected) {
        if(out.size()>expected.size()) {
            return false;
        }
        for(int i=0; i<out.size(); i++) {
            if(!out.get(i).equals(expected.get(i))) {
                return false;
            }
        }
        return true;
    }

    long getCombo(int value) {
        return switch (value%8) {
            case 0,1,2,3 -> value;
            case 4 -> A;
            case 5 -> B;
            case 6 -> C;
            case 7 -> Long.MIN_VALUE;//throw new IllegalStateException("The value 7 should not appear in valid programs");
            default -> throw new IllegalStateException("Impossible to reach");
        };
    }

    String print() {
        return String.join(",", out.stream().map(Long::toString).toList());
    }
}

class Solution{
    Computer device;
    public Solution() throws IOException{
        Input I = new Input();
        I.getInput();
        device = new Computer(I.register_A,I.register_B,I.register_C,I.instructions);


    }
    public String part1(){
        this.device.execute();
        return device.print();
    }

    public String part2() throws IOException {
        Input I = new Input();
        I.getInput();
        List<Integer> remainingProgram = new ArrayList<>(I.instructions);
        List<Integer> program = new ArrayList<>();
        long A = 0L;
        while (!remainingProgram.isEmpty()) {
            --A;
            program.addFirst(remainingProgram.removeLast());
            String pString = program.stream().map(Long::toString).collect(Collectors.joining(","));
            Computer computer;
            do {
                ++A;
                computer = new Computer(A, I.register_B, I.register_C, I.instructions);
                computer.execute(true, program);
            } while (!computer.print().equals(pString));
            if(!remainingProgram.isEmpty()) {
                A=A<<3;
            }
        }
        return Long.toString(A);
    }
}








public class Main {
    public static void main(String[] args) throws IOException{
        Solution S = new Solution();
        System.out.println("The final values after part1 are :-" + S.part1());
        System.out.println("The final values after part1 are :-" + S.part2());
    }
}
