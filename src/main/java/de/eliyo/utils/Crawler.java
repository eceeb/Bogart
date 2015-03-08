package de.eliyo.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

@Stateless
public class Crawler {

	private String body;
	private static final Logger logger = Logger.getLogger( Crawler.class.getName() );
	
	public Crawler in(String body) {
		this.body = body;
		return this;
	}

	public boolean after(String searchString) {
		if (StringUtils.containsIgnoreCase(body, searchString))
			return true;

		logger.log( Level.INFO, "#### nothing found for {0}", searchString );
		return false;
	}
}
