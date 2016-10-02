package database;

/**
 *
 * @author Luca
 */
public class CreationStrings {
    public static final String createDatabase = "CREATE DATABASE IF NOT EXISTS ruleta;";
    public static final String createPlayersTable = "CREATE TABLE IF NOT EXISTS players(ID BIGINT NOT NULL, GROUPID BIGINT NOT NULL, WON INTEGER NOT NULL DEFAULT 0, LOST INTEGER NOT NULL DEFAULT 0, POINTS INTEGER NOT NULL DEFAULT 0, LAST DATE DEFAULT NULL, SHOTS INTEGER DEFAULT 0 NOT NULL, PRIMARY KEY(ID,GROUPID));";
    public static final String createGroupsTable = "CREATE TABLE IF NOT EXISTS groups(ID BIGINT NOT NULL UNIQUE, ISCHALLENGE BOOLEAN, ID1 BIGINT DEFAULT NULL, ID2 BIGINT DEFAULT NULL, NCHALLENGES INTEGER DEFAULT 0, LANGUAGE VARCHAR(2) DEFAULT 'EN', ROUND INTEGER DEFAULT 0, PRIMARY KEY(ID), FOREIGN KEY(ID) REFERENCES players(GROUPID));";
}