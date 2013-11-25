package br.com.powerup.domain.model;

import java.util.UUID;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Load;

@Embed
public class Exercise {

    @Load Ref<ExerciseDescriptor> descriptor;
    private String id = UUID.randomUUID().toString();
	private Double carga = 10d;
	private String serie = "serie";
	private Integer numeroDeRepeticoes = 10;
	private Double duracao = 32d;
	private Double descanso = 30d;
	private Integer order;
	
	private String name;
	private String description;
	private String imageUrl;
	
	private String workoutDeviceId;
	
	
	@SuppressWarnings("unused")
	private Exercise() {
	}
	
	public Exercise(ExerciseDescriptor value) {
		setExerciseDescriptor(value);
		this.name = value.getName();
		this.workoutDeviceId = this.name;
		this.description = value.getDescription();
		this.imageUrl = value.getImageUrl();
	}
	
	public String getId() {
		return this.id;
	}
	
	public Double getCarga() {
		return carga;
	}
	
	public String getSerie() {
		return serie;
	}
	
	public Integer getNumeroDeRepeticoes() {
		return numeroDeRepeticoes;
	}
	
	public Double getDuracao() {
		return duracao;
	}
	
	public Double getDescanso() {
		return descanso;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	ExerciseDescriptor getExerciseDescriptor() {
		return descriptor.get();
	}

	void setExerciseDescriptor(ExerciseDescriptor value) {
		descriptor = Ref.create(value);
	}

	public String getWorkoutDeviceId() {
		return workoutDeviceId;
	}

}
