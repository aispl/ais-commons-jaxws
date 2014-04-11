ais-commons-jaxws
=================

Utility classes for JaxWS-based applications.

SimpleHandlerResolver
---------------------

`SimpleHandlerResolver` allows to configure list of `javax.xml.ws.handler.Handler` instances.

Sample usage:

    <bean id="handlerResolver" class="pl.ais.commons.jaxws.SimpleHandlerResolver">
      <property name="handlers">
        <list>
          <bean class="class.implementing.javax.xml.ws.handler.Handler"/>
          <bean class="class,implementing.javax.xml.ws.handler.Handler"/>
        </list>
      </property>
    </bean>

    <bean id="webServiceClient" class="org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean">
      <property name="serviceInterface" value="generated.from.wsdl.interface.WebServicePortType" />

      <!-- those values are defined in WSDL -->
      <property name="serviceName" value="WebService" />
      <property name="namespaceUri" value="namespace.defined.in.wsdl"/>
      <property name="wsdlDocumentUrl" value="classpath:location.of.wsdl.in.classpath" />
      <property name="portName" value="WebServiceHttpPort"/>

      <property name="handlerResolver" ref="handlerResolver" />

      <!-- environment-specific entries -->
      <property name="endpointAddress" ref="name.of.bean.contains.url.of.endpoint" />
      <!-- optional, for HTTP Basic authentication
      <property name="username"        ref="name.of.bean.contains.basic.http.username" />
      <property name="password"        ref="name.of.bean.contains.basic.http.password" />
      -->
    </bean>


LoggingHandler
--------------

Sample usage of `LoggingHandler`:

    <bean id="handlerResolver" class="pl.ais.commons.jaxws.SimpleHandlerResolver">
      <property name="handlers">
        <list>
          <bean class="pl.ais.commons.jaxws.handlers.LoggingHandler"/>
        </list>
      </property>
    </bean>

SecurityHandler
---------------

Sample usage of `SecurityHandler`:

    <bean id="handlerResolver" class="pl.ais.commons.jaxws.SimpleHandlerResolver">
      <property name="handlers">
        <list>
          <bean class="pl.ais.commons.jaxws.handlers.SecurityHandler">
            <property name="username"  ref="name.of.bean.contains.wsse.username"/>
            <property name="password"  ref="name.of.bean.contains.wsse.password"/>
          </bean>
        </list>
      </property>
    </bean>
