//sätter default för vem som är inloggad, ändras om man loggar in.
//används för att lagra username i frontend
if (!sessionStorage.getItem("currentUser")) {
    sessionStorage.setItem("currentUser", "UNKNOWN");
}

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
    updateAccountButton();

    const params = new URLSearchParams(window.location.search);
    if (params.get('openSettings') === 'true') {
        const modal = new bootstrap.Modal(document.getElementById('settings'));
        modal.show();
    }

    const modal = document.getElementById('log-in');
    if (modal) {
        modal.addEventListener('hidden.bs.modal', () => {
            showView('view-username');
            document.getElementById('usernameInput').value = '';
        });

        modal.addEventListener('hidden.bs.modal', () => {
            updateAccountButton();
        });
    }
});

//Uppdaterar knappen på index för om du är inloggad eller inte
function updateAccountButton() {
    const user = sessionStorage.getItem("currentUser");
    const isLoggedIn = user && user !== "UNKNOWN";

    const loginBtn = document.querySelector('[data-bs-target="#log-in"], [data-bs-target="#logged-in"]');
    if (loginBtn) {
        if (isLoggedIn) {
            loginBtn.textContent = "LOG OUT";
            loginBtn.setAttribute('data-bs-target', '#logged-in');
        } else {
            loginBtn.textContent = "LOG IN";
            loginBtn.setAttribute('data-bs-target', '#log-in');
        }
    }

    const accountBtn = document.getElementById('account-button');
    if (accountBtn) {
        const newBtn = accountBtn.cloneNode(true);
        accountBtn.parentNode.replaceChild(newBtn, accountBtn);

        newBtn.onclick = () => {
            if (isLoggedIn) {
                // Visa toast när man är inloggad
                const toastMsg = document.getElementById('toast-message');
                if (toastMsg) toastMsg.textContent = `Logged in as: ${user}`;

                const toast = new bootstrap.Toast(document.getElementById('account-toast'));
                toast.show();
            } else {
                const modal = new bootstrap.Modal(document.getElementById('log-in'));
                modal.show();
            }
        };
    }

    const loggedInModal = document.getElementById('logged-in');
    if (loggedInModal) {
        // Ta bort gamla lyssnare genom att klona
        const newModal = loggedInModal.cloneNode(true);
        loggedInModal.parentNode.replaceChild(newModal, loggedInModal);

        newModal.addEventListener('show.bs.modal', () => {
            const msg = document.getElementById('logged-in-message');
            const currentUser = sessionStorage.getItem("currentUser");
            if (msg && currentUser && currentUser !== "UNKNOWN") {
                msg.textContent = `You are logged in as: ${currentUser}`;
            }
        });
    }
}

//Bekräfta om user vill logga ut
function confirmLogout() {
    const loggedInModal = bootstrap.Modal.getInstance(document.getElementById('logged-in'));
    if (loggedInModal) loggedInModal.hide();

    //Backdrop ville inte FÖRSVINNA
    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
    document.body.classList.remove('modal-open');

    setTimeout(() => {
        const confirmModal = new bootstrap.Modal(document.getElementById('confirm-logout'));
        confirmModal.show();
    }, 300);
}

function logout() {
    sessionStorage.setItem("currentUser", "UNKNOWN");

    const confirmModal = bootstrap.Modal.getInstance(document.getElementById('confirm-logout'));
    if (confirmModal) confirmModal.hide();

    // Backdrop ville inte försvinna...
    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
    document.body.classList.remove('modal-open');
    document.body.style.overflow = '';
    document.body.style.paddingRight = '';

    updateAccountButton();

    setTimeout(() => {
        showView('view-username');
        const loginModal = new bootstrap.Modal(document.getElementById('log-in'));
        loginModal.show();
    }, 300);
}