/**
 * author: Isabell Persson
 * section: Gameboard
 */
function showResultMessageWon() {
    const params = new URLSearchParams(window.location.search);

    const word = params.get('word');
    const tries = params.get('tries');

    const wonMessage = document.getElementById('message-won');

    if (wonMessage) {
        wonMessage.textContent = `You guessed the right ${word} word in ${tries} tries`;
    }
}

showResultMessageWon();