package cn.zjnu.matcha.fragments.advisory;

import android.support.annotation.Nullable;

import cn.zjnu.matcha.factory.model.advisory.SpecialistModel;

/**
 * Created by Hu on 2017/11/7.
 */

public interface IAdvisoryTrigger {
    void triggerView(@Nullable SpecialistModel model);
}
