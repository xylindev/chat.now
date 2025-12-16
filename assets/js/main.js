const main = () => {
    const parameter = new URLSearchParams(window.location.search)

    if(parameter.get("auth") === "false")
        alert("Mot de passe incorrect. Veuillez vérifier votre saisie ou choisir un nouveau nom d’utilisateur pour vous inscrire.")
}

main()