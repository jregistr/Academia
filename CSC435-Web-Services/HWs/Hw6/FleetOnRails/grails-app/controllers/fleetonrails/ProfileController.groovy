package fleetonrails

import Models.Battle
import Models.Comment
import Models.Customization
import Models.User

class ProfileController {

    def index(String viewing) {
        String username = session["user"]
        if(username != null && !username.isEmpty()){
            User user = User.findByUsername(username)
            if(user != null){
                if(viewing == null) {
                    Customization customization = user.customization
                    def battles = user.blueBattles + user.redBattles
                    def comments = user.comments
                    [
                            user: user, battles: battles, summary: Battle.summary(user), comments: comments, customization: customization, viewing: false
                    ]
                }else {
                    User toView = User.findByUsername(viewing)
                    if(toView != null){
                        render(view: 'viewing',model: [viewinguser:user,viewingcustomization:user.customization,user: toView,battles: toView.redBattles + toView.blueBattles,
                        comments: toView.comments,customization: toView.customization,summary: Battle.summary(toView)
                        ])
                    }else {
                        redirect(controller: 'profile',action: 'index')
                    }
                }
            }else {
                session.removeAttribute("user")
                session.invalidate()
                redirect(controller: 'welcome',action: 'index')
            }
        }else {
            session.removeAttribute("user")
            session.invalidate()
            redirect(controller: 'welcome',action: 'index')
        }
    }

   /* def index() {
      index(null)
    }
*/
}
