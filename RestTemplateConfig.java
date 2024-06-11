package your.package;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate oauth2RestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress("proxyIP", proxyPort)); // replace with your proxy IP and port
        requestFactory.setProxy(proxy);
        return new RestTemplate(requestFactory);
    }
}
