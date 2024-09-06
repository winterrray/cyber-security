package RSA;
import java.util.Scanner;

public class RSA {
	public static int expo(int a, int b) {
		int expo = 1;
		for (int i=0; i<b; i++) expo*=a;
		return expo;
	}
	
	public static int gcd (int a, int b) {
		int temp;
		while(true) {
			temp = a % b;
			if(temp == 0) return b;
			a=b;
			b=temp;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter 2 primes : ");
		int p = sc.nextInt();
		int q = sc.nextInt();
		System.out.println("(p,q) -> " + p + "," + q);
		
		int n = p*q;
		int e=2;
		int phi = (p-1)*(q-1);
		while(e<phi) {
			// e - coprime of phi & e < phi
			if (gcd(e,phi) == 1) break;
			else e++;
		}
		
		int k=2;
		int d = (k*phi + 1) / e;

		System.out.print("Enter msg to be encrypted : ");
		int msg = sc.nextInt();
		System.out.println("Plaintext -> " + msg);

		// encrpytion
		// cipher = (msg ^ e) % n
		int cipher = expo(msg,e);
		cipher %= n;
		System.out.println("Cipher    -> " + cipher);

		// decryption
		// m = (c^d) % n;
		int m = expo(cipher,d);
		m %= n;
		System.out.println("Decryted  -> " + m);

		sc.close();
	}
}