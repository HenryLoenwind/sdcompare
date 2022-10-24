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

    private boolean isInRange(int value, int lower, int upper){
        return (value >= lower && value < upper);
    }

    private String ScoreToFolderName(Integer score){

        String result;
        Integer scoreBy10 = (Integer)(score / 10);
        
        if(scoreBy10 == 100){result = "A_100_Winners";}
        else if(isInRange(scoreBy10, 90, 100)){result = "B_90-99";}
        else if(isInRange(scoreBy10, 80, 90)){result = "C_80-89";}
        else if(isInRange(scoreBy10, 70, 80)){result = "D_70-79";}
        else if(isInRange(scoreBy10, 60, 70)){result = "E_60-69";}
        else if(isInRange(scoreBy10, 50, 60)){result = "F_50-59";}
        else if(isInRange(scoreBy10, 40, 50)){result = "G_40-49";}
        else if(isInRange(scoreBy10, 30, 40)){result = "H_30-39";}
        else if(isInRange(scoreBy10, 20, 30)){result = "I_20-29";}
        else if(isInRange(scoreBy10, 10, 20)){result = "J_10-19";}
        else {result = "K_0-9";}
        
        return result;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String targetFolder = settings.getTargetFolder().toString();
        if(settings.isTargetFolderValid())
        {
            try{
                for (int i = 0; i < owner.getTable().getRowCount(); i++) {
                    Integer score = (Integer)owner.getTable().getValueAt(i, 0);
                    File file = new File((String)owner.getTable().getValueAt(i,1));          
                    Path pathToScoreFolder = Paths.get(targetFolder,  ScoreToFolderName(score));
                    Files.createDirectories(pathToScoreFolder);
                    Files.move(Paths.get(file.getAbsolutePath()), Paths.get(pathToScoreFolder.toString(), file.getName()));
                }
                
            } catch (IOException err) {
                System.err.println("Failed to create directory!" + err.getMessage());
            }
        }

        JOptionPane.showMessageDialog(owner,"All files has been moved to the target folder",targetFolder, JOptionPane.INFORMATION_MESSAGE);
    }
}
