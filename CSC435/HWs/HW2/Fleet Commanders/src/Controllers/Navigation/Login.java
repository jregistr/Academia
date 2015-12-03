package Controllers.Navigation;

import Controllers.Controller;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends Controller {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
            User user = User.retrieve(username);
            if(user != null){
                if(password.equals(user.getPassword())) {
                    HttpSession session = request.getSession();
                    session.setAttribute(USERNAME_PARAMETER,username);
                    redirectTo(request, response, PROFILE_SERVLET);
                }else {
                    redirectTo(request,response,WELCOME_SERVLET);
                }
            }else {
                redirectTo(request,response,WELCOME_SERVLET);
            }
        }else {
            redirectTo(request,response,WELCOME_SERVLET);
        }
    }
}
