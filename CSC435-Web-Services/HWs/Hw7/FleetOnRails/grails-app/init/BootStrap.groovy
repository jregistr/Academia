import Models.Battle
import Models.Comment
import Models.User

class BootStrap {

    def init = { servletContext ->
        User fizz = new User("fizz","the mechanized menace","xxxx")
        fizz.save()
        User fiddle = new User("fiddle","the harbinger of doom","xxxx")
        fiddle.save()
        User jack = new User("jack","the jackness","xxxx")
        jack.save()
        User syl = new User("sylvanus","the wind runner","xxxx")
        syl.save()
        User gar = new User("garosh","from hellscream","xxxx")
        gar.save()
        User lux = new User("lux","the weird","xxxx")
        lux.save()


        Comment ftf = new Comment(poster: fizz,target:fizz,msg: "My first message to myself",timePosted: new Date())
        fizz.addToComments(ftf)

        Comment jtf = new Comment(poster: fiddle,target:fizz,msg:"fizz you fooooooooool, I am the harbinger",timePosted: new Date())
        fizz.addToComments(jtf)

        Battle fib = new Battle(dateStarted: new Date(),dateEnded: new Date(),blueWin: true,turns: 100,knockedByBlue:5,lostByBlue:3,knockedByRed:3,
                lostByRed:5,firedByBlue:35,hitsByBlue:30,firedByRed:40,hitsByRed:5,blue: fizz,red: jack)

        Battle fir = new Battle(dateStarted: new Date(),dateEnded: new Date(),blueWin: true,turns: 80,knockedByBlue:5,lostByBlue:1,knockedByRed:1,
                lostByRed:5,firedByBlue:20,hitsByBlue:20,firedByRed:45,hitsByRed:7,blue: fiddle,red: fizz)
        fizz.addToBlueBattles(fib)
        jack.addToRedBattles(fib)

        fizz.addToRedBattles(fir)
        fiddle.addToBlueBattles(fir)

        fizz.addToFriends(syl)
        fizz.addToFriends(jack)

    }
    def destroy = {
    }
}
