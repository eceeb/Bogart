package de.eliyo.utils;

import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

@Stateless
public class WebsiteLoader {

	private static final Logger logger = Logger.getLogger( WebsiteLoader.class.getName() );
	
	@Inject SearchCache cache;

	public String loadBody(String websiteUrl) {
		String body = cache.load(websiteUrl);
		if (body != null){
			logger.log(Level.INFO, "#### found in cache: " + websiteUrl);
			return body;
		}
		
		try {
			URLConnection con = new URL(websiteUrl).openConnection();
			String encoding = getWebsiteEncoding(con.getContentEncoding());
			body = IOUtils.toString(con.getInputStream(), encoding);
			cache.store(websiteUrl, body);
		} catch (Exception x) {
			logger.log( Level.SEVERE, x.toString(), x );
		}
		return body;
	}

	private String getWebsiteEncoding(String encoding) {
		if(encoding == null || !Charset.availableCharsets().containsKey(encoding))
			encoding = "UTF-8";
		return encoding;
	}
}
