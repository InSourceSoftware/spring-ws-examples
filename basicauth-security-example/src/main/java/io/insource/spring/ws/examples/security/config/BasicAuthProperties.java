package io.insource.spring.ws.examples.security.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties("app.security.basic")
public class BasicAuthProperties {
    /**
     * HTTP basic realm name.
     */
    private String realm = "Spring";

    /**
     * Comma-separated list of paths to secure.
     */
    private String[] path = new String[] { "/**" };

    /**
     * Basic authentication users.
     */
    private List<SecurityProperties.User> users = new ArrayList<>(Collections.singletonList(
        new SecurityProperties.User()
    ));

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public List<SecurityProperties.User> getUsers() {
        return users;
    }

    public void setUsers(List<SecurityProperties.User> users) {
        this.users = users;
    }
}
