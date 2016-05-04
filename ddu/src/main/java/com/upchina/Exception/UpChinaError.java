package com.upchina.Exception;

public enum UpChinaError {

    //通用错误
    SUCCESS("0000", "正确返回"),
    ERROR("0999", "服务器未知错误"),
    SIGN_ERROR("0001", "参数验签失败"),
    ENCRYPT_ERROR("0002", "加解密错误"),
    DATE_FORMAT_ERROR("0003", "时间格式错误"),
    JSON_FORMAT_ERROR("0004", "JSON格式错误"),

    REGISTER_SUCCESS("10000", "注册成功"),
    REGISTER_EXIST_USER("10002", "用户名已存在"),
    REGISTER_MISS_PARAM("10003", "缺失参数"),
    REGISTER_PHONENUM_ERROR("10004", "手机号码错误"),
    REGISTER_SYSTEM_ERROR("10005", "系统错误"),
    REGISTER_USER_HAVE_REGIST("10006", "用户已经认证"),
    REGISTER_VERIFYCODE_OUTTIME("10007", "验证码过期"),
    REGISTER_USER_HAVE_PHONENUM("10009", "用户无手机号码"),
    REGISTER_PHONENUM_HAVE_REGIST("10010", "手机号码已认证"),
    REGISTER_SENT_THRID_TIMES("10021,", "十五分钟已连续发送三次 "),
    REGISTER_CODE_ERROR("10023,,", "验证码错误 "),

    //输入参数错误
    USERID_NULL_ERROR("1001", "用户ID不能为空"),
    USERNAME_NULL_ERROR("1002", "用户名字不能为空"),
    PORTRAITURI_NULL_ERROR("1002", "用户头像不能为空"),
    USER_NOT_EXIST_ERROR("1003", "用户不存在"),
    FRIENDID_NULL_ERROR("1004", "好友ID不能为空"),
    GROUP_IMG_NULL_ERROR("2001", "牛圈头像不能为空"),
    GROUP_IMG_NOT_SUPPORT_ERROR("2002", "请上传jpg,jpeg,gif,png的格式图片"),
    GROUP_NAME_ERROR("2003", "牛圈名字不能为空"),
    GROUP_ID_ERROR("2004", "牛圈ID不能为空"),
    GROUP_JOIN_ERROR("2005", "不能加入牛圈"),
    GROUP_CREATE_ERROR("2006", "牛圈名字不能为空"),
    GROUP_UPDATE_ERROR("2007", "只有创建者才能更新牛圈"),
    GROUP_IS_EXIST_ERROR("2008", "牛圈名称已存在"),
    GROUP_NAME_LONG("2009", "牛圈名字不能超过10个字"),
    GROUP_INTRO_EMPTY("2010", "牛圈简介不能为空"),
    GROUP_INTRO_LONG("2011", "牛圈简介只能是20到70个字"),
    TAG_NOT_EXIST_ERROR("3001", "标签不存在"),
    SEARCH_CONDITION_IS_NULL("3002","搜索条件为空"),
    USERID_FRIENDID_IS_NULL("3003","用户ID、好友ID不能为空"),
    SEARCH_USER_IS_NULL("3004","被搜索的用户名为空"),
    CURRENT_USERID_NULL_ERROR("3005","当前用户ID为空"),
    ANSWERID_NULL_ERROR("3006","参数answerIds为空，保存答案失败"),
    
