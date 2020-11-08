import java.io.File;

public class Terminal {
    String currentPath = "D:\\";

    private File makeFile(String destinationPath){
        File file = new File(destinationPath);
        if (!file.isAbsolute()){
            file = new File(currentPath, destinationPath);
        }
        return file;
    }

    public boolean mkdir(String destinationPath){
        File file = makeFile(destinationPath);

        try {
            if (!file.mkdir()) return false;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    static void deleteFolder(File file){
        for (File sub : file.listFiles()) {
            if(sub.isDirectory()) {
                deleteFolder(sub);
            } else {
                sub.delete();
            }
        }
        file.delete();
    }
    public boolean rmdir(String destinationPath){
        File file = makeFile(destinationPath);

        try{
            deleteFolder(file);
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean touch(String destinationPath){
        File file = makeFile(destinationPath);

        try {
            if (!file.createNewFile()) return false;
        }
        catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        Terminal t = new Terminal();
        System.out.println(t.mkdir("D:\\demo1\\"));
        System.out.println(t.mkdir("demo1\\demo2\\"));
        System.out.println(t.mkdir("demo1\\demo3"));
        System.out.println(t.touch("demo1\\demo2\\demofile1.txt"));
    }
}
