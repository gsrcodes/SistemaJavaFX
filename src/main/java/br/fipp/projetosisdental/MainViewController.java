package br.fipp.projetosisdental;

import br.fipp.projetosisdental.util.UIControl;
import javafx.fxml.Initializable;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    public ImageView opADM;
    public ImageView opSecretaria;
    public ImageView opDentista;
    public ImageView opConfig;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(UIControl.nivel==3) {
            opDentista.setDisable(false);
            opDentista.setOpacity(1);
            opConfig.setDisable(false);
            opConfig.setOpacity(1);
            opADM.setDisable(true);
            opADM.setOpacity(0);
            opSecretaria.setDisable(true);
            opSecretaria.setOpacity(0);
        }
        if(UIControl.nivel==2) { // secretaria
            opSecretaria.setDisable(false);
            opSecretaria.setOpacity(1);
            opConfig.setDisable(false);
            opConfig.setOpacity(1);
            opADM.setDisable(true);
            opADM.setOpacity(0);
            opDentista.setDisable(true);
            opDentista.setOpacity(0);
        }
        if(UIControl.nivel==1) { // ADM
            opSecretaria.setDisable(false);
            opSecretaria.setOpacity(1);
            opADM.setDisable(false);
            opADM.setOpacity(1);
            opDentista.setDisable(false);
            opDentista.setOpacity(1);
            opConfig.setDisable(false);
            opConfig.setOpacity(1);
        }
    }
    public void onSecretaria(MouseEvent mouseEvent) throws IOException {
        UIControl.trocaPainel("agendamento-view.fxml");
    }
    public void onAdm(MouseEvent mouseEvent) throws IOException {
        UIControl.trocaPainel("adm-view.fxml");
    }
    public void onDentista(MouseEvent mouseEvent) throws IOException{
        UIControl.trocaPainel("acompanhamento-view.fxml");
    }

    public void onConfig(MouseEvent mouseEvent) {

    }
    public void onMouseExited(MouseEvent mouseEvent) {

        ((ImageView)mouseEvent.getSource()).setEffect(null);
    }

    public void onMouseEntered(MouseEvent mouseEvent) {
        ((ImageView)mouseEvent.getSource()).setEffect(new InnerShadow());
    }
}