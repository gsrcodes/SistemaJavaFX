module br.fipp.sisdentalfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.logging;
    requires org.json;
    requires jasperreports;
    requires javafx.web;

    opens br.fipp.projetosisdental to javafx.fxml;
    opens br.fipp.projetosisdental.banco.Entidades to javafx.fxml;
    exports br.fipp.projetosisdental;
    exports br.fipp.projetosisdental.banco.Entidades;
}