    //组合相关
    PORTFOLIOID_NULL_ERROR("4001", "组合ID不能为空"),
    PORTFOLIO_DEAL_FLAG_NULL_ERROR("4002", "买卖标志不能为空"),
    PORTFOLIO_STOCK_CODE_NULL_ERROR("4003", "股票代码不能为空"),
    PORTFOLIO_STOCK_COUNT_NULL_ERROR("4004", "股票数量不能为空"),
    PORTFOLIO_STOCK_ENTRUST_NULL_ERROR("4005", "委托价格不能为空"),
    PORTFOLIO_NOT_EXIST_ERROR("4006", "组合不存在"),
    PORTFOLIO_NOT_START_ERROR("4007", "组合还没有启动"),
    PORTFOLIO_ALREADY_END_ERROR("4008", "组合还已经结束了"),
    PORTFOLIO_TRADE_NOT_CREATOR_ERROR("4009", "组合调仓用户非组合创建者"),
    CREATE_PORTFOLIO_ERRER("4010", "参数填写不正确"),
    PORTFOLIO_TARGET_ERROR("4011", "组合目标不能是负数"),
    NO_SET_PAY_MONEY("4012", "没有设置收费金额"),
    PORTFOLIO_NAME_EXIST("4013", "组合名称已经存在"),
    PORTFOLIO_CREATE_MAX_NUM_EXIST("4014", "超过创建牛圈上限"),
    PORTFOLIO_ID_IS_NULL("4015", "组合ID为空"),
    PORTFOLIO_JYSM_IS_NULL("4016","交易码为空"),
    PORTFOLIO_WTJG_IS_NULL("4017","委托价格为空"),
    PORTFOLIO_ZQDM_IS_NULL("4018","证券代码为空"),
    PORTFOLIO_PARAMETER_IS_ERROR("4019","dicKey参数不正确"),
    PORTFOLIO_USERID_IS_ERROR("4020","当前用户ID是投顾，请用用户ID查询"),
    PORTFOLIO_USER("4021","前用户是投顾，不能添加好友"),
    ADD_USER_NOTE_PORTFOLIO("4022","即将添加为好友的用户不是投顾，不能添加为好友"),
    PORTFOLIO_IS_FRIEND("4023","即将添加为好友的投顾已经是好友，不能重复添加"),
    USERID_OR_GRIENDID_IS＿NULL("4024","userId或者friendId为空，添加好友失败"),
    USER_SUBSCRIBULE_ADVISER_MAX_NUM_ERROR("4025", "超过关注投顾上限"),
    
    
    //直播相关
    LIVE_TITLE_NULL_ERROR("5001", "请输入直播标题"),
    LIVE_SUMMARY_NULL_ERROR("5002", "请输入直播摘要"),
    ALREADY_CREATE_LIVE("5003", "当前用户已经创建过直播"),
    LIVE_NOT_EXIST_ERROR("5004", "直播不存在"),
    LIVE_PUSH_CONTENT_ERROR("5005", "只有投顾才能直播"),
    LIVE_PUSH_MESSAGE_ERROR("5006", "只有用户才能互动"),
    LIVE_ID_NULL_ERROR("5007", "请输入直播ID"),
    LIVE_TITLE_DUPLICATE_ERROR("5008", "直播室名字重复"),
    LIVE_FAVORITE_ALREADY("5009", "您今天已经赞过了"),

    //笔记相关
    NOTE_ID_IS_NULL("6001","笔记ID为空"),
    NOTE_NOT_EXIST("6002","笔记不存在"),
    NOTE_CHECK_ZAN_FAIL("6003","笔记点赞失败"),
    NOTE_NOTE_ALLOWED_ZAN("6004","不能点赞自己的笔记"),
    NOTE_ALREADY_ZAN("6005","已经赞过了"),
    NOTE_BUY_FAIL("6006","购买笔记失败"),
    NOTE_CONDITION_IS_NULL("6006","userID、payment和orderId不能为空"),
    TAGID_NULL_ERROR("6007","参数tagIds为空，保存答案失败"),
    TAGNAME_NULL_ERROR("6008","参数tagNames为空，保存答案失败"),
    
    PARAM_ERROR("3000", "参数信息错误"),

    RONGYUN_ERROR("1000", "调用融云api返回错误码"),

    STOCK_TRADE_ERROR("4000", "调用股票交易api返回空"),

    CRM_ORDER_ERROR("6000", "调用crm订单api失败"),

    TICKET_HAVA_OPENED("0005", "已刮过奖"),
    TICKET_SIGN_ERROR("0006", "签名错误"),
    TICKET_TIMESTAMP_ERROR("0007", "时间戳错误"),
    TICKET_PARAM_ERROR("0008", "参数错误"),

    //牛圈闲逛
    NIU_GROUP_CREATE_MAX_NUM_ERROR("7000", "超过创建牛圈上限"),
    NIU_GROUP_JOIN_CREATE_MAX_NUM_ERROR("7001", "超过关注牛圈上限"),
    NIU_GROUP_MAX_NUM_ERROR("7002", "超过最大牛圈人数"),
    NIU_GROUP_NOT_EXIST_ERROR("7004", "牛圈不存在"),
    GROUPID_NULL_ERROR("7005", "groupId不能为空"),
    GROUP_NAME_NULL_ERROR("7006", "groupName不能为空"),
    GROUP_NAME_AND_GROUPID_ERROR("7007", "groupId与groupName数量不一致"),
    GROUPID_NULL_ERROR1("7005", "groupId不能为空"),
    
    //订单相关
    ORDER_EXISTS_PAID("8001","该内容你已经购买，请到我的订单中查看"),
    ORDER_EXISTS_UNPAID("8002","该内容你已经提交订单，请到我的订单中支付"),
    ORDER_CONTENT_NOT_EXIST("8003","购买内容不存在"),
    ORDER_NOT_EXIST("8004","订单不存在");

    private UpChinaError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code;

    public String message;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    public boolean isError() {
        return !isSuccess();
    }

}