package main.java.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.utils.Buscador;
public class BuscadorController {

    public static final int maxPages = 20; 
    
	@FXML
	private Label nameLbl, errorLbl;
	@FXML
	private TextField searchField;
	@FXML
	private Button searchBtn;
	
	public static String PagAnterior;
	
	@FXML
	private void initialize() {
	}
	
	@FXML
	public void search(ActionEvent event) {
		errorLbl.setText("");
		
		if (!searchField.getText().equals("")) {
			
			cambiarPantalla(event,searchField.getText());
			
		} else {
			errorLbl.setText("Esta vacio crack");
		}
	}
	
	private void cambiarPantalla(Event event,String text) {
		
		  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../resources/views/CardList_view.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				stage.setUserData(text);
				CardListController controller = loader.getController();
				controller.receiveData(event);

				stage.setTitle("fekjiodgrijogjio");
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				errorLbl.setText("Error al buscar el juego");
				e.printStackTrace();
			}
	}
}
	