package me.soso.model.config.score;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import me.soso.utils.DateUtils;

import java.util.*;

public class DailyScoreManager {
    private static DailyScoreManager manager = new DailyScoreManager();
    private DailyScoreManager() {
        this.getDataFromLeanCloud();
    }
    public static DailyScoreManager getSharedManager() {
        return manager;
    }

    /** 是否已经初始化完成数据 */
    private boolean isInit = false;
    /** 全部日常评分数据 */
    private List<DailyScore> dailyScores;
    /** 存储日期yyyy-MM-dd与日常评分的Map，用于添加数据时校验日期重复数据 */
    private Map<Date, DailyScore> dailyScoreMap;
    /** 当前周的日常评分数据 */
    private List<DailyScore> currentWeekDailyScores;
    /** 当前月的日常评分数据 */
    private List<DailyScore> currentMonthDailyScores;

    private void getDataFromLeanCloud() {
        if (!isInit) {
            synchronized(DailyScoreManager.class) {
                if (!isInit) {
                    try {
                        dailyScoreMap = new HashMap<>();
                        currentWeekDailyScores = new ArrayList<>();
                        currentMonthDailyScores = new ArrayList<>();

                        AVObject.registerSubclass(DailyScore.class);
                        AVQuery<DailyScore> query = new AVQuery<>("DailyScore");
                        query.orderByDescending(DailyScore.DATE);

                        dailyScores = query.find();

                        Date today = new Date();
                        for (DailyScore dailyScore: dailyScores) {
                            Date date = dailyScore.getDate();

                            dailyScoreMap.put(date, dailyScore);

                            if (DateUtils.isSameWeek(today, date)) {
                                currentWeekDailyScores.add(dailyScore);
                            }

                            if (DateUtils.isSameMonth(today, date)) {
                                currentMonthDailyScores.add(dailyScore);
                            }
                        }

                        isInit = true;
                    } catch (AVException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public boolean add(DailyScore dailyScore) {
        Date date = dailyScore.getDate();
        if (dailyScoreMap.containsKey(date)) {
            return false;
        } else {
            try {
                dailyScore.save();
            } catch (AVException e) {
                e.printStackTrace();
                return false;
            }

            dailyScoreMap.put(date, dailyScore);

            Date today = new Date();
            if (DateUtils.isSameWeek(today, date)) {
                currentWeekDailyScores.add(dailyScore);
            }

            if (DateUtils.isSameMonth(today, date)) {
                currentMonthDailyScores.add(dailyScore);
            }

            return true;
        }

    }

    public int getCurWeekDays() {
        return currentWeekDailyScores.size();
    }

    public int getCurWeekTotalScore() {
        int totalScore = 0;
        for (DailyScore dailyScore : currentWeekDailyScores) {
            totalScore += dailyScore.getTotalScore();
        }
        return totalScore;
    }

    public float getCurWeekAvgScore() {
        int days = this.getCurWeekDays();
        if (days == 0) {
            return 0;
        }
        return this.getCurWeekTotalScore() / (float)days;
    }

    public int getCurMonthDays() {
        return currentMonthDailyScores.size();
    }

    public int getCurMonthTotalScore() {
        int totalScore = 0;
        for (DailyScore dailyScore : currentMonthDailyScores) {
            totalScore += dailyScore.getTotalScore();
        }
        return totalScore;
    }

    public float getCurMonthAvgScore() {
        int days = this.getCurMonthDays();
        if (days == 0) {
            return 0;
        }
        return this.getCurMonthTotalScore() / (float)days;
    }

    public List<DailyScore> getDailyScores() {
        return dailyScores;
    }

    public List<DailyScore> getCurrentWeekDailyScores() {
        return currentWeekDailyScores;
    }

    public List<DailyScore> getCurrentMonthDailyScores() {
        return currentMonthDailyScores;
    }
}
