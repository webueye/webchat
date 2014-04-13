package net.ueye.webchat.server;

import java.io.File;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

/**
 * @author rubys
 * @since 2012-3-27
 */
public class WebChatEmbedTomcatServer {

	public static void main(String[] args) throws Exception {

		Connector httpsConnector = new Connector();
		httpsConnector.setScheme("https");
		httpsConnector.setSecure(true);
		httpsConnector.setPort(8086);

		httpsConnector.setAttribute("keyAlias", "tomcat-embed");

		httpsConnector.setAttribute("keystorePass", "tomcat-embed");
		String keystoreFile = WebChatEmbedTomcatServer.class.getResource("tomcat-embed.key").getFile();
		httpsConnector.setAttribute("keystoreFile", keystoreFile);

		httpsConnector.setAttribute("clientAuth", "false");
		httpsConnector.setAttribute("sslProtocol", "TLS");
		httpsConnector.setAttribute("SSLEnabled", true);

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8081);

		tomcat.getService().addConnector(httpsConnector);

		// Add AprLifecycleListener
		AprLifecycleListener aprLifecycleListener = new AprLifecycleListener();
		StandardServer server = (StandardServer) tomcat.getServer();
		server.addLifecycleListener(aprLifecycleListener);

		tomcat.addWebapp("/webchat", new File("src/webapp").getAbsolutePath());

		tomcat.start();
		server.await();

	}

}
