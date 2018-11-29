package me.soso.controller.module;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import me.soso.controller.base.DialogController;
import me.soso.controller.base.MainController;
import me.soso.model.config.DailyScore;
import me.soso.model.config.DailyScoreTreeObject;
import me.soso.utils.DateUtils;

@FXMLController(value = "/fxml/module/DailyScore.fxml", title = "每日评分表")
public class DailyScoreController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private JFXTreeTableView<DailyScoreTreeObject> treeTableView;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, String> dateColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> exerciseColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> workColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> studyColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> readNewsColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> lolColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> sleepColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> dietColumn;
    @FXML
    private JFXTreeTableColumn<DailyScoreTreeObject, Integer> totalColumn;

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

    private DialogController dialog;

    @PostConstruct
    public void init() {
        for (JFXTextField textField : textFieldList) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textChanged();
            });
        }

        AVObject.registerSubclass(DailyScore.class);

        dialog = (DialogController) context.getRegisteredObject(MainController.DIALOG);

        initTableView();
    }

    private void textChanged() {
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

    private void initTableView() {
        setupCellValueFactory(dateColumn, DailyScoreTreeObject::dateProperty);
        setupCellValueFactory(exerciseColumn, d -> d.exerciseProperty().asObject());
        setupCellValueFactory(workColumn, d -> d.workProperty().asObject());
        setupCellValueFactory(studyColumn, d -> d.studyProperty().asObject());
        setupCellValueFactory(readNewsColumn, d -> d.readNewsProperty().asObject());
        setupCellValueFactory(lolColumn, d -> d.lolProperty().asObject());
        setupCellValueFactory(sleepColumn, d -> d.sleepProperty().asObject());
        setupCellValueFactory(dietColumn, d -> d.dietProperty().asObject());
        setupCellValueFactory(totalColumn, d -> d.totalProperty().asObject());

        final ObservableList<DailyScoreTreeObject> list = FXCollections.observableArrayList();

        try {
            AVQuery<DailyScore> query = new AVQuery<>("DailyScore");
            query.orderByDescending(DailyScore.DATE);
            List<DailyScore> dailyScores = query.find();
            for (DailyScore dailyScore: dailyScores) {
                DailyScoreTreeObject treeObject = new DailyScoreTreeObject(dailyScore);
                list.add(treeObject);
            }
        } catch (AVException e) {
            e.printStackTrace();
        }

        treeTableView.setRoot(new RecursiveTreeItem<>(list, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<DailyScoreTreeObject, T> column,
                                           Function<DailyScoreTreeObject, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<DailyScoreTreeObject, T> param) -> {
            if (column.validateValue(param)) {
                return mapper.apply(param.getValue().getValue());
            } else {
                return column.getComputedValue(param);
            }
        });
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
            dialog.showDialog("成功", dailyScore.toString());
        } catch (NumberFormatException e) {
            dialog.showDialog("错误", e.getMessage());
            e.printStackTrace();
        } catch (AVException e) {
            dialog.showDialog("错误", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            dialog.showDialog("错误", e.getMessage());
            e.printStackTrace();
        }
    }

}
