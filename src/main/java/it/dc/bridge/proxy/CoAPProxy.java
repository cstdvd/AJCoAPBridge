package it.dc.bridge.proxy;

import java.util.logging.Logger;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.Request;

import it.dc.bridge.rd.ResourceDirectory;

public class CoAPProxy implements Runnable {

	/* the logger */
	private static final Logger LOGGER = Logger.getGlobal();
	
	private static final CoAPProxy proxy = new CoAPProxy();
	
	/*
	 * Since the CoAPProxy is a singleton,
	 * the constructor must be private.
	 */
	private CoAPProxy() {}
	
	/**
	 * The CoAP Proxy is a Singleton.
	 * This method returns the class instance.
	 * 
	 * @return the class instance
	 */
	public static CoAPProxy getInstance() {
		
		return proxy;
		
	}
	
	/**
	 * Obtains the context associated to the specific resource path and sends
	 * a specific request message to the CoAP Server.
	 * 
	 * @param RDPath the resource path inside the RD
	 * @param request the request message
	 * @return the response message
	 */
	public static CoapResponse callMethod(final String RDPath, final Request request) {
		
		// take the node context from the RD (the path is unique within the RD)
		String context = ResourceDirectory.getInstance().getContextFromResource(RDPath);
		
		// take the resource path within the CoAP Server from the RD
		String path = ResourceDirectory.getInstance().getResourcePath(RDPath);
		
		CoapClient client = new CoapClient(context+path);
		
		CoapResponse response = client.advanced(request);
		if (response==null) {
			LOGGER.warning("No response received.");
		}
		
		return response;
		
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
