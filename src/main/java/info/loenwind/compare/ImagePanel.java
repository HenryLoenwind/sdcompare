package info.loenwind.compare;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @beaninfo attribute: isContainer false description: A component that displays an image.
 * 
 * @author micaja
 *
 */
public class ImagePanel extends JPanel {

  private static final long serialVersionUID = -3470117837379811002L;
  private BufferedImage image;
  private JLabel label = null;
  private ImagePanel other = null;
  private boolean hovered = false;
  private Rectangle imgpos;

  private final JPanel overlay;

  public ImagePanel() {
    super();
    overlay = new JPanel();
    overlay.setOpaque(false);
    add(overlay);
  }

  public void setImage(File file) {
    if (file == null) {
      image = null;
      label.setText("No image");
      return;
    }
    try {
      image = ImageIO.read(file);
      label.setText(file.getName());
      repaint();
    } catch (IOException ex) {
      ex.printStackTrace();
      image = null;
      label.setText(ex.getLocalizedMessage());
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (image != null) {
      int w = getWidth();
      int h = getHeight();
      if (other != null) {
        w = Math.min(w, other.getWidth());
        h = Math.min(h, other.getHeight());
      }
      int imgW = image.getWidth();
      int imgH = image.getHeight();
      int targetW = imgW;
      int targetH = imgH;
      double ar = imgW / (double) imgH;
      if (w > targetW) {
        targetW = w;
        targetH = (int) (targetW / ar);
      }
      if (h > targetH) {
        targetH = h;
        targetW = (int) (targetH * ar);
      }
      if (w < targetW) {
        targetW = w;
        targetH = (int) (targetW / ar);
      }
      if (h < targetH) {
        targetH = h;
        targetW = (int) (targetH * ar);
      }
      int offsetW = (w - targetW) / 2;
      int offsetH = (h - targetH) / 2;
      imgpos = new Rectangle(offsetW, offsetH, targetW, targetH);
      overlay.setBounds(imgpos);

      if (g instanceof Graphics2D) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      }
      g.drawImage(image, offsetW, offsetH, targetW, targetH, this);

      if (hovered) {
        g.setColor(Color.BLUE);
        g.drawRect(offsetW, offsetH, targetW, targetH);
        g.drawRect(offsetW + 1, offsetH + 1, targetW - 2, targetH - 2);
        g.drawRect(offsetW + 2, offsetH + 2, targetW - 4, targetH - 4);
      }
    } else {
      imgpos = null;
    }
  }

  public void addClickListener(Runnable listener1, Runnable listener2) {
    overlay.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {
      }

      @Override
      public void mousePressed(MouseEvent e) {
      }

      @Override
      public void mouseExited(MouseEvent e) {
        hovered = false;
        ImagePanel.this.repaint();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        hovered = true;
        ImagePanel.this.repaint();
      }

      @Override
      public void mouseClicked(MouseEvent e) {
        if (imgpos != null && imgpos.contains(e.getX(), e.getY())) {
          switch (e.getButton()) {
          case MouseEvent.BUTTON1:
            listener1.run();
            break;
          case MouseEvent.BUTTON2:
            listener2.run();
            break;
          }
        }
      }
    });
  }

  /**
   * 
   * @param label
   * 
   * @beaninfo bound: true description: Defines the label to be updated with the filename.
   * 
   */
  public void setLabel(JLabel label) {
    this.label = label;
    label.setText("No image");
  }

  public void setOther(ImagePanel other) {
    this.other = other;
  }

}