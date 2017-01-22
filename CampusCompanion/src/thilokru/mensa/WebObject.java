package thilokru.mensa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.google.gson.Gson;

/**
 * @author Sinus
 * This Object represents the JSON data structure of the data given by the Uni Bayreuth
 */
public class WebObject {

	/**
	 * Strings may contain HTML
	 */
	public String home, optionen, datum, mehr, standard;
	public String[] date;
	public Termin[] termine;
	public Tipp[] tipps;
	public News[] news;
	public String hauptmensa, frischraum, cafeteria;

	public static WebObject fromWeb(URL sourceUrl) throws IOException {
		String pageText;
		URLConnection conn = sourceUrl.openConnection();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
			pageText = reader.lines().collect(Collectors.joining("\n"));
		}
		Gson gson = new Gson();
		return gson.fromJson(pageText, WebObject.class);
	}
	
	public class Tipp {
		public String img;
	}
	
	public class Termin {
		public int day;
		public String category;
		public String categoryid; //is really a number
		public String eventid; //is really a number
		public String title;
		public String description;
		public String location;
		public String begin;
	}
	
	public class News {
		public String link, hedline, contents;
	}
}
