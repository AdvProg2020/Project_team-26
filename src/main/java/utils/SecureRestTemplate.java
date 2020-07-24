//package utils;
//
//import org.apache.http.client.HttpClient;
//import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
//import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.ssl.SSLContextBuilder;
//import org.springframework.http.client.ClientHttpRequestFactory;
//import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.security.KeyStore;
//
//public class SecureRestTemplate extends RestTemplate {
//
//    private static ClientHttpRequestFactory secureClientFactory = null;
//
//    public static RestTemplate create() {
////        if(secureClientFactory == null) {
////            secureClientFactory = getClientHttpRequestFactory();
////        }
//        try {
//            return new RestTemplate(getClientHttpRequestFactory());
//        } catch (Exception exception) {
//            exception.printStackTrace();
//            return null;
//        }
//    }
//
//    private static ClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
//        FileInputStream fileInputStream = new FileInputStream(new File("/keystore/keystore.p12"));
////        ResourceLoader resourceLoader = new DefaultResourceLoader();
////        Resource resource = resourceLoader.getResource("classpath:keystore/keystore.p12");
//        String keyStorePassword = "H589QkHFIdafh6@*yuydfjh879yfdWWMjHUyoih&jnawi0asd23Yzq";
//        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keyStore.load(fileInputStream, keyStorePassword.toCharArray());
//        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
//                new SSLContextBuilder()
//                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
//                        .loadKeyMaterial(keyStore, keyStorePassword.toCharArray()).build());
//        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();
//        return new HttpComponentsClientHttpRequestFactory(httpClient);
//    }
//}
