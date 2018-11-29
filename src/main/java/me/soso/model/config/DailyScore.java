package me.soso.model.config;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import me.soso.utils.DateUtils;

import java.util.Date;

@AVClassName(value = "DailyScore")
public class DailyScore extends AVObject {
    public static final Creator CREATOR = AVObjectCreator.instance;

    public static final String DATE = "date";
    public static final String EXERCISE = "exercise";
    public static final String WORK = "work";
    public static final String STUDY = "study";
    public static final String READ_NEWS = "readNews";
    public static final String LOL = "lol";
    public static final String SLEEP = "sleep";
    public static final String DIET = "diet";

    public DailyScore() {

    }

    public Date getDate() {
        return getDate(DATE);
    }

    public void setDate(Date date) {
        put(DATE, date);
    }

    public int getExercise() {
        return getInt(EXERCISE);
    }

    public void setExercise(int exercise) {
        put(EXERCISE, exercise);
    }

    public int getWork() {
        return getInt(WORK);
    }

    public void setWork(int work) {
        put(WORK, work);
    }

    public int getStudy() {
        return getInt(STUDY);
    }

    public void setStudy(int study) {
        put(STUDY, study);
    }

    public int getReadNews() {
        return getInt(READ_NEWS);
    }

    public void setReadNews(int readNews) {
        put(READ_NEWS, readNews);
    }

    public int getLol() {
        return getInt(LOL);
    }

    public void setLol(int lol) {
        put(LOL, lol);
    }

    public int getSleep() {
        return getInt(SLEEP);
    }

    public void setSleep(int sleep) {
        put(SLEEP, sleep);
    }

    public int getDiet() {
        return getInt(DIET);
    }

    public void setDiet(int diet) {
        put(DIET, diet);
    }

    public int getTotalScore() {
        return getExercise() + getWork() + getStudy() + getReadNews() + getLol() + getSleep() + getDiet();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n{日期：" + DateUtils.dateFormat(getDate()));
        stringBuilder.append("\n运动：" + getExercise());
        stringBuilder.append("\n工作：" + getWork());
        stringBuilder.append("\n学习：" + getStudy());
        stringBuilder.append("\n浏览新闻：" + getReadNews());
        stringBuilder.append("\nLOL：" + getLol());
        stringBuilder.append("\n早睡：" + getSleep());
        stringBuilder.append("\n饮食规律：" + getDiet());
        stringBuilder.append("\n总分：" + getTotalScore());
        stringBuilder.append("\n}");
        return stringBuilder.toString();
    }
}
