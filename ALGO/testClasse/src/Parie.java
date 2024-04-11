import java.util.Arrays;

public class Parie {
	
	private int idJoueur ;
	private int[] sommePairImpair = new int [2];
	private int[] sommeLigne = new int[3];
	private int[] sommeInferieurSuperieur = new int[2];
	private int[] sommeCouleur = new int[2];
	private int[][] sommeNombre;
	private int[] sommeTiers = new int[3];
	
	
	
	
	@Override
	public String toString() {
		return "Parie [idJoueur=" + idJoueur + ", sommePairImpair=" + Arrays.toString(sommePairImpair) + ", sommeLigne="
				+ Arrays.toString(sommeLigne) + ", sommeInferieurSuperieur=" + Arrays.toString(sommeInferieurSuperieur)
				+ ", sommeCouleur=" + Arrays.toString(sommeCouleur) + ", sommeNombre=" + Arrays.toString(sommeNombre)
				+ ", sommeTiers=" + Arrays.toString(sommeTiers) + "]";
	}
	
	public int[] getSommePairImpair() {
		return sommePairImpair;
	}
	public void setSommePairImpair(int[] sommePairImpair) {
		this.sommePairImpair = sommePairImpair;
	}
	public int[] getSommeLigne() {
		return sommeLigne;
	}
	public void setSommeLigne(int[] sommeLigne) {
		this.sommeLigne = sommeLigne;
	}
	public int[] getSommeInferieurSuperieur() {
		return sommeInferieurSuperieur;
	}
	public void setSommeInferieurSuperieur(int[] sommeInferieurSuperieur) {
		this.sommeInferieurSuperieur = sommeInferieurSuperieur;
	}
	public int[] getSommeCouleur() {
		return sommeCouleur;
	}
	public void setSommeCouleur(int[] sommeCouleur) {
		this.sommeCouleur = sommeCouleur;
	}
	public int[][] getSommeNombre() {
		return sommeNombre;
	}
	public void setSommeNombre(int[][] sommeNombre) {
		this.sommeNombre = sommeNombre;
	}
	public int[] getSommeTiers() {
		return sommeTiers;
	}
	public void setSommeTiers(int[] sommeTiers) {
		this.sommeTiers = sommeTiers;
	}
	public int getIdJoueur() {
		return idJoueur;
	}
	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}
	
}
