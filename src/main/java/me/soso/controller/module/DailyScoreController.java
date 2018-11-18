package me.soso.controller.module;

import com.avos.avoscloud.AVException;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.FXMLController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.List;

import me.soso.model.config.DailyScore;
import me.soso.utils.DateUtils;

@FXMLController(value = "/fxml/module/DailyScore.fxml", title = "每日评分表")
public class DailyScoreController {
    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTextField exerciseTextField;

    @FXML
    private JFXTextField workTextField;

    @FXML
    private JFXTextField studyTextField;

    @FXML
    private JFXTextField readNewsTextField;

    @FXML
    private JFXTextField lolTextField;

    @FXML
    private JFXTextField sleepTextField;

    @FXML
    private JFXTextField dietTextField;

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
        try {
            DailyScore dailyScore = new DailyScore();
            dailyScore.setDate(DateUtils.asDate(datePicker.getValue()));
            dailyScore.setExercise(Integer.valueOf(exerciseTextField.getText()));
            dailyScore.setWork(Integer.valueOf(workTextField.getText()));
            dailyScore.setStudy(Integer.valueOf(studyTextField.getText()));
            dailyScore.setReadNews(Integer.valueOf(readNewsTextField.getText()));
            dailyScore.setLol(Integer.valueOf(lolTextField.getText()));
            dailyScore.setSleep(Integer.valueOf(sleepTextField.getText()));
            dailyScore.setDiet(Integer.valueOf(dietTextField.getText()));

            System.out.println(dailyScore.toString());
//            dailyScore.save();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

}
