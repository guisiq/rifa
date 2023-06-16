import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.IOException;
import java.net.Socket;

public class RaffleClient {
    private static SocketClientThread clientThread ;
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RaffleService service = (RaffleService) registry.lookup("RaffleService");

            Scanner scanner = new Scanner(System.in);
            start();

            while (true) {
                System.out.println("Digite o comando ('buyTicket', 'listBilhets' , 'exit' ):");
                String command = scanner.nextLine();

                if (command.equalsIgnoreCase("buyTicket")) {
                    System.out.println("Digite o nome do participante:");
                    String participantName = scanner.nextLine();
                    System.out.println("Digite o número do bilhete:");
                    int ticketNumber = Integer.parseInt(scanner.nextLine());
                    String response = service.buyTicket(participantName, ticketNumber);
                    System.out.println(response);
                } else if (command.equalsIgnoreCase("exit")) {
                    clientThread.setCommand(command);
                    break;
                } else if (command.equalsIgnoreCase("listBilhets")) {
                    clientThread.setCommand(command);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
        }
    }

    public static void start() {
        try {
            Socket serverSocket = new Socket("localhost", 8080);

            // Crie e inicie uma nova thread para lidar com a conexão com o servidor
            clientThread = new SocketClientThread(serverSocket);
            clientThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
