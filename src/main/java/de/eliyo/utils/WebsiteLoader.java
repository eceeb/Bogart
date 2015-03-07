package de.eliyo.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.apache.commons.io.IOUtils;

@Stateless
public class WebsiteLoader {

	private String body;
	private String websiteUrl;

	private static final Logger logger = Logger.getLogger( WebsiteLoader.class.getName() );

	public WebsiteLoader load(String websiteUrl) {
		if (previouslyLoaded(websiteUrl))
			return this;

		this.websiteUrl = websiteUrl;
		body = loadBody();

		return this;
	}

	private String loadBody() {
		try {
			URL url = new URL(websiteUrl);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			body = IOUtils.toString(in, encoding);
		} catch (Exception x) {
			logger.log( Level.SEVERE, x.toString(), x );
		}
		return body;
	}

	private boolean previouslyLoaded(String websiteUrl) {
		return websiteUrl.equals(this.websiteUrl);
	}

	public String getBody() {
		return body;
	}
}
