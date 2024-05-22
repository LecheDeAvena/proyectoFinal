package main.java.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.utils.Buscador;
import main.java.utils.OfertaJuego;

public class GamePreviewController {

	@FXML
	private Label gameTitleLbl,tagTitleLbl,descTitleLbl,descLbl,priceLbl;
	@FXML
	private HBox tagContainer;
	@FXML
	private ImageView gameImg;
	@FXML
	private ListView<Pane> offersList;
	@FXML
	private Button g2aBtn;
	@FXML
	private Button instantBtn;
	@FXML
	private Button enebaBtn;
	@FXML
	private Button webBtn;
	@FXML
	private Label notificationLbl;
	
	private String steamURL="";

	ObservableList<Pane> itemsG2A =FXCollections.observableArrayList();
	ObservableList<Pane> itemsInstant =FXCollections.observableArrayList();
	ObservableList<Pane> itemsEneba =FXCollections.observableArrayList();

	int[] nOfertas=new int[] {0,0,0};
	
	private void inicializar() {
		// TODO Auto-generated method stub
		
		if(!steamURL.equals("")) {
			
			Map<String,String> gameData=Buscador.getInfoJuego(steamURL);//Conseguir los datos para mostrar informacion del juego
			
			gameTitleLbl.setText(gameData.get("title"));
			descLbl.setText(gameData.get("desc"));
			gameImg.setImage(new Image(gameData.get("img")));
			priceLbl.setText(gameData.get("price"));
			
			String[] genreTags=gameData.get("genre").split(",");
			for(int i=0;i<genreTags.length;i++) {
				Label newTag = new Label(genreTags[i]);
				tagContainer.getChildren().add(newTag);
			}
			
			Map<String, OfertaJuego[]> dataDiscount=null;
			try {//Conseguir las ofertas a mostrar
				dataDiscount=Buscador.getOfertas(gameData.get("title"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//mostrar las vistas	
			int i=0;
			for (Entry<String, OfertaJuego[]> oferta : dataDiscount.entrySet()) {//dataDiscount.keySet().toArray()[i]

				
				if(i<dataDiscount.size()) {
					
					if(oferta.getKey()=="g2a") {
						for(int j=0;j<dataDiscount.get(dataDiscount.keySet().toArray()[i]).length;j++) {
							//System.out.println(dataDiscount.keySet().toArray()[i]);
							//System.out.println(dataDiscount.get(dataDiscount.keySet().toArray()[i])[j].getTitulo());
							//System.out.println(oferta.getValue()[j].getUrl());
							itemsG2A.add(setOffersList(oferta.getValue()[j].getTitulo(),oferta.getValue()[j].getPrecio(),oferta.getValue()[j].getDescuento(),
						    		oferta.getValue()[j].getUrl()));
							nOfertas[0]++;
						}
					}if(oferta.getKey()=="instant") {
						for(int j=0;j<dataDiscount.get(dataDiscount.keySet().toArray()[i]).length;j++) {
							itemsInstant.add(setOffersList(oferta.getValue()[j].getTitulo(),oferta.getValue()[j].getPrecio(),oferta.getValue()[j].getDescuento(),
						    		oferta.getValue()[j].getUrl()));
							nOfertas[1]++;
						}
					}if(oferta.getKey()=="eneba") {
						for(int j=0;j<dataDiscount.get(dataDiscount.keySet().toArray()[i]).length;j++) {
							itemsEneba.add(setOffersList(oferta.getValue()[j].getTitulo(),oferta.getValue()[j].getPrecio(),oferta.getValue()[j].getDescuento(),
						    		oferta.getValue()[j].getUrl()));
							nOfertas[2]++;
						}
					}
				}else {
					
				}

			    i++;
			}
			offersList.setItems(itemsG2A);
			g2aBtn.setDisable(true);
			notificationLbl.setText(String.valueOf(nOfertas[0]));
		}
		
		g2aBtn.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				changeList(0);
			}
			
		});
		instantBtn.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				changeList(1);
			}
			
		});
		enebaBtn.setOnAction(new EventHandler <ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				changeList(2);
			}
			
		});
	}
	
	private Pane setOffersList(String titulo, String precio, String porcentage, String enlace) {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../../resources/views/OfferCard_view.fxml"));
			Pane newPane = loader.load();
			OfferCardController controller = loader.getController();
			controller.setData(titulo, precio, porcentage, enlace);
			
			newPane.setMaxWidth(Double.MAX_VALUE);
			
			newPane.setOnMouseClicked(new EventHandler <MouseEvent>() {
				
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
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
	  
	  	steamURL=(String) (stage.getUserData());
	  

	  	inicializar();
	}
	
	private void changeList(int Btn) {//0 G2a -- 1 Instant -- 2 Eneba
		
		if(Btn==0) {
			g2aBtn.setDisable(true);
			instantBtn.setDisable(false);
			enebaBtn.setDisable(false);
			
			offersList.setItems(itemsG2A);
			
			changeNOfertas(0);
			
			webBtn.setOnAction(new EventHandler <ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					try {
					    Desktop.getDesktop().browse(new URL("https://www.g2a.com/es/").toURI());
					} catch (IOException e) {
					    e.printStackTrace();
					} catch (URISyntaxException e) {
					    e.printStackTrace();
					}
				}
			});
		}else if(Btn==1) {
			g2aBtn.setDisable(false);
			instantBtn.setDisable(true);
			enebaBtn.setDisable(false);
			
			offersList.setItems(itemsInstant);

			changeNOfertas(1);
			
			webBtn.setOnAction(new EventHandler <ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					try {
					    Desktop.getDesktop().browse(new URL("https://www.instant-gaming.com/es/").toURI());
					} catch (IOException e) {
					    e.printStackTrace();
					} catch (URISyntaxException e) {
					    e.printStackTrace();
					}
				}
			});
		}else if(Btn==2) {
			g2aBtn.setDisable(false);
			instantBtn.setDisable(false);
			enebaBtn.setDisable(true);
			
			offersList.setItems(itemsEneba);

			changeNOfertas(2);
			
			webBtn.setOnAction(new EventHandler <ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					try {
					    Desktop.getDesktop().browse(new URL("https://www.eneba.com/es/").toURI());
					} catch (IOException e) {
					    e.printStackTrace();
					} catch (URISyntaxException e) {
					    e.printStackTrace();
					}
				}
			});
		}
	}
	
	private void changeNOfertas(int Btn) {
		
		notificationLbl.setText(String.valueOf(nOfertas[Btn]));
	}
}
