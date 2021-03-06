package cce.client;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;

@Configuration
public class ClientConfig {
    private static final Logger LOG = LoggerFactory.getLogger(ClientConfig.class);

//    @Value("${client.ssl.trust-store}")
//    private Resource trustStore;
//
//    @Value("${client.ssl.trust-store-password}")
//    private String trustStorePassword;

    @Value("${client.ssl.key-store}")
    private Resource clientKs;

    @Value("${client.ssl.key-store-password}")
    private String clientKsPwd;

    @Value("${client.ssl.key-store-alias}")
    private String clientKsAlias;

    @Value("client.ws-url")
    private String wsUrl;

    @Bean
    public Boolean disableSSLValidation() throws Exception {
        final SSLContext sslContext = SSLContext.getInstance("TLS");

        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }}, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        return true;
    }

	@Bean
    public Wss4jSecurityInterceptor wss4jSecurityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // set security actions: Timestamp Signature SAMLTokenSigned SAMLTokenUnsigned
        securityInterceptor.setSecurementActions("Timestamp Signature");

        // sign the request
        securityInterceptor.setSecurementUsername(clientKsAlias);
        securityInterceptor.setSecurementPassword(clientKsPwd);
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementSignatureParts(
                "{Element}{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp;" +
                        "{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body"
        );

        // X509KeyIdentifier, DirectReference
        securityInterceptor.setSecurementSignatureKeyIdentifier("DirectReference");
        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword(clientKsPwd);
        cryptoFactoryBean.setKeyStoreLocation(clientKs);
        return cryptoFactoryBean;
    }

    @Bean
    public Jaxb2Marshaller getMarshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("nl.kvk.schemas.schemas.hrip");
        return marshaller;
    }

    @Bean
    public WSClient getCCERequestClient(@Value("${client.ws.url}") String wsUrl,
                                  @Autowired Wss4jSecurityInterceptor interceptor) throws Exception {
        LOG.info("Building WSS Client");
        WSClient client = new WSClient();
        client.setMarshaller(getMarshaller());
        client.setUnmarshaller(getMarshaller());
        client.setDefaultUri(wsUrl);
        ClientInterceptor[] interceptors = new ClientInterceptor[]{interceptor};
        client.setInterceptors(interceptors);
        return client;
    }

}
