package info.loenwind.compare.tools;

import java.nio.file.Paths;
import java.util.Collections;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileFinderTest {

  @Test
  public final void testFileFinderPath() {
    FileFinder finder = new FileFinder(Paths.get("src/test/resources", "test.txt"));
    assertEquals("2 matches", 2, finder.getMatches().size());
    Collections.sort(finder.getMatches());
    assertEquals("Found xml neighbour", Paths.get("src/test/resources", "test.xml"), finder.getMatches().get(0));
    assertEquals("Found yml neighbor", Paths.get("src/test/resources", "test.yaml"), finder.getMatches().get(1));
  }

}
