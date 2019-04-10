package com.reversi.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application {


//   private Stage stage;

    public void rmain(String[] args){
        launch(args);
    }

    public void start(Stage primaryStage){

//      this.stage = primaryStage;

        Scene scene = new Scene(new logIn().getView(), 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

 //   public void removeStage(){
//        stage.hide();
//    }

}



