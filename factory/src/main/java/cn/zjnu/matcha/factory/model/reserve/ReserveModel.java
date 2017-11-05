package cn.zjnu.matcha.factory.model.reserve;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */

public class ReserveModel {

    private int id;
    private String name;
    private String numberNow;
    private String numberTotal;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberNow() {
        return numberNow;
    }

    public void setNumberNow(String numberNow) {
        this.numberNow = numberNow;
    }

    public String getNumberTotal() {
        return numberTotal;
    }

    public void setNumberTotal(String numberTotal) {
        this.numberTotal = numberTotal;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
