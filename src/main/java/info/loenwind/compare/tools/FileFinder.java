package info.loenwind.compare.tools;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.CONTINUE;

public class FileFinder extends SimpleFileVisitor<Path> {

  private final PathMatcher matcher;
  private final String ignore;
  private final List<Path> matches = new ArrayList<>();

  @SuppressWarnings("resource")
  public FileFinder(Path startingDir, String pattern) {
    matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    ignore = "";
    try {
      Files.walkFileTree(startingDir, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings("resource")
  public FileFinder(Path startingFile) {
    Path fileName = startingFile.getFileName();
    Path startingDir = startingFile.getParent();
    ignore = fileName.toString();
    String pattern = ignore.replaceFirst("\\.[^.]+$", ".*");
    matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    try {
      Files.walkFileTree(startingDir, this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void find(Path file) {
    Path name = file.getFileName();
    if (name != null && matcher.matches(name) && !ignore.equals(name.toString())) {
      matches.add(file);
    }
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
    find(file);
    return CONTINUE;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    return CONTINUE;
  }

  public List<Path> getMatches() {
    return matches;
  }

}