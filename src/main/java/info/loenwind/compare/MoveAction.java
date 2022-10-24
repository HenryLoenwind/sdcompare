package info.loenwind.compare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.util.*;
import java.io.File;
import info.loenwind.compare.tools.Settings;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MoveAction implements ActionListener{

    private final Settings settings;
    private final ResultWindow owner;

    MoveAction(ResultWindow owner, Settings settings){
        this.owner = owner;
        this.settings = settings;
    }

    private List MakeFolders(String location){
        List<File> dirList = new ArrayList<>();
        String[] folderNames = {"100","90-99","80-89","70-79","60-79","50-59","40-49","30-39","20-29","10-19","0-9"};
        for(String name : folderNames){
            String path = location + '\\' + name;
            File newFolder = new File(path);
            dirList.add(newFolder);
            newFolder.mkdirs();
            
        }
        return dirList;
    }

    private String ScoreToFolder(Integer score){

        String result;
        Integer scoreBy10 = (Integer)(score / 10);

        if(scoreBy10 == 100){result = "A_100";}
        else if(scoreBy10 >= 90 && scoreBy10  < 100){result = "B_90-99";}
        else if(scoreBy10 >= 80 && scoreBy10 < 90){result = "C_80-89";}
        else if(scoreBy10 >= 70 && scoreBy10 < 80){result = "D_70-79";}
        else if(scoreBy10 >= 60 && scoreBy10 < 70){result = "E_60-69";}
        else if(scoreBy10 >= 50 && scoreBy10 < 60){result = "F_50-59";}
        else if(scoreBy10 >= 40 && scoreBy10 < 50){result = "G_40-49";}
        else if(scoreBy10>= 30 && scoreBy10 < 40){result = "H_30-39";}
        else if(scoreBy10 >= 20 && scoreBy10 < 30){result = "I_20-29";}
        else if(scoreBy10 >= 10 && scoreBy10 < 20){result = "J_10-19";}
        else {result = "K_0-9";}
        
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser j = new JFileChooser();
        j.setCurrentDirectory(new File("d:\\test"));
        j.setDialogTitle("Select directoy to move file into");
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        j.setAcceptAllFileFilterUsed(false);

        if(j.showOpenDialog(owner) == JFileChooser.APPROVE_OPTION){

            File selectedDirectory = j.getSelectedFile();
            String root = selectedDirectory.toString();
            try{
                for (int i = 0; i < owner.getTable().getRowCount(); i++) {
                    Integer score = (Integer)owner.getTable().getValueAt(i, 0);
                    File file = new File((String)owner.getTable().getValueAt(i,1));
                    String folderName = ScoreToFolder(score);             
                    Path fullPath = Paths.get(root, folderName);
                    String fullPathStr = fullPath.toString();
                    Files.createDirectories(Paths.get(fullPathStr));
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(fullPathStr, file.getName()));
                }
            } catch (IOException err) {
                System.err.println("Failed to create directory!" + err.getMessage());
            }

        }

/*
            for (int j = 0; j < owner.getTable().getColumnCount(); j++) {
                Object value = owner.getTable().getValueAt(i, j);
                if (value == null) {
                line += ";";
                } else if (value instanceof Integer) {
                line += value + ";";
                } else {
                line += "\"" + value + "\";";
                }
                myWriter.write(line.substring(0, line.length() - 1) + System.lineSeparator());
              }
        */
    }
}
