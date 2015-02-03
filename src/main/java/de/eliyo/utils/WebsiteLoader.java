package de.eliyo.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.ejb.Singleton;

import org.apache.commons.io.IOUtils;

@Singleton
public class WebsiteLoader {

	private String websiteUrl;
	private String body;
	private boolean previouslyLoaded;

	public WebsiteLoader load(String websiteUrl) {
		if (websiteUrl.equals(this.websiteUrl))
			previouslyLoaded = true;
		else
			previouslyLoaded = false;
		this.websiteUrl = websiteUrl;
		return this;
	}

	public String getBody() throws Exception {
		if(previouslyLoaded)
			return body;
			
		URL url = new URL(websiteUrl);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;
		body = IOUtils.toString(in, encoding);
		return body;
	}
}
