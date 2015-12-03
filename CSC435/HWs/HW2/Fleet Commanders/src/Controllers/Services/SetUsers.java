package Controllers.Services;

import Controllers.Controller;
import Models.Customization;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SetUsers extends Controller{

    private static final String TARGET_PARAMETER = "target";
    private static final String SET_PROFILE_IMG_PARAMETER = "prof";
    private static final String SET_BACK_IMG_PARAMETER = "back";
    private static final String COMMAND_PARAMETER = "command";
    private static final String COMMAND_SET = "set";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SET_VALUE = "value";



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter(COMMAND_PARAMETER);
        String target = request.getParameter(TARGET_PARAMETER);
        String idString = request.getParameter(getUserIdColumn());
        System.out.println(command + "," + target  + "," + idString);
        if(command != null && !command.isEmpty() && target != null && !target.isEmpty() && idString != null && !idString.isEmpty()) {
            int id = toInt(idString);
            if (id > 0 && command.equals(COMMAND_SET)) {
                String value = request.getParameter(COMMAND_SET_VALUE);
                if (value != null && !value.isEmpty()) {
                    if (target.equals(SET_BACK_IMG_PARAMETER)) {
                        Customization customization = Customization.retrieve(id);
                        if(customization != null) {
                            customization.setBackgroundImage(value);
                            customization.save();
                        }else {
                            customization = new Customization(Customization.DEFAULT_PROFILE_IMG, value,id);
                            customization.save();
                        }
                        PrintWriter writer = response.getWriter();
                        if(customization != null)
                             writer.write(Customization.retrieveToJson(id));
                        else
                            writeError(request, response);
                    } else if (target.equals(SET_PROFILE_IMG_PARAMETER)) {
                        Customization customization = Customization.retrieve(id);
                        if(customization != null) {
                            customization.setProfileImage(value);
                            customization.save();
                        }else {
                            customization = new Customization(value, Customization.DEFAULT_BACK_IMG, id);
                            customization.save();
                        }
                        PrintWriter writer = response.getWriter();
                        if(customization != null)
                            writer.write(customization.toJson());
                        else
                            writeError(request, response);
                    } else
                        writeError(request, response);
                } else
                    writeError(request, response);
            } else if (command.equals(COMMAND_DELETE)) {
                if (target.equals(SET_BACK_IMG_PARAMETER)) {
                    Customization customization = Customization.retrieve(id);
                    if(customization != null) {
                        customization.setBackgroundImage(Customization.DEFAULT_BACK_IMG);
                        customization.save();
                        PrintWriter writer = response.getWriter();
                        writer.write(Customization.retrieveToJson(id));
                    }
                } else if (target.equals(SET_PROFILE_IMG_PARAMETER)) {
                    Customization customization = Customization.retrieve(id);
                    if(customization != null) {
                        customization.setProfileImage(Customization.DEFAULT_PROFILE_IMG);
                        customization.save();
                        PrintWriter writer = response.getWriter();
                        writer.write(customization.toJson());
                    }
                } else
                    writeError(request, response);
            } else
                writeError(request, response);
        }else
            writeError(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
