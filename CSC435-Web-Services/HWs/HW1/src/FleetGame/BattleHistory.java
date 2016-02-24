package FleetGame;

import com.google.gson.JsonObject;
import sun.org.mozilla.javascript.internal.json.JsonParser;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class BattleHistory extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String user = request.getParameter("user");
        if(user != null && !user.isEmpty()){
            ServletContext context = getServletContext();
            String accountInfo = context.getAttribute(user).toString();
            if(accountInfo != null && !accountInfo.isEmpty()){
                com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
                JsonObject battleInfo = parser.parse(accountInfo).getAsJsonObject().getAsJsonObject(IntroServlet.BATTLE_INFO_PARAM);
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.println("User: " + user + "<br>");
                writer.println("Battles: " + battleInfo.get(ProfileServlet.BATTLES_PARAM)+ "<br>");
                writer.println("Ships Knockedout: " + battleInfo.get(ProfileServlet.KNOCK_OUTS_PARAM)+ "<br>");
                writer.println("Ships Lost: " + battleInfo.get(ProfileServlet.SHIPS_LOST_PARAM)+ "<br>");
                writer.println("Battles Won: " + battleInfo.get(ProfileServlet.WON_PARAM)+ "<br>");
                writer.println("Rating: " + battleInfo.get(ProfileServlet.RATING_PARAM)+ "<br>");
                writer.println("Rank: " + battleInfo.get(ProfileServlet.RANK_PARAM)+ "<br>");
            }else {
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.println("No such user exists");
            }
        }else {
            String name = session.getAttribute(IntroServlet.USER_NAME_PARAM).toString();
            if(name != null && !name.isEmpty()){
                ServletContext context = getServletContext();
                String accountInfo = context.getAttribute(name).toString();
                if(accountInfo != null && !accountInfo.isEmpty()){
                    com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
                    JsonObject battleInfo = parser.parse(accountInfo).getAsJsonObject().getAsJsonObject(IntroServlet.BATTLE_INFO_PARAM);
                    response.setContentType("text/html");
                    PrintWriter writer = response.getWriter();
                    writer.println("User: " + name + "<br>");
                    writer.println("Battles: " + battleInfo.get(ProfileServlet.BATTLES_PARAM)+ "<br>");
                    writer.println("Ships Knockedout: " + battleInfo.get(ProfileServlet.KNOCK_OUTS_PARAM)+ "<br>");
                    writer.println("Ships Lost: " + battleInfo.get(ProfileServlet.SHIPS_LOST_PARAM)+ "<br>");
                    writer.println("Battles Won: " + battleInfo.get(ProfileServlet.WON_PARAM)+ "<br>");
                    writer.println("Rating: " + battleInfo.get(ProfileServlet.RATING_PARAM)+ "<br>");
                    writer.println("Rank: " + battleInfo.get(ProfileServlet.RANK_PARAM)+ "<br>");
                }else {
                    response.setContentType("text/html");
                    PrintWriter writer = response.getWriter();
                    writer.println("No such user exists");
                }
            }else {
                response.setContentType("text/html");
                PrintWriter writer = response.getWriter();
                writer.println("No such user exists");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         doGet(request,response);
    }
}
