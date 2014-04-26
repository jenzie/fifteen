/**
 * @author Jenny Zhen
 * date: 02.20.14
 * language: Java
 * file: Fifteen.java
 * assignment: Fifteen
 * http://www.cs.rit.edu/~wrc/courses/csci251/projects/3/
 */

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Fifteen {
    public static void main(String[] args) {
        // Check command line arguments.
        if(args.length != 3) {
            System.err.println(
                    "Usage: java Fifteen <playerName> <host> <port>");
            System.exit(0);
        }

        // Sanitize and retrieve arguments.
        String host = args[1].trim();
        int port = -1;
        try {
            port = Integer.parseInt(args[2].trim());
        }
        catch (NumberFormatException e) {
            System.err.println(
                    "Error: Given port is not an integer value.");
            System.err.println(
                    "Usage: java Fifteen <playerName> <host> <port>");
            System.exit(0);
        }

        // Check the hostname and port number, and attempt to connect.
        Socket socket;
        try {
            socket = new Socket(host, port);
        }
        catch (UnknownHostException e) {
            System.err.println(
                    "Error: Given host is unknown.");
            System.err.println(
                    "Usage: java Fifteen <playerName> <host> <port>");
            System.exit(0);
        }
        catch (IOException e) {
            System.err.println(
                    "Error: Connection to the given host and port failed.");
            System.err.println(
                    "Usage: java Fifteen <playerName> <host> <port>");
            System.exit(0);
        }
    }
}
