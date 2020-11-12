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

    public boolean ls(){
        File currentDirectory = new File(currentPath);
        String[] directories = currentDirectory.list();
        for (String sub: directories){
            System.out.println(sub);
        }
        return  true;

    }

    public boolean ls(String destinationPath){
        return false;
    }

    public static void main(String[] args){
        Terminal t = new Terminal();
        t.mkdir("D:\\demo1\\");
        t.mkdir("demo1\\demo2\\");
        t.mkdir("demo1\\demo3");
        t.ls();
        t.touch("demo1\\demo2\\demofile1.txt");

        t.ls();
        t.ls();
        t.ls("demo1");
    }
}
