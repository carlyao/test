////------------------------------------------------------------------------------
////
//// Copyright (c) 2007 CoreObjects Software, Inc. All Rights Reserved.
////
//// $Id: AuditInterceptor.java Oct 15, 2007 8:13:01 PM neeravk$
////
////------------------------------------------------------------------------------
//
//package com.augmentum.masterchef.util;
//
//import java.io.Serializable;
//import java.util.Date;
//
//import org.hibernate.EmptyInterceptor;
//import org.hibernate.Transaction;
//
//import com.augmentum.masterchef.data.AbstractAuditedModel;
//
///**
// * AuditInterceptor - Interceptor that adds audit info to the Audited objects.
// */
//public class AuditInterceptor extends EmptyInterceptor {
//    /**
//     * Unique Serial Version UID.
//     */
//    private static final long serialVersionUID = 1L;
//    
//    private static ThreadLocal<String> ipAddress = new ThreadLocal<String>();
//    private static ThreadLocal<String> currentUserId = new ThreadLocal<String>();
//
//    /**
//     * {@inheritDoc}
//     */
//    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames,
//            Type[] types) {
//        // do nothing
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
//            Object[] previousState, String[] propertyNames, Type[] types) {
//        boolean result = false;
//
//        if (entity instanceof AbstractAuditedModel) {
//            for (int i = 0; i < propertyNames.length; i++) {
//                if ("modifiedDate".equals(propertyNames[i])) {
//                    currentState[i] = new Date();
//                    result = true;
//                } else if ("ip".equals(propertyNames[i])) {
//                    currentState[i] = ipAddress.get();
//                    result = true;
////                } else if ("modifiedBy".equals(propertyNames[i])) {
////                     currentState[i] = currentUserId.get();
////                     result = true;
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames,
//            Type[] types) {
//        return false;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames,
//            Type[] types) {
//        boolean result = false;
//
//        if (entity instanceof AbstractAuditedModel) {
//            for (int i = 0; i < propertyNames.length; i++) {
//                if ("createdDate".equals(propertyNames[i]) && state[i]==null) {
//                    state[i] = new Date();
//                    result = true;
////                } else if ("createdBy".equals(propertyNames[i])) {
////                    // state[i] = getUser();
////                    // result = true;
//                } else if ("modifiedDate".equals(propertyNames[i])) {
//                    state[i] = new Date();
//                    result = true;
////                } else if ("modifiedBy".equals(propertyNames[i]) && state[i]==null) {
////                     state[i] = currentUserId.get();
////                     result = true;
//                } else if ("ip".equals(propertyNames[i])) {
//                    state[i] = ipAddress.get();
//                    result = true;
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    public void afterTransactionCompletion(Transaction tranx) {
//    }
//
//    public static ThreadLocal<String> getIpAddress() {
//        return ipAddress;
//    }
//
//    public static void setIpAddress(ThreadLocal<String> ipAddress) {
//        AuditInterceptor.ipAddress = ipAddress;
//    }
//
//	public static ThreadLocal<String> getCurrentUserId() {
//		return currentUserId;
//	}
//
//	public static void setCurrentUserId(ThreadLocal<String> currentUserId) {
//		AuditInterceptor.currentUserId = currentUserId;
//	}
//}