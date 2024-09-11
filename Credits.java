import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Credits{
    
    final static int TEMPO = 102;
    private static final double MILLISECONDS_PER_BEAT = 60000.0 / TEMPO; // 600 ms per beat
    private final static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    private static Scanner lyrics;
    private static double pause = 0;
    private static Scanner printLyrics;

    public static void roll() throws InterruptedException, FileNotFoundException{
        Sound portalSong = new Sound("portalsong.wav");
        portalSong.play();
        lyrics = new Scanner(new File("text2print.dat"));
        printLyrics = new Scanner(new File("text2print.dat"));
        printLyrics.nextLine();
        System.out.println("\033[H\033[2J");
        processLine(lyrics.nextLine());
        lyrics.close();
    }

    public static void processLine(String line){
        double restLength = checkRestLength(line);
        rest(restLength);
    }

    public static double checkRestLength(String line){
        boolean lineDot = true;
        while(lineDot){
            if(line.contains(".")){
                pause += 0.5;
                line = line.substring(0, line.indexOf(".")) + line.substring(line.indexOf(".") + 1);
            } else {
                lineDot = false;
                return pause;
            }
        }
        return 0;
    }

    
    public static void rest(double restLength) {
        long restTimeMillis = (long) (restLength * MILLISECONDS_PER_BEAT);
        if(lyrics.hasNextLine()){
            processLine(lyrics.nextLine());
        }
        executor.schedule(() -> {
            try {
                print();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, restTimeMillis, TimeUnit.MILLISECONDS);
    }

    // Clean up the executor when you're done
    public static void shutdown() {
        executor.shutdown();
    }

    public static void print() throws FileNotFoundException, InterruptedException{
        
        String toPrint = printLyrics.nextLine();
        int[] speed = checkSpeed(toPrint);
        boolean secondHalf = false;
        for(int i = 0; i < toPrint.length(); i++){
            if(toPrint.substring(i, i+1).equals("/")){
                System.out.print("\033[H\033[2J");
                break;
            }
            if(toPrint.substring(i, i+1).matches("[\\w()?,\\[\\]:' -]") && !secondHalf) {
                System.out.print(toPrint.charAt(i));
                Thread.sleep(speed[0]);
            } 
            if(secondHalf && toPrint.substring(i, i+1).matches("[\\w()?,\\[\\]:' -]")){
                System.out.print(toPrint.charAt(i));
                Thread.sleep(speed[1]);
            } else if (toPrint.substring(i, i+1).equals("*")){
                secondHalf = true;
            }
        }
        if(speed[0] == 300){
            Thread.sleep(7000);
            System.out.print("\033[H\033[2J");
            System.exit(0);
        }
        System.out.println();
    }

    public static int[] checkSpeed(String toPrint) {
        toPrint = toPrint.replaceAll("[\\w()?,\\[\\]:' -]", "");
        toPrint = toPrint.replaceAll("\\.", "");
        int[] speed = new int[2];
        if(toPrint.length() == 0){
            return new int[]{65, 65};
        }
        if(toPrint.length() == 3){
            for(int i = 1; i < 3; i ++){
                switch(toPrint.charAt(i)){
                    case '!': speed[i-1] = 65; 
                    break;
                    case '@': speed[i-1] = 85; 
                    break;
                    case '#': speed[i-1] = 100; 
                    break;
                    case '$': speed[i-1] = 150;
                    break;
                    case '%': speed[i-1] = 200;
                    break;
                    case '^': speed[i-1] = 250;
                    break;
                    case '&': speed[i-1] = 300;
                    break; 
                    case '<': speed[i-1] = 0;
                    break;
                }
            }
        }
        if(toPrint.length() == 1){
            switch(toPrint.charAt(0)){
                case '!': speed[0] = 65; 
                break;
                case '@': speed[0] = 85; 
                break;
                case '#': speed[0] = 100; 
                break;
                case '$': speed[0] = 150;
                break;
                case '%': speed[0] = 200;
                break;
                case '^': speed[0] = 250;
                break;
                case '&': speed[0] = 300;
                break; 
                case '<': speed[0] = 0;
                break;
            }
        }
        return speed;
    }
}