package me.scifi.rin;

import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.scifi.rin.gui.RinGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;


@Getter @Setter
public final class Rin {

   @Getter private final static JsonParser jsonParser = new JsonParser();

   @Getter @Setter private static int amount;

   @Getter  private final Rin instance = new Rin();

   private static String directory;

    public static void main(final String[] args) {
        System.out.println(getLink());
       File file = new File(System.getProperty("user.home"), "/Desktop/homework");

       if (!file.isDirectory()) {
           file.mkdir();
       }

       directory = file.getAbsolutePath();

       new RinGui("Test");
    }

    @SneakyThrows
    public static void writeImages() {
        for (int i = 0; i < getAmount(); i++) {

            String link = getLink();

            System.out.println(i + 1 + ". " + link);

            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

            BufferedImage bufferedImage = ImageIO.read(urlConnection.getInputStream());
            ImageIO.write(bufferedImage, "png", new File(directory + "/" + UUID.randomUUID() + "-OOF" +".png"));
        }
    }

    @SneakyThrows
    public static final String getLink() {

        URL url = new URL("https://nekos.life/api/v2/img/lewd");
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

        InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());

        return jsonParser.parse(inputStreamReader).getAsJsonObject().get("url").getAsString();

    }

}
