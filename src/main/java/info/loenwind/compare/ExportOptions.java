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

public class ExportOptions extends JDialog {

  public static class Options {
    public enum Select {
      MANUAL,
      AUTO,
      NONE;
    }

    Select prompt, seed, ddim;
    boolean echoOff, title, pause, ok;
    String promptText, commandText, seedText, ddimText;
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
      JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      contentPanel.add(tabbedPane, BorderLayout.CENTER);
      {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        tabbedPane.addTab("Recreate Script", null, scrollPane, null);
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
              textDdim.setText("500");
              GridBagConstraints gbc_textDdim = new GridBagConstraints();
              gbc_textDdim.insets = new Insets(0, 0, 5, 5);
              gbc_textDdim.fill = GridBagConstraints.HORIZONTAL;
              gbc_textDdim.gridx = 2;
              gbc_textDdim.gridy = 7;
              panel.add(textDdim, gbc_textDdim);
              textDdim.setColumns(10);
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
            options.commandText = textCommand.getText();
            options.prompt = radioManual.isSelected() ? Options.Select.MANUAL : Options.Select.AUTO;
            options.promptText = textPrompt.getText();
            options.seed = radioManualSeed.isSelected() ? Options.Select.MANUAL : radioSeedNone.isSelected() ? Options.Select.NONE : Options.Select.AUTO;
            options.seedText = textSeed.getText();
            options.ddim = radioManualDdim.isSelected() ? Options.Select.MANUAL : Options.Select.AUTO;
            options.ddimText = textDdim.getText();
            options.echoOff = checkEcho.isSelected();
            options.title = checkTitle.isSelected();
            options.pause = checkPause.isSelected();
            options.ok = true;
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
