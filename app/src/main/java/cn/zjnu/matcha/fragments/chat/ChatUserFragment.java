package cn.zjnu.matcha.fragments.chat;

import com.bumptech.glide.request.RequestOptions;

import cn.zjnu.matcha.R;

/**
 * Created by fsh on 2017/10/17.
 */

public class ChatUserFragment {

    private RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .dontAnimate();


    protected int getHeaderLayoutId() {
        return R.layout.fragment_chat_common;
    }


}
