package com.bulvee.proxy.email;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;

import java.util.List;

public interface MailSender {

    SendMessageResponse sendMessage(
            String message
    ) throws RequestFailedException;
}
