package com.stoldo.m120_rcas_projektarbeit;

import com.stoldo.m120_rcas_projektarbeit.gui.pages.race_cars_overview.TestController;
import com.stoldo.m120_rcas_projektarbeit.model.javafx.AbstractController;
import com.stoldo.m120_rcas_projektarbeit.shared.constants.ResourceKey;
import com.stoldo.m120_rcas_projektarbeit.shared.util.JavaFxUtils;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RCASApplicationStarter extends Application {
	
	
	
	public static void main(String[] args) {
		Application.launch(RCASApplicationStarter.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		AbstractController mainController = new TestController();
		mainController.setStage(primaryStage);
		mainController.load();
		
		Pane mainPane = mainController.getPane();
		
		Scene mainScene = new Scene(mainPane, 800, 600);
		primaryStage.centerOnScreen();
		primaryStage.setTitle(JavaFxUtils.getResourceBundle().getString(ResourceKey.APPLICATION_TITLE.getKey()));
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
}
