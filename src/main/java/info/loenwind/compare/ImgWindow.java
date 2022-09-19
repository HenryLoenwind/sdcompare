package info.loenwind.compare;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import info.loenwind.compare.tools.Settings;

public class ImgWindow extends JFrame {

  private static final long serialVersionUID = -7998385048393275848L;

  private static final AbstractAction CLICK_ACTION = new AbstractAction() {
    private static final long serialVersionUID = 2969505039616463263L;

    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JButton) {
        ((JButton) e.getSource()).doClick();
        ((JButton) e.getSource()).requestFocusInWindow();
      }
    }
  };

  private JPanel contentPane;
  private ImagePanel imagePanelL;
  private ImagePanel imagePanelR;
  private JButton buttonExcludeL;
  private JButton buttonLikeL;
  private JButton buttonLikeR;
  private JButton buttonExcludeR;
  private JLabel textTotal;
  private JLabel textUnrated;
  private JLabel textTop;
  private JLabel textExcluded;
  private JLabel textPartial;
  private JLabel textRemaining;
  private JLabel textFully;

  private final List<File> allFiles = new ArrayList<>();
  private final List<File> excludedFiles = new ArrayList<>();
  private final List<File> currentRound = new ArrayList<>();
  private final List<File> nextRound = new ArrayList<>();
  private final List<File> doneFiles = new ArrayList<>();
  private boolean inFirstRound = true;

  private final Map<File, CompData> likes = new HashMap<>();
  private final Set<FilePair> data = new HashSet<>();

  private FilePair pair = null;

  private final Settings settings;

  public boolean setFiles(List<File> files) {
    allFiles.addAll(files);
    currentRound.addAll(files);
    for (File file : files) {
      likes.put(file, new CompData());
    }
    Collections.shuffle(currentRound);
    nextPair();
    return pair != null;
  }

  private void nextPair() {
    nextPair(null, false);
  }

  private void nextPair(File a, boolean isB) {
    updateLabels();
    pair = a != null ? findPairing(a) : findPairing();
    if (pair == null) {
      buttonExcludeL.setEnabled(false);
      buttonLikeL.setEnabled(false);
      buttonLikeR.setEnabled(false);
      buttonExcludeR.setEnabled(false);
      imagePanelL.setImage(null);
      imagePanelR.setImage(null);
      updateLabels();
      openResultWindow();
    } else {
      if (isB) {
        pair = new FilePair(pair.getB(), pair.getA());
      }
      imagePanelL.setImage(pair.getA());
      imagePanelR.setImage(pair.getB());
    }
  }

  /**
   * Finds the next pairing.
   * <p>
   * - Input data must be in currentRound and nextRound.<br>
   * - Returned files are in nextRound.<br>
   * - Returns null if no pairings can be found.<br>
   * - Finished files are placed into doneFiles.
   * 
   * @return a FilePair with two not yet compared files
   */
  private FilePair findPairing() {
    while (true) {
      if (currentRound.isEmpty()) {
        if (nextRound.isEmpty()) {
          return null;
        }
        currentRound.addAll(nextRound);
        nextRound.clear();
        inFirstRound = false;
        Collections.shuffle(currentRound);
      }
      if (currentRound.size() + nextRound.size() == 1) {
        return null; // we need pairs
      }
      File a = currentRound.remove(0);
      FilePair pairing = checkPairing(a);
      if (pairing != null) {
        return pairing;
      }
      doneFiles.add(a);
    }
  }

  private FilePair findPairing(File a) {
    if (currentRound.size() + nextRound.size() == 1) {
      return null; // we need pairs
    }
    nextRound.remove(a);
    FilePair pairing = checkPairing(a);
    if (pairing != null) {
      return pairing;
    }
    doneFiles.add(a);
    return findPairing();
  }

  private FilePair checkPairing(File a) {
    for (File b : currentRound) {
      FilePair candidate = new FilePair(a, b);
      if (!data.contains(candidate)) {
        nextRound.add(a);
        currentRound.remove(b);
        nextRound.add(b);
        data.add(candidate);
        return candidate;
      }
    }
    for (File b : nextRound) {
      FilePair candidate = new FilePair(a, b);
      if (!data.contains(candidate)) {
        nextRound.add(a);
        data.add(candidate);
        return candidate;
      }
    }
    return null;
  }

  private void vote(File pro, File con) {
    likes.get(pro).like();
    likes.get(con).dislike();
    if (settings.isSuddenDeathEnabled()) {
      nextRound.remove(con);
      doneFiles.add(con);
    }
    nextPair();
  }

  private void exclude(File pro, File con, boolean swap) {
    likes.remove(con);
    nextRound.remove(con);
    excludedFiles.add(con);
    trash(con);
    nextPair(pro, swap);
  }

  private void updateLabels() {
    // files
    textTotal.setText(allFiles.size() + "");
    textExcluded.setText(excludedFiles.size() + "");
    int remaining = allFiles.size() - excludedFiles.size();
    textRemaining.setText(remaining + "");
    // ratings
    int rounds = currentRound.size() + nextRound.size();
    textUnrated.setText(inFirstRound ? (currentRound.size() + "") : "0");
    textPartial.setText((inFirstRound ? nextRound.size() : rounds) + "");
    textFully.setText(doneFiles.size() + "");
    // result
    int max = 0, maxC = 0;
    for (CompData entry : likes.values()) {
      int score = entry.getScore();
      if (score > max) {
        max = score;
        maxC = 1;
      } else if (score == max) {
        maxC++;
      }
    }
    textTop.setText(maxC + " (" + (max / 10) + "%)");
  }

  /**
   * Create the frame.
   */
  public ImgWindow(Settings settings) {
    super(Main.APP_NAME);
    this.settings = settings;
    setBounds(100, 100, 640, 480);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    GridBagLayout gbl_contentPane = new GridBagLayout();
    gbl_contentPane.columnWidths = new int[] { 0, 0 };
    gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
    gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gbl_contentPane.rowWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
    contentPane.setLayout(gbl_contentPane);

    JPanel panel = new JPanel();
    panel.setBorder(new LineBorder(new Color(0, 0, 0)));
    GridBagConstraints gbc_panel = new GridBagConstraints();
    gbc_panel.insets = new Insets(0, 0, 5, 0);
    gbc_panel.fill = GridBagConstraints.BOTH;
    gbc_panel.gridx = 0;
    gbc_panel.gridy = 0;
    contentPane.add(panel, gbc_panel);
    GridBagLayout gbl_panel = new GridBagLayout();
    gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
    gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
    gbl_panel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
    gbl_panel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
    panel.setLayout(gbl_panel);

    JLabel labelL = new JLabel("New label");
    GridBagConstraints gbc_labelL = new GridBagConstraints();
    gbc_labelL.anchor = GridBagConstraints.SOUTH;
    gbc_labelL.insets = new Insets(0, 0, 5, 5);
    gbc_labelL.gridx = 0;
    gbc_labelL.gridy = 0;
    panel.add(labelL, gbc_labelL);

    JLabel labelR = new JLabel("New label");
    GridBagConstraints gbc_labelR = new GridBagConstraints();
    gbc_labelR.anchor = GridBagConstraints.SOUTH;
    gbc_labelR.insets = new Insets(0, 0, 5, 0);
    gbc_labelR.gridx = 2;
    gbc_labelR.gridy = 0;
    panel.add(labelR, gbc_labelR);

    imagePanelL = new ImagePanel();
    imagePanelL.setLabel(labelL);
    GridBagConstraints gbc_imagePanelL = new GridBagConstraints();
    gbc_imagePanelL.weighty = 1.0;
    gbc_imagePanelL.insets = new Insets(0, 0, 5, 5);
    gbc_imagePanelL.fill = GridBagConstraints.BOTH;
    gbc_imagePanelL.gridx = 0;
    gbc_imagePanelL.gridy = 1;
    panel.add(imagePanelL, gbc_imagePanelL);

    Component horizontalStrut_2 = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut_2 = new GridBagConstraints();
    gbc_horizontalStrut_2.fill = GridBagConstraints.HORIZONTAL;
    gbc_horizontalStrut_2.insets = new Insets(0, 0, 5, 5);
    gbc_horizontalStrut_2.gridx = 1;
    gbc_horizontalStrut_2.gridy = 1;
    panel.add(horizontalStrut_2, gbc_horizontalStrut_2);

    imagePanelR = new ImagePanel();
    imagePanelR.setLabel(labelR);
    imagePanelR.setOther(imagePanelL);
    imagePanelL.setOther(imagePanelR);
    GridBagConstraints gbc_imagePanelR = new GridBagConstraints();
    gbc_imagePanelR.weighty = 1.0;
    gbc_imagePanelR.insets = new Insets(0, 0, 5, 0);
    gbc_imagePanelR.fill = GridBagConstraints.BOTH;
    gbc_imagePanelR.gridx = 2;
    gbc_imagePanelR.gridy = 1;
    panel.add(imagePanelR, gbc_imagePanelR);

    JPanel panel_2 = new JPanel();
    GridBagConstraints gbc_panel_2 = new GridBagConstraints();
    gbc_panel_2.anchor = GridBagConstraints.NORTHEAST;
    gbc_panel_2.insets = new Insets(0, 0, 0, 5);
    gbc_panel_2.gridx = 0;
    gbc_panel_2.gridy = 2;
    panel.add(panel_2, gbc_panel_2);
    GridBagLayout gbl_panel_2 = new GridBagLayout();
    gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
    gbl_panel_2.rowHeights = new int[] { 0, 0 };
    gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    gbl_panel_2.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    panel_2.setLayout(gbl_panel_2);

    buttonExcludeL = new JButton("Exclude");
    buttonExcludeL.setToolTipText("Shortcuts: 1, Keypad 1");
    buttonExcludeL.addActionListener(e -> exclude(pair.getB(), pair.getA(), true));
    GridBagConstraints gbc_buttonExcludeL = new GridBagConstraints();
    gbc_buttonExcludeL.insets = new Insets(0, 0, 0, 5);
    gbc_buttonExcludeL.gridx = 0;
    gbc_buttonExcludeL.gridy = 0;
    panel_2.add(buttonExcludeL, gbc_buttonExcludeL);

    buttonLikeL = new JButton("    Like    ");
    buttonLikeL.setToolTipText("Shortcuts: Left, 4, Keypad 4");
    buttonLikeL.addActionListener(e -> vote(pair.getA(), pair.getB()));
    GridBagConstraints gbc_buttonLikeL = new GridBagConstraints();
    gbc_buttonLikeL.gridx = 1;
    gbc_buttonLikeL.gridy = 0;
    panel_2.add(buttonLikeL, gbc_buttonLikeL);
    imagePanelL.addClickListener(() -> buttonLikeL.doClick(), () -> buttonExcludeL.doClick());
    setupHotkeys(buttonLikeL, KeyEvent.VK_LEFT, KeyEvent.VK_KP_LEFT, KeyEvent.VK_4, KeyEvent.VK_NUMPAD4);
    setupHotkeys(buttonExcludeL, KeyEvent.VK_1, KeyEvent.VK_NUMPAD1);

    JPanel panel_3 = new JPanel();
    GridBagConstraints gbc_panel_3 = new GridBagConstraints();
    gbc_panel_3.anchor = GridBagConstraints.NORTHWEST;
    gbc_panel_3.gridx = 2;
    gbc_panel_3.gridy = 2;
    panel.add(panel_3, gbc_panel_3);
    GridBagLayout gbl_panel_3 = new GridBagLayout();
    gbl_panel_3.columnWidths = new int[] { 0, 0, 0 };
    gbl_panel_3.rowHeights = new int[] { 0, 0 };
    gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
    gbl_panel_3.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
    panel_3.setLayout(gbl_panel_3);

    buttonLikeR = new JButton("    Like    ");
    buttonLikeR.setToolTipText("Shortcuts: Right, 6, Keypad 6");
    buttonLikeR.addActionListener(e -> vote(pair.getB(), pair.getA()));
    GridBagConstraints gbc_buttonLikeR = new GridBagConstraints();
    gbc_buttonLikeR.insets = new Insets(0, 0, 0, 5);
    gbc_buttonLikeR.gridx = 0;
    gbc_buttonLikeR.gridy = 0;
    panel_3.add(buttonLikeR, gbc_buttonLikeR);

    buttonExcludeR = new JButton("Exclude");
    buttonExcludeR.setToolTipText("Shortcuts: 3, Keypad 3");
    buttonExcludeR.addActionListener(e -> exclude(pair.getA(), pair.getB(), false));
    GridBagConstraints gbc_buttonExcludeR = new GridBagConstraints();
    gbc_buttonExcludeR.gridx = 1;
    gbc_buttonExcludeR.gridy = 0;
    panel_3.add(buttonExcludeR, gbc_buttonExcludeR);
    imagePanelR.addClickListener(() -> buttonLikeR.doClick(), () -> buttonExcludeR.doClick());
    setupHotkeys(buttonLikeR, KeyEvent.VK_RIGHT, KeyEvent.VK_KP_RIGHT, KeyEvent.VK_6, KeyEvent.VK_NUMPAD6);
    setupHotkeys(buttonExcludeR, KeyEvent.VK_3, KeyEvent.VK_NUMPAD3);

    JPanel panel_1 = new JPanel();
    GridBagConstraints gbc_panel_1 = new GridBagConstraints();
    gbc_panel_1.anchor = GridBagConstraints.SOUTH;
    gbc_panel_1.gridx = 0;
    gbc_panel_1.gridy = 1;
    contentPane.add(panel_1, gbc_panel_1);
    GridBagLayout gbl_panel_1 = new GridBagLayout();
    gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
    gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
    gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0 };
    panel_1.setLayout(gbl_panel_1);

    JLabel labelTotal = new JLabel("Total images:");
    labelTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    GridBagConstraints gbc_labelTotal = new GridBagConstraints();
    gbc_labelTotal.anchor = GridBagConstraints.EAST;
    gbc_labelTotal.insets = new Insets(0, 0, 5, 5);
    gbc_labelTotal.gridx = 0;
    gbc_labelTotal.gridy = 0;
    panel_1.add(labelTotal, gbc_labelTotal);

    textTotal = new JLabel("New label");
    GridBagConstraints gbc_textTotal = new GridBagConstraints();
    gbc_textTotal.insets = new Insets(0, 0, 5, 5);
    gbc_textTotal.gridx = 1;
    gbc_textTotal.gridy = 0;
    panel_1.add(textTotal, gbc_textTotal);

    JLabel labelUnrated = new JLabel("Unrated images:");
    GridBagConstraints gbc_labelUnrated = new GridBagConstraints();
    gbc_labelUnrated.anchor = GridBagConstraints.EAST;
    gbc_labelUnrated.insets = new Insets(0, 0, 5, 5);
    gbc_labelUnrated.gridx = 3;
    gbc_labelUnrated.gridy = 0;
    panel_1.add(labelUnrated, gbc_labelUnrated);

    textUnrated = new JLabel("New label");
    GridBagConstraints gbc_textUnrated = new GridBagConstraints();
    gbc_textUnrated.insets = new Insets(0, 0, 5, 5);
    gbc_textUnrated.gridx = 4;
    gbc_textUnrated.gridy = 0;
    panel_1.add(textUnrated, gbc_textUnrated);

    JLabel labelTop = new JLabel("Top rated:");
    GridBagConstraints gbc_labelTop = new GridBagConstraints();
    gbc_labelTop.anchor = GridBagConstraints.EAST;
    gbc_labelTop.insets = new Insets(0, 0, 5, 5);
    gbc_labelTop.gridx = 6;
    gbc_labelTop.gridy = 0;
    panel_1.add(labelTop, gbc_labelTop);

    textTop = new JLabel("New label");
    GridBagConstraints gbc_textTop = new GridBagConstraints();
    gbc_textTop.insets = new Insets(0, 0, 5, 0);
    gbc_textTop.gridx = 7;
    gbc_textTop.gridy = 0;
    panel_1.add(textTop, gbc_textTop);

    JLabel labelExcluded = new JLabel("Excluded images:");
    labelExcluded.setHorizontalAlignment(SwingConstants.RIGHT);
    GridBagConstraints gbc_labelExcluded = new GridBagConstraints();
    gbc_labelExcluded.anchor = GridBagConstraints.EAST;
    gbc_labelExcluded.insets = new Insets(0, 0, 5, 5);
    gbc_labelExcluded.gridx = 0;
    gbc_labelExcluded.gridy = 1;
    panel_1.add(labelExcluded, gbc_labelExcluded);

    textExcluded = new JLabel("New label");
    GridBagConstraints gbc_textExcluded = new GridBagConstraints();
    gbc_textExcluded.insets = new Insets(0, 0, 5, 5);
    gbc_textExcluded.gridx = 1;
    gbc_textExcluded.gridy = 1;
    panel_1.add(textExcluded, gbc_textExcluded);

    Component horizontalStrut = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
    gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
    gbc_horizontalStrut.gridx = 2;
    gbc_horizontalStrut.gridy = 1;
    panel_1.add(horizontalStrut, gbc_horizontalStrut);

    JLabel labelPartial = new JLabel("Partially rated images:");
    GridBagConstraints gbc_labelPartial = new GridBagConstraints();
    gbc_labelPartial.anchor = GridBagConstraints.EAST;
    gbc_labelPartial.insets = new Insets(0, 0, 5, 5);
    gbc_labelPartial.gridx = 3;
    gbc_labelPartial.gridy = 1;
    panel_1.add(labelPartial, gbc_labelPartial);

    textPartial = new JLabel("New label");
    GridBagConstraints gbc_textPartial = new GridBagConstraints();
    gbc_textPartial.insets = new Insets(0, 0, 5, 5);
    gbc_textPartial.gridx = 4;
    gbc_textPartial.gridy = 1;
    panel_1.add(textPartial, gbc_textPartial);

    Component horizontalStrut_1 = Box.createHorizontalStrut(20);
    GridBagConstraints gbc_horizontalStrut_1 = new GridBagConstraints();
    gbc_horizontalStrut_1.insets = new Insets(0, 0, 5, 5);
    gbc_horizontalStrut_1.gridx = 5;
    gbc_horizontalStrut_1.gridy = 1;
    panel_1.add(horizontalStrut_1, gbc_horizontalStrut_1);

    JButton btnNewButton_4 = new JButton("Show ratings...");
    btnNewButton_4.addActionListener(e -> openResultWindow());
    GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
    gbc_btnNewButton_4.gridheight = 2;
    gbc_btnNewButton_4.gridwidth = 2;
    gbc_btnNewButton_4.gridx = 6;
    gbc_btnNewButton_4.gridy = 1;
    panel_1.add(btnNewButton_4, gbc_btnNewButton_4);

    JLabel labelRemaining = new JLabel("Remaining images:");
    GridBagConstraints gbc_labelRemaining = new GridBagConstraints();
    gbc_labelRemaining.anchor = GridBagConstraints.EAST;
    gbc_labelRemaining.insets = new Insets(0, 0, 0, 5);
    gbc_labelRemaining.gridx = 0;
    gbc_labelRemaining.gridy = 2;
    panel_1.add(labelRemaining, gbc_labelRemaining);

    textRemaining = new JLabel("New label");
    GridBagConstraints gbc_textRemaining = new GridBagConstraints();
    gbc_textRemaining.insets = new Insets(0, 0, 0, 5);
    gbc_textRemaining.gridx = 1;
    gbc_textRemaining.gridy = 2;
    panel_1.add(textRemaining, gbc_textRemaining);

    JLabel labelFully = new JLabel("Fully rated images:");
    GridBagConstraints gbc_labelFully = new GridBagConstraints();
    gbc_labelFully.anchor = GridBagConstraints.EAST;
    gbc_labelFully.insets = new Insets(0, 0, 0, 5);
    gbc_labelFully.gridx = 3;
    gbc_labelFully.gridy = 2;
    panel_1.add(labelFully, gbc_labelFully);

    textFully = new JLabel("New label");
    GridBagConstraints gbc_textFully = new GridBagConstraints();
    gbc_textFully.insets = new Insets(0, 0, 0, 5);
    gbc_textFully.gridx = 4;
    gbc_textFully.gridy = 2;
    panel_1.add(textFully, gbc_textFully);

    updateLabels();
  }

  private static void setupHotkeys(JButton button, int... keys) {
    for (int key : keys) {
      button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, 0), "click");
    }
    button.getActionMap().put("click", CLICK_ACTION);
  }

  private DefaultTableModel getTableModel() {
    String[] cols = { "Score", "Image" };
    List<Object[]> rows = new ArrayList<>();
    for (File file : allFiles) {
      if (!excludedFiles.contains(file) && likes.get(file).getLikes() > 0) {
        Object[] row = { new Integer(likes.get(file).getScore()), file.toString() };
        rows.add(row);
      }
    }
    Object[][] tableData = {};
    tableData = rows.toArray(tableData);
    DefaultTableModel model = new DefaultTableModel(tableData, cols) {
      private static final long serialVersionUID = -3600837296901913357L;

      @Override
      public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 0 ? Integer.class : String.class;
      }
    };
    return model;
  }

  private String getResultString() {
    StringBuilder b = new StringBuilder();
    for (File file : allFiles) {
      if (!excludedFiles.contains(file) && likes.get(file).getLikes() > 0) {
        b.append(file).append("\t").append(likes.get(file).getScore()).append("\n");
      }
    }
    return b.toString();
  }

  private void trash(File file) {
    if (settings.isAutoTrashingEnabled() && settings.isTrashFolderValid()) {
      String name = file.getName();
      File target = new File(settings.getTrashFolder(), name);
      int rollingPostfix = 0;
      while (target.exists()) {
        target = new File(settings.getTrashFolder(), insertNumber(name, rollingPostfix++));
      }
      try {
        Files.move(file.toPath(), target.toPath());
        settings.addFileID(file, "Moved from " + file);
        String safename = (name.contains(" ") ? "\"" + name + "\"" : name) + " ";
        String prefix = System.lineSeparator() + safename.replaceAll(".", " ");
        Files.write(new File(settings.getTrashFolder(), "files.bbs").toPath(),
            (safename + String.join(prefix, settings.getFileID(file)) + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND,
            StandardOpenOption.CREATE);
      } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "The file " + name + " could not be moved to " + target + ". Message: " + ex.getLocalizedMessage(), "Error",
            JOptionPane.WARNING_MESSAGE);
      }
    }
  }

  private void openResultWindow() {
    ResultWindow resultWindow = new ResultWindow(getTableModel(), getResultString());
    resultWindow.setVisible(true);
    if ((ImgWindow.this.getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
      resultWindow.setExtendedState(resultWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
  }

  protected static String insertNumber(String name, int number) {
    return name.contains(".") ? name.replaceFirst("\\.([^.]+)$", "_" + number + ".$1") : name + "_" + number;
  }

}
