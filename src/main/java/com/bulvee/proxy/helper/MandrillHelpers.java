package com.bulvee.proxy.helper;

import com.bulvee.proxy.email.MailSender;
import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.*;

public class MandrillHelpers implements MailSender {

    private static final String API_VERSION = "1.0";
    private static final String BASE_URL = "https://mandrillapp.com/api";
    private static final String MANDRILL_API_KEY = "vtLeG3xv9Jha_l4cCQSL4w";

    private static MandrillRESTRequest request = new MandrillRESTRequest();
    private static MandrillConfiguration config = new MandrillConfiguration();
    private static MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
    private static HttpClient client = new DefaultHttpClient();
    private static ObjectMapper mapper = new ObjectMapper();
    MandrillRecipient[] recipients;


    public MandrillHelpers(String... emails) {
        List<MandrillRecipient> mandrillRecipientList = new ArrayList<>();

        List<String> emailList = new ArrayList<>(Arrays.asList(emails));
        emailList.forEach(email -> {
            mandrillRecipientList.add(new MandrillRecipient("", email));
        });

        this.recipients =  mandrillRecipientList.toArray(new MandrillRecipient[mandrillRecipientList.size()]);
    }

    {
        config.setApiKey(MANDRILL_API_KEY);
        config.setApiVersion(API_VERSION);
        config.setBaseURL(BASE_URL);
        request.setConfig(config);
        request.setObjectMapper(mapper);
        request.setHttpClient(client);
        messagesRequest.setRequest(request);
    }

    @Override
    public SendMessageResponse sendMessage(
            String message
    ) throws RequestFailedException {
        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage htmlMessage = new MandrillHtmlMessage();

        Map<String, String> headers = new HashMap<String, String>();
        htmlMessage.setHeaders(headers);
        htmlMessage.setHtml(message);
        htmlMessage.setSubject("Mail for proxy teste");
        htmlMessage.setTo(this.recipients);
        htmlMessage.setTrack_clicks(true);
        htmlMessage.setTrack_opens(true);
        mmr.setMessage(htmlMessage);

        return messagesRequest.sendMessage(mmr);
    }
}