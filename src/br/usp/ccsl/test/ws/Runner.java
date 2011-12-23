package br.usp.ccsl.test.ws;

import javax.xml.ws.Endpoint;

public class Runner {
	
	public static void main(String [] args){
		   SampleWS service = new SampleWS();
		   Endpoint endpoint = Endpoint.create(service);
		   endpoint.publish("http://192.168.1.103:1234/sample");
		}
}
