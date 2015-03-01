package de.eliyo.main;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomee.embedded.EmbeddedTomEEContainer;

public class Launcher {

	  public static void main(String[] args) throws Exception {
		  
	        Tomcat appServer = new Tomcat();
	        EmbeddedTomEEContainer.createEJBContainer();

	        String webPort = System.getenv("PORT");
	        if(webPort == null || webPort.isEmpty())
	            webPort = "8080";

	        appServer.setPort(Integer.valueOf(webPort));
	        appServer.getServer().await();
	    }

}
