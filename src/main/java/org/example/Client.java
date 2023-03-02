package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\n");

        try {
            System.out.println("Bienvenido al sistema de servicio al cliente");
            Socket socket = new Socket("localhost", 5050);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String message = in.readUTF();
            System.out.println(message);
            String name = scanner.next();
            out.writeUTF(name);


            ClientHilo clientHilo = new ClientHilo(in, out);
            clientHilo.start();
            clientHilo.join();
            System.out.println("Gracias por utilizar nuestro sistema de servicio al cliente");


        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
