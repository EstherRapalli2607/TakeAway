# TakeAway
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

**Output**: 
Enter UK Postcode : 
 Below are list of UK PostCode 
'CT12EH - BS14DJ - L40TH - NE97TY
SW1A1AA - CF118AZ - M160RA - EH11RE
BN11AE - CB74DL - LS27HY - G38AG
PL40DW - B263QJ - DH45QZ - BT71NN' 

BS14DJ
********************** 1 ************************************
Name: Pizza GoGo - Bristol 1
Cuisines: Pizza,	American,	Deals
Rating: 3.1
Address: Bristol,11-16 Dighton St, 
Postal Code: BS2 8AJ
********************** 2 ************************************
Name: China Capital
Cuisines: Chinese,	Thai,	Deals,	Freebies
Rating: 4.5
Address: Bristol,24 Ashton Road, 
Postal Code: BS3 2EA
********************** 3 ************************************
Name: Pizza Paradise
Cuisines: Pizza,	Kebab,	Halal,	Low Delivery Fee,	Deals
Rating: 3.5
Address: Bristol,183 Whiteladies Road, 
Postal Code: BS8 2RY
********************** 4 ************************************
Name: Subway - Broad Quay
Cuisines: Sandwiches,	Lunch
Rating: 3.7
Address: Bristol,3b Broad Quay, 
Postal Code: BS1 4DA
********************** 5 ************************************
Name: M.I.S Pizza & Kebab
Cuisines: Pizza,	Kebab,	Collect stamps,	Low Delivery Fee,	Deals,	Freebies
Rating: 4.1
Address: Bristol,29 West Street, 
Postal Code: BS2 0BZ
********************** 6 ************************************
Name: Subway - Union Street
Cuisines: Sandwiches,	Lunch,	Deals,	Freebies
Rating: 2.5
Address: Bristol,31 Union St,, 
Postal Code: BS1 2DJ
********************** 7 ************************************
Name: Franco Manca - Bristol
Cuisines: Pizza,	Italian,	Deals,	Freebies
Rating: 3.9
Address: Bristol,20 Clare Street, 
Postal Code: BS1 1YG
********************** 8 ************************************
Name: Pizza Express - Bristol - Harbourside
Cuisines: Pizza,	Italian,	Low Delivery Fee,	Deals,	Freebies
Rating: 3.6
Address: Harbourside,Unit 1,  Building 8 Explore Lane
City Centre, 
Postal Code: BS1 5TY
********************** 9 ************************************
Name: Tortilla - Bristol Cabot Circus
Cuisines: Mexican,	Burritos,	Halal,	Low Delivery Fee,	Deals,	Freebies
Rating: 4.1
Address: Bristol,SU32b Concorde Street, 
Postal Code: BS1 3BF
********************** 10 ************************************
Name: McDonald's® - Bristol Horsefair
Cuisines: Burgers,	Chicken,	Deals,	Freebies
Rating: 3.1
Address: Bristol,103/105 The Horsefair, 
Postal Code: BS1 3JR
------------------------------------END---------------------------------------
