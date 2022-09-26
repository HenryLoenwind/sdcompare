package info.loenwind.compare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import info.loenwind.compare.ExportOptions.Options.Select;
import info.loenwind.compare.tools.ValueFilter;

public class ExportOptions extends JDialog {

  public static class Options {
    public enum Commands {
      @SuppressWarnings("hiding")
      ABORT,
      TXT,
      IMG;
    }

    public enum Select {
      MANUAL,
      AUTO,
      NONE;

      public static Select bi(boolean a) {
        return a ? Options.Select.MANUAL : Options.Select.AUTO;
      }

      public static Select tri(boolean a, boolean b) {
        return a ? Options.Select.MANUAL : b ? Options.Select.NONE : Options.Select.AUTO;
      }
    }

    protected Commands command = Commands.ABORT;
    Select prompt, seed, ddim;
    String promptText, commandText, seedText, ddimText, strengthText, iterText, samplesText;
    boolean echoOff, title, pause;
  }

  private static final long serialVersionUID = -402551196090220378L;

  private final JPanel contentPanel = new JPanel();
  private JTextField textPrompt;
  private final ButtonGroup buttonGroupPrompt = new ButtonGroup();
  @SuppressWarnings("unused")
  private final Options options;
  private JRadioButton radioManual;
  private JCheckBox checkEcho;
  private JCheckBox checkTitle;
  private JCheckBox checkPause;
  private JTextField textSeed;
  private JRadioButton radioManualSeed;
  private final ButtonGroup buttonGroupSeed = new ButtonGroup();
  private JTextField textDdim;
  private final ButtonGroup buttonGroupDdim = new ButtonGroup();
  private JTextField textCommand;
  private JRadioButton radioSeedNone;
  private JRadioButton radioManualDdim;
  private JTextField textCommandImg;
  private JTextField textPromptImg;
  private JTextField textDdimImg;
  private JTextField textStrength;
  private JTextField textSeedImg;
  private final ButtonGroup buttonGroupPromptImg = new ButtonGroup();
  private final ButtonGroup buttonGroupSeedImg = new ButtonGroup();
  private final ButtonGroup buttonGroupDdimImg = new ButtonGroup();
  private JTextField textIterImg;
  private JTextField textSamplesImg;
  private JTabbedPane tabbedPane;
  private JRadioButton radioManualImg;
  private JRadioButton radioManualSeedImg;
  private JRadioButton radioSeedNoneImg;
  private JRadioButton radioManualDdimImg;
  private JCheckBox checkEchoImg;
  private JCheckBox checkTitleImg;
  private JCheckBox checkPauseImg;

