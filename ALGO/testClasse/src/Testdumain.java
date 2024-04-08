import java.util.Date;

public class Testdumain {
    public static void main(String[] args) {
        // Création d'un joueur avec le constructeur qui prend tous les arguments
        Joueur joueur = new Joueur(1, "Joueur1", new Date(), 100.0f);

        // Affichage des détails du joueur
        System.out.println("Détails du joueur :");
        System.out.println("ID : " + joueur.getId());
        System.out.println("Pseudo : " + joueur.getPseudo());
        System.out.println("Date de naissance : " + joueur.getDateNaissance());
        System.out.println("Crédit : " + joueur.getCredit());

        // Modification du pseudo du joueur
        joueur.setPseudo("NouveauPseudo");

        // Ajout de crédit au joueur
        joueur.setCredit(joueur.getCredit() + 50.0f);

        // Affichage des détails mis à jour du joueur
        System.out.println("\nDétails mis à jour du joueur :");
        System.out.println("ID : " + joueur.getId());
        System.out.println("Pseudo : " + joueur.getPseudo());
        System.out.println("Date de naissance : " + joueur.getDateNaissance());
        System.out.println("Crédit : " + joueur.getCredit());
    }
}
