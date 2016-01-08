package mentalPoker;

import java.math.BigInteger;
import java.util.LinkedList;

public class MentalPoker {

    public static void main(String[] args) {
        new MentalPoker("172.19.250.218",1025);
    }

    private LinkedList<Integer> deck = new LinkedList<>();
    private Network network;
    private Sra sra = new Sra();

    public MentalPoker(String ip, int port){
        init();
        network = new Network(ip, port);
        sendP();
        readQ();

        //Cipher deck


        sendDeck();
        readShuffleDeck();


        //Decipher deck


        System.out.println(deck.get(0));
        sendAliceCard();
        network.close();

    }

    private void sendAliceCard(){
        network.write(deck.get(1));
    }


    private void readQ(){
        BigInteger q = (BigInteger)network.read();
        System.out.println(q);
        sra.setQ(q);
    }

    private void sendP(){
        BigInteger p = sra.getP();
        System.out.println(p);
        network.write(p);
    }

    private void readShuffleDeck(){
        deck = (LinkedList<Integer>)network.read();
    }

    private void sendDeck(){
        network.write(deck);
    }

    private void init(){
        deck.add(0);
        deck.add(42);
        deck.add(39);
        sra.generateP();
    }
}
