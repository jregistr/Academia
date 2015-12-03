package fleetonrails

import Models.User

class LoginController {

    def index(String username,String password) {

       User user = User.findByUsername(username)
        if(user != null && user.password.equals(password)){
            session["user"] = user.getUsername()
            redirect(controller: 'profile',action: 'index')
        }else {//no such user
            redirect(controller: 'welcome',action: 'index')
        }

    }

}
