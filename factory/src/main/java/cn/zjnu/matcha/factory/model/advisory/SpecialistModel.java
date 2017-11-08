package cn.zjnu.matcha.factory.model.advisory;

/**
 * Created by Hu on 2017/11/7.
 */

public class SpecialistModel {
    private String expertName;
    private String area;
    private String picture;

    public String getExpertName() {
        return expertName;
    }

    public SpecialistModel setExpertName(String expertName) {
        this.expertName = expertName;
        return this;
    }

    public String getArea() {
        return area;
    }

    public SpecialistModel setArea(String area) {
        this.area = area;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public SpecialistModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

}
