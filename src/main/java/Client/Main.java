package Client;

import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        Client client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
