package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.Paciente;
import br.fipp.projetosisdental.banco.Entidades.Pessoa;
import br.fipp.projetosisdental.Singleton.Singleton;
import br.fipp.projetosisdental.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PacienteTableController implements Initializable {
    public TextField tfPesquisa;
    public Button btNovo;
    public TableView <Pessoa> tabela;
    public TableColumn <Pessoa, Integer> colID;
    public TableColumn <Pessoa, String> colNome;
    public TableColumn <Paciente, String> colFone;
    public TableColumn <Paciente, String> colCidade;
    public Button btClose;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        this.preencherTabela("");
    }

    private void preencherTabela(String filtro) {
       List<Pessoa> pessoas = new PessoaDAL().get(filtro, new Paciente());
       colID.setCellValueFactory(new PropertyValueFactory<>("id"));
       colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
       colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));
       colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
       this.tabela.setItems(FXCollections.observableArrayList(pessoas));
    }

    public void onNovoPaciente(ActionEvent actionEvent) {
        Singleton.getInstance().setModoEdicao(false);
        UIControl.abreModal("paciente-view.fxml");
        preencherTabela("");
    }

    public void onFiltrar(KeyEvent keyEvent) {
        String filtro = tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(pac_nome) like '%" + filtro + "%'");
    }

    public void onAlterar(ActionEvent actionEvent) {
        int id;
        Paciente p;
        PessoaDAL pd = new PessoaDAL();

        if (tabela.getSelectionModel().getSelectedItem() != null) {
            id = tabela.getSelectionModel().getSelectedItem().getId();
            p = (Paciente) pd.get(id, new Paciente());
            Singleton.getInstance().setModoEdicao(true);
            Singleton.getInstance().setPaciente(p);
            UIControl.abreModal("paciente-view.fxml");
        }
        preencherTabela("");
    }

    public void onApagar(ActionEvent actionEvent) {
        if (tabela.getSelectionModel().getSelectedItem() != null) {
            Pessoa pessoa = tabela.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o paciente " + pessoa.getNome());
            if (alert.showAndWait().get() == ButtonType.OK) {
                new PessoaDAL().apagar(pessoa);
                preencherTabela("");
            }
        }
    }

}
