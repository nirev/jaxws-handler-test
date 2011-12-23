package br.usp.ccsl.test.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Element;

public class ContentSetter implements SOAPHandler<SOAPMessageContext> {
	String bodyContent = "<ns2:testResponse xmlns:ns2=\"http://ws.test.ccsl.usp.br/\">" +
						 "   <return>Vaca</return>" +
						 "</ns2:testResponse>";
	
	String content = "<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
			"   <S:Body>" +	bodyContent + "</S:Body>" +
			"</S:Envelope>";
	
	@Override
	public void close(MessageContext context) {

	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return false;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		//Inquire incoming or outgoing message. 
		boolean outbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY); 

		try { 
			if (outbound) { 
				// OUTBOUND 

				System.out.println("Direction=outbound (handleMessage)"); 
				SOAPMessage msg = context.getMessage();
				
				//context.setMessage(MessageFactory.newInstance().createMessage(
				//		null, new ByteArrayInputStream(content.getBytes())));

				// get SOAP-Part 
				SOAPPart sp = msg.getSOAPPart(); 

				//edit Envelope 
				SOAPEnvelope env = sp.getEnvelope(); 
				
				// add namespaces 
				//env.addNamespaceDeclaration(name, url); 

				// add the Header with additional Elements 
				//SOAPElement soapElement1 = env.addHeader().addHeaderElement(
				//		new QName(namespace_URI, local_part)); 
				//soapElement1.addTextNode("something"); 


				// get the SOAP-Body 
				SOAPBody body = env.getBody(); 
				
				body.removeContents();
				
				
				DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
						.newInstance();
				docBuilderFactory.setNamespaceAware(true);
								
				Element node = docBuilderFactory  
					    .newDocumentBuilder()
					    .parse(new ByteArrayInputStream(bodyContent.getBytes()))
					    .getDocumentElement();
				
				body.addChildElement(SOAPFactory.newInstance().createElement(node));
				
				// print SOAP-Message 
				dumpSOAPMessage(msg); 

			} else { 
				// INBOUND 
				System.out.println("Direction=inbound (handleMessage)"); 
				SOAPMessage msg = ((SOAPMessageContext) context).getMessage(); 
				dumpSOAPMessage(msg); 
			} 

		} catch (Exception e) { 

			//All other unhandled problems. 
			e.printStackTrace(); 
		} 
		return true;  
	}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	/**
	 * Returns the message encoding (e.g. utf-8)
	 *
	 * @param msg
	 * @return
	 * @throws javax.xml.soap.SOAPException
	 */ 
	private String getMessageEncoding(SOAPMessage msg) throws SOAPException { 
		String encoding = "utf-8"; 
		if (msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING) != null) { 
			encoding = msg.getProperty(SOAPMessage.CHARACTER_SET_ENCODING).toString(); 
		} 
		return encoding; 
	}  

	/**
	 * Dump SOAP Message to console
	 *
	 * @param msg
	 */ 
	private void dumpSOAPMessage(SOAPMessage msg) { 
		if (msg == null) { 
			System.out.println("SOAP Message is null"); 
			return; 
		} 
		System.out.println(""); 
		System.out.println("--------------------"); 
		System.out.println("DUMP OF SOAP MESSAGE"); 
		System.out.println("--------------------"); 
		try { 
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			msg.writeTo(baos); 
			System.out.println(baos.toString(getMessageEncoding(msg))); 
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
	}


}
