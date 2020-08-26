package me.scifi.rin;

import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.scifi.rin.gui.RinGui;
import me.scifi.rin.utils.FileWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@Getter
@Setter
public final class Rin {

    @Getter
    private final static JsonParser jsonParser = new JsonParser();

    @Getter
    @Setter
    private static int amount;

    @Getter
    private final Rin instance = new Rin();

    @Getter
    private static String directory;

    @Getter
    private static FileWriter fileWriter;

    @Getter
    private static List<String> links = new ArrayList<>();

    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    static {
        File file = new File(System.getProperty("user.home"), "/Desktop/homework");

        if (!file.isDirectory()) {
            file.mkdir();
        }

        directory = file.getAbsolutePath();
    }

    public static void main(final String[] args) {
        fileWriter = new FileWriter();
        System.out.println(getLink());
        new RinGui("Test");
    }

    @SneakyThrows
    public static void writeImages() {
        for (int i = 0; i < getAmount(); i++) {

            String link = getLink();

            System.out.println(i + 1 + ". " + link);

            pool.submit(() -> {
                try {
                    URL url = new URL(link);
                    URLConnection urlConnection = url.openConnection();
                    urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

                    BufferedImage bufferedImage = ImageIO.read(urlConnection.getInputStream());

                    ImageIO.write(bufferedImage, "png", new File(directory + "/" + UUID.randomUUID() + "-OOF" + ".png"));

                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });

            links.add(link);
        }

        fileWriter.write(links);
    }

    @SneakyThrows
    public static String getLink() {

        URLConnection urlConnection = new URL("https://nekos.life/api/v2/img/lewd").openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());

        return jsonParser.parse(inputStreamReader).getAsJsonObject().get("url").getAsString();

    }

    public static void addLink(String link) {
        links.add(link);
    }

}
