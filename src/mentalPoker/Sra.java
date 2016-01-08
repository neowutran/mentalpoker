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

    private Integer SIZE = 4;


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

    public void setP(BigInteger p){
        this.p = p;
    }

    public void init(){
        on=(p.min(new BigInteger("1"))).multiply(q.min(new BigInteger("1")));
        do{
            e = new BigInteger(2*SIZE, new SecureRandom());
        }while( (e.equals(on))|| (pgcd(e,on).intValue() != 1));
        d = e.modInverse(on);
        n=p.multiply(q);
    }

    public boolean checkPrimeQ(BigInteger q){
        return q.isProbablePrime(1);
    }

    private BigInteger pgcd(BigInteger x, BigInteger y){
        return x.gcd(y);
    }

    public BigInteger decryptCard(BigInteger card){
        return card.modPow(d,n);
    }

    public BigInteger encryptCard(BigInteger card){
        return card.modPow (e,n);
    }
}
