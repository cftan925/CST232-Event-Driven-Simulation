package application;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application{
	public static int partitionChoice;
	public static int algoOption;
	public static int memoryBlock;
	private Stage primaryStage;
	private static BorderPane mainLayout;

	public static void main(String[] args) {
		ReadFile.ReadJobFile();
		ReadFile.ReadMemoryFile();
		launch(args);
	}

	/*
	@Override
	public void start(Stage stage) throws Exception {
		stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("RunEvent.fxml"))));
		stage.show();
	}
	*/
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Event-Driven Simulation");
			showMainView();
			showPartitionMenu();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);	
		primaryStage.show();
	}
	
	public static void showPartitionMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("PartitionMenu.fxml"));
		BorderPane partitionMenu = loader.load();
		mainLayout.setCenter(partitionMenu);	
	}
	
	public static void showFixedMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Fixed.fxml"));
		BorderPane fixed = loader.load();
		mainLayout.setCenter(fixed);
	}
	
	public static void showDynamicMenu() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Dynamic.fxml"));
		BorderPane dynamic = loader.load();
		mainLayout.setCenter(dynamic);
	}
	
	public static void showDynamicMemory() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("DynamicMemory.fxml"));
		BorderPane dynamicMemory = loader.load();
		mainLayout.setCenter(dynamicMemory);
	}
	
	public static void showFixedMemory() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("FixedMemory.fxml"));
		BorderPane fixedMemory = loader.load();
		mainLayout.setCenter(fixedMemory);
	}

	public static void runEvent() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("RunEvent.fxml"));
		BorderPane runEvent = loader.load();
		mainLayout.setCenter(runEvent);
	}	

}