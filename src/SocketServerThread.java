import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SocketServerThread extends Thread {
    private Socket clientSocket;
    private List<Ticket> availableTickets;

    public SocketServerThread(Socket clientSocket, List<Ticket> Tickets) {
        this.clientSocket = clientSocket;
        this.availableTickets = Tickets;
    }

    @Override
    public void run() {
        try {
            // Criar streams de entrada e saída para comunicação com o cliente
            // ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            // ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            // Aguardar comandos do cliente
            while (true) {
                String command = in.readUTF();

                if (command.equalsIgnoreCase("getAvailableTickets")) {
                    // Enviar a lista de bilhetes disponíveis para o cliente
                    System.out.println("enviando lista de bilhetes disponíveis");
                    System.out.println(Ticket.getTicketNumbers(availableTickets));
                    out.writeUTF(Ticket.getTicketNumbers(availableTickets));
                } else if (command.equalsIgnoreCase("exit")) {
                    break;
                }
            }

            // Fechar as conexões
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
