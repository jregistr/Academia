package Controllers.Services;

import Controllers.Controller;
import Models.Battle;
import Models.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class GetUsers extends Controller {

    private static final String INFO_TYPE_PARAMETER = "infoType";

    private static final String NAMES_PARAMETER = "names";
    private static final String FULL_HISTORY_PARAMETER = "history";
    private static final String BATTLE_HISTORY_PARAMETER = "battles";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter(INFO_TYPE_PARAMETER);
        System.out.println(request.getParameter(INFO_TYPE_PARAMETER) + "," + request.getParameter(FIND_USERS_LIKE_PARAMETER));
        if(type != null && !type.isEmpty()){
            if(type.equals(NAMES_PARAMETER)){
                String begins = request.getParameter(FIND_USERS_LIKE_PARAMETER);
                if(begins != null) {
                    String[] names = User.retrieveNamesLike(begins);
                    if(names != null && names.length > 0) {
                        HashMap<String, String[]> map = new HashMap<String, String[]>();
                        map.put("names", names);
                        response.setContentType("application/json");
                        PrintWriter writer = response.getWriter();
                        writer.write(new Gson().toJson(map));
                    }else {
                        writeError(request, response);
                    }
                }
            }else if(type.equals(FULL_HISTORY_PARAMETER)) {
                System.out.println("History");
                String idString = request.getParameter("id");
                if(idString != null && !idString.isEmpty()) {
                    int id = toInt(idString);
                    if(id > 0){
                        response.setContentType("application/json");
                        PrintWriter writer = response.getWriter();
                        System.out.println("ID: " + id);
                        writer.write(Battle.fullHistory(id));
                    }else
                        writeError(request, response);
                }else {
                    writeError(request, response);
                }
            }else if(type.equals(BATTLE_HISTORY_PARAMETER)){
                String idString = request.getParameter("id");
                if(idString != null && !idString.isEmpty()) {
                    int id = toInt(idString);
                    if(id > 0){
                        response.setContentType("application/json");
                        PrintWriter writer = response.getWriter();
                        writer.write(Battle.retrieveToJson(id));
                    }else
                        writeError(request, response);
                }else {
                    writeError(request, response);
                }
            }else {
                writeError(request, response);
            }
        }else {
            writeError(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get u post");
        doGet(request, response);
    }
}
