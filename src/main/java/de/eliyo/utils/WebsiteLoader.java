package de.eliyo.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.ejb.Singleton;

import org.apache.commons.io.IOUtils;

@Singleton
public class WebsiteLoader {

	private String websiteUrl;

	public WebsiteLoader load(String websiteUrl) {
		this.websiteUrl = websiteUrl;
		return this;
	}

	public String getBody() throws Exception {
		URL url = new URL(websiteUrl);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "UTF-8" : encoding;

		return IOUtils.toString(in, encoding);
	}
}
