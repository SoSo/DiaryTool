package me.soso.controller.module;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.List;

@FXMLController(value = "/fxml/module/DailyScore.fxml", title = "每日评分表")
public class DailyScoreController {
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

    @FXML
    private Label totalLabel;

    @FXML
    private List<JFXTextField> textFieldList;

    @PostConstruct
    public void init() {
        for (JFXTextField textField : textFieldList) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textChanged();
            });
        }
    }

    public void textChanged() {
        int totalScore = 0;
        for (JFXTextField textField : textFieldList) {
            String text = textField.getText();
            try {
                if (text != null && text.length() > 0 && !"-".equals(text)) {
                    totalScore += Integer.valueOf(text);
                }
            } catch (NumberFormatException e) {
                System.out.println(textField.getPromptText() + " 格式转换错误");
            }
        }
        totalLabel.setText(String.valueOf(totalScore));
    }

    public void submit() {


    }

}
