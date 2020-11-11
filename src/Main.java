public class Main {

    public static void main(String[] args){

        Terminal t = new Terminal();
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
