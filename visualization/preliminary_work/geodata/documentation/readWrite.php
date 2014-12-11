<?php
$file = 'data/csv/copy.txt';
// Ouvre un fichier pour lire un contenu existant
$current = file_get_contents($file);
// Ajoute une personne


$current .= "Jean Dupond\n";
// Écrit le résultat dans le fichier
print_r($current);
file_put_contents($file, $current);
?>