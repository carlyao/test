package com.upchina.util;

public interface Constants {
	
	public static final String UPCHINA_DDU_SUCCESS_CODE="0000";
	
    //牛圈用户类型
    public static final Integer TYPE_CREATOR = 1;
    public static final Integer TYPE_ADMIN = 2;
    public static final Integer TYPE_MEMBER = 3;
    public static final String NIU_GROUP_TOUGU_CREATE = "NIU_GROUP_TOUGU_CREATE";

    //牛圈用户状态
    public static final Integer STATUS_JOIN = 1;
    public static final Integer STATUS_QUIT = 2;
    //牛圈状态
    public static final Integer STATUS_TOP = 1;
    public static final Integer STATUS_COLSE = 2;
    public static final Integer STATUS_NORMAL = 3;
    public static final Integer STATUS_DISMISS = 4;

    String STATUS_GROUP_ON = "1,3";

    //牛圈标签状态
    public static final Integer STATUS_ADD = 1;
    public static final Integer STATUS_DELETE = 2;

    //用户类型
    public static final Integer USER_TYPE_INVESTMENT = 1;
    public static final Integer USER_TYPE_USER = 2;
    
    //投顾类型
    public static final Integer USER_PROFILE_TYPE_INVESTMENT =1;
    public static final Integer USER_PROFILE_TYPE_MAN =2;

    //定单类型 1为笔记2为直播3为问答4为组合5牛圈6投顾
    public static final Integer ORDER_TYPE_NOTE = 1;
    public static final Integer ORDER_TYPE_lIVE = 2;
    public static final Integer ORDER_TYPE_QA = 3;
    public static final Integer ORDER_TYPE_PORTFOLIO = 4;
    public static final Integer ORDER_TYPE_GROUP = 5;
    public static final Integer ORDER_TYPE_ADVISER = 6;

    //交易类型(1:购买；2:打赏)
    public static final Integer TRADE_TYPE_BUY = 1;
    public static final Integer TRADE_TYPE_REWARD = 2;
    public static final Integer TRADE_TYPE_SUBSCRIBE = 3;


    //订单支付状态
    public static final Integer ORDER_STATUS_ALL = 0;
    public static final Integer ORDER_STATUS_UNPAID = 1;
    public static final Integer ORDER_STATUS_PAID_SUCCESS = 2;
    public static final Integer ORDER_STATUS_PAID_FAILURE = 3;
    public static final Integer ORDER_STATUS_DISABLED = 4;

    //商户ID
    public static final Integer BUSINESS_ID = 1;

    //dictionary表相关(适用人群)
    public static final String SYSTEM_NAME = "userEvaluation";
    public static final String MODEL_NAME = "applicableCrowd";

    //用户好友关系
    public static final Integer RELATION_FRIEND = 2;
    public static final Integer RELATION_NONE = 0;
    public static final Integer RELATION_REQUEST = 1;
    public static final Integer RELATION_STRANGER = 3;
    public static final Integer RELATION_BLACKLIST = 4;

    //dictionary表相关(搜索类型)
    public static final String SYSTEM_NAME_FOR_SEARCH = "portfolioSearch";
    public static final String MODEL_NAME_FOR_SEARCH = "searchType";
    //dictionary表相关(牛人类型)
    public static final String SYSTEM_NAME_FOR_NIUER = "niuer";
    public static final String MODEL_NAME_FOR_NIUER = "niuerType";
    //dictionary表相关(直播类型)
    public static final String SYSTEM_NAME_FOR_LIVE = "live";
    public static final String MODEL_NAME_FOR_LIVE = "liveType";
    //dictionary表相关(组合类型)
    public static final String SYSTEM_NAME_FOR_PORTFOLIO = "portfolio";
    public static final String MODEL_NAME_FOR_PORTFOLIO = "portfolioType";
    //dictionary表相关(牛圈类型)
    public static final String SYSTEM_NAME_FOR_GROUP = "group";
    public static final String MODEL_NAME_FOR_GROUP = "groupType";
    //dictionary表相关(首页搜索类型)
    public static final String SYSTEM_NAME_FOR_HOMESEARCH = "homeSearch";
    public static final String MODEL_NAME_FOR_HOMESEARCH = "homeSearchType";
    
    //牛人是否推荐(0:不推荐；1:推荐)
    public static final Integer IS_RECOMMEND_N = 0;
    public static final Integer IS_RECOMMEND_Y = 1;

    //股票交易api调用返回码
    public static final String STOCK_TRADE_API_ERMS = "0";

    //0未启动的组合,1已启动的组合,2已结束的组合
    public static final Integer NO_START = 0;
    public static final Integer ALREADY_START = 1;
    public static final Integer IS_END = 2;

    //for test
//	public static final Integer BARGAINHIS = 10000;
//	public static final Integer VIEWRUNNING = 1;
//	public static final Integer USERCODE = 10000;

    public static final Integer PORTFOLIO_SUBSCRIBED_YES = 1;
    public static final Integer PORTFOLIO_SUBSCRIBED_NO = 0;
    //message
    public static final Integer MESSAGE_KEEP_DAY = 7;
    public static final Integer MESSAGE_NOT_READ = 0;
    public static final Integer MESSAGE_ALREADY_READ = 1;

