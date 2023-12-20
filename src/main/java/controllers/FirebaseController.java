package main.java.controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import main.java.utils.ManejoDatos;

public class FirebaseController {
	
	@FXML
	TextField NameField,InfoLbl;
	@FXML
	Button SabeBtn;
	
	@FXML
	private void btnGuardarDatos() {
		
		int id=(int) (Math.random() * 100000);
		
		try {
			Map<String, Object> data = new HashMap<>();
			data.put("Nombre", NameField.getText());
			
			ManejoDatos.guardarPersona("Persona", String.valueOf(id), data);
			InfoLbl.setStyle("-fx-text-fill: black;");
			InfoLbl.setText("Guardado con éxito");
			
		} catch (Exception e) {
			InfoLbl.setStyle("-fx-text-fill: red;");
			InfoLbl.setText("Error al guardarse los datos");
		}
	}
}
