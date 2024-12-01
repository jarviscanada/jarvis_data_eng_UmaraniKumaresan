package ca.jrvs.apps.jdbcPractice.util;

import ca.jrvs.apps.stockquote.DAO.ConnectionFactory;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Sample {


    public static void main(String[] args) {
        // Create a Scanner object for interactive input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter your name: ");
        String name = scanner.nextLine(); // Read a line of text

        System.out.print("Enter your age: ");
        int age = scanner.nextInt(); // Read an integer

        System.out.print("Enter your favorite number: ");
        double favoriteNumber = scanner.nextDouble(); // Read a double

        // Output the collected data
        System.out.println("\nHello, " + name + "!");
        System.out.println("You are " + age + " years old.");
        System.out.println("Your favorite number is " + favoriteNumber + ".");

        // Close the scanner
        scanner.close();
    }
}