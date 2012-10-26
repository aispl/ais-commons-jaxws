package pl.ais.commons.jaxws.handlers;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * Adding <code>http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd</code>
 * headers for outbound messages.
 */
public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

  private static final Logger log = Logger.getLogger(SecurityHandler.class.getName());

  private String username;

  private String password;

  private static final String NAMESPACE = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
  private static final String PREFIX = "wsse";

  @Override
  public boolean handleMessage(SOAPMessageContext context) {
    Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
    SOAPMessage message = context.getMessage();
    if (!outboundProperty.booleanValue()) {
      return true;
    }
    try {
      SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
      SOAPHeader header = envelope.getHeader();
      if (header == null) {
        header = envelope.addHeader();
      }
      SOAPElement security = header.addChildElement("Security", PREFIX, NAMESPACE);
      SOAPElement usernameToken = security.addChildElement("UsernameToken", PREFIX);
      SOAPElement usernameElement = usernameToken.addChildElement("Username", PREFIX);
      usernameElement.addTextNode(username);
      SOAPElement passwordElement = usernameToken.addChildElement("Password", PREFIX);
      passwordElement.addTextNode(password);
    } catch (SOAPException e) {
      log.log(Level.WARNING, "exception during ws interaction", e);
    }
    return true;
  }

  @Override
  public Set<QName> getHeaders() {
    return Collections.emptySet();
  }

  @Override
  public void close(MessageContext mc) {
  }

  @Override
  public boolean handleFault(SOAPMessageContext arg0) {
    return false;
  }

  /**
   * Sets <code>Username</code>
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Sets <code>Password</code>
   */
  public void setPassword(String password) {
    this.password = password;
  }
}
