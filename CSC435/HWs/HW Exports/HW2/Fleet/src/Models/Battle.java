package Models;

import java.sql.*;
import java.util.ArrayList;

public class Battle extends Model {
    public static final String BATTLES_TABLE_NAME = "battles";
    public static final String WINNER_BLUE = "BLUE";
    public static final String WINNGER_RED = "RED";
    public static final String DATE_STARTED_COLUMN = "date_started";
    public static final String DATE_ENDED_COLUMN = "date_ended";
    public static final String WINNER_COLUMN = "winner";
    public static final String TURNS_COLUMN = "turns";
    public static final String KNOCKED_BY_BLUE_COLUMN = "knocked_by_blue";
    public static final String LOST_BY_BLUE_COLUMN = "lost_by_blue";
    public static final String KNOCKED_BY_RED_COLUMN = "knocked_by_red";
    public static final String LOST_BY_RED_COLUMN = "lost_by_red";
    public static final String FIRED_BY_BLUE_COLUMN = "fired_by_blue";
    public static final String HITS_BY_BLUE_COLUMN = "hits_by_blue";
    public static final String FIRED_BY_RED_COLUMN = "fired_by_red";
    public static final String HITS_BY_RED_COLUMN = "hits_by_red";
    public static final String BLUE_USER_COLUMN = "blue_user";
    public static final String RED_USER_COLUMN = "red_user";
    public static final String BATTLE_ID_COLUMN = "battle_id";


    private Timestamp dateStarted;
    private Timestamp dateEnded;
    private String winner;
    private int turns;
    private int knockedByBlue;
    private int lostByBlue;
    private int knockedByRed;
    private int lostByRed;
    private int firedByBlue;
    private int hitsByBlue;
    private int firedByRed;
    private int hitsByRed;
    private int blueUser;
    private int redUser;
    private int battleID;


    public Battle(Timestamp dateStarted, Timestamp dateEnded, boolean winnr, int turns, int knockedByBlue, int lostByBlue, int knockedByRed, int lostByRed, int firedByBlue, int hitsByBlue, int firedByRed, int hitsByRed, int blueUser, int redUser) {
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        setWinner(winnr);
        this.turns = turns;
        this.knockedByBlue = knockedByBlue;
        this.lostByBlue = lostByBlue;
        this.knockedByRed = knockedByRed;
        this.lostByRed = lostByRed;
        this.firedByBlue = firedByBlue;
        this.hitsByBlue = hitsByBlue;
        this.firedByRed = firedByRed;
        this.hitsByRed = hitsByRed;
        this.blueUser = blueUser;
        this.redUser = redUser;
        this.battleID = 0;
    }

    private Battle(Timestamp dateStarted, Timestamp dateEnded, boolean winnr, int turns, int knockedByBlue, int lostByBlue, int knockedByRed, int lostByRed, int firedByBlue, int hitsByBlue, int firedByRed, int hitsByRed, int blueUser, int redUser, int battleID) {
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        setWinner(winnr);
        this.turns = turns;
        this.knockedByBlue = knockedByBlue;
        this.lostByBlue = lostByBlue;
        this.knockedByRed = knockedByRed;
        this.lostByRed = lostByRed;
        this.firedByBlue = firedByBlue;
        this.hitsByBlue = hitsByBlue;
        this.firedByRed = firedByRed;
        this.hitsByRed = hitsByRed;
        this.blueUser = blueUser;
        this.redUser = redUser;
        this.battleID = battleID;
    }

    public Timestamp getDateStarted() {
        return dateStarted;
    }

    public Timestamp getDateEnded() {
        return dateEnded;
    }

    public String getWinner() {
        return winner;
    }

    public int getTurns() {
        return turns;
    }

    public int getKnockedByBlue() {
        return knockedByBlue;
    }

    public int getLostByBlue() {
        return lostByBlue;
    }

    public int getKnockedByRed() {
        return knockedByRed;
    }

    public int getLostByRed() {
        return lostByRed;
    }

    public int getFiredByBlue() {
        return firedByBlue;
    }

    public int getHitsByBlue() {
        return hitsByBlue;
    }

    public int getFiredByRed() {
        return firedByRed;
    }

    public int getHitsByRed() {
        return hitsByRed;
    }

    public int getBlueUser() {
        return blueUser;
    }

    public int getRedUser() {
        return redUser;
    }

    public int getBattleID() {
        return battleID;
    }



    private void setWinner(boolean blueWin){
        winner = (blueWin) ? WINNER_BLUE : WINNGER_RED;
    }

