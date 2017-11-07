package cn.zjnu.matcha.factory.model.advisory;

import java.util.List;

/**
 * Created by Hu on 2017/11/7.
 */

public class SpecialistModel {
    private String name;
    private String skill;
    private List<LeaveMessageModel> messageModels;

    public List<LeaveMessageModel> getMessageModels() {
        return messageModels;
    }

    public void setMessageModels(List<LeaveMessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
