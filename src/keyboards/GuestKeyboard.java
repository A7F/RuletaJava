package keyboards;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import utilities.LocalisationService;

/**
 *
 * @author Luca
 */
public class GuestKeyboard extends ReplyKeyboardMarkup{
    
    public GuestKeyboard(String region){
        this.setSelective(true);
        this.setResizeKeyboard(true);
        this.setResizeKeyboard(true);
        this.setOneTimeKeyboad(true);
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
            firstRow.add(LocalisationService.getInstance().getString("registerMe", region));
        keyboard.add(firstRow);
        
        this.setKeyboard(keyboard);
    }
    
}