  public ExportOptions(Frame owner, Options options) {
    super(owner, "Export Options", true);
    this.options = options;
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 640, 480);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(new BorderLayout(0, 0));
    {
      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      contentPanel.add(tabbedPane, BorderLayout.CENTER);
      {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPane.addTab("txt2img", null, scrollPane, null);
        {
          JPanel panel = new JPanel();
          scrollPane.setViewportView(panel);
          {
            GridBagLayout gbl_panel_2 = new GridBagLayout();
            gbl_panel_2.columnWidths = new int[] { 30, 0, 0, 0 };
            gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            gbl_panel_2.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
            gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
            panel.setLayout(gbl_panel_2);
            {
              Component verticalStrut = Box.createVerticalStrut(20);
              GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
              gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
              gbc_verticalStrut.gridx = 0;
              gbc_verticalStrut.gridy = 0;
              panel.add(verticalStrut, gbc_verticalStrut);
            }
            {
              Component horizontalStrut = Box.createHorizontalStrut(20);
              GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
              gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
              gbc_horizontalStrut.gridx = 3;
              gbc_horizontalStrut.gridy = 1;
              panel.add(horizontalStrut, gbc_horizontalStrut);
            }
            {
              Component horizontalStrut = Box.createHorizontalStrut(20);
              GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
              gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
              gbc_horizontalStrut.gridx = 0;
              gbc_horizontalStrut.gridy = 1;
              panel.add(horizontalStrut, gbc_horizontalStrut);
            }
            {
              JLabel lblNewLabel_4 = new JLabel("Command:");
              GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
              gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
              gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
              gbc_lblNewLabel_4.gridx = 1;
              gbc_lblNewLabel_4.gridy = 1;
              panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
            }
            {
              textCommand = new JTextField();
              textCommand.setText("python optimizedSD/optimized_txt2img.py");
              GridBagConstraints gbc_textCommand = new GridBagConstraints();
              gbc_textCommand.insets = new Insets(0, 0, 5, 5);
              gbc_textCommand.fill = GridBagConstraints.HORIZONTAL;
              gbc_textCommand.gridx = 2;
              gbc_textCommand.gridy = 1;
              panel.add(textCommand, gbc_textCommand);
              textCommand.setColumns(10);
            }
            {
              JLabel lblNewLabel = new JLabel("Prompt:");
              GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
              gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
              gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
              gbc_lblNewLabel.gridx = 1;
              gbc_lblNewLabel.gridy = 2;
              panel.add(lblNewLabel, gbc_lblNewLabel);
            }
            {
              JPanel panel_1 = new JPanel();
              FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
              flowLayout.setVgap(0);
              flowLayout.setHgap(0);
              flowLayout.setAlignment(FlowLayout.LEFT);
              panel_1.setBorder(null);
              GridBagConstraints gbc_panel_1 = new GridBagConstraints();
              gbc_panel_1.anchor = GridBagConstraints.WEST;
              gbc_panel_1.insets = new Insets(0, 0, 5, 5);
              gbc_panel_1.gridx = 2;
              gbc_panel_1.gridy = 2;
              panel.add(panel_1, gbc_panel_1);
              {
                radioManual = new JRadioButton("manual");
                buttonGroupPrompt.add(radioManual);
                panel_1.add(radioManual);
              }
              {
                JRadioButton radioFiles = new JRadioButton("from files.bbs");
                buttonGroupPrompt.add(radioFiles);
                radioFiles.setSelected(true);
                panel_1.add(radioFiles);
              }
            }
            {
              textPrompt = new JTextField();
              textPrompt.setToolTipText("When \"from files.bbs\" is selected, this also is the default when no entry if found there");
              textPrompt.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.STRNQ));
              GridBagConstraints gbc_textPrompt = new GridBagConstraints();
              gbc_textPrompt.insets = new Insets(0, 0, 5, 5);
              gbc_textPrompt.fill = GridBagConstraints.HORIZONTAL;
              gbc_textPrompt.gridx = 2;
              gbc_textPrompt.gridy = 3;
              panel.add(textPrompt, gbc_textPrompt);
              textPrompt.setColumns(10);
            }
            {
              JLabel lblNewLabel_2 = new JLabel("Seed:");
              GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
              gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
              gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
              gbc_lblNewLabel_2.gridx = 1;
              gbc_lblNewLabel_2.gridy = 4;
              panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
            }
            {
              JPanel panel_1 = new JPanel();
              FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
              flowLayout.setVgap(0);
              flowLayout.setHgap(0);
              panel_1.setBorder(null);
              GridBagConstraints gbc_panel_1 = new GridBagConstraints();
              gbc_panel_1.anchor = GridBagConstraints.WEST;
              gbc_panel_1.insets = new Insets(0, 0, 5, 5);
              gbc_panel_1.fill = GridBagConstraints.VERTICAL;
              gbc_panel_1.gridx = 2;
              gbc_panel_1.gridy = 4;
              panel.add(panel_1, gbc_panel_1);
              {
                radioManualSeed = new JRadioButton("manual");
                buttonGroupSeed.add(radioManualSeed);
                panel_1.add(radioManualSeed);
              }
              {
                JRadioButton rdbtnFromFilename = new JRadioButton("from filename");
                buttonGroupSeed.add(rdbtnFromFilename);
                rdbtnFromFilename.setSelected(true);
                panel_1.add(rdbtnFromFilename);
              }
              {
                radioSeedNone = new JRadioButton("none");
                panel_1.add(radioSeedNone);
              }
            }
            {
              textSeed = new JTextField();
              textSeed.setToolTipText(
                  "When \"from filename\" is selected, this also is the default when no value can be determined from the filename. This field supports a comma-separated list of values to generate multiple permutations.");
              textSeed.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INT));
              GridBagConstraints gbc_textSeed = new GridBagConstraints();
              gbc_textSeed.insets = new Insets(0, 0, 5, 5);
              gbc_textSeed.fill = GridBagConstraints.HORIZONTAL;
              gbc_textSeed.gridx = 2;
              gbc_textSeed.gridy = 5;
              panel.add(textSeed, gbc_textSeed);
              textSeed.setColumns(10);
            }
            {
              JLabel lblNewLabel_3 = new JLabel("ddim:");
              GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
              gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
              gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
              gbc_lblNewLabel_3.gridx = 1;
              gbc_lblNewLabel_3.gridy = 6;
              panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
            }
            {
              JPanel panel_1 = new JPanel();
              FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
              flowLayout.setVgap(0);
              flowLayout.setHgap(0);
              panel_1.setBorder(null);
              GridBagConstraints gbc_panel_1 = new GridBagConstraints();
              gbc_panel_1.anchor = GridBagConstraints.WEST;
              gbc_panel_1.insets = new Insets(0, 0, 5, 5);
              gbc_panel_1.fill = GridBagConstraints.VERTICAL;
              gbc_panel_1.gridx = 2;
              gbc_panel_1.gridy = 6;
              panel.add(panel_1, gbc_panel_1);
              {
                radioManualDdim = new JRadioButton("manual");
                radioManualDdim.setSelected(true);
                buttonGroupDdim.add(radioManualDdim);
                panel_1.add(radioManualDdim);
              }
              {
                JRadioButton rdbtnFromFilename = new JRadioButton("from filename");
                buttonGroupDdim.add(rdbtnFromFilename);
                panel_1.add(rdbtnFromFilename);
              }
            }
            {
              textDdim = new JTextField();
              textDdim.setToolTipText(
                  "When \"from filename\" is selected, this also is the default when no value can be determined from the filename. This field supports a comma-separated list of values to generate multiple permutations.");
              textDdim.setText("500");
              GridBagConstraints gbc_textDdim = new GridBagConstraints();
              gbc_textDdim.insets = new Insets(0, 0, 5, 5);
              gbc_textDdim.fill = GridBagConstraints.HORIZONTAL;
              gbc_textDdim.gridx = 2;
              gbc_textDdim.gridy = 7;
              panel.add(textDdim, gbc_textDdim);
              textDdim.setColumns(10);
              textDdim.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INTNN));
            }
            {
              JLabel lblNewLabel_1 = new JLabel("Options:");
              GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
              gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
              gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
              gbc_lblNewLabel_1.gridx = 1;
              gbc_lblNewLabel_1.gridy = 8;
              panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
            }
            {
              checkEcho = new JCheckBox("\"@echo off\" at the start");
              checkEcho.setToolTipText(".cmd and .bat only");
              checkEcho.setSelected(true);
              GridBagConstraints gbc_checkEcho = new GridBagConstraints();
              gbc_checkEcho.insets = new Insets(0, 0, 5, 5);
              gbc_checkEcho.anchor = GridBagConstraints.WEST;
              gbc_checkEcho.gridx = 2;
              gbc_checkEcho.gridy = 8;
              panel.add(checkEcho, gbc_checkEcho);
            }
            {
              checkTitle = new JCheckBox("\"title x/y\" between commands");
              checkTitle.setToolTipText(".cmd only, generates \"echo\" otherwise");
              checkTitle.setSelected(true);
              GridBagConstraints gbc_checkTitle = new GridBagConstraints();
              gbc_checkTitle.insets = new Insets(0, 0, 5, 5);
              gbc_checkTitle.anchor = GridBagConstraints.WEST;
              gbc_checkTitle.gridx = 2;
              gbc_checkTitle.gridy = 9;
              panel.add(checkTitle, gbc_checkTitle);
            }
            {
              checkPause = new JCheckBox("\"pause\" at the end");
              checkPause.setToolTipText(".cmd/.bat only");
              GridBagConstraints gbc_checkPause = new GridBagConstraints();
              gbc_checkPause.insets = new Insets(0, 0, 5, 5);
              gbc_checkPause.anchor = GridBagConstraints.WEST;
              gbc_checkPause.gridx = 2;
              gbc_checkPause.gridy = 10;
              panel.add(checkPause, gbc_checkPause);
            }
          }
        }
      }
      {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tabbedPane.addTab("img2img", null, scrollPane, null);
        {
          JPanel panel = new JPanel();
          scrollPane.setViewportView(panel);
          GridBagLayout gbl_panel = new GridBagLayout();
          gbl_panel.columnWidths = new int[] { 30, 0, 0, 0 };
          gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
          gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
          gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
          panel.setLayout(gbl_panel);
          {
            Component verticalStrut = Box.createVerticalStrut(20);
            GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
            gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
            gbc_verticalStrut.gridx = 0;
            gbc_verticalStrut.gridy = 0;
            panel.add(verticalStrut, gbc_verticalStrut);
          }
          {
            Component horizontalStrut = Box.createHorizontalStrut(20);
            GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
            gbc_horizontalStrut.insets = new Insets(0, 0, 5, 5);
            gbc_horizontalStrut.gridx = 0;
            gbc_horizontalStrut.gridy = 1;
            panel.add(horizontalStrut, gbc_horizontalStrut);
          }
          {
            JLabel lblNewLabel_4 = new JLabel("Command:");
            GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
            gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_4.gridx = 1;
            gbc_lblNewLabel_4.gridy = 1;
            panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
          }
          {
            textCommandImg = new JTextField();
            textCommandImg.setText("python optimizedSD/optimized_img2img.py");
            textCommandImg.setColumns(10);
            GridBagConstraints gbc_textCommandImg = new GridBagConstraints();
            gbc_textCommandImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textCommandImg.insets = new Insets(0, 0, 5, 5);
            gbc_textCommandImg.gridx = 2;
            gbc_textCommandImg.gridy = 1;
            panel.add(textCommandImg, gbc_textCommandImg);
          }
          {
            Component horizontalStrut = Box.createHorizontalStrut(20);
            GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
            gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
            gbc_horizontalStrut.gridx = 3;
            gbc_horizontalStrut.gridy = 1;
            panel.add(horizontalStrut, gbc_horizontalStrut);
          }
          {
            JLabel lblNewLabel = new JLabel("Prompt:");
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 1;
            gbc_lblNewLabel.gridy = 2;
            panel.add(lblNewLabel, gbc_lblNewLabel);
          }
          {
            JPanel panel_1 = new JPanel();
            FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
            flowLayout.setVgap(0);
            flowLayout.setHgap(0);
            panel_1.setBorder(null);
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.anchor = GridBagConstraints.WEST;
            gbc_panel_1.insets = new Insets(0, 0, 5, 5);
            gbc_panel_1.gridx = 2;
            gbc_panel_1.gridy = 2;
            panel.add(panel_1, gbc_panel_1);
            {
              radioManualImg = new JRadioButton("manual");
              buttonGroupPromptImg.add(radioManualImg);
              panel_1.add(radioManualImg);
            }
            {
              JRadioButton radioFiles = new JRadioButton("from files.bbs");
              buttonGroupPromptImg.add(radioFiles);
              radioFiles.setSelected(true);
              panel_1.add(radioFiles);
            }
          }
          {
            textPromptImg = new JTextField();
            textPromptImg.setToolTipText("When \"from files.bbs\" is selected, this also is the default when no entry if found there");
            textPromptImg.setColumns(10);
            textPromptImg.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.STRNQ));
            GridBagConstraints gbc_textPromptImg = new GridBagConstraints();
            gbc_textPromptImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textPromptImg.insets = new Insets(0, 0, 5, 5);
            gbc_textPromptImg.gridx = 2;
            gbc_textPromptImg.gridy = 3;
            panel.add(textPromptImg, gbc_textPromptImg);
          }
          {
            JLabel lblNewLabel_2 = new JLabel("Seed:");
            GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
            gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_2.gridx = 1;
            gbc_lblNewLabel_2.gridy = 4;
            panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
          }
          {
            JPanel panel_1 = new JPanel();
            FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
            flowLayout.setVgap(0);
            flowLayout.setHgap(0);
            panel_1.setBorder(null);
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.anchor = GridBagConstraints.WEST;
            gbc_panel_1.insets = new Insets(0, 0, 5, 5);
            gbc_panel_1.gridx = 2;
            gbc_panel_1.gridy = 4;
            panel.add(panel_1, gbc_panel_1);
            {
              radioManualSeedImg = new JRadioButton("manual");
              buttonGroupSeedImg.add(radioManualSeedImg);
              panel_1.add(radioManualSeedImg);
            }
            {
              JRadioButton rdbtnFromFilename = new JRadioButton("from filename");
              buttonGroupSeedImg.add(rdbtnFromFilename);
              panel_1.add(rdbtnFromFilename);
            }
            {
              radioSeedNoneImg = new JRadioButton("none");
              buttonGroupSeedImg.add(radioSeedNoneImg);
              radioSeedNoneImg.setSelected(true);
              panel_1.add(radioSeedNoneImg);
            }
          }
          {
            textSeedImg = new JTextField();
            textSeedImg.setToolTipText(
                "When \"from filename\" is selected, this also is the default when no value can be determined from the filename. This field supports a comma-separated list of values to generate multiple permutations.");
            textSeedImg.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INT));
            GridBagConstraints gbc_textSeedImg = new GridBagConstraints();
            gbc_textSeedImg.insets = new Insets(0, 0, 5, 5);
            gbc_textSeedImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textSeedImg.gridx = 2;
            gbc_textSeedImg.gridy = 5;
            panel.add(textSeedImg, gbc_textSeedImg);
            textSeedImg.setColumns(10);
          }
          {
            JLabel lblNewLabel_3 = new JLabel("ddim:");
            GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
            gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_3.gridx = 1;
            gbc_lblNewLabel_3.gridy = 6;
            panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
          }
          {
            JPanel panel_1 = new JPanel();
            FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
            flowLayout.setHgap(0);
            flowLayout.setVgap(0);
            panel_1.setBorder(null);
            GridBagConstraints gbc_panel_1 = new GridBagConstraints();
            gbc_panel_1.fill = GridBagConstraints.VERTICAL;
            gbc_panel_1.anchor = GridBagConstraints.WEST;
            gbc_panel_1.insets = new Insets(0, 0, 5, 5);
            gbc_panel_1.gridx = 2;
            gbc_panel_1.gridy = 6;
            panel.add(panel_1, gbc_panel_1);
            {
              radioManualDdimImg = new JRadioButton("manual");
              buttonGroupDdimImg.add(radioManualDdimImg);
              radioManualDdimImg.setSelected(true);
              panel_1.add(radioManualDdimImg);
            }
            {
              JRadioButton rdbtnFromFilename = new JRadioButton("from filename");
              buttonGroupDdimImg.add(rdbtnFromFilename);
              panel_1.add(rdbtnFromFilename);
            }
          }
          {
            textDdimImg = new JTextField();
            textDdimImg.setToolTipText(
                "When \"from filename\" is selected, this also is the default when no value can be determined from the filename. This field supports a comma-separated list of values to generate multiple permutations.");
            textDdimImg.setText("50");
            textDdimImg.setColumns(10);
            textDdimImg.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INTNN));
            GridBagConstraints gbc_textDdimImg = new GridBagConstraints();
            gbc_textDdimImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textDdimImg.insets = new Insets(0, 0, 5, 5);
            gbc_textDdimImg.gridx = 2;
            gbc_textDdimImg.gridy = 7;
            panel.add(textDdimImg, gbc_textDdimImg);
          }
          {
            JLabel lblNewLabel_5 = new JLabel("Strength:");
            GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
            gbc_lblNewLabel_5.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_5.gridx = 1;
            gbc_lblNewLabel_5.gridy = 8;
            panel.add(lblNewLabel_5, gbc_lblNewLabel_5);
          }
          {
            textStrength = new JTextField();
            textStrength.setToolTipText("This field supports a comma-separated list of values to generate multiple permutations.");
            textStrength.setText("0.75");
            textStrength.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.FLOAT01));
            GridBagConstraints gbc_textStrength = new GridBagConstraints();
            gbc_textStrength.insets = new Insets(0, 0, 5, 5);
            gbc_textStrength.fill = GridBagConstraints.HORIZONTAL;
            gbc_textStrength.gridx = 2;
            gbc_textStrength.gridy = 8;
            panel.add(textStrength, gbc_textStrength);
            textStrength.setColumns(10);
          }
          {
            JLabel lblNewLabel_6 = new JLabel("Iterations:");
            GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
            gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_6.gridx = 1;
            gbc_lblNewLabel_6.gridy = 9;
            panel.add(lblNewLabel_6, gbc_lblNewLabel_6);
          }
          {
            textIterImg = new JTextField();
            textIterImg.setToolTipText("This field supports a comma-separated list of values to generate multiple permutations.");
            textIterImg.setText("1");
            textIterImg.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INTNN));
            GridBagConstraints gbc_textIterImg = new GridBagConstraints();
            gbc_textIterImg.insets = new Insets(0, 0, 5, 5);
            gbc_textIterImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textIterImg.gridx = 2;
            gbc_textIterImg.gridy = 9;
            panel.add(textIterImg, gbc_textIterImg);
            textIterImg.setColumns(10);
          }
          {
            JLabel lblNewLabel_7 = new JLabel("Samples:");
            lblNewLabel_7.setToolTipText("");
            GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
            gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_7.gridx = 1;
            gbc_lblNewLabel_7.gridy = 10;
            panel.add(lblNewLabel_7, gbc_lblNewLabel_7);
          }
          {
            textSamplesImg = new JTextField();
            textSamplesImg.setToolTipText("This field supports a comma-separated list of values to generate multiple permutations.");
            textSamplesImg.setText("5");
            textSamplesImg.getDocument().addUndoableEditListener(new ValueFilter(ValueFilter.INTNN));
            GridBagConstraints gbc_textSamplesImg = new GridBagConstraints();
            gbc_textSamplesImg.insets = new Insets(0, 0, 5, 5);
            gbc_textSamplesImg.fill = GridBagConstraints.HORIZONTAL;
            gbc_textSamplesImg.gridx = 2;
            gbc_textSamplesImg.gridy = 10;
            panel.add(textSamplesImg, gbc_textSamplesImg);
            textSamplesImg.setColumns(10);
          }
          {
            JLabel lblNewLabel_1 = new JLabel("Options:");
            GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
            gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
            gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel_1.gridx = 1;
            gbc_lblNewLabel_1.gridy = 11;
            panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
          }
          {
            checkEchoImg = new JCheckBox("\"@echo off\" at the start");
            checkEchoImg.setToolTipText(".cmd and .bat only");
            checkEchoImg.setSelected(true);
            GridBagConstraints gbc_checkEchoImg = new GridBagConstraints();
            gbc_checkEchoImg.anchor = GridBagConstraints.WEST;
            gbc_checkEchoImg.insets = new Insets(0, 0, 5, 5);
            gbc_checkEchoImg.gridx = 2;
            gbc_checkEchoImg.gridy = 11;
            panel.add(checkEchoImg, gbc_checkEchoImg);
          }
          {
            checkTitleImg = new JCheckBox("\"title x/y\" between commands");
            checkTitleImg.setToolTipText(".cmd only, generates \"echo\" otherwise");
            checkTitleImg.setSelected(true);
            GridBagConstraints gbc_checkTitleImg = new GridBagConstraints();
            gbc_checkTitleImg.anchor = GridBagConstraints.WEST;
            gbc_checkTitleImg.insets = new Insets(0, 0, 5, 5);
            gbc_checkTitleImg.gridx = 2;
            gbc_checkTitleImg.gridy = 12;
            panel.add(checkTitleImg, gbc_checkTitleImg);
          }
          {
            checkPauseImg = new JCheckBox("\"pause\" at the end");
            checkPauseImg.setToolTipText(".cmd/.bat only");
            GridBagConstraints gbc_checkPauseImg = new GridBagConstraints();
            gbc_checkPauseImg.anchor = GridBagConstraints.WEST;
            gbc_checkPauseImg.insets = new Insets(0, 0, 5, 5);
            gbc_checkPauseImg.gridx = 2;
            gbc_checkPauseImg.gridy = 13;
            panel.add(checkPauseImg, gbc_checkPauseImg);
          }
        }
      }
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("Save...");
        okButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (tabbedPane.getSelectedIndex() == 0) {
              options.command = Options.Commands.TXT;
              options.commandText = textCommand.getText();
              options.prompt = Select.bi(radioManual.isSelected());
              options.promptText = textPrompt.getText();
              options.seed = Select.tri(radioManualSeed.isSelected(), radioSeedNone.isSelected());
              options.seedText = textSeed.getText();
              options.ddim = Select.bi(radioManualDdim.isSelected());
              options.ddimText = textDdim.getText();
              options.echoOff = checkEcho.isSelected();
              options.title = checkTitle.isSelected();
              options.pause = checkPause.isSelected();
            } else {
              options.command = Options.Commands.IMG;
              options.commandText = textCommandImg.getText();
              options.prompt = Select.bi(radioManualImg.isSelected());
              options.promptText = textPromptImg.getText();
              options.seed = Select.tri(radioManualSeedImg.isSelected(), radioSeedNoneImg.isSelected());
              options.seedText = textSeedImg.getText();
              options.ddim = Select.bi(radioManualDdimImg.isSelected());
              options.ddimText = textDdimImg.getText();
              options.strengthText = textStrength.getText();
              options.iterText = textIterImg.getText();
              options.samplesText = textSamplesImg.getText();
              options.echoOff = checkEchoImg.isSelected();
              options.title = checkTitleImg.isSelected();
              options.pause = checkPauseImg.isSelected();
            }
            dispose();
          }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
  }

}
