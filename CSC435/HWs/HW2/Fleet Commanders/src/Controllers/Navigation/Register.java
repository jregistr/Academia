package Controllers.Navigation;

import Controllers.Controller;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Register extends Controller {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String display = request.getParameter(DISPLAYNAME_PARAMETER);
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String confPass = request.getParameter(CONFIRM_PASS_PARAMETER);
        if((display != null && !display.isEmpty()) && (username != null && !username.isEmpty()) && (password != null && !password.isEmpty() && (confPass != null && !confPass.isEmpty()))) {
            if(!hasSpecialCharacters(username)){
                User testUser = User.retrieve(username);
                if(testUser == null) {
                    User newUser = new User(display, username, password);
                    if(newUser.save()) {
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
}
