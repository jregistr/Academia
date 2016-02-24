package Models

import com.google.gson.JsonObject

class Battle extends Model{

    static hasOne = [blue:User,red:User]

    public static final String FOUGHT_PROPERTY = "fought"
    public static final String WINS_PROPERTY = "wins"
    public static final String SHIPS_KNOCKED_PROPERTY = "sKnocked"
    public static final String SHIPS_LOST_PROPERTY = "sLost"
    public static final String FIRED_PROPERTY = "fired"
    public static final String HITS_PROPERTY = "hits"
    public static final String RANK_PROPERTY = "rank"
    public static final String LADDER_POINTS_PROPERTY = "ladderPoints"
    public static final String LADDER_NEXT_PROPERTY = "ladderNext"
    public static final String WIN_RATIO_PROPERTY = "winRatio"
    public static final String AVERAGE_TURNS_PER_PROPERTY = "turnsPerBattle"
    public static final String ACCURACY_PROPERTY = "accuracy";
    public static final String KNOCKED_PER_LOST_PROPERTY = "knockedPerLost"

    private static final float RANK_START = 992.065f
    private static final float RANK_GROWTH_RATE = 0.7f

    private static final float BATTLES_VALUE = 50f
    private static final float WIN_VALUE = 350;
    private static final float SHIPS_KNOCKED_VALUE = 100
    private static final float SHIPS_LOST_VALUE = -25
    private static final float FIRED_VALUE = 5
    private static final float HIT_VALUE = 50
    private static final float TURNS_VALUE = 2
    private static final int   RANK_STAGE_CAP = 10
    private static final int   LADDER_CAP = 200000

    private static final String[] RANKS = [
            "Deck Washer","Deck Washer Boss","Marauder",
            "Sergeant","Corporal","Lance Corporal","Commander","Invincible","Conqueror","Invincible High Seas Ceaser"
    ]

    Date dateStarted
    Date dateEnded
    boolean blueWin
    int turns
    int knockedByBlue
    int lostByBlue
    int knockedByRed
    int lostByRed
    int firedByBlue
    int hitsByBlue
    int firedByRed
    int hitsByRed


    static constraints = {

    }

    public static String summary(User user){
        List<Battle> blues = findAllByBlue(user)
        List<Battle> red = findAllByRed(user)
        List<Battle> battles = null

        if(blues != null && blues.size() > 0 &&  red != null && red.size() > 0){
            battles = blues.plus(red)
        }else if(blues != null && blues.size()){
            battles = blues
        }else if(red != null && red.size() > 0){
            battles = red
        }

        int fought = (battles != null && battles.size() > 0) ? battles.size() : 0
        int wins = 0
        int sKnocked = 0
        int sLost = 0
        int fired = 0
        int hit = 0
        int turnsTotal = 0

        if(battles != null) {
            for (Battle battle : battles) {
                turnsTotal += battle.turns
                if (battle.blue.username.equals(user.username)) {//player was blue in said battle
                    sKnocked += battle.knockedByBlue
                    sLost += battle.lostByBlue
                    fired += battle.firedByBlue
                    hit += battle.hitsByBlue
                    if (battle.blueWin) {
                        wins++;
                    }
                } else {
                    sKnocked += battle.knockedByRed
                    sLost += battle.lostByRed
                    fired += battle.firedByRed
                    hit += battle.hitsByRed
                    if (!battle.blueWin) {
                        wins++;
                    }
                }
            }
        }

        int rating = rating(fought, wins, sKnocked, sLost, fired, hit, turnsTotal)
        JsonObject jsonObject = new JsonObject()
        jsonObject.addProperty(FOUGHT_PROPERTY, fought)
        jsonObject.addProperty(WINS_PROPERTY, wins)
        jsonObject.addProperty(SHIPS_KNOCKED_PROPERTY, sKnocked)
        jsonObject.addProperty(SHIPS_LOST_PROPERTY, sLost)
        jsonObject.addProperty(FIRED_PROPERTY, fired)
        jsonObject.addProperty(HITS_PROPERTY, hit)
        jsonObject.addProperty("Turns", turnsTotal)
        jsonObject.addProperty(RANK_PROPERTY, rank(rating))
        jsonObject.addProperty(LADDER_POINTS_PROPERTY, rating)
        jsonObject.addProperty(LADDER_NEXT_PROPERTY, ratingForNextRank(rating))
        jsonObject.addProperty(WIN_RATIO_PROPERTY, winRatio(fought, wins))
        jsonObject.addProperty(AVERAGE_TURNS_PER_PROPERTY, averageTurnsPerBattle(fought, turnsTotal))
        jsonObject.addProperty(ACCURACY_PROPERTY, accuracy(fired, hit))
        jsonObject.addProperty(KNOCKED_PER_LOST_PROPERTY, knockedPerLost(sKnocked, sLost))
        return jsonObject.toString()
    }

