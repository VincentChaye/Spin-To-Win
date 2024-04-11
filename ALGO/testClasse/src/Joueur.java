import java.util.Date;

public class Joueur {
	private int id;
	private String pseudo;
	private Date dateNaissance;
	private float credit;

	// Constructeur par défaut
	public Joueur() {
	}

	// Constructeur avec tous les arguments
	public Joueur(int id, String pseudo, Date dateNaissance, float credit) {
		this.id = id;
		this.pseudo = pseudo;
		this.dateNaissance = dateNaissance;
		this.credit = credit;
	}

	// Accesseurs (getters)
	public int getId() {
		return id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public Date getDateNaissance() {
		return dateNaissance;
	}

	public float getCredit() {
		return credit;
	}

	// Mutateurs (setters)
	public void setId(int id) {
		this.id = id;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}
}
