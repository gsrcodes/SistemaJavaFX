package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.Util.DB;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdmViewController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onCadastros(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("agendamento-view.fxml"));
        fxmlLoader.load();
        HelloController.staticpainel.setCenter(fxmlLoader.getRoot());
    }

    public void onRelatorios(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("relatorios-view.fxml"));
        fxmlLoader.load();
        HelloController.staticpainel.setCenter(fxmlLoader.getRoot());
    }


}
