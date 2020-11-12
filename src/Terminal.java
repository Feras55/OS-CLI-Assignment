import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
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
        if(destinationSubDirectory.equals("..")) {
            int numberOfDirectoriesInPath = paths.size();
            if (numberOfDirectoriesInPath > 1) {
                paths.remove(numberOfDirectoriesInPath-1);
            }
            return true;
        }

            File dir = new File(destinationSubDirectory);
        if(dir.isAbsolute()){
                if(dir.exists())
                {
                        paths.clear();
                        String[] arr = destinationSubDirectory.split(Pattern.quote("\\"));
                        for (String sub : arr) {
                            paths.add(sub);
                    }


                }else{
                System.out.println("No such file or directory");
                return  false;
            }

        }else{
            String curPath = pathGenerator();
            curPath+=destinationSubDirectory;
            dir = new File(curPath);
                if(dir.exists()) {
                    paths.add(destinationSubDirectory);
                }

                else{
                System.out.println("No such file or directory");
                return  false;
                }

        }

        return  true;
    }
    public void pwd(){
        String curPath;
        curPath = pathGenerator();
        if(paths.size()!=1)
        {
            System.out.println(curPath.substring(0,curPath.length()-1));
        }
        else{

            System.out.println(curPath);
        }
    }

    public boolean ls(String destinationPath){
        return false;
    }

    public static void main(String[] args){

}
