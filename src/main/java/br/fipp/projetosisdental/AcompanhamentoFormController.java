package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.ConsultaDAL;
import br.fipp.projetosisdental.banco.DALS.MaterialDAL;
import br.fipp.projetosisdental.banco.DALS.ProcedimentoDAL;
import br.fipp.projetosisdental.banco.Entidades.*;
import br.fipp.projetosisdental.Singleton.Singleton;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;


import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class AcompanhamentoFormController implements Initializable {

    @FXML
    public Button tfConf;
    public javafx.scene.control.TextArea tfRelato;
    public Button btnAddMat;
    public Button btnAddProc;
    public TableView<Consulta> TableMat;
    public TableView<Consulta> TableProc;
    public TableColumn<Consulta,String> colMat;
    public TableColumn<Consulta,Integer> colQuantidadeProc;
    public TableColumn<Consulta,Integer> colquant;
    public TableColumn<Consulta,String> colProc;
    public ComboBox<Material> selectMat;
    public Spinner<Integer> quanttxf;
    public ComboBox<Procedimento> selectProc;
    public Spinner<Integer> quantProctxf;
    @FXML
    private Button tfCancelar;
    private List<Procedimento> procedimentos = new ArrayList<Procedimento>();
    private List<Material> materiais = new ArrayList<Material>();
    private List<Consulta.ItemMat> ListaItMat;
    private List<Consulta.ItemProc> ListaItProc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Horario horario = Singleton.getInstance().getHorario();
            String sql = "pac_id = '" + horario.getPaciente().getId() + "' and con_horario = " + horario.getHora();
            Consulta consulta = (Consulta) new ConsultaDAL().get(sql).get(0);
            tfRelato.setText(consulta.getRelato());
            List<Procedimento> procedimentos = new ProcedimentoDAL().get("");
            for(Procedimento procedimento : procedimentos){
                selectProc.getItems().add(procedimento);
            }
            List<Material> materiais = new MaterialDAL().get("");
            for(Material material : materiais){
                selectMat.getItems().add(material);
            }
            selectProc.setValue((Procedimento) procedimentos.get(0));
            selectMat.setValue((Material)materiais.get(0));
            //SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
            //quanttxf.setValueFactory(valueFactory);
            //SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
            //quantProctxf.setValueFactory(valueFactory2);
            //preencherMateriais();
            //preencherProcedimento();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar relato,material e procedimento");
            alert.showAndWait();
        }
    }

    public void onCancelar(javafx.event.ActionEvent actionEvent) {
        tfCancelar.getScene().getWindow().hide();
    }

    public void onConfirmar(javafx.event.ActionEvent actionEvent){
        Horario horario = Singleton.getInstance().getHorario();
        String sql = "pac_id = '" + horario.getPaciente().getId() + "' and con_horario = " + horario.getHora();
        Consulta consulta = (Consulta) new ConsultaDAL().get(sql).get(0);
        consulta.setRelato(tfRelato.getText());
        if(new ConsultaDAL().alterar(consulta)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText("Relato salvo com sucesso");
            alert.showAndWait();
            tfCancelar.getScene().getWindow().hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao salvar relato");
            alert.showAndWait();
        }
    }

    public void onAdicionarProc(ActionEvent actionEvent) {
        /*
        Procedimento procedimento = selectProc.getValue();
        int quantidade = colQuantidadeProc.getValue();

        // Cria um novo ItemMat com a quantidade e o material selecionado
        Consulta.ItemProc itemProc = new Consulta.ItemProc(quantidade, procedimento);

        // Adiciona o ItemMat à lista de materiais da consulta no Singleton
        Singleton.getInstance().getConsulta().addProcedimento(itemProc);

        // Adiciona o ItemMat diretamente à lista de itens da tabela
        TableProc.getItems().addAll(itemProc);
        */ // Não está funcionando
    }

    public void onAdicionarMat(ActionEvent actionEvent) {
        /*
        Material material = selectMat.getValue();
        int quantidade = quanttxf.getValue();

        // Cria um novo ItemMat com a quantidade e o material selecionado
        Consulta.ItemMat itemMat = new Consulta.ItemMat(quantidade, material);

        // Adiciona o ItemMat à lista de materiais da consulta no Singleton
        Singleton.getInstance().getConsulta().addMaterial(itemMat);

        // Adiciona o ItemMat diretamente à lista de itens da tabela
        TableMat.getItems().addAll(itemMat);
        */ // Não está funcionando
    }
    private void preencherMateriais(){
        /*
        //colMaterial.setCellValueFactory(new PropertyValueFactory<>("material"));
        //colQtdeMaterial.setCellValueFactory(new PropertyValueFactory<>("quant"));
        colMat.setCellValueFactory(cellData->{
            Consulta.ItemMat itMat = cellData.getValue();
            Material m = itMat.material();
            return new SimpleObjectProperty<>(m);
        });
        colquant.setCellValueFactory(cellData->{
            Consulta.ItemMat itemMat = cellData.getValue();
            int quant = itemMat.quant();
            return new SimpleIntegerProperty(quant).asObject();
        });
        TableMat.setItems(FXCollections.observableArrayList(ListaItMat));
        */
    }
    // Esse não está funcionando
    private void preencherProcedimentos(){
        /*
        colProc.setCellValueFactory(cellData->{
            Consulta.ItemProc itProc = cellData.getValue();
            Material m = itProc.procedimento();
            return new SimpleObjectProperty<>(m);
        });
        colQuantidadeProc.setCellValueFactory(cellData->{
            Consulta.ItemProc itemProc = cellData.getValue();
            int quant = itemProc.quant();
            return new SimpleIntegerProperty(quant).asObject();
        });
        TableProc.setItems(FXCollections.observableArrayList(ListaItProc));
        */ // Não está funcionando
    }
}
