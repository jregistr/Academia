package controllers;

import controllers.*;
import models.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import play.api.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.Transactional;
import play.mvc.*;

import views.html.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static play.mvc.Results.ok;

public class Application extends Controller {

    private static final String USER_NAME_SESSION_NAME = "userName";
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

        /*User u2 = new User("fish", "the super fish", "xxx");
        u2.save();

        Battle b1 = new Battle(new Date(), new Date(), true, 100, 5, 2, 2, 5, 100, 30, 50, 67, "super", "fish");
        b1.save();

        Battle b2 = new Battle(new Date(), new Date(), true, 200, 5, 2, 2, 5, 20, 20, 50, 3, "super", "fish");
        b2.save();*/


        if (session(USER_NAME_SESSION_NAME) == null) {
            return ok(Template.render("Welcome", Template.render("Welcome", Welcome.render())));
        } else {
            User user = User.getByUserName(session(USER_NAME_SESSION_NAME));

            if (user == null) {
                Application.session().remove(USER_NAME_SESSION_NAME);
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
                if(user.getUsername().equals(userName) && user.getPassword().equals(passWord)){//success
                    session(USER_NAME_SESSION_NAME,user.getUsername());
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
        Application.session().remove(USER_NAME_SESSION_NAME);
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
                        Application.session(USER_NAME_SESSION_NAME,user.getUsername());
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
        String userNameString = session(USER_NAME_SESSION_NAME);
        if(userNameString != null) {
            //Long id = Long.parseLong(idString);
            User user = User.getByUserName(userNameString);
            if(user != null) {
                JsonParser parser = new JsonParser();
                Customization customization = Customization.getForUser(user.getUsername());
                String fullHist = Battle.fullHistory(user.getUsername());
                List<Friend> friends = Friend.getFriendsForUser(user.getUsername());
                ArrayList<JsonObject> friendsList = new ArrayList<>();
                if(friends != null && friends.size() > 0){
                    for (Friend friend : friends) {
                        if(friend != null){
                            friendsList.add(parser.parse(friend.toJson()).getAsJsonObject());
                        }
                    }
                }
                List<Battle> battles = Battle.getBattlesForUser(user.getUsername());
                ArrayList<JsonObject> battlesList = new ArrayList<>();
                if(battles != null && battles.size() > 0){
                    for (Battle battle : battles) {
                        if(battle != null){
                            battlesList.add(parser.parse(battle.toJson()).getAsJsonObject());
                        }
                    }
                }
                List<Comment> comments = Comment.getAllToUser(user.getUsername());
                ArrayList<JsonObject> commentsList = new ArrayList<>();
                if(comments != null && comments.size() > 0){
                    for (Comment comment : comments) {
                        if(comment != null){
                            commentsList.add(parser.parse(comment.toJson()).getAsJsonObject());
                        }
                    }
                }

                return ok(Template.render("Profile",Profile.render(user.getUsername(),
                        (customization != null)?customization.getBackPic() : DEFAULT_BACK,(customization != null) ? customization.getProfPic() : DEFAULT_PROF
                        ,(fullHist != null && !fullHist.isEmpty())?parser.parse(fullHist).getAsJsonObject() : null,friendsList.toArray(new JsonObject[friendsList.size()]),
                        commentsList.toArray(new JsonObject[commentsList.size()]),(battlesList.size() > 0) ? battlesList.toArray(new JsonObject[battlesList.size()]):null)));
            }else {
                Application.session().remove(USER_NAME_SESSION_NAME);
                return redirect(routes.Application.welcome());
            }
        }else {
            return redirect(routes.Application.welcome());
        }
    }

}
