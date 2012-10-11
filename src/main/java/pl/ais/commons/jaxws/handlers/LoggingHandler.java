package pl.ais.commons.jaxws.handlers;

import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NodeList;

/**
 * Logging message using Commons Logging library.
 *
 * Using <code>pl.ais.commons.jaxws.handlers.LoggingHandler</code> as logging category.
 * Provides ability to configure elements, that should have masked content.
 */
public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {

  private final Log log = LogFactory.getLog(LoggingHandler.class.getName());

  private String[] maskedElements = {};

  @Override
  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }

  @Override
  public boolean handleMessage(SOAPMessageContext messageContext) {
    Boolean outboundProperty = (Boolean) messageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

    String header = null;
    if (outboundProperty.booleanValue()) {
      header = "Outbound message: ";
    } else {
      header = "Inbound message: ";
    }

    if (log.isInfoEnabled()) {
      log.info(header + prepareLogMessage(messageContext.getMessage()));
    }

    return true;
  }

  public void setMaskedElements(List<String> maskedElements) {
    this.maskedElements = maskedElements.toArray(new String[maskedElements.size()]);
  }

  @Override
  public boolean handleFault(SOAPMessageContext context) {
    return true;
  }

  @Override
  public void close(MessageContext context) {

  }

  public String prepareLogMessage(SOAPMessage msg) {
    try {
      SOAPPart soapPart = msg.getSOAPPart();

      Source source = null;
      if (maskedElements.length > 0) {
        SOAPPart clonnedSoapPart = (SOAPPart) soapPart.cloneNode(true);
        SOAPEnvelope envelope = clonnedSoapPart.getEnvelope();
        for (String tag : maskedElements) {
          NodeList nodeList = envelope.getElementsByTagName(tag);
          for (int i = 0; i < nodeList.getLength(); i++) {
            nodeList.item(i).setTextContent("***");
          }
        }
        source = clonnedSoapPart.getContent();
      } else {
        source = soapPart.getContent();
      }
      StringWriter stringWriter = new StringWriter();
      Result result = new StreamResult(stringWriter);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.transform(source, result);
      return (stringWriter.getBuffer().toString());

    } catch (Exception e) {
      log.fatal("Exception during logging message", e);
    }
    return null;
  }
}
