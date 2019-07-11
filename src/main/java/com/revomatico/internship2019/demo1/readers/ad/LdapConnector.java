package com.revomatico.internship2019.demo1.readers.ad;

import com.revomatico.internship2019.demo1.readers.EventsReader;
import io.vavr.collection.List;
import net.tirasa.connid.bundles.ldap.LdapConfiguration;
import net.tirasa.connid.bundles.ldap.LdapConnection;
import net.tirasa.connid.bundles.ldap.search.LdapFilter;
import net.tirasa.connid.bundles.ldap.search.LdapSearch;
import net.tirasa.connid.bundles.ldap.search.VlvIndexSearchStrategy;
import org.identityconnectors.common.security.GuardedString;
import org.identityconnectors.framework.common.objects.ConnectorObject;
import org.identityconnectors.framework.common.objects.ObjectClass;
import org.identityconnectors.framework.common.objects.OperationOptionsBuilder;
import org.identityconnectors.test.common.ToListResultsHandler;

public class LdapConnector implements EventsReader {
  private static final LdapConfiguration config = createConfig("dc=revomatico,dc=com", "172.16.201.3", 389, "<password>",
      "cn=admin,dc=revomatico,dc=com", false);

  @Override
  public List<List<String>> readEvents() {
    LdapConnection conn = new LdapConnection(config);

    LdapFilter filter = null;// LdapFilter.forNativeFilter("(objectClass=user)");
    ToListResultsHandler handler = new ToListResultsHandler();
    final OperationOptionsBuilder oob = new OperationOptionsBuilder();
    oob.setReturnDefaultAttributes(true);
    oob.setAttributesToGet(
        "dn,cn,sn,givenName,distinguishedName,displayName,name,objectGUID,userAccountControl,badPwdCount,badPasswordTime,pwdLastSet,accountExpires,logonCount,sAMAccountName,sAMAccountType,userPrincipalName,member,memberOf"
            .split(","));

    new LdapSearch(conn, ObjectClass.ACCOUNT, filter, handler, oob.build()).execute();
    new LdapSearch(conn, ObjectClass.GROUP, filter, handler, oob.build()).execute();
    // handler.getObjects().forEach(x->x.getAttributes());
    List<ConnectorObject> all = List.ofAll(handler.getObjects());
    return List.ofAll(all)
        .map(x -> List.of(toString(x.getAttributeByName("distinguishedName")), toString(x.getAttributeByName("userPrincipalName")))
            .appendAll(List.ofAll(x.getAttributes()).map(y -> toString(y))));
  }

  public String toString(org.identityconnectors.framework.common.objects.Attribute at) {
    if (at == null || at.getValue() == null)
      return "-";
    return at.getName()+"="+at.getValue().toString();
  }

  private static LdapConfiguration createConfig(String baseContext, String host, int port, String credentials, String principal,
      boolean readSchema) {
    // public static LdapConfiguration createConfig(final boolean readSchema) {
    LdapConfiguration config = new LdapConfiguration();
    config.setHost(host);
    config.setPort(port);
    config.setBaseContexts(baseContext);
    config.setPrincipal(principal);
    config.setCredentials(new GuardedString(credentials.toCharArray()));
    config.setReadSchema(readSchema);
    // config.setConnectTimeout(30 * 1000);
    // config.setReadTimeout(30 * 1000);
    return config;
  }
}
