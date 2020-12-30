package main;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			URL fxmlURL=getClass().getResource("../application/application.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((AnchorPane) root, 750, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Boutique de Noël");
			primaryStage.getIcons().add(new Image("file:lib/noel.png"));
			primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace(); 
		}
	}
		
	public static void main(String[] args) {
		launch(args);
		}
}