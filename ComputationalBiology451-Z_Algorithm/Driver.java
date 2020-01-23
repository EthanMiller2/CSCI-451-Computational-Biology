
import java.io.FileNotFoundException;

import java.io.FileReader;

import java.util.ArrayList;

import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		String pattern = args[0];
		String FILEPATH = args[1];
		String temp = "";
		String line;
		String patternAndText;
		ArrayList<String> text = new ArrayList<String>();
		
		try {
			Scanner read = new Scanner(new FileReader(FILEPATH));
			while (read.hasNext()) {
				line = read.nextLine();
				if (!checkLetter(line)) {
					temp = temp + line;
				}
				else {
					text.add(temp);
					temp = "";
				}
			}
			text.add(temp);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String s : text) {
			patternAndText = pattern + "$" + s;
			int[] z = new int[patternAndText.length()];
			ZArr(patternAndText, z);
			
			for (int i = 0; i < patternAndText.length(); i++)
			{
				if (z[i] == pattern.length())
				{
					System.out.println("Matching string found at position " + i);
				}
			}
		}
	}
	
	private static boolean checkLetter(String text) {
		return text.charAt(0) == '>';
	}
	
	private static void ZArr(String text, int[] z) {
		z[0] = 0;
		int l = 0;
		int r = 0;
		int n = text.length();
		if (l == 0 && r == 0)
		{
			if (text.charAt(0) == text.charAt(1)) {
				z[1] = 1;
				if (z[1] > 0) {
					l = 2;
					r = z[1] + 1;
				}
			}
		}
		for (int k = 2; k < n - 1; k++) {
			if (k > r) {
				l = k;
				r = k;
				
				while (r < n && text.charAt(r - l) == text.charAt(r)) {
					r++;
					z[k]++;
				}
				
				if (z[k] > 0) {
					l = k;
					r = z[k] + k - 1;
				}
			}

			else {
				int j = k - l + 1;
				int beta = r - k;
				if (j < beta) {
					z[k] = z[j];
				}

				else {
					l = k;
					while (r < n && text.charAt(r - l) == text.charAt(r)) {
						r++;
						z[k] = r - l;
						r--;
					}
				}
			}
		}
	}
}
