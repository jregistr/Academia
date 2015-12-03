package RatingInfo;


public class UserSave {
    public LoginSave loginInfo;
    public BattleSave battleInfo;

    public UserSave(LoginSave log, BattleSave bt){
        loginInfo = log;
        battleInfo = bt;
    }
}
