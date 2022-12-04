package helpers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Helpers {

    public static int randomInt20(){
    Random rand = new Random();
    return rand.nextInt((20 - 1) + 1) + 1;
    }

    public static boolean checkServer() throws IOException {
        URL url = new URL("http://localhost:4000");
        HttpURLConnection openConnection = (HttpURLConnection) url.openConnection();
        try {
            openConnection.connect();
        } catch (Exception exception){
            return false;
        }
        return true;
    }
}
