package Models

import com.google.gson.JsonObject

class Customization extends Model{

    String profileImage = "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/prof_26.jpg"
    String backgroundImage = "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/capital.jpg"

    private static final String[] PROFILE_IMAGES = [
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/avatar_05.jpg",
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/avatar_02.jpg",
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/avatar_01.jpg",
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/avatar_04.jpg",
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/avatar_03.jpg"
    ]

    private static final String[] BACKGROUND_IMAGES = [
            "http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/capital.jpg",
            "http://www.imgbase.info/images/safe-wallpapers/anime/avatar_the_last_airbender/20848_avatar_the_last_airbender.jpg",
            "http://free.wallpaperbackgrounds.com/military/jet%20fighter/175539-35102.jpg"
    ]

    static belongsTo = [user:User]

    static constraints = {

    }
   /* static mapping = {
        profileImage defaultValue: "'http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/prof_26.jpg'"
        backgroundImage defaultValue: "'http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/capital.jpg'"
    }*/

    public static Customization randomCustomization(User temp){
        def rand = new Random()
        String prof = PROFILE_IMAGES[rand.nextInt(PROFILE_IMAGES.length)]
        String back = BACKGROUND_IMAGES[rand.nextInt(BACKGROUND_IMAGES.length)]
        new Customization(user: temp,profileImage: prof,backgroundImage: back)
    }

    @Override
    JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserID",user.username);
        jsonObject.addProperty("ProfPic",profileImage);
        jsonObject.addProperty("BackPic",backgroundImage);
        jsonObject
    }

    @Override
    String toJson() {
        toJsonObject().toString()
    }

    @Override
    public String toString() {
        return "Customization{" +"user=" + user +", profileImage='" + profileImage + '\'' +", backgroundImage='" + backgroundImage + '\'' +'}';
    }
}
