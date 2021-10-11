package org.weditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
git import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class WEditor extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var localeString = getParameters().getNamed().get("locale");
        if(localeString != null)
        {
            // FIXME: A locale was specified on the command-line. What are you going to do about it? ;-)
        }
        // Display Main Editor
        this.display(primaryStage);
    }
    public void  display(Stage stage){
        // Window Properties
        stage.setTitle("W-Editor");
        stage.setMinWidth(1000);
        stage.setMinHeight(800);

        // Menus
        // File Menu
        Menu menu1 = new Menu("File");
        MenuItem menuItem1 = new MenuItem("Save");
        MenuItem menuItem2 = new MenuItem("Load");

        menu1.getItems().add(menuItem1);
        menu1.getItems().add(menuItem2);

        // Options menu
        Menu menu2 = new Menu("Options");
        MenuItem menuItem3 = new MenuItem("Language");
        MenuItem menuItem4 = new MenuItem("Keyboard Shortcuts");
        MenuItem menuItem5 = new MenuItem("Plugins");
        MenuItem menuItem6 = new MenuItem("Scripts");

        menu2.getItems().add(menuItem3);
        menu2.getItems().add(menuItem4);
        menu2.getItems().add(menuItem5);
        menu2.getItems().add(menuItem6);

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);


        // Buttons
        Button dateBtn = new Button("Date"); // Will be replaced by Date Plugin
        Button findBtn = new Button("Find"); // Will be replaced by Find Plugin

        // ToolBar
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(dateBtn);
        toolBar.getItems().add(findBtn);


        // Text Area
        TextArea textArea = new TextArea();
        textArea.setMinHeight(800);


        // Layout Box
        VBox mainBox = new VBox(menuBar, toolBar, textArea);

        // Scene properties
        Scene scene = new Scene(mainBox);

        // Rendering
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();

        // Event Handler
        EventHandler<ActionEvent> action = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Clicked :)");
            }
        };


        // Save Menu event handler
        menuItem1.setOnAction(action);


    }
}
