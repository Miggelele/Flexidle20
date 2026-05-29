//sätter default för vem som är inloggad, ändras om man loggar in.
//används för att lagra username i frontend
sessionStorage.setItem("currentUser", "UNKNOWN");

function showView(viewId) {
    document.querySelectorAll('#view-username, #view-password, #view-create')
        .forEach(v => v.style.display = 'none');
    document.getElementById(viewId).style.display = 'block';
}

function checkUsername() {
    const username = document.getElementById('usernameInput').value.trim(); //trim så att mellanslag försvinner

    if (!username) return;

    //krav i databasen att username högst 12 tecken
    if (username.length > 12) {
        //TODO: ge användaren feedback att username för långt!! har vi en minimigräns?
        return;
    }

    //kollar med backend om username finns, skickar till olika views beroende på svaret
    let usernameExists;
    fetch(`/registered_user/checkUsername/${username}`)
        .then(response => response.json())
        .then(data => {
            usernameExists = data;
            console.log(`resultat av checkUsername: `+ usernameExists);
            if (usernameExists === true) {
                showView('view-password')
            } else {
                showView('view-create');
            }
        });
}

function login() {
    const password = document.getElementById('passwordInput').value;
    const username = document.getElementById('usernameInput').value;

    if (!password) return;

    //kollar med backend om password stämmer för username
    let passwordCorrect;
    fetch(`/registered_user/checkPassword/${password}/${username}`)
        .then(response => response.json())
        .then(data => {
            passwordCorrect = data;

            if (passwordCorrect) {
                //lagrar vem som är inloggad i frontend.
                console.log(`resultat av checkPassword: `+ passwordCorrect);
                sessionStorage.setItem("currentUser",username);
                console.log(`inloggad som: ` + sessionStorage.getItem("currentUser"));
                const modal = bootstrap.Modal.getInstance(document.getElementById('log-in'));
                modal.hide();
            } else {
                //TODO ge feedback att lösenord var fel!
            }
        });
}

function createAccount() {
    const username = document.getElementById('usernameInput').value;
    const password = document.getElementById('newPassword').value;
    const question = document.getElementById('securityQuestion').value;
    const answer = document.getElementById('securityAnswer').value;

    //TODO: ge användaren feedback när något blir fel!
    if (!password) return;
    if (!question) return;      //osäker på om rätt syntax för att kolla om den är tom?? finns ju text i rutan från början
    if (!answer) return;

    //TODO kontrollera våra andra villkor för lösenord, specialtecken, stor bokstav mm!
    //krav i databasen att password högst 24 tecken
    if (password.length > 24) return;

    //krav i databasen att answer högst 12 tecken
    if (answer.length > 12) return;

    //skapar nytt registered_user objekt som JSON
    const newUser = {
        username: username,
        password: password,
        s_question: question,
        s_answer: answer
    };


    //skickar JSON-objektet till backend för att skapa ny registered_user. skriver ut det i konsolen.
    fetch("/registered_user/createAccount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newUser)
    })
        .then(response => response.json())
        .then(data => {
            console.log("Skapad användare:", data);
            sessionStorage.setItem("currentUser", username)       //lagrar vem som är inloggad i frontend
            alert('Welcome! ' + username + ' Hope you enjoy Flexidle');
        })
        .catch(error => {
            console.error("Fel:", error);
        });

    const modal = bootstrap.Modal.getInstance(document.getElementById('log-in'));
    modal.hide();
}


document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('log-in');

    if (modal) {
        modal.addEventListener('hidden.bs.modal', () => {
            showView('view-username');
            document.getElementById('usernameInput').value = '';
        });
    }
});