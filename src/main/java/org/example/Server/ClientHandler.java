package org.example.Server;

import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import org.example.API.controllers.*;
import org.example.DataAccess.HibernateUtil;
import org.example.DataAccess.services.RecetaService;
import org.example.Domain.dtos.RequestDto;
import org.example.Domain.dtos.ResponseDto;
import org.example.Domain.dtos.auth.UsuarioResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final AuthController authController;
    private final MedicoController medicoController;
    private final FarmaceuticoController farmaceuticoController;
    private final MedicamentoController medicamentosController;
    private final PacienteController pacienteController;
    private final RecetaController recetaController;
    private final DespachoController despachoController;
    private final AdministradorController administradorController;
    private final DashboardController dashboardController;
    private final SocketServer server ;
    private final Gson gson = new Gson();
    private PrintWriter out;

    public ClientHandler(Socket clientSocket,
                         AuthController authController,
                         MedicoController medicoController,
                         FarmaceuticoController farmaceuticoController,
                         MedicamentoController medicamentosController,
                         PacienteController pacienteController,
                         RecetaController recetaController,
                         AdministradorController administradorController,
                         DashboardController dashboardController,
                         DespachoController despachoController,
                         SocketServer server) {
        this.clientSocket = clientSocket;
        this.authController = authController;
        this.medicoController = medicoController;
        this.farmaceuticoController = farmaceuticoController;
        this.medicamentosController = medicamentosController;
        this.pacienteController = pacienteController;
        this.recetaController = recetaController;
        this.administradorController = administradorController;
        this.dashboardController = dashboardController;
        this.despachoController = despachoController;
        this.server = server;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            System.out.println("[ClientHandler] Connected: " + Thread.currentThread().getName());

            String inputJson;
            while ((inputJson = in.readLine()) != null) {
                System.out.println("[ClientHandler] " + Thread.currentThread().getName() + " received: " + inputJson);

                RequestDto request = null;
                ResponseDto response = null;

                try {
                    request = gson.fromJson(inputJson, RequestDto.class);
                    System.out.println("[ClientHandler] Parsed RequestDto: controller=" + request.getController() + ", request=" + request.getRequest());

                    response = handleRequest(request);
                    System.out.println("[ClientHandler] Response prepared: " + gson.toJson(response));
                } catch (Exception e) {
                    System.err.println("[ClientHandler] Error processing request: " + e.getMessage());
                    e.printStackTrace();
                    response = new ResponseDto(false, "Server error: " + e.getMessage(), null);
                }

                try {
                    System.out.println("[ClientHandler] Sending response to client...");
                    out.println(gson.toJson(response));
                    System.out.println("[ClientHandler] Response sent successfully");
                } catch (Exception e) {
                    System.err.println("[ClientHandler] Failed to send response: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("[ClientHandler] " + Thread.currentThread().getName() + " disconnected: " + e.getMessage());
        } finally {
            try { clientSocket.close(); } catch (IOException ignore) {}
            server.removeClient(this);
            System.out.println("[ClientHandler] Client removed. Active clients: " + server.getActiveClientCount());
        }
    }

    private ResponseDto handleRequest(RequestDto request) {
        ResponseDto response;

        switch (request.getController()) {
            case "Auth":
                response = authController.route(request);

                // If login successful, broadcast notification
                if ("login".equals(request.getRequest()) && response.isSuccess()) {
                    UsuarioResponseDto user = gson.fromJson(response.getData(), UsuarioResponseDto.class);
                    String notification = "User " + user.getUsername() + " just logged in!";
                    System.out.println("[ClientHandler] Login detected, broadcasting: " + notification);
                    server.broadcast(notification);
                }
                break;

            case "Medico":
                response = medicoController.route(request);
                break;

            case "Farmaceutico":
                response = farmaceuticoController.route(request);
                break;


           case "Medicamento":
               response = medicamentosController.route(request);
               break;

           case "Paciente":
                response = pacienteController.route(request);
                break;

           case "Receta":
               response = recetaController.handle(request);
                break;

           case "Despacho":
               response = despachoController.handle(request);
                break;

           case "Admin":
           case "Administrador":
                response = administradorController.route(request);
                break;

           case "Dashboard":
                response = dashboardController.route(request);
                break;
            default:
                response = new ResponseDto(false, "Unknown controller", null);
        }

        return response;
    }

    public void sendMessage(Object message) {
        if (out != null) {
            String jsonMessage = gson.toJson(message);
            out.println(jsonMessage);
            System.out.println("[ClientHandler] " + Thread.currentThread().getName() + " sent: " + jsonMessage);
        }
    }


}
