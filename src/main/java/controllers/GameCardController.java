package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameCardController {

	@FXML
	private Label nameLbl, dateLbl;
	@FXML
	private ImageView gameImg;
	
	protected void setData(String name, String date,String imageSource) {
		nameLbl.setText(name);
		dateLbl.setText(date);
        
		gameImg.setImage(new Image(imageSource));
	}
	
	
}
