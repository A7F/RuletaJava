package ruletabot;

import database.DatabaseHandler;
import database.UserResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import keyboards.*;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import utilities.LocalisationService;
import utilities.MessageHandler;

/**
 *
 * @author Luca
 */
public class BotHandler extends TelegramLongPollingBot{

    @Override
    public String getBotToken(){
        return BotConfig.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        DatabaseHandler database = new DatabaseHandler();
        MessageHandler msgHandler = new MessageHandler(message,database.getLanguagebyGroup(message.getChat().getId()));
        
        if(message.hasText()){
            
            //this bot can't be used on private chats so here's the handler
            if(msgHandler.isUserMessage()){
                SendMessage msgRequest = new SendMessage();
                msgRequest.setText(msgHandler.getNoPvtMessageText("EN")).setChatId(message.getChatId().toString());
                try {
                    sendMessage(msgRequest);
                } catch (TelegramApiException ex) {
                    System.err.println("errore invio messaggio");
                }
                return;
            }
            
            //process only bot commands
            if (msgHandler.isCommand(message.getText())){
                long CHAT_ID = message.getChatId();
                long USER_ID = message.getFrom().getId();
                Integer MESSAGE_ID = message.getMessageId();
                
                if(message.getText().equals("/start")){
                    OnStart startAct = new OnStart();
                    SendMessage msgRequest = startAct.onStartActions(message);
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        System.err.println("errore invio messaggio");
                    }
                    return;
                }
                
                //register new group/user
                if(message.getText().equals(LocalisationService.getInstance().getString("registerMe", database.getLanguagebyGroup(CHAT_ID)))){
                    SendMessage msgRequest = new SendMessage();
                    
                    if(database.checkExistingUser(USER_ID, CHAT_ID)){
                        msgRequest.setText(LocalisationService.getInstance().getString("alreadyaddedText", database.getLanguagebyGroup(CHAT_ID)));
                        msgRequest.setChatId(message.getChatId().toString());
                        msgRequest.setReplyToMessageId(MESSAGE_ID);
                        msgRequest.setReplyMarkup(new UserKeyboard(database.getLanguagebyGroup(CHAT_ID)));
                    }else{
                        database.registerNewUser(USER_ID, CHAT_ID);
                        msgRequest.setText(LocalisationService.getInstance().getString("addeduserText", database.getLanguagebyGroup(CHAT_ID)));
                        msgRequest.setReplyToMessageId(MESSAGE_ID);
                        msgRequest.setChatId(message.getChatId().toString());
                        msgRequest.setReplyMarkup(new UserKeyboard(database.getLanguagebyGroup(CHAT_ID)));
                    }
                    
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        System.err.println("errore invio messaggio");
                    }
                }
                
                //use free ruleta mode
                if(message.getText().equals(LocalisationService.getInstance().getString("ruletaButton", database.getLanguagebyGroup(CHAT_ID)))){
                    RuletaFree ruletaFree = new RuletaFree();
                    SendMessage msgRequest = ruletaFree.ruletafree(message);
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        ex.toString();
                    }
                }
                
                //get bot infos
                if(message.getText().equals(LocalisationService.getInstance().getString("botinfoText", database.getLanguagebyGroup(CHAT_ID)))){
                    OnHelpReceived help = new OnHelpReceived();
                    SendMessage msgRequest = help.getHelp(message);
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        System.err.println("errore invio messaggio");
                    }
                    return;
                }
                
                //reset current duel
                if(message.getText().equals(LocalisationService.getInstance().getString("challengeButton", database.getLanguagebyGroup(CHAT_ID)))){
                    if(database.checkIsChallengeOnGroup(CHAT_ID)){
                        database.resetDuelOnGroup(CHAT_ID);
                        SendMessage msgRequest = new SendMessage();
                        msgRequest.setText(LocalisationService.getInstance().getString("resetcurrentchallengeText", database.getLanguagebyGroup(CHAT_ID)));
                        msgRequest.setReplyToMessageId(MESSAGE_ID);
                        msgRequest.setChatId(message.getChatId().toString());
                    }else{
                        SendMessage msgRequest = new SendMessage();
                        msgRequest.setText(LocalisationService.getInstance().getString("nochallengeactiveText", database.getLanguagebyGroup(CHAT_ID)));
                        msgRequest.setReplyToMessageId(MESSAGE_ID);
                        msgRequest.setChatId(message.getChatId().toString());
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        System.err.println("errore invio messaggio");
                    }
                    }
                    return;
                }
                
                //change language
                if(message.getText().equals(LocalisationService.getInstance().getString("languageButton", database.getLanguagebyGroup(CHAT_ID)))){
                    database.changeGroupLanguage(CHAT_ID);
                    SendMessage msgRequest = new SendMessage();
                    msgRequest.setText(LocalisationService.getInstance().getString("changedlanguageText", database.getLanguagebyGroup(CHAT_ID)));
                    msgRequest.setChatId(message.getChatId().toString());
                    msgRequest.setReplyMarkup(new UserKeyboard(LocalisationService.getInstance().getString("languageButton", database.getLanguagebyGroup(CHAT_ID))));
                    try {
                        sendMessage(msgRequest);
                    } catch (TelegramApiException ex) {
                        System.err.println("errore invio messaggio");
                    }
                }
                
                //get user statistics
                if(message.getText().equals(LocalisationService.getInstance().getString("statisticsButton", database.getLanguagebyGroup(CHAT_ID)))){
                    if(database.checkExistingUser(USER_ID, CHAT_ID)){
                        UserResultSet urs = new UserResultSet(database.getHandler(),USER_ID, CHAT_ID);
                        SendMessage msgRequest = new SendMessage();
                        msgRequest.setText(LocalisationService.getInstance().getString("changedlanguageText", database.getLanguagebyGroup(CHAT_ID)));
                        msgRequest.setChatId(message.getChatId().toString());
                        msgRequest.setReplyMarkup(new UserKeyboard(LocalisationService.getInstance().getString("languageButton", database.getLanguagebyGroup(CHAT_ID))));
                    }else{
                        
                    }
                }
            }
            
        }
        
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }
    
}
