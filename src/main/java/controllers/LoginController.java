package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button loginBtn;
	@FXML
	private Label errorLbl;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void ingresar(ActionEvent event) {
		errorLbl.setText("");
		String datosUsu="";
		cambiarPantalla(event,datosUsu);
	}
	
	private void cambiarPantalla(Event event,String userData) {
		System.out.println("awgfagwgag");
		  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../resources/views/Pruebas.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				stage.setUserData(userData);
				BuscadorController controller = loader.getController();
				//controller.receiveData(event);

				stage.setTitle("fekjiodgrijogjio");
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				errorLbl.setText("Error iniciar sesion");
				e.printStackTrace();
			}
	}
}
