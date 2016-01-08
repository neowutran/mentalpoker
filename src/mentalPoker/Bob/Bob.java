package mentalPoker.Bob;

import mentalPoker.Sra;

import java.math.BigInteger;
import java.util.LinkedList;

public class Bob {

    public static void main(String[] args) {
        new Bob("192.168.0.1",1025);
    }

    private LinkedList<BigInteger> deck = new LinkedList<>();
    private Network network;
    private BigInteger aliceCard;
    private Sra sra = new Sra();

    public Bob(String ip, int port){
        System.out.println("Begin");
        init();
        System.out.println("Begin Network");
        network = new Network(ip, port);
        System.out.println("Send P");
        sendP();
        System.out.println("Read Q");
        readQ();

        System.out.println("Sra init");

        //Cipher deck
        sra.init();
        cipherDeck();

        System.out.println("Send deck");
        sendDeck();

        System.out.println("read shuffle deck");
        readShuffleDeck();

        //Decipher deck
        System.out.println("decipher deck");
        decipherDeck();


        System.out.println(deck.get(0));
        System.out.println("Send alice card");
        sendAliceCard();

        System.out.println("Alice decipher key");
        readAliceD();

        System.out.println("Send decipher key");
        sendD();

        network.close();

    }

    private void sendD(){
        network.write(sra.getD());
    }

    private void cipherDeck(){
        LinkedList<BigInteger> cipherDeck = new LinkedList<>();
        for (BigInteger aDeck : deck) {
            cipherDeck.add(sra.encryptCard(aDeck));
        }
        deck = cipherDeck;
    }

    private void decipherDeck(){
        LinkedList<BigInteger> plainDeck = new LinkedList<>();
        for(BigInteger aDeck: deck){
            plainDeck.add(sra.decryptCard(aDeck));
        }
        deck = plainDeck;
     }

    private void sendAliceCard(){
        aliceCard = deck.get(1);
        network.write(aliceCard);
    }

    private void readAliceD(){
        BigInteger aliceD = (BigInteger)network.read();
        Sra aliceSecurity = new Sra();
        aliceSecurity.setP(sra.getP());
        aliceSecurity.setQ(sra.getQ());
        aliceSecurity.init();
        aliceSecurity.setD(aliceD);
        System.out.println(aliceSecurity.decryptCard(aliceCard));

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
        deck = (LinkedList<BigInteger>)network.read();
    }

    private void sendDeck(){
        network.write(deck);
    }

    private void init(){
        deck.add(new BigInteger("91"));
        deck.add(new BigInteger("42"));
        deck.add(new BigInteger("39"));
        sra.generateP();
    }
}
