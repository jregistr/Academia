package FleetGame;

import java.util.Random;


public  class Calculate {

    private static final int BATTLES_VALUE = 25;
    private static final int SHIPS_KNOCKED_OUT_VALUE = 60;
    private static final int SHIPS_LOST_VALUE = -45;
    private static final int BATTLES_WON_VALUE = 75;
    private static final float RANK_START = 200;
   // private static final float RANK_GROWTH = 5.15f;
    private static final float RANK_GROWTH = 0.75f;

    private static final String[] ranks = new String[]{"Deck Washer","Deck Washer Boss","Marauder",
            "Sergeant","Corporal","Bigger Corporal","Commander","Invincible","Conqueror","High Seas Ceaser"};

    public static int calculateBattleRating(int battFo,int shipsKnocked,int sLost,int battWon){
        int rating = 0;
        rating += battFo * BATTLES_VALUE;
        rating += shipsKnocked * SHIPS_KNOCKED_OUT_VALUE;
        rating += sLost * SHIPS_LOST_VALUE;
        rating += battWon * BATTLES_WON_VALUE;
        return rating;
    }

    public static String ratingToRatingName(int battleRating){
        // ln(rate/start)/ln(1+growth) = stage
        double stage =  Math.log(battleRating/RANK_START)/Math.log(1 + RANK_GROWTH);
        int index = (int)Math.round(stage);

        if(index >= (ranks.length-1))
            index = ranks.length-1;
        else if(index < 0)
            index = 0;

        return ranks[index];
    }

    public static String makeID(){
        char[] alph = "A1B2C3D4E5F6G7H8I9J0KLMNOPQRSTUVWXYZ".toCharArray();
        String id = "";
        for(int i = 0; i<20; i++){
            Random rand = new Random();
            id += alph[rand.nextInt(alph.length)];
        }
        return  id;
    }
}
