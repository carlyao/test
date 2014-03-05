package com.augmentum.common.util;

public class NumberUtil {
	public static double round(double num, double nearest) {
		if (nearest <= 0)
			return num;

		return Math.round(num / nearest) * nearest;
	}

	public static double roundUp(double num, double nearest) {
		if (nearest <= 0)
			return num;

		return Math.ceil((Math.round(num * 100) / 100) / nearest) * nearest;
	}

	public static int toIntZero(Long num) {
		if (num != null) {
			return num.intValue();
		} else {
			return 0;
		}
	}

	public static int toIntZero(Integer num) {
		if (num != null) {
			return num;
		} else {
			return 0;
		}
	}

	public static int toIntZero(Double num) {
		if (num != null) {
			return num.intValue();
		} else {
			return 0;
		}
	}

	public static int toIntZero(Float num) {
		if (num != null) {
			return num.intValue();
		} else {
			return 0;
		}
	}

	public static long toLongZero(Integer num) {
		if (num != null) {
			return new Long(num);
		} else {
			return 0;
		}
	}

	public static Integer toIntNullable(Long num) {
		if (num != null) {
			return num.intValue();
		} else {
			return null;
		}
	}

	public static Long toLongNullable(Integer num) {
		if (num != null) {
			return new Long(num);
		} else {
			return null;
		}
	}
}
