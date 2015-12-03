package Models

import com.google.gson.JsonObject

class Customization extends Model{

    String profileImage
    String backgroundImage

    static belongsTo = [user:User]

    static constraints = {

    }

    static mapping = {
        profileImage defaultValue: "'http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/prof_26.jpg'"
        backgroundImage defaultValue: "'http://i626.photobucket.com/albums/tt345/thedragonblade/CSC435HW/capital.jpg'"
    }

    @Override
    String toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UserID",user.username);
        jsonObject.addProperty("ProfPic",profileImage);
        jsonObject.addProperty("BackPic",backgroundImage);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return "Customization{" +"user=" + user +", profileImage='" + profileImage + '\'' +", backgroundImage='" + backgroundImage + '\'' +'}';
    }
}
