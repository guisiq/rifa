import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RaffleServer implements RaffleService {
    private List<Ticket> tickets;
    private int ticketCount;
    private Random random;

    public RaffleServer() {
        tickets = new ArrayList<>();
        ticketCount = 0;
        random = new Random();
    }

    @Override
    public synchronized String buyTicket(String participantName, int ticketNumber) throws RemoteException {
        if (ticketCount >= 50) {
            return "Não é possível comprar bilhetes após o sorteio.";
        }

        for (Ticket ticket : tickets) {
            if (ticket.getNumber() == ticketNumber) {
                return "Número de bilhete já adquirido.";
            }
        }

        tickets.add(new Ticket(ticketNumber, participantName));
        ticketCount++;

        if (ticketCount == 3) {
            var winnerIndex = drawWinner();
            return "Bilhete comprado com sucesso. Sorteio realizado. Vencedor: Participante no índice " + winnerIndex;
        }

        return "Bilhete comprado com sucesso.";
    }

    private String drawWinner() {
        if (tickets.isEmpty()) {
            throw new RuntimeException("Não há bilhetes adquiridos.");
        }

        System.out.println("Sorteando o vencedor...");
        int winnerIndex = random.nextInt(tickets.size());
        var ganhador = tickets.get(winnerIndex);
        System.out.println("O vencedor é o participante " + ganhador.getNomeComprador() + " com o bilhete " + ganhador.getNumber() );

        return ganhador.getNomeComprador() + " com o bilhete " + ganhador.getNumber();
    }

    public static void main(String[] args) {
        try {
            RaffleServer server = new RaffleServer();
            RaffleService stub = (RaffleService) UnicastRemoteObject.exportObject(server, 0);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RaffleService", stub);
            System.out.println("Servidor de rifa iniciado.");
        } catch (Exception e) {
            System.err.println("Erro no servidor: " + e.toString());
        }
    }
}
