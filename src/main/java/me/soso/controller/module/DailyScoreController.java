package me.soso.controller.module;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
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

    @FXML
    private JFXDialog dialog;

    @FXML
    private Label dialogTitleLabel;

    @FXML
    private Label dialogMsgLabel;

    @PostConstruct
    public void init() {
        for (JFXTextField textField : textFieldList) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textChanged();
            });
        }

        AVObject.registerSubclass(DailyScore.class);
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

            dailyScore.save();
            showDialog("成功", dailyScore.toString());
        } catch (NumberFormatException e) {
            showDialog("错误", e.getMessage());
            e.printStackTrace();
        } catch (AVException e) {
            showDialog("错误", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showDialog("错误", e.getMessage());
            e.printStackTrace();
        }
    }

    private void showDialog(String title, String msg) {
        dialogTitleLabel.setText(title);
        dialogMsgLabel.setText(msg);
        dialog.show();
    }

}
