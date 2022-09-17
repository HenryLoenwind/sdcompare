package info.loenwind.compare;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AppWindow {

  private JFrame frame;
  private JTextField textField;
  private JLabel labelScan;
  private JButton buttonScan;
  private final List<File> files = new ArrayList<>();
  private JButton buttonCompare;
  private JLabel lblNewLabel_1;
  private JLabel lblNewLabel_2;
  private JLabel lblNewLabel_3;
  private Component horizontalStrut;
  private JLabel labelCompare;
  private Component horizontalStrut_1;
  private Component verticalStrut;
  private Component horizontalStrut_2;

  /**
   * Launch the application.
   */
  public static void run() {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        try {
          AppWindow window = new AppWindow();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public AppWindow() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e2) {
      e2.printStackTrace();
    }
    frame = new JFrame("Image Voter");
    frame.setBounds(100, 100, 640, 320);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
    frame.getContentPane().setLayout(gridBagLayout);

    verticalStrut = Box.createVerticalStrut(20);
    GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
    gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
    gbc_verticalStrut.gridx = 0;
    gbc_verticalStrut.gridy = 0;
    frame.getContentPane().add(verticalStrut, gbc_verticalStrut);

    horizontalStrut_2 = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
    gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 0);
    gbc_horizontalStrut_2.gridx = 6;
    gbc_horizontalStrut_2.gridy = 0;
    frame.getContentPane().add(horizontalStrut_2, gbc_horizontalStrut_2);

    horizontalStrut_1 = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
    gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
    gbc_horizontalStrut_1.gridx = 0;
    gbc_horizontalStrut_1.gridy = 1;
    frame.getContentPane().add(horizontalStrut_1, gbc_horizontalStrut_1);

    lblNewLabel_1 = new JLabel("Step 1:");
    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
    gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_1.gridx = 1;
    gbc_lblNewLabel_1.gridy = 1;
    frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

    horizontalStrut = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
    gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
    gbc_horizontalStrut.gridx = 2;
    gbc_horizontalStrut.gridy = 1;
    frame.getContentPane().add(horizontalStrut, gbc_horizontalStrut);

    JLabel lblNewLabel = new JLabel("Source folder:");
    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
    gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel.gridx = 3;
    gbc_lblNewLabel.gridy = 1;
    frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

    textField = new JTextField();
    textField.getDocument().addDocumentListener(new DocumentListener() {

      @Override
      public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
      }

      @Override
      public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        buttonScan.setEnabled(!textField.getText().isEmpty());
      }
    });
    GridBagConstraints gbc_textField = new GridBagConstraints();
    gbc_textField.insets = new Insets(0, 0, 5, 5);
    gbc_textField.fill = GridBagConstraints.HORIZONTAL;
    gbc_textField.gridx = 4;
    gbc_textField.gridy = 1;
    frame.getContentPane().add(textField, gbc_textField);
    textField.setColumns(10);

    JButton buttonSelect = new JButton("Select...");
    buttonSelect.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        if (textField.getText().isEmpty()) {
          chooser.setCurrentDirectory(new java.io.File("."));
        } else {
          chooser.setCurrentDirectory(new java.io.File(textField.getText()));
        }
        chooser.setDialogTitle("Select images folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
          try {
            textField.setText(chooser.getSelectedFile().getCanonicalPath().toString());
            buttonScan.setEnabled(true);
          } catch (IOException e1) {
            e1.printStackTrace();
            textField.setText(e1.getLocalizedMessage());
            buttonScan.setEnabled(false);
          }
          buttonCompare.setEnabled(false);
          labelCompare.setVisible(true);
        }
      }
    });
    GridBagConstraints gbc_buttonSelect = new GridBagConstraints();
    gbc_buttonSelect.insets = new Insets(0, 0, 5, 0);
    gbc_buttonSelect.gridx = 5;
    gbc_buttonSelect.gridy = 1;
    frame.getContentPane().add(buttonSelect, gbc_buttonSelect);

    lblNewLabel_2 = new JLabel("Step 2:");
    GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
    gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_2.gridx = 1;
    gbc_lblNewLabel_2.gridy = 3;
    frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

    buttonScan = new JButton("Scan for images");
    buttonScan.setEnabled(false);
    buttonScan.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (textField.getText().isEmpty()) {
          buttonScan.setEnabled(false);
          return;
        }
        File folder = new java.io.File(textField.getText());
        if (!folder.isDirectory()) {
          labelScan.setText("Not a folder");
          buttonScan.setEnabled(false);
        }
        try {
          files.clear();
          Files.walkFileTree(folder.toPath(), new FileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
              if (file.toString().toLowerCase(Locale.ENGLISH).endsWith(".png") || file.toString().toLowerCase(Locale.ENGLISH).endsWith(".jpg")) {
                files.add(file.toFile());
              }
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
              return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
              return FileVisitResult.CONTINUE;
            }
          });
          labelScan.setText(files.size() + " images found");
          buttonCompare.setEnabled(!files.isEmpty());
          labelCompare.setVisible(files.isEmpty());
        } catch (IOException e1) {
          e1.printStackTrace();
          labelScan.setText(e1.getLocalizedMessage());
          buttonScan.setEnabled(false);
          buttonCompare.setEnabled(false);
          labelCompare.setVisible(true);
        }
      }
    });
    GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
    gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
    gbc_btnNewButton_1.gridx = 3;
    gbc_btnNewButton_1.gridy = 3;
    frame.getContentPane().add(buttonScan, gbc_btnNewButton_1);

    labelScan = new JLabel("Please select a folder");
    GridBagConstraints gbc_labelScan = new GridBagConstraints();
    gbc_labelScan.gridwidth = 2;
    gbc_labelScan.insets = new Insets(0, 0, 5, 0);
    gbc_labelScan.gridx = 4;
    gbc_labelScan.gridy = 3;
    frame.getContentPane().add(labelScan, gbc_labelScan);

    lblNewLabel_3 = new JLabel("Step 3:");
    GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
    gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_3.gridx = 1;
    gbc_lblNewLabel_3.gridy = 5;
    frame.getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

    JButton buttonExit = new JButton("Exit");
    buttonExit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
      }
    });

    buttonCompare = new JButton("Compare images");
    buttonCompare.setEnabled(false);
    buttonCompare.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
        ImgWindow imgframe = new ImgWindow();
        imgframe.addWindowListener(new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent e1) {
            frame.setVisible(true);
            imgframe.dispose();
          }
        });
        imgframe.setFiles(files);
        imgframe.setVisible(true);
        if ((frame.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
          imgframe.setExtendedState(imgframe.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
      }
    });
    // buttonCompare.setEnabled(false);
    GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
    gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
    gbc_btnNewButton_2.gridx = 3;
    gbc_btnNewButton_2.gridy = 5;
    frame.getContentPane().add(buttonCompare, gbc_btnNewButton_2);

    labelCompare = new JLabel("Please scan for images");
    GridBagConstraints gbc_labelCompare = new GridBagConstraints();
    gbc_labelCompare.gridwidth = 2;
    gbc_labelCompare.insets = new Insets(0, 0, 5, 0);
    gbc_labelCompare.gridx = 4;
    gbc_labelCompare.gridy = 5;
    frame.getContentPane().add(labelCompare, gbc_labelCompare);
    GridBagConstraints gbc_buttonExit = new GridBagConstraints();
    gbc_buttonExit.gridx = 5;
    gbc_buttonExit.gridy = 7;
    frame.getContentPane().add(buttonExit, gbc_buttonExit);
  }

}
