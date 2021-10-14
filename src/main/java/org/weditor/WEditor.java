package org.weditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class WEditor extends Application {
    private Set<Class<?>> handlers = new HashSet<Class<?>>();
    private Object mutex = new Object();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        var localeString = getParameters().getNamed().get("locale");
        if (localeString != null) {
            // FIXME: A locale was specified on the command-line. What are you going to do about it? ;-)
        }
        // Display Main Editor
        this.display(primaryStage);
    }

    public void display(Stage stage) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
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
        Button findBtn = new Button("Find"); // Will be replaced by Find Plugin

        // ToolBar
        ToolBar toolBar = new ToolBar();
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
                synchronized (mutex) {
                    for (Class<?> handler : handlers) {
                        Object pluginObj = null;
                        try {
                            pluginObj = handler.getDeclaredConstructor().newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                        try {
                            Method onLoadMethod = handler.getDeclaredMethod("onLoad");
                            String control = (String) onLoadMethod.invoke(pluginObj);
                            String[] elem = control.split("\\.");
                            Button pluginBtn = new Button(elem[0]);
                            toolBar.getItems().add(pluginBtn);
                            Object finalPluginObj = pluginObj;
                            pluginBtn.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    try {
                                        Method pluginBtnClick = handler.getDeclaredMethod(elem[1]);
                                        pluginBtnClick.invoke(finalPluginObj);
                                    } catch (NoSuchMethodException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        };
        Class<?> pluginClass = Class.forName("textEditor.DatePlugin");
        handlers.add(pluginClass);

        // Save Menu event handler
        menuItem5.setOnAction(action);


    }
}
