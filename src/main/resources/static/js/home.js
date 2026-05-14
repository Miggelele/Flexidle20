function showView(viewId) {
    document.querySelectorAll('#view-username, #view-password, #view-create')
        .forEach(v => v.style.display = 'none');
    document.getElementById(viewId).style.display = 'block';
}

function checkUsername() {
    const username = document.getElementById('usernameInput').value;

    if (!username) return;

    // TODO: CHECKA MED DATABASEN

    //DETTA SKA BORT EFTER DEMO / NÄR DATABASEN FUNKAR :D Visar nästa view direkt oberoende av om kontot finns
    showView('view-create');
}

function login() {
    // TODO: KONTO HANTERING MED DATABASEN

    const modal = bootstrap.Modal.getInstance(document.getElementById('log-in'));
    modal.hide();
}

function createAccount() {
    const username = document.getElementById('usernameInput').value;
    const password = document.getElementById('newPassword').value;
    const question = document.getElementById('securityQuestion').value;
    const answer = document.getElementById('securityAnswer').value;

    // TODO: SPARA TILL DATABASEN
    alert('Welcome! ' + username + ' Hope you enjoy Flexidle');

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