package Controllers.Services;

import Controllers.Controller;
import Models.Comment;
import Models.User;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PostComment extends Controller {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userParam = request.getParameter(USER_PARAMETER);
        String postToParam = request.getParameter(TOWARDS_USER_PARAMETER);
        String postComment = request.getParameter(POST_COMMENT_PARAMETER);
        System.out.println("USER:" + userParam + " Target:" + postToParam + " MSG:" + postComment);
        System.out.println(postComment);
        if((userParam != null && !userParam.isEmpty()) && (postToParam != null && !postToParam.isEmpty()) && (postComment != null && !postComment.isEmpty())) {
            int userParamToInt = -1;
            int userPostToParamToInt = -1;
            try {
                userParamToInt = Integer.parseInt(userParam);
            }catch (NumberFormatException e) {
                userParamToInt = -1;
            }
            try {
                userPostToParamToInt = Integer.parseInt(postToParam);
            }catch (NumberFormatException e) {
                userPostToParamToInt = -1;
            }
            User poster = (userParamToInt > 0) ? User.retrieve(userParamToInt) : User.retrieve(userParam);
            User postedto = (userPostToParamToInt > 0) ? User.retrieve(userPostToParamToInt) : User.retrieve(postToParam);

            if(poster != null && postedto != null) {
                Comment comment = Comment.add(poster.getUserID(), postedto.getUserID(), postComment);
                if(comment != null){
                    System.out.println(comment.toString());
                    JsonObject object = new JsonObject();
                    object.addProperty(getMsgColumn(),comment.getMsg());
                    object.addProperty(getTimePostedColumn(),comment.getTimePosted().toString());
                    object.addProperty(getPostedToUserColumn(),User.retrieveName(comment.getPostedToUser()));
                    object.addProperty(getPosterUserColumn(), User.retrieveName(comment.getPosterUser()));
                    System.out.println("Sending this to javascript: " + object.toString());
                    response.setContentType("application/json");
                    PrintWriter writer = response.getWriter();
                    writer.write(object.toString());
                    System.out.println(object.toString());
                }else {
                    writeError(request,response);
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
        doGet(request,response);
    }
}
