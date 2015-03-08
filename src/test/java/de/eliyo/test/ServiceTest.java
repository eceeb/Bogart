package de.eliyo.test;

import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.eliyo.service.WantedService;

public class ServiceTest {

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

//	@Test
//	public void search_is_Successfull() throws Exception {
//		String body = new WebsiteLoader().loadBody("http://google.de");
//		boolean found = new Crawler().in(body).after("a");
//		assertTrue(found);
//	}

}
