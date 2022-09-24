package info.loenwind.compare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

final class SaveAction implements ActionListener {
  private final ResultWindow owner;
  private final String result;

  SaveAction(ResultWindow owner, String result) {
    this.owner = owner;
    this.result = result;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    boolean csv = owner.getTabbedPane().getSelectedIndex() == 0;

    JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Save report");

    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Comma Separed Values (csv)", "csv"));
    chooser.addChoosableFileFilter(new FileNameExtensionFilter("Plain Text (txt)", "txt"));

    chooser.setFileFilter(chooser.getChoosableFileFilters()[csv ? 1 : 2]);

    if (chooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      String name = file.getName().toString(); // just the filename for now
      if (!name.contains(".")) {
        FileFilter fileFilter = chooser.getFileFilter();
        if (fileFilter instanceof FileNameExtensionFilter) {
          file = new File(chooser.getSelectedFile() + "." + ((FileNameExtensionFilter) fileFilter).getExtensions()[0]);
        } else {
          file = new File(file.toString() + (csv ? ".csv" : ".txt"));
        }
      }
      name = file.toString(); // full path from here on
      // prefer the content type we started with but switch if it is clear from the file extension that the user wants the other one
      csv = csv ? !name.toLowerCase(Locale.ENGLISH).endsWith(".txt") : !name.toLowerCase(Locale.ENGLISH).endsWith(".csv");

      if (file.exists()) {
        if (JOptionPane.showConfirmDialog(owner, "The file " + name + " already exists. Overwrite?", "Save Report",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
          return;
        }
      }
      try {
        FileWriter myWriter = new FileWriter(file);
        if (csv) {
          for (int i = 0; i < owner.getTable().getRowCount(); i++) {
            String line = "";
            for (int j = 0; j < owner.getTable().getColumnCount(); j++) {
              Object value = owner.getTable().getValueAt(i, j);
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
        JOptionPane.showMessageDialog(owner, "The file " + name + " could not be written. Message: " + ex.getLocalizedMessage(), "Error",
            JOptionPane.WARNING_MESSAGE);
        ex.printStackTrace();
      }
    }

  }
}