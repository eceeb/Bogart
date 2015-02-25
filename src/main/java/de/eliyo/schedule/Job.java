package de.eliyo.schedule;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import de.eliyo.entity.Wanted;
import de.eliyo.service.WantedService;
import de.eliyo.utils.WebsiteLoader;
import de.eliyo.utils.MailSender;
import de.eliyo.utils.Crawler;

@Singleton
public class Job {

	@Inject Crawler search;
	@Inject MailSender mail;
	@Inject	WantedService service;
	@Inject WebsiteLoader website; 

	private static final Logger logger = Logger.getLogger( Job.class.getName() );
	
	@Schedule(minute = "*/10", hour = "*")
	public void doJob() {
		
		logger.log(Level.INFO, "starting job");
		
		for (Wanted w : getActiveSearches()) {
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
	}


	@Schedule
	public void dailyJob() {
		logger.log(Level.INFO, "midnight test");
	}


	private void processResult(Wanted w, String body) throws Exception {
		if (search.in(body).after(w.getSeek())) {
			mail.notifySubsriber(w.getEmail(), w.getSeek(), w.getUrl());
			service.markAsFound(w);
		}
	}


	private List<Wanted> getActiveSearches() {
		
		List<Wanted> activeSearches;
		try {
			activeSearches = service.getAllWanted();
			logger.log( Level.INFO, "got {0} active searches from database", activeSearches.size() );
		} catch (Exception x){
			mail.notifyAdmin(x);
			logger.log( Level.SEVERE, x.toString(), x );
			return new ArrayList<Wanted>();
		}
		return activeSearches;
	}

}
