package de.eliyo.utils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
		cache = new SearchCache();
		String body = cache.load(websiteUrl);
		if (body != null){
			logger.log(Level.INFO, "#### found in cache: " + websiteUrl);
			return body;
		}
		try {
			URL url = new URL(websiteUrl);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			body = IOUtils.toString(in, encoding);
			cache.store(websiteUrl, body);
		} catch (Exception x) {
			logger.log( Level.SEVERE, x.toString(), x );
		}
		return body;
	}
}
