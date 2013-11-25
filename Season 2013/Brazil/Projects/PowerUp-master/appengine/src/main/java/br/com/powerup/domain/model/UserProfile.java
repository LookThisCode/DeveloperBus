package br.com.powerup.domain.model;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.common.collect.Lists;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class UserProfile {
	
	@Parent
	private com.google.appengine.api.datastore.Key parent;
	@Id Long id;
	private String name;
	private Double weight;
	private Double height;
	private String imageUrl;
	private Date createdOn = new Date();
	private Level level = Level.ZERO;
	private List<Badge> badges = Lists.newArrayList();
	
	@SuppressWarnings("unused")
	private UserProfile() {
		super();
		// objectify
	}

	public UserProfile(String userId) {
		this.parent = KeyFactory.createKey(User.class.getSimpleName(), userId);
	}

	public UserProfile(SignupRequest signupRequest) {
		this(signupRequest.getUserId());
		this.height = signupRequest.getHeight();
		this.name = signupRequest.getName();
		this.weight = signupRequest.getWeight();
		this.imageUrl = signupRequest.getImageUrl();
	}

	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Double getWeight() {
		return weight;
	}
	public Double getHeight() {
		return height;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public Date getCreatedOn() {
		return createdOn;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public boolean adjustLevel(Score score) {
		Level newLevel = Level.fit(score.getTotalPoints());
		if (!this.level.equals(newLevel)) {
			setLevel(newLevel);
			return true;
		}
		return false;
	}

	public void addBadge(Badge newBadge) {
		badges.add(newBadge);		
	}

	public List<Badge> getBadges() {
		return badges;
	}
	
}