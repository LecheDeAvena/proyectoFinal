package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;


import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.utils.Buscador;

public class CardListController{

	@FXML
	private ListView<Pane> juegosList;
	
	String searchText="";
	
	private void inicializar() {
		// TODO Auto-generated method stub
		ObservableList<Pane> items =FXCollections.observableArrayList();
		
		if(!searchText.equals("")) {
			Map<String, String[]> gamesFound=Buscador.buscarJuego(searchText);
			
			for (Entry<String, String[]> juego : gamesFound.entrySet()) {
				
			    items.add(newCard(juego.getKey(),juego.getValue()[0],juego.getValue()[1],juego.getValue()[2]));
			}
			
			juegosList.setItems(items);
		}
	}
	
	private Pane newCard(String title, String date, String imgURL, String gameURL) {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../resources/views/GameCard_view.fxml"));
			Pane newPane = loader.load();
			GameCardController controller = loader.getController();
			controller.setData(title, date, imgURL);
			
			newPane.setOnMouseClicked(new EventHandler <MouseEvent>() {
				
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
					if(arg0.getButton().equals(MouseButton.PRIMARY)&&arg0.getClickCount()==2) {
						//Entrar en juego
						cambiarPantalla(arg0, gameURL);
					}
				}
			});
			
			return newPane;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	void receiveData(Event event) {
		  
	  Node node = (Node) event.getSource();
	  Stage stage = (Stage) node.getScene().getWindow();
	  
	  searchText=(String) (stage.getUserData());
	  
	  inicializar();
	}
	
	private void cambiarPantalla(Event event,String gameURL) {
		
		  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../../resources/views/GameInfo_view.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);

				stage.setUserData(gameURL);
				GamePreviewController controller = loader.getController();
				controller.receiveData(event);
				stage.setTitle("fekjiodgrijogjio");
				stage.setScene(scene);
				stage.show();
			} catch (Exception e) {
				//mensaje de error
				System.out.print("Error cambiar de pantalla");
			}
	}
}
