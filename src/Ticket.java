public class Ticket {
    private int number;
    private String nomeComprador;

    public String getNomeComprador() {
        return nomeComprador;
    }

    public void setNomeComprador(String nomeComprador) {
        this.nomeComprador = nomeComprador;
    }

    public Ticket(int number) {
        this.number = number;
    }

    public Ticket(int ticketNumber, String participantName) {
        this.number = ticketNumber;
        this.nomeComprador = participantName;
    }

    public int getNumber() {
        return number;
    }
}
