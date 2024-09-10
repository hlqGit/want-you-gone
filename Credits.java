import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Credits {
    public static void roll() throws InterruptedException, FileNotFoundException{
        
        System.out.println("\033[H\033[2J");
        Sound portal = new Sound("portalsong.wav");
        portal.play();
        
        Scanner lyrics = new Scanner(new File("text2print.dat"));
        while(lyrics.hasNextLine()){
            type(lyrics.nextLine());
        }
        
        lyrics.close();
    }

    public static void type(String line) throws InterruptedException{
        int pause = checkPause(line);
        int speed = checkSpeed(line);
        
        switch (speed) {
            case 0:
            for(int i = 0; i < line.length(); i++){
                System.out.print(line.charAt(i));
                Thread.sleep(85);
            }
            System.out.print("\n");
            break;

            case 1:
            for(int i = 0; i < line.length(); i++){
                if( (line.charAt(i) >= 65 && line.charAt(i) <= 90) || (line.charAt(i) >= 97 && line.charAt(i) <= 122) 
                || line.charAt(i) == 32 || line.charAt(i) == 63 || line.charAt(i) == 39) {
                    System.out.print(line.charAt(i));
                    Thread.sleep(85);
                }
            }
            System.out.print("\n");
            break;

            case 2:
            for(int i = 0; i < line.length(); i++){
                if( (line.charAt(i) >= 65 && line.charAt(i) <= 90) || (line.charAt(i) >= 97 && line.charAt(i) <= 122) 
                || line.charAt(i) == 32 || line.charAt(i) == 63 || line.charAt(i) == 39) {
                    System.out.print(line.charAt(i));
                    Thread.sleep(100);
                }
            }
            System.out.print("\n");
            break;

            case 3:
            for(int i = 0; i < line.length(); i++){
                if( (line.charAt(i) >= 65 && line.charAt(i) <= 90) || (line.charAt(i) >= 97 && line.charAt(i) <= 122) 
                || line.charAt(i) == 32 || line.charAt(i) == 63 || line.charAt(i) == 39 || line.charAt(i) == 91 || line.charAt(i) == 93) {
                    System.out.print(line.charAt(i));
                    Thread.sleep(150);
                }
            }
            System.out.print("\n");
            break;

            case 4:
            for(int i = 0; i < line.length(); i++){
                if( (line.charAt(i) >= 65 && line.charAt(i) <= 90) || (line.charAt(i) >= 97 && line.charAt(i) <= 122) 
                || line.charAt(i) == 32 || line.charAt(i) == 63 || line.charAt(i) == 39 || line.charAt(i) == 40 || line.charAt(i) == 41
                || line.charAt(i) == 44){
                    System.out.print(line.charAt(i));
                    Thread.sleep(65);
                }
            }
            System.out.print("\n");
            break;

            case 5:
            break;

            case 6:
            System.out.println("\033[H\033[2J");
            break;

            case 7:
            System.out.println(line.substring(0, line.length()-1));
            break;
        }
        
        switch(pause){
            case 1: Thread.sleep(500);
            break;
            case 2: Thread.sleep(1000);
            break;
            case 3: Thread.sleep(1300);
            break;
            case 4: Thread.sleep(1500); 
            break;
        }
    }

    public static int checkPause(String line){
        boolean lineDot = true;
        int pause = 0;
        while(lineDot){
            if(line.contains(".")){
                pause++;
                line = line.substring(0, line.indexOf(".")) + line.substring(line.indexOf(".") + 1);
            } else {
                lineDot = false;
                return pause;
            }
        }
        return 0;
    }

    public static int checkSpeed(String line){
        if(line.matches(".*\\d.*")){
            line = line.replaceAll("[^\\d]", "");
            if(line.length() > 1){
                return 0;
            }
            if(line.substring(0, 1).matches(".*\\d.*")){
                return Integer.parseInt(line);
            }
        }
        return 1;
    }
}
