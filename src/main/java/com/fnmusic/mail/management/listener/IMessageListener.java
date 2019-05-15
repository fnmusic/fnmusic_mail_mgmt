package com.fnmusic.mail.management.listener;

import javax.mail.MessagingException;
import java.io.IOException;

public interface IMessageListener {

    public void onListen(Object object) throws IOException, ClassNotFoundException, MessagingException;
}
