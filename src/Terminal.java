import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Terminal {
    ArrayList<String> paths = new ArrayList<String>();

    public Terminal() {
        paths.add("D:");
    }

    private String pathGenerator(){
        String curPath = "";
        for (String dir: paths) {
            curPath+=(dir + '\\');

        }
        return  curPath;
    }
    private File makeFile(String destinationPath){
        File file = new File(destinationPath);
        if (!file.isAbsolute()){
            String curPath = pathGenerator();
            file = new File(curPath, destinationPath);
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

    public void ls(){
        String curPath = pathGenerator();
        File currentDirectory = new File(curPath);
        String[] directories = currentDirectory.list();
        for (String sub: directories){
            System.out.println(sub);
        }
        return;

    }

    public boolean cd(String destinationSubDirectory)
    {
        if(destinationSubDirectory.equals(".."))
        {
            int numberOfDirectoriesInPath = paths.size();
            if(numberOfDirectoriesInPath>1){
                paths.remove(numberOfDirectoriesInPath);
            }

            return  true;
        }
        File dir = new File(destinationSubDirectory);
        try {
            if(dir.exists()) {

                if (dir.isAbsolute()) {
                    paths.clear();
                }
                String[] arr = destinationSubDirectory.split(Pattern.quote("//"));
                for (String sub :
                        arr) {
                    paths.add(sub);

                }

            }
            return  true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }
    }

    public static void main(String[] args){
        Terminal t = new Terminal();
        t.mkdir("D:\\demo1\\");
        t.mkdir("demo1\\demo2\\");
        t.mkdir("demo1\\demo3");
        t.ls();
        System.out.println("----------------");
        t.cd("demo1");
        t.ls();
        System.out.println("----------------");
        t.cd("..");
        t.ls();

//        t.touch("demo2\\demofile1.txt");
    }
}
