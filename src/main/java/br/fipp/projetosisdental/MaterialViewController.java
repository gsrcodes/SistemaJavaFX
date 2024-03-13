package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.MaterialDAL;
import br.fipp.projetosisdental.banco.Entidades.Material;
import br.fipp.projetosisdental.banco.Util.DB;
import br.fipp.projetosisdental.Singleton.Singleton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MaterialViewController implements Initializable {
    public TextField tfId;
    public TextField tfValor;
    public TextField tfDescricao;
    public Button btnConfirmar;
    public Button btnCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> tfDescricao.requestFocus());
        btnCancelar.setOnAction(e -> ((Control)e.getSource()).getScene().getWindow().hide());
        insereDadosInput();
        if(Singleton.getInstance().getModoEdicao() == false)
            btnConfirmar.setOnAction(this::gravarMaterial);
        else{
            btnConfirmar.setText("Atualizar");
            btnConfirmar.setOnAction(e -> atualizarMaterial(e));
        }
    }
    private void atualizarMaterial(ActionEvent e){
        int id = Singleton.getInstance().getMaterial().getId();

        Material m = new Material(
                id,
                tfDescricao.getText(),
                Double.parseDouble(tfValor.getText())
        );

        if(new MaterialDAL().alterar(m)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Material atualizado com sucesso!");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar material " + DB.getCon().getMensagemErro());
            alert.showAndWait();
        }
        ((Control) e.getSource()).getScene().getWindow().hide();
    }
    private void gravarMaterial(ActionEvent e) {

        Material material = new Material(
                tfDescricao.getText(),
                Double.parseDouble(tfValor.getText())
        );

        if(!new MaterialDAL().gravar(material)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao cadastrar material "+ DB.getCon().getMensagemErro());
            alert.showAndWait();
        }

        ((Control)e.getSource()).getScene().getWindow().hide();
    }
    public void insereDadosInput(){
        if(Singleton.getInstance().getModoEdicao() == true){
            tfDescricao.setText(Singleton.getInstance().getMaterial().getDescricao());
            tfValor.setText(String.valueOf(Singleton.getInstance().getMaterial().getPreco()));
        }
    }
}
