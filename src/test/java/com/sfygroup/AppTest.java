package com.sfygroup;

import static org.junit.Assert.assertTrue;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testClient() throws IOException {
        /** 用户认证对象 */
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        /** 设置账号密码 */
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "suofy2019"));

        /** 创建rest client对象 */
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("10.10.10.65", 9200, "http"))
                          .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback(){

                              @Override
                              public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                  return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                              }
                          })

        );

        GetRequest getRequest = new GetRequest(
                "shijiange",
                "9");
        //getRequest.fetchSourceContext(new FetchSourceContext(false));
        //getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("文档是否存在："+exists);
    }
}
