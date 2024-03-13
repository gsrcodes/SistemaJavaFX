package br.fipp.projetosisdental;

import br.fipp.projetosisdental.banco.DALS.PessoaDAL;
import br.fipp.projetosisdental.banco.Entidades.Dentista;
import br.fipp.projetosisdental.banco.Entidades.Pessoa;
import br.fipp.projetosisdental.banco.Entidades.Usuario;
import br.fipp.projetosisdental.Singleton.Singleton;
import br.fipp.projetosisdental.util.UIControl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static BorderPane staticpainel;
    public BorderPane painel;
    public Button btHome;
    public Button btClose;
    public Label lbAcesso;
    public Button btLogout;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        staticpainel = painel;
        btHome.setTooltip(new Tooltip("Ir para o início"));
        btClose.setTooltip(new Tooltip("Fechar o sistema"));
        btHome.setDisable(true);
        btLogout.setDisable(true);

        lbAcesso.setOnMouseClicked(e -> {
            try {
                onAcess(null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean logar(String usuariologin, String senha) {
        Usuario usuario;
        boolean logado = false;
        List<Pessoa> usuarios = new PessoaDAL().get("uso_nome like '%" + usuariologin + "%'", new Usuario());
        Singleton singleton = Singleton.getInstance();

        if (!usuarios.isEmpty()) {
            usuario = (Usuario) usuarios.get(0);

            if (usuario.getSenha().equals(senha)) {
                singleton.setDentista(new Dentista(usuario.getId(), usuario.getNome(), 0, "", ""));
                UIControl.usuario = usuario.getNome();
                UIControl.nivel = usuario.getNivel();
                logado = true;
                btLogout.setDisable(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Senha incorreta");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Usuário não encontrado");
            alert.showAndWait();
        }

        return logado;
    }


    public void onAcess(ActionEvent actionEvent) throws IOException {
        Dialog<Pair<String, String>> loginDialog = new Dialog<>();
        loginDialog.setTitle("Login Sorriso Maroto");
        loginDialog.setHeaderText(null);

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        usernameField.setPromptText("Digite aqui seu usuário");
        passwordField.setPromptText("Senha");

        loginDialog.getDialogPane().setContent(new VBox(10, new Label("Usuário:"), usernameField, new Label("Senha:"), passwordField));

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        loginDialog.getDialogPane().lookupButton(loginButtonType).setDisable(true);

        usernameField.textProperty().addListener((observable, oldValue, newValue) ->
                loginDialog.getDialogPane().lookupButton(loginButtonType).setDisable(newValue.trim().isEmpty() || passwordField.getText().trim().isEmpty()));

        passwordField.textProperty().addListener((observable, oldValue, newValue) ->
                loginDialog.getDialogPane().lookupButton(loginButtonType).setDisable(newValue.trim().isEmpty() || usernameField.getText().trim().isEmpty()));
        loginDialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usernameField.getText(), passwordField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> loginResult = loginDialog.showAndWait();

        loginResult.ifPresent(usernamePassword -> {
            boolean logado = logar(usernamePassword.getKey(), usernamePassword.getValue());
            if (logado) {
                btHome.setDisable(false);
                btLogout.setDisable(false);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
                    fxmlLoader.load();
                    staticpainel.setCenter(fxmlLoader.getRoot());
                    btLogout.setDisable(false);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void onHome(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        staticpainel.setCenter(root);
    }
    public void onClose(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Finalizar o sistema?");
        if(alert.showAndWait().get() == ButtonType.OK)
            Platform.exit();
    }
    public void onLogout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Deseja realmente sair?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            UIControl.usuario = null;
            UIControl.nivel = 0;
            btLogout.setDisable(true);
            Platform.runLater(() -> {
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();

                try {
                    new HelloApplication().start(new Stage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public void onHelp(ActionEvent actionEvent) {
        File file = new File("help/exemplo.html");
        UIControl.abreHelp(file.toURI().toString());
    }
}