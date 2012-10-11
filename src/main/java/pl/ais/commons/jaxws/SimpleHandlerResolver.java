package pl.ais.commons.jaxws;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * Provides injected list of handlers regardless of {@link PortInfo}.
 */
public class SimpleHandlerResolver implements HandlerResolver {

  private List<Handler> handlers;

  @Override
  public List<Handler> getHandlerChain(PortInfo arg0) {
    return handlers;
  }

  public void setHandlers(List<Handler> handlers) {
    this.handlers = handlers;
  }
}
