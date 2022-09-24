package info.loenwind.compare.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageInfo {

  private final int height;
  private final int width;

  public ImageInfo(File file) {
    int tmpW = 512;
    int tmpH = 512;
    if (file != null) {
      try {
        BufferedImage image;
        image = ImageIO.read(file);
        tmpW = image.getWidth();
        tmpH = image.getHeight();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    width = tmpW;
    height = tmpH;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

}
