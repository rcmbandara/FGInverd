package db;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

public class GetDBData {
	public static void main(String[] args) throws IOException {

		 String username = null;
		 String passWord = null;
		 String ConString = null;
		int lineNo;
		try {
			FileReader fr = new FileReader("C:\\Program Files\\MySQL\\MySQL Server 5.7\\DBUtil.txt");
			BufferedReader br = new BufferedReader(fr);
			for (lineNo = 1; lineNo < 10; lineNo++) {
				if (lineNo == 1) {
					ConString = br.readLine();
				}else if(lineNo ==2) {
					username =br.readLine();
				}else if(lineNo ==3) {
					passWord= br.readLine();
				} else
					br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
