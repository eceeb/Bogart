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
		ad = new AuthDescriptor(new String[] { "PLAIN" }, new PlainCallbackHandler("a1204c", "dd38af880d"));
		try {
			mc = new MemcachedClient(
					new ConnectionFactoryBuilder()
							.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
							.setAuthDescriptor(ad).build(),	AddrUtil.getAddresses("mc3.dev.ec2.memcachier.com:11211"));
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