package keyboards;

import java.util.ArrayList;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import utilities.LocalisationService;

/**
 *
 * @author Luca
 */
public class UserKeyboard extends ReplyKeyboardMarkup{
    
    public UserKeyboard(String region){
        this.setSelective(true);
        this.setResizeKeyboard(true);
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
            firstRow.add(LocalisationService.getInstance().getString("ruletaButton", region));
            firstRow.add(LocalisationService.getInstance().getString("challengeButton", region));
        KeyboardRow secondRow = new KeyboardRow();
            secondRow.add(LocalisationService.getInstance().getString("localchartButton", region));
            secondRow.add(LocalisationService.getInstance().getString("resetchallengeButton", region));
        KeyboardRow thirdRow = new KeyboardRow();
            thirdRow.add(LocalisationService.getInstance().getString("statisticsButton", region));
            thirdRow.add(LocalisationService.getInstance().getString("helpButton", region));
            thirdRow.add(LocalisationService.getInstance().getString("languageButton", region));
        keyboard.add(firstRow);
        keyboard.add(secondRow);
        keyboard.add(thirdRow);
        this.setKeyboard(keyboard);
    }
    
}
