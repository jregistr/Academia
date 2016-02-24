package services

import com.google.gson.Gson
import Models.*

class SetterController {

    /*private static final String TYPE_LIKE = "like"
    private static final String TYPE_USER = "user"
    "*/
    private static final String TYPE_BATTLES = "battles"
    private static final String TYPE_COMMENTS = "comments"
    private static final String TYPE_FRIENDS = "friends"
    private static final String TYPE_CUSTOMIZATION = "customization"

    private static final String TARGET_BACK = "background"
    private static final String TARGET_PROF = "profile"

    def index(String type, String from, String target, String item) {
        if(type != null && !type.isEmpty() && from != null && !from.isEmpty() && item != null && !item.isEmpty()) {
            if(type.equals(TYPE_CUSTOMIZATION)){
                User fr = User.findByUsername(from)
                if(fr != null){
                    if(target.equals(TARGET_BACK)){
                        fr.customization.setBackgroundImage(item)
                        chain(controller: 'getter',action: 'index',params: [type:'customization',target: from])
                    }else if(target.equals(TARGET_PROF)){
                        fr.customization.setProfileImage(item)
                        chain(controller: 'getter',action: 'index',params: [type:'customization',target: from])
                    }else {
                        render(text: errorJson("Not a valid target for command type"), contentType: "application/json", encoding: "UTF-8")
                    }
                }else
                    render(text: errorJson("No such user"), contentType: "application/json", encoding: "UTF-8")
            }else {
                User fr = User.findByUsername(from)
                User to = User.findByUsername(target)
                if(fr != null && to != null){
                    if(type.equals(TYPE_COMMENTS)){
                        Comment comment = new Comment(poster: fr,target:to,msg: item,timePosted: new Date())
                        to.addToComments(comment)
                        render(text: comment.toJson(), contentType: "application/json", encoding: "UTF-8")
                    }else if(type.equals(TYPE_FRIENDS)){
                        if(item.equals("delete")){
                            if(fr.friends != null && fr.friends.size() > 0){
                                def friends = fr.friends
                                def exists = friends.find {x->x.username.equals(to.username)} != null
                                if(exists){
                                    fr.removeFromFriends(to)
                                    chain(controller: 'getter',action: 'index',params: [type:'friends',target: from])
                                } else
                                    render(text: errorJson("No such friend"), contentType: "application/json", encoding: "UTF-8")
                            }else
                                render(text: errorJson("Empty friends list"), contentType: "application/json", encoding: "UTF-8")
                        }else{
                            if(fr.friends != null && fr.friends.size() > 0){
                                if(fr.friends.find {x->x.username.equals(to.username)} == null){
                                    fr.addToFriends(to)
                                    chain(controller: 'getter',action: 'index',params: [type:'friends',target: from])
                                }else
                                    render(text: errorJson("Already had " + target + " as a friend" ), contentType: "application/json", encoding: "UTF-8")
                            }else {
                                fr.addToFriends(to)
                                chain(controller: 'getter',action: 'index',params: [type:'friends',target: from])
                            }
                        }
                    }else if(type.equals(TYPE_BATTLES)){
                        boolean blueWin = (item.equals("blue"))
                        def rand = new Random()
                        Battle battle = new Battle(dateStarted: new Date(),dateEnded: new Date(),blueWin:blueWin,knockedByBlue: blueWin ? 5 : (rand.nextInt(3) + 1),
                        lostByBlue: blueWin ? (rand.nextInt(3) + 1):5,knockedByRed: blueWin ? (rand.nextInt(3) + 1):5,lostByRed: blueWin ? 5:rand.nextInt(3)+1,
                        firedByBlue: rand.nextInt(50),hitsByBlue: rand.nextInt(50),firedByRed: rand.nextInt(50),hitsByRed: rand.nextInt(50),blue: fr,red: to)
                        fr.addToBlueBattles(battle)
                        to.addToRedBattles(battle)
                        chain(controller: 'getter',action: 'index',params: [type:'battles',target: from])
                    }else {
                        render(text: errorJson("No such type found"), contentType: "application/json", encoding: "UTF-8")
                    }
                }else
                    render(text: errorJson("One of the users cannot be found"), contentType: "application/json", encoding: "UTF-8")
            }
        }else
            render(text: errorJson("One of the inputs is missing"),contentType: "application/json",encoding: "UTF-8")
    }

    private static String errorJson(String error){
        HashMap<String,String> map = new HashMap<>();
        map.put("Error",error);
        new Gson().toJson(map);
    }

}
