import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientThread extends Thread {
    private Socket serverSocket;
    private String command;
    private final Object lock = new Object();

    public SocketClientThread(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        try {
            // Criar streams de entrada e saída para comunicação com o servidor
            // ObjectOutputStream out = new ObjectOutputStream(serverSocket.getOutputStream());
            // ObjectInputStream in = new ObjectInputStream(serverSocket.getInputStream());

            DataInputStream in = new DataInputStream(serverSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(serverSocket.getOutputStream());
            
            boolean finalizar = false;

            while (!finalizar) {
                synchronized (lock) {
                    
                    lock.wait();
                    if (command.equalsIgnoreCase("listBilhets")) {
                        // Enviar o comando para obter a lista de bilhetes disponíveis
                        out.writeUTF("getAvailableTickets");

                        // Receber a lista de bilhetes disponíveis do servidor
                        String availableTickets = in.readUTF();
                        System.out.println("bilhetes indisponiveis");
                        System.out.println(availableTickets);
                    } else if (command.equalsIgnoreCase("exit")) {
                        // Enviar o comando para finalizar a conexão com o servidor
                        out.writeUTF("exit");
                        finalizar = true;
                    }

                    // Limpar o comando
                    command = null;
                }
                
            }

            in.close();
            out.close();
            serverSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCommand(String command) {
        synchronized (lock) {
            this.command = command;
            lock.notify();
            
        }
    }
}
