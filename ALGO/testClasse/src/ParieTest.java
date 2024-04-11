import java.util.Arrays;
import java.util.Random;

public class ParieTest {
    public static void main(String[] args) {
        // Création d'un objet Parie associé à un joueur d'ID 1
        Parie parie = new Parie();
        parie.setIdJoueur(1);

        // Génération de valeurs aléatoires pour les sommes
        Random random = new Random();

        // Somme pair/impair
        parie.setSommePairImpair(new int[]{random.nextInt(37), random.nextInt(37)});

        // Somme ligne
        parie.setSommeLigne(new int[]{random.nextInt(37), random.nextInt(37), random.nextInt(37)});

        // Somme inférieur/supérieur
        parie.setSommeInferieurSuperieur(new int[]{random.nextInt(37), random.nextInt(37)});

        // Somme couleur
        parie.setSommeCouleur(new int[]{random.nextInt(37), random.nextInt(37)});

        // Somme nombre
        int[][] sommeNombre = new int[3][];
        for (int i = 0; i < 3; i++) {
            sommeNombre[i] = new int[]{random.nextInt(37), random.nextInt(37)};
        }
        parie.setSommeNombre(sommeNombre);

        // Somme tiers
        parie.setSommeTiers(new int[]{random.nextInt(37), random.nextInt(37), random.nextInt(37)});

        // Affichage du pari associé au joueur d'ID 1
        System.out.println(parie);
        System.out.println(parie.toString());
    }

   
}
