package mentalPoker;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Sra {

    private BigInteger p;
    private BigInteger n;
    private BigInteger q;
    private BigInteger d;
    private BigInteger e;
    private BigInteger on;

    private Integer SIZE = 512;


    public BigInteger getQ(){
        return q;
    }

    public BigInteger getP(){
        return p;
    }

    public void generateQ(){
        q=generate();
    }

    public void generateP(){
        p=generate();
    }

    private BigInteger generate(){
        return BigInteger.probablePrime(SIZE, new SecureRandom());
    }

    public void setQ(BigInteger q){
        this.q = q;

    }

    public void setN(){
        n=p.multiply(q);
    }

    public void setON(){
        on=(p.min(new BigInteger("1"))).multiply(q.min(new BigInteger("1")));
    }

    public void setE(){
        do{
            e = new BigInteger(2*SIZE, new SecureRandom());
        }while( (e.equals(on))|| (pgcd(e,on).intValue() != 1));
    }

    public void setD(){
        this.d = e.modInverse(on);
    }

    public boolean checkPrimeQ(BigInteger q){
        return q.isProbablePrime(1);
    }

    public BigInteger pgcd(BigInteger x, BigInteger y){
        return x.gcd(y);
    }

    private Integer decryptCard(BigInteger card){
        return card.modPow(d,n).intValue();
    }

    private BigInteger encryptCard(Integer card){
        BigInteger bigCard = new BigInteger(card+"");
        return bigCard.modPow (e,n);
    }
}
