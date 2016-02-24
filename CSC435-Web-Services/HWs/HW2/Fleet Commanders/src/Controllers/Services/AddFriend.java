package Controllers.Services;

import Controllers.Controller;
import Models.Friend;
import Models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddFriend extends Controller {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userParam = request.getParameter(USER_PARAMETER);
        String to = request.getParameter(TOWARDS_USER_PARAMETER);
        if(userParam != null && !userParam.isEmpty() && to != null && !to.isEmpty()){
            User user = User.retrieve(userParam);
            User friendOf = User.retrieve(to);
            if(Friend.addFriend(friendOf.getUserID(), user.getUserID())){
                String show = request.getParameter(SHOW_RESULT_PARAMETER);
                if(show != null && show.equals("yes")){
                    String all = Friend.retrieveAllToJson(friendOf.getUserID());
                    response.setContentType("application/json");
                    PrintWriter writer = response.getWriter();
                    writer.write(all);
                }
            }else {
                writeError(request,response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
