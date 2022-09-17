package info.loenwind.compare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class ResultWindow extends JFrame {

  private static final long serialVersionUID = -6097476795465117300L;
  private final JPanel contentPanel = new JPanel();
  private JTable table;
  private JTabbedPane tabbedPane;

  /**
   * Create the dialog.
   */
  public ResultWindow(DefaultTableModel model, String result) {
    setBounds(100, 100, 640, 480);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(new BorderLayout(0, 0));
    {
      tabbedPane = new JTabbedPane(JTabbedPane.TOP);
      contentPanel.add(tabbedPane, BorderLayout.CENTER);
      {
        JPanel panel = new JPanel();
        tabbedPane.addTab("Table", null, panel, null);
        panel.setLayout(new BorderLayout(0, 0));
        {
          table = new JTable(model) {
            private static final long serialVersionUID = -9039636245317280707L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {

              Component c = super.prepareRenderer(renderer, row, col);
              if (c instanceof JComponent) {
                // try {
                JComponent jc = (JComponent) c;
                String name = getValueAt(row, convertColumnIndexToModel(1)).toString();
                String html = "<html><body><img src=\"" + new File(name).toURI()/* .toURL() */ + "\"></body></html>";
                jc.setToolTipText(html);
                // } catch (MalformedURLException e) {
                // e.printStackTrace();
                // }
              }
              return c;
            }
          };
          table.setFillsViewportHeight(true);
          table.setAutoCreateRowSorter(true);
          List<RowSorter.SortKey> sortKeys = new ArrayList<>();
          sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
          table.getRowSorter().setSortKeys(sortKeys);

          table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

          for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();
            for (int row = 0; row < table.getRowCount(); row++) {
              TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
              Component c = table.prepareRenderer(cellRenderer, row, column);
              int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
              preferredWidth = Math.max(preferredWidth, width);
              if (preferredWidth >= maxWidth) {
                preferredWidth = maxWidth;
                break;
              }
            }

            table.addMouseListener(new MouseAdapter() {

              private final int defaultDelay = ToolTipManager.sharedInstance().getInitialDelay();

              @Override
              public void mouseEntered(MouseEvent me) {
                ToolTipManager.sharedInstance().setInitialDelay(0);
              }

              @Override
              public void mouseExited(MouseEvent me) {
                ToolTipManager.sharedInstance().setInitialDelay(defaultDelay);
              }
            });

            tableColumn.setPreferredWidth(preferredWidth);
          }

          panel.add(new JScrollPane(table));
        }
      }
      {
        JPanel panel = new JPanel();
        tabbedPane.addTab("Text", null, panel, null);
        panel.setLayout(new BorderLayout(0, 0));
        {
          JTextPane textPane = new JTextPane();
          textPane.setText(result);
          textPane.setEditable(false);
          panel.add(new JScrollPane(textPane));
        }
      }
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
      }
      {
        JButton buttonSave = new JButton("Save...");
        buttonSave.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            boolean csv = tabbedPane.getSelectedIndex() == 0;

            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save report");
            chooser.setFileFilter(new FileFilter() {

              @Override
              public String getDescription() {
                return csv ? "Comma Separed Values (csv)" : "Plain Text (txt)";
              }

              @Override
              public boolean accept(File f) {
                String name = f.getName().toLowerCase(Locale.ENGLISH);
                return !name.contains(".") || name.endsWith(csv ? ".csv" : ".txt");
              }
            });
            if (chooser.showSaveDialog(ResultWindow.this) == JFileChooser.APPROVE_OPTION) {
              String name = chooser.getSelectedFile().getName().toString();
              if (!name.contains(".")) {
                name = chooser.getSelectedFile().toString() + (csv ? ".csv" : ".txt");
              } else {
                name = chooser.getSelectedFile().toString();
              }
              File file = new File(name);
              if (file.exists()) {
                if (JOptionPane.showConfirmDialog(ResultWindow.this, "The file " + name + " already exists. Overwrite?", "Save Report",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
                  return;
                }
              }
              try {
                FileWriter myWriter = new FileWriter(file);
                if (csv) {
                  for (int i = 0; i < table.getRowCount(); i++) {
                    String line = "";
                    for (int j = 0; j < table.getColumnCount(); j++) {
                      Object value = table.getValueAt(i, j);
                      if (value == null) {
                        line += ";";
                      } else if (value instanceof Integer) {
                        line += value + ";";
                      } else {
                        line += "\"" + value + "\";";
                      }
                    }
                    myWriter.write(line.substring(0, line.length() - 1) + System.lineSeparator());
                  }

                } else {
                  myWriter.write(result);
                }
                myWriter.close();
              } catch (IOException ex) {
                JOptionPane.showMessageDialog(ResultWindow.this, "The file " + name + " could not be written. Message: " + ex.getLocalizedMessage(), "Error",
                    JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
              }
            }

          }
        });
        buttonPane.add(buttonSave);
      }
    }
  }

}
