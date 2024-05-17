# Définir le chemin du répertoire
$repertoire = ".\IHM\SpinToWinAngular"

# Vérifier si le répertoire existe
if (Test-Path $repertoire) {
    # Accéder au répertoire
    Set-Location $repertoire
    
    # Exécuter la commande ng serve
    ng serve
} else {
    Write-Host "Le répertoire $repertoire n'existe pas."
}
