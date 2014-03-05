package com.augmentum.masterchef.util;

import java.util.ArrayList;
import java.util.List;

public class IpUtil {

    /**
     * Judge whether the given IP is in the specified IP range list
     * @param ip
     * @param ipRanges
     * @return
     */
	public static boolean isIpInRange(String ip, List<String> ipRanges) {

		for (String ipRange : ipRanges) {
			if(ipRange==null){
				continue;
			}
			if (ip.equals(ipRange.trim())) {
				return true;
			} else if (ipRange.contains(",")) {
			    String[] ips=ipRange.split(",");
			    List<String> ipList=new ArrayList<String>();
			    for(int i=0;i<ips.length;i++){
			        ipList.add(ips[i]);
			    }
                if (isIpInRange(ip, ipList)) {
                    return true;
                }
            } else if (ipRange.contains("-")) {
				if (isIpInRange(ip, ipRange)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Judge whether the given IP is in the specified IP range
	 * @param ip
	 * @param ipRange
	 * @return
	 */
	public static boolean isIpInRange(String ip, String ipRange) {
		String[] ipRanges = ipRange.split("-");
		
		if (formatIp(ip).compareTo(formatIp(ipRanges[0])) >= 0
				&& formatIp(ip).compareTo(formatIp(ipRanges[1])) <= 0) {
			return true;
		}

		return false;
	}

	/**
	 * Format the IP to full length String for comparison. E.g. 127.0.0.1 -> 127000000001
	 * @param ip
	 * @return
	 */
	public static String formatIp(String ip) {
		String[] ipBlocks = ip.split("\\.");
		StringBuffer formatted = new StringBuffer();
		for (int i = 0; i < ipBlocks.length; i++) {
			if (ipBlocks[i].length() == 1) {
				formatted.append("00").append(ipBlocks[i]);
			} else if (ipBlocks[i].length() == 2) {
				formatted.append("0").append(ipBlocks[i]);
			} else {
				formatted.append(ipBlocks[i]);
			}
		}
		return formatted.toString();
	}
}
