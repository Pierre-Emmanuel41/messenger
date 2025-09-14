package fr.pederobien.messenger.example;

import fr.pederobien.messenger.example.client.MyCustomTcpProtocolClient;
import fr.pederobien.messenger.example.server.MyCustomTcpProtocolServer;
import fr.pederobien.messenger.example.wrappers.Player;
import fr.pederobien.utils.event.Logger;

public class Example {

    public static void main(String[] args) {
        Logger.instance().newLine(true).timeStamp(true);
        MyCustomTcpProtocolServer server = new MyCustomTcpProtocolServer();
        server.open();

        MyCustomTcpProtocolClient client = new MyCustomTcpProtocolClient();
        client.connect();

        sleep(2000);

        client.send("Client 123456");

        sleep(1000);

        client.send(987654);

        sleep(1000);

        client.send(5.0f);

        sleep(1000);

        client.send(new Player("Pierre", 35));

        sleep(2000);

        client.disconnect();
        client.dispose();

        sleep(500);

        server.close();
        server.dispose();
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
