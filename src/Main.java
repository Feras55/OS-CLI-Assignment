import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Terminal t = new Terminal();
        boolean end = false;
        while(true){
            Scanner s = new Scanner(System.in);
            String cmd = s.next();
            if(cmd.equals("0"))
                return;
            else if(cmd.equals("clear")) {
                t.clear();
                continue;
            }
            else{

                t.mkdir("D:\\demo1\\");
                t.mkdir("demo1\\demo2\\");
                t.mkdir("demo1\\demo3");
                t.pwd();
                System.out.println("----------------");
                t.cd("demo1");
                t.pwd();
                t.cd("demo2");
                t.pwd();
                System.out.println("----------------");
                t.cd("..");
                t.pwd();
                System.out.println("----------------");
                t.cd("..");
                t.pwd();
                t.cd("demo1");
                t.touch("demo2\\demofile1.txt");
            }
        }
    }
}
