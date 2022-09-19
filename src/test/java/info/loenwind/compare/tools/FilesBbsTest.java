package info.loenwind.compare.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilesBbsTest {

  /**
   * Test method for {@link info.loenwind.compare.tools.FileIdReader#read(info.loenwind.compare.tools.Settings, java.io.File, java.util.List)}.
   */
  @Test
  public void testReadSettingsFileListOfString() {
    Settings settings = new Settings();
    List<String> lines = new ArrayList<>();
    lines.add("        a header line to be ignored");
    lines.add("GORILLA.BAS 3859 1985-07-12 A boring game");
    lines.add("gorilla.zip 5232546115846513 2030-05-18 Some tripple-A title");
    lines.add("another.file this");
    lines.add("             one");
    lines.add("             has");
    lines.add("             a multi-line description");
    lines.add("image.png    and this one doesn't");
    lines.add("\"a modern filename.text\" spaces in file names, yeah!");
    lines.add("                           I could do without");
    FileIdReader.read(settings, new File("/folder"), lines);
    Map<File, List<String>> ids = settings.getFileIds();

    assertEquals("number of entries", 5, ids.size());

    assertTrue("entry for GORILLA.BAS exists", ids.containsKey(new File("/folder/GORILLA.BAS")));
    assertTrue("entry for gorilla.zip exists", ids.containsKey(new File("/folder/gorilla.zip")));
    assertTrue("entry for another.file exists", ids.containsKey(new File("/folder/another.file")));
    assertTrue("entry for image.png exists", ids.containsKey(new File("/folder/image.png")));
    assertTrue("entry for a modern filename.text exists", ids.containsKey(new File("/folder/a modern filename.text")));

    assertEquals("number of lines for GORILLA.BAS", 1, ids.get(new File("/folder/GORILLA.BAS")).size());
    assertEquals("number of lines for gorilla.zip", 1, ids.get(new File("/folder/gorilla.zip")).size());
    assertEquals("number of lines for another.file", 4, ids.get(new File("/folder/another.file")).size());
    assertEquals("number of lines for image.png", 1, ids.get(new File("/folder/image.png")).size());
    assertEquals("number of lines for a modern filename.text", 2, ids.get(new File("/folder/a modern filename.text")).size());

    assertEquals("lines 1 for GORILLA.BAS", "3859 1985-07-12 A boring game", ids.get(new File("/folder/GORILLA.BAS")).get(0));
    assertEquals("lines 1 for gorilla.zip", "5232546115846513 2030-05-18 Some tripple-A title", ids.get(new File("/folder/gorilla.zip")).get(0));
    assertEquals("lines 1 for another.file", "this", ids.get(new File("/folder/another.file")).get(0));
    assertEquals("lines 1 for image.png", "and this one doesn't", ids.get(new File("/folder/image.png")).get(0));
    assertEquals("lines 1 for a modern filename.text", "spaces in file names, yeah!", ids.get(new File("/folder/a modern filename.text")).get(0));

    assertEquals("lines 2 for another.file", "one", ids.get(new File("/folder/another.file")).get(1));
    assertEquals("lines 2 for a modern filename.text", "I could do without", ids.get(new File("/folder/a modern filename.text")).get(1));

    assertEquals("lines 3 for another.file", "has", ids.get(new File("/folder/another.file")).get(2));
    assertEquals("lines 4 for another.file", "a multi-line description", ids.get(new File("/folder/another.file")).get(3));
  }

}
