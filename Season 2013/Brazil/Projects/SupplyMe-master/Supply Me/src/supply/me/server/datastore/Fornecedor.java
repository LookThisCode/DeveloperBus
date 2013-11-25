package supply.me.server.datastore;

public class Fornecedor {
	private long id;
	private String nome;
	private String descricao;
	private double lat;
	private double lng;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public double getLatitude() {
		return lat;
	}
	public void setLatitude(double lat) {
		this.lat = lat;
	}
	public double getLongitude() {
		return lng;
	}
	public void setLongitude(double lng) {
		this.lng = lng;
	}
}
