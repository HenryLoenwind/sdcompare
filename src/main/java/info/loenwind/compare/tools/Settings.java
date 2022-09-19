package info.loenwind.compare.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Settings {

  private File sourceFolder, targetFolder, trashFolder;
  private boolean targetFolderValid, trashFolderValid, autoTrashingEnabled, suddenDeathEnabled;
  private final Map<File, List<String>> fileIds = new HashMap<>();

  public File getSourceFolder() {
    return sourceFolder;
  }

  public void setSourceFolder(File sourceFolder) {
    this.sourceFolder = sourceFolder;
  }

  public File getTargetFolder() {
    return targetFolder;
  }

  public void setTargetFolder(File targetFolder) {
    this.targetFolder = targetFolder;
  }

  public File getTrashFolder() {
    return trashFolder;
  }

  public void setTrashFolder(File trashFolder) {
    this.trashFolder = trashFolder;
  }

  public boolean isTargetFolderValid() {
    return targetFolderValid;
  }

  public void setTargetFolderValid(boolean targetFolderValid) {
    this.targetFolderValid = targetFolderValid;
  }

  public boolean isTrashFolderValid() {
    return trashFolderValid;
  }

  public void setTrashFolderValid(boolean trashFolderValid) {
    this.trashFolderValid = trashFolderValid;
  }

  public boolean isAutoTrashingEnabled() {
    return autoTrashingEnabled;
  }

  public void setAutoTrashingEnabled(boolean autoTrashingEnabled) {
    this.autoTrashingEnabled = autoTrashingEnabled;
  }

  public void addFileID(File file, String line) {
    if (!fileIds.containsKey(file)) {
      fileIds.put(file, new ArrayList<>());
    }
    fileIds.get(file).add(line);
  }

  public List<String> getFileID(File file) {
    if (!fileIds.containsKey(file)) {
      return Collections.emptyList();
    }
    return fileIds.get(file);
  }

  protected Map<File, List<String>> getFileIds() {
    return fileIds;
  }

  public boolean isSuddenDeathEnabled() {
    return suddenDeathEnabled;
  }

  public void setSuddenDeathEnabled(boolean suddenDeathEnabled) {
    this.suddenDeathEnabled = suddenDeathEnabled;
  }

}
