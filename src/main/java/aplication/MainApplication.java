package main.java.aplication;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApplication extends Application {

    private Stage primeraPantalla;
    
    public MainApplication() {
    }

   	@Override
    public void start(Stage primeraPantalla) {
        this.primeraPantalla = primeraPantalla;
        this.primeraPantalla.setTitle("pruebas");

        initRootLayout();
    }

    /**
     * Este método lanza la pantalla
     */
    public void initRootLayout() {
        try {
        	
        	FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("../../resources/views/Login_view.fxml"));
            //System.out.println(MainApp.class.getResource("../../resources/view/login.fxml"));
            Scene scene = new Scene(loader.load());
            primeraPantalla.setScene(scene);
            primeraPantalla.show();
            
        } catch (IOException e) {
        	System.out.print("ijosdgzdgsijosdgijosdgrioj");
            e.printStackTrace();
        }
    }

    /**
     * Devuelve la primera pantalla
     * @return
     */
   /* public Stage getPrimaryStage() {
        return primeraPantalla;
    }*/

    public static void main(String[] args) {
    	
        launch(args);
    }
}