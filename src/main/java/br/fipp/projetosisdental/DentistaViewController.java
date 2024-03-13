package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.Dentista;
import br.fipp.projetosisdental.banco.Util.DB;
import br.fipp.projetosisdental.Singleton.Singleton;
import br.fipp.projetosisdental.util.MaskFieldUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class DentistaViewController implements Initializable {

    @FXML
    public Button tfConf;
    @FXML
    private TextField tfId;
    @FXML
    private TextField tfnome;
    @FXML
    private TextField tfcro;
    @FXML
    private TextField tfFone;
    @FXML
    private TextField tfEmail;
    @FXML
    private Button tfCancelar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> tfId.requestFocus());
        MaskFieldUtil.foneField(tfFone);
        tfCancelar.setOnAction(e -> ((Control)e.getSource()).getScene().getWindow().hide());
        insereDadosInput();
        if(Singleton.getInstance().getModoEdicao() == false){
            tfConf.setOnAction(e -> gravarDentista(e));
        }
        else{
            tfConf.setText("Alterar");
            tfConf.setOnAction(e -> alterarDentista(e));
        }
    }

    public void insereDadosInput(){
        if(Singleton.getInstance().getModoEdicao() == true) {
            tfnome.setText(Singleton.getInstance().getDentista().getNome());
            tfcro.setText(String.valueOf(Singleton.getInstance().getDentista().getCro()));
            tfFone.setText(Singleton.getInstance().getDentista().getFone());
            tfEmail.setText(Singleton.getInstance().getDentista().getEmail());
        }
    }

    private void gravarDentista(javafx.event.ActionEvent e) {
        if(Singleton.getInstance().getModoEdicao() == false){
            Dentista dentista = new Dentista(
                    tfnome.getText(),
                    Integer.parseInt(tfcro.getText()),
                    tfFone.getText(),
                    tfEmail.getText()
            );

            if (!new PessoaDAL().gravar(dentista)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Erro ao gravar dentista " + DB.getCon().getMensagemErro());
                alert.showAndWait();
            }
            ((Control) e.getSource()).getScene().getWindow().hide();
        }
    }

    private void alterarDentista(javafx.event.ActionEvent e){
        int id = Singleton.getInstance().getDentista().getId();
        Dentista dentista = new Dentista(
                id,
                tfnome.getText(),
                Integer.parseInt(tfcro.getText()),
                tfFone.getText(),
                tfEmail.getText()
        );

        if(new PessoaDAL().alterar(dentista)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Dentista atualizado com sucesso!");
            alert.showAndWait();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erro ao gravar dentista " + DB.getCon().getMensagemErro());
            alert.showAndWait();
        }
        ((Control) e.getSource()).getScene().getWindow().hide();
    }

}
