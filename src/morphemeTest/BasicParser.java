package morphemeTest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.htmlparser.jericho.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;


public class BasicParser {

	private static String s;
	private static double scoreAverage = 0.0;
	
	public static double getEwgScore(){		
		return scoreAverage * 100.0;	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			//URL url = new URL("http://www.ewg.org/skindeep/product/596621/Calvin_Klein_CK_Be_Eau_de_Toilette/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/669277/Estee_Lauder_Crescent_White_UV_Protector%2C_SPF_50/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/702662/Missha_Time_Revolution_Night_Repair/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619842/Clinique_Moisture_Surge_CC_Cream%2C_SPF_30_%282015_formulation%29/");
			URL url = new URL("http://www.ewg.org/skindeep/product/467011/Thayers_Alcohol-Free_Witch_Hazel_with_Organic_Aloe_Vera_Formula_Toner%2C_Unscented/");
			
			//URL url = new URL("http://www.ewg.org/skindeep/product/619760/Clinique_Superdefense_Daily_Defense_Moisturizer%2C_Combination_Oily_to_Oily%2C_SPF_20_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/478420/Clean_%26_Clear_Essentials_Foaming_Facial_Cleanser/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/122048/Neutrogena_Deep_Clean_Facial_Cleanser%2C_For_Normal_to_Oily_Skin/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/674687/Cetaphil_Moisturizing_Cream_/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/680999/L%27Oreal_Paris_Advanced_Haircare_Extraordinary_Oil_Curls_Re-Nourish_Mask/");
			
			//URL url = new URL("http://www.ewg.org/skindeep/product/534746/Ck_One_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/97408/Ck_Eternity_For_Men_Eau_De_Toilette_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/519791/CK_Free_by_Calvin_Klein_Deodorant_%28old_formulation%29/");
			
			//URL url = new URL("http://www.ewg.org/skindeep/product/620360/Est%C3%83%C2%A9e_Lauder_Resilience_Lift_Firming_Sculpting_Face_and_Neck%2C_SPF_15_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/620003/Est%C3%83%C2%A9e_Lauder_Advanced_Time_Zone_Age_Reversing_Line%3B%3BWrinkle_Cream%2C_Dry_Skin%2C_SPF_15_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/527312/Estee_Lauder_Pleasures_Eau_de_Parfum_Spray/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619896/Est%C3%83%C2%A9e_Lauder_CyberWhite_HD_BB_Creme%2C_SPF_50_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619875/Est%C3%83%C2%A9e_Lauder_DayWear_BB_Anti-Oxidant_Beauty_Benefit_Creme%2C_SPF_35_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/621017/Est%C3%83%C2%A9e_Lauder_RE_Nutriv_Radiant_UV_Base%2C_SPF_50_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/662643/Clinique_For_Men_UV_Defense%2C_SPF_50/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619557/Clinique_Youth_Surge_Age_Decelerating_Moisturizer%2C_Combination_Oily_to_Oily%2C_SPF_15_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619803/Clinique_Age_Defense_BB_Cream%2C_SPF_30_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/514214/Clinique_Age_Defense_BB_Cream%2C_SPF_30_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691022/Thayers_Alcohol-Free_Witch_Hazel_Aloe_Vera_Formula_Toner%2C_Lavender/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/467008/Thayers_Alcohol-Free_Witch_Hazel_with_Organic_Aloe_Vera_Formula_Toner%2C_Rose_Petal/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691025/Thayers_Witch_Hazel_Aloe_Vera_Formula_Astringent%2C_Original/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/467006/Thayers_Original_Witch_Hazel_with_Organic_Aloe_Vera_Formula_Astringent/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/467007/Thayers_Lemon_Witch_Hazel_with_Organic_Aloe_Vera_Formula_Astringent/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/467010/Thayers_Alcohol-Free_Witch_Hazel_with_Organic_Aloe_Vera_Formula_Toner%2C_Cucumber_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/13425/Clean_%26_Clear_Oil-Free_Daily_Pore_Cleanser/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/479665/Clean_%26_Clear_Morning_Burst_Cleanser/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/11027/Neutrogena_Shampoo%2C_Anti-Residue_Formula/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/464611/Neutrogena_Body_Oil%2C_Light_Sesame_Formula%2C_Fragrance_Free/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/469290/Neutrogena_Naturals_Purifying_Facial_Cleanser/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/245137/Neutrogena_Norwegian_Formula_Hand_Cream%2C_Fragrance_Free/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/594239/Neutrogena_Make-up_Remover_Cleansing_Towelettes/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/592322/Neutrogena_Rainbath_Refreshing_Shower_%26_Bath_Gel%2C_Original/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/467359/Neutrogena_Revitalizing_Grapefruit_Rainbath_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/662143/Neutrogena_Norwegian_Formula_Lip_Moisturizer%2C_SPF_15/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/662119/Neutrogena_CoolDry_Sport_Sunscreen_Lotion%2C_SPF_50/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/617869/Neutrogena_Ultra_Sheer_Dry-Touch_Sunscreen_Lotion%2C_SPF_55_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/100148/Neutrogena_Norwegian_Formula_Fast_Absorbing_Hand_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/566793/Neutrogena_Healthy_Skin_Face_Lotion%2C_SPF_15/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/662118/Neutrogena_CoolDry_Sport_Sunscreen_Spray%2C_SPF_50/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/674677/Cetaphil_Baby_Moisturizing_Oil/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/674675/Cetaphil_Baby_Daily_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/517495/Cetaphil_DailyAdvance_Ultra_Hydrating_Lotion_For_Dry%2C_Sensitive_Skin/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/239808/Cetaphil_Gentle_Skin_Cleanser%2C_For_All_Skin_Types/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/2656/Cetaphil_Moisturizing_Lotion%2C_Fragrance_Free/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/468197/Cetaphil_Restoraderm_Skin_Restoring_Body_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/468009/L%27Oreal_Men%27s_Expert_Hydra-Energetic_Ice_Cold_Eye_Roller/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/662025/L%27Oreal_Men_Expert_Vita_Lift_Daily_Moisturizer%2C_SPF_15/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/463316/L%27Oreal_Men%27s_Expert_Vita_Lift_Anti-Wrinkle_%26_Firming_Moisturizer_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/595070/L%27Oreal_Paris_Studio_Secrets_Pro_Felt_Tip_Eyeliner%2C_Black_Mica/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/596733/L%27Oreal_Paris_Advanced_Haircare_Total_Repair_5_Restoring_Conditioner/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/595913/L%27Oreal_Paris_Advanced_Haircare_Total_Repair_5_Restoring_Shampoo/");
			
			//URL url = new URL("http://www.ewg.org/skindeep/product/682047/Lancome_Hypnose_Eau_de_Toilette/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/514339/Dior_Diorsnow_BB_Creme%2C_SPF_50_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/514216/Dior_Diorskin_Nude_BB_Creme%2C_SPF_10_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/531959/Instyle_Fragrances_An_Impression_Spray_Cologne_for_Men%2C_Chanel_Bleu/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/527247/Instyle_Fragrances_An_Impression_Spray_Cologne_for_Women%2C_Chanel_No_5/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/593414/Avene_Gentle_Eye_Make-Up_Remover/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/672812/Avene_Cicalfate_Repair_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/532723/Avene_Cold_Cream_Hand_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/672840/Avene_Soothing_Eye_Contour_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/660276/eau_thermale_Avene_Hydrating_Sunscreen_Lotion%2C_Face_%26_Body%2C_SPF_50%2B/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/672819/Avene_Cold_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/464534/Avene_Rich_Compensating_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/672830/Av%C3%83%C2%A8ne_Hydrance_Optimale_Hyrdating_Serum/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/526883/Avene_Cleanance_Emulsion_-_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/527914/Avene_Cleanance_Gel/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/280719/Avene_Cold_Cream_Lip_Balm/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/660281/eau_thermale_Avene_Mineral_Light_Hydrating_Sunscreen_Lotion%2C_Face_%26_Body%2C_SPF_50%2B/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/660433/Benefit_Dream_Screen_Sunscreen%2C_SPF_45/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/660434/Benefit_Big_Easy_Sunscreen%2C_01_Fair%2C_SPF_35/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/619844/MAC_Prep_%2B_Prime_CC_Color_Correcting%2C_SPF_30_%282015_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/532080/Yves_Saint_Laurent_Kouros_Eau_De_Toilette_Spray_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/597332/La_Roche-Posay_Toleriane_Purifying_Foaming_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/594414/La_Roche-Posay_Toleriane_Fluide_Soothing_Protective_Non-Oily_Emulsion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/682023/La_Roche-Posay_Toleriane_Soothing_Protective_Skincare%2C_Fragrance-Free/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/682022/La_Roche-Posay_Toleriane_Riche_Daily_Soothing_Protective_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/591948/La_Roche-Posay_Toleriane_Ultra/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/529654/La_Roche-Posay_Redermic_Eyes/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/596934/La_Roche-Posay_Effaclar_Deep_Cleansing_Foaming_Cream/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/595126/La_Roche-Posay_Effaclar_Purifying_Foaming_Gel/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/593699/La_Roche-Posay_Physiological_Micellar_Solution%2C_Sensitive_Skin/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/242405/La_Roche-Posay_Active_C_Anti-Wrinkle_Dermatological_Treatment%2C_Dry_Skin_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/681968/La_Roche-Posay_Effaclar_Duo_Dual_Action_Acne_Treatment/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/537254/La_Roche_Posay_Effaclar_K_Daily_Renovating_Acne_Treatment_%28old_formulation%29/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/220238/La_Roche-Posay_Effaclar_Toner_Astringent_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/594597/La_Roche_Posay_Effaclar_Clarifying_Solution/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/465127/La_Roche-Posay_Toleriane_Dermo-Cleanser%2C_All_Types_of_Intolerant_Skin/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691613/Vaseline_Lip_Therapy_Lip_Balm%2C_Original/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691610/Vaseline_Lip_Therapy%2C_Original/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691586/Vaseline_Intensive_Care_Advanced_Repair_Unscented_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691599/Vaseline_Intensive_Care_Rescue_Moisture_Repairing_Body_Lotions_of/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/465372/Vaseline_Men_Body_%26_Face_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691587/Vaseline_Intensive_Care_Aloe_Soothe_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/691598/Vaseline_Intensive_Care_Healthy_Hands_Stronger_Nails_Lotion/");
			//URL url = new URL("http://www.ewg.org/skindeep/product/464049/Vaseline_Lip_Therapy_Skin_Protectant%2C_Cherry_%28old_formulation%29/");
			
			
			int idxNumber = 100;
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("User-Agent", "Mozilla");
			Source product = new Source(conn);
			
			System.out.println(product);	
			
			String productName = product.getElementById("righttoptitleandcats").getAllElements(HTMLElementName.H1).get(0).getTextExtractor().toString();
			String productIngredient = null;
			String allIngredientString = "";
			Integer ingredientScore = 0;
			int ingredientRange = 0;
			
			Integer totalScore = 0;
			
			HashMap<String, Integer> hash = new HashMap<String, Integer>();
			
			productName = productName.replace('\'', ' ');
			productName = productName.replace(',', '_');
			productName = productName.replace(' ', '_');
			System.out.println("Product Name : " + productName);
			
			while(true){
				try {
					
					productIngredient = product.getAllElementsByClass("product_tables").get(0).getAllElementsByClass("firstcol").get(ingredientRange).getTextExtractor().toString();
					ingredientScore = (Integer)(product.getAllElementsByClass("preview").get(ingredientRange + 1).getAllElements(HTMLElementName.IMG).get(0).getAttributeValue("src").charAt(58) - 48);
					
					productIngredient = productIngredient.replace('\'', ' ');
					productIngredient = productIngredient.replace(' ',  '_');					
					allIngredientString += productIngredient + ", ";
										
					if(hash.containsKey(productIngredient) == false)
						hash.put(productIngredient, ingredientScore);
					
					ingredientRange++;
					totalScore += ingredientScore;
				}
				catch(IndexOutOfBoundsException e){
					scoreAverage = totalScore.doubleValue() / (double)(ingredientRange + 1);
					ingredientRange = 0;
					break;
				}
			}
			System.out.println("");
			System.out.println("Product Ingredient, EWG Score");
			System.out.println("=====================================================================================");
			for(Entry<String, Integer> e : hash.entrySet()){
				System.out.println(e.getKey() + ", " + e.getValue());
			}
			
			//System.out.println(totalScore);
			System.out.println("=====================================================================================");
			System.out.println("EWG Score Average : " + Math.round(getEwgScore()) / 100.0);
			
			Connection con = null;
			java.sql.Statement st = null;
			
			
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "id", "pwd");
				st = con.createStatement();
				st.executeUpdate("INSERT INTO cosmetic_ingredient.product (idx, product_category, product_name, product_ingredient, product_score) VALUES ('"+ idxNumber + "', '" + "Fragrance" + "', '" + productName + "', '" + allIngredientString + "', '" + Math.round(getEwgScore()) / 100.0 + "');");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			
		} catch (MalformedURLException e) {
			// 	TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

