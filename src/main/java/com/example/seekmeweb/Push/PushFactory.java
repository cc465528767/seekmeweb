package com.example.seekmeweb.Push;

import com.example.seekmeweb.Bean.Message;
import com.example.seekmeweb.Bean.User;

public class PushFactory {
    public static void pushNewMessage(User sender, Message message){
        if (sender == null || message == null)
            return;
        PushDispatcher dispatcher = new PushDispatcher();


    }
}
