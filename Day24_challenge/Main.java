import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;


class Operation{
    String operand1;
    String operand2;
    String operator;
    Integer value;
    String register;

    public Operation(String operand1, String operand2,String operator){
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    @Override
    public String toString(){
        return ("the information is  :-" + ", " + " " + this.operand1 + "," + " " + this.operand2 + "," + " " + this.operator + "," + " ." + "The value of register is" + " " +this.register + " " +"." + "The operation result is :-" + " " + this.value);
    }

}
class Input{
    Map<String,Integer> registers = new TreeMap<>();
    List<Operation>operations = new ArrayList<>();
    public void readInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day24_challenge\\.idea\\Sample_Input.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        int index = 0;
        for(int i=0;i<lines.size();i++){
            if(lines.get(i).length()==0){
                index =i;
                break;
            }
        }
        //System.out.println(index);
        List<String> variables = lines.subList(0,index);
        List<String> instructions = lines.subList(index+1,lines.size());
        fillRegisters(variables);
        //System.out.println(registers.toString());
        instructions.forEach((x)->{
            String[] cache = x.split("->");
            String[] left = cache[0].split(" ");
            Operation o = new Operation(left[0].trim(),left[2].trim(),left[1].trim());
            o.register = cache[1].trim();
            operations.add(o);
        });

        //printOperations();


    }

    private void fillRegisters(List<String> variables){
        variables.forEach((x)->{
            String[] cache = x.split(":");
            registers.put(cache[0],Integer.parseInt(cache[1].trim()));
            //System.out.println(Arrays.toString(cache));
        });
    }

    private void printOperations(){
        operations.forEach((x)->System.out.println(x.toString()));
    }
}


class Solution{
    public Long part1(Map<String,Integer> registers, List<Operation> operations){
        //List<Operation> calculated = new ArrayList<>();
        for(Operation o : operations){
            String operand1 = o.operand1;
            String operand2 =o.operand2;
            String operator =o.operator;
            if(registers.containsKey(operand1) && registers.containsKey(operand2)){
                int result = 0;
                int op1 = registers.get(operand1);
                int op2 = registers.get(operand2);
                if(operator.equals("AND")){
                    result = op1 & op2;
                }
                else if(operator.equals("OR")){
                    result = op1 | op2;
                }
                else{
                    result = op1 ^ op2;
                }
                String result_register = o.register;
                if(!registers.containsKey(result_register)){
                    registers.put(result_register,result);
                }
                o.value = result;
                //calculated.add(o);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(String register : registers.keySet()){
                sb.append(registers.get(register));
        }
        Long ans =0L;
        String S = sb.reverse().toString();
        int power = 0;
        for(int i=S.length()-1;i>=0;i--){
            int val = Character.getNumericValue(S.charAt(i));
            int r = (int)((val)*(Math.pow(2,power)));
            ans += r;
            power++;
        }



        return ans;

    }
}





public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        I.readInput();
        Solution S = new Solution();
        System.out.println("The output of part1 is " + S.part1(I.registers,I.operations));
    }
}
