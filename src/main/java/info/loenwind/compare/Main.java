package info.loenwind.compare;

public class Main {

  public static void main(String[] args) {
    System.setProperty("apple.laf.useScreenMenuBar", "true");
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "ImageVoter");
    System.setProperty("apple.awt.application.name", "ImageVoter");
    AppWindow.run();
  }

}
