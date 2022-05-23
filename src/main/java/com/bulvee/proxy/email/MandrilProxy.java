package com.bulvee.proxy.email;

import com.bulvee.proxy.helper.MandrillHelpers;
import com.bulvee.proxy.model.Contact;
import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.thoughtworks.xstream.XStream;

import java.util.*;

public class MandrilProxy implements MailSender {

    public List<String> emails = new ArrayList<>();

    public MandrilProxy(String... emails){
        this.emails = new ArrayList<>(Arrays.asList(emails));
    }

    //Avoid misconception
    private MandrilProxy(){}

    @Override
    public SendMessageResponse sendMessage(String message) throws RequestFailedException {
        List<Contact> blackList = loadBlackList();

        blackList.forEach(contact -> {
            emails.remove(contact.getEmail());
        });

        MandrillHelpers mandrillHelpers = new MandrillHelpers("alexandrehsantos@gmail.com","alehss@gmail.com","hsale@live.com");

        return mandrillHelpers.sendMessage(message);
    }

    private List<Contact> loadBlackList() {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[]{com.bulvee.proxy.model.Contact.class});
        xStream.alias("contacts", List.class);
        xStream.alias("contact", Contact.class);


        List<Contact> blackList = (List<Contact>)xStream.fromXML(this.getClass().getResource("/blacklist.xml"));
        return blackList;
    }
}
