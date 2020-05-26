package k26_DBProject3rdExam;

import java.util.*;

public class K26_Score {
	private String k26_condition = "";
	private int k26_score = 0;
	
	
	public K26_Score() {}
	
	public K26_Score(String k26_condition, int k26_score) {
		this.k26_condition = k26_condition;
		this.k26_score = k26_score;
	}

	public String getCondition() {
		return k26_condition;
	}

	public int getScore() {
		return k26_score;
	}

	public void setCondition(String k26_condition) {
		this.k26_condition = k26_condition;
	}

	public void setScore(int k26_score) {
		this.k26_score = k26_score;
	}
	
	public void setFieldAll(String k26_condition, int k26_score) {
		this.k26_condition = k26_condition;
		this.k26_score = k26_score;
	}
	
	public boolean isScoreZero() {
		return (k26_score == 0 ? true : false);
	}
}