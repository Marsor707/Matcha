package cn.zjnu.matcha.factory.model.pe;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class PEDataModel {

    private String name;
    private String mark;
    private float score;

    public PEDataModel(String name, String mark, float score) {
        this.name = name;
        this.mark = mark;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
