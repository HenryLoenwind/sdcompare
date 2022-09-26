package info.loenwind.compare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import info.loenwind.compare.ExportOptions.Options.Commands;
import info.loenwind.compare.ExportOptions.Options.Select;
import info.loenwind.compare.tools.Settings;

final class ExportAction implements ActionListener {
  private static final Pattern seedPattern = Pattern.compile("seed_(\\d+)");
  private static final Pattern ddimPattern = Pattern.compile("ddim_(\\d+)");

  private final Settings settings;
  private final ResultWindow owner;

  ExportAction(ResultWindow owner, Settings settings) {
    this.owner = owner;
    this.settings = settings;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ExportOptions.Options options = new ExportOptions.Options();
    ExportOptions exportOptions = new ExportOptions(owner, options);
    exportOptions.setVisible(true);
    if (options.command != Commands.ABORT) {

      JFileChooser chooser = new JFileChooser();
      chooser.setDialogTitle("Export");

      FileNameExtensionFilter filter_cmd = new FileNameExtensionFilter("Command (cmd)", "cmd");
      chooser.addChoosableFileFilter(filter_cmd);
      FileNameExtensionFilter filter_bat = new FileNameExtensionFilter("Batch (bat)", "bat");
      chooser.addChoosableFileFilter(filter_bat);
      FileNameExtensionFilter filter_sh = new FileNameExtensionFilter("Shell (sh)", "sh");
      chooser.addChoosableFileFilter(filter_sh);

      boolean windows = System.getProperty("os.name").contains("indows");
      chooser.setFileFilter(chooser.getChoosableFileFilters()[windows ? 1 : 3]);

      if (chooser.showSaveDialog(owner) == JFileChooser.APPROVE_OPTION) {
        FileFilter filter = chooser.getFileFilter();
        File file = chooser.getSelectedFile();
        String name = file.getName().toString(); // just the filename for now
        if (!name.contains(".")) {
          if (filter instanceof FileNameExtensionFilter) {
            file = new File(chooser.getSelectedFile() + "." + ((FileNameExtensionFilter) filter).getExtensions()[0]);
          } else {
            file = new File(file.toString() + (windows ? ".cmd" : ".sh"));
          }
        }
        name = file.toString(); // full path from here on
        if (!(filter instanceof FileNameExtensionFilter)) {
          filter = name.toLowerCase(Locale.ENGLISH).endsWith(".cmd") ? filter_cmd : name.toLowerCase(Locale.ENGLISH).endsWith(".bat") ? filter_bat : filter_sh;
        }

        if (file.exists()) {
          if (JOptionPane.showConfirmDialog(owner, "The file " + name + " already exists. Overwrite?", "Export", JOptionPane.YES_NO_OPTION,
              JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) {
            return;
          }
        }
        List<String> commands = new ArrayList<>();
        for (int i = 0; i < owner.getTable().getRowCount(); i++) {
          for (int j = 0; j < owner.getTable().getColumnCount(); j++) {
            Object value = owner.getTable().getValueAt(i, j);
            if (value instanceof String) {
              File imagefile = new File((String) value);
              List<String> fileID = settings.getFileID(imagefile);
              String prompt = quote(prompt(options, fileID));
              List<String> command = new ArrayList<>();
              for (String ddim : split(ddim(options, imagefile.toString()), "15")) {
                for (String seed : split(seed(options, imagefile.toString()), null)) {
                  for (String strength : split(options.strengthText, "0.75")) {
                    for (String iter : split(options.iterText, "1")) {
                      for (String samples : split(options.samplesText, "1")) {
                        command.add(options.commandText);
                        command.add("--ddim_steps");
                        command.add(ddim);
                        if (seed != null) {
                          command.add("--seed ");
                          command.add(seed);
                        }
                        command.add("--n_iter");
                        command.add(iter);
                        command.add("--H");
                        command.add("" + settings.getHeight(imagefile));
                        command.add("--W");
                        command.add("" + settings.getWidth(imagefile));
                        command.add("--n_samples");
                        command.add(samples);
                        if (options.command == Commands.IMG) {
                          command.add("--strength");
                          command.add(strength);
                          command.add("--init-img");
                          command.add(quote(imagefile.toString()));
                        }
                        command.add("--prompt");
                        command.add(prompt);
                        commands.add(String.join(" ", command));
                        command.clear();
                      }
                    }
                  }
                }
              }
            }
          }
        }

        try {
          FileWriter myWriter = new FileWriter(file);
          if (filter == filter_sh) {
            myWriter.write("#!/bin/sh" + System.lineSeparator());
          }
          if (options.echoOff && filter != filter_sh) {
            myWriter.write("@echo off" + System.lineSeparator());
          }
          int i = 1;
          for (String command : commands) {
            if (options.title) {
              if (filter == filter_cmd) {
                myWriter.write("title " + (i++) + "/" + commands.size() + System.lineSeparator());
              } else {
                myWriter.write("echo '" + (i++) + "/" + commands.size() + "'" + System.lineSeparator());
              }
            }
            myWriter.write(command + System.lineSeparator());
          }
          if (options.title) {
            if (filter == filter_cmd) {
              myWriter.write("title Done." + System.lineSeparator());
            } else {
              myWriter.write("echo 'Done.'" + System.lineSeparator());
            }
          }
          if (options.pause && filter != filter_sh) {
            myWriter.write("pause" + System.lineSeparator());
          }
          myWriter.close();
          try {
            Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxr-x---"));
          } catch (Exception e1) {
            // not really interested in letting the user know, setting permissions is just a bonus
            e1.printStackTrace();
          }
        } catch (IOException ex) {
          JOptionPane.showMessageDialog(owner, "The file " + name + " could not be written. Message: " + ex.getLocalizedMessage(), "Error",
              JOptionPane.WARNING_MESSAGE);
          ex.printStackTrace();
        }
      }

    }
  }

  protected String prompt(ExportOptions.Options options, List<String> fileID) {
    if (options.prompt != Select.MANUAL && fileID != null && !fileID.isEmpty()) {
      return fileID.get(0);
    } else {
      return options.promptText;
    }
  }

  protected String ddim(ExportOptions.Options options, String filename) {
    switch (options.ddim) {
    case AUTO:
      Matcher matcher = ddimPattern.matcher(filename);
      if (matcher.matches()) {
        return matcher.group(1);
      }
      // fallthrough
    case MANUAL:
      return options.ddimText;
    case NONE:
    default:
      return null;
    }
  }

  protected String seed(ExportOptions.Options options, String filename) {
    switch (options.seed) {
    case AUTO:
      Matcher matcher = seedPattern.matcher(filename);
      if (matcher.matches()) {
        return matcher.group(1);
      }
      // fallthrough
    case MANUAL:
      return options.seedText;
    case NONE:
    default:
      return null;
    }
  }

  private static String quote(String s) {
    return "\"" + s.replaceAll("\"", "\\\"") + "\"";
  }

  private static List<String> split(String s, String fallback) {
    if (s == null || s.trim().isEmpty()) {
      return Collections.singletonList(fallback);
    } else if (s.contains(",")) {
      return Arrays.asList(s.split("\\s*,\\s*"));
    } else {
      return Collections.singletonList(s);
    }
  }

}