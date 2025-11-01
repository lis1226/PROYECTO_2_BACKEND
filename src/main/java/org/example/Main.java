package org.example;

import org.example.API.controllers.AuthController;
import org.example.API.controllers.FarmaceuticoController;
import org.example.API.controllers.MedicamentoController;
import org.example.API.controllers.MedicoController;
import org.example.DataAccess.HibernateUtil;
import org.example.DataAccess.services.AuthService;
import org.example.DataAccess.services.FarmaceuticoService;
import org.example.DataAccess.services.MedicamentoService;
import org.example.DataAccess.services.MedicoService;
import org.example.Domain.models.Medico;
import org.example.Server.MessageBroadcaster;
import org.example.Server.SocketServer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var sessionFactory = HibernateUtil.getSessionFactory();

        // Initialize services and controllers
        AuthService authService = new AuthService(sessionFactory);
        AuthController authController = new AuthController(authService);

        MedicoService medicoService = new MedicoService(sessionFactory);
        MedicoController medicoController = new MedicoController(medicoService);

        FarmaceuticoService farmaceuticoService = new FarmaceuticoService(sessionFactory);
        FarmaceuticoController farmaceuticoController = new FarmaceuticoController(farmaceuticoService);

        MedicamentoService medicamentoService = new MedicamentoService(sessionFactory);
        MedicamentoController medicamentoController = new MedicamentoController(medicamentoService);


        var createUsers = true;
        /*if(createUsers) {
            authService.register("user", "email@example.com", "pass", "USER");
            authService.register("otro", "otro@example.com", "pass", "USER");
        }*/

        // Server for request/response (API-like)
        int requestPort = 7000;
        SocketServer requestServer = new SocketServer(
                requestPort,
                authController,
                medicoController,
                farmaceuticoController,
                medicamentoController
                );

        // Server for chat/broadcasting (persistent connections)
        int messagePort = 7001;
        MessageBroadcaster messageBroadcaster = new MessageBroadcaster(messagePort, requestServer);

        // Register the broadcaster with the request server so it can broadcast messages
        requestServer.setMessageBroadcaster(messageBroadcaster);

        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nShutting down servers...");
            requestServer.stop();
            messageBroadcaster.stop();
        }));

        // Start servers
        requestServer.start();
        messageBroadcaster.start();
        System.out.println("Servers started - Requests: " + requestPort + ", Messages: " + messagePort);

    }
}