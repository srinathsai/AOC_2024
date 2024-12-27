import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Robot{
    private int x;
    private int y;
    private final int velX;
    private final int velY;

    public Robot(final int x, final int y, final int velX, final int velY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }

    public void move(final int sizeX, final int sizeY) {
        final int newX = this.x + this.velX;
        final int newY = this.y + this.velY;
        this.x = newX >= sizeX ? newX % sizeX : newX < 0 ? sizeX + newX : newX;
        this.y = newY >= sizeY ? newY % sizeY : newY < 0 ? sizeY + newY : newY;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

class Input{
    public List<String> getInput() throws IOException{
        String path = "C:\\Users\\Srinath Sai\\Downloads\\Day14_challenge\\.idea\\input14.txt";
        InputStream is = new FileInputStream(path);
        Scanner sc = new Scanner(is, StandardCharsets.UTF_8.name());
        List<String> lines = new ArrayList<>();
        while(sc.hasNextLine()){
            lines.add(sc.nextLine());
        }
        return lines;
    }
}

class Solution{
    private List<Robot> parseRobots(final List<String> input) {
        final List<Robot> robots = new ArrayList<>();
        for (final String line : input) {
            final int x = Integer.parseInt(line.split("=")[1].split(",")[0]);
            final int y = Integer.parseInt(line.split("=")[1].split(",")[1].split(" ")[0]);
            final int velX = Integer.parseInt(line.split("v=")[1].split(",")[0]);
            final int velY = Integer.parseInt(line.split("v=")[1].split(",")[1]);
            robots.add(new Robot(x, y, velX, velY));
        }
        return robots;
    }

    private void moveRobots(final List<Robot> robots, final int sizeX, final int sizeY, final int times) {
        for (int i = 1; i <= times; i++) {
            for (final Robot robot : robots) {
                robot.move(sizeX, sizeY);
            }
        }
    }

    private long getSafetyFactor(final List<Robot> robots, final int sizeX, final int sizeY) {
        final int middleX = sizeX / 2;
        final int middleY = sizeY / 2;
        final long q1 = robots.stream().filter(r -> r.getX() < middleX && r.getY() < middleY).count();
        final long q2 = robots.stream().filter(r -> r.getX() > middleX && r.getY() < middleY).count();
        final long q3 = robots.stream().filter(r -> r.getX() < middleX && r.getY() > middleY).count();
        final long q4 = robots.stream().filter(r -> r.getX() > middleX && r.getY() > middleY).count();
        return q1 * q2 * q3 * q4;
    }

    private boolean hasHorizontalLine(final List<Robot> robots) {
        final int lineSize = 10;
        for (final Robot robot : robots) {
            final int x = robot.getX();
            for (int i = 1; i <= lineSize; i++) {
                if (!robots.contains(new Robot(x + i, robot.getY(), 0, 0))) {
                    break;
                } else if (i == lineSize) {
                    return true;
                }
            }
        }
        return false;
    }


    public String runPart2(final List<String> input) {

        final List<Robot> robots = parseRobots(input);
        final int sizeX = 101;
        final int sizeY = 103;
        int seconds = 1;
        while (true) {
            moveRobots(robots, sizeX, sizeY, 1);
            if (hasHorizontalLine(robots)) {
                break;
            }
            seconds++;

        }
        return String.valueOf(seconds);
    }


    public String runPart1(final List<String> input) {
        final List<Robot> robots = parseRobots(input);
        final int sizeX = 101;
        final int sizeY = 103;
        moveRobots(robots, sizeX, sizeY, 100);
        return String.valueOf(getSafetyFactor(robots, sizeX, sizeY));
    }
}











public class Main {
    public static void main(String[] args) throws IOException{
        Input I = new Input();
        Solution S = new Solution();
        System.out.println("The part1 value is :-" + " " + S.runPart1(I.getInput()));
        System.out.println("The part2 value is :-" + " " + S.runPart2(I.getInput()));
    }
}