    //push message
    public static final Integer MESSAGE_TYPE_CHANGE_PORTFOLIO = 1;//牛人组合调整
    public static final Integer MESSAGE_TYPE_GROUP = 2;//用户加入牛圈
    public static final Integer MESSAGE_TYPE_FRIEND = 3;//用户添加好友
    public static final Integer MESSAGE_TYPE_SUBSCRIBE_NOTE = 8;//用户购买笔记
    public static final Integer MESSAGE_TYPE_REMOVE_FRIEND = 9;//用户取消好友
    public static final Integer MESSAGE_TYPE_QUIT_NIUGROUP = 11;//用户退出牛圈
    public static final Integer MESSAGE_TYPE_COMMENT_NOTE = 12;//用户评论笔记
    public static final Integer MESSAGE_TYPE_FAVOURITE_NOTE = 13;//用户点赞笔记
    public static final Integer MESSAGE_TYPE_PORTFOLIO_START = 14;//牛人组合启动
    public static final Integer MESSAGE_TYPE_SUBSCRIBE_PORTFOLIO = 15;//用户购买组合
    public static final Integer MESSAGE_TYPE_PORTFOLIO_STANDARD = 16;//牛人组合达标
    public static final Integer MESSAGE_TYPE_PORTFOLIO_END = 17;//牛人组合结束
    public static final Integer MESSAGE_TYPE_PUBLISH_NOTE = 18;//牛人发布笔记
    public static final Integer MESSAGE_TYPE_ESTABLISH_PORTFOLIO = 19;//牛人创建组合
    public static final Integer MESSAGE_TYPE_ESTABLISH_NIUGROUP = 20;//牛人创建牛圈

    //消息推送，接收方的通知类型
    public static final Integer PUSH_TYPE_SUBSCRIB = 1;//购买通知
    public static final Integer PUSH_TYPE_FRIEND = 2;//好友通知
    public static final Integer PUSH_TYPE_GROUP_FRIEND = 3;//圈友通知
    public static final Integer PUSH_TYPE_COMMENT = 4;//评论通知
    public static final Integer PUSH_TYPE_CLICK_ZAN = 5;//点赞通知
    public static final Integer PUSH_TYPE_SERVICE = 6;//服务通知
    public static final Integer PUSH_TYPE_NIUER = 7;//牛人通知

    //费用类型
    public static final Integer COST_TYPE_NO_CHARGE = 1;
    public static final Integer COST_TYPE_CHARGE = 2;

    //笔记状态
    Integer NOTE_PUBLISH = 0;
    Integer NOTE_DRAFT = 1;
    Integer NOTE_DELETE = -1;

    //笔记点赞
    public static final String ZAN_FAIL = "-2";//点赞失败
    public static final String NOTE_NOT_EXIST = "-1";//被点赞的笔记不存在
    public static final String NOT_ALLOWED_ZAN = "-3";//不准许给笔记点赞
    public static final String ALREADY_ZAN = "-4";//已经给笔记点赞过了

    //好友状态（user_friend表）
    public static final Integer REQUEST_FRIEND = 1;//请求添加为好友
    public static final Integer IS_FRIEND = 2;//2表示好友

    //笔记列表用户标签
    String NOTE_USER_TAG = "4,6,7,8,9,10,11,12";
    //笔记标签
    String NOTE_TAG = "13";
    //最热标签
    public static final String HOT_TAG_TYPES = "4,5,6";

    //信息状态（user_message）
    public static final Integer NOT_READ = 0;//0表示未读
    public static final Integer IS_READ = 1;//1表示已读

    //rong cloud http success code
    public static final Integer HTTP_CODE = 200;
    //点赞状态
    Integer FAVORITE_YES = 1;
    Integer FAVORITE_NO = 0;

    //
    public static final String LINE = "_";
    public static final Integer PULL_DOWN = 1;
    public static final Integer PULL_UP = 2;

    public static final Integer FIRST_OR_NOT_OVER = 1;//第一个组合或者没有组合结束
    public static final Integer NO_FIRST_OR_IS_OVER = 2;//不是第一个组合或者已经有组合结束


    //直播火爆程度
    Integer LIVE_HOT_FIVE = 5;
    Integer LIVE_HOT_FOUR = 4;
    Integer LIVE_HOT_THREE = 3;
    Integer LIVE_HOT_TWO = 2;
    Integer LIVE_HOT_ONE = 1;

    //attachment
    public static final Integer ATTACHMENT_MODULE_LIVE = 2;
    public static final Integer ATTACHMENT_MODULE_GROUP = 1;

    public static final String PLATFORM_TYPE_IOS = "ios";
    public static final String PLATFORM_TYPE_ANDROID = "android";
    public static final String PLATFORM_TYPE_WEB = "web";
    
    public static final String APPID_DDU = "ddu";
    public static final String APPID_UP = "up";

    //账期
    public static final Integer PERIOD = 15;
}
