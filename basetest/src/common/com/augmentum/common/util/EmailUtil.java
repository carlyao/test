package com.augmentum.common.util;

import java.util.StringTokenizer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailUtil {
	public static boolean isValidEmailAddress(String emailAddress) {
		// a null string is invalid
		if (emailAddress == null)
			return false;

		// a string without a "@" is an invalid email address
		if (emailAddress.indexOf("@") < 0)
			return false;

		// a string without a "."  is an invalid email address
		if (emailAddress.indexOf(".") < 0)
			return false;

		if (lastEmailFieldTwoCharsOrMore(emailAddress) == false)
			return false;

		try {
			InternetAddress internetAddress = new InternetAddress(emailAddress);
			return true;
		} catch (AddressException ae) {
			// log exception
			return false;
		}
	}

	protected static boolean lastEmailFieldTwoCharsOrMore(String emailAddress) {
		if (emailAddress == null)
			return false;
		StringTokenizer st = new StringTokenizer(emailAddress, ".");
		String lastToken = null;
		while (st.hasMoreTokens()) {
			lastToken = st.nextToken();
		}

		if ((lastToken != null) && (lastToken.length() >= 2)) {
			return true;
		} else {
			return false;
		}
	}
}
