package no.pk.util;

import no.pk.attributter.Lenker;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderUtil {
    public static void headlessReadcsv(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Lenker.LOGIN_FEIDE);
        HttpResponse response = null;
        List<NameValuePair> postFields = new ArrayList<NameValuePair>(2);

        postFields.add(new BasicNameValuePair("feidename", System.getenv("FEIDE_BRUKER")));
        postFields.add(new BasicNameValuePair("password", System.getenv("FEIDE_PW")));
        post.setEntity(new UrlEncodedFormEntity(postFields, HTTP.UTF_8));
        Authenticator.setDefault (new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication ("username", "password".toCharArray());
            }
        });
        response = client.execute(post);
        EntityUtils.consumeQuietly(response.getEntity());
        String URL = "https://no.timeedit.net/web/hib/db1/student/ri1GZ68Yyga7YYQY5bQ9xmZ1ZQgjnd8cxa0yKYx5YY6a0YgvQy5w655560W66525200.csv";
        HttpGet get = new HttpGet(URL);
        response = client.execute(get);

        HttpEntity entity = response.getEntity();
        InputStream in = entity.getContent();
        for(int i = 0; i < 100; i ++) {
            System.out.println("lol: " + response.getEntity().getContent().read());

        }

        in.close();
        EntityUtils.consumeQuietly(response.getEntity());

    }

    public static ReaderHjelp readCSVInternett(String urlen) throws IOException {
        if (urlen.contains(".html")) {
            urlen = urlen.replace("html", "csv");
        }
        URL url = new URL(urlen);
        System.out.println(url.toString());
        ReaderHjelp reader = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            if (connection.getResponseCode() == 200) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(streamReader);
                String line = br.readLine() + br.readLine() + br.readLine() + br.readLine();
                String[] fieldsene;
                reader = new ReaderHjelp();
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    fieldsene = line.split(",");
                    reader.setOppData(fieldsene);
                }
                br.close();
            } else {
                System.out.println("KOMMER IKKE INN PÅ LENKEN!");
            }
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        return reader;
    }
}
