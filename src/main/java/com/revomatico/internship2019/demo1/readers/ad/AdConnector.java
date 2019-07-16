package com.revomatico.internship2019.demo1.readers.ad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import com.revomatico.internship2019.demo1.readers.EventsConnector;
import io.vavr.collection.List;
import net.tirasa.connid.bundles.ad.ADConfiguration;
import net.tirasa.connid.bundles.ad.ADConnector;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import net.tirasa.connid.bundles.ldap.search.LdapSearch;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.api.APIConfiguration;
import org.identityconnectors.framework.api.ConnectorFacade;
import org.identityconnectors.framework.api.ConnectorFacadeFactory;
import org.identityconnectors.framework.common.objects.AttributeBuilder;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.framework.common.objects.ResultsHandler;
import org.identityconnectors.framework.common.objects.filter.Filter;
import org.identityconnectors.framework.common.objects.filter.FilterBuilder;
import org.identityconnectors.test.common.TestHelpers;
import org.identityconnectors.test.common.ToListResultsHandler;
import org.springframework.boot.ApplicationArguments;

public class AdConnector implements EventsConnector {
  private static final ADConfiguration config = createConfig("OU=Roweb,DC=example,DC=com", "WIN-0IO1D0UTKLH.example.com", 389,
      "<password>", "CN=midPoint Admin,CN=Users,DC=example,DC=com", false);

  @Override
  public List<List<String>> readEvents() {
    ConnectorFacade connector = createConnection(config);

    Filter filter = null;// LdapFilter.forNativeFilter("(objectClass=user)");
    ToListResultsHandler handler = new ToListResultsHandler();
    final OperationOptionsBuilder oob = new OperationOptionsBuilder();
    oob.setReturnDefaultAttributes(true);
    final List<String> header = List.of(
        "dn,cn,sn,givenName,distinguishedName,displayName,name,objectGUID,userAccountControl,badPwdCount,badPasswordTime,pwdLastSet,accountExpires,logonCount,sAMAccountName,sAMAccountType,userPrincipalName,member,memberOf"
            .split(","));
    oob.setAttributesToGet(header.toJavaArray(String.class));
    connector.search(ObjectClass.GROUP, filter, handler, oob.build());
    connector.search(ObjectClass.ACCOUNT, filter, handler, oob.build());
    List<ConnectorObject> all = List.ofAll(handler.getObjects());
    return List.of(List.of("DEFAULT1","DEFAULT2").appendAll(header)).appendAll(List.ofAll(all)
        .map(x -> List.of(toString(x.getAttributeByName("distinguishedName")), toString(x.getAttributeByName("userPrincipalName")))
            .appendAll(header.map(key->x.getAttributeByName(key)).map(y -> toString(y)))));
  }

  private ConnectorFacade createConnection(ADConfiguration config) {
    final ConnectorFacadeFactory factory = ConnectorFacadeFactory.getInstance();
    final APIConfiguration impl = TestHelpers.createTestConfiguration(ADConnector.class, config);
    impl.getResultsHandlerConfiguration().setFilteredResultsHandlerInValidationMode(true);
    ConnectorFacade connector = factory.newInstance(impl);
    connector.test();
    return connector;
  }

  public String toString(org.identityconnectors.framework.common.objects.Attribute at) {
    if (at == null || at.getValue() == null)
      return "-";
    String res = at.getValue().toString();
    res = res.substring(1,res.length()-1);
    return res;
  }

  private static ADConfiguration createConfig(String baseContext, String host, int port, String credentials, String principal,
      boolean readSchema) {
    final ADConfiguration config = new ADConfiguration();
    config.setSsl(false);
    config.setUidAttribute("sAMAccountName");
    config.setGidAttribute("sAMAccountName");
    config.setDefaultPeopleContainer(baseContext);
    config.setDefaultGroupContainer(baseContext);
    config.setObjectClassesToSynchronize("user");
    config.setAccountObjectClasses("top", "person", "organizationalPerson", "user");
    config.setGroupBaseContexts(config.getDefaultGroupContainer());
    config.setRetrieveDeletedUser(false);
    config.setTrustAllCerts(true);
    config.setMembershipsInOr(true);
    config.setUserSearchScope("subtree");
    config.setGroupSearchScope("subtree");
    config.setHost(host);
    config.setPort(port);
    config.setUserBaseContexts(baseContext);
    config.setPrincipal(principal);
    config.setCredentials(new GuardedString(credentials.toCharArray()));
    config.setReadSchema(readSchema);
    // config.setConnectTimeout(30 * 1000);
    // config.setReadTimeout(30 * 1000);
    return config;
  }
}