    public boolean save(){
        if(blueUser > 0 && redUser > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                boolean newEntry = true;
                if(battleID > 0){
                    Statement statement = connection.createStatement();
                    String testQuery = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BATTLE_ID_COLUMN + " = " + battleID;
                    ResultSet set = statement.executeQuery(testQuery);
                    newEntry = !set.next();
                }
                if(newEntry) {
                    String queryString = "INSERT INTO " + BATTLES_TABLE_NAME + "(" + buildCommaSeperated(DATE_STARTED_COLUMN, DATE_ENDED_COLUMN, WINNER_COLUMN, TURNS_COLUMN,
                            KNOCKED_BY_BLUE_COLUMN, LOST_BY_BLUE_COLUMN, KNOCKED_BY_RED_COLUMN, LOST_BY_RED_COLUMN,FIRED_BY_BLUE_COLUMN,HITS_BY_BLUE_COLUMN,FIRED_BY_RED_COLUMN,HITS_BY_RED_COLUMN,BLUE_USER_COLUMN, RED_USER_COLUMN, BATTLE_ID_COLUMN) +
                            ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setTimestamp(1,dateStarted);
                    preparedStatement.setTimestamp(2, dateEnded);
                    preparedStatement.setString(3, winner);
                    preparedStatement.setInt(4, turns);
                    preparedStatement.setInt(5, knockedByBlue);
                    preparedStatement.setInt(6, lostByBlue);
                    preparedStatement.setInt(7, knockedByRed);
                    preparedStatement.setInt(8, lostByRed);
                    preparedStatement.setInt(9, firedByBlue);
                    preparedStatement.setInt(10,hitsByBlue);
                    preparedStatement.setInt(11,firedByRed);
                    preparedStatement.setInt(12,hitsByRed);
                    preparedStatement.setInt(13, blueUser);
                    preparedStatement.setInt(14, redUser);
                    preparedStatement.setNull(15,Types.NULL);
                    System.out.println("Prepared: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error saving battle");
                return false;
            }
        }else {
            System.out.println("Blue user or Red user is 0 or less. Such a user can't exist");
            return false;
        }
    }

    public static boolean save(Battle battle){
        if(battle.getBlueUser() > 0 && battle.getRedUser() > 0){
            try{
                Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                boolean newEntry = true;
                if(battle.getBattleID() > 0){
                    Statement statement = connection.createStatement();
                    String testQuery = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BATTLE_ID_COLUMN + " = " + battle.getBattleID();
                    ResultSet set = statement.executeQuery(testQuery);
                    newEntry = !set.next();
                }
                if(newEntry) {
                    String queryString = "INSERT INTO " + BATTLES_TABLE_NAME + "(" + buildCommaSeperated(DATE_STARTED_COLUMN, DATE_ENDED_COLUMN, WINNER_COLUMN, TURNS_COLUMN,
                            KNOCKED_BY_BLUE_COLUMN, LOST_BY_BLUE_COLUMN, KNOCKED_BY_RED_COLUMN, LOST_BY_RED_COLUMN,FIRED_BY_BLUE_COLUMN,HITS_BY_BLUE_COLUMN,FIRED_BY_RED_COLUMN,HITS_BY_RED_COLUMN,BLUE_USER_COLUMN, RED_USER_COLUMN, BATTLE_ID_COLUMN) +
                            ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(queryString);
                    preparedStatement.setTimestamp(1,battle.getDateEnded());
                    preparedStatement.setTimestamp(2, battle.getDateEnded());
                    preparedStatement.setString(3, battle.getWinner());
                    preparedStatement.setInt(4, battle.getTurns());
                    preparedStatement.setInt(5, battle.knockedByBlue);
                    preparedStatement.setInt(6, battle.lostByBlue);
                    preparedStatement.setInt(7, battle.knockedByRed);
                    preparedStatement.setInt(8, battle.lostByRed);
                    preparedStatement.setInt(9, battle.firedByBlue);
                    preparedStatement.setInt(10,battle.hitsByBlue);
                    preparedStatement.setInt(11,battle.firedByRed);
                    preparedStatement.setInt(12,battle.hitsByRed);
                    preparedStatement.setInt(13, battle.getBlueUser());
                    preparedStatement.setInt(14, battle.getRedUser());
                    preparedStatement.setNull(15,Types.NULL);
                    System.out.println("Prepared: " + preparedStatement.toString());
                    preparedStatement.executeUpdate();
                }
                return true;
            }catch (SQLException e){
                e.printStackTrace();
                System.out.println("Error saving battle");
                return false;
            }
        }else {
            System.out.println("Blue user or Red user is 0 or less. Such a user can't exist");
            return false;
        }
    }

    public static Battle[] retrieve(int uID){
        if(uID > 0) {
           try{
               ArrayList<Battle> battles = new ArrayList<Battle>();;
               Connection connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
               Statement statement = connection.createStatement();
               String queryString = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BLUE_USER_COLUMN + " = " + uID + " OR " + RED_USER_COLUMN + " = " + uID;
               System.out.println(queryString);
               ResultSet set = statement.executeQuery(queryString);
               while (set.next()) {
                   Timestamp ds = set.getTimestamp(DATE_STARTED_COLUMN);
                   Timestamp de = set.getTimestamp(DATE_ENDED_COLUMN);
                   String w = set.getString(WINNER_COLUMN);
                   int t = set.getInt(TURNS_COLUMN);
                   int bk = set.getInt(KNOCKED_BY_BLUE_COLUMN);
                   int bl = set.getInt(LOST_BY_BLUE_COLUMN);
                   int rk = set.getInt(KNOCKED_BY_RED_COLUMN);
                   int rl = set.getInt(LOST_BY_RED_COLUMN);
                   int fb = set.getInt(FIRED_BY_BLUE_COLUMN);
                   int hb = set.getInt(HITS_BY_BLUE_COLUMN);
                   int fr = set.getInt(FIRED_BY_RED_COLUMN);
                   int hr = set.getInt(HITS_BY_RED_COLUMN);
                   int bu = set.getInt(BLUE_USER_COLUMN);
                   int ru = set.getInt(RED_USER_COLUMN);
                   int bi = set.getInt(BATTLE_ID_COLUMN);
                   battles.add(new Battle(ds,de,w.equals(WINNER_BLUE),t,bk,bl,rk,rl,fb,hb,fr,hr,bu,ru,bi));
               }
               return (battles.size() > 0) ? battles.toArray(new Battle[battles.size()]) : null;
           }catch (SQLException e) {
               e.printStackTrace();
               return null;
           }
        }else {
            return null;
        }
    }

