package com.augmentum.common.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BeanUtils {
    private static final Log LOG = LogFactory.getLog(BeanUtils.class);

    protected static final String PRIMARY_KEY = "PrimaryKey";

    public static void setData(Object fromObj, Object toObj) {
        if ((fromObj != null) && (toObj != null)) {
            Class thisClass = toObj.getClass();
            Class dataClass = fromObj.getClass();
            Method[] methods = dataClass.getMethods();

            if ((methods != null) && (methods.length > 0)) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    String methodName = method.getName();

                    if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
                        String propName = null;
                        if (methodName.startsWith("get"))
                            propName = methodName.substring(3);
                        else
                            propName = methodName.substring(2);

                        // get method value. If error, log and set value to null.
                        Object getValue = null;
                        try {
                            getValue = method.invoke(fromObj, null);
                        } catch (Exception e) {
                            // e.printStackTrace();
                            LOG.error("Could not invoke get method : " + methodName);
                        }

                        // get return type
                        Class returnType = method.getReturnType();

                        // set parameter type of set method
                        Class[] paramTypes = new Class[1];
                        paramTypes[0] = returnType;

                        // get set method name
                        String setMethodName = "set" + propName;

                        // get set method and invoke it
                        try {
                            Method setMethod = thisClass.getMethod(setMethodName, paramTypes);

                            Object[] paramValues = new Object[1];
                            paramValues[0] = getValue;

                            // set property value to this class
                            setMethod.invoke(toObj, paramValues);
                        } catch (NoSuchMethodException nm_ex) {
                            // set method not found. Continue on
                            // LOG.debug("Set method not found : " + setMethodName);
                        } catch (Exception e) {
                            LOG.error("Could not invoke set method : " + setMethodName);
                        }
                    }
                }
            }
        }
    }

	public static void setDataForUpdate(Object fromObj, Object toObj) {
		setDataForUpdate(fromObj, toObj, true);
	}

	public static void setDataForUpdate(Object fromObj, Object toObj, boolean includeZeroes) {
		if ((fromObj != null) && (toObj != null)) {
			Class thisClass = toObj.getClass();
			Class dataClass = fromObj.getClass();
			Method[] methods = dataClass.getMethods();

			if ((methods != null) && (methods.length > 0)) {
				Object primaryKey = null;
				Method primaryKeyMethod = null;

				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					String methodName = method.getName();

					if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
						String propName = null;
						if (methodName.startsWith("get"))
							propName = methodName.substring(3);
						else
							propName = methodName.substring(2);

						// get method value. If error, log and set value to null.
						Object getValue = null;
						try {
							getValue = method.invoke(fromObj, null);
						} catch (Exception e) {
							// e.printStackTrace();
							LOG.error("Could not invoke get method : " + methodName);
						}

						// get return type
						Class returnType = method.getReturnType();

						// only if get method value is not null or empty, set to new object
						if ((getValue != null)
								&& (includeZeroes || !returnType.isPrimitive() || !getValue
										.toString().trim().equalsIgnoreCase("0"))) {

							if ((returnType.equals(String.class))
									&& (StringUtils.isEmpty((String) getValue))) {
								// do nothing
							} else {

								// set parameter type of set method
								Class[] paramTypes = new Class[1];
								paramTypes[0] = returnType;

								// get set method name
								String setMethodName = "set" + propName;

								// get set method and invoke it
								try {
									Method setMethod = thisClass.getMethod(setMethodName,
											paramTypes);

									// save primary key value and don't call set method
									if (propName.equals(PRIMARY_KEY)) {
										try {
											primaryKey = method.invoke(toObj, null);
											primaryKeyMethod = setMethod;

										} catch (Exception e) {
											// e.printStackTrace();
											LOG
													.error("Could not invoke get method : "
															+ methodName);
										}
									}

									Object[] paramValues = new Object[1];
									paramValues[0] = getValue;

									// set property value to this class
									setMethod.invoke(toObj, paramValues);
								} catch (NoSuchMethodException nm_ex) {
									// set method not found. Continue on
									// LOG.debug("Set method not found : " + setMethodName);
								} catch (Exception e) {
									LOG.error("Could not invoke set method : " + setMethodName);
								}
							}
						}
					}
				}

				// set primary key with old primary key. We do this instead of just not calling
				// the set method on the primary key method because the primary key can also
				// be set via the propery method itself.
				// save primary key value
				if (primaryKeyMethod != null) {
					try {

						Object[] paramValues = new Object[1];
						paramValues[0] = primaryKey;

						// set property value to this class
						primaryKeyMethod.invoke(toObj, paramValues);
					} catch (Exception e) {
						LOG.error("Could not invoke set method : PrimaryKey");
					}
				}
			}
		}
	}

    public static void setDataForUpdateWithNulls(Object fromObj, Object toObj) {
        if ((fromObj != null) && (toObj != null)) {
            Class thisClass = toObj.getClass();
            Class dataClass = fromObj.getClass();
            Method[] methods = dataClass.getMethods();

            if ((methods != null) && (methods.length > 0)) {
                Object primaryKey = null;
                Method primaryKeyMethod = null;

                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    String methodName = method.getName();

                    if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
                        String propName = null;
                        if (methodName.startsWith("get"))
                            propName = methodName.substring(3);
                        else
                            propName = methodName.substring(2);

                        // get method value. If error, log and set value to null.
                        Object getValue = null;
                        try {
                            getValue = method.invoke(fromObj, null);
                        } catch (Exception e) {
                            // e.printStackTrace();
                            LOG.error("Could not invoke get method : " + methodName);
                        }

                        // get return type
                        Class returnType = method.getReturnType();

                        // set parameter type of set method
                        Class[] paramTypes = new Class[1];
                        paramTypes[0] = returnType;

                        // get set method name
                        String setMethodName = "set" + propName;

                        // get set method and invoke it
                        try {
                            Method setMethod = thisClass.getMethod(setMethodName, paramTypes);

                            // save primary key value and don't call set method
                            if (propName.equals(PRIMARY_KEY)) {
                                try {
                                    primaryKey = method.invoke(toObj, null);
                                    primaryKeyMethod = setMethod;

                                } catch (Exception e) {
                                    // e.printStackTrace();
                                    LOG.error("Could not invoke get method : " + methodName);
                                }
                            }

                            Object[] paramValues = new Object[1];
                            paramValues[0] = getValue;

                            // set property value to this class
                            setMethod.invoke(toObj, paramValues);
                        } catch (NoSuchMethodException nm_ex) {
                            // set method not found. Continue on
                            // LOG.debug("Set method not found : " + setMethodName);
                        } catch (Exception e) {
                            LOG.error("Could not invoke set method : " + setMethodName);
                        }
                    }
                }

                // set primary key with old primary key. We do this instead of just not calling
                // the set method on the primary key method because the primary key can also
                // be set via the propery method itself.
                // save primary key value
                if (primaryKeyMethod != null) {
                    try {

                        Object[] paramValues = new Object[1];
                        paramValues[0] = primaryKey;

                        // set property value to this class
                        primaryKeyMethod.invoke(toObj, paramValues);
                    } catch (Exception e) {
                        LOG.error("Could not invoke set method : PrimaryKey");
                    }
                }
            }
        }
    }

    public static String toString(Object strObj) {
        if (strObj != null) {
            StringBuffer buf = new StringBuffer();
            boolean firstProp = true;

            Class clazz = strObj.getClass();
            Method[] methods = clazz.getMethods();

            if ((methods != null) && (methods.length > 0)) {
                for (int i = 0; i < methods.length; i++) {
                    Method method = methods[i];
                    String methodName = method.getName();

                    if ((methodName.startsWith("get")) || (methodName.startsWith("is"))) {
                        // if first property, then append class name and beginning
                        // bracket
                        if (firstProp) {
                            buf.append(clazz.getName() + "[ ");
                            firstProp = false;
                        }
                        // else continuation, so append comma
                        else {
                            buf.append(", ");
                        }

                        // invoke method. If error, log and set value to null.
                        Object obj = null;
                        try {
                            obj = method.invoke(strObj, null);
                        } catch (Exception e) {
                            LOG.error("Could not invoke method : " + methodName);
                        }

                        String propName = null;
                        if (methodName.startsWith("get"))
                            propName = methodName.substring(3);
                        else
                            propName = methodName.substring(2);

                        buf.append(propName + " : " + obj);
                    }
                }

                // check if there was any property methods at all. If so,
                // close bracket. Otherwise, return Object.toString()
                if (!firstProp)
                    buf.append(" ]");
                else
                    buf.append(strObj.toString());
            } else {
                buf.append(strObj.toString());
            }

            return buf.toString();
        } else {
            return null;
        }
    }

    public static int getValue(Integer obj) {
        int num = 0;

        if (obj != null)
            num = obj.intValue();

        return num;
    }

    public static double getValue(Double obj) {
        double num = 0.0d;

        if (obj != null)
            num = obj.doubleValue();

        return num;
    }

    public static double getDoubleValue(String str) {
        double num = 0.0;

        if (NumberUtils.isNumber(str))
            num = Double.parseDouble(str);

        return num;
    }

    public static boolean isModified(String original, String newValue) {
        boolean modified = false;

        if (((original == null) && (newValue != null))
                || ((original != null) && (newValue == null))) {
            modified = true;
        } else if ((original != null) && (newValue != null) && !original.equals(newValue)) {
            modified = true;
        } else if ((original == null) && (newValue == null)) {
            modified = true;
        }

        return modified;
    }

    public static boolean isEmpty(Collection col) {
        boolean empty = true;

        if ((col != null) && (col.size() > 0)) {
            empty = false;
        }

        return empty;
    }

    public static boolean isEmpty(Map map) {
        boolean empty = true;

        if ((map != null) && (map.size() > 0)) {
            empty = false;
        }

        return empty;
    }
}
