package br.fipp.projetosisdental.util;

import br.fipp.projetosisdental.HelloApplication;
import br.fipp.projetosisdental.HelloController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UIControl {
    public static String usuario=null;
    public static int nivel=0;
    public static void trocaPainel(String fxml)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            fxmlLoader.load();
            HelloController.staticpainel.setCenter(fxmlLoader.getRoot());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void abreModal(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Parent parent = fxmlLoader.load();
            parent.setStyle("-fx-background-insets: 10; -fx-background-radius: 0; -fx-effect: dropshadow(three-pass-box, rgba(0.5, 0.5, 0.5, 1.0), 10, 0.2, 0, 0);");

            Scene scene = new Scene(parent);
            scene.setFill(new Color(0, 0, 0, 0));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.TRANSPARENT);
            //parent.setEffect(new DropShadow(10, Color.GRAY));
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static void abreHelp(String uri) {
        try {
            WebView webView = new WebView();
            webView.getEngine().load(uri);

            Scene scene = new Scene(webView, 1280, 768);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


