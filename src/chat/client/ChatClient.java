package chat.client;

import java.io.IOException;
import java.net.InetAddress;

public class ChatClient {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        InetAddress host = InetAddress.getLocalHost();
        System.out.println(host.getHostAddress());
    }
}
