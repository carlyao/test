//package com.augmentum.masterchef.gameserver.config;
//
//import java.util.Iterator;
//
//import javax.annotation.PostConstruct;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import com.augmentum.masterchef.util.GameProps;
//
//
//@Component
//public class GameService {
//
//    private final static Logger logger = LoggerFactory
//            .getLogger(GameService.class);
//
//    private final GameProps cfg = GameProps.getInstance();
//
//    @PostConstruct
//    public void printConfig() {
//        StringBuilder sb = new StringBuilder();
//        for (Iterator i = cfg.getConfig().getKeys(); i.hasNext();) {
//            String key = (String) i.next();
//            sb.append(String.format("%35s: %s%n", key, cfg.getString(key)));
//        }
//        logger.info("\n{}", sb.toString());
//    }
//
//    public String getRegisterMailSubject() {
//        return cfg.getString("register.mail.subject");
//    }
//
//    public String getRegisterMailContentTemplate() {
//        return cfg.getString("register.mail.content.template");
//    }
//
//    public String getContenderNotificationMailSubject() {
//        return cfg.getString("contender.notification.mail.subject");
//    }
//
//    public String getContenderNotificationMailContentTemplate() {
//        return cfg.getString("contender.notification.mail.content.template");
//    }
//
//    public long getInitialCredit() {
//        return cfg.getLong("player.wallet.credit.init");
//    }
//
//    public String getCreateOwnerMailSubject() {
//        return cfg.getString("create.owner.mail.subject");
//    }
//
//    public String getCreateOwnerMailContentTemplate() {
//        return cfg.getString("create.owner.content.template");
//    }
//    public String getPlayerPrizeTo() {
//            return cfg.getString("playerprize.toAdmin.mail.to");
//        }
//
//     public String getPlayerPrizeMailSubject() {
//            return cfg.getString("playerprize.toAdmin.mail.subject");
//        }
//
//   public String getPlayerPrizeContentTemplate() {
//            return cfg.getString("playerprize.toAdmin.mail.content.template");     
//        
//    }
// 
//   
//	public String getToPlayerMailSubject() {
//	       return cfg.getString("playerprize.toPlayer.mail.subject");
//	   }
//	
//	public String getToPlayerContentTemplate() {
//	       return cfg.getString("playerprize.toPlayer.mail.content.template");     
//	   
//	}
//}
