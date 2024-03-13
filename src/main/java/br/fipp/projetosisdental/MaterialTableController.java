package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.MaterialDAL;
import br.fipp.projetosisdental.banco.Entidades.Material;
import br.fipp.projetosisdental.Singleton.Singleton;
import br.fipp.projetosisdental.util.UIControl;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MaterialTableController implements Initializable {
    public TableView <Material> tabela;
    public TableColumn <Material, Integer> colID;
    public TableColumn <Material, String> colDescricao;
    public TableColumn <Material, Double> colValor;
    public Button btClose;
    public Button btNovo;
    public TextField tfPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        this.preencherTabela("");
    }

    private void preencherTabela(String s) {
        List<Material> materiais = new MaterialDAL().get(s);
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("preco"));
        this.tabela.setItems(FXCollections.observableArrayList(materiais));
    }
    public void onFiltrar(KeyEvent keyEvent) {
        String filtro = tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(mat_desc) like '%" + filtro + "%'");
    }

    public void onNovoMaterial(javafx.event.ActionEvent actionEvent) {
        Singleton.getInstance().setModoEdicao(false);
        UIControl.abreModal("material-view.fxml");
        preencherTabela("");
    }

    public void onAlterar(javafx.event.ActionEvent actionEvent) {
        int id;
        Material m;
        MaterialDAL md = new MaterialDAL();

        if (tabela.getSelectionModel().getSelectedItem() != null) {
            id = tabela.getSelectionModel().getSelectedItem().getId();
            m = md.get(id);
            Singleton.getInstance().setModoEdicao(true);
            Singleton.getInstance().setMaterial(m);
            UIControl.abreModal("material-view.fxml");
        }
        preencherTabela("");
    }

    public void onApagar(javafx.event.ActionEvent actionEvent) {
        if (tabela.getSelectionModel().getSelectedItem() != null) {
            Material material = tabela.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o material " + material.getDescricao());
            if (alert.showAndWait().get() == ButtonType.OK) {
                new MaterialDAL().apagar(material);
                preencherTabela("");
            }
        }
    }

}
