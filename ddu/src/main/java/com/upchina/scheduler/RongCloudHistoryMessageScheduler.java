package com.upchina.scheduler;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.util.GsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import com.upchina.util.DateFormat;
import com.upchina.util.ImagePathUtil;
import com.upchina.vo.rest.RongCloudHistoryMessageVo;

public class RongCloudHistoryMessageScheduler {

	public void load() throws Exception {
		String key = ImagePathUtil.getKey();
		String secret = ImagePathUtil.getSecret();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		String rongCloudMessagePath = ImagePathUtil.getRongCloudMessagePath();
		for (int i = 0; i < 24; i++) {
			c.set(Calendar.HOUR_OF_DAY, i);
			String date = DateFormat.GetDateFormat(c.getTime(), "yyyyMMddHH");
			SdkHttpResult result = ApiHttpClient.getMessageHistoryUrl(key, secret, date, FormatType.json);
			RongCloudHistoryMessageVo cloudHistoryMessageVo = (RongCloudHistoryMessageVo) GsonUtil.fromJson(
					result.toString(), RongCloudHistoryMessageVo.class);
			if (null != cloudHistoryMessageVo) {
				String url = cloudHistoryMessageVo.getResult().getUrl();
				// String dateTime = cloudHistoryMessageVo.getDate();
				if (null != url && !"".equals(url.trim())) {
					File f = new File(rongCloudMessagePath);
					if (!f.exists()) {// 文件路径不存在时，自动创建目录
						f.mkdir();
					}
					URL urlfile = new URL(url);
					URLConnection connection = urlfile.openConnection();
					InputStream in = connection.getInputStream();
					File zipFile = new File(f + System.getProperty("file.separator") + date + ".zip");
					if(zipFile.exists()){
						continue;
					}
					FileOutputStream os = new FileOutputStream(f + System.getProperty("file.separator") + date + ".zip");
					byte[] buffer = new byte[4 * 1024];
					int read;
					while ((read = in.read(buffer)) > 0) {
						os.write(buffer, 0, read);
					}
					os.close();
					in.close();
				}
			}
			// System.out.println(DateFormat.GetDateFormat(c.getTime(),"yyyyMMddHH"));
		}
	}

	public static void main(String[] args) throws Exception {
		RongCloudHistoryMessageScheduler rongCloudHistoryMessageScheduler = new RongCloudHistoryMessageScheduler();
		rongCloudHistoryMessageScheduler.load();
	}
}
