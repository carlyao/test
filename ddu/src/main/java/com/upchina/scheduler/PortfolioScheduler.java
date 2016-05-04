package com.upchina.scheduler;

import java.util.Date;
import java.util.List;

import com.upchina.dao.UserGroupMapper;
import com.upchina.dao.UserNoSayingTimeMapper;
import com.upchina.model.UserNoSayingTime;
import com.upchina.util.ImagePathUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.upchina.account.model.AccountRankHis;
import com.upchina.account.service.AccountRankHisService;
import com.upchina.model.PortfolioRankHis;
import com.upchina.service.PortfolioRankHisService;
import com.upchina.service.PortfolioService;
import com.upchina.service.PushMessageService;
import com.upchina.service.UserInfoService;
import com.upchina.service.UserOrderService;
import com.upchina.util.Constants;
import com.upchina.vo.rest.output.PushMessagePortfolioOutVo;
import com.upchina.vo.rest.output.PushMessageUserOutVo;
import com.upchina.util.DateFormat;
import org.springframework.transaction.annotation.Transactional;

public class PortfolioScheduler {
    private static Logger logger = LoggerFactory.getLogger(PortfolioScheduler.class);

    @Autowired
    private AccountRankHisService accountRankHisService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private PortfolioRankHisService portfolioRankHisService;

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserNoSayingTimeMapper userNoSayingTimeMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    public synchronized void  loadPortfolio(String date) {
        int count = portfolioRankHisService.getCountByDate(date);
        if (count == 0) {
            List<AccountRankHis> accountRankHisList = accountRankHisService.getAccountRankHis(date);
            for (AccountRankHis accountRankHis : accountRankHisList) {
                PortfolioRankHis portfolioRankHis = new PortfolioRankHis();
                portfolioRankHis.setId(accountRankHis.getId());
                portfolioRankHis.setWeeks(accountRankHis.getWeeks());
                portfolioRankHis.setDayOfWeek(accountRankHis.getDayofweek());
                portfolioRankHis.setUserId(accountRankHis.getUserid());
                portfolioRankHis.setUserCode(accountRankHis.getUsercode());
                portfolioRankHis.setTotalProfit(accountRankHis.getTotalprofit());
                portfolioRankHis.setMonthNetValue(accountRankHis.getMonthnetvalue());
                portfolioRankHis.setWeekNetValue(accountRankHis.getWeeknetvalue());
                portfolioRankHis.setDayNetValue(accountRankHis.getDaynetvalue());
                portfolioRankHis.setNewNetValue(accountRankHis.getNewnetvalue());
                portfolioRankHis.setMaxDrawdown(accountRankHis.getMaxdrawdown());
                portfolioRankHis.setWin(accountRankHis.getWin());
                portfolioRankHis.setTotalWin(accountRankHis.getTotalwin());
                portfolioRankHis.setLose(accountRankHis.getLose());
                portfolioRankHis.setTotalLose(accountRankHis.getTotallose());
                portfolioRankHis.setDraw(accountRankHis.getDraw());
                portfolioRankHis.setTotalDraw(accountRankHis.getTotaldraw());
                portfolioRankHis.setUnfinished(accountRankHis.getUnfinished());
                portfolioRankHis.setRank(accountRankHis.getRank());
                portfolioRankHis.setTotalNum(accountRankHis.getTotalnum());
                portfolioRankHis.setUpdateTime(accountRankHis.getUpdatetime());
                portfolioRankHis.setZhId(Integer.parseInt(accountRankHis.getZhid()));
                portfolioRankHisService.insertSelective(portfolioRankHis);
            }
            System.out.println(DateFormat.GetDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss") + "scheduler protfolio end");
        }
    }

    public void load() {
        loadPortfolio(null);
    }

    /**
     * 牛人组合启动
     */
    public void start() {
        logger.debug("组合启动0");
        try {
            List<PushMessagePortfolioOutVo> portfolios = portfolioService.findTodayStartPortfolio();
            logger.debug("[PortfolioScheduler] start portfolios size:", portfolios.size());
            for (PushMessagePortfolioOutVo portfolioVo : portfolios) {
                //根据组合ID查找已订阅的用户
                List<String> users = userOrderService.selectUserOrder(portfolioVo.getPortfolioId(), Constants.ORDER_TYPE_PORTFOLIO);
                PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(portfolioVo.getUserId());
                pushMessageService.pushPortfolioStartMessage(users, portfolioVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), portfolioVo.getPortfolioId(), portfolioVo.getPortfolioName(), portfolioVo.getStartTime());
            }
            logger.debug("组合启动1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 牛人组合结束
     */
    public void end() {
        logger.debug("组合结束0");
        try {
            List<PushMessagePortfolioOutVo> portfolios = portfolioService.findTodayEndPortfolio();
            logger.debug("[PortfolioScheduler] end portfolios size:", portfolios.size());
            for (PushMessagePortfolioOutVo portfolioVo : portfolios) {
                //根据组合ID查找已订阅的用户
                List<String> users = userOrderService.selectUserOrder(portfolioVo.getPortfolioId(), Constants.ORDER_TYPE_PORTFOLIO);
                PushMessageUserOutVo userMessageInVo = userInfoService.findByUserId(portfolioVo.getUserId());
                pushMessageService.pushPortfolioEndMessage(users, portfolioVo.getUserId(), userMessageInVo.getUserName(), userMessageInVo.getAvatar(), portfolioVo.getPortfolioId(), portfolioVo.getPortfolioName(), portfolioVo.getStartTime());
            }
            logger.debug("组合结束1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每5分钟定时扫描被禁言的用户是否已经到达时间可以解除禁言了
     */
    @Transactional
    public void releaseAndCanSaying() {
        System.out.println("===任务计划====");
        Integer minute = Integer.valueOf(ImagePathUtil.getNoSayingTime());//默认禁言10分钟
        List<UserNoSayingTime> list = userNoSayingTimeMapper.selectAllRecord();
        if (list != null && !list.isEmpty()) {
            System.out.println("开始执行解除用户被禁言任务计划。。。");
            for (int i = 0; i < list.size(); i++) {
                UserNoSayingTime userNoSayingTime = list.get(i);
                Date lastTime = DateUtils.addMinutes(userNoSayingTime.getStartTime(), minute);
                Date currentTime = new Date();
                if (lastTime.compareTo(currentTime) != 1) {
                    userGroupMapper.updateUserGroupStatus(userNoSayingTime.getUserGroupId(), Constants.STATUS_JOIN);
                    userNoSayingTimeMapper.delete(userNoSayingTime);
                }
            }
            System.out.println("结束执行解除用户被禁言任务计划。。。");
        }
    }
}
