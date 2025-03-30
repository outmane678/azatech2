// login.js

document.addEventListener('DOMContentLoaded', function() {
    // Sélectionner le formulaire de connexion
    const form = document.querySelector('form');
    
    // Ajouter un écouteur d'événement pour la soumission du formulaire
    form.addEventListener('submit', function(event) {
        // Récupérer les valeurs des champs email et mot de passe
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        // Vérifier si les champs sont vides
        if (!email || !password) {
            event.preventDefault(); // Empêcher la soumission du formulaire
            alert("Veuillez remplir tous les champs.");
        }
    });

    // Gérer l'affichage du message d'erreur (si un paramètre d'erreur est passé)
    const errorMessage = document.querySelector('.error-message');
    if (errorMessage) {
        setTimeout(() => {
            errorMessage.style.display = 'none';
        }, 5000); // Cacher le message d'erreur après 5 secondes
    }
});
