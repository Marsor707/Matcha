package cn.zjnu.matcha.factory.model.advisory;

/**
 * Created by Hu on 2017/11/7.
 */

public class SpecialistModel {
    private String mExpertId;
    private String mExpertName;
    private String mArea;
    private String mPicture;

    public SpecialistModel(String mExpertId, String mExpertName, String mArea, String mPicture) {
        this.mExpertId = mExpertId;
        this.mExpertName = mExpertName;
        this.mArea = mArea;
        this.mPicture = mPicture;
    }

    public String getExpertId() {
        return mExpertId;
    }

    public String getExpertName() {
        return mExpertName;
    }

    public String getArea() {
        return mArea;
    }

    public String getPicture() {
        return mPicture;
    }

    public static final class Builder {
        private String expertId;
        private String expertName;
        private String area;
        private String picture;

        public Builder setExpertId(String expertId) {
            this.expertId = expertId;
            return this;
        }

        public Builder setExpertName(String expertName) {
            this.expertName = expertName;
            return this;
        }

        public Builder setArea(String area) {
            this.area = area;
            return this;
        }

        public Builder setPicture(String picture) {
            this.picture = picture;
            return this;
        }

        public SpecialistModel builder() {
            return new SpecialistModel(expertId, expertName, area, picture);
        }
    }
}
