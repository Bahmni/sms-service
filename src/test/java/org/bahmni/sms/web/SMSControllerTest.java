package org.bahmni.sms.web;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.bahmni.sms.SMSSender;
import org.bahmni.sms.web.security.OpenMRSAuthenticator;
import org.bahmni.sms.web.security.TokenValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class SMSControllerTest {
    @MockBean
    private SMSSender smsSender;

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private OpenMRSAuthenticator authenticator;

    @MockBean
    private TokenValidator tokenValidator;


    @Test
    public void shouldAcceptTheSMSRequest() {
        Object requestBody = "{" +
                "\"phoneNumber\":\"+919999999999\"," +
                "\"message\":\"hello\"" +
                "}";
        when(tokenValidator.validateToken("dummy")).thenReturn(Boolean.valueOf("true"));
        webClient.post()
                .uri("/notification/sms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .header("Authorization","Bearer dummy")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    public void shouldThrowBadRequest() {
        Object requestBody = "{" +
                "'phoneNumber':'+919999999999'," +
                "'message':'hello'" +
                "}";
        when(tokenValidator.validateToken("dummy")).thenReturn(Boolean.valueOf("true"));
        webClient.post()
                .uri("/notification/sms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .header("Authorization","Bearer dummy")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void shouldCallSend() {
        Object requestBody = "{" +
                "\"message\":\"hello\"," +
                "\"phoneNumber\":\"919999999999\"" +
                "}";
        when(tokenValidator.validateToken("dummy")).thenReturn(Boolean.valueOf("true"));
        webClient.post()
                .uri("/notification/sms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .header("Authorization","Bearer dummy")
                .exchange();

        Mockito.verify(smsSender, times(1)).send("919999999999", "hello");
    }

    @Test
    public void shouldThrowUnAuthorizedWhenAuthenticationFailed() {
        Object requestBody = "{" +
                "\"message\":\"hello\"," +
                "\"phoneNumber\":\"919999999999\"" +
                "}";
        when(tokenValidator.validateToken("dummy")).thenReturn(Boolean.valueOf("false"));
        webClient.post()
                .uri("/notification/sms")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .header("Authorization","Bearer dummy")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }
}
