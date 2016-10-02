package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca
 */
public class UserResultSet {
    
    ConnectionHandler handler;
    protected long ID;
    protected long GROUPID;
    protected int WON;
    protected int LOST;
    protected int POINTS;
    protected Date DATE;
    protected int SHOTS;
    protected int TOTAL_ROUNDS;
    
    public UserResultSet(ConnectionHandler handler, long groupid,long userid){
        this.handler=handler;
        populate(groupid,userid);
    }

    private void populate(long groupid,long userid){
        ResultSet rs;
        PreparedStatement ps;
        
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM players WHERE GROUPID=? AND ID=?");
            ps.setLong(1, groupid);
            ps.setLong(2, userid);
            rs=ps.executeQuery();
            while(rs.next()){
                this.GROUPID=rs.getLong("GROUPID");
                this.ID=rs.getLong("ID");
                this.DATE=rs.getDate("LAST");
                this.LOST=rs.getInt("LOST");
                this.WON=rs.getInt("WON");
                this.SHOTS=rs.getInt("SHOTS");
                this.TOTAL_ROUNDS=LOST+WON;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserResultSet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public long getID() {
        return ID;
    }

    public long getGROUPID() {
        return GROUPID;
    }

    public int getWON() {
        return WON;
    }

    public int getLOST() {
        return LOST;
    }

    public int getPOINTS() {
        return POINTS;
    }

    public Date getDATE() {
        return DATE;
    }

    public int getTOTAL_ROUNDS() {
        return TOTAL_ROUNDS;
    }
    
}
