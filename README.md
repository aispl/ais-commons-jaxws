ais-commons-jaxws
=================

Utility classes for JaxWS-based applications.

LoggingHandler
--------------

Sample usage of LoggingHandler with Spring support for JaxWS:

    <bean id="handlerResolver" class="pl.ais.commons.jaxws.SimpleHandlerResolver">
      <property name="handlers">
        <list>
          <bean class="pl.ais.commons.jaxws.handlers.LoggingHandler"/>
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

      <!-- environment-specific entires -->
      <property name="endpointAddress" ref="name.of.bean.contains.url.of.endpoint" />
      <!-- optional, for HTTP Basic authentication
      <property name="username"        ref="name.of.bean.contains.basic.http.username" />
      <property name="password"        ref="name.of.bean.contains.basic.http.password" />
      -->
    </bean>
