package br.usp.ccsl.test.ws;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
@HandlerChain(file = "handler-chain.xml")
public class SampleWS {
	
	@WebMethod
	public String test(){
		return "Vaca";
	}

}
