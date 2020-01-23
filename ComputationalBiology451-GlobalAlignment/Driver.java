import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Driver {
	
	public static void main(String[] args) {
//		String s = "-CACTAG";
//		String t = "-CAATGGG";
//		int match = 2;
//		int mismatch = 0;
//		int insert = -1;
//		
		
		int match = Integer.parseInt(args[0]);
		int mismatch = Integer.parseInt(args[1]);
		int insert = Integer.parseInt(args[2]);
		String FILEPATH = args[3];
		String s = "-", t = "-", temp = "";
		int count = 0;
		try {
			Scanner read = new Scanner(new FileReader(FILEPATH));
			while (read.hasNext()) {
				temp = read.nextLine();
				if (!checkLetter(temp)) {
					if(count == 0) {
						s = s + temp;
						count ++;
					}
					else {
						t = t + temp;
					}
					
				}
				else {
					
				
				}
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}

		int maxAlignScore = getMaxAlignScore(s ,t, match, mismatch, insert);
	}
	
	public static int getMaxAlignScore(String s, String t, int match, int mismatch, int insert) {
		 int n = s.length();
		 int m = t.length();
		 
		 String s1 = "";
		 String t1 = "";
		 
		 int topScore;
		 int leftScore;
		 int diagonalScore;
		 
		 int alignArr[][] = new int[n][m];
		 alignArr[0][0] = 0;
		 for(int i=1; i<n; i++) {
			 alignArr[i][0] = i*insert;
		 }
		 for(int j=1; j<m; j++) {
			 alignArr[0][j] = j*insert; 
		 }
		 
		 for(int i=1; i<n; i++) {
			 for(int j=1; j<m; j++){
				 topScore = alignArr[i][j-1] + insert;
				 leftScore = alignArr[i-1][j] + insert;
				 
				 if (s.charAt(i) == t.charAt(j)) {
					 diagonalScore = alignArr[i-1][j-1] + match;
				 } else {
					 diagonalScore = alignArr[i-1][j-1] + mismatch;
				 }
				 alignArr[i][j] = Math.max(Math.max(topScore, leftScore), diagonalScore);		 
			 }
		 }
		 
		 System.out.println("Dynamic Programing Table:");
		 for(int i=0; i<n; i++) {
			 for(int j=0; j<m; j++){
				 System.out.print(" "+alignArr[i][j]+ " ");
			 }
			 System.out.println();
		 }
		 System.out.println("");
		 int i = n-1;
		 int j = m-1;
		 while(i != 0 && j !=0)
		 {
			 topScore = alignArr[i][j-1] + insert;
			 leftScore = alignArr[i-1][j] + insert;
			 
			 if (s.charAt(i) == t.charAt(j)) {
				 diagonalScore = alignArr[i-1][j-1] + match;
			 } else {
				 diagonalScore = alignArr[i-1][j-1] + mismatch;
			 }
			 if(s.charAt(i-1) == t.charAt(j-1)) {
				 s1 = s1+s.charAt(i);
				 t1 = t1+t.charAt(j);
				 i --;
				 j --;
			 } else if(leftScore == alignArr[i][j]) {
				 t1=t1+"-";
				 s1 = s1+s.charAt(i);
				 i --;
			 } else if(topScore == alignArr[i][j]) {
				 t1 = t1+t.charAt(j);
				 s1=s1+"-";
				 j --;
			 } else {
				 s1 = s1+s.charAt(i);
				 t1 = t1+t.charAt(j);
				 i --;
				 j --;
				 }
		 }
		 while(i != -1 && j !=-1) {
			 if(j==0) {
				 s1 = s1+s.charAt(i);
				 t1=t1+"-";
				 j --;
			 }
			 else if(i==0) {
				 s1=s1+"-";
				 t1 = t1+s.charAt(i);
				 i --;
			 }
		 }
		 StringBuilder sb1 = new StringBuilder(); 
         sb1.append(s1); 
         sb1 = sb1.reverse();  
         StringBuilder sb2 = new StringBuilder();   
         sb2.append(t1);
         sb2 = sb2.reverse();  
		 s1 = sb1.toString();
		 t1 = sb2.toString();
		 
		 if(s1.charAt(0)=='-' && t1.charAt(0)=='-') {
			 s1 = s1.substring(1);
			 t1 = t1.substring(1);
		 }
			 
		 
		 System.out.println("Global Alignment Score: " +  alignArr[n-1][m-1]);
		 System.out.println("Alignment: ");
		 System.out.println("S' : "+ s1);
		 System.out.println("T' : "+ t1);
		 
		 return alignArr[n-1][m-1];	 
	}
	
	private static boolean checkLetter(String text) {
		return text.charAt(0) == '>';
	}
	

}
