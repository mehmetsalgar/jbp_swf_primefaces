package org.salgar.jsf.context;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortalActionURL {
    private static final Pattern urlPattern = Pattern
            .compile("^(\\w*:)?(//[\\w\\._-]+[^/:])?((?:\\:)(\\d+))?([^?]*)?((?:\\?)(.*))?$");

    private static final String NULL = "";

    private String protocol;

    private String host;

    private int port = -1;

    /**
     * The authority part of this URL.
     * 
     * @serial
     */
    private String authority;

    /**
     * The path part of this URL.
     */
    private transient String path;

    /**
     * The userinfo part of this URL.
     */
    private transient String userInfo;

    private Map<String, String[]> parameters;

    private String queryString;

    private int _length;

    /**
     * @param url
     */
    public PortalActionURL(String url) throws MalformedURLException {
        Matcher urlMatcher = urlPattern.matcher(url);
        if (!urlMatcher.matches()) {
            throw new MalformedURLException(url);
        }
        _length = url.length();
        this.protocol = urlMatcher.group(1);
        this.host = urlMatcher.group(2);
        String portStr = urlMatcher.group(4);
        if (null != portStr && portStr.length() > 0) {
            this.port = Integer.parseInt(portStr);
        }
        this.path = urlMatcher.group(5);
        queryString = urlMatcher.group(7);
        parameters = new LinkedHashMap<String, String[]>(30);
        if (null != queryString) {
            String[] queryParams = queryString.split("&");
            for (int i = 0; i < queryParams.length; i++) {
                String par = queryParams[i];
                int eqIndex = par.indexOf('=');
                if (eqIndex >= 0) {
                    addParameter(par.substring(0, eqIndex), par.substring(eqIndex + 1));
                } else {
                    addParameter(par, NULL);
                }
            }
        }
    }

    /**
     * Clone constructor
     * 
     * @param src
     */
    public PortalActionURL(PortalActionURL src) {
        if (null == src) {
            throw new NullPointerException("Source URL is null");
        }
        this._length = src._length;
        this.protocol = src.protocol;
        this.host = src.host;
        this.port = src.port;
        this.path = src.path;
        this.queryString = src.queryString;
        this.parameters = new LinkedHashMap<String, String[]>(src.parameters);
        this.authority = src.authority;
        this.userInfo = src.userInfo;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the authority
     */
    public String getAuthority() {
        return authority;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return the userInfo
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * @return the queryString
     */
    public String getQueryString() {
        return queryString;
    }

    public String getParameter(String name) {
        String[] values = parameters.get(name);
        if (null != values && values.length > 0) {
            return values[0];
        }
        return null;
    }

    public void setParameter(String name, String value) {
        parameters.put(name, new String[] { value });
    }

    public void addParameter(String name, String value) {
        String[] values = parameters.get(name);
        if (null != values && values.length > 0) {
            List<String> valuesList = new ArrayList<String>(Arrays.asList(values));
            valuesList.add(value);
            values = valuesList.toArray(new String[valuesList.size()]);
        } else {
            values = new String[] { value };
        }
        parameters.put(name, values);
    }

    public String removeParameter(String name) {
        String[] values = parameters.remove(name);
        if (null != values && values.length > 0) {
            return values[0];
        }
        return null;
    }

    public int parametersSize() {
        return parameters.size();
    }

    public boolean isInContext(String context) {
        return host == null && protocol == null && port == -1 && path.startsWith(context + "/");
    }

    @Override
    public String toString() {
        StringBuffer url = new StringBuffer(_length);
        if (null != protocol) {
            url.append(protocol);
        }
        if (null != host) {
            url.append(host);
        }
        if (port > 0) {
            url.append(':').append(port);
        }
        url.append(path);
        if (parameters.size() > 0) {
            url.append('?');
            for (Iterator<Entry<String, String[]>> iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
                Entry<String, String[]> param = iterator.next();
                String[] values = param.getValue();
                for (int i = 0; i < values.length; i++) {
                    url.append(param.getKey());
                    if (values[i] != NULL) {
                        url.append('=').append(values[i]);
                    }
                    if (i < values.length - 1) {
                        url.append('&');
                    }

                }
                if (iterator.hasNext()) {
                    url.append('&');
                }
            }
        }
        return url.toString();
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
