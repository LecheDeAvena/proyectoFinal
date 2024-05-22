package main.java.controllers;

import java.util.concurrent.ExecutionException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.utils.ManejoDatos;
import main.java.utils.Storage;

public class LoginController {

	@FXML
	private Button loginBtn;
	@FXML
	private Label errorLbl;
	@FXML	
	private Hyperlink RegisterLink;
	
	@FXML
	private void initialize() {
		Storage.conectarFirebase();
	}
	
	@FXML
	public void ingresar(ActionEvent event) {
		errorLbl.setText("");
		String datosUsu="";
		ManejoDatos.sacarDatos("Persona","ramon@ram.on");
		cambiarPantalla(event,datosUsu,"Pruebas");

	}
	
	@FXML
	public void registrar(ActionEvent event) {
		errorLbl.setText("");
		String datosUsu="";
		cambiarPantalla(event,datosUsu,"Firebase_view");
	}
	
	private void cambiarPantalla(Event event,String userData, String view) {
		System.out.println("awgfagwgag");
		  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../resources/views/"+view+".fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				stage.setUserData(userData);
				if(view.equals("Pruebas")) {

					BuscadorController controller = loader.getController();
				}else {

					FirebaseController controller = loader.getController();
				}
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
