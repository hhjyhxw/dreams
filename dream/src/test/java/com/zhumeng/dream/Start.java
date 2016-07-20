package com.zhumeng.dream;
import org.mortbay.jetty.Server;
import com.zhumeng.dream.util.others.JettyUtils;

public class Start {
	public static final int PORT = 8081;
	public static final String CONTEXT = "/";
	public static final String BASE_URL = "http://localhost:"+PORT+CONTEXT;
	
	
	public static void main(String[] args) throws Exception {
		Server server = JettyUtils.buildNormalServer(PORT, CONTEXT);
		server.start();
		System.out.println(BASE_URL);
		System.out.println(BASE_URL+"/bms");
		System.out.println("Hit Enter in console to stop server");
		if (System.in.read() != 0) {
			server.stop();
			System.out.println("Server stopped");
		}
	}
}
