package cn.zjnu.matcha.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by marsor on 2017/9/16.
 */

public enum EcIcons implements Icon {
    icon_dynamic('\ue634'),
    icon_chat('\ue605'),
    icon_function('\ue617'),
    icon_read('\ue65b'),
    icon_personal('\ue616');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
