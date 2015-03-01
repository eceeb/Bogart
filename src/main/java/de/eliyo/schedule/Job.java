package de.eliyo.schedule;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import de.eliyo.entity.Wanted;
import de.eliyo.service.WantedService;
import de.eliyo.utils.WebsiteLoader;
import de.eliyo.utils.MailSender;
import de.eliyo.utils.Crawler;

@Stateless
public class Job {

	@Inject Crawler search;
	@Inject MailSender mail;
	@Inject	WantedService service;
	@Inject WebsiteLoader website; 

	private static final Logger logger = Logger.getLogger( Job.class.getName() );
	
	
	@Schedule(minute = "*/10", hour = "*")
	public void doJob() {
		
		for (Wanted w : getActiveSearchesForInterval(10)) {
			searchFor(w);
		}
	}


	@Schedule
	public void dailyJob() {
		for (Wanted w : getActiveSearchesForInterval(1440)) {
			searchFor(w);
		}
	}
	
	
	private synchronized List<Wanted> getActiveSearchesForInterval(int minutes) {
		
		List<Wanted> activeSearches;
		try {
			activeSearches = service.getAllWantedForInterval(minutes);
			logger.log( Level.INFO, "got {0} active searches for {1} minutes interval", 
					new Object[]{activeSearches.size(), String.valueOf(minutes)});
		} catch (Exception x){
			mail.notifyAdmin(x);
			logger.log( Level.SEVERE, x.toString(), x );
			return new ArrayList<Wanted>();
		}
		return activeSearches;
	}
	
	
	private void searchFor(Wanted w) {
		try {
			String body = website.load(w.getUrl()).getBody();
			logger.log( Level.INFO, "searching for {0} on {1}", new Object[]{w.getSeek(), w.getUrl()});
			processResult(w, body);
		} catch (MalformedURLException x){
			logger.log( Level.SEVERE, x.toString(), x );
		} catch (Exception x) {
			mail.notifyAdmin(x);
			logger.log( Level.SEVERE, x.toString(), x );
		}
	}


	private void processResult(Wanted w, String body) throws Exception {
		if (search.in(body).after(w.getSeek())) {
			mail.notifySubsriber(w.getEmail(), w.getSeek(), w.getUrl());
			service.markAsFound(w);
		}
	}

}
