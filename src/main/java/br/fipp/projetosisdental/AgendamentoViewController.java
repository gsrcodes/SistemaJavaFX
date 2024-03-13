package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.ConsultaDAL;
import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.*;
import br.fipp.projetosisdental.util.PesquisaPaciente;
import br.fipp.projetosisdental.util.UIControl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AgendamentoViewController implements Initializable {
    public DatePicker dpDiaConsulta;
    public ComboBox <Dentista>cbDentista;
    public TableView<Horario> tableView;
    public TableColumn<Horario,Integer> colHora;
    public TableColumn <Horario, Paciente> colPaciente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dpDiaConsulta.setValue(LocalDate.now());
        List <Pessoa>dentistas=new PessoaDAL().get("",new Dentista());
        for(Pessoa dentista:dentistas) {
            cbDentista.getItems().add((Dentista)dentista);
        }
    }

    public void onMaterial(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb);
        UIControl.abreModal("material-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onDentista(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb);
        UIControl.abreModal("dentista-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onPaciente(ActionEvent actionEvent) throws IOException {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb);
        UIControl.abreModal("paciente-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onProcedimento(ActionEvent actionEvent) {
        BoxBlur bb = new BoxBlur(15,15,10);
        dpDiaConsulta.getScene().getRoot().setEffect(bb);
        UIControl.abreModal("procedimento-table-view.fxml");
        dpDiaConsulta.getScene().getRoot().setEffect(null);
    }

    public void onTrocouDentista(ActionEvent actionEvent) {
        preencherHorarios();
    }

    public void onAgendar(ActionEvent actionEvent) {
        // abrir janela selecionar um paciente
        PesquisaPaciente pesquisa = new PesquisaPaciente();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(pesquisa));
        stage.setWidth(400);
        stage.setHeight(600);
        stage.showAndWait();

        Paciente paciente = pesquisa.getPaciente();
        if(paciente != null) {
            int hora = tableView.getSelectionModel().getSelectedItem().getHora();
            new ConsultaDAL().gravar(new Consulta(dpDiaConsulta.getValue(), hora, cbDentista.getValue(), paciente, ""));
            preencherHorarios();
        }
    }

    public void onCancelarAgendamento(ActionEvent actionEvent) {
        Paciente paciente = tableView.getSelectionModel().getSelectedItem().getPaciente();
        if(paciente != null) {
            int hora = tableView.getSelectionModel().getSelectedItem().getHora();
            new ConsultaDAL().apagar(new Consulta(dpDiaConsulta.getValue(), hora, cbDentista.getValue(), paciente, ""));
            preencherHorarios();
        }
    }

    public void onTrocouData(ActionEvent actionEvent) {
        preencherHorarios();
    }

    private void preencherHorarios() {
        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("paciente"));
        List<Consulta> cmarcadas = new ConsultaDAL().get(cbDentista.getSelectionModel().getSelectedItem(), dpDiaConsulta.getValue());
        List<Horario> horarios = new ArrayList();
        for(int hora = 8; hora < 18; hora++) {
            horarios.add(new Horario(hora, new Paciente()));
        }
        for(Consulta c : cmarcadas) {
            horarios.set(c.getHorario() - 8, new Horario(c.getHorario(), c.getPaciente()));
        }

        tableView.setItems(FXCollections.observableArrayList(horarios));
    }
}