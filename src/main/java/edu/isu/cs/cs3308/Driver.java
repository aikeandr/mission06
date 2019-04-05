package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.impl.SetSpellChecker;
import java.util.List;
import java.util.Scanner;

/**
 * Console program which uses a simple spell check on user input.
 *
 * @author Andrew Aikens
 */
public class Driver {

    public static void main(String args[]) {
        SpellChecker dictionary = new SetSpellChecker("data/en-US.dic");       //loads the dictionary into a map
        List<String> output;
        System.out.println("To end session, exit with code \"0\"\n");
        System.out.print("Enter a sentence to check: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(!input.equals("0")) {
            output = dictionary.check(input);
            for (String out : output) {
                System.out.print(out);
            }
            System.out.print("\nEnter a sentence to check: ");
            input = scanner.nextLine();
        }
    }
}
