package TakeAwayAssignment;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class RestaurantFinder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter UK Postcode : \n Below are list of UK PostCode \n'CT12EH - BS14DJ - L40TH - NE97TY\r\n"
				+ "SW1A1AA - CF118AZ - M160RA - EH11RE\r\n"
				+ "BN11AE - CB74DL - LS27HY - G38AG\r\n"
				+ "PL40DW - B263QJ - DH45QZ - BT71NN' \n");
		String postcode = sc.nextLine().trim();
		sc.close();
		fetchRestaurantData(postcode);
		
	}

	private static void fetchRestaurantData(String postcode) {
		// TODO Auto-generated method stub
		try {
			
			URL url = new URL("https://uk.api.just-eat.io/discovery/uk/restaurants/enriched/bypostcode/" + postcode);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();
			
			//Read response data into String
			Scanner scanner = new Scanner(url.openStream());
			StringBuilder response = new StringBuilder();
			while(scanner.hasNext()) {
				
				response.append(scanner.nextLine());
				
			}
			scanner.close();
			
			//Parse JSON response
			JSONObject jsonResponse = new JSONObject(response.toString());
			JSONArray restaurants = jsonResponse.getJSONArray("restaurants");
			
			//Display information for the first 10 restaurants
			for (int i =0; i<Math.min(10, restaurants.length()); i++) {
				System.out.println("********************** " + (i+1) + " ************************************");
				JSONObject restaurant = restaurants.getJSONObject(i);
				System.out.println("Name: " + restaurant.getString("name"));
				System.out.print("Cuisines: ");
				JSONArray cuisines = restaurant.getJSONArray("cuisines");
				for(int j =0; j <cuisines.length(); j++) {
					
					System.out.print(cuisines.getJSONObject(j).getString("name"));
					if (j < cuisines.length() - 1) {
						System.out.print(",\t");
					}
					
				}
				
				System.out.println();
				JSONObject rating = restaurant.getJSONObject("rating");
				System.out.println("Rating: "+ rating.getDouble("starRating"));
				JSONObject address = restaurant.getJSONObject("address");
				System.out.println("Address: " + address.getString("city") + "," + address.getString("firstLine") + ", \nPostal Code: " + address.getString("postalCode"));
				
			}
			System.out.println("------------------------------------END---------------------------------------");
			conn.disconnect();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
