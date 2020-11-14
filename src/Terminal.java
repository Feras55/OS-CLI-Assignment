import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Terminal {
    Path currentPath;

    public Terminal() {
        currentPath = Paths.get("D:\\");
    }

    // EFFECTS: returns a file pointing to the given path.
    private File makeFile(String destinationPath) {
        File file = new File(destinationPath);
        if (!file.isAbsolute()) {
            String curPath = getCurrentPath().toString();
            file = new File(curPath, destinationPath);
        }
        return file;
    }

    public Path getCurrentPath() {
        return currentPath;
    }

    // REQUIRES: Path must be valid and directory can't be already exists
    // EFFECTS: creates a new directory at the given path.
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

    // EFFECTS: clears the terminal.
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

    // REQUIRES: Path must be valid and must be a directory
    // EFFECTS: removes the the directory at the given path if its empty. returns true if successful else false.
    public boolean rmdir(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.exists())
            throw new Exception(String.format("failed to remove '%s': No such file or directory", destinationPath));
        if (!file.isDirectory())
            throw new Exception(String.format("failed to remove '%s': Not a directory", destinationPath));

        try {
            if (!file.delete())
                return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    // REQUIRES: Path must be valid
    // EFFECTS: creates a new file at the given path.
    public boolean touch(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.getParentFile().exists())
            throw new Exception(String.format("cannot touch '%s': No such file or directory", destinationPath));

        try {
            if (!file.createNewFile()) return false;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    // REQUIRES: Path must be valid
    // EFFECTS: returns a list of files and folders inside the current path.
    public File[] ls() {
        File file = new File(getCurrentPath().toString());
        return file.listFiles();
    }

    // REQUIRES: Path must be valid
    // EFFECTS: returns a list of files and folders inside the given path.
    public File[] ls(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.exists())
            throw new Exception(String.format("cannot access '%s': No such file or directory", destinationPath));
        if (!file.isDirectory()){
            File[] files = new File[1];
            files[0] = file;
            return files;
        }
        return file.listFiles();
    }

    // REQUIRES: Path must be valid and must point to a directory.
    // MODIFIES: this
    // EFFECTS: Moves the current path according to the given path.
    public boolean cd(String destinationSubDirectory) throws Exception {
        File file = makeFile(destinationSubDirectory);
        if (destinationSubDirectory.length() == 0 || !file.exists())
            throw new Exception(String.format("%s: No such file or directory", destinationSubDirectory));
        if (!file.isDirectory())
            throw new Exception(String.format("%s: Not a directory", destinationSubDirectory));

        currentPath = Paths.get(file.getAbsolutePath()).normalize();
        return true;
    }

    // EFFECTS: Prints the current working directory.
    public String pwd() {
        return getCurrentPath().toString();
    }

    // REQUIRES: The destination path must be valid and must point to a file and not a directory.
    // EFFECTS: Writes the given content to a file and appends it if append is true else it overwrites.
    public boolean outputRedirect(String destinationPath, List<String> content, boolean append) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.getParentFile().exists())
            throw new Exception(String.format("%s: No such file or directory", destinationPath));
        if (file.isDirectory())
            throw new Exception(String.format("%s: Is a directory", destinationPath));

        if (!append) file.delete();
        file.createNewFile();
        for (int i = 0; i < content.size(); ++i) {
            Files.writeString(Paths.get(destinationPath), content.get(i) + System.lineSeparator(),
                    StandardOpenOption.APPEND);
        }
        return true;
    }

    // REQUIRES: The destination path must be valid and must point to a file and not a directory.
    // EFFECTS: Reads the given file and returns a list of all its lines.
    public List<String> inputRedirect(String destinationPath) throws Exception {
        File file = makeFile(destinationPath);
        if (destinationPath.length() == 0 || !file.exists())
            throw new Exception(String.format("%s: No such file or directory", destinationPath));
        if (file.isDirectory())
            throw new Exception(String.format("%s: Is a directory", destinationPath));

        List<String> lines = Files.readAllLines(Paths.get(destinationPath));
        return lines;
    }

    public static void main(String[] args) throws Exception {
        // use cases for outputRedirect & inputRedirect
        Terminal terminal = new Terminal();
        File[] files = terminal.ls("D:\\programs");
        List<String> strings = new ArrayList<>();
        for (File file : files) strings.add(file.toString());
        terminal.outputRedirect("D:\\demo\\text.txt", strings, true);
        terminal.inputRedirect("D:\\demo\\text.txt").forEach(line -> System.out.println(line));
    }
}

