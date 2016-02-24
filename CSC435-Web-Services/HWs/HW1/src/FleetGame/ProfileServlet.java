package FleetGame;

import RatingInfo.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Random;


public class ProfileServlet extends HttpServlet {

    public static final String BATTLES_PARAM = "battles";
    public static final String KNOCK_OUTS_PARAM = "knockOuts";
    public static final String SHIPS_LOST_PARAM="shipsLost";
    public static final String WON_PARAM = "won";
    public static final String RATING_PARAM = "rating";
    public static final String RANK_PARAM = "rank";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String checkName = (String)session.getAttribute(IntroServlet.USER_NAME_PARAM);
        if(checkName != null && !checkName.isEmpty()){
            String battles = "0";
            String knockouts = "0";
            String shipsLost = "0";
            String won = "0";
            String rating = "0";
            String rank = "0";

           // debug();

            String accountSring = getServletContext().getAttribute(checkName).toString();
            JsonParser parser = new JsonParser();
            JsonObject battlesInfo = parser.parse(accountSring).getAsJsonObject().getAsJsonObject(IntroServlet.BATTLE_INFO_PARAM);
            battles = battlesInfo.get(BATTLES_PARAM).getAsString();
            knockouts = battlesInfo.get(KNOCK_OUTS_PARAM).getAsString();
            shipsLost = battlesInfo.get(SHIPS_LOST_PARAM).getAsString();
            won = battlesInfo.get(WON_PARAM).getAsString();
            rating = battlesInfo.get(RATING_PARAM).getAsString();
            rank = battlesInfo.get(RANK_PARAM).getAsString();

            request.setAttribute(BATTLES_PARAM, battles);
            request.setAttribute(KNOCK_OUTS_PARAM,knockouts);
            request.setAttribute(SHIPS_LOST_PARAM,shipsLost);
            request.setAttribute(WON_PARAM,won);
            request.setAttribute(RATING_PARAM,rating);
            request.setAttribute(RANK_PARAM,rank);
            RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
            dispatcher.forward(request,response);
        }else{
            response.sendRedirect("Intro");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("battle") != null){
            HttpSession session = request.getSession();
            ServletContext context = request.getServletContext();

            String checkName = (String)session.getAttribute(IntroServlet.USER_NAME_PARAM);
            if(checkName != null && !checkName.isEmpty()){
               // System.out.println("In here");
                int battles;
                int knockouts;
                int shipsLost;
                int won;
                int rating;
                String rank;

                String accountSring = context.getAttribute(checkName).toString();
                JsonParser parser = new JsonParser();
                JsonObject battlesInfo = parser.parse(accountSring).getAsJsonObject().getAsJsonObject(IntroServlet.BATTLE_INFO_PARAM);
                JsonObject logInfo = parser.parse(accountSring).getAsJsonObject().getAsJsonObject(IntroServlet.LOGIN_INFO_PARAM);
                String id = logInfo.get("ID").getAsString();
                String uname = logInfo.get(IntroServlet.USER_NAME_PARAM).getAsString();
                String pass = logInfo.get(IntroServlet.PASS_PARAM).getAsString();
                String fn = logInfo.get(IntroServlet.FULL_NAME_PARAM).getAsString();

                battles = Integer.parseInt( battlesInfo.get(BATTLES_PARAM).getAsString());
                knockouts = Integer.parseInt(battlesInfo.get(KNOCK_OUTS_PARAM).getAsString()) ;
                shipsLost = Integer.parseInt( battlesInfo.get(SHIPS_LOST_PARAM).getAsString());
                won = Integer.parseInt(battlesInfo.get(WON_PARAM).getAsString()) ;

                Random random = new Random();
                battles += 2;
                knockouts +=6;
                shipsLost += 2;
                won += 1;
                rating = Calculate.calculateBattleRating(battles,knockouts,shipsLost,won);
                rank = Calculate.ratingToRatingName(rating);

                LoginSave loginSave = new LoginSave(uname,pass,fn,id);
                BattleSave battleSave = new BattleSave(battles,knockouts,shipsLost,won,rating,rank);
                UserSave userSave = new UserSave(loginSave,battleSave);

                Gson gson = new Gson();
                String userJs = gson.toJson(userSave);
                context.setAttribute(uname,userJs);
                doGet(request,response);
            }else{
                response.sendRedirect("Intro");
            }
        }else {
            doGet(request,response);
        }
    }

    public void debug(){
        Enumeration<String> enumeration = getServletContext().getAttributeNames();
        while(enumeration.hasMoreElements()){
            String a = enumeration.nextElement();
            System.out.println(a);
        }
    }


}
