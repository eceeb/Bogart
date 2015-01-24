package de.eliyo.main;

import java.io.File;


import org.apache.catalina.startup.Tomcat;
import org.apache.tomee.embedded.EmbeddedTomEEContainer;

public class Launcher {

	  public static void main(String[] args) throws Exception {
		  
	        String webappDirLocation = "src/main/webapp/";
	        
	        Tomcat appServer = new Tomcat();
	        EmbeddedTomEEContainer.createEJBContainer();

	        String webPort = System.getenv("PORT");
	        if(webPort == null || webPort.isEmpty())
	            webPort = "8080";

	        appServer.setPort(Integer.valueOf(webPort));
	        appServer.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
	        appServer.getServer().await();
	    }

}
