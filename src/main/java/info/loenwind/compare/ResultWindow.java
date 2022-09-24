package info.loenwind.compare;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.ToolTipManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import info.loenwind.compare.tools.Settings;

public class ResultWindow extends JFrame {

  private static final long serialVersionUID = -6097476795465117300L;

  private final JPanel contentPanel = new JPanel();
  private JTable table;
  private JTabbedPane tabbedPane;
  @SuppressWarnings("unused")
  private final Settings settings;

  public ResultWindow(Settings settings, DefaultTableModel model, String result) {
    super(Main.APP_NAME);
    this.settings = settings;
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
                JComponent jc = (JComponent) c;
                String name = getValueAt(row, convertColumnIndexToModel(1)).toString();
                String html = "<html><body><img src=\"" + new File(name).toURI() + "\"></body></html>";
                jc.setToolTipText(html);
              }
              return c;
            }
          };
          table.setFillsViewportHeight(true);
          table.setAutoCreateRowSorter(true);
          List<RowSorter.SortKey> sortKeys = new ArrayList<>();
          sortKeys.add(new RowSorter.SortKey(0, SortOrder.DESCENDING));
          table.getRowSorter().setSortKeys(sortKeys);

          if (table.getRowCount() < 1000) {
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

              tableColumn.setPreferredWidth(preferredWidth);
            }
          } else {
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(1).setPreferredWidth(1000);
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
        ResultWindow owner = this;
        buttonSave.addActionListener(new SaveAction(owner, result));
        buttonPane.add(buttonSave);
      }
      {
        JButton btnNewButton = new JButton("Export...");
        btnNewButton.addActionListener(new ExportAction(this, settings));
        buttonPane.add(btnNewButton);
      }
    }
  }

  JTable getTable() {
    return table;
  }

  JTabbedPane getTabbedPane() {
    return tabbedPane;
  }

}
