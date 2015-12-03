package Suggestron;


import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Suggestor {
    public TextField userURL;
    public Hyperlink suggestionHyper;
    public ImageView refreshingImage;
    public Button initButton;
    public Button userURLButton;

    public void urlBoxClicked(){
        userURL.setText("");
    }

    public void matchedHyperClicked(){

    }

    public void urlButtonClicked(){
        String typedURL = userURL.getText();
        boolean correctURL = false;
        try {
            URL url = new URL(typedURL);
            correctURL = true;
            url = null;
        } catch (MalformedURLException e) {
            userURL.setText("URL WAS NOT CORRECTLY FORMATTED!!");
        }
        if(correctURL){
           // changeRefreshImageState(true);
            changeURLButonState(false);
            changeUserURLState(false);
            try {
                String best = Comparetron.getInstance().getMostSimilar(typedURL);
                changeHyperState(true);
                suggestionHyper.setText(best);
                changeURLButonState(true);
                changeUserURLState(true);
            } catch (IOException e) {
                userURL.setText("URL WAS NOT CORRECTLY FORMATTED!!");
                changeHyperState(false);
                changeURLButonState(true);
                changeUserURLState(true);
            }
        }
    }

    public void initButtonClicked() throws IOException {
        initButton.setDisable(true);
        initButton.setOpacity(0);
        changeRefreshImageState(true);
        Comparetron.getInstance().setBases();
        changeRefreshImageState(false);
        changeUserURLState(true);
        changeURLButonState(true);
    }

    private void changeURLButonState(boolean enable){
        userURLButton.setDisable(!enable);
        if(enable)
            userURLButton.setOpacity(1);
        else
            userURLButton.setOpacity(0);
    }

    private void changeRefreshImageState(boolean enable){
        refreshingImage.setDisable(!enable);
        if(enable)
            refreshingImage.setOpacity(1);
        else
            refreshingImage.setOpacity(0);
    }

    private void changeUserURLState(boolean enable){
        userURL.setDisable(!enable);
        if(enable)
            userURL.setOpacity(1);
        else
            userURL.setOpacity(0);
    }

    private void changeHyperState(boolean enable){
        suggestionHyper.setDisable(!enable);
        if(enable)
            suggestionHyper.setOpacity(1);
        else
            suggestionHyper.setOpacity(0);
    }

}
