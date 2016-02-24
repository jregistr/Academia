package models;


import com.avaje.ebean.Expr;
import com.google.gson.JsonObject;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "BATTLES")
public class Battle extends Model{

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

    @Column(name = "DateStarted")
    private Date dateStarted;

    @Column(name = "DateEnded")
    private Date dateEnded;

    @Column(name = "BlueWin")
    private boolean blueWin;

    @Column(name = "Turns")
    private int turns;

    @Column(name = "KnockedBlue")
    private int knockedByBlue;

    @Column(name = "LostBlue")
    private int lostByBlue;

    @Column(name = "KnockedRed")
    private int knockedByRed;

    @Column(name = "LostRed")
    private int lostByRed;

    @Column(name = "FiredBlue")
    private int firedByBlue;

    @Column(name = "HitBlue")
    private int hitsByBlue;

    @Column(name = "FiredRed")
    private int firedByRed;

    @Column(name = "HitRed")
    private int hitsByRed;

    @Column(name = "BlueUser")
    private String blueUser;

    @Column(name = "RedUser")
    private String redUser;

    public Battle(){

    }

    public Battle(Date dateStarted, Date dateEnded, boolean blueWin, int turns, int knockedByBlue, int lostByBlue, int knockedByRed, int lostByRed, int firedByBlue, int hitsByBlue, int firedByRed, int hitsByRed, String blueUser, String redUser) {
        this.dateStarted = dateStarted;
        this.dateEnded = dateEnded;
        this.blueWin = blueWin;
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
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Date getDateEnded() {
        return dateEnded;
    }

    public void setDateEnded(Date dateEnded) {
        this.dateEnded = dateEnded;
    }

    public boolean isBlueWin() {
        return blueWin;
    }

    public void setBlueWin(boolean blueWin) {
        this.blueWin = blueWin;
    }

    public int getTurns() {
        return turns;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public int getKnockedByBlue() {
        return knockedByBlue;
    }

    public void setKnockedByBlue(int knockedByBlue) {
        this.knockedByBlue = knockedByBlue;
    }

    public int getLostByBlue() {
        return lostByBlue;
    }

    public void setLostByBlue(int lostByBlue) {
        this.lostByBlue = lostByBlue;
    }

    public int getKnockedByRed() {
        return knockedByRed;
    }

    public void setKnockedByRed(int knockedByRed) {
        this.knockedByRed = knockedByRed;
    }

    public int getLostByRed() {
        return lostByRed;
    }

    public void setLostByRed(int lostByRed) {
        this.lostByRed = lostByRed;
    }

    public int getFiredByBlue() {
        return firedByBlue;
    }

    public void setFiredByBlue(int firedByBlue) {
        this.firedByBlue = firedByBlue;
    }

    public int getHitsByBlue() {
        return hitsByBlue;
    }

    public void setHitsByBlue(int hitsByBlue) {
        this.hitsByBlue = hitsByBlue;
    }

    public int getFiredByRed() {
        return firedByRed;
    }

    public void setFiredByRed(int firedByRed) {
        this.firedByRed = firedByRed;
    }

    public int getHitsByRed() {
        return hitsByRed;
    }

    public void setHitsByRed(int hitsByRed) {
        this.hitsByRed = hitsByRed;
    }

    public String getBlueUser() {
        return blueUser;
    }

    public void setBlueUser(String blueUser) {
        this.blueUser = blueUser;
    }

    public String getRedUser() {
        return redUser;
    }

    public void setRedUser(String redUser) {
        this.redUser = redUser;
    }

    public static List<Battle> getBattlesForUser(String uName) {
        return new Finder<>(String.class, Battle.class).where().or(Expr.eq("BlueUser", uName), Expr.eq("RedUser", uName)).findList();
    }

    public static String fullHistory(String uName) {
        List<Battle> battles = getBattlesForUser(uName);
        int fought = battles.size();
        int wins = 0;
        int sKnocked = 0;
        int sLost = 0;
        int fired = 0;
        int hit = 0;
        int turnsTotal = 0;
        for (Battle battle : battles) {
            turnsTotal += battle.getTurns();
            if (battle.getBlueUser().equals(uName)) {//player was blue in said battle
                sKnocked += battle.getKnockedByBlue();
                sLost += battle.getLostByBlue();
                fired += battle.getFiredByBlue();
                hit += battle.getHitsByBlue();
                if (battle.isBlueWin()) {
                    wins++;
                }
            } else {
                sKnocked += battle.getKnockedByRed();
                sLost += battle.getLostByBlue();
                fired += battle.getFiredByRed();
                hit += battle.getHitsByRed();
                if (!battle.isBlueWin()) {
                    wins++;
                }
            }
        }
        int rating = rating(fought, wins, sKnocked, sLost, fired, hit, turnsTotal);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(FOUGHT_PROPERTY, fought);
        jsonObject.addProperty(WINS_PROPERTY, wins);
        jsonObject.addProperty(SHIPS_KNOCKED_PROPERTY, sKnocked);
        jsonObject.addProperty(SHIPS_LOST_PROPERTY, sLost);
        jsonObject.addProperty(FIRED_PROPERTY, fired);
        jsonObject.addProperty(HITS_PROPERTY, hit);
        jsonObject.addProperty("Turns", turnsTotal);
        jsonObject.addProperty(RANK_PROPERTY, rank(rating));
        jsonObject.addProperty(LADDER_POINTS_PROPERTY, rating);
        jsonObject.addProperty(LADDER_NEXT_PROPERTY, ratingForNextRank(rating));
        jsonObject.addProperty(WIN_RATIO_PROPERTY, winRatio(fought, wins));
        jsonObject.addProperty(AVERAGE_TURNS_PER_PROPERTY, averageTurnsPerBattle(fought, turnsTotal));
        jsonObject.addProperty(ACCURACY_PROPERTY, accuracy(fired, hit));
        jsonObject.addProperty(KNOCKED_PER_LOST_PROPERTY, knockedPerLost(sKnocked, sLost));
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

    public String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("DateStarted",this.getDateStarted().toString());
        jsonObject.addProperty("DateEnded",this.getDateEnded().toString());
        jsonObject.addProperty("BlueWin",this.isBlueWin());
        jsonObject.addProperty("Turns",this.getTurns());
        jsonObject.addProperty("KnockedByBlue", this.getKnockedByBlue());
        jsonObject.addProperty("LostByBlue", this.getLostByBlue());
        jsonObject.addProperty("KnockedByRed", this.getKnockedByRed());
        jsonObject.addProperty("LostByRed", this.getLostByRed());
        jsonObject.addProperty("FiredByBlue",this.getFiredByBlue());
        jsonObject.addProperty("HitsByBlue", this.getHitsByBlue());
        jsonObject.addProperty("FiredByRed", this.getFiredByRed());
        jsonObject.addProperty("HitsByRed",this.getHitsByRed());
        jsonObject.addProperty("BlueUser",this.getBlueUser());
        jsonObject.addProperty("RedUser", this.getRedUser());
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Battle{" +
                "dateStarted=" + dateStarted +
                ", dateEnded=" + dateEnded +
                ", blueWin=" + blueWin +
                ", turns=" + turns +
                ", knockedByBlue=" + knockedByBlue +
                ", lostByBlue=" + lostByBlue +
                ", knockedByRed=" + knockedByRed +
                ", lostByRed=" + lostByRed +
                ", firedByBlue=" + firedByBlue +
                ", hitsByBlue=" + hitsByBlue +
                ", firedByRed=" + firedByRed +
                ", hitsByRed=" + hitsByRed +
                ", blueUser='" + blueUser + '\'' +
                ", redUser='" + redUser + '\'' +
                '}';
    }
}
