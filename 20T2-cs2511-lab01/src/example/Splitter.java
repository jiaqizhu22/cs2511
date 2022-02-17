package example;

import java.util.Scanner;

public class Splitter {

    public static void main(String[] args){
        System.out.println("Enter a sentence specified by spaces only: ");
        // Add your code
        Scanner s = new Scanner(System.in);
        String[] words = s.nextLine().split(" ");
        s.close();
        for (String word : words) {
            System.out.println(word);
        }
    }
}
