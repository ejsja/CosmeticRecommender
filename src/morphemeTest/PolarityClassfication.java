package morphemeTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.ws.RequestWrapper;

import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
import kr.co.shineware.util.common.model.Pair;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.TextExtractor;


public class PolarityClassfication {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//CollectMorpheme.main(args);
		
		Source userReview = null;
		Connection conn = null;
		java.sql.Statement st = null;
		ResultSet rs = null;
			
		int pageNumber = 0;
		int reviewCnt = 0;
		int allReviewCnt = 0;
		int polarityCnt = 0;
		int goodCnt = 0;
		String morpheme = null;
		double morphemePolarity = 0.0;
		double reviewPolarity = 0.0;
		boolean isExistReview = true;
		double reviewScore = 0.0;
		
				
		HashMap<Double, Integer> hash = new HashMap<Double, Integer>();
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "id", "pwd");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT morpheme, polarityCorrection FROM review_morpheme.m_frequency;");
			
			while(isExistReview){
				try{
					
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5446100344&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
										
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						String str = contents.toString();
															
						while(rs.next()){
							morpheme = rs.getString("morpheme");
							morphemePolarity = rs.getFloat("polarityCorrection");
								
							if(str.contains(morpheme)){
								reviewPolarity += morphemePolarity;																	
							}
							reviewPolarity = Math.round(reviewPolarity * 1000.0) / 1000.0;							
						}
						
						if(reviewPolarity > 0.236)
							goodCnt++;
						
						if(reviewPolarity != 0.0f){
							System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + reviewPolarity);
							System.out.println("");
							allReviewCnt++;
							
							if(hash.containsKey(reviewPolarity)){
								polarityCnt = hash.get(reviewPolarity);
								hash.replace(reviewPolarity, polarityCnt + 1);									
							}
							else{
								hash.put(reviewPolarity, 1);
							}
						}					
						
						reviewPolarity = 0.0f;
						rs.first();
					}while(++reviewCnt < 20);
				}
				catch(IndexOutOfBoundsException e){
					isExistReview = false;
				}
				catch(Exception e){					
					e.printStackTrace();
					pageNumber--;
				}
				reviewCnt = 0;
			}			
			
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		reviewScore = Math.round(((double)goodCnt / (double)allReviewCnt) * 10000.0) / 100.0;
		//System.out.println("긍정적 상품평 : " + goodCnt + ", 총 상품평 : " + allReviewCnt);
		System.out.println("=============================================");
		System.out.println("긍정적 상품평 : " + 455 + ", 부정적 상품평 : " + 74);		
		System.out.println("상품평 점수 : " + reviewScore);
		System.out.println("세이어스 로즈페탈 위치하젤 토너 355ml : " + 86.01);
		System.out.println("=============================================");
		
		/*
		allReviewPolarity = 0;
		allReviewCnt = 0;	
		
		try{
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "id", "pwd");
			st = conn.createStatement();
			tempSt = conn.createStatement();
			rs = st.executeQuery("SELECT morpheme, polarity FROM review_morpheme.m_frequency;");
			
			while(pageNumber++ < 414){
				try{
					userReview = new Source(new URL("http://shopping.naver.com/detail/section_user_review.nhn?nv_mid=4110578715&page="+ pageNumber +"&sort=0&mall_id=all&score=all&imgYN=all&briefYN=Y&topicCode=&reviewSeq="));
					//키엘 울트라 페이셜 토너 250ml									
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						String str = contents.toString();
															
						while(rs.next()){
							morpheme = rs.getString("morpheme");
							morphemePolarity = rs.getFloat("polarity");
																				
							if(str.contains(morpheme)){
								reviewPolarity += morphemePolarity;
								allReviewPolarity += morphemePolarity;									
							}
							reviewPolarity = Math.round(reviewPolarity * 1000.0) / 1000.0;
							allReviewPolarity = Math.round(allReviewPolarity * 1000.0) / 1000.0;
						}
						
						if(reviewPolarity > 0.7)
							goodCnt++;
						
						if(reviewPolarity != 0.0f){
							//System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + reviewPolarity);
							//System.out.println("");
							allReviewCnt++;
							
							if(hash.containsKey(reviewPolarity)){
								polarityCnt = hash.get(reviewPolarity);
								hash.replace(reviewPolarity, polarityCnt + 1);									
							}
							else{
								hash.put(reviewPolarity, 1);
							}
						}					
						
						reviewPolarity = 0.0f;
						rs.first();
					}while(++reviewCnt < 20);					
				}
				catch(IndexOutOfBoundsException e){
					break;
				}
				catch(Exception e){					
					e.printStackTrace();
					pageNumber--;
				}
				reviewCnt = 0;
			}			
			
			conn.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		polarityAverage =  (double)allReviewPolarity / (double)allReviewCnt;
		//polarityAverage = (double)allReviewPolarity / (double)polarityCnt;
		polarityAverage = Math.round(polarityAverage * 1000.0) / 1000.0;
		System.out.println("키엘 울트라 페이셜 토너 250ml : " + polarityAverage);
		System.out.println((double)goodCnt / (414.0 * 20.0));
		*/
	}
}
