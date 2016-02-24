package Controllers;


import Models.Battle;
import Models.Comment;
import Models.History;
import Models.User;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Controller extends HttpServlet {

    private static final String WELCOME_SERVLET_PATH = "/Welcome";
    private static final String PROFILE_SERVLET_PATH = "/Profile";
    private static final String BATTLE_SERVLET_PATH = "/Battle";
    private static final String HISTORY_SERVLET_PATH = "/History";
    private static final String LOGIN_SERVLET_PATH = "/Login";
    private static final String REGISTER_SERVLET_PATH = "/Register";
    private static final String LOGOUT_SERVLET_PATH = "/Logout";

    private static final String WELCOME_SERVLET = "Welcome";
    private static final String PROFILE_SERVLET = "Profile";
    private static final String BATTLE_SERVLET = "Battle";
    private static final String HISTORY_SERVLET = "History";

    public static final String USERNAME_PARAMETER = "username";
    public static final String PASSWORD_PARAMETER= "password";
    public static final String DISPLAYNAME_PARAMETER = "displayname";
    public static final String CONFIRM_PASS_PARAMETER = "confpass";
    public static final String POST_COMMENT_PARAMETER = "comment";
    public static final String BATTLES_PARAMETER = "battles";
    public static final String USER_ID_PARAMETER = "UID";
    public static final String VIEWING_ID_PARAMETER = "viewing";

    public static final String PROFILE_IMAGE_PARAMETER = "profileimage";
    public static final String BACK_IMAGE_PARAMETER = "backimage";
    public static final String DEFAULT_PROFILE_PIC = "guy1.png";
    public static final String DEFAULT_BACK_PIC = "introBack.png";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        if(userPath.equals(WELCOME_SERVLET_PATH)){
            welcomeRoute(request, response);
        }else if(userPath.equals(PROFILE_SERVLET_PATH)){
            profileRoute(request, response);
        }else if(userPath.equals(BATTLE_SERVLET_PATH)){
            battleRoute(request, response);
        }else if(userPath.equals(HISTORY_SERVLET_PATH)){
            historyRoute(request, response);
        }else if(userPath.equals(LOGOUT_SERVLET_PATH)){
            logoutRoute(request, response);
        }else{
            //page doesnt exist, re route to error page
            noSuchPageRoute(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        if(userPath.equals(LOGIN_SERVLET_PATH)){
            loginRoute(request, response);
        }else if(userPath.equals(REGISTER_SERVLET_PATH)){
            registerRoute(request, response);
        }else if(userPath.equals(LOGOUT_SERVLET_PATH)){
            logoutRoute(request, response);
        }else if(userPath.equals(HISTORY_SERVLET_PATH)){
            historyRoute(request, response);
        }else if(userPath.equals(PROFILE_SERVLET_PATH)){
            profileRoute(request,response);
        }
    }

    private void welcomeRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void profileRoute(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        HttpSession session = request.getSession();
        String uName = (String)session.getAttribute(USERNAME_PARAMETER);
        if(uName != null && !uName.isEmpty()) {
            User user = User.retrieve(uName);
            if(user != null) {
                //check if we are not viewing another's profile and we're just going to our own
                String viewing = request.getParameter(VIEWING_ID_PARAMETER);
                int viewID = -1;
                try{
                    viewID = Integer.parseInt(viewing);
                }catch (NumberFormatException e){
                    viewID = -1;
                }
                if(viewing == null || viewing.isEmpty() || viewID == user.getUserID()){ //we're just viewing out own profile
                    //Check if we're posting a comment on our own wall
                    String post = request.getParameter(POST_COMMENT_PARAMETER);
                    if(post == null || post.isEmpty()){//not posting, just viewing out own profile
                        setProfileAttributes(request,response,user);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                        dispatcher.forward(request,response);
                    }else {//posting to our own profile
                        Comment.add(user.getUserID(),user.getUserID(),post);
                        setProfileAttributes(request,response,user);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                        dispatcher.forward(request,response);
                    }
                }else {//viewing someone else's profile
                    User viewingUser = User.retrieve(viewID);
                    if(viewingUser != null){//user exists, check if we're posting or just viewing
                        String post = request.getParameter(POST_COMMENT_PARAMETER);
                        System.out.println("On their Profile : " + post);
                        if(post == null || post.isEmpty()){//just stalking this other viewer's profile
                            System.out.println("Looking at " + viewingUser.getDisplayName());
                            setProfileAttributes(request, response, user);
                            request.setAttribute(VIEWING_ID_PARAMETER,viewID);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/otherprofile.jsp");
                            dispatcher.forward(request,response);
                        }else {//posting a comment on their profile
                            Comment.add(viewingUser.getUserID(),user.getUserID(),post);
                            setProfileAttributes(request,response,user);
                            request.setAttribute(VIEWING_ID_PARAMETER,viewID);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/otherprofile.jsp");
                            dispatcher.forward(request,response);
                        }
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

  /*  private void profileRoute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String uName = (String)session.getAttribute(USERNAME_PARAMETER);
        if(uName != null && !uName.isEmpty()){
            User user = User.retrieve(uName);
            if(user != null){
                String viewing = request.getParameter(VIEWING_ID_PARAMETER);
                int viewID = -1;
                try{
                    viewID = Integer.parseInt(viewing);
                }catch (NumberFormatException e){
                    viewID = -1;
                }
                if(viewing == null || viewing.isEmpty() || viewID == user.getUserID()){
                    String post = request.getParameter(POST_COMMENT_PARAMETER);
                    if(post == null || post.isEmpty()){ //we are not posting a comment, just going to our wall
                        request.setAttribute(USERNAME_PARAMETER,user.getUserName());
                        request.setAttribute(DISPLAYNAME_PARAMETER,user.getDisplayName());
                        //Battle[] battles = Battle.retrieve(user.getUserID());
                        //request.setAttribute(BATTLES_PARAMETER,battles);
                        Integer u = user.getUserID();
                        request.setAttribute(USER_ID_PARAMETER,u);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                        dispatcher.forward(request,response);
                    }else{ // we are posting a comment to our to our own wall
                        Comment.add(user.getUserID(),user.getUserID(),post);
                        request.setAttribute(USERNAME_PARAMETER,user.getUserName());
                        request.setAttribute(DISPLAYNAME_PARAMETER,user.getDisplayName());
                        //Battle[] battles = Battle.retrieve(user.getUserID());
                        //request.setAttribute(BATTLES_PARAMETER,battles);
                        Integer u = user.getUserID();
                        request.setAttribute(USER_ID_PARAMETER,u);

                        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                        dispatcher.forward(request,response);
                    }
                }else { // we are trying to view someone else's profile page
                    if(viewID > 0){
                        response.setContentType("text/html");
                        User viewUser = User.retrieve(viewID);
                        if(viewUser != null){ //that user does exist,, check if we are just viewing it or posting a comment
                            PrintWriter writer = response.getWriter();
                            writer.write("Viewing: " + viewUser.getDisplayName());
                        }else {//that user doesnt exist, go back to your profile.
                            setProfileAttributes(request,response,user);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/profile.jsp");
                            dispatcher.forward(request,response);
                        }
                    }else {//user id can't exist, go back to your own profile
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
    }*/

    private void setProfileAttributes(HttpServletRequest request, HttpServletResponse response, User user){
        request.setAttribute(USERNAME_PARAMETER,user.getUserName());
        request.setAttribute(DISPLAYNAME_PARAMETER,user.getDisplayName());
        Integer u = user.getUserID();
        request.setAttribute(USER_ID_PARAMETER,u);
    }

    private void battleRoute(HttpServletRequest request, HttpServletResponse response){
        //for putting user into battle
    }

    private void historyRoute(HttpServletRequest request, HttpServletResponse response){
        //this is for exposing out data as a service. This will take a user or "All" as parameter and
        //give back a json string containing battle history statistics
    }

    private void loginRoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

    private void registerRoute(HttpServletRequest request, HttpServletResponse response) throws IOException{
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

    private void logoutRoute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(USERNAME_PARAMETER);
        redirectTo(request,response,WELCOME_SERVLET);
    }

    private void noSuchPageRoute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/views/nosuchpage.jsp");
        dispatcher.forward(request,response);
    }

    private void redirectTo(HttpServletRequest request, HttpServletResponse response,String serv) throws IOException {
        response.sendRedirect(serv);
    }

    private boolean hasSpecialCharacters(String s) {
        if(s != null && !s.isEmpty()) {
            String regex = "[^A-Za-z0-9]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(s);
            return m.find();
        }else {
            return false;
        }
    }

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

}
