package thilokru.mensa;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MensaDataRetriever implements IMensa{

	private WebObject obj;
	
	public MensaDataRetriever(URL sourceUrl) throws IOException {
		obj = WebObject.fromWeb(sourceUrl);
	}
	
	@Override
	public List<Date> getAvailableDates() {
		List<Date> dates = new ArrayList<Date>();
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		for(String desc:obj.date) {
			try {
				dates.add(format.parse(desc));
			} catch (ParseException e) {
				System.err.println(String.format("Got an invalid date string: %s", desc));
				e.printStackTrace();
			}
		}
		return dates;
	}

	@Override
	public Map<String, Map<String, Double>> getFoodPriceList(Date date, FoodSource source, Role role) {
		String htmlSource = null;
		switch(source) {
		case MENSA:
			htmlSource = obj.hauptmensa;
			break;
		case CAFETERIA:
			htmlSource = obj.cafeteria;
			break;
		case FRISCHRAUM:
			htmlSource = obj.frischraum;
		}
		
		int dateID = -1;
		String dateString = new SimpleDateFormat("dd.MM.yyyy").format(date);
		for(int i = 0; i < obj.date.length; i++) {
			if(obj.date[i].equals(dateString)) {
				dateID = i;
				break;
			}
		}
		if(dateID == -1) {
			return null;
		}
		
		Map<String, Map<String, Double>> retVal = new HashMap<String, Map<String, Double>>();
		
		Document doc = Jsoup.parse(htmlSource);
		List<Element> elements = doc.getElementsByAttributeValue("data-type", Integer.toString(dateID));
		String currentCategory = "Standard";
		for(Element child:elements) {
			Element firstChild = child.child(0);
			if(firstChild.attr("class").equals("zutat")) {
				currentCategory = firstChild.text();
			} else if(firstChild.attr("class").contains("gericht")) {
				String desc = improveFormat(firstChild.text());
				
				String priceString = child.child(1).getElementsByAttributeValue("class", role.getRoleName()).get(0).text();
				priceString = priceString.substring(2).replace(',', '.');
				double price = Double.parseDouble(priceString);
				
				addToList(retVal, currentCategory, desc, price);
			}
		}
		if(retVal.isEmpty())return null;
		return retVal;
	}
	
	private static void addToList(Map<String, Map<String, Double>> retVal, String currentCategory, String desc, double price) {
		Map<String, Double> mealMap = retVal.get(currentCategory);
		if(mealMap == null) {
			mealMap = new HashMap<String, Double>();
			retVal.put(currentCategory, mealMap);
		}
		mealMap.put(desc, price);
	}

	private static String improveFormat(String mealString) {
		StringBuilder builder = new StringBuilder(mealString);
		while(builder.indexOf("\\") != -1) {
			builder.deleteCharAt(builder.indexOf("\\"));
		}
		return builder.toString();
	}

}
