package thilokru.mensa;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IMensa {

	/**
	 * @return a list of all dates for which food data is available
	 */
	public List<Date> getAvailableDates();
	
	/**
	 * 
	 * @param date The date for which the food data is retrieved
	 * @param source The place where the food is offered
	 * @param role The role of the requester, determines the prices returned
	 * @return A map mapping the type of food to a map mapping the food description to its price
	 * 		null when no data is available
	 */
	public Map<String, Map<String, Double>> getFoodPriceList(Date date, FoodSource source, Role role);
	
	public enum Role {
		STUDENT("preis0"), WORKER("preis1"), GUEST("preis2"), OTHER("preis3");
		
		private String roleName;
		
		private Role(String name) {
			this.roleName = name;
		}
		
		public String getRoleName() {
			return roleName;
		}
	}
	
	public enum FoodSource {
		MENSA, CAFETERIA, FRISCHRAUM;
	}
}
