import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp(){
        parser = new Parser();
    }

    @Test
    public void testParseOneArgument(){
        assertEquals(parser.parse("   mkdir folder1       "), true);
        assertEquals(parser.getCmd(), "mkdir");
        assertEquals(parser.getArgument().get(0), "folder1");
    }

    @Test
    public void testParseMultipleArgument(){
        assertEquals(parser.parse("   cp text.txt     folder1  "), true);
        assertEquals(parser.getCmd(), "cp");
        assertEquals(parser.getArgument().get(0), "text.txt");
        assertEquals(parser.getArgument().get(1), "folder1");
    }
}
