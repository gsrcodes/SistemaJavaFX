package br.fipp.projetosisdental.util;

import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.Paciente;
import br.fipp.projetosisdental.banco.Entidades.Pessoa;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class PesquisaPaciente extends BorderPane {
    private TableView <Pessoa> tableView;
    private Button btConfirmar, btCancelar;

    private Paciente paciente = null;

    public PesquisaPaciente() {
        super();
        tableView = new TableView();
        TableColumn <Pessoa, String> colNome = new TableColumn<>("Nome");
        TableColumn <Pessoa, String> colFone = new TableColumn<>("Telefone");

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFone.setCellValueFactory(new PropertyValueFactory<>("fone"));

        tableView.getColumns().add(colNome);
        tableView.getColumns().add(colFone);

        this.setCenter(tableView);

        btConfirmar = new Button("Confirmar");
        btCancelar = new Button("Cancelar");
        HBox hbox = new HBox(100);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(btConfirmar);
        hbox.getChildren().add(btCancelar);

        btCancelar.setOnAction(e -> {
            getScene().getWindow().hide();
        });
        btConfirmar.setOnAction(e -> {
            getScene().getWindow().hide();
            paciente = (Paciente) tableView.getSelectionModel().getSelectedItem();
        });

        this.setBottom(hbox);

        List<Pessoa> pessoas = new PessoaDAL().get("", new Paciente());
        tableView.setItems(FXCollections.observableArrayList(pessoas));
    }

    public Paciente getPaciente() {
        return paciente;
    }
}
