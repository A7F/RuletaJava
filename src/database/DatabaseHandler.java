package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luca
 */
public class DatabaseHandler {
    ConnectionHandler handler = new ConnectionHandler();
    
    public DatabaseHandler(){
        init();
    }

    private void init(){
        try {
            handler.getStatement().executeUpdate(CreationStrings.createDatabase);
            handler.getStatement().executeUpdate(CreationStrings.createPlayersTable);
            handler.getStatement().executeUpdate(CreationStrings.createGroupsTable);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkExistingUser(long userid, long groupid){
        
        boolean flag=false;
        
        try {
            ResultSet rs;
            PreparedStatement ps = handler.getConnection().prepareStatement("SELECT * FROM players WHERE ID=? AND GROUPID=?");
            ps.setLong(1, userid);
            ps.setLong(2, groupid);
            rs = ps.executeQuery();
            
            if(rs.next()){
                flag=true;
            }else{
                flag=false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return flag;
    }
    
    public void registerNewUser(long userid,long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        
        if(checkExistingUser(userid,groupid)==false){
            try {
                ps = handler.getConnection().prepareStatement("INSERT INTO players(ID,GROUPID) VALUES(?,?)");
                ps.setLong(1, userid);
                ps.setLong(2, groupid);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
    }
    
    public void registerNewGroup(long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        
        if(checkExistingGroup(groupid)==false){
            try {
                ps = handler.getConnection().prepareStatement("INSERT INTO groups(ID) VALUES(?)");
                ps.setLong(1, groupid);
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getLanguagebyGroup(long groupid){
        String lang_code="";
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
                lang_code=rs.getString("LANGUAGE");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lang_code;
    }
    
    public boolean checkIsChallengeOnGroup(long groupid){
        
        boolean flag=false;
        
        try {
            ResultSet rs;
            PreparedStatement ps = handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            
            if(rs.next()){
                flag=rs.getBoolean("IS_CHALLENGE");
            }else{
                flag=false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return flag;
    }
    
    public long getChallengeFirstId(long groupid){
        long id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
                id=rs.getLong("ID1");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public long getChallengeSecondId(long groupid){
        long id = 0;
        ResultSet rs;
        PreparedStatement ps;
        try {
            ps = handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
                id=rs.getLong("ID2");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public void incrementUserWon(long userid,long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        int incr_points=0;
        int cur_points=0;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM players WHERE ID=? AND GROUPID=?");
            ps.setLong(1, userid);
            ps.setLong(2, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               cur_points=rs.getInt("WON");
               incr_points=cur_points+1;
            }
            ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET WON=? WHERE ID=?");
            ps.setInt(1, incr_points);
            ps.setLong(2, userid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void incrementUserLost(long userid,long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        int incr_points=0;
        int cur_points=0;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM players WHERE ID=? AND GROUPID=?");
            ps.setLong(1, userid);
            ps.setLong(2, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               cur_points=rs.getInt("LOST");
               incr_points=cur_points+1;
            }
            ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET LOST=? WHERE ID=?");
            ps.setInt(1, incr_points);
            ps.setLong(2, userid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void incrementGroupChallenges(long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        int incr_points=0;
        int cur_points=0;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               cur_points=rs.getInt("NCHALLENGES");
               incr_points=cur_points+1;
            }
            ps=handler.getConnection().prepareStatement("UPDATE TABLE groups SET NCHALLENGES=? WHERE ID=?");
            ps.setInt(1, incr_points);
            ps.setLong(2, groupid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkExistingGroup(long groupid) {
        boolean flag=false;
        
        try {
            ResultSet rs;
            PreparedStatement ps = handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            
            if(rs.next()){
                flag=true;
            }else{
                flag=false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return flag;
    }
    
    public void incrementUserPoints(long userid,long groupid, int points){
        
        ResultSet rs;
        PreparedStatement ps;
        int cur_points=0;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM players WHERE ID=? AND GROUPID=?");
            ps.setLong(1, userid);
            ps.setLong(2, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               cur_points=rs.getInt("POINTS");
               cur_points=cur_points+points;
            }
            ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET POINTS=? WHERE ID=? AND GROUPID=?");
            ps.setInt(1, cur_points);
            ps.setLong(2, userid);
            ps.setLong(3, groupid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void incrementUserShots(long userid,long groupid){
        
        ResultSet rs;
        PreparedStatement ps;
        int cur_shots=0;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM players WHERE ID=? AND GROUPID=?");
            ps.setLong(1, userid);
            ps.setLong(2, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               cur_shots=rs.getInt("SHOTS");
               cur_shots=cur_shots+1;
            }
            ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET SHOTS=? WHERE ID=? AND GROUPID=?");
            ps.setInt(1, cur_shots);
            ps.setLong(2, userid);
            ps.setLong(3, groupid);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetDuelOnGroup(long groupid){
        ResultSet rs;
        PreparedStatement ps;
        boolean IS_CHALLENGE=false;
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               IS_CHALLENGE=rs.getBoolean("ISCHALLENGE");
            }
            if(IS_CHALLENGE){
                ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET ISCHALLENGE=? WHERE ID=?");
                ps.setBoolean(1, false);
                ps.setLong(2, groupid);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void changeGroupLanguage(long groupid){
        ResultSet rs;
        PreparedStatement ps;
        String CUR_LANG = "";
        try {
            ps=handler.getConnection().prepareStatement("SELECT * FROM groups WHERE ID=?");
            ps.setLong(1, groupid);
            rs = ps.executeQuery();
            while(rs.next()){
               CUR_LANG=rs.getString("LANGUAGE");
            }
            if(CUR_LANG.equals("EN")){
                ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET LANGUAGE=? WHERE ID=?");
                ps.setString(1, "IT");
                ps.setLong(2, groupid);
                ps.executeUpdate();
            }else{
                ps=handler.getConnection().prepareStatement("UPDATE TABLE players SET LANGUAGE=? WHERE ID=?");
                ps.setString(1, "EN");
                ps.setLong(2, groupid);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConnectionHandler getHandler() {
        return handler;
    }
    
}