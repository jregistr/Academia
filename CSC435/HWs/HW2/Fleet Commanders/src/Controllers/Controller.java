package Controllers;

import Models.*;
import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Controller extends HttpServlet {

    protected static final String WELCOME_SERVLET = "Welcome";
    protected static final String PROFILE_SERVLET = "Profile";

    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER= "password";
    public static final String DISPLAYNAME_PARAMETER = "displayname";
    public static final String CONFIRM_PASS_PARAMETER = "confpass";

    public static final String USER_ID_PARAMETER = "UID";
    public static final String VIEWING_ID_PARAMETER = "viewing";

    public static final String USER_PARAMETER = "user";
    public static final String USERS_PARAMETER = "users";
    public static final String TOWARDS_USER_PARAMETER = "target";
    public static final String FIND_USERS_LIKE_PARAMETER = "begins";
    public static final String POST_COMMENT_PARAMETER = "comment";
    public static final String SHOW_RESULT_PARAMETER = "showResult";
    public static final String VIEWING_USER_INFO = "viewUser";
    public static final String VIEWING_USER_CUSTOMIZATION = "viewingCust";

    public static final String IMAGES_PROPERTY = "images";


    /*public static final String FULL_HISTORY_PARAMETER = "fullHistory";
    public static final String FRIENDS_PARAMETER = "allFriends";
    public static final String COMMENTS_PARAMETER = "comments";
    public static final String BATTLES_PARAMETER = "battles";
    public static final String GAME_INFO_PARAMETER = "infos";*/

    public static final String[] SHIPS_INFO_ARRAY = new String[]{
            "The Cruiser is a fast mobile ship. It doesn't pack much firepower having only one weapon aboard" +
                    "but it is perfectly capable of firing and relocating before the enemy knows that hit them",

            "The Submarine is the stealthy killer of the high seas. it lacks mobility but makes up for it" +
                    "with the ability to easily scan for enemy positions and strike them hard",

            "The Destroyer is the perfect embodiment of mobile high impact fighting. Once an enemy has been spotted" +
                    "the destroyer can make really quick work of them striking them where it hurts. HOOOOOORAH!!",

            "The battleship is the noise maker of the high seas. It is capable of blanketing huge areas with sustained" +
                    "high impact shells forcing the enemy to think twice about their positioning.",
            "The carrier is the back bone artillery of the your fleet It is capable of blanketing, assist in spotting" +
                    "and delivering the pain where it's needed. Once located, it is extremely vulnerable."
    };

    public static final String[] SHIPS_INFO_IMAGE_ARRAY = new String[]{
            "cruiser.png",
            "submarine.png",
            "destroyer.png",
            "battleship.png",
            "carrier.png"
    };

    @Override
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    @Override
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected void redirectTo(HttpServletRequest request, HttpServletResponse response,String serv) throws IOException {
        response.sendRedirect(serv);
    }

    protected boolean hasSpecialCharacters(String s) {
        if(s != null && !s.isEmpty()) {
            String regex = "[^A-Za-z0-9]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(s);
            return m.find();
        }else {
            return false;
        }
    }

    protected void writeError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        String nothing = "An error occured, check that you're using the correct path and parameters";
        writer.write(new Gson().toJson(new HashMap<String,String>().put("Error",nothing)));
    }

    protected int toInt(String s) {
        int conv;
        if (s != null) {
            try {
                conv = Integer.parseInt(s);
            }catch (NumberFormatException e) {
                conv = -1;
            }
            return conv;
        } else
            return -1;
    }

    public static String getUsersTableName() {
        return User.USERS_TABLE_NAME;
    }

    public static String getUserIdColumn() {
        return User.USER_ID_COLUMN;
    }

    public static String getUserNameColumn() {
        return User.USER_NAME_COLUMN;
    }

    public static String getDisplayNameColumn() {
        return User.DISPLAY_NAME_COLUMN;
    }

    public static String getPasswordColumn() {
        return User.PASSWORD_COLUMN;
    }

    public static String getCommentsTableName() {
        return Comment.COMMENTS_TABLE_NAME;
    }

    public static String getMsgColumn() {
        return Comment.MSG_COLUMN;
    }

    public static String getTimePostedColumn() {
        return Comment.TIME_POSTED_COLUMN;
    }

    public static String getPosterUserColumn() {
        return Comment.POSTER_USER_COLUMN;
    }

    public static String getPostedToUserColumn() {
        return Comment.POSTED_TO_USER_COLUMN;
    }

    public static String getCustomizationsTableName() {
        return Customization.CUSTOMIZATIONS_TABLE_NAME;
    }

    public static String getProfileImgColumn() {
        return Customization.PROFILE_IMG_COLUMN;
    }

    public static String getBackgroundImgColumn() {
        return Customization.BACKGROUND_IMG_COLUMN;
    }

    public static String getFriendsTableName() {
        return Friend.FRIENDS_TABLE_NAME;
    }


    public static String getFriendOfColumn() {
        return Friend.FRIEND_OF_COLUMN;
    }

    public static String getDateAddedColumn() {
        return Friend.DATE_ADDED_COLUMN;
    }

    public static String getBattlesTableName() {
        return Battle.BATTLES_TABLE_NAME;
    }

    public static String getWinnerBlue() {
        return Battle.WINNER_BLUE;
    }

    public static String getWinngerRed() {
        return Battle.WINNGER_RED;
    }

    public static String getDateStartedColumn() {
        return Battle.DATE_STARTED_COLUMN;
    }

    public static String getDateEndedColumn() {
        return Battle.DATE_ENDED_COLUMN;
    }

    public static String getWinnerColumn() {
        return Battle.WINNER_COLUMN;
    }

    public static String getTurnsColumn() {
        return Battle.TURNS_COLUMN;
    }

    public static String getKnockedByBlueColumn() {
        return Battle.KNOCKED_BY_BLUE_COLUMN;
    }

    public static String getLostByBlueColumn() {
        return Battle.LOST_BY_BLUE_COLUMN;
    }

    public static String getKnockedByRedColumn() {
        return Battle.KNOCKED_BY_RED_COLUMN;
    }

    public static String getLostByRedColumn() {
        return Battle.LOST_BY_RED_COLUMN;
    }

    public static String getFiredByBlueColumn() {
        return Battle.FIRED_BY_BLUE_COLUMN;
    }

    public static String getHitsByBlueColumn() {
        return Battle.HITS_BY_BLUE_COLUMN;
    }

    public static String getFiredByRedColumn() {
        return Battle.FIRED_BY_RED_COLUMN;
    }

    public static String getHitsByRedColumn() {
        return Battle.HITS_BY_RED_COLUMN;
    }

    public static String getBlueUserColumn() {
        return Battle.BLUE_USER_COLUMN;
    }

    public static String getRedUserColumn() {
        return Battle.RED_USER_COLUMN;
    }

    public static String getBattleIdColumn() {
        return Battle.BATTLE_ID_COLUMN;
    }

    public static String getFoughtProperty() {
        return Battle.FOUGHT_PROPERTY;
    }

    public static String getWinsProperty() {
        return Battle.WINS_PROPERTY;
    }

    public static String getShipsKnockedProperty() {
        return Battle.SHIPS_KNOCKED_PROPERTY;
    }

    public static String getShipsLostProperty() {
        return Battle.SHIPS_LOST_PROPERTY;
    }

    public static String getFiredProperty() {
        return Battle.FIRED_PROPERTY;
    }

    public static String getHitsProperty() {
        return Battle.HITS_PROPERTY;
    }

    public static String getRankProperty() {
        return Battle.RANK_PROPERTY;
    }

    public static String getLadderPointsProperty() {
        return Battle.LADDER_POINTS_PROPERTY;
    }

    public static String getLadderNextProperty() {
        return Battle.LADDER_NEXT_PROPERTY;
    }

    public static String getWinRatioProperty() {
        return Battle.WIN_RATIO_PROPERTY;
    }

    public static String getAverageTurnsPerProperty() {
        return Battle.AVERAGE_TURNS_PER_PROPERTY;
    }

    public static String getAccuracyProperty() {
        return Battle.ACCURACY_PROPERTY;
    }

    public static String getKnockedPerLostProperty() {
        return Battle.KNOCKED_PER_LOST_PROPERTY;
    }

    public static String getDefaultBack() {
        return Customization.DEFAULT_BACK_IMG;
    }

    public static String getDefaultProf() {
        return Customization.DEFAULT_PROFILE_IMG;
    }
}
