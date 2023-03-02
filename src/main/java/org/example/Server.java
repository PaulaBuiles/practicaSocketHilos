package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(5050);
            Socket socket;

            System.out.println("Iniciamos el servidor");
            while (true) {
                socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());


                out.writeUTF("Ingresa tu nombre");
                String clientName = in.readUTF();


                ServerHilo serverHilo = new ServerHilo(in , out, clientName, socket);
                serverHilo.start();

                System.out.println("Se ha creado la conexion con el cliente "+clientName);

            }


        } catch (IOException e) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE,null, e);

        }

    }

}
