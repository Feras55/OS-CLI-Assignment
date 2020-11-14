import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TerminalTest {
    private Terminal terminal;
    private File file;

    @BeforeEach
    public void setup(){
        terminal = new Terminal();
        // Creating files and directories used during testing
        try {
            file = new File("D:\\dir1\\sub-dir1"); file.mkdirs();
            file = new File("D:\\dir1\\sub-dir1\\file.txt"); file.createNewFile();
            file = new File("D:\\dir1\\sub-dir2"); file.mkdirs();
            file = new File("D:\\dir1\\sub-dir2\\file.txt"); file.createNewFile();
            file = new File("D:\\dir2\\sub-dir1"); file.mkdirs();
            file = new File("D:\\dir2\\sub-dir1\\file.txt"); file.createNewFile();
            file = new File("D:\\dir2\\sub-dir2"); file.mkdirs();
            file = new File("D:\\dir2\\sub-dir2\\file.txt"); file.createNewFile();
            file = new File("D:\\dir"); file.mkdirs();
            file = new File("D:\\file.txt"); file.createNewFile();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testMkdirCurrentDir(){
        // tests making the new directory in the current directory given its name only
        String dir = "new-dir";
        try {
            terminal.mkdir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirRelativePath(){
        // tests making the new directory given a relative path
        String dirPath = "./dir1/sub-dir1/new-dir";
        try {
            terminal.mkdir(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\dir1\\sub-dir1\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirAbsolutePath(){
        // tests making the new direcotry given an absolute path
        String dirPath = "D:\\dir1\\sub-dir2\\new-dir";
        try {
            terminal.mkdir(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\dir1\\sub-dir2\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirPathNotExist() {
        String dirPath = "";
        Exception exception = assertThrows(Exception.class, () -> {
            terminal.mkdir(dirPath);
        });

        String expectedMessage = "cannot create directory ‘’: No such file or directory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testMkdirAlreadyExists() {
        String dirPath = "./dir1";
        Exception exception = assertThrows(Exception.class, () -> {
            terminal.mkdir(dirPath);
        });

        String expectedMessage = "cannot create directory ‘./dir1’: File exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRmdirCurrentDir(){
        // tests deleting directory in the current directory given its name only
        String dir = "dir";
        try {
            terminal.rmdir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\dir");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirRelativePath(){
        // tests deleting directory given a relative path
        String dirPath = "./dir1/sub-dir1/";
        try {
            terminal.rmdir(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\dir1\\sub-dir1");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirAbsolutePath(){
        // tests deleting directory given an absolute path
        String dirPath = "D:\\dir1\\sub-dir2";
        try {
            terminal.rmdir(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        file = new File("D:\\dir1\\sub-dir2");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirPathNotExist() {
        // test removing a directory which doesn't exist
        String dirPath = "";
        Exception exception = assertThrows(Exception.class, () -> {
            terminal.rmdir(dirPath);
        });

        String expectedMessage = "failed to remove '': No such file or directory";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testRmdirNotDir() {
        String dirPath = "./dir1/sub-dir1/file.txt";
        Exception exception = assertThrows(Exception.class, () -> {
            terminal.rmdir(dirPath);
        });

        String expectedMessage = "failed to remove './dir1/sub-dir1/file.txt': Not a directory";
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTouch(){
        // tests creating a new file
        String dirPath1 = "./dir1/sub-dir1/new-file.txt";
        String dirPath2 = "D:\\new-file.txt";
        String dirPath3 = "D:\\none\\file.txt";
        assertTrue(terminal.touch(dirPath1));
        assertTrue(terminal.touch(dirPath2));
        assertFalse(terminal.touch(dirPath3));
        file = new File("D:\\dir1\\sub-dir1\\new-file.txt");
        assertTrue(file.exists());
        file.delete();
        file = new File(dirPath2);
        assertTrue(file.exists());
        file.delete();
        file = new File(dirPath3);
        assertFalse(file.exists());
    }

    @Test
    public void testCd(){
        assertEquals(terminal.getCurrentPath().toString(), "D:\\");
        terminal.cd("D:\\dir1\\sub-dir1");
        assertEquals(terminal.getCurrentPath().toString(), "D:\\dir1\\sub-dir1");
        terminal.cd("..\\sub-dir2");
        assertEquals(terminal.getCurrentPath().toString(), "D:\\dir1\\sub-dir2");
        terminal.cd("../../dir2/sub-dir1");
        assertEquals(terminal.getCurrentPath().toString(), "D:\\dir2\\sub-dir1");
    }

    @Test
    public void testPwd(){
        assertEquals(terminal.pwd(), "D:\\");
        terminal.cd("dir1/sub-dir2");
        assertEquals(terminal.pwd(), "D:\\dir1\\sub-dir2");
        terminal.cd("..\\..\\dir2");
        assertEquals(terminal.pwd(), "D:\\dir2");
    }

    @Test
    public void testLs(){
        terminal.cd("dir1");
        File file1 = new File("D:\\dir1\\sub-dir1"), file2 = new File("D:\\dir1\\sub-dir2");
        File[] files = {file1 , file2};
        for (int i = 0; i < 2; ++i){
            assertEquals(files[i].toString(), terminal.ls()[i].toString());
        }
    }

    @Test
    public void testLsDifferentDir(){
        File file1 = new File("D:\\dir1\\sub-dir1"), file2 = new File("D:\\dir1\\sub-dir2");
        File[] files = {file1 , file2};
        for (int i = 0; i < 2; ++i){
            assertEquals(files[i].toString(), terminal.ls("D:\\dir1")[i].toString());
        }
    }

    @AfterEach
    public void teardown(){
        // Deleting files and directories used during testing
        file = new File("D:\\dir1\\sub-dir1\\file.txt"); file.delete();
        file = new File("D:\\dir1\\sub-dir1"); file.delete();
        file = new File("D:\\dir1\\sub-dir2\\file.txt"); file.delete();
        file = new File("D:\\dir1\\sub-dir2"); file.delete();
        file = new File("D:\\dir1"); file.delete();
        file = new File("D:\\dir2\\sub-dir1\\file.txt"); file.delete();
        file = new File("D:\\dir2\\sub-dir1"); file.delete();
        file = new File("D:\\dir2\\sub-dir2\\file.txt"); file.delete();
        file = new File("D:\\dir2\\sub-dir2"); file.delete();
        file = new File("D:\\dir2"); file.delete();
        file = new File("D:\\dir"); file.delete();
        file = new File("D:\\file.txt"); file.delete();
    }
}
