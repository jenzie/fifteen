import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Proxy to the actual Model on the server.
 *
 * @author Jenny Zhen
 * date: 02.20.14  * language: Java
 * file: Fiftee.java
 * assignment: Fifteen
 * http://www.cs.rit.edu/~wrc/courses/csci251/projects/3/
 */

public class FifteenModelProxy {
    private Socket socket;
    private Scanner in;
    private PrintStream out;

    public FifteenModelProxy(Socket socket) {
        this.socket = socket;

        // Set up the i/o for client-to-server messages.
        try {
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintStream(socket.getOutputStream());
        }
        catch (IOException e) {
            System.err.println(
                    "Error: Connection to the given host and port failed.");
            System.exit(0);
        }
    }

    public void joinServer(String playerName) {
        out.println("join " + playerName);
        out.flush();
    }

    public void digitServer(int digit) {
        out.println("digit " + digit);
        out.flush();
    }

    public void newgameServer() {
        out.println("newgame");
        out.flush();
    }

    public void quitServer() {
        out.println("quit");
        out.close();
        in.close();
        try {
            socket.close();
        }
        catch (IOException e ) {
            System.err.println(
                    "Error: Connection to the given host and port failed.");
            System.exit(0);
        }
    }
}
