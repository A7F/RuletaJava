package ruletabot;
import database.DatabaseHandler;
import java.util.Random;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import utilities.LocalisationService;

/**
 *
 * @author Luca
 */
public class RuletaFree {
    int USER_ID,MESSAGE_ID=0;
    long CHAT_ID=0;
    
    public static int randInt(int min,int max){
        Random rand = new Random();
        int randNum = rand.nextInt((max-min)+1)+min;
        return randNum;
    }
    
    
    public boolean killByShot(){
        int val1 = randInt(1,6);
        int val2 = randInt(1,6);
        boolean flag=false;
        
        if(val1==val2){
            flag=true;
        }
        
        return flag;
    }
    
    public SendMessage ruletafree(Message message){
        SendMessage msgRequest = new SendMessage();
        DatabaseHandler database = new DatabaseHandler();
        this.CHAT_ID=message.getChat().getId();
        this.MESSAGE_ID=message.getMessageId();
        this.USER_ID=message.getFrom().getId();
        
        if(database.checkIsChallengeOnGroup(CHAT_ID)){
            msgRequest.setText(LocalisationService.getInstance().getString("challengeactiveText", database.getLanguagebyGroup(CHAT_ID)));
            msgRequest.setReplyToMessageId(MESSAGE_ID);
            msgRequest.setChatId(message.getChatId().toString());
        }

        if(database.checkExistingUser(USER_ID, CHAT_ID)){
            if(killByShot()){
                database.incrementUserPoints(USER_ID, CHAT_ID, -3);
                database.incrementUserShots(USER_ID, CHAT_ID);
                msgRequest.setText(LocalisationService.getInstance().getString("deadfreeruletaText", database.getLanguagebyGroup(CHAT_ID)));
                msgRequest.setReplyToMessageId(MESSAGE_ID);
                msgRequest.setChatId(message.getChatId().toString());
            }else{
                database.incrementUserPoints(USER_ID, CHAT_ID, 1);
                database.incrementUserShots(USER_ID,CHAT_ID);
                msgRequest.setText(LocalisationService.getInstance().getString("alivefreeruletaText", database.getLanguagebyGroup(CHAT_ID)));
                msgRequest.setReplyToMessageId(MESSAGE_ID);
                msgRequest.setChatId(message.getChatId().toString());
            }
        }
        return msgRequest;
    }
}
