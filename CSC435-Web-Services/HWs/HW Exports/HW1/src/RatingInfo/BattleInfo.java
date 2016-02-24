package RatingInfo;


import com.google.gson.*;

public class BattleInfo {

    private static final int BATTLES_VALUE = 25;
    private static final int SHIPS_KNOCKED_OUT_VALUE = 60;
    private static final int SHIPS_LOST_VALUE = -45;
    private static final int BATTLES_WON_VALUE = 75;
    private static final float RANK_START = 200;
    private static final float RANK_GROWTH = 0.5f;

    private static final String[] ranks = new String[]{"Deck Washer","Deck Washer Boss","Marauder",
            "Sergeant","Corporal","Bigger Corporal","Commander","Invincible","Conqueror","High Seas Ceaser"};

    public static final String BATTLES_NAME = "Battles";
    public static final String SHIPS_KNOCKED_NAME="Knockouts";
    public static final String SHIPS_LOST_NAME="ShipsLost";
    public static final String WON_NAME = "Won";
    public static final String RATING_NAME = "Rating";

    private int battlesFought;
    private int shipsKnockedOut;
    private int shipsLost;
    private int battlesWon;

    private  float battleRating;

    public  BattleInfo(int batFought,int shipKnocked,int sLost,int won){
        battlesFought = batFought;
        shipsKnockedOut = shipKnocked;
        shipsLost = sLost;
        battlesWon = won;
    }

    public int getBattlesFought() {
        return battlesFought;
    }


    public int getShipsKnockedOut() {
        return shipsKnockedOut;
    }


    public int getShipsLost() {
        return shipsLost;
    }

    public int getBattlesWon() {
        return battlesWon;
    }


    public void calculateBattleRating(){
        int rating = 0;
        rating += battlesFought * BATTLES_VALUE;
        rating += shipsKnockedOut * SHIPS_KNOCKED_OUT_VALUE;
        rating += shipsLost * SHIPS_LOST_VALUE;
        rating += battlesWon * BATTLES_WON_VALUE;
        battleRating = rating;
    }

    public String ratingToRatingName(){
        // ln(rate/start)/ln(1+growth) = stage
        double stage =  Math.log(battleRating/RANK_START)/Math.log(1 + RANK_GROWTH);
        int index = (int)Math.round(stage);

        if(index >= (ranks.length-1))
            index = ranks.length-1;
        else if(index < 0)
            index = 0;

        return ranks[index];
    }

    public String convertToJSON(){

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(BATTLES_NAME,String.valueOf(battlesFought));
        jsonObject.addProperty(SHIPS_KNOCKED_NAME,String.valueOf(shipsKnockedOut));
        jsonObject.addProperty(SHIPS_LOST_NAME,String.valueOf(shipsLost));
        jsonObject.addProperty(WON_NAME,String.valueOf(battlesWon));
        jsonObject.addProperty(RATING_NAME,String.valueOf(battleRating));
        jsonObject.addProperty("Rank",String.valueOf(ratingToRatingName()));
        return jsonObject.toString();
       // return  jsonObject.toString();
    }

}
