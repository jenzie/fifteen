/**
 * Proxy to the actual Model on the server.
 *
 * @author Jenny Zhen
 * date: 02.20.14
 * language: Java
 * file: FifteenModelProxy.java
 * assignment: Fifteen
 * http://www.cs.rit.edu/~wrc/courses/csci251/projects/3/
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class FifteenModelProxy implements Runnable, FifteenViewListener {
    private Socket socket; // connection to the server
    private Scanner in; // server-to-client messages
    private PrintStream out; // client-to-server messages
    private FifteenModelListener fifteenML;

    /**
     * Constructor for FifteenModelProxy.
     *
     * @param socket
     */
    public FifteenModelProxy(Socket socket) {
        this.socket = socket;

        // Set up the I/O (input/output) for client-to-server messages.
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(
                    "Error: Connection to the given host and port failed.");
            System.exit(0);
        }
    }
    
    public void setModelListener(FifteenModelListener fifteenML) {
        this.fifteenML = fifteenML;
    }

    /**
     * Client-to-server message:
     * join n
     * Sent when the client starts up; n is replaced with the player's name.
     *
     * @param playerName username of the player.
     */
    public void joinServer(String playerName) {
        out.println("join " + playerName);
        out.flush();
    }

    /**
     * Client-to-server message:
     * digit d
     * Sent when the player clicks a number button;
     * d is replaced with the number (1 to 9).
     *
     * @param digit the value the player played.
     */
    public void digitServer(int digit) {
        out.println("digit " + digit);
        out.flush();
    }

    /**
     * Client-to-server message:
     * newgame
     * Sent when the player clicks the New Game button.
     */
    public void newgameServer() {
        out.println("newgame");
        out.flush();
    }

    /**
     * Client-to-server message:
     * quit
     * Sent when the player closes the window.
     */
    public void quitServer() {
        out.close();
        in.close();
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println(
                    "Error: Connection to the given host and port failed.");
            System.exit(0);
        }
    }

    @Override
    public void run() {
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] message = line.split(" ");

            if (message[0].equals("id")) {
                fifteenML.setID(Integer.parseInt(message[1]));
            } else if (message[0].equals("name")) {
                fifteenML.setName(Integer.parseInt(message[1]), message[2]);
            } else if (message[0].equals("digits")) {
                fifteenML.setDigits(message[1]);
            } else if (message[0].equals("score")) {
                fifteenML.setScore(Integer.parseInt(message[1]),
                        Integer.parseInt(message[2]));
            } else if (message[0].equals("turn")) {
                fifteenML.setTurn(Integer.parseInt(message[1]));
            } else if (message[0].equals("win")) {
                fifteenML.setWin(Integer.parseInt(message[1]));
            } else if (message[0].equals("quit")) {
                fifteenML.quit();
            } else {
                System.err.println(
                        "Error: Invalid server-to-client message.");
                System.exit(0);
            }
        }
    }

    @Override
    public void newgame() {
        newgameServer();
    }

    @Override
    public void setDigit(int digit) {
        digitServer(digit);
    }

    @Override
    public void quit() {
        out.println("quit");
        out.flush();
        quitServer();
    }
}
