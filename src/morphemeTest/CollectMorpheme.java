package morphemeTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.Map.Entry;

import kr.co.shineware.nlp.komoran.core.analyzer.Komoran;
import kr.co.shineware.util.common.model.Pair;

import net.htmlparser.jericho.*;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;

public class CollectMorpheme
{
	
	
	public static void main(String[] args)
	{
		Source userReview = null;
		HashMap<Pair<String, String>, Pair<Integer, Integer>> hash = new HashMap<Pair<String, String>, Pair<Integer, Integer>>();
		Pair<Integer, String> pair = new Pair<Integer, String>();
		
		Connection conn = null;
		java.sql.Statement st = null;
		
		FileWriter writer = null;
		
		int pageNumber = 0;
		int reviewCnt = 0;
		int positiveReviewCnt = 0;
		int negativeReviewCnt = 0;
		int starPoint = 0;
		
		
		try{
			
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "apfhdzz20C0!");
			st = conn.createStatement();			
			Komoran komoran = new Komoran("D:\\Development\\models-full");
		
			writer = new FileWriter("review.txt");
			
			while(pageNumber++ < 60){
				try{
					
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5638086651&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//세이어스 로즈페탈 위치하젤 토너 355ml			
					System.out.println(userReview);
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
					
						String str = contents.toString();
						String temp = "";
						
						writer.write(str + "\r\n");					
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								//System.out.println(wordMorph);
								
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 414){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4110578715&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//키엘 울트라 페이셜 토너 250ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						writer.write(str + "\r\n");
						
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
						
			while(pageNumber++ < 2922){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4048397218&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//키엘 울트라 페이셜 크림 50ml									
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						writer.write(str + "\r\n");	
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 1331){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4157790954&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//설화수 자음 2종 세트									
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 939){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5423094546&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//더마하우스 알로에베라 수분 크림 30g									
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 686){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=6173988333&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//미샤 타임 레볼루션 나이트 리페어 사이언스 액티베이터 앰플 50ml			
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 696){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5743345985&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//싸이닉 올 데이 파인 포어 토너 200ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 696){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4157790550&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//설화수 윤조 에센스 60ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 620){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5420378250&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//에스티로더 어드밴스드 나이트 리페어 싱크로나이즈드 리커버리 콤플렉스 50ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 575){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=10682514299&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//나인위시스 VB 얼티밋 톤업 크림 50ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 557){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4119105365&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//크리니크 모이스처 써지 익스텐디드 썰스트 릴리프 50ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 528){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5649723094&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//빌리프 더 트루 크림 모이스처라이징 밤 50ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 524){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=4157790737&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//설화수 탄력 크림 75ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 520){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5755073782&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//네이처리퍼블릭 슈퍼 아쿠아 맥스 컴비네이션 수분크림 80ml(복합)
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 813){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=5976546524&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//미샤 타임 레볼루션 더 퍼스트 트리트먼트 에센스 150ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			reviewCnt = 0;
			pageNumber = 0;
			
			while(pageNumber++ < 765){
				try{
					URL url = new URL("http://shopping.naver.com/detail/review_list.nhn?nvMid=9369552064&page=" + ++pageNumber + "&reviewSort=accuracy&score=&topicCode=&reviewSeq=&reviewExpand=false");
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					userReview = new Source(con);
					//피지오겔 DMT 크림 150ml
					
					do{					
						TextExtractor contents = userReview.getAllElementsByClass("atc").get(reviewCnt).getTextExtractor();
						starPoint = (int)((userReview.getAllElementsByClass("curr_avg").get(reviewCnt).getTextExtractor().toString().charAt(0)) - 48);
						System.out.println(pageNumber + ", " + (reviewCnt + 1) +", " + contents + ", " + starPoint);
						System.out.println("");						
												
						String str = contents.toString();
						String temp = "";
						
						//writer.write(str + "\r\n");
					   					    
						List<List<Pair<String,String>>> result = komoran.analyze(str);
												
						for (List<Pair<String, String>> eojeolResult : result) {
							
							for (Pair<String, String> wordMorph : eojeolResult) {
									
								String firstKey = wordMorph.getFirst().toString();
								String secondKey = wordMorph.getSecond().toString();
								
								// NNG는 제외
								if (wordMorph.getSecond().equals("VA") || wordMorph.getSecond().equals("XR")) {
									if(temp.contains(firstKey))										
										break;
									else
										temp += firstKey;
									
									if(hash.containsKey(new Pair<String, String>(firstKey, secondKey))){
										if(starPoint > 4){
											positiveReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst() + 1, hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond()));											
										}
										else {
											negativeReviewCnt++;
											hash.replace(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(hash.get(new Pair<String, String>(firstKey, secondKey)).getFirst(), hash.get(new Pair<String, String>(firstKey, secondKey)).getSecond() + 1));
										}
									}
									else {
										if(starPoint > 4)
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(1, 0));
										else
											hash.put(new Pair<String, String>(firstKey, secondKey), new Pair<Integer, Integer>(0, 1));
									}
								} 
							}
						}
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
			
			double wordDetect = 0.0;
			double polarityCorrection = 0.0;
								
			for(Entry<Pair<String, String>, Pair<Integer, Integer>> e : hash.entrySet()){				
				if(e.getValue().getFirst() > 9 || e.getValue().getSecond() > 9)	{					
					
					wordDetect = ((double)e.getValue().getFirst() / (double)(e.getValue().getFirst() + e.getValue().getSecond())) - ((double)e.getValue().getSecond() / (double)(e.getValue().getFirst() + e.getValue().getSecond()));
					wordDetect = Math.round(wordDetect * 1000.0) / 1000.0;
					polarityCorrection = Math.round((wordDetect - 0.5) * 1000.0) / 1000.0;
					
					if(polarityCorrection < -1)
						polarityCorrection = -1;
					
					//st.executeUpdate("INSERT INTO review_morpheme.m_frequency (morpheme, category, positivefrequency, negativefrequency, polarity, polaritycorrection) VALUES ('"+ e.getKey().getFirst() + "', '" + e.getKey().getSecond() + "', '" + e.getValue().getFirst() + "', '" + e.getValue().getSecond() + "', '" + wordDetect + "', '" + polarityCorrection + "');");
					//System.out.println(e.getKey() + ", " + e.getValue().getFirst() + ", " + e.getValue().getSecond() + ", " + wordDetect + ", " + polarityCorrection);					
				}				
			}			
			conn.close();
			writer.close();
		}		
		catch(Exception e){
			
		}
	}
}