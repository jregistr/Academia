package Controllers.Navigation;

import Controllers.Controller;
import Models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Welcome extends Controller {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String uName = (String)session.getAttribute(USERNAME_PARAMETER);
        if(uName != null && !uName.isEmpty()){
            User user = User.retrieve(uName);
            if(user != null){
                redirectTo(request,response,PROFILE_SERVLET);
                // goToProfile(request, response);
            }else {//no such user exists
                // goToWelcome(request, response);
                RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
                dispatcher.forward(request,response);
            }
        }else {//first time user or session has expired
            //goToWelcome(request, response);
            /*Timestamp start = Timestamp.valueOf("2015-03-08 11:57:00");
            Timestamp end = Timestamp.valueOf("2015-03-08 12:00:00");
            Battle battle = new Battle(start,end,false,34,2,5,5,2,45,11,39,3,2,3);
            battle.save();*/

            RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
