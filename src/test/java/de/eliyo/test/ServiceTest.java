package de.eliyo.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.eliyo.entity.Wanted;
import de.eliyo.service.WantedService;
import de.eliyo.utils.Crawler;
import de.eliyo.utils.WebsiteLoader;

public class ServiceTest {

	
	// TODO: close connections somehow
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void database_Access_Works() throws Exception {
		assertTrue(new WantedService().getAllWantedForInterval(10).size() >= 0);
	}
	
	@Test
	public void search_is_Successfull() throws Exception {
		String body = new WebsiteLoader().load("http://google.de").getBody();
		boolean found = new Crawler().in(body).after("a");
		assertTrue(found);
	}

}
