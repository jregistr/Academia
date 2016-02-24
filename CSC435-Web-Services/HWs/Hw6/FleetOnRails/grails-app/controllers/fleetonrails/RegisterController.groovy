package fleetonrails

import Models.Customization
import Models.User

class RegisterController {

    def index(String username,String displayname,String password,String confirmpassword) {
        println("user:" + username + ", display:" + displayname + ", pass:" + password + ", conf:" + confirmpassword)
        if(!User.findByUsername(username)){//doesn't exists
            if(password.equals(confirmpassword)){
                User user = new User(username:username,displayName: displayname,password:password)
                //user.customization = Customization.randomCustomization(user)
                user.save()
                session["user"] = user.getUsername()
                redirect(controller: 'profile',action: 'index')
            }else
                redirect(controller: 'welcome', action: 'index')
        }else
            redirect(controller: 'welcome', action: 'index')
    }
}