    public static int totalKnockouts(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        total += b.getKnockedByBlue();
                    }else if(b.getRedUser() == uid){
                        total += b.getKnockedByRed();
                    }
                }
            }
            return total;
        }else {
            return 0;
        }
    }

    public static int totaShipslLost(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        total += b.getLostByBlue();
                    }else if(b.getRedUser() == uid){
                        total += b.getLostByRed();
                    }
                }
            }
            return total;
        }else {
            return 0;
        }
    }

    public static int totalTurns(Battle[] battles){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                   total += b.getTurns();
                }
            }
            return total;
        }else {
            return 0;
        }
    }

    public static int totalBattles(Battle[] battles){
        if(battles != null && battles.length > 0){
           return battles.length;
        }else {
            return 0;
        }
    }

    public static int totalWon(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        if(b.getWinner().equals(WINNER_BLUE)){
                            total += 1;
                        }
                    }else if(b.getRedUser() == uid){
                        if(b.getWinner().equals(WINNGER_RED)){
                            total += 1;
                        }
                    }
                }
            }
            return total;
        }else {
            return 0;
        }
    }

    public static float winRatio(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            float total = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        if(b.getWinner().equals(WINNER_BLUE)){
                            total += 1;
                        }
                    }else if(b.getRedUser() == uid){
                        if(b.getWinner().equals(WINNGER_RED)){
                            total += 1;
                        }
                    }
                }
            }
            return total/battles.length;
        }else {
            return 0;
        }
    }

    public static float averageTurnsPerBattle(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                   total += b.getTurns();
                }
            }
            return total/battles.length;
        }else {
            return 0;
        }
    }

    public static float shipsKnockedPerLost(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            float totalKnocked = 0;
            float totalLost = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        totalKnocked += b.getKnockedByBlue();
                        totalLost += b.getLostByBlue();
                    }else if(b.getRedUser() == uid){
                        totalKnocked += b.getKnockedByRed();
                        totalLost += b.getLostByRed();
                    }
                }
            }
            return totalKnocked/totalLost;
        }else {
            return 0;
        }
    }

    public static float totalShotsFired(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            int total = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        total += b.getFiredByBlue();
                    }else if(b.getRedUser() == uid){
                        total += b.getFiredByRed();
                    }
                }
            }
            return total;
        }else {
            return 0;
        }
    }

    public static float totalAccuracy(Battle[] battles,int uid){
        if(battles != null && battles.length > 0){
            float totalFired = 0;
            float totalHit = 0;
            for (Battle b : battles) {
                if(b != null){
                    if(b.getBlueUser() == uid){
                        totalFired += b.getFiredByBlue();
                        totalHit += b.getHitsByBlue();
                    }else if(b.getRedUser() == uid){
                        totalFired += b.getFiredByRed();
                        totalHit += b.getHitsByRed();
                    }
                }
            }
            return totalHit/totalFired;
        }else {
            return 0;
        }
    }

   /* @Override
    public String toString() {
        return String.format("%s:%s,%s:%s,%s:%s,%s:%s,%s:%s,%s:%s,%s:%s,%s:%s,%s:%s,%s:%s", DATE_STARTED_COLUMN,dateStarted, DATE_ENDED_COLUMN,dateEnded, WINNER_COLUMN,winner,
                TURNS_COLUMN,turns, KNOCKED_BY_BLUE_COLUMN,knockedByBlue,LOST_BY_BLUE_COLUMN,lostByBlue, KNOCKED_BY_RED_COLUMN,knockedByRed, LOST_BY_RED_COLUMN,lostByRed,
                BLUE_USER_COLUMN,blueUser, RED_USER_COLUMN,redUser);
    }*/

    @Override
    public String toString() {
        return "Battle{" +
                "dateStarted=" + dateStarted +
                ", dateEnded=" + dateEnded +
                ", winner='" + winner + '\'' +
                ", turns=" + turns +
                ", knockedByBlue=" + knockedByBlue +
                ", lostByBlue=" + lostByBlue +
                ", knockedByRed=" + knockedByRed +
                ", lostByRed=" + lostByRed +
                ", blueUser=" + blueUser +
                ", redUser=" + redUser +
                ", battleID=" + battleID +
                '}';
    }
}























