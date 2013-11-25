package br.com.powerup.domain.model;

import java.util.List;

import com.google.common.collect.Lists;


public class LogWorkResponse {

	private List<Badge> badgesEarned = Lists.newArrayList();
	private Integer pointsEarned;
	private Long totalPoints;
	private Boolean levelUp = false;
	

	@SuppressWarnings("unused")
	private LogWorkResponse() {
		super();
	}

	/**
	 * @param pointsEarned
	 * @param levelUp
	 */
	public LogWorkResponse(Integer pointsEarned, Long totalPoints) {
		super();
		this.pointsEarned = pointsEarned;
		this.totalPoints = totalPoints;
	}

	public LogWorkResponse(int earned) {
		this.pointsEarned = earned;
	}

	public Integer getPointsEarned() {
		return pointsEarned;
	}

	public Boolean getLevelUp() {
		return levelUp;
	}

	public List<Badge> getBadgesEarned() {
		return badgesEarned;
	}

	public void addBadge(Badge badgeEarned) {
		this.badgesEarned.add(badgeEarned);
	}

	public void levelUp() {
		this.levelUp = true;
	}

	public Long getTotalPoints() {
		return totalPoints;
	}
	
}