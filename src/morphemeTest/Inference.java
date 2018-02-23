package morphemeTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import kr.co.shineware.util.common.model.Pair;

public class Inference {

	static final int STRONG_NEGATIVE = 0;
	static final int NEGATIVE = 1;
	static final int RECOMMENDABLE = 2;
	static final int POSITIVE = 3;
	static final int STRONG_POSITIVE = 4;
	
	static final double[] SN_RANGE = {0.1, 0.4};
	static final double[] N_RANGE = {0.2, 0.6};
	static final double[] R_RANGE = {0.4, 0.8};
	static final double[] P_RANGE = {0.6, 1.0};
	static final double[] SP_RANGE = {0.8, 1.1};
	
	static final int VERY_LOW = 0;
	static final int LOW = 1;
	static final int MEDIUM = 2;
	static final int HIGH = 3;
	static final int VERY_HIGH = 4;
	
	static final int VERY_BAD = 0;
	static final int BAD = 1;
	static final int NORMAL = 2;
	static final int GOOD = 3;
	static final int VERY_GOOD = 4;	
	
	
	public static void main(String[] args) {
		
				
		double getEwgScore = 2.25;
		double getReviewScore = 82.46;
		
		double[] ewgScore = new double[2];
		int[] ewgState = new int[2]; // 0 : Very Low, 1 : Low, 2 : Medium, 3 : High, 4 : Very High
		
		double[] reviewScore = new double[2];
		int[] reviewState = new int[2]; // 0 : Very Bad, 1 : Bad, 2 : Normal, 3 : Good, 4 : Very Good
		
		double[] membershipValue = new double[4];
		int[] resultState = new int[4]; // 0 : Strong Negative, 1 : Negative, 2 : Recommendable, 3 : Positive, 4 : Strong Positive
		
		double[] minValue = new double[4];
		HashMap<Integer, Pair<Integer, Double>> maxValue = new HashMap<Integer, Pair<Integer, Double>>();
		
		int ruleCnt = 0;
		int ewgCnt = 0;
		int reviewCnt = 0;
				
		for(int i = 0; i < 2; i++){
			ewgScore[i] = -1.0;
			reviewScore[i] = -1.0;
			ewgState[i] = -1;
			reviewState[i] = -1;			
		}
		
		for(int i = 0; i < 4; i++){
			minValue[i] = -1.0;			
		}
		
		
		if(getEwgScore <= 1.0){ // Very Low 1
			ewgScore[0] = 1.0;
			ewgState[0] = VERY_LOW;
		}
		if(getEwgScore >= 1.0 && getEwgScore <= 2.5){ // Very Low 2
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((-(2.0 / 3.0) * getEwgScore + (5.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[0] = VERY_LOW;
			}
			else{
				ewgScore[1] = Math.round((-(2.0 / 3.0) * getEwgScore + (5.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[1] = VERY_LOW;
			}			
		}
		if(getEwgScore >= 1.5 && getEwgScore <= 2.5){ // Low 1
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((getEwgScore - 1.5) * 1000.0) / 1000.0;
				ewgState[0] = LOW;
			}
			else{
				ewgScore[1] = Math.round((getEwgScore - 1.5) * 1000.0) / 1000.0;
				ewgState[1] = LOW;
			}			
		}
		if(getEwgScore >= 2.5 && getEwgScore <= 4.0){ // Low 2
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((-(2.0 / 3.0) * getEwgScore + (8.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[0] = LOW;
			}
			else{
				ewgScore[1] = Math.round((-(2.0 / 3.0) * getEwgScore + (8.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[1] = LOW;
			}			
		}
		if(getEwgScore >= 3.0 && getEwgScore <= 4.5){ // Medium 1
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round(((((2.0 / 3.0) * getEwgScore) - 2.0)) * 1000.0) / 1000.0;						
				ewgState[0] = MEDIUM;
			}
			else{
				ewgScore[1] = Math.round(((((2.0 / 3.0) * getEwgScore) - 2.0)) * 1000.0) / 1000.0;
				ewgState[1] = MEDIUM;
			}			
		}
		if(getEwgScore >= 9.0 / 2.0 && getEwgScore <= 6.0){ // Medium 2
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((-((2.0 / 3.0) * getEwgScore) + 4.0) * 1000.0) / 1000.0;						
				ewgState[0] = MEDIUM;
			}
			else{
				ewgScore[1] = Math.round((-((2.0 / 3.0) * getEwgScore) + 4.0) * 1000.0) / 1000.0;
				ewgState[1] = MEDIUM;
			}
		}
		if(getEwgScore >= 5.0 && getEwgScore <= 13.0 / 2.0){ // High 1
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((((2.0 / 3.0) * getEwgScore) - (10.0 / 3.0)) * 1000.0) / 1000.0;						
				ewgState[0] = HIGH;
			}
			else{
				ewgScore[1] = Math.round((((2.0 / 3.0) * getEwgScore) - (10.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[1] = HIGH;
			}
		}
		if(getEwgScore >= 13.0 / 2.0 && getEwgScore <= 8.0){ // High 2
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((-((2.0 / 3.0) * getEwgScore) + (16.0 / 3.0)) * 1000.0) / 1000.0;						
				ewgState[0] = HIGH;
			}
			else{
				ewgScore[1] = Math.round((-((2.0 / 3.0) * getEwgScore) + (16.0 / 3.0)) * 1000.0) / 1000.0;
				ewgState[1] = HIGH;
			}
		}
		if(getEwgScore >= 7.0 && getEwgScore <= 9.0){ // Very High 1
			if(ewgScore[0] == -1.0){
				ewgScore[0] = Math.round((((1.0 / 2.0) * getEwgScore) + (7.0 / 2.0)) * 1000.0) / 1000.0;						
				ewgState[0] = VERY_HIGH;
			}
			else{
				ewgScore[1] = Math.round((((1.0 / 2.0) * getEwgScore) + (7.0 / 2.0)) * 1000.0) / 1000.0;
				ewgState[1] = VERY_HIGH;
			}
		}
		if(getEwgScore >= 9.0){ // Very High 2
			if(ewgScore[0] == -1.0){
				ewgScore[0] = 1.0;
				ewgState[0] = VERY_HIGH;
			}
			else{
				ewgScore[1] = 1.0;
				ewgState[1] = VERY_HIGH;
			}
		}
				
		if(getReviewScore <= 15.0){ // Very Bad 1
			reviewScore[0] = 1.0;
			reviewState[0] = VERY_BAD;
		}
		if(getReviewScore >= 15.0 && getReviewScore <= 20.0){ // Very Bad 2
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((-(0.2 * getReviewScore) + 4.0)) * 1000.0) / 1000.0;						
				reviewState[0] = VERY_BAD;
			}
			else{
				reviewScore[1] = Math.round(((-(0.2 * getReviewScore) + 4.0)) * 1000.0) / 1000.0;
				reviewState[1] = VERY_BAD;
			}
		}
		if(getReviewScore >= 18.0 && getReviewScore <= 25.0){ // Bad 1
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((((1.0 / 7.0) * getReviewScore) - (18.0 / 7.0))) * 1000.0) / 1000.0;						
				reviewState[0] = BAD;
			}
			else{
				reviewScore[1] = Math.round(((((1.0 / 7.0) * getReviewScore) - (18.0 / 7.0))) * 1000.0) / 1000.0;
				reviewState[1] = BAD;
			}
		}
		if(getReviewScore >= 25.0 && getReviewScore <= 35.0){ // Bad 2
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((-(0.1 * getReviewScore) + 3.5)) * 1000.0) / 1000.0;						
				reviewState[0] = BAD;
			}
			else{
				reviewScore[1] = Math.round(((-(0.1 * getReviewScore) + 3.5)) * 1000.0) / 1000.0;
				reviewState[1] = BAD;
			}
		}
		if(getReviewScore >= 30.0 && getReviewScore <= 50.0){ // Normal 1
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((((1.0 / 20.0) * getReviewScore) - 1.5)) * 1000.0) / 1000.0;						
				reviewState[0] = NORMAL;
			}
			else{
				reviewScore[1] = Math.round(((((1.0 / 20.0) * getReviewScore) - 1.5)) * 1000.0) / 1000.0;
				reviewState[1] = NORMAL;
			}
		}
		if(getReviewScore >= 50.0 && getReviewScore <= 65.0){ // Normal 2
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((-((1.0 / 15.0) * getReviewScore) + (65.0 / 15.0))) * 1000.0) / 1000.0;						
				reviewState[0] = NORMAL;
			}
			else{
				reviewScore[1] = Math.round(((-((1.0 / 15.0) * getReviewScore) + (65.0 / 15.0))) * 1000.0) / 1000.0;
				reviewState[1] = NORMAL;
			}
		}
		if(getReviewScore >= 50.0 && getReviewScore <= 60.0){ // Good 1
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round((((0.1 * getReviewScore) - 0.5)) * 1000.0) / 1000.0;						
				reviewState[0] = GOOD;
			}
			else{
				reviewScore[1] = Math.round((((0.1 * getReviewScore) - 0.5)) * 1000.0) / 1000.0;
				reviewState[1] = GOOD;
			}
		}
		if(getReviewScore >= 60.0 && getReviewScore <= 85.0){ // Good 2
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((-((1.0 / 25.0) * getReviewScore) + (85.0 / 25.0))) * 1000.0) / 1000.0;						
				reviewState[0] = GOOD;
			}
			else{
				reviewScore[1] = Math.round(((-((1.0 / 25.0) * getReviewScore) + (85.0 / 25.0))) * 1000.0) / 1000.0;
				reviewState[1] = GOOD;
			}
		}
		if(getReviewScore >= 70.0 && getReviewScore <= 90.0){ // Very Good 1
			if(reviewScore[0] == -1.0){
				reviewScore[0] = Math.round(((((1.0 / 20.0) * getReviewScore) - (7.0 / 2.0))) * 1000.0) / 1000.0;						
				reviewState[0] = VERY_GOOD;
			}
			else{
				reviewScore[1] = Math.round(((((1.0 / 20.0) * getReviewScore) - (7.0 / 2.0))) * 1000.0) / 1000.0;
				reviewState[1] = VERY_GOOD;
			}
		}
		if(getReviewScore >= 90.0){ // Very Good 2
			if(reviewScore[0] == -1.0){
				reviewScore[0] = 1.0;
				reviewState[0] = VERY_GOOD;
			}
			else{
				reviewScore[1] = 1.0;
				reviewState[1] = VERY_GOOD;
			}		
		}
		
		for(int i = 0; i < 2; i++){
			System.out.println(ewgScore[i] + ", " + reviewScore[i]);
		}
		
		for(int i = 0; i < 2; i++){
			if(ewgState[i] != -1)
				ewgCnt++;
			
			if(reviewState[i] != -1)
				reviewCnt++;
		}
			
		
		for(int i = 0; i < ewgCnt; i++){
			for(int j = 0; j < reviewCnt; j++){
				if(ewgState[i] == VERY_LOW){
					if(reviewState[j] == VERY_BAD){
						resultState[ruleCnt] = RECOMMENDABLE;	
					}
					else if(reviewState[j] == LOW){
						resultState[ruleCnt] = RECOMMENDABLE;
					}
					else if(reviewState[j] == NORMAL){
						resultState[ruleCnt] = POSITIVE;
					}
					else if(reviewState[j] == HIGH){
						resultState[ruleCnt] = POSITIVE;
					}
					else{
						resultState[ruleCnt] = STRONG_POSITIVE;
					}					
				}
				else if(ewgState[i] == LOW){
					if(reviewState[j] == VERY_BAD){
						resultState[ruleCnt] = RECOMMENDABLE;		
					}
					else if(reviewState[j] == LOW){
						resultState[ruleCnt] = RECOMMENDABLE;
					}
					else if(reviewState[j] == NORMAL){
						resultState[ruleCnt] = RECOMMENDABLE;
					}
					else if(reviewState[j] == HIGH){
						resultState[ruleCnt] = POSITIVE;
					}
					else{
						resultState[ruleCnt] = STRONG_POSITIVE;
					}					
				}
				else if(ewgState[i] == MEDIUM){
					if(reviewState[j] == VERY_BAD){
						resultState[ruleCnt] = NEGATIVE;			
					}
					else if(reviewState[j] == LOW){
						resultState[ruleCnt] = NEGATIVE;
					}
					else if(reviewState[j] == NORMAL){
						resultState[ruleCnt] = RECOMMENDABLE;
					}
					else if(reviewState[j] == HIGH){
						resultState[ruleCnt] = POSITIVE;
					}
					else{
						resultState[ruleCnt] = POSITIVE;
					}					
				}
				else if(ewgState[i] == HIGH){
					if(reviewState[j] == VERY_BAD){
						resultState[ruleCnt] = STRONG_NEGATIVE;		
					}
					else if(reviewState[j] == LOW){
						resultState[ruleCnt] = NEGATIVE;
					}
					else if(reviewState[j] == NORMAL){
						resultState[ruleCnt] = NEGATIVE;
					}
					else if(reviewState[j] == HIGH){
						resultState[ruleCnt] = NEGATIVE;
					}
					else{
						resultState[ruleCnt] = NEGATIVE;
					}					
				}
				else{
					if(reviewState[j] == VERY_BAD){
						resultState[ruleCnt] = STRONG_NEGATIVE;		
					}
					else if(reviewState[j] == LOW){
						resultState[ruleCnt] = STRONG_NEGATIVE;
					}
					else if(reviewState[j] == NORMAL){
						resultState[ruleCnt] = NEGATIVE;
					}
					else if(reviewState[j] == HIGH){
						resultState[ruleCnt] = NEGATIVE;
					}
					else{
						resultState[ruleCnt] = NEGATIVE;
					}
				}
				
				if(ewgScore[i] < reviewScore[j])
					minValue[ruleCnt] = ewgScore[i];
				else
					minValue[ruleCnt] = reviewScore[j];
				
				ruleCnt++;			
			}
		}
				
		System.out.println("ewgScore : " + getEwgScore + ", reviewScore : " + getReviewScore);
		System.out.println();
				
		for(int i = 0; i < 2; i++){
			System.out.println("ewgState : " + ewgState[i] + ", reviewState : " + reviewState[i]);
		}
		
		System.out.println();
		
		for(int i = 0; i < ruleCnt; i++){
			System.out.println("resultState : " + resultState[i] + ", minValue : " + minValue[i]);
		}
				
		
		for(int i = 0; i < ruleCnt; i++){			
			if(maxValue.containsKey(resultState[i])){
				if(maxValue.get(resultState[i]).getSecond() < minValue[i]){
					maxValue.replace(resultState[i], new Pair<Integer, Double>(i, minValue[i]));
				}						
			}
			else{
				maxValue.put(resultState[i], new Pair<Integer, Double>(i, minValue[i]));
			}	
		}
		
		System.out.println();
			
		for(Entry<Integer, Pair<Integer, Double>> e : maxValue.entrySet()){
			System.out.println("resultState : " + e.getKey() + ", NumOfRules : " + e.getValue().getFirst() + ", maxValue : " + e.getValue().getSecond());
		}
		
		System.out.println();
		
		double temp1 = 0.0;
		double temp2 = 0.0;
		
		for(Entry<Integer, Pair<Integer, Double>> e : maxValue.entrySet()){			
			if(e.getKey() == 0){
				for(double i = SN_RANGE[0]; i < SN_RANGE[1]; i += 0.1){
					temp1 += e.getValue().getSecond() * i;
					temp2 += e.getValue().getSecond();					
				}
			}
			else if(e.getKey() == 1){
				for(double i = N_RANGE[0]; i < N_RANGE[1]; i += 0.1){
					temp1 += e.getValue().getSecond() * i;
					temp2 += e.getValue().getSecond();
				}
			}
			else if(e.getKey() == 2){
				for(double i = R_RANGE[0]; i < R_RANGE[1]; i += 0.1){
					temp1 += e.getValue().getSecond() * i;
					temp2 += e.getValue().getSecond();
				}
			}
			else if(e.getKey() == 3){
				for(double i = P_RANGE[0]; i < P_RANGE[1]; i += 0.1){
					temp1 += e.getValue().getSecond() * i;
					temp2 += e.getValue().getSecond();
				}
			}
			else if(e.getKey() == 4){
				for(double i = SP_RANGE[0]; i < SP_RANGE[1]; i += 0.1){
					temp1 += e.getValue().getSecond() * i;
					temp2 += e.getValue().getSecond();									
				}
			}
			
		}
		
		System.out.println(temp1 + ", " + temp2);
		System.out.println(Math.round(temp1 / temp2 * 1000.00) / 1000.00);
		double inferenceValue = temp1 / temp2 * 50 / 9;
		//double inferenceValue = 0.858 * 50 / 9;
		System.out.println("추천 적합도 : " + (Math.round(inferenceValue * 100.0) / 100.0));
	}

}
