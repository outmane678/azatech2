document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("signup-form");

    if (form) {
        form.addEventListener("submit", function (event) {
            event.preventDefault(); // Empêcher la soumission classique

            const username = document.getElementById("username").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;
            const confirmPassword = document.getElementById("confirmPassword").value;
            const errorMessage = document.getElementById("error-message");

            // Vérifier si les mots de passe correspondent
            if (password !== confirmPassword) {
                errorMessage.style.display = "block";
                errorMessage.textContent = "Les mots de passe ne correspondent pas.";
                return;
            }

            // Construire l'objet JSON
            const formData = {
                username: username,
                email: email,
                password: password
            };

            // Envoyer les données au serveur
            fetch("/signup", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(formData)
            })
            .then(response => {
                if (response.ok) {
                    // Redirection vers la page de connexion après une inscription réussie
                    window.location.href = "/login";
                } else {
                    errorMessage.style.display = "block";
                    errorMessage.textContent = "Une erreur s'est produite lors de l'inscription.";
                }
            })
            .catch(error => {
                console.error("Erreur :", error);
                errorMessage.style.display = "block";
                errorMessage.textContent = "Problème de connexion au serveur.";
            });
        });
    } else {
        console.error("Formulaire non trouvé. Vérifiez l'ID du formulaire dans signup.html.");
    }
});
