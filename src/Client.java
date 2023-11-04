import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
             try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

                 String serverResponse;
                 String pressEnter;

            while ((serverResponse = in.readLine()) != null) {
                clearConsole();
                System.out.println(serverResponse);
                if (serverResponse.startsWith("Enter your username:")) {
                    String username = userInput.readLine();
                    out.println(username);
                    out.flush();
                } else if (serverResponse.startsWith("Welcome")) {
                    boolean loggedIn = true;
                    while (loggedIn) {
                        clearConsole();
                        System.out.println("Options:");
                        System.out.println("1. View messages");
                        System.out.println("2. Send a message");
                        System.out.println("3. View online users");
                        System.out.println("4. View Tags");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");

                        String choice = userInput.readLine();

                        switch (choice) {
                            case "1":
                                clearConsole();
                                out.println("viewmessages");
                                out.flush();
                                String messagesResponse;
                                while (!(messagesResponse = in.readLine()).equals("End of messages.")) {
                                    if (messagesResponse.equals("You have no messages.")) {
                                        System.out.println(messagesResponse);
                                        System.out.println("\nPress ENTER to return to home:");
                                        pressEnter = userInput.readLine();
                                        break;
                                    }
                                    System.out.println(messagesResponse);

                                }
                                System.out.println("\nPress ENTER to return to home:");
                                pressEnter = userInput.readLine();
                                break;
                            case "2":
                                clearConsole();
                                System.out.print("Enter recipient's username: ");
                                String recipient = userInput.readLine();
                                System.out.print("Enter the title: ");
                                String title = userInput.readLine();
                                System.out.print("Enter your message: ");
                                String message = userInput.readLine();
                                out.println("newmessage:{to}" + recipient + "{to;}{title}" + title + "{title;}{body}" + message + "{body;}");
                                out.flush();
                                String sentStatus = in.readLine();
                                System.out.println(sentStatus);
                                System.out.println("\nPress ENTER to return to home:");
                                pressEnter = userInput.readLine();
                                break;
                            case "3":
                                clearConsole();
                                out.println("listusers");
                                out.flush();
                                String usersResponse;
                                while (!(usersResponse = in.readLine()).equals("End of users list.")) {
                                    System.out.println(usersResponse);
                                }
                                System.out.println("\nPress ENTER to return to home:");
                                pressEnter = userInput.readLine();
                                break;
                            case "4":
                                clearConsole();
                                out.println("viewtags");
                                out.flush();
                                String tagsResponse;
                                while (!(tagsResponse = in.readLine()).equals("End of tags list.")) {
                                    System.out.println(tagsResponse);
                                }
                                System.out.println("\nPress ENTER to return to home:");
                                pressEnter = userInput.readLine();
                                break;
                            case "5":
                                clearConsole();
                                out.println("exit");
                                out.flush();
                                loggedIn = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }
                    }
                } else if (serverResponse.equals("Goodbye!")) {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder = new ProcessBuilder();
            if (os.contains("win")) {
                processBuilder.command("cmd", "/c", "cls");
            } else {
                processBuilder.command("clear");
            }
            processBuilder.inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
