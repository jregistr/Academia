package FleetGame;

import RatingInfo.BattleSave;
import RatingInfo.LoginSave;
import RatingInfo.UserSave;
import com.google.gson.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;


public class IntroServlet extends HttpServlet {

    public static final String USER_NAME_PARAM = "userName";
    public static final String PASS_PARAM = "passWord";
    public static final String CONF_PASS_PARAM = "confirmPass";
    public static final String FULL_NAME_PARAM = "fullName";
    public static final String REGIS_FORM = "regisForm";
    public static final String ID_PARAM = "ID";
    public static final String BATTLE_INFO_PARAM = "battleInfo";
    public static final String LOGIN_INFO_PARAM = "loginInfo";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String checkName = (String)session.getAttribute(USER_NAME_PARAM);
        if(checkName == null || checkName.isEmpty()){ //first time user
            RequestDispatcher view = request.getRequestDispatcher("introPage.html");
            view.forward(request,response);
        }else {
            ServletContext context = request.getServletContext();
            String info = (String)context.getAttribute(checkName);
            if(info == null || info.isEmpty()){
               // System.out.println("Info in context is null");
                RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                view.forward(request,response);
            }else{
                response.sendRedirect("Home");
            }
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,JsonParseException,JsonIOException,JsonSyntaxException {
        //response.getWriter().println("Hello there" + request.getParameter("userName"));
        String regis = request.getParameter(REGIS_FORM);
        if(regis != null && !regis.isEmpty()){ //registration form
            String uName = request.getParameter(USER_NAME_PARAM);
            String fName = request.getParameter(FULL_NAME_PARAM);
            String pass = request.getParameter(PASS_PARAM);
            String confPass = request.getParameter(CONF_PASS_PARAM);
            if(uName != null && !uName.isEmpty() && fName != null && !fName.isEmpty() && pass != null && !pass.isEmpty() && confPass != null && !confPass.isEmpty()){
                //ServletContext context = request.getServletContext();
                ServletContext context =  request.getServletContext();
                boolean nameNotExists = context.getAttribute(uName) == null;
                boolean passMatch = pass.equals(confPass);
                if(passMatch && nameNotExists){
                    String id = Calculate.makeID();

                    LoginSave loginSave = new LoginSave(uName,pass,fName,id);
                    BattleSave battleSave = new BattleSave(1,1,1,1,Calculate.calculateBattleRating(1,1,1,1),Calculate.ratingToRatingName(0));
                    UserSave userSave = new UserSave(loginSave,battleSave);
                    Gson gson = new Gson();
                    String userInfoJs = gson.toJson(userSave);

                    context.setAttribute(uName,userInfoJs);
                   // debug();
                    HttpSession session = request.getSession();
                    session.setAttribute(USER_NAME_PARAM,uName);
                    session.setAttribute(FULL_NAME_PARAM,fName);
                    response.sendRedirect("Home");
                }else{
                    RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                    view.forward(request,response);
                }

            }else{
                RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                view.forward(request,response);
            }
        }else{
            String uName = request.getParameter(USER_NAME_PARAM);
            String pass = request.getParameter(PASS_PARAM);
            if(uName != null && !uName.isEmpty() && pass != null && !pass.isEmpty()){
                ServletContext context = request.getServletContext();
                String info = context.getAttribute(uName).toString();
             //   System.out.println("Info: " + info);
                if(info != null && !info.isEmpty()){
                    com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
                    JsonObject userObj = parser.parse(info).getAsJsonObject();
                    JsonObject logStr = userObj.getAsJsonObject("loginInfo");
                  //  System.out.println(logStr.get("passWord"));
                    String savedPass = logStr.get(PASS_PARAM).getAsString();
                    if(savedPass.equals(pass)){
                        HttpSession session = request.getSession();
                        session.setAttribute(USER_NAME_PARAM,uName);
                        session.setAttribute(FULL_NAME_PARAM,logStr.get(FULL_NAME_PARAM).getAsString());
                        response.sendRedirect("Home");
                    }else{
                        //System.out.println("Not the same");
                        RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                        view.forward(request,response);
                    }
                }else{
                    RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                    view.forward(request,response);
                }
            }else{
                RequestDispatcher view = request.getRequestDispatcher("introPage.html");
                view.forward(request,response);
            }
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
