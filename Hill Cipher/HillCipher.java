import java.util.Scanner;

public class HillCipher {
    public static float mod(float a, float b) {
        return (a%b+b)%b;
    }

    public static float[][] inverseMatrix(int[][] matrixKey) {
        float p, q;
        float[][]identity = new float[3][3];
        float[][]m = new float[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j=0; j<3; j++) {
                m[i][j] = (int) (matrixKey[i][j]);
            }
        }

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
               if(i == j)
                  identity[i][j]=1;
               else
                  identity[i][j]=0;
            }
        }

        for(int k = 0; k < 3; k++) {
           for(int i = 0; i < 3; i++) {
              p = m[i][k];
              q = m[k][k];
              for(int j = 0; j < 3; j++) {
                 if(i != k) {
                    m[i][j] = m[i][j]*q - p*m[k][j];
                    identity[i][j] = identity[i][j]*q - p*identity[k][j];
                 }
              }
           }
        }
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                identity[i][j] = identity[i][j] / m[i][i];
            }
        }

        System.out.println ("\nInverse Matrix is :");
        for(int i = 0; i < 3; i++) {
           for(int j = 0; j < 3; j++)
           System.out.print(identity[i][j] + " ");
           System.out.println();
        }

        return identity;
     }

	public static int toNum (char ch) {
		return (int)ch - 65;
	}

	public static char toChar (int num) {
		return (char)(num + 65);
	}

//____________________________________________________________________________________________

	public static StringBuilder decrypt (int[] cipherMatrix, int[][] matrixKey) {
		StringBuilder text = new StringBuilder();

        // find inverse of key matrix
        float[][] inverse = inverseMatrix(matrixKey);
        float[] decrypted = {0,0,0};

        // inverse(key matrix) * cipher
        System.out.println();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                decrypted[i] += inverse[i][j]*cipherMatrix[j];
            }
            decrypted[i] = mod(decrypted[i], 26);
            System.out.print((int)decrypted[i] + " ");
        } System.out.println("\n");
        
        for (int i = 0; i < 3; i++) {
            text.append((char)((int)(decrypted[i]) + 65));
        }

		return text;
	}

//_____________________________________________________________________________________________

	public static int[] encrypt (StringBuilder text, int[][] matrixKey) {
		int[] numText = new int[3];
		

		System.out.print("\nplaintext toNum -> ");
		for (int i=0; i<3; i++) {
			numText[i] = toNum(text.charAt(i));
			System.out.print(numText[i] + " ");
		}
        System.out.println("\n");

		// matrix multiplication text*key
		// matrixKey x numText

		int[] result = {0,0,0};
		
        System.out.print("Cipher matrix : ");
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				result[i] += matrixKey[i][j] * numText[j];
			}
			// mod 26
			result[i] %= 26; 
			System.out.print(result[i] + " ");
		} System.out.println();
        return result;
	}

//_____________________________________________________________________________________________

	public static void main (String[] args)  {
		StringBuilder text = new StringBuilder();

//		text.append("ACT");
//		key.append("GYBNQKURP");

		Scanner sc = new Scanner(System.in);		

		System.out.print("Enter text : ");
		String strtext = sc.nextLine();
		text.append(strtext);
		System.out.println("Enter key matrix : ");
        	int[][]matrixKey = new int[3][3];
	        for (int i = 0; i < 3; i++) {
	        	for (int j = 0; j < 3; j++) {
		                matrixKey[i][j] = sc.nextInt();
            		}
        	}

		int[] cipherMatrix = encrypt(text, matrixKey);
        StringBuilder cipher = new StringBuilder();
		for (int i=0; i<3; i++) {
			cipher.append(toChar(cipherMatrix[i]));
		}
		System.out.println("cipher -> " + cipher);

        StringBuilder decryptedText = decrypt(cipherMatrix, matrixKey);
        System.out.println("decrypted -> " + decryptedText);		

        sc.close();
	}
}