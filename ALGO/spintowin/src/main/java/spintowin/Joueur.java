package spintowin;

import java.util.Date;

public class Joueur {
	private int id;
	private String pseudo;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private float credit;
	private String mot_de_passe_hash;

	
	

	// Constructeur par défaut
	public Joueur() {
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getPseudo() {
		return pseudo;
	}




	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}




	public String getNom() {
		return nom;
	}




	public void setNom(String nom) {
		this.nom = nom;
	}




	public String getPrenom() {
		return prenom;
	}




	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}




	public Date getDateNaissance() {
		return dateNaissance;
	}




	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}




	public float getCredit() {
		return credit;
	}




	public void setCredit(float credit) {
		this.credit = credit;
	}




	public Joueur(String pseudo) {
		super();
		this.pseudo = pseudo;
	}




	public String getMot_de_passe_hash() {
		return mot_de_passe_hash;
	}




	public void setMot_de_passe_hash(String mot_de_passe_hash) {
		this.mot_de_passe_hash = mot_de_passe_hash;
	}




	public Joueur(String pseudo, String nom, String prenom, Date dateNaissance, float credit,
			String mot_de_passe_hash) {
		super();
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.credit = credit;
		this.mot_de_passe_hash = mot_de_passe_hash;
	}




	public Joueur(int id, String pseudo, String nom, String prenom, Date dateNaissance, float credit,
			String mot_de_passe_hash) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.credit = credit;
		this.mot_de_passe_hash = mot_de_passe_hash;
	}




	@Override
	public String toString() {
		return "Joueur [id=" + id + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance="
				+ dateNaissance + ", credit=" + credit + ", mot_de_passe_hash=" + mot_de_passe_hash + "]";
	}

}