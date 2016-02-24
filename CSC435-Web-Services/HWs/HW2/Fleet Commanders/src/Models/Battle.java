package Models;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.*;
import java.util.*;
import java.util.Date;

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

    public static final String FOUGHT_PROPERTY = "fought";
    public static final String WINS_PROPERTY = "wins";
    public static final String SHIPS_KNOCKED_PROPERTY = "sKnocked";
    public static final String SHIPS_LOST_PROPERTY = "sLost";
    public static final String FIRED_PROPERTY = "fired";
    public static final String HITS_PROPERTY = "hits";
    public static final String RANK_PROPERTY = "rank";
    public static final String LADDER_POINTS_PROPERTY = "ladderPoints";
    public static final String LADDER_NEXT_PROPERTY = "ladderNext";
    public static final String WIN_RATIO_PROPERTY = "winRatio";
    public static final String AVERAGE_TURNS_PER_PROPERTY = "turnsPerBattle";
    public static final String ACCURACY_PROPERTY = "accuracy";
    public static final String KNOCKED_PER_LOST_PROPERTY = "knockedPerLost";



    private static final float RANK_START = 992.065f;
    private static final float RANK_GROWTH_RATE = 0.7f;

    private static final float BATTLES_VALUE = 50f;
    private static final float WIN_VALUE = 350;
    private static final float SHIPS_KNOCKED_VALUE = 100;
    private static final float SHIPS_LOST_VALUE = -25;
    private static final float FIRED_VALUE = 5;
    private static final float HIT_VALUE = 50;
    private static final float TURNS_VALUE = 2;
    private static final int RANK_STAGE_CAP = 10;
    private static final int LADDER_CAP = 200000;

    private static final String[] RANKS = new String[]{
            "Deck Washer","Deck Washer Boss","Marauder",
            "Sergeant","Corporal","Lance Corporal","Commander","Invincible","Conqueror","Invincible High Seas Ceaser"
    };


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
            Connection connection = null;
            Statement statement = null;
            try{
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                boolean newEntry = true;
                if(battleID > 0){
                    statement = connection.createStatement();
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
               // e.printStackTrace();
                System.out.println("Error saving battle");
                return false;
            }finally {
                closeConnection(connection);
                closeStatement(statement);
            }
        }else {
            System.out.println("Blue user or Red user is 0 or less. Such a user can't exist");
            return false;
        }
    }

    public static boolean save(Battle battle){
        if(battle.getBlueUser() > 0 && battle.getRedUser() > 0){
            Connection connection = null;
            Statement statement = null;
            PreparedStatement preparedStatement = null;
            try{
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                boolean newEntry = true;
                if(battle.getBattleID() > 0){
                    statement = connection.createStatement();
                    String testQuery = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BATTLE_ID_COLUMN + " = " + battle.getBattleID();
                    ResultSet set = statement.executeQuery(testQuery);
                    newEntry = !set.next();
                }
                if(newEntry) {
                    String queryString = "INSERT INTO " + BATTLES_TABLE_NAME + "(" + buildCommaSeperated(DATE_STARTED_COLUMN, DATE_ENDED_COLUMN, WINNER_COLUMN, TURNS_COLUMN,
                            KNOCKED_BY_BLUE_COLUMN, LOST_BY_BLUE_COLUMN, KNOCKED_BY_RED_COLUMN, LOST_BY_RED_COLUMN,FIRED_BY_BLUE_COLUMN,HITS_BY_BLUE_COLUMN,FIRED_BY_RED_COLUMN,HITS_BY_RED_COLUMN,BLUE_USER_COLUMN, RED_USER_COLUMN, BATTLE_ID_COLUMN) +
                            ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(queryString);
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
                //e.printStackTrace();
                System.out.println("Error saving battle");
                return false;
            }finally {
                closeConnection(connection);
                closeStatement(statement);
                closePreparedStatement(preparedStatement);
            }
        }else {
            System.out.println("Blue user or Red user is 0 or less. Such a user can't exist");
            return false;
        }
    }

    public static Battle[] retrieve(int uID){
        if(uID > 0) {
            Connection connection = null;
            Statement statement = null;
            ResultSet set = null;
           try{
               ArrayList<Battle> battles = new ArrayList<Battle>();;
               connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
               statement = connection.createStatement();
               String queryString = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BLUE_USER_COLUMN + " = " + uID + " OR " + RED_USER_COLUMN + " = " + uID;
               //System.out.println(queryString);
               set = statement.executeQuery(queryString);
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
           }finally {
               closeConnection(connection);
               closeStatement(statement);
               closeResultset(set);
           }
        }else {
            return null;
        }
    }

    public static String retrieveToJson(int uID){
        if(uID > 0) {
            Connection connection = null;
            Statement statement = null;
            ResultSet set = null;
            try {
                //ArrayList<Battle> battles = new ArrayList<Battle>();;
                ArrayList<JsonObject> searches = new ArrayList<JsonObject>();
                connection = DriverManager.getConnection(DB_LINK, DB_USER, DB_PASS);
                statement = connection.createStatement();
                String queryString = "SELECT * FROM " + BATTLES_TABLE_NAME + " WHERE " + BLUE_USER_COLUMN + " = " + uID + " OR " + RED_USER_COLUMN + " = " + uID;
                //System.out.println(queryString);
                set = statement.executeQuery(queryString);
                while (set.next()) {
                    JsonObject object = new JsonObject();
                    object.addProperty(DATE_STARTED_COLUMN,set.getTimestamp(DATE_STARTED_COLUMN).toString());
                    object.addProperty(DATE_ENDED_COLUMN,set.getTimestamp(DATE_ENDED_COLUMN).toString());
                    object.addProperty(WINNER_COLUMN, set.getString(WINNER_COLUMN));
                    object.addProperty(TURNS_COLUMN, set.getInt(TURNS_COLUMN));
                    object.addProperty(KNOCKED_BY_BLUE_COLUMN, set.getInt(KNOCKED_BY_BLUE_COLUMN));
                    object.addProperty(LOST_BY_BLUE_COLUMN, set.getInt(LOST_BY_BLUE_COLUMN));
                    object.addProperty(KNOCKED_BY_RED_COLUMN, set.getInt(KNOCKED_BY_RED_COLUMN));
                    object.addProperty(LOST_BY_RED_COLUMN, set.getInt(LOST_BY_RED_COLUMN));
                    object.addProperty(FIRED_BY_BLUE_COLUMN, set.getInt(FIRED_BY_BLUE_COLUMN));
                    object.addProperty(HITS_BY_BLUE_COLUMN, set.getInt(HITS_BY_BLUE_COLUMN));
                    object.addProperty(FIRED_BY_RED_COLUMN, set.getInt(FIRED_BY_RED_COLUMN));
                    object.addProperty(HITS_BY_RED_COLUMN, set.getInt(HITS_BY_RED_COLUMN));
                    User bu = User.retrieve(set.getInt(BLUE_USER_COLUMN));
                    User ru = User.retrieve(set.getInt(RED_USER_COLUMN));
                    if(bu != null)
                        object.addProperty(BLUE_USER_COLUMN,bu.getUserName());
                    else
                        object.addProperty(BLUE_USER_COLUMN, "null");

                    if(ru != null)
                        object.addProperty(RED_USER_COLUMN, ru.getUserName());
                    else
                        object.addProperty(RED_USER_COLUMN, "null");
                    searches.add(object);
                }
                if(searches != null && !searches.isEmpty()){
                    HashMap<String, JsonObject[]> map = new HashMap<String, JsonObject[]>();
                    map.put(BATTLES_TABLE_NAME, searches.toArray(new JsonObject[searches.size()]));
                    return new Gson().toJson(map);
                }else {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("error", "none");
                    return new Gson().toJson(map);
                }
            }catch (SQLException e) {
                e.printStackTrace();
                return null;
            }finally {
                closeConnection(connection);
                closeStatement(statement);
                closeResultset(set);
            }
        }else {
            return null;
        }
    }

    public static String fullHistory(int uid) {
        Battle[] battles = retrieve(uid);
        int fought = battles != null ? battles.length : 0;
        int wins = 0;
        int sKnocked = 0;
        int sLost = 0;
        int fired = 0;
        int hit = 0;
        int turnsTotal = 0;
        if(battles != null && battles.length > 0) {
            for (Battle battle : battles) {
                turnsTotal += battle.getTurns();
                if (battle.getBlueUser() == uid) {//player was blue in said battle
                    sKnocked += battle.getKnockedByBlue();
                    sLost += battle.getLostByBlue();
                    fired += battle.getFiredByBlue();
                    hit += battle.getHitsByBlue();
                    if (battle.getWinner().equals(WINNER_BLUE)) {
                        wins++;
                    }
                } else {
                    sKnocked += battle.getKnockedByRed();
                    sLost += battle.getLostByBlue();
                    fired += battle.getFiredByRed();
                    hit += battle.getHitsByRed();
                    if (battle.getWinner().equals(WINNGER_RED)) {
                        wins++;
                    }
                }
            }
        }
        int rating = rating(fought, wins, sKnocked, sLost, fired, hit, turnsTotal);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(FOUGHT_PROPERTY,fought);
        jsonObject.addProperty(WINS_PROPERTY,wins);
        jsonObject.addProperty(SHIPS_KNOCKED_PROPERTY,sKnocked);
        jsonObject.addProperty(SHIPS_LOST_PROPERTY, sLost);
        jsonObject.addProperty(FIRED_PROPERTY, fired);
        jsonObject.addProperty(HITS_PROPERTY, hit);
        jsonObject.addProperty(TURNS_COLUMN, turnsTotal);
        jsonObject.addProperty(RANK_PROPERTY,rank(rating));
        jsonObject.addProperty(LADDER_POINTS_PROPERTY,rating);
        jsonObject.addProperty(LADDER_NEXT_PROPERTY,ratingForNextRank(rating));
        jsonObject.addProperty(WIN_RATIO_PROPERTY,winRatio(fought, wins));
        jsonObject.addProperty(AVERAGE_TURNS_PER_PROPERTY,averageTurnsPerBattle(fought, turnsTotal));
        jsonObject.addProperty(ACCURACY_PROPERTY,accuracy(fired, hit));
        jsonObject.addProperty(KNOCKED_PER_LOST_PROPERTY,knockedPerLost(sKnocked,sLost));
        return jsonObject.toString();
    }

    private static int rating(int fought,int battlesWon,int sKnocked,int sLost,int fired,int hit,int turnsTotal) {
        int total = 0;
        total += fought * BATTLES_VALUE;
        total += battlesWon * WIN_VALUE;
        total += sKnocked * SHIPS_KNOCKED_VALUE;
        total += sLost * SHIPS_LOST_VALUE;
        total += fired * FIRED_VALUE;
        total += hit * HIT_VALUE;
        total += turnsTotal * TURNS_VALUE;
        return total;
    }

    private static int rankStage(int currentRating) {
        // ln(rate/start)/ln(1+growth) = stage
        int stage = (int) Math.round(Math.log((float)currentRating / RANK_START) / Math.log(1 + RANK_GROWTH_RATE));
        if(stage < 0)
            stage = 0;
        if(stage > RANK_STAGE_CAP)
            stage = RANK_STAGE_CAP;
        return stage;
    }

    private static float ratingForNextRank(int currentRating) {
        return (currentRating < LADDER_CAP) ? pointsForStage(rankStage(currentRating)+ 1) : LADDER_CAP;
    }

    public static int pointsForStage(int stage) {
        //amountForStage = RANK_START(1 + RANK_GROWTH_RATE)^(STAGE)
        return (int)Math.round(RANK_START * Math.pow((1 + RANK_GROWTH_RATE),stage));
    }

    private static String rank(int rating) {
        return RANKS[rankStage(rating-1)];
    }

    private static float winRatio(int fought, int wins) {
        return (float)wins/(float)fought;
    }

    private static int averageTurnsPerBattle(int fought,int turnsTotal) {
        return Math.round((float)turnsTotal/(float)fought);
    }

    private static float accuracy(int fired,int hit) {
        return (float) hit / (float) fired;
    }

    private static float knockedPerLost(int knocked,int lost) {
        return (float) knocked / (float) lost;
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



    public static int rating(Battle[] battles,int uid) {
        return 0;
    }
    public static String rank(Battle[] battles,int uid) {
        return "Write the rank calculator jeff";
    }


    public static void DoRandomBattle(){
        Random random = new Random();
        int blueU = random.nextInt(7) + 1;
        int redU = random.nextInt(7) + 1;
        boolean blueWin = random.nextInt(100) > 50;

        int blueKnocked = 0;
        int blueLost = 0;
        int redKnocked = 0;
        int redLost = 0;
        int blueFired = random.nextInt(30);
        int blueHit = random.nextInt(30);
        int redFired = random.nextInt(30);
        int redHit = random.nextInt(30);

        if(blueWin){
            blueKnocked = 5;
            blueLost = random.nextInt(4);
            redKnocked = blueLost;
            redLost = 5;
        }else {
            redKnocked = 5;
            redLost = random.nextInt(4);
            blueKnocked = blueLost;
            blueLost = 5;
        }
        Battle battle = new Battle(new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()),blueWin,random.nextInt(30),blueKnocked,blueLost,redKnocked,redLost,blueFired,blueHit,redFired,redHit,blueU,redU);
        battle.save();
    }

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























