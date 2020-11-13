import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Terminal {
    Path currentPath;

    public Terminal() {
        currentPath = Paths.get("D:\\");
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    private File makeFile(String destinationPath) {
        File file = new File(destinationPath);
        if (!file.isAbsolute()) {
            String curPath = getCurrentPath().toString();
            file = new File(curPath, destinationPath);
        }
        return file;
    }

    public boolean mkdir(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);

        if (destinationPath.length() == 0 || !file.getParentFile().exists())
            throw new Exception(String.format("cannot create directory ‘%s’: No such file or directory", destinationPath));
        if (file.exists())
            throw new Exception(String.format("cannot create directory ‘%s’: File exists", destinationPath));

        try {
            if (!file.mkdir()) return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033\143");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static void deleteFolder(File file) {
        for (File sub : file.listFiles()) {
            if (sub.isDirectory()) {
                deleteFolder(sub);
            } else {
                sub.delete();
            }
        }
        file.delete();
    }

    public boolean rmdir(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.exists())
            throw new Exception(String.format("failed to remove '%s': No such file or directory", destinationPath));
        if (!file.isDirectory())
            throw new Exception(String.format("failed to remove '%s': Not a directory", destinationPath));

        try {
            deleteFolder(file);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean touch(String destinationPath) {
        File file = makeFile(destinationPath);
        if (!file.getParentFile().exists() || file.isDirectory()) return false;
        try {
            if (!file.createNewFile()) return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    public File[] ls() {
        File file = new File(getCurrentPath().toString());
        return file.listFiles();
    }

    public File[] ls(String destinationPath) {
        File file = makeFile(destinationPath);
        return file.listFiles();
    }

    public boolean cd(String destinationSubDirectory) {
        File file = makeFile(destinationSubDirectory);
        currentPath = Paths.get(file.getAbsolutePath()).normalize();
        return true;
    }

    public String pwd() {
        return getCurrentPath().toString();
    }

    public static void main(String args[]){
        Terminal terminal = new Terminal();
        System.out.println(terminal.cd("./downloads/books/../../games"));
    }
}


