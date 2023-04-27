import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SyncSongLyricsRetriever {

    private String getLyricsSync(String artist, String song) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newBuilder().build();

        URI uri = new URI("https", "api.lyrics.ovh", "/v1/" + artist + "/" + song, null);
        System.out.println(uri);

        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        System.out.println(new SyncSongLyricsRetriever().getLyricsSync("The Weeknd", "Starboy"));
    }
}
