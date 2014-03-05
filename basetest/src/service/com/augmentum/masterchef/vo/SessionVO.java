package com.augmentum.masterchef.vo;

import java.io.Serializable;
import java.util.Date;

public class SessionVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long activeSeconds;
	private Date activeTime;

	private Long gameId;
	private Long gameServerId;
	private Long id;
	private String ip;
	private String keyValue;
	private Date lastAccessTime;
	private Date loginTime;
	private String sessionId;
	private boolean isSingleSignon;

	private Long characterId;
	private Long accountId;

	public SessionVO() {
	}

	public SessionVO(Long accountId, Long characterId) {
		this.accountId = accountId;
		this.characterId = characterId;
	}

	public SessionVO(Long accountId, Long activeSeconds, Date activeTime,
			Long characterId, Long gameId, Long gameServerId, Long id,
			String ip, String keyValue, Date lastAccessTime, Date loginTime,
			String sessionId) {
		super();
		this.accountId = accountId;
		this.activeSeconds = activeSeconds;
		this.activeTime = activeTime;
		this.characterId = characterId;
		this.gameId = gameId;
		this.gameServerId = gameServerId;
		this.id = id;
		this.ip = ip;
		this.keyValue = keyValue;
		this.lastAccessTime = lastAccessTime;
		this.loginTime = loginTime;
		this.sessionId = sessionId;
	}

	public SessionVO(String ip, Long accountId, Long gameId, Long gameServerId,
			Long characterId, Long activeSeconds, Boolean isSingleSignon) {
		this.ip = ip;
		this.accountId = accountId;
		this.gameId = gameId;
		this.gameServerId = gameServerId;
		this.characterId = characterId;
		this.activeSeconds = activeSeconds;
		this.isSingleSignon = isSingleSignon;
	}

    public SessionVO(Long accountId) {
        this.accountId = accountId;
    }

	public Long getCharacterId() {
		return characterId;
	}

	public void setCharacterId(Long characterId) {
		this.characterId = characterId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getActiveSeconds() {
		return activeSeconds;
	}

	public void setActiveSeconds(Long activeSeconds) {
		this.activeSeconds = activeSeconds;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getGameServerId() {
		return gameServerId;
	}

	public void setGameServerId(Long gameServerId) {
		this.gameServerId = gameServerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSingleSignon(boolean isSingleSignon) {
		this.isSingleSignon = isSingleSignon;
	}

	public boolean isSingleSignon() {
		return isSingleSignon;
	}

}
