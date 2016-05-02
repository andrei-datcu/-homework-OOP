package urlreaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class URLReader {

    public static String getLines(String searchTemplate, String param) throws IOException, MalformedURLException{
        String lines = null;
        URL showURL = new URL(String.format(searchTemplate, param).replaceAll(" +", "%20"));
        HttpURLConnection connection = (HttpURLConnection) showURL.openConnection();
        lines = readFromConnection(connection);
        return lines;
    }

    /**
     * Intoarce liniile pe care le poate citi de la o conexiune deja stabilita
     * @param connection Conexiunea Http
     * @return Lista de stringuri. Fiecare string reprezinta o linie.
     * @throws IOException daca ne poate optine fluxul de intrare de la conexiune
     */
    private static String readFromConnection(HttpURLConnection connection) throws IOException {
        // Se obtine input stream-ul asociat conexiunii
        InputStream urlInputStream = connection.getInputStream();

        InputStreamReader urlInputReader = new InputStreamReader(urlInputStream);
        BufferedReader connectionReader = new BufferedReader(urlInputReader);

        String lines = "";
        String line;

        while ((line = connectionReader.readLine()) != null) {
            lines+= line + "\n";
        }

        return lines;
    }
}