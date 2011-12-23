package br.usp.ccsl.test.ws;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import br.usp.ccsl.test.handler.ContentSetter;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class SampleWS {
	
	@WebMethod
	public String test(){
		String bodyContent = "<ns2:testResponse xmlns:ns2=\"http://ws.test.ccsl.usp.br/\">" +
				"<return>Vaca</return>" +
				"</ns2:testResponse>";
		ContentSetter.setBodyContent(bodyContent);
		
		return "This won't be sent";
	}

}
