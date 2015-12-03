package FleetGame;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class ShipInformations extends HttpServlet {

   // <img src="images/favicon.png">
    private static final String cruiserImage = "<img src=\"images/cruiser.png\" style=\"width: 256px; height: 256px\">";
    private static final String submarineImage = "<img src=\"images/submarine.png\" style=\"width: 256px; height: 256px\">";
    private static final String destroyerImage = "<img src=\"images/destroyer.png\" style=\"width: 256px; height: 256px\">";
    private static final String battleshipImage = "<img src=\"images/battleship.png\" style=\"width: 256px; height: 256px\">";
    private static final String carrierImage = "<img src=\"images/carrier.png\" style=\"width: 256px; height: 256px\">";

    private static final String cruiserDiscription = "The Cruiser is a fast mobile ship. It doesn't pack much firepower having only one weapon aboard" +
            "but it is perfectly capable of firing and relocating before the enemy knows that hit them";
    private static final String submarineDiscription = "The Submarine is the stealthy killer of the high seas. it lacks mobility but makes up for it" +
            "with the ability to easily scan for enemy positions and strike them hard";
    private static final String destroyerDescription = "The Destroyer is the perfect embodiment of mobile high impact fighting. Once an enemy has been spotted" +
            "the destroyer can make really quick work of them striking them where it hurts. HOOOOOORAH!!";
    private static final String battleshipDescription = "The battleship is the noise maker of the high seas. It is capable of blanketing huge areas with sustained" +
            "high impact shells forcing the enemy to think twice about their positioning.";
    private static final String carrierDescription = "The carrier is the back bone artillery of the your fleet It is capable of blanketing, assist in spotting" +
            "and delivering the pain where it's needed. Once located, it is extremely vulnerable.";


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        writer.println("<h1>The ships of Fleet Commanders</h1><br>");

        writer.println("<h1>Cruiser</h1><br>");
        writer.println(cruiserImage + "<br>");
        writer.println(cruiserDiscription + "<br>");

        writer.println("<h1>Submarine</h1><br>");
        writer.println(submarineImage + "<br>");
        writer.println(submarineDiscription + "<br>");

        writer.println("<h1>Destroyer</h1><br>");
        writer.println(destroyerImage + "<br>");
        writer.println(destroyerDescription + "<br>");

        writer.println("<h1>Battleship</h1><br>");
        writer.println(battleshipImage + "<br>");
        writer.println(battleshipDescription + "<br>");

        writer.println("<h1>Carrier</h1><br>");
        writer.println(carrierImage + "<br>");
        writer.println(carrierDescription + "<br>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
