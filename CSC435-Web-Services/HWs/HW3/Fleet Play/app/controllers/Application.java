package controllers;

import Models.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import controllers.routes;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application extends Controller {

    private static final String USER_ID_SESSION_NAME = "userID";
    private static final String DEFAULT_BACK = "http://i626.photobucket.com/albums/tt345/thedragonblade/introBack.png";
    private static final String DEFAULT_PROF = "http://i626.photobucket.com/albums/tt345/thedragonblade/guy1.png";

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

    @Transactional
    public static Result welcome() {
        //session().clear();
       /* User cUser = new User("bandleCity","The mechanized menace","xxxx");
        cUser.save();
        User oUser = new User("fizz","The tidal fish","xxxx");
        oUser.save();
        Customization customization = new Customization(cUser.getUserName(),"http://www.vetprofessionals.com/catprofessional/images/home-cat.jpg","http://img4.wikia.nocookie.net/__cb20120301094350/callofduty/images/e/e6/Predator_Missile-killstreak-Mw3.jpg");
        customization.save();
        Friend friend = new Friend(cUser.getUserName(),oUser.getUserName());
        friend.save();
        Comment comment = new Comment(oUser.getUserName(),cUser.getUserName(),"This is from fizz");
        comment.save();
        Battle battle = new Battle(new Date(),new Date(),true,35,5,2,2,5,100,20,110,6,cUser.getUserName(),oUser.getUserName());
        battle.save();*/

        if (session(USER_ID_SESSION_NAME) == null) {
            return ok(Template.render("Welcome", Template.render("Welcome", Welcome.render())));
        } else {
            User user = User.getByID(Long.parseLong(session(USER_ID_SESSION_NAME)));
            if (user == null) {
                Application.session().remove(USER_ID_SESSION_NAME);
                return ok(Template.render("Welcome", Template.render("Welcome", Welcome.render())));
            } else {
                return redirect(routes.Application.Profile());
            }
        }
    }

    @Transactional
    public static Result Login(){
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String userName = dynamicForm.get("username");
        String passWord = dynamicForm.get("password");
        if(userName != null && !userName.isEmpty() && passWord != null && !passWord.isEmpty()) {
            User user = User.getByUserName(userName);
            if(user != null){
                if(user.getUserName().equals(userName) && user.getPassword().equals(passWord)){//success
                    session(USER_ID_SESSION_NAME,user.getId().toString());
                    return redirect(routes.Application.Profile());
                }else {
                    return redirect(routes.Application.welcome());
                }
            }else {
                return redirect(routes.Application.welcome());
            }
        }else {
            return redirect(routes.Application.welcome());
        }
    }

    public static Result Logout() {
        Application.session().remove(USER_ID_SESSION_NAME);
        Application.session().clear();
        return redirect(routes.Application.welcome());
    }

    @Transactional
    public static Result Register() {
        DynamicForm dynamicForm = Form.form().bindFromRequest();
        String displayName = dynamicForm.get("displayname");
        String username = dynamicForm.get("username");
        String password = dynamicForm.get("password");
        String confpass = dynamicForm.get("confpass");
        if(displayName != null && !displayName.isEmpty() && username != null && !username.isEmpty() && password != null && !password.isEmpty() && confpass != null && !confpass.isEmpty()) {
            if(password.equals(confpass)){
                User test = User.getByUserName(username);
                if(test == null) {//if User already exists
                    User user = new User(username, displayName, password);
                    user.save();
                    User pull = User.getByUserName(username);
                    if(pull != null){
                        Application.session(USER_ID_SESSION_NAME,user.getId().toString());
                        return redirect(routes.Application.Profile());
                    }else {
                        return redirect(routes.Application.welcome());
                    }
                }else {
                    return redirect(routes.Application.welcome());
                }
            }else {
                return redirect(routes.Application.welcome());
            }
        }else {
            return redirect(routes.Application.welcome());
        }
    }

    @Transactional
    public static Result Profile() {
        String idString = session(USER_ID_SESSION_NAME);
        if(idString != null) {
            Long id = Long.parseLong(idString);
            User user = User.getByID(id);
            if(user != null) {
                JsonParser parser = new JsonParser();
                Customization customization = Customization.getForUser(user.getId());
                String fullHist = Battle.fullHistory(user.getUserName());
                List<Friend> friends = Friend.getFriendsForUser(user.getUserName());
                ArrayList<JsonObject> friendsList = new ArrayList<>();
                if(friends != null && friends.size() > 0){
                    for (Friend friend : friends) {
                        if(friend != null){
                            friendsList.add(parser.parse(friend.toJson()).getAsJsonObject());
                        }
                    }
                }
                List<Battle> battles = Battle.getBattlesForUser(user.getUserName());
                ArrayList<JsonObject> battlesList = new ArrayList<>();
                if(battles != null && battles.size() > 0){
                    for (Battle battle : battles) {
                        if(battle != null){
                            battlesList.add(parser.parse(battle.toJson()).getAsJsonObject());
                        }
                    }
                }
                List<Comment> comments = Comment.getAllToUser(user.getUserName());
                ArrayList<JsonObject> commentsList = new ArrayList<>();
                if(comments != null && comments.size() > 0){
                    for (Comment comment : comments) {
                        if(comment != null){
                            commentsList.add(parser.parse(comment.toJson()).getAsJsonObject());
                        }
                    }
                }

                return ok(Template.render("Profile",Profile.render(user.getId(),user.getUserName(),
                        (customization != null)?customization.getBackPic() : DEFAULT_BACK,(customization != null) ? customization.getProfPic() : DEFAULT_PROF
                ,(fullHist != null && !fullHist.isEmpty())?parser.parse(fullHist).getAsJsonObject() : null,friendsList.toArray(new JsonObject[friendsList.size()]),
                        commentsList.toArray(new JsonObject[commentsList.size()]),battlesList.toArray(new JsonObject[battlesList.size()]))));
            }else {
                Application.session().remove(USER_ID_SESSION_NAME);
                return redirect(routes.Application.welcome());
            }
        }else {
            return redirect(routes.Application.welcome());
        }
    }

}
