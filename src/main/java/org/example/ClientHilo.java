package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHilo extends Thread{

    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }


    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        String message;
        int opcion;
        boolean salir = false;

        while (!salir){
            try {
                System.out.println("1. Almacenar la peticion en el archivo");
                System.out.println("2. Numero de peticiones recibidas hasta ahora");
                System.out.println("3. Lista de peticiones recibidas ( el servidor las vera completas, el cliente solo la ultima");
                System.out.println("4. Salir");

                opcion = scanner.nextInt();
                out.writeInt(opcion);

                switch (opcion) {
                    case 1 -> {
                        //El cliente genera una peticion
                        System.out.println("Por favor introduzca su peticion:");
                        String petition;
                        Scanner entradaEscaner = new Scanner(System.in);
                        petition = entradaEscaner.nextLine();
                        String clientPetition = generarPeticiones(petition);
                        System.out.println("Tu peticion ha sido recibida y es: " + clientPetition);

                        //Le mandamos al servidor la peticion
                        out.writeUTF(clientPetition);
                        // Recibimos y mostramos el mensaje
                        message = in.readUTF();
                        System.out.println(message);
                    }
                    case 2 -> {
                        int numPetitions = in.readInt();
                        System.out.println("hay " + numPetitions + " peticiones");
                    }
                    case 3 -> {
                        String pet = in.readUTF();
                        System.out.println("La última peticion recibida hasta el momento es: " + pet);
                    }
                    case 4 -> salir = true;
                    default -> {
                        message = in.readUTF();
                        System.out.println(message);
                    }
                }

            }catch (IOException e){
                Logger.getLogger(ClientHilo.class.getName()).log(Level.SEVERE,null,e);

            }

        }
    }

    public String generarPeticiones (String message){return message;}


}
