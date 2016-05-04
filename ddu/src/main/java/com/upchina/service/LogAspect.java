package com.upchina.service;

import java.lang.reflect.Method;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;

import com.upchina.model.ActionLog;
import com.upchina.util.JacksonUtil;
import com.upchina.vo.rest.input.NoteInVo;

@Aspect
public class LogAspect {

	@Pointcut("execution( * com.upchina.*.NoteService.add*(..))")
	public void insertPointcut() {

	}

	@AfterReturning(pointcut = "insertPointcut()")
	public void insertLogInfo(JoinPoint joinPoint) throws Exception {
		String methodName = joinPoint.getSignature().getName();
		joinPoint.getStaticPart();
		joinPoint.getThis();
		// 获取操作内容
		String jsonObject = JSONObject.valueToString(joinPoint.getArgs());
		List<NoteInVo> noteInVos =  (List<NoteInVo>) JacksonUtil.jsonToList(jsonObject, NoteInVo.class);
		for (NoteInVo noteInVo : noteInVos) {
			ActionLog actionLog = new ActionLog();
//			actionLog.setModuleId(moduleId);
			actionLog.setUserId(noteInVo.getUserId());
		}
	}

	public String adminOptionContent(Object[] args, String mName) throws Exception {

		if (args == null) {
			return null;
		}

		StringBuffer rs = new StringBuffer();
		rs.append(mName);
		String className = null;
		int index = 1;
		// 遍历参数对象
		for (Object info : args) {

			// 获取对象类型
			className = info.getClass().getName();
			className = className.substring(className.lastIndexOf(".") + 1);
			//rs.append("[参数" + index + "，类型：" + className + "，值：");

			// 获取对象的所有方法
			Method[] methods = info.getClass().getDeclaredMethods();

			// 遍历方法，判断get方法
			for (Method method : methods) {

				String methodName = method.getName();
				// 判断是不是get方法
				if (methodName.indexOf("get") == -1) {// 不是get方法
					continue;// 不处理
				}

				Object rsValue = null;
				try {

					// 调用get方法，获取返回值
					rsValue = method.invoke(info);

					if (rsValue == null) {// 没有返回值
						continue;
					}

				} catch (Exception e) {
					continue;
				}

				// 将值加入内容中
				rs.append("(" + methodName + " : " + rsValue + ")");
			}

			rs.append("]");

			index++;
		}

		return rs.toString();
	}
}
