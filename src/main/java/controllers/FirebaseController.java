package main.java.controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.utils.ManejoDatos;
import main.java.utils.Storage;

public class FirebaseController {
	
	@FXML
	private TextField nameField;
	@FXML
	private TextField passField;
	@FXML
	private TextField confPassField;
	@FXML
	private TextField mailField;
	@FXML
	private Label infoLbl;
	@FXML
	private Button saveBtn;
	
	@FXML
	private void initialize() {
		Storage.conectarFirebase();
	}
	
	@FXML
	public void btnGuardarDatos() {
		
		//int id=(int) (Math.random() * 100000); //Posible implementacion de ID
		boolean puedeCrear=true;
		infoLbl.setStyle("-fx-text-fill: red;");
		
		if(nameField.getText().equals("")) {
			puedeCrear=false;
		}else if(passField.getText().equals("")) {
			puedeCrear=false;
		}else if(confPassField.getText().equals("")) {
			puedeCrear=false;
		}else if(mailField.getText().equals("")) {
			puedeCrear=false;
		}
		
		if(confPassField.getText().equals(passField.getText())) {
			if(puedeCrear) {
				try {
					Map<String, Object> data = new HashMap<>();//Cosas a meter en el documento. La Key es el nombre del campo.
					data.put("Nombre", nameField.getText());
					data.put("Contra", passField.getText());
					data.put("Mail", mailField.getText());
					data.put("Juegos", "");
					
					ManejoDatos.guardarDatos("Persona", /*String.valueOf(id)*/mailField.getText(), data);//se guarda un documento para la persona
					infoLbl.setStyle("-fx-text-fill: black;");
					infoLbl.setText("Guardado con éxito");;
					
				} catch (Exception e) {
					infoLbl.setText("Error al guardarse los datos");
				}
			}else {
				infoLbl.setText("Faltan datos por rellenar");
			}
		}else {
			infoLbl.setText("La contraseña no coincide");
		}
	}
}
	