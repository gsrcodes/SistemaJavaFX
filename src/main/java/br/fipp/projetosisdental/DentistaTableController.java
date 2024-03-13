package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.Dentista;
import br.fipp.projetosisdental.banco.Entidades.Pessoa;
import br.fipp.projetosisdental.Singleton.Singleton;
import br.fipp.projetosisdental.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DentistaTableController implements Initializable {

    @FXML
    public TableView <Pessoa> tabela;
    @FXML
    public Button btClose;
    @FXML
    public TextField tfPesquisa;
    @FXML
    public Button btNovo;
    @FXML
    public TableColumn <Pessoa, Integer> colID;
    @FXML
    public TableColumn <Pessoa, String> colNome;
    @FXML
    public TableColumn <Pessoa, Integer> colCro;
    @FXML
    public TableColumn <Pessoa, String> colFone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        this.preencherTabela("");
    }

    private void preencherTabela(String filtro) {
        List<Pessoa> pessoa = new PessoaDAL().get(filtro, new Dentista());
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCro.setCellValueFactory(new PropertyValueFactory<>("cro"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        this.tabela.setItems(FXCollections.observableArrayList(pessoa));
    }

    public void onApagar(ActionEvent actionEvent) {
        if (tabela.getSelectionModel().getSelectedItem()!=null) {
            Pessoa pessoa = tabela.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o dentista "+ pessoa.getNome());
            if(alert.showAndWait().get()==ButtonType.OK){
                new PessoaDAL().apagar(pessoa);
                preencherTabela("");
            }
        }
    }
    public void onAlterar(ActionEvent actionEvent) {
        int id;
        Dentista d;
        PessoaDAL pd = new PessoaDAL();

        if(tabela.getSelectionModel().getSelectedItem() != null){
            id = tabela.getSelectionModel().getSelectedItem().getId();
            d = (Dentista) pd.get(id, new Dentista());
            Singleton.getInstance().setModoEdicao(true);
            Singleton.getInstance().setDentista(d);
            UIControl.abreModal("dentista-view.fxml");
        }
        preencherTabela("");
    }
    public void onFiltrar(KeyEvent keyEvent) {
        String filtro = tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(den_nome) like '%"+filtro+"%'");
    }

    public void onNovoDentista(ActionEvent actionEvent) {
        Singleton.getInstance().setModoEdicao(false);
        UIControl.abreModal("dentista-view.fxml");
        preencherTabela("");
    }
}