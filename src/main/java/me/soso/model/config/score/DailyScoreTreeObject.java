package me.soso.model.config.score;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import me.soso.utils.DateUtils;

public class DailyScoreTreeObject extends RecursiveTreeObject<DailyScoreTreeObject> {

    private final StringProperty date;
    private final IntegerProperty exercise;
    private final IntegerProperty work;
    private final IntegerProperty study;
    private final IntegerProperty readNews;
    private final IntegerProperty lol;
    private final IntegerProperty sleep;
    private final IntegerProperty diet;
    private final IntegerProperty total;

    public DailyScoreTreeObject(DailyScore dailyScore) {
        this.date = new SimpleStringProperty(DateUtils.dateFormat(dailyScore.getDate()));
        this.exercise = new SimpleIntegerProperty(dailyScore.getExercise());
        this.work = new SimpleIntegerProperty(dailyScore.getWork());
        this.study = new SimpleIntegerProperty(dailyScore.getStudy());
        this.readNews = new SimpleIntegerProperty(dailyScore.getReadNews());
        this.lol = new SimpleIntegerProperty(dailyScore.getLol());
        this.sleep = new SimpleIntegerProperty(dailyScore.getSleep());
        this.diet = new SimpleIntegerProperty(dailyScore.getDiet());
        this.total = new SimpleIntegerProperty(dailyScore.getTotalScore());
    }

    public StringProperty dateProperty() {
        return date;
    }

    public IntegerProperty exerciseProperty() {
        return exercise;
    }

    public IntegerProperty workProperty() {
        return work;
    }

    public IntegerProperty studyProperty() {
        return study;
    }

    public IntegerProperty readNewsProperty() {
        return readNews;
    }

    public IntegerProperty lolProperty() {
        return lol;
    }

    public IntegerProperty sleepProperty() {
        return sleep;
    }

    public IntegerProperty dietProperty() {
        return diet;
    }

    public IntegerProperty totalProperty() {
        return total;
    }
}
