package info.loenwind.compare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;

import java.io.File;
import info.loenwind.compare.tools.Settings;
import java.io.IOException;

import javax.swing.JOptionPane;

public class MoveAction implements ActionListener{

    private final Settings settings;
    private final ResultWindow owner;

    MoveAction(ResultWindow owner, Settings settings){
        this.owner = owner;
        this.settings = settings;
    }

    private String ScoreToFolder(Integer score){

        String result;
        Integer scoreBy10 = (Integer)(score / 10);

        if(scoreBy10 == 100){result = "A_100_Winners";}
        else if(scoreBy10 >= 90 && scoreBy10  < 100){result = "B_90-99";}
        else if(scoreBy10 >= 80 && scoreBy10 < 90){result = "C_80-89";}
        else if(scoreBy10 >= 70 && scoreBy10 < 80){result = "D_70-79";}
        else if(scoreBy10 >= 60 && scoreBy10 < 70){result = "E_60-69";}
        else if(scoreBy10 >= 50 && scoreBy10 < 60){result = "F_50-59";}
        else if(scoreBy10 >= 40 && scoreBy10 < 50){result = "G_40-49";}
        else if(scoreBy10 >= 30 && scoreBy10 < 40){result = "H_30-39";}
        else if(scoreBy10 >= 20 && scoreBy10 < 30){result = "I_20-29";}
        else if(scoreBy10 >= 10 && scoreBy10 < 20){result = "J_10-19";}
        else {result = "K_0-9";}
        
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String root = settings.getTargetFolder().toString();//selectedDirectory.toString();

        if(settings.isTargetFolderValid())
        {
            try{
                for (int i = 0; i < owner.getTable().getRowCount(); i++) {
                    Integer score = (Integer)owner.getTable().getValueAt(i, 0);
                    File file = new File((String)owner.getTable().getValueAt(i,1));          
                    Path fullPath = Paths.get(root,  ScoreToFolder(score));
                    String fullPathStr = fullPath.toString();
                    Files.createDirectories(Paths.get(fullPathStr));
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(fullPathStr, file.getName()));
                }
                
            } catch (IOException err) {
                System.err.println("Failed to create directory!" + err.getMessage());
            }
        }

        JOptionPane.showMessageDialog(owner,"All files has been move to the target folder",root, JOptionPane.INFORMATION_MESSAGE);
    }
}
