package BtreeSuggestron;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Scene.*;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Main extends Application {

    private Scene opening,init, program;
    private Stage window;

    public static void main(String[] args) throws IOException {
        //Suggestron.getInstance().Initialiase();

        /*String[] sugs = Suggestron.getInstance().getRecommendations("https://www.assetstore.unity3d.com/en/#!/content/25162");
        for (String sug : sugs) {
            System.out.println(sug);
        }*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //LocalDate dt = LocalDate.parse("");
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        window = primaryStage;


        primaryStage.setTitle("Suggestron 9000");
        primaryStage.setMaxHeight(400);
        primaryStage.setMaxWidth(600);

        opening = defineOpeningScene();
        primaryStage.setScene(opening);
        primaryStage.show();


        /*try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }*/

        program = defineProgramScene();

        if (!Suggestron.getInstance().isInitialsed()) {
            init = defineInitScene(false);
            primaryStage.setScene(init);
        } else {
            if (Suggestron.getInstance().needsUpdate()) {
                init = defineInitScene(true);
                primaryStage.setScene(init);
            } else {
                primaryStage.setScene(program);
            }
        }

        primaryStage.show();
    }

    private void DoInit() {
        window.setScene(opening);
        window.show();
        try {
            Suggestron.getInstance().Initialiase();
        } catch (IOException e) {
            System.out.println("Failed to initialise");
        }
        window.setScene(program);
        window.show();
    }

    private void DoUpdate() {
        Suggestron.getInstance().DoUpdate();
    }

    private boolean properUrl(String urlString){
        try {
            URL url = new URL(urlString);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    private Scene defineProgramScene() {
        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        Label label = new Label("Suggestron ready to go...");
        label.setFont(new Font(30));
        Image image = new Image(new File("btree.png").toURI().toString(), 20, 24, true, true);
        ImageView imageView = new ImageView(image);
        topBox.getChildren().addAll(label, imageView);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);

        VBox midGroup = new VBox();
        HBox inputGroup = new HBox();


        Label errorLabel = new Label("Badly formatted link");
        errorLabel.setFont(new Font(18));
        errorLabel.setVisible(false);


        TextField textInput = new TextField();
        textInput.setPromptText("Enter a link and press harmless button");

        Button submit = new Button("Submit");


        inputGroup.getChildren().addAll(textInput, submit);
        inputGroup.setAlignment(Pos.CENTER);


        HBox firstSuggestBox = new HBox();
        firstSuggestBox.setAlignment(Pos.CENTER);
        TextField first = new TextField();
        firstSuggestBox.setPrefWidth(500);
        Label firstLabel = new Label("First");
        first.setFont(new Font(18));
        firstLabel.setLabelFor(first);

        firstSuggestBox.getChildren().addAll(firstLabel, first);


        HBox secondSuggestBox = new HBox();
        secondSuggestBox.setAlignment(Pos.CENTER);
        TextField second = new TextField();
        Label seconLabel = new Label("Second");
        second.setFont(new Font(18));
        seconLabel.setLabelFor(second);
        secondSuggestBox.getChildren().addAll(seconLabel, second);

        HBox thirdSuggestBox = new HBox();
        thirdSuggestBox.setAlignment(Pos.CENTER);
        TextField third = new TextField();
        Label thirdLabel = new Label("Third");
        third.setFont(new Font(18));
        thirdLabel.setLabelFor(third);
        thirdSuggestBox.getChildren().addAll(thirdLabel, third);

        midGroup.getChildren().addAll(inputGroup,errorLabel,firstSuggestBox,secondSuggestBox,thirdSuggestBox);
        midGroup.setAlignment(Pos.CENTER);
        borderPane.setCenter(midGroup);

        submit.setOnAction(x-> {
            String text = textInput.getText();
            textInput.clear();
            if(properUrl(text)){
                if(errorLabel.isVisible()){
                    errorLabel.setVisible(false);
                }
                first.clear();
                second.clear();
                third.clear();
                window.setScene(opening);
                window.show();

                Task<String[]> task = new Task<String[]>() {
                    @Override
                    protected String[] call() throws Exception {
                        System.out.println("Running Task");
                        return Suggestron.getInstance().getRecommendations(text);
                    }
                };
                Thread thread = new Thread(task);
                thread.start();
                task.setOnSucceeded(z->showRecommendations(task.getValue(),first,second,third));
                task.setOnFailed(z->showRecommendations(null,first,second,third));
                /*task.setOnSucceeded(z->{
                    String[] vals = task.getValue();
                    for (String val : vals) {
                        System.out.println("Val:" + val);
                    }
                });*/


            }else {
                errorLabel.setVisible(true);
            }
        });

        return new Scene(borderPane, 600, 400);
    }

    private void showRecommendations(String[] suggestions,TextField first,TextField second,TextField third){
       // String[] suggestions = null;
       /* try {
            suggestions = Suggestron.getInstance().getRecommendations(text);

        } catch (IOException e) {
            suggestions = null;
        }*/

        if(suggestions != null){
            window.setScene(program);
            window.show();
            first.setText(suggestions[0]);
            second.setText(suggestions[1]);
            third.setText(suggestions[2]);
        }else {
            //throw new IllegalArgumentException("Trying to get suggestion went wrong");
            window.setScene(program);
            window.show();
        }
    }

    private Scene defineInitScene(boolean needsUpdate) {
        BorderPane borderPane = new BorderPane();
        HBox topBox = new HBox();
        Label label = new Label("Tree needs Inits or Update");

        Image image = new Image(new File("btree.png").toURI().toString(), 40, 48, true, true);
        ImageView imageView = new ImageView(image);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(label, imageView);
        borderPane.setTop(topBox);

        VBox mid = new VBox();
        Button initButton = new Button("Initialse");
        initButton.setMinHeight(50);
        initButton.setMinWidth(100);
        initButton.setOnAction(x -> DoInit());
        initButton.setAlignment(Pos.CENTER);

        Button updateButton = new Button("Update");
        updateButton.setMinHeight(50);
        updateButton.setMinWidth(100);
        updateButton.setOnAction(x -> DoUpdate());
        updateButton.setAlignment(Pos.CENTER);
        updateButton.setDisable(!needsUpdate);

        mid.getChildren().addAll(initButton, updateButton);
        mid.setAlignment(Pos.CENTER);
        borderPane.setCenter(mid);
        return new Scene(borderPane, 600, 400);
    }

    private Scene defineOpeningScene() {
        BorderPane pane = new BorderPane();
        HBox box = new HBox();
        Label label = new Label("Launching Suggestron.....");
        //label.setText("Launching Suggestron.....");
        label.setFont(new Font(20));
        label.setAlignment(Pos.CENTER);
        box.getChildren().add(label);
        box.setAlignment(Pos.CENTER);
        pane.setTop(box);

        //new Image("btree.png", 240, 200, true, false)
        Image image = new Image(new File("btree.png").toURI().toString(), 240, 200, true, true);
        ImageView imageView = new ImageView(image);
        HBox mid = new HBox();
        mid.getChildren().add(imageView);
        mid.setAlignment(Pos.CENTER);
        pane.setCenter(mid);
        /*pane.setMaxWidth(600);
        pane.setMaxHeight(400);*/

        HBox footer = new HBox();
        footer.getChildren().add(new Label("Painfull Assignment"));
        pane.setBottom(footer);
        return new Scene(pane,600,400);
    }



}
