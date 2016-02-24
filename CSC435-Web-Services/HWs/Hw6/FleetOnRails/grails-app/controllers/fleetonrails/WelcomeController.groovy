package fleetonrails

class WelcomeController {

    def index() {
       // def bla = "so this is somestring i wrote"
        //[something:bla]
        if(session["user"] != null){
            session.removeAttribute("user")
            session.invalidate()
        }
    }



}
