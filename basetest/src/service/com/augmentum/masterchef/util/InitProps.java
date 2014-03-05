package com.augmentum.masterchef.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class InitProps extends AbstractInitProps {
	protected static final String PROPS_NAME = "config.xml";

	//protected static InitProps props = new InitProps();
	protected static Map<String, InitProps> props = new HashMap<String, InitProps>();

	// overall game name
	protected String gameName;

	// authentication
	protected long defaultPlatformId;

	// timezones
	protected String defaultTimeZone;

	// login
	protected boolean clientSecurity;
	protected String clientLogoutUrl;
	protected String clientLoginUrl;
	protected String newClientLoginUrl;
	protected String loginUrl;

	// urls
	protected String gameHomeUrl;
	protected String redirectUrl;

	// views
	protected String clientLoginView;
	protected String loginView;
	protected String redirectView;
	protected String landingView;

	// cookie
	protected String cookieDomain;
	protected int cookieIdKey;
	protected int cookieEmailKey;
	protected int cookieSubIdKey;
	protected int cookieNickNameKey;
	protected int cookiePhotoUrlKey;
	protected int cookiePhotoTypeKey;

	// facebook
	protected String facebookAppId;
	protected String facebookApiKey;
	protected String facebookSecret;
	protected String facebookAppsUrl;
	protected String facebookAppBaseUrl;

	// cache
	protected boolean localCacheEnabled;
	protected int remoteCacheType;
	protected boolean remoteCacheEnabled;
    protected String timeToLiveShortest;
    protected String timeToLiveMidShort;
	protected String timeToLiveShort;
	protected String timeToLiveMedium;
	protected String timeToLiveLong;
	protected String timeToLiveDefault;
	

	// web init
	protected String webInitAppBase;
	protected String webInitTopFrame;
	protected String webInitUrlBase;
	protected String webInitImagePath;

	// facebookeTrack
	protected String facebookTrackUrl;
	protected String facebookTrackApiKey;
	protected String facebookTrackSecret;
	
	// csv path
	protected String csvPath;
	
	//cacheName   
	protected String cacheName;
	
	//httpClient config
	protected int maxConnectionsPerHost;
    protected int maxTotalConnections;
    protected String responseCharset;
    
    protected int timeStampCheckNumber;
    protected boolean enableEncryption;
    protected boolean duplicatedSubmitCheck;
    protected String authenticationKeyUrl;
    protected String authenticationCreateSessionUrl;

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	// sports
	protected Set<Integer> sports = new HashSet<Integer>();

	public InitProps(String propsFileName) {
		super(propsFileName);

		// initialize variables from properties file.  Make sure to load this
		// on application startup so that it will not start up properly if
		// properties are not set correctly (formatting issues etc.)
		try {
			gameName = config.getString("gameName", null);

			defaultPlatformId = config.getInt("defaultPlatformId", 0);

			defaultTimeZone = config.getString("defaultTimeZone", "America/New_York");

			clientSecurity = config.getBoolean("clientSecurity", false);
			clientLogoutUrl = config.getString("clientLogoutUrl", null);
			clientLoginUrl = config.getString("clientLoginUrl", null);
			newClientLoginUrl = config.getString("newClientLoginUrl", null);
			loginUrl = config.getString("loginUrl", null);

			gameHomeUrl = config.getString("gameHomeUrl", null);
			redirectUrl = config.getString("redirectUrl", null);

			clientLoginView = config.getString("clientLoginView", null);
			loginView = config.getString("loginView", null);
			redirectView = config.getString("redirectView", null);
			landingView = config.getString("landingView", null);

			cookieDomain = config.getString("cookieDomain", null);
			cookieIdKey = config.getInt("cookieIdKey", 0);
			cookieEmailKey = config.getInt("cookieEmailKey", 0);
			cookieSubIdKey = config.getInt("cookieSubIdKey", 0);
			cookieNickNameKey = config.getInt("cookieNickNameKey", 0);
			cookiePhotoUrlKey = config.getInt("cookiePhotoUrlKey", 0);
			cookiePhotoTypeKey = config.getInt("cookiePhotoTypeKey", 0);

			facebookAppId = config.getString("facebookAppId", null);
			facebookApiKey = config.getString("facebookApiKey", null);
			facebookSecret = config.getString("facebookSecret", null);
			facebookAppsUrl = config.getString("facebookAppsUrl", null);
			facebookAppBaseUrl = config.getString("facebookAppBaseUrl", null);

			// cache
			localCacheEnabled = config.getBoolean("localCacheEnabled", false);
			remoteCacheType = config.getInt("remoteCacheType", 0);
			remoteCacheEnabled = config.getBoolean("remoteCacheEnabled", false);
			timeToLiveShortest = config.getString("timeToLiveShortest", null);
            timeToLiveMidShort = config.getString("timeToLiveMidShort", null);
			timeToLiveShort = config.getString("timeToLiveShort", null);
			timeToLiveMedium = config.getString("timeToLiveMedium", null);
			timeToLiveLong = config.getString("timeToLiveLong", null);
			timeToLiveDefault = config.getString("timeToLiveDefault", null);
			

			// web init
			webInitAppBase = config.getString("webInitAppBase", null);
			webInitTopFrame = config.getString("webInitTopFrame", null);
			webInitUrlBase = config.getString("webInitUrlBase", null);
			webInitImagePath = config.getString("webInitImagePath", null);

			facebookTrackUrl = config.getString("facebookTrackUrl", null);
			facebookTrackApiKey = config.getString("facebookTrackApiKey", null);
			facebookTrackSecret = config.getString("facebookTrackSecret", null);
			

            csvPath = config.getString("csv.path", null);
            
            cacheName = config.getString("authenticationCacheName",null);
            
            maxConnectionsPerHost = config.getInt("maxConnectionsPerHost",200);
            maxTotalConnections = config.getInt("maxTotalConnections",500);
            responseCharset = config.getString("responseCharset",null);
            
            timeStampCheckNumber = config.getInt("timeStampCheckNumber", 1000);
            enableEncryption = config.getBoolean("enableEncryption", true);
            duplicatedSubmitCheck = config.getBoolean("duplicatedSubmitCheck", true);
            authenticationKeyUrl = config.getString("authenticationKeyUrl", null);
            authenticationCreateSessionUrl = config.getString("authenticationCreateSessionUrl", null);
            
			// handle available sports
			String sportsStr = config.getString("sports", null);
			if (StringUtils.isNotBlank(sportsStr)) {
				StringTokenizer tk = new StringTokenizer(sportsStr, ",");
				while (tk.hasMoreTokens()) {
					String token = tk.nextToken().trim();
					if (NumberUtils.isDigits(token)) {
						sports.add(Integer.parseInt(token));
					}
				}
			}
		} catch (Throwable th) {
			th.printStackTrace();
			throw new ExceptionInInitializerError(th);
		}
	}

	public static InitProps getInstance() {
		return getInstance(PROPS_NAME);
	}

	public static InitProps getInstance(String propsFileName) {
		InitProps init = props.get(propsFileName);
		if (init == null) {
			init = new InitProps(propsFileName);
			props.put(propsFileName, init);
		}

		return init;
	}

	public long getDefaultPlatformId() {
		return defaultPlatformId;
	}

	public void setDefaultPlatformId(long defaultPlatformId) {
		this.defaultPlatformId = defaultPlatformId;
	}

	public String getDefaultTimeZone() {
		return defaultTimeZone;
	}

	public void setDefaultTimeZone(String defaultTimeZone) {
		this.defaultTimeZone = defaultTimeZone;
	}

	public boolean isClientSecurity() {
		return clientSecurity;
	}

	public void setClientSecurity(boolean clientSecurity) {
		this.clientSecurity = clientSecurity;
	}

	public String getClientLogoutUrl() {
		return clientLogoutUrl;
	}

	public void setClientLogoutUrl(String clientLogoutUrl) {
		this.clientLogoutUrl = clientLogoutUrl;
	}

	public String getClientLoginUrl() {
		return clientLoginUrl;
	}

	public void setClientLoginUrl(String clientLoginUrl) {
		this.clientLoginUrl = clientLoginUrl;
	}

	public String getNewClientLoginUrl() {
		return newClientLoginUrl;
	}

	public void setNewClientLoginUrl(String newClientLoginUrl) {
		this.newClientLoginUrl = newClientLoginUrl;
	}

	public String getGameHomeUrl() {
		return gameHomeUrl;
	}

	public void setGameHomeUrl(String gameHomeUrl) {
		this.gameHomeUrl = gameHomeUrl;
	}

	public String getClientLoginView() {
		return clientLoginView;
	}

	public void setClientLoginView(String clientLoginView) {
		this.clientLoginView = clientLoginView;
	}

	public String getLoginView() {
		return loginView;
	}

	public void setLoginView(String loginView) {
		this.loginView = loginView;
	}

	public String getRedirectView() {
		return redirectView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	public String getLandingView() {
		return landingView;
	}

	public void setLandingView(String landingView) {
		this.landingView = landingView;
	}

	public int getCookieIdKey() {
		return cookieIdKey;
	}

	public void setCookieIdKey(int cookieIdKey) {
		this.cookieIdKey = cookieIdKey;
	}

	public int getCookieEmailKey() {
		return cookieEmailKey;
	}

	public void setCookieEmailKey(int cookieEmailKey) {
		this.cookieEmailKey = cookieEmailKey;
	}

	public int getCookieSubIdKey() {
		return cookieSubIdKey;
	}

	public void setCookieSubIdKey(int cookieSubIdKey) {
		this.cookieSubIdKey = cookieSubIdKey;
	}

	public int getCookieNickNameKey() {
		return cookieNickNameKey;
	}

	public void setCookieNickNameKey(int cookieNickNameKey) {
		this.cookieNickNameKey = cookieNickNameKey;
	}

	public int getCookiePhotoUrlKey() {
		return cookiePhotoUrlKey;
	}

	public void setCookiePhotoUrlKey(int cookiePhotoUrlKey) {
		this.cookiePhotoUrlKey = cookiePhotoUrlKey;
	}

	public int getCookiePhotoTypeKey() {
		return cookiePhotoTypeKey;
	}

	public void setCookiePhotoTypeKey(int cookiePhotoTypeKey) {
		this.cookiePhotoTypeKey = cookiePhotoTypeKey;
	}

	public String getFacebookAppId() {
		return facebookAppId;
	}

	public void setFacebookAppId(String facebookAppId) {
		this.facebookAppId = facebookAppId;
	}

	public String getFacebookApiKey() {
		return facebookApiKey;
	}

	public void setFacebookApiKey(String facebookApiKey) {
		this.facebookApiKey = facebookApiKey;
	}

	public String getFacebookSecret() {
		return facebookSecret;
	}

	public void setFacebookSecret(String facebookSecret) {
		this.facebookSecret = facebookSecret;
	}

	public String getFacebookAppsUrl() {
		return facebookAppsUrl;
	}

	public void setFacebookAppsUrl(String facebookAppsUrl) {
		this.facebookAppsUrl = facebookAppsUrl;
	}

	public String getFacebookAppBaseUrl() {
		return facebookAppBaseUrl;
	}

	public void setFacebookAppBaseUrl(String facebookAppBaseUrl) {
		this.facebookAppBaseUrl = facebookAppBaseUrl;
	}

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public Set<Integer> getSports() {
		return sports;
	}

	public void setSports(Set<Integer> sports) {
		this.sports = sports;
	}

	public boolean isValidSport(int sport) {
		return this.sports.contains(sport);
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public int getRemoteCacheType() {
		return remoteCacheType;
	}

	public void setRemoteCacheType(int remoteCacheType) {
		this.remoteCacheType = remoteCacheType;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public boolean isLocalCacheEnabled() {
		return localCacheEnabled;
	}

	public void setLocalCacheEnabled(boolean localCacheEnabled) {
		this.localCacheEnabled = localCacheEnabled;
	}

	public boolean isRemoteCacheEnabled() {
		return remoteCacheEnabled;
	}

	public void setRemoteCacheEnabled(boolean remoteCacheEnabled) {
		this.remoteCacheEnabled = remoteCacheEnabled;
	}

	public String getTimeToLiveShort() {
		return timeToLiveShort;
	}

	public void setTimeToLiveShort(String timeToLiveShort) {
		this.timeToLiveShort = timeToLiveShort;
	}

	public String getTimeToLiveShortest() {
        return timeToLiveShortest;
    }

    public void setTimeToLiveShortest(String timeToLiveShortest) {
        this.timeToLiveShortest = timeToLiveShortest;
    }

    public String getTimeToLiveMidShort() {
        return timeToLiveMidShort;
    }

    public void setTimeToLiveMidShort(String timeToLiveMidShort) {
        this.timeToLiveMidShort = timeToLiveMidShort;
    }

    public String getTimeToLiveMedium() {
		return timeToLiveMedium;
	}

	public void setTimeToLiveMedium(String timeToLiveMedium) {
		this.timeToLiveMedium = timeToLiveMedium;
	}

	public String getTimeToLiveLong() {
		return timeToLiveLong;
	}

	public void setTimeToLiveLong(String timeToLiveLong) {
		this.timeToLiveLong = timeToLiveLong;
	}

	public String getTimeToLiveDefault() {
		return timeToLiveDefault;
	}

	public void setTimeToLiveDefault(String timeToLiveDefault) {
		this.timeToLiveDefault = timeToLiveDefault;
	}

	public String getWebInitAppBase() {
		return webInitAppBase;
	}

	public void setWebInitAppBase(String webInitAppBase) {
		this.webInitAppBase = webInitAppBase;
	}

	public String getWebInitTopFrame() {
		return webInitTopFrame;
	}

	public void setWebInitTopFrame(String webInitTopFrame) {
		this.webInitTopFrame = webInitTopFrame;
	}

	public String getWebInitUrlBase() {
		return webInitUrlBase;
	}

	public void setWebInitUrlBase(String webInitUrlBase) {
		this.webInitUrlBase = webInitUrlBase;
	}

	public String getWebInitImagePath() {
		return webInitImagePath;
	}

	public void setWebInitImagePath(String webInitImagePath) {
		this.webInitImagePath = webInitImagePath;
	}

	public String getFacebookTrackUrl() {
		return facebookTrackUrl;
	}

	public void setFacebookTrackUrl(String facebookTrackUrl) {
		this.facebookTrackUrl = facebookTrackUrl;
	}

	public String getFacebookTrackApiKey() {
		return facebookTrackApiKey;
	}

	public void setFacebookTrackApiKey(String facebookTrackApiKey) {
		this.facebookTrackApiKey = facebookTrackApiKey;
	}

	public String getFacebookTrackSecret() {
		return facebookTrackSecret;
	}

	public void setFacebookTrackSecret(String facebookTrackSecret) {
		this.facebookTrackSecret = facebookTrackSecret;
	}

    public String getCsvPath() {
        return csvPath;
    }

    public void setCsvPath(String csvPath) {
        this.csvPath = csvPath;
    }

    public int getMaxConnectionsPerHost() {
        return maxConnectionsPerHost;
    }

    public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
        this.maxConnectionsPerHost = maxConnectionsPerHost;
    }

    public int getMaxTotalConnections() {
        return maxTotalConnections;
    }

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public int getTimeStampCheckNumber() {
        return timeStampCheckNumber;
    }

    public void setTimeStampCheckNumber(int timeStampCheckNumber) {
        this.timeStampCheckNumber = timeStampCheckNumber;
    }

    public boolean isEnableEncryption() {
        return enableEncryption;
    }

    public void setEnableEncryption(boolean enableEncryption) {
        this.enableEncryption = enableEncryption;
    }

    public boolean isDuplicatedSubmitCheck() {
        return duplicatedSubmitCheck;
    }

    public void setDuplicatedSubmitCheck(boolean duplicatedSubmitCheck) {
        this.duplicatedSubmitCheck = duplicatedSubmitCheck;
    }

    public String getAuthenticationKeyUrl() {
        return authenticationKeyUrl;
    }

    public void setAuthenticationKeyUrl(String authenticationKeyUrl) {
        this.authenticationKeyUrl = authenticationKeyUrl;
    }

	public String getAuthenticationCreateSessionUrl() {
		return authenticationCreateSessionUrl;
	}
    
	public String getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(String responseCharset) {
		this.responseCharset = responseCharset;
	}
}