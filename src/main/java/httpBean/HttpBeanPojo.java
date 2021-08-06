package httpBean;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpBeanPojo {
    private Headers headers;
    private String origin;
    private String url;
    private Map args = new HashMap<String, Object>();

    public Headers getHeaders() {
        return headers;
    }

    public String getOrigin() {
        return origin;
    }

    public String getUrl() {
        return url;
    }

    public Map getArgs() {
        return args;
    }

    public void setArgs(Map args) {
        this.args = args;
    }

    @JsonAnySetter
    public void setArgs(String key, Object value) {
        args.put(key, value);
    }

    @Override
    public String toString() {
        return "HttpBeanPojo{" +
                "headers=" + headers +
                ", origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                ", args=" + args +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Headers {
        @JsonProperty("Accept")
        private String accept;
        @JsonProperty("Cache-Control")
        private String cacheControl;
        @JsonProperty("Host")
        private String host;
        @JsonProperty("UserAgent")
        private String userAgent;
        @JsonProperty("X-Amzn-Trace-Id")
        private String xAmznTraceId;

        public String getAccept() {
            return accept;
        }

        public String getCacheControl() {
            return cacheControl;
        }

        public String getHost() {
            return host;
        }

        public String getUserAgent() {
            return userAgent;
        }

        public String getxAmznTraceId() {
            return xAmznTraceId;
        }

        @Override
        public String toString() {
            return "Headers{" +
                    "accept='" + accept + '\'' +
                    ", cacheControl='" + cacheControl + '\'' +
                    ", host='" + host + '\'' +
                    ", userAgent='" + userAgent + '\'' +
                    ", xAmznTraceId='" + xAmznTraceId + '\'' +
                    '}';
        }
    }
}
