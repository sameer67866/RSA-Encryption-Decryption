import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;


public class Main {

    static BigInteger n, e, d;

    public static void main(String[] args) {
        int p = 1;
        int q = 1;

        while (p != 0) {
            Scanner primeNums = new Scanner(System.in);
            System.out.println("Enter 2 prime numbers:");
            p = primeNums.nextInt();
            q = primeNums.nextInt();
            if (p > 100 || q > 100) {
                System.out.println("Please enter 2 prime numbers less than 100");
                p = primeNums.nextInt();
                q = primeNums.nextInt();
            } else if (p < 1 || q < 1) {
                System.out.println("Please enter 2 prime numbers greater than 1");
                p = primeNums.nextInt();
                q = primeNums.nextInt();
            }
            break;
        }

        // Generate RSA keys
        keyGenerator(p, q);

        System.out.println("Public Key: (n=" + n + ", e=" + e + ")");
        System.out.println("Private Key: (n=" + n + ", d=" + d + ")");

        //BigInteger message = new BigInteger("109");
        Scanner messageNum = new Scanner(System.in);
        System.out.println("Enter a message number between 0 and 256:");
        BigInteger message = messageNum.nextBigInteger();

        BigInteger ciphertext = encrypt(message, n, e);
        System.out.println("Ciphertext: " + ciphertext);

        BigInteger plaintext = decrypt(ciphertext, n, d);
        System.out.println("Plaintext: " + plaintext);
    }

    public static BigInteger keyGenerator(int p, int q) {
        // Compute n and phi(n)
        BigInteger pBig = BigInteger.valueOf(p);
        BigInteger qBig = BigInteger.valueOf(q);
        n = pBig.multiply(qBig);
        BigInteger phiN = pBig.subtract(BigInteger.ONE).multiply(qBig.subtract(BigInteger.ONE));

        // Choose a random e, such that 3 < e < phi(n), and gcd(e, phi(n)) = 1
        Random random = new Random();
        BigInteger eBig = BigInteger.valueOf(random.nextInt(phiN.intValue() - 3) + 3);
        while (!eBig.gcd(phiN).equals(BigInteger.ONE)) {
            eBig = BigInteger.valueOf(random.nextInt(phiN.intValue() - 3) + 3);
        }
        e = eBig;

        // Compute the multiplicative inverse of e (mod phi(n)), which is d
        d = e.modInverse(phiN);

        return n;
    }

    public static BigInteger encrypt(BigInteger message, BigInteger n, BigInteger e) {

        return message.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger ciphertext, BigInteger n, BigInteger d) {
        return ciphertext.modPow(d, n);
    }
}