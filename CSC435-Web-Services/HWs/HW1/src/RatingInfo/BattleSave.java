package RatingInfo;


public class BattleSave {
    public int battles;
    public int knockOuts;
    public int shipsLost;
    public int won;
    public int rating;
    public String rank;

    public BattleSave(int bt,int ko,int sl,int wo,int ra,String ran){
        battles = bt;
        knockOuts = ko;
        shipsLost = sl;
        won = wo;
        rating = ra;
        rank = ran;
    }
}
