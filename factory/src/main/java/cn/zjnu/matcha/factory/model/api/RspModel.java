package cn.zjnu.matcha.factory.model.api;

/**
 * 0	Success	成功
 * 871101	Invalid request parameters.	请求参数不合法
 * 871303	Invalid username.	用户名不合法
 * 871304	Invalid password.	密码不合法
 * 871305	Invalid name.	名称不合法（包括nickname groupname notename）
 * 871306	Invalid input.	其他输入不合法
 * 871311	User avatar not specified. download avatar failed.	用户未设定头像，下载头像失败
 * 871317	Target user cannot be yourself.	操作目标用户不能是自己
 * <p>
 * 898001	User exist	用户已存在
 * 898002	No such user	用户不存在
 * 898003	Parameter invalid!	请求参数不合法
 * 898004	Password error	更新密码操作，用户密码错误
 * 898006	Group id invalid	Group id不存在
 * 899011	Repeat to add the members	重复添加群成员
 * 899047	duplicate add group	已经设置此群组为消息免打扰，重复设置错误
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class RspModel<T> {
    public static final int SUCCEED = 0;
    public static final int ERROR_UNKNOWN = 1;

    public static final int INVALID_REQUEST_PARAMETERS = 871101;
    public static final int INVALID_USERNAME = 871303;
    public static final int INVALID_PASSWORD = 871304;
    public static final int INVALID_NAME = 871305;
    public static final int INVALID_INPUT = 871306;
    public static final int USER_AVATAR_NOT_SPECIFIED = 871311;
    public static final int TARGET_USER_CANNOT_BE_YOURSELF = 871317;

    public static final int USER_EXIST = 898001;
    public static final int NO_SUCH_USER = 898002;
    public static final int PARAMETER_INVALID = 898003;
    public static final int PASSWORD_ERROR = 898004;
    public static final int GROUP_ID_INVALID = 898006;
    public static final int REPEAT_TO_ADD_THE_MEMBERS = 898011;
    public static final int DUPLICATE_ADD_GROUP = 898047;

    private int code;

    public int getCode() {
        return code;
    }

}