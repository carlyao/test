package com.augmentum.masterchef.gameserver.config;

import java.util.Iterator;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.augmentum.masterchef.util.ConfigProps;

@Component
public class ConfigService {

	private final static Logger logger = LoggerFactory
			.getLogger(ConfigService.class);

	private final ConfigProps cfg = ConfigProps.getInstance();

	@PostConstruct
	public void printConfig() {
		StringBuilder sb = new StringBuilder();
		for (Iterator i = cfg.getConfig().getKeys(); i.hasNext();) {
			String key = (String) i.next();
			sb.append(String.format("%35s: %s%n", key, cfg.getString(key)));
		}
		logger.info("\n{}", sb.toString());
	}

	public String getSecret() {
		return cfg.getString("weibo.secret");
	}

	public String getPreOrderId() {
		return cfg.getString("weibo.payment.id");
	}

	public String getAppId() {
		return cfg.getString("appId");
	}

	public String getAppSignature() {
		return cfg.getString("appSignature");
	}

	public String getSocailConnectorUrl() {
		return cfg.getString("socialConnectorUrl");
	}

	public String getGamePortalUrl() {
		return cfg.getString("gameportalUrl");
	}

	public String getGamePortalAppKey() {
		return cfg.getString("gameportalAppKey");
	}

	public String gethandleClass(String name) {
		return cfg.getString(name);
	}

	public String getMetricsUrl() {
		return cfg.getString("metricsUrl");
	}

	public String getTuisongbaoUrl() {
		return cfg.getString("tuisongbaoUrl");
	}

	public String getTuisongbaoApisecret() {
		return cfg.getString("tuisongbaoApisecret");
	}

	public String getTuisongbaoApikey() {
		return cfg.getString("tuisongbaoApikey");
	}

	public String getTuisongbaoAppkey() {
		return cfg.getString("tuisongbaoAppkey");
	}

}
