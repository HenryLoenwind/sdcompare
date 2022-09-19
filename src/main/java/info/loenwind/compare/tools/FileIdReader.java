package info.loenwind.compare.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileIdReader {

  public static void read(Settings settings, File file) {
    try {
      read(settings, file.getParentFile(), Files.readAllLines(file.toPath()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  protected static void read(Settings settings, File folder, List<String> lines) {
    File current = null;
    for (String line : lines) {
      if (line.startsWith(" ")) {
        if (current != null) {
          settings.addFileID(current, line.replaceFirst("^\\s+", ""));
        }
      } else {
        if (line.startsWith("\"")) {
          String[] split = line.split("\"", 3);
          if (split.length >= 3) {
            current = new File(folder, split[1]);
            settings.addFileID(current, split[2].replaceFirst("^\\s+", ""));
          }
        } else {
          String[] split = line.split("\\s+", 2);
          if (split.length >= 2) {
            current = new File(folder, split[0]);
            settings.addFileID(current, split[1]);
          }
        }
      }
    }
  }

}
