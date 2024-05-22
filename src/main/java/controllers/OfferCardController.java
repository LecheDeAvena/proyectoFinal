package main.java.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OfferCardController {//Controlador para visualizar cada oferta en una lista

	private String urlPage;
	@FXML
	private Label nameLbl, regionLbl,percentLabel;
	@FXML
	private Button priceBtn;
	
	protected void setData(String name, String price, String percent, String url) {
		nameLbl.setText(name);
		percentLabel.setText(percent);
		priceBtn.setText(price);
		urlPage=url;
	}
	
	@FXML
	void abrirLink() {
		try {
		    Desktop.getDesktop().browse(new URL(urlPage).toURI());
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (URISyntaxException e) {
		    e.printStackTrace();
		}
	}
}
