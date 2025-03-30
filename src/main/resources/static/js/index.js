// src/main/resources/static/js/index.js
document.addEventListener("DOMContentLoaded", function() {
    console.log("Page d'accueil chargée avec succès !");
    const logoutLink = document.querySelector("a[href='/logout']");
    if (logoutLink) {
        logoutLink.addEventListener("click", function(event) {
            event.preventDefault();
            // Implémentez ici la logique de déconnexion si nécessaire, ou laissez Spring gérer la déconnexion
            alert("Vous êtes maintenant déconnecté.");
            window.location.href = "/logout";  // Redirige vers la page de déconnexion
        });
    }
});
