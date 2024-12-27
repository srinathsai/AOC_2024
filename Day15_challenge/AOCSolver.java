

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AOCSolver {

    protected AOCSolver(final String filename)  throws IOException{
        final List<String> input = getStringList(filename);

        Instant start = Instant.now();
        final String output1 = runPart1(input);
        Instant end = Instant.now();
        System.out.println("Answer to part 1: " + output1);
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");

        start = Instant.now();
        final String output2 = runPart2(input);
        end = Instant.now();
        System.out.println("Answer to part 2: " + output2);
        System.out.println("Runtime: " + Duration.between(start, end).toMillis() + " ms.");
    }

    protected abstract String runPart2(List<String> input);

    protected abstract String runPart1(List<String> input);

    private List<String> getStringList(final String filename) throws IOException{
        List<String> lines = new ArrayList<>();
        InputStream is = new FileInputStream(filename);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        return lines;

    }

    protected List<Integer> convertToIntList(final List<String> input) {
        return input.stream().map(Integer::parseInt).collect(Collectors.toList());
    }

    protected List<Long> convertToLongList(final List<String> input) {
        return input.stream().map(Long::parseLong).collect(Collectors.toList());
    }
}