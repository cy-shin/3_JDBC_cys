package prac.cy.temp;

public class Temp {
	public static void main(String[] args) {
		char A = 'A'; // 65
		char Z = 'Z'; // 90
		char a = 'a'; // 97
		char z = 'z'; // 122
		char zero = '0'; // 48
		char nine = '9'; // 57
		
		System.out.println((int)nine);
		
		int ran = (int)(Math.random() * 90000) + 10000;
		System.out.println(ran);
	}
}
