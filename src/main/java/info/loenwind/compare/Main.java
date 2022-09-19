package info.loenwind.compare;

public class Main {

  public static final String APP_NAME = "Image Voter";

  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);
    System.setProperty("apple.awt.application.name", APP_NAME);
    AppWindow.run();
  }

}
