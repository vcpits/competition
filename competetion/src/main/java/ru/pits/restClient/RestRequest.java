package ru.pits.restClient;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

public class RestRequest {

    private String url;
    private String path;
    private MultivaluedMap request;
    private Map<String, String > headersMap;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MultivaluedMap getRequest() {
        return request;
    }

    public void setRequest(MultivaluedMap request) {
        this.request = request;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }
}
