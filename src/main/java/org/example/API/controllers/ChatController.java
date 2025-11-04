package org.example.API.controllers;

import com.google.gson.Gson;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.chat.ChatMessageDto;
import org.example.Server.MessageBroadcaster;

public class ChatController {
    private final Gson gson = new Gson();
    private final MessageBroadcaster broadcaster;

    public ChatController(MessageBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    public ResponseDto route(RequestDto request) {
        try {
            switch (request.getRequest()) {
                case "sendMessage":
                    return handleSendMessage(request);
                default:
                    return new ResponseDto(false, "Unknown chat request: " + request.getRequest(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDto(false, "Error: " + e.getMessage(), null);
        }
    }

    private ResponseDto handleSendMessage(RequestDto request) {
        ChatMessageDto msg = gson.fromJson(request.getData(), ChatMessageDto.class);
        System.out.println("[ChatController] " + msg.getSender() + " -> " + msg.getMessage());

        // Envía el mensaje a todos los clientes conectados (puerto 7001)
     //   broadcaster.broadcast(gson.toJson(msg));

        return new ResponseDto(true, "Message sent", null);
    }
}
