package org.example;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHilo extends Thread{

    private final DataInputStream in;
    private final DataOutputStream out;
    private final String clientName;
    private final Socket socket;


    public ServerHilo(DataInputStream in, DataOutputStream out, String clientName, Socket socket) {
        this.in = in;
        this.out = out;
        this.clientName = clientName;
        this.socket = socket;
    }

    @Override
    public void run() {


        int opcion;
        File file = new File("peticiones.txt");
        boolean salir = false;

        while (!salir){

            try {
                opcion= in.readInt();

                switch (opcion) {
                    case 1 -> {
                        //Recibimos la peticion
                        String clientPetition = in.readUTF();
                        //Imprimimos la peticion
                        imprimirPeticion(file, clientPetition);
                        System.out.println("Se guardó la peticion en el cliente " + clientName);
                        // Mandamos mensaje de confimracion al cliente
                        out.writeUTF("Peticion recibida y almacenada correctamente");
                    }
                    case 2 -> {
                        int numPetitions = numLineasArchivo(file);
                        out.writeInt(numPetitions);
                    }
                    case 3 -> {
                        out.writeUTF(listOfPetitions(file));
                        System.out.println("Las peticiones realizadas hasta el momento son: ");
                    }
                    case 4 -> salir = true;
                    default -> out.writeUTF("Solo puedes ingresar numeros del 1 al 4");
                }
            }catch (IOException e){
                Logger.getLogger(ServerHilo.class.getName()).log(Level.SEVERE,null,e);
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("La conexion con el cliente "+clientName+" se ha cerrado");
    }

    private void imprimirPeticion(File file, String clientPetition) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(clientName + ": "+clientPetition+ "\r\n");
        fw.close();

    }

    private int numLineasArchivo(File file) throws IOException {

        int numPetitions = 0;
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line = "";
        while (br.readLine() != null) {
            numPetitions++;
        }

        br.close();
        return numPetitions;
    }

    private String listOfPetitions(File file) throws IOException {

        /*BufferedReader br = new BufferedReader(new FileReader(file));

        String line = br.readLine() ;
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();*/
        Scanner scanner = new Scanner(file);
        String line="";
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            System.out.println(line);
        }

        return line;
    }
}