    private static def rating(int fought,int battlesWon,int sKnocked,int sLost,int fired,int hit,int turnsTotal) {
        int total = 0
        total += fought * BATTLES_VALUE
        total += battlesWon * WIN_VALUE
        total += sKnocked * SHIPS_KNOCKED_VALUE
        total += sLost * SHIPS_LOST_VALUE
        total += fired * FIRED_VALUE
        total += hit * HIT_VALUE;
        total += turnsTotal * TURNS_VALUE
        return total
    }

    private static def rankStage(int currentRating) {
        int stage = (int) Math.round(Math.log((float)currentRating / RANK_START) / Math.log(1 + RANK_GROWTH_RATE))
        if(stage < 0)
            stage = 0
        if(stage > RANK_STAGE_CAP)
            stage = RANK_STAGE_CAP
        return stage
    }

    private static float ratingForNextRank(int currentRating) {
        return (currentRating < LADDER_CAP) ? pointsForStage(rankStage(currentRating)+ 1) : LADDER_CAP
    }

    public static int pointsForStage(int stage) {
        //amountForStage = RANK_START(1 + RANK_GROWTH_RATE)^(STAGE)
        return (int)Math.round(RANK_START * Math.pow((1 + RANK_GROWTH_RATE),stage))
    }

    private static String rank(int rating) {
        return RANKS[rankStage(rating-1)]
    }

    private static float winRatio(int fought, int wins) {
        if(fought > 0)
            return (float)wins/(float)fought
        else
            return 0
    }

    private static int averageTurnsPerBattle(int fought,int turnsTotal) {
        if(fought > 0)
            return Math.round((float)turnsTotal/(float)fought)
        else
            return 0
    }

    private static float accuracy(int fired,int hit) {
        if(fired > 0)
            return (float) hit / (float) fired
        else
            return 0
    }

    private static float knockedPerLost(int knocked,int lost) {
        if(lost > 0)
            return (float) knocked / (float) lost
        else
            return 0
    }

    @Override
    public String toJson() {
        JsonObject jsonObject = new JsonObject()
        jsonObject.addProperty("DateStarted",dateStarted.toString())
        jsonObject.addProperty("DateEnded",dateEnded.toString())
        jsonObject.addProperty("BlueWin",blueWin)
        jsonObject.addProperty("Turns",turns)
        jsonObject.addProperty("KnockedByBlue", knockedByBlue)
        jsonObject.addProperty("LostByBlue", lostByBlue)
        jsonObject.addProperty("KnockedByRed", knockedByRed)
        jsonObject.addProperty("LostByRed", lostByRed)
        jsonObject.addProperty("FiredByBlue",firedByBlue)
        jsonObject.addProperty("HitsByBlue", hitsByBlue)
        jsonObject.addProperty("FiredByRed", firedByRed)
        jsonObject.addProperty("HitsByRed",hitsByRed)
        jsonObject.addProperty("BlueUser",this.blue.username)
        jsonObject.addProperty("RedUser", this.red.username)
        return jsonObject.toString()
    }

    @Override
    public String toString() {
        return "Battle{red=" + red.toString() +", blue=" + blue.toString() +", dateStarted=" + dateStarted + ", dateEnded=" + dateEnded +", blueWin=" + blueWin +", turns=" + turns +
                ", knockedByBlue=" + knockedByBlue +", lostByBlue=" + lostByBlue +", knockedByRed=" + knockedByRed +", lostByRed=" + lostByRed +", firedByBlue=" + firedByBlue +
                ", hitsByBlue=" + hitsByBlue +", firedByRed=" + firedByRed +", hitsByRed=" + hitsByRed +'}'
    }
}















