package com.geccocrawler.gecco.downloader;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBeanContext;
import com.geccocrawler.gecco.spider.SpiderThreadLocal;

/**
 * 获得当前线程，正在抓取的SpiderBean的下载器
 * 
 * @author huchengyi
 *
 */
public class DownloaderContext {
	
	public static HttpResponse download(HttpRequest request) throws DownloadException {
		SpiderBeanContext context = SpiderThreadLocal.get().getSpiderBeanContext();
		BeforeDownload before = null;
		AfterDownload after = null;
		int timeout = 1000;
		if(context != null) {
			before = context.getBeforeDownload();
			after = context.getAfterDownload();
			timeout = context.getTimeout();
		}
		if (before != null) {
			before.process(request);
		}
		HttpResponse response = context.getDownloader().download(request, timeout);
		if (after != null) {
			after.process(request, response);
		}
		return response;
	}

}
