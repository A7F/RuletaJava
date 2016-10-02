package utilities;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.Message;

/**
 *
 * @author Luca
 */
public class MessageHandler{
    
    ArrayList<String> commands = new ArrayList<>();
    Message message;
    String locale;
    
    public MessageHandler(Message msg, String locale){
        this.message=msg;
        this.locale=locale;
        commands.add(LocalisationService.getInstance().getString("registerMe", locale));
        commands.add(LocalisationService.getInstance().getString("ruletaButton", locale));
        commands.add(LocalisationService.getInstance().getString("challengeButton", locale));
        commands.add(LocalisationService.getInstance().getString("localchartButton", locale));
        commands.add(LocalisationService.getInstance().getString("resetchallengeButton", locale));
        commands.add(LocalisationService.getInstance().getString("statisticsButton", locale));
        commands.add(LocalisationService.getInstance().getString("globalchartButton", locale));
        commands.add(LocalisationService.getInstance().getString("languageButton", locale));
    }
    
    public boolean isCommand(String text){
        boolean flag;
        if (commands.contains(text)){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }
    
    public boolean isUserMessage(){
        boolean flag;
        if(message.isUserMessage()){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }
    
    public String getNoPvtMessageText(String locale){
        String text = LocalisationService.getInstance().getString("nopvtText", locale);
        return text;
    }
    
    public String getAddedMessageText(String locale){
        String text = LocalisationService.getInstance().getString("addedText", locale);
        return text;
    }
    
    public String getAlreadyAddedMessageText(String locale){
        String text = LocalisationService.getInstance().getString("alreadyaddedText", locale);
        return text;
    }
}
