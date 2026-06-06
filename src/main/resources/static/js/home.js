

if (!sessionStorage.getItem("currentUser")) {
    sessionStorage.setItem("currentUser", "UNKNOWN");
}

function showView(viewId) {
    document.querySelectorAll('#view-username, #view-password, #view-create')
        .forEach(v => v.style.display = 'none');
    document.getElementById(viewId).style.display = 'block';
}

function checkUsername() {
    const username = document.getElementById('usernameInput').value.trim();

    if (!username) return;

    if (username.length > 12) {
        return;
    }

    let usernameExists;
    fetch(`/registered_user/checkUsername/${username}`)
        .then(response => response.json())
        .then(data => {
            usernameExists = data;
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

    let passwordCorrect;
    fetch(`/registered_user/checkPassword/${password}/${username}`)
        .then(response => response.json())
        .then(data => {
            passwordCorrect = data;

            if (passwordCorrect) {
                sessionStorage.setItem("currentUser",username);
                const modal = bootstrap.Modal.getInstance(document.getElementById('log-in'));
                alert('Welcome back! ' + username);
                modal.hide();
            } else {
            }
        });
}

function createAccount() {
    const username = document.getElementById('usernameInput').value;
    const password = document.getElementById('newPassword').value;
    const question = document.getElementById('securityQuestion').value;
    const answer = document.getElementById('securityAnswer').value;

    if (!password) return;
    if (!question) return;
    if (!answer) return;
    if (password.length > 24) return;
    if (answer.length > 12) return;

    const newUser = {
        username: username,
        password: password,
        s_question: question,
        s_answer: answer
    };

    fetch("/registered_user/createAccount", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(newUser)
    })
        .then(response => response.json())
        .then(data => {
            sessionStorage.setItem("currentUser", username)
            alert('Welcome! ' + username + ' Hope you enjoy Flexidle');
        })
        .catch(error => {
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

/**
 * Author: Elin Piho
 * Section: Log in
 */
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

/**
 * Author: Elin Piho
 * Section: Log in
 */
function confirmLogout() {
    const loggedInModal = bootstrap.Modal.getInstance(document.getElementById('logged-in'));
    if (loggedInModal) loggedInModal.hide();

    document.querySelectorAll('.modal-backdrop').forEach(el => el.remove());
    document.body.classList.remove('modal-open');

    setTimeout(() => {
        const confirmModal = new bootstrap.Modal(document.getElementById('confirm-logout'));
        confirmModal.show();
    }, 300);
}

/**
 * Author: Elin Piho
 * Section: Log in
 */
function logout() {
    sessionStorage.setItem("currentUser", "UNKNOWN");

    const confirmModal = bootstrap.Modal.getInstance(document.getElementById('confirm-logout'));
    if (confirmModal) confirmModal.hide();

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