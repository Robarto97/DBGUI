package com.ru.mag.db.jdbc.gui;

import com.ru.mag.db.jdbc.util.DBUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

    @FXML private TextField textPosition;

    public void showWorkers(ActionEvent event){
        try {
            //showError("ERROR!"); //TODO: Check for the input and show error!
            //TODO: Show the same error in a self-built dialog!
            String wantedPosition = textPosition.getText();
            ResultSet workersForPosition = DBUtil.getInstance().getWorkersForPosition(wantedPosition);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TableDialog.fxml"));
            Parent tableParent = (Parent) fxmlLoader.load();

            //get controller and transfer data!
            TableController tblcontroller = fxmlLoader.getController();
            try {
                tblcontroller.setTableResultset(workersForPosition);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Workers list");
            stage.setScene(new Scene(tableParent));
            stage.show();

            // Hide this current window (if this is what you want)
            //((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Text is: " + textPosition.getText());
        alert.showAndWait();
    }

}
