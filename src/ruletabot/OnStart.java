package ruletabot;
import database.DatabaseHandler;
import keyboards.GuestKeyboard;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import utilities.LocalisationService;

/**
 *
 * @author Luca
 */
public class OnStart {
    int USER_ID,MESSAGE_ID=0;
    long CHAT_ID=0;
    
    public SendMessage onStartActions(Message message){
        this.CHAT_ID=message.getChat().getId();
        this.MESSAGE_ID=message.getMessageId();
        this.USER_ID=message.getFrom().getId();
        DatabaseHandler database = new DatabaseHandler();
        database.registerNewGroup(CHAT_ID);
        database.registerNewUser(USER_ID, CHAT_ID);
        SendMessage msgRequest = new SendMessage();
        msgRequest.setText(LocalisationService.getInstance().getString("addedText", database.getLanguagebyGroup(CHAT_ID)));
        msgRequest.setReplyToMessageId(MESSAGE_ID);
        msgRequest.setReplyMarkup(new GuestKeyboard(database.getLanguagebyGroup(CHAT_ID)));
        return msgRequest;
    }
}
