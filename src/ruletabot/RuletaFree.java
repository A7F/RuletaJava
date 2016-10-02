package ruletabot;
import java.util.Random;

/**
 *
 * @author Luca
 */
public class RuletaFree {
    
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
}
