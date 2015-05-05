package de.eliyo.utils;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.auth.AuthDescriptor;

public class SearchCache {
	
	private MemcachedClient mc;
	private AuthDescriptor ad;
	
	public SearchCache() {
		String user = System.getenv("MEMCACHE_USER");
		String pass = System.getenv("MEMCACHE_PASS");
				
		ad = new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler(user, pass));
		try {
			mc = new MemcachedClient(
					new ConnectionFactoryBuilder()
							.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
							.setAuthDescriptor(ad).build(),	AddrUtil.getAddresses("mc3.dev.eu.ec2.memcachier.com:11211"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void store(String websiteUrl, String body){
		mc.set(websiteUrl, 60, body);
	}
	
	public String load(String websiteUrl) {
		return (String) mc.get(websiteUrl);
	}
}