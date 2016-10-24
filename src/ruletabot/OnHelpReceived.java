package ruletabot;
import database.DatabaseHandler;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import utilities.LocalisationService;

/**
 *
 * @author Luca
 */
public class OnHelpReceived {
    int USER_ID,MESSAGE_ID=0;
    long CHAT_ID=0;
    
    public SendMessage getHelp(Message message){
        this.CHAT_ID=message.getChat().getId();
        this.MESSAGE_ID=message.getMessageId();
        this.USER_ID=message.getFrom().getId();
        SendMessage msgRequest = new SendMessage();
        DatabaseHandler database = new DatabaseHandler();
        msgRequest.setText(LocalisationService.getInstance().getString("deadfreeruletaText", database.getLanguagebyGroup(CHAT_ID)));
        msgRequest.setReplyToMessageId(MESSAGE_ID);
        msgRequest.setChatId(message.getChatId().toString());
        return msgRequest;
    }
}
