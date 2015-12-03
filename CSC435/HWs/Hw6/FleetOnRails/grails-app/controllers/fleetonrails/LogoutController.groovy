package fleetonrails

class LogoutController {

    def index() {
        session.removeAttribute("user")
        session.invalidate()
        redirect(controller: 'welcome',action: 'index')
    }
}
