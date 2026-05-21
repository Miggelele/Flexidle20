
function showResultMessageLost() {
    const params = new URLSearchParams(window.location.search);

    const word = params.get('word');

    const lostMessage = document.getElementById('message-lost');

    if (lostMessage) {
        lostMessage.textContent = `The right word was ${word}`;
    }
}

showResultMessageLost();