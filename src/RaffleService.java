import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RaffleService extends Remote {

    String buyTicket(String participantName, int ticketNumber) throws RemoteException;
}
