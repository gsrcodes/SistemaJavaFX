package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.ProcedimentoDAL;
import br.fipp.projetosisdental.banco.Entidades.Procedimento;
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

public class ProcedimentoTableController implements Initializable {
    public TableView <Procedimento> tabela;
    public TableColumn <Procedimento, String> colID;
    public TableColumn <Procedimento, String> colDescricao;
    public TableColumn <Procedimento, Integer> colTempo;
    public TableColumn <Procedimento, Double> colValor;
    public Button btClose;
    public Button btNovo;
    public TextField tfPesquisa;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btClose.setOnAction(e->{((Button)e.getSource()).getScene().getWindow().hide();});
        this.preencherTabela("");
    }

    private void preencherTabela(String s) {
        List<Procedimento> procedimentos = new ProcedimentoDAL().get(s);
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colTempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        this.tabela.setItems(FXCollections.observableArrayList(procedimentos));
    }
    public void onFiltrar(KeyEvent keyEvent) {
        String filtro = tfPesquisa.getText().toUpperCase();
        preencherTabela("upper(pro_desc) like '%"+filtro+"%'");
    }
    public void onAlterar(javafx.event.ActionEvent actionEvent) {
        int id;
        Procedimento p;
        ProcedimentoDAL pd = new ProcedimentoDAL();

        if(tabela.getSelectionModel().getSelectedItem() != null){
            id = tabela.getSelectionModel().getSelectedItem().getId();
            p = pd.get(id);
            Singleton.getInstance().setModoEdicao(true);
            Singleton.getInstance().setProcedimento(p);
            UIControl.abreModal("procedimento-view.fxml");
        }
        preencherTabela("");
    }
    public void onNovoProcedimento(javafx.event.ActionEvent actionEvent) {
        Singleton.getInstance().setModoEdicao(false);
        UIControl.abreModal("procedimento-view.fxml");
        preencherTabela("");
    }
    public void onApagar(javafx.event.ActionEvent actionEvent) {
        if(tabela.getSelectionModel().getSelectedItem()!=null){
            Procedimento procedimento = (Procedimento) tabela.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Posso apagar definitivamente o procedimento " + procedimento.getDescricao());
            if(alert.showAndWait().get() == ButtonType.OK){
                new ProcedimentoDAL().apagar(procedimento);
                preencherTabela("");
            }
        }
    }
}
