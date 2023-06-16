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
    DataInputStream in;
    DataOutputStream out;
    public SocketServerThread(Socket clientSocket, List<Ticket> Tickets) {
        this.clientSocket = clientSocket;
        this.availableTickets = Tickets;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            

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
            this.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
