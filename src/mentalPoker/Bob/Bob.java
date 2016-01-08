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
    private Sra sra = new Sra();

    public Bob(String ip, int port){
        System.out.println("Begin");

        init();
        System.out.println("Begin Network");
        network = new Network(ip, port);
        System.out.println("1");
        sendP();
        System.out.println("2");
        readQ();

        System.out.println("3");

        //Cipher deck
       // sra.init();
        //cipherDeck();


        sendDeck();
        readShuffleDeck();

        //Decipher deck
        //decipherDeck();


        System.out.println(deck.get(0));
        sendAliceCard();
        network.close();

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
        deck = (LinkedList<BigInteger>)network.read();
    }

    private void sendDeck(){
        network.write(deck);
    }

    private void init(){
        deck.add(new BigInteger("0"));
        deck.add(new BigInteger("42"));
        deck.add(new BigInteger("39"));
        sra.generateP();
    }
}
