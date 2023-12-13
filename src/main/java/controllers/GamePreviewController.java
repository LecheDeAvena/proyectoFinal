package main.java.controllers;

import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.utils.Buscador;

public class GamePreviewController {

	@FXML
	private Label gameTitleLbl,tagTitleLbl,descTitleLbl,descLbl,priceLbl;
	@FXML
	private HBox tagContainer;
	@FXML
	private ImageView gameImg;
	@FXML
	private ListView offersList;
	
	private String steamURL="";
	
	private void inicializar() {
		// TODO Auto-generated method stub
		ObservableList<Pane> items =FXCollections.observableArrayList();
		
		if(!steamURL.equals("")) {
				Map<String,String> gameData=Buscador.getInfoJuego(steamURL);
				
				gameTitleLbl.setText(gameData.get("title"));
				descLbl.setText(gameData.get("desc"));
				gameImg.setImage(new Image(gameData.get("img")));
				priceLbl.setText(gameData.get("price"));
				
				String[] genreTags=gameData.get("genre").split(",");
				for(int i=0;i<genreTags.length;i++) {
					Label newTag = new Label(genreTags[i]);
					tagContainer.getChildren().add(newTag);
				}
				Buscador.sacarPrecios(gameData.get("title"));
		}
	}
	
	private void setOffersList() {
		
	}
	
	void receiveData(Event event) {
		  
		  Node node = (Node) event.getSource();
		  Stage stage = (Stage) node.getScene().getWindow();
		  
		  steamURL=(String) (stage.getUserData());
		  

		  inicializar();
		}
}
