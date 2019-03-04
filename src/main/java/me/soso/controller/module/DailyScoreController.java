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
import javafx.scene.control.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

import me.soso.controller.base.DialogController;
import me.soso.controller.base.MainController;
import me.soso.model.config.score.DailyScore;
import me.soso.model.config.score.DailyScoreManager;
import me.soso.model.config.score.DailyScoreTreeObject;
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
    private Label weekStatisticsLabel;
    @FXML
    private Label monthStatisticsLabel;
    @FXML
    private List<JFXTextField> textFieldList;

    private DialogController dialog;

    private final ObservableList<DailyScoreTreeObject> treeObjectList = FXCollections.observableArrayList();

    private DailyScoreManager dailyScoreManager = DailyScoreManager.getSharedManager();

    @PostConstruct
    public void init() {
        for (JFXTextField textField : textFieldList) {
            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textChanged();
            });
        }

        dialog = (DialogController) context.getRegisteredObject(MainController.DIALOG);

        this.initTableView();
        this.statistics();
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

        for (DailyScore dailyScore: dailyScoreManager.getDailyScores()) {
            DailyScoreTreeObject treeObject = new DailyScoreTreeObject(dailyScore);
            treeObjectList.add(treeObject);
        }

        treeTableView.setRoot(new RecursiveTreeItem<>(treeObjectList, RecursiveTreeObject::getChildren));
        treeTableView.setShowRoot(false);
        dateColumn.setSortType(TreeTableColumn.SortType.DESCENDING);
        treeTableView.getSortOrder().add(dateColumn);
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

    public void statistics() {
        String weekStatisticsText = "本周已统计" + dailyScoreManager.getCurWeekDays() + "天，总共"
                + dailyScoreManager.getCurWeekTotalScore() + "分，日均" + dailyScoreManager.getCurWeekAvgScore() + "分";
        weekStatisticsLabel.setText(weekStatisticsText);

        String monthStatisticsText = "本月已统计" + dailyScoreManager.getCurMonthDays() + "天，总共"
                + dailyScoreManager.getCurMonthTotalScore() + "分，日均" + dailyScoreManager.getCurMonthAvgScore() + "分";
        monthStatisticsLabel.setText(monthStatisticsText);
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

            if (dailyScoreManager.add(dailyScore)) {
                DailyScoreTreeObject treeObject = new DailyScoreTreeObject(dailyScore);
                treeObjectList.add(0, treeObject);
                treeTableView.sort();
                dialog.showDialog("成功", dailyScore.toString());
            } else {
                dialog.showDialog("失败", dailyScore.getDate().toString() + "日期已存在");
            }
        } catch (NumberFormatException e) {
            dialog.showDialog("错误", e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            dialog.showDialog("错误", e.getMessage());
            e.printStackTrace();
        }
    }

}
