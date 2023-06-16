import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RaffleClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            RaffleService service = (RaffleService) registry.lookup("RaffleService");

            Scanner scanner = new Scanner(System.in);

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
                    break;
                } else {
                    System.out.println("Comando inválido.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro no cliente: " + e.toString());
        }
    }
}
