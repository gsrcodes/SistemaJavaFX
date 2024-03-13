package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.Util.DB;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RelatoriosViewController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void onRelatorioMateriais(MouseEvent mouseEvent){
        gerarRelatorio("select * from material", "src/relatorios/rel_materiais.jasper", "Relação de Materiais");
    }

    public void onRelatorioProcedimentos(MouseEvent mouseEvent){
        gerarRelatorio("select * from procedimento", "src/relatorios/rel_procedimentos.jasper", "Relação de Procedimentos");
    }

    public void onRelatorioPacientes(MouseEvent mouseEvent){
        gerarRelatorio("select * from paciente order by pac_nome", "src/relatorios/rel_pacientes.jasper","Relação de Pacientes");
    }

    public void onRelatorioAgenda(MouseEvent mouseEvent){
        gerarRelatorio("select * from consulta where con_data = CURRENT_DATE", "src/relatorios/rel_agenda.jasper", "Agenda do dia");
    }

    private void gerarRelatorio(String sql, String relat, String titulotela)
    {
        try {
            ResultSet rs = DB.getCon().consultar(sql);
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            String jasperPrint = JasperFillManager.fillReportToFile(relat, null, jrRS);
            JasperViewer viewer = new JasperViewer(jasperPrint, false, false);
            viewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);//maximizado
            viewer.setTitle(titulotela);
            viewer.setVisible(true);
        } catch (JRException erro) {
            System.out.println(erro);
        }
    }
}
