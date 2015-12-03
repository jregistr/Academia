package Controllers.Navigation;

import Controllers.Controller;
import Models.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Profile extends Controller {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String uName = (String)session.getAttribute(USERNAME_PARAMETER);
        if(uName != null && !uName.isEmpty()) {
            User user = User.retrieve(uName);
            if(user != null) {
                //check if we are not viewing another's profile and we're just going to our own
                String viewing = request.getParameter(VIEWING_ID_PARAMETER);
                boolean enteredID = false;
                int viewID = -1;
                try{
                    viewID = Integer.parseInt(viewing);
                    enteredID = true;
                }catch (NumberFormatException e){
                    viewID = -1;
                }
                if(viewing == null || viewing.isEmpty() || viewID == user.getUserID()){ //we're just viewing out own profile
                    //Check if we're posting a comment on our own wall
                    //String post = request.getParameter(POST_COMMENT_PARAMETER);
                    setProfileAttributes(request,response,user);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                    dispatcher.forward(request,response);
                }else {//viewing someone else's profile
                    User viewingUser = (enteredID) ? User.retrieve(viewID) :User.retrieve(viewing) ;
                    if(viewingUser != null){//user exists
                        // String post = request.getParameter(POST_COMMENT_PARAMETER);
                        System.out.println("Looking at " + viewingUser.getDisplayName());
                        setProfileAttributes(request, response, viewingUser);
                        request.setAttribute(VIEWING_USER_INFO,user.toJson());
                        request.setAttribute(VIEWING_USER_CUSTOMIZATION,Customization.retrieveToJson(user.getUserID()));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/otherprofile.jsp");
                        dispatcher.forward(request,response);
                    }else {//user we want to view doesnt exist
                        setProfileAttributes(request,response,user);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                        dispatcher.forward(request,response);
                    }
                }
            }else {
                session.removeAttribute(USERNAME_PARAMETER);
                redirectTo(request,response,WELCOME_SERVLET);
            }
        }else {
            session.removeAttribute(USERNAME_PARAMETER);
            redirectTo(request,response,WELCOME_SERVLET);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void setProfileAttributes(HttpServletRequest request, HttpServletResponse response, User user){
        request.setAttribute(USERNAME_PARAMETER,user.getUserName());
        request.setAttribute(DISPLAYNAME_PARAMETER,user.getDisplayName());
        Integer u = user.getUserID();
        request.setAttribute(USER_ID_PARAMETER,u);

        request.setAttribute(Customization.CUSTOMIZATIONS_TABLE_NAME,Customization.retrieveToJson(user.getUserID()));
        request.setAttribute("info",User.retrieveToJson(user.getUserName()));
        request.setAttribute("fullHistory", Battle.fullHistory(user.getUserID()));
        //System.out.println(Battle.retrieveToJson(user.getUserID()));
        request.setAttribute(getBattlesTableName(), Battle.retrieveToJson(user.getUserID()));
        request.setAttribute(getCommentsTableName(), Comment.retrieveToJson(user.getUserID()));
        request.setAttribute(getFriendsTableName(), Friend.retrieveAllToJson(user.getUserID()));
    }
}
