package com.augmentum.masterchef.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.augmentum.masterchef.constant.ICodeConstants;

public class ServiceAccessDefinition {

	private final Log log = LogFactory.getLog(getClass());
	
	private boolean sorted = false;

	private List<String> trustAddresses;
	private List<String> publicApis;

	private String authenticationServerURL=InitProps.getInstance().getAuthenticationKeyUrl();
	private String authenticationCreateSessionUrl=InitProps.getInstance().getAuthenticationCreateSessionUrl();
	private boolean useDuplicateSubmitProtection=InitProps.getInstance().isDuplicatedSubmitCheck();
	private long timestampCacheCount=InitProps.getInstance().getTimeStampCheckNumber();	
	private boolean useEncryptURL = InitProps.getInstance().isEnableEncryption();//whether use encrypt URL, default value is false
	
	
	public boolean isUseEncryptURL() {
		return useEncryptURL;
	}

	public void setUseEncryptURL(boolean useEncryptURL) {
		this.useEncryptURL = useEncryptURL;
	}

	public List<String> getPublicApis() {
		if (!sorted) {
			Collections.sort(publicApis, new DescStringComparator());
			sorted = true;
		}
		return publicApis;
	}

	public void setPublicApis(List<String> publicApis) {
		this.publicApis = publicApis;
	}

	public List<String> getTrustAddresses() {
		return trustAddresses;
	}

	public void setTrustAddresses(List<String> trustAddresses) {
		this.trustAddresses = trustAddresses;
	}

	/**
	 * Judge whether an API requires securing access
	 * 
	 * @param request
	 * @return
	 */
	public boolean isSecureRequired(HttpServletRequest request) {

		// If visit IP comes from trusted IP range, then doesn't need secure
		// access
		String requestIp = request.getRemoteAddr();
		//log.debug("requestIp:"+requestIp+", trustAddresses:"+trustAddresses);
		if (IpUtil.isIpInRange(requestIp, trustAddresses)) {
			log.debug("client ip in trust range.");
			return false;
		}
		
		String uri = request.getRequestURI();
		int pos = uri.indexOf(ICodeConstants.RESTFUL_FORMAT);
		int len = ICodeConstants.RESTFUL_FORMAT.length();
		String partUri = uri.substring(pos+len);//get part uri that is after 'restful' 
		log.debug("part Uri:"+partUri);
		for (String api : getPublicApis()) {
			if (partUri.startsWith(api)) {
				// If it's a public API, don't require secure access
				log.debug("call public API.");
				return false;
			}
		}

		// Not from trusted IP and not public API, then need secure access
		return true;
	}

	private class DescStringComparator implements Comparator<String> {

		@Override
		public int compare(String s1, String s2) {
			return -s1.compareTo(s2);
		}

	}
	
	
	public boolean isUseDuplicateSubmitProtection() {
		return useDuplicateSubmitProtection;
	}

	public void setUseDuplicateSubmitProtection(boolean useDuplicateSubmitProtection) {
		this.useDuplicateSubmitProtection = useDuplicateSubmitProtection;
	}
	
	public long getTimestampCacheCount() {
		return timestampCacheCount;
	}

	public void setTimestampCacheCount(long timestampCacheCount) {
		this.timestampCacheCount = timestampCacheCount;
	}

    public String getAuthenticationServerURL() {
        return authenticationServerURL;
    }

    public void setAuthenticationServerURL(String authenticationServerURL) {
        this.authenticationServerURL = authenticationServerURL;
    }

	public String getAuthenticationCreateSessionUrl() {
		return authenticationCreateSessionUrl;
	}
	
	/*
	private boolean mock;
	
	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}
	*/
}
