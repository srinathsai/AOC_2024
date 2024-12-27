import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Input{
    public String getInput() throws IOException{
        String diskMap = new String(Files.readAllBytes(new File("C:\\Users\\Srinath Sai\\Downloads\\Day9_challenge\\.idea\\input9.txt").toPath())).trim();
        return diskMap;
    }
}

class Solution1{
    public Long part1(String input){
        List<Object> blocks = new ArrayList<>();
        int diskId = 0;
        Long result =0L;
        for (int i = 0; i < input.length(); i++) {
            int size = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                for (int j = 0; j < size; j++) {
                    blocks.add(diskId);
                }
                diskId++;
            } else {
                for (int j = 0; j < size; j++) {
                    blocks.add(".");
                }
            }
        }

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).equals(".")) {
                while (blocks.get(blocks.size() - 1).equals(".")) {
                    blocks.remove(blocks.size() - 1);
                }
                if (blocks.size() <= i) {
                    break;
                }
                blocks.set(i, blocks.remove(blocks.size() - 1));
            }
        }


        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).equals(".")) {
                result += i * (int) blocks.get(i);
            }
        }
        return result;
    }
}

class Solution2{
    public Long part2(String input){
        List<int[]> freeSpace = new ArrayList<>();
        Map<Integer, int[]> files = new HashMap<>();

        int diskId = 0, position = 0;

        for (int i = 0; i < input.length(); i++) {
            int size = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) {
                files.put(diskId, new int[]{position, size});
                diskId++;
            } else {
                if (size > 0) {
                    freeSpace.add(new int[]{position, size});
                }
            }
            position += size;
        }

        for (diskId = files.size() - 1; diskId >= 0; diskId--) {
            int[] file = files.get(diskId);
            int pos = file[0];
            int size = file[1];
            int[] found = null;
            int foundIdx = -1;

            for (int idx = 0; idx < freeSpace.size(); idx++) {
                int[] space = freeSpace.get(idx);
                int start = space[0];
                int length = space[1];

                if (start >= pos) {
                    freeSpace = freeSpace.subList(0, idx);
                    found = null;
                    break;
                }
                if (size <= length) {
                    found = space;
                    foundIdx = idx;
                    break;
                }
            }

            if (found != null) {
                int start = found[0];
                int length = found[1];
                files.put(diskId, new int[]{start, size});

                if (size == length) {
                    freeSpace.remove(foundIdx);
                } else {
                    freeSpace.set(foundIdx, new int[]{start + size, length - size});
                }
            }
        }

        Long result = 0L;
        for (Map.Entry<Integer, int[]> entry : files.entrySet()) {
            diskId = entry.getKey();
            int[] file = entry.getValue();
            int pos = file[0];
            int size = file[1];
            for (int x = pos; x < pos + size; x++) {
                result += diskId * x;
            }
        }
        return result;








    }
}

public class Main {
    public static void main(String [] args) throws IOException {
        Input I = new Input();
        String input = I.getInput();
        System.out.println("The value is :-" + " " + new Solution1().part1(input));
        System.out.println("The value of second part is :-" + " " + new Solution2().part2(input));
    }
}
