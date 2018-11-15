package me.soso.controller.module;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;

@FXMLController(value = "/fxml/module/DailyQOL.fxml", title = "每日评分表")
public class DailyQOLController {
    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTextField exerciseTextField;

    @FXML
    private JFXTextField workTextField;

    @FXML
    private JFXTextField learnTextField;

    @FXML
    private JFXTextField readNewsTextField;

    @FXML
    private JFXTextField LOLTextField;

    @FXML
    private JFXTextField sleepTextField;

    @FXML
    private JFXTextField eatTextField;

    public void submit() {

    }

}
