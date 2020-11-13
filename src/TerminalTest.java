import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerminalTest {
    private Terminal terminal;
    private File file;

    @Before
    public void setup(){
        terminal = new Terminal();
        // Creating files and directories used during testing
        try {
            file = new File("D:\\dir1\\sub-dir1"); file.mkdirs();
            file = new File("D:\\dir1\\sub-dir1\\file.txt"); file.createNewFile();
            file = new File("D:\\dir1\\sub-dir1\\file2.txt"); file.createNewFile();
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
        terminal.mkdir(dir);
        file = new File("D:\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirRelativePath(){
        // tests making the new directory given a relative path
        String dirPath = "./dir1/sub-dir1/new-dir";
        terminal.mkdir(dirPath);
        file = new File("D:\\dir1\\sub-dir1\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirAbsolutePath(){
        // tests making the new direcotry given an absolute path
        String dirPath = "D:\\dir1\\sub-dir2\\new-dir";
        terminal.mkdir(dirPath);
        file = new File("D:\\dir1\\sub-dir2\\new-dir");
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void testMkdirPathNotExist(){
        // test making a directory with invalid path
        String dirPath = "./none/new-dir";
        assertFalse(terminal.mkdir(dirPath));
        file = new File("D:\\none\\new-dir");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirCurrentDir(){
        // tests deleting directory in the current directory given its name only
        String dir = "dir";
        terminal.rmdir(dir);
        file = new File("D:\\dir");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirRelativePath(){
        // tests deleting directory given a relative path
        String dirPath = "./dir1/sub-dir1/";
        terminal.rmdir(dirPath);
        file = new File("D:\\dir1\\sub-dir1");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirAbsolutePath(){
        // tests deleting directory given an absolute path
        String dirPath = "D:\\dir1\\sub-dir2";
        terminal.rmdir(dirPath);
        file = new File("D:\\dir1\\sub-dir2");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirPathNotExist(){
        // test removing a directory which doesn't exist
        String dirPath = "./none";
        assertFalse(terminal.rmdir(dirPath));
        file = new File("D:\\none");
        assertFalse(file.exists());
    }

    @Test
    public void testRmdirNotDir(){
        // test removing a directory which doesn't exist
        String dirPath = "./dir1/sub-dir1/file.txt";
        assertFalse(terminal.rmdir(dirPath));
        file = new File("D:\\dir1\\sub-dir1\\file.txt");
        assertTrue(file.exists());
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

    @Test
    public  void testCat() throws IOException {
        String Content1 = "This is the first file\n";
        String Content2 = "This is the second file\n";
        ArrayList<String>ans = new ArrayList<>();
        ans.add(Content1);
        ans.add(Content2);
        Path path1 = Path.of("D:\\dir1\\sub-dir1\\file.txt");
        Path path2 = Path.of("D:\\dir1\\sub-dir1\\file2.txt");
        Files.writeString(path1,Content1);
        Files.writeString(path2,Content2);
        ArrayList<String> files = new ArrayList<>();
        files.add("D:\\dir1\\sub-dir1\\file.txt");
        files.add("D:\\dir1\\sub-dir1\\file2.txt");
        for (int i = 0; i < 2; i++) {

            assertEquals(ans.get(i), terminal.cat(files).get(i));
        }
    }

    @After
    public void teardown(){
        // Deleting files and directories used during testing
        file = new File("D:\\dir1\\sub-dir1\\file.txt"); file.delete();
        file = new File("D:\\dir1\\sub-dir1\\file2.txt"); file.delete();
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
