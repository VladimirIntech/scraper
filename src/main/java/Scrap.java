import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrap {
    static int NumberOfCycles = 1000;


    public static String createRandomString() {
        String mCHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int STR_LENGTH = 6; // длина генерируемой строки
        String stt = null;
        Random random = new Random();
        StringBuilder builderTwo = new StringBuilder();
        List<String> url = new ArrayList<String>();
        // for (int j = 0; j <= NumberOfCycles; j++) {
        for (int i = 0; i < STR_LENGTH; i++) {
            int number = random.nextInt(mCHAR.length());
            char ch = mCHAR.charAt(number);
            builderTwo.append(ch);
            //    }
            // url.add("https://prnt.sc/" + builderTwo.toString());
            //  builderTwo.delete(0, builderTwo.length());
        }
        return "https://prnt.sc/" + builderTwo.toString();
    }

    public static void downloadFiles(String strURL, String strPath, int buffSize) {
        try {
            URL connection = new URL(strURL);
            HttpURLConnection urlconn;
            urlconn = (HttpURLConnection) connection.openConnection();
            urlconn.setRequestMethod("GET");
            urlconn.connect();
            InputStream in = null;
            in = urlconn.getInputStream();
            //OutputStream writer = new FileOutputStream(strPath);
            FileOutputStream writer = new FileOutputStream(strPath);
            byte buffer[] = new byte[buffSize];
            int c = in.read(buffer);
            while (c > 0) {
                writer.write(buffer, 0, c);
                c = in.read(buffer);
            }
            writer.flush();
            writer.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public static List<String> parser() throws IOException {

        Document doc;
        doc = Jsoup.connect(String.format(createRandomString())).get();
        Elements html = doc.getElementsByAttributeValueContaining("src", "https://image.prntscr.com/image/");
        List<String> arrTwo = new ArrayList<String>();
        Pattern pattern = Pattern.compile("https://image.prntscr.com/image/.+? ");
        String textSub = null;
        try {
            for (int i = 0; i < NumberOfCycles; i++) {
                if (html.isEmpty() == false) {
                    for (Element tetxHtml : html) {
                        textSub = tetxHtml.toString();
                        Matcher matcher = pattern.matcher(textSub);
                        while (matcher.find()) {
                            arrTwo.add(i, textSub.substring(matcher.start(), matcher.end()));
                        }
                    }
                } else {
                    arrTwo.add(i, "Ссылка пуста " + createRandomString());
                }
            }
        } catch (
                Exception e) {
            System.out.println(e);
            ;
        }

        return arrTwo;
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < NumberOfCycles; i++) {
            downloadFiles(parser().get(i), "img/" + new Random().toString() + ".png", 100);
            //parser().get(i);
        }
    }

}
