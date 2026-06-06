const paramsInput = new URLSearchParams(window.location.search);

const bgColor = paramsInput.get("bg");
const tileShape = paramsInput.get("shape");
const language = paramsInput.get("language");
const wordLength = parseInt(paramsInput.get("length"));
const maxGuesses = parseInt(paramsInput.get("guesses"));

document.body.style.background = bgColor;

let currentUser = sessionStorage.getItem("currentUser");
let answer = "";
let wordId = null;
let currentRow = 0;
let currentCol = 0;
let gameOver = false;

const board = document.getElementById("board");
const keyboard = document.getElementById("keyboard");

const keyboardGreenArray = [];
const keyboardYellowArray = [];
const keyboardGrayArray = [];

/**
 * @Author Frida
 * @section fetch
 */

fetch(`/game_word/${wordLength}/${language}`)
    .then(response => response.json())
    .then(data => {
        answer = data.word;
        wordId = data.word_id;
        console.log(answer);
    })
    .catch(error => {
    });


/**
 * @Author: Cecilia Raalas
 * @Section: startGame()
 */
function startGame(){
    if (currentUser=== "UNKNOWN") {
    } else {
    }

    if (maxGuesses < 4 || maxGuesses > 6 ) {
        window.location.href = "/settings";
        return;
    }

    board.innerHTML = "";
    board.style.gridTemplateColumns = `repeat(${wordLength}, 48px)`;
    board.style.gridTemplateRows = `repeat(${maxGuesses}, 48px)`;

    for (let i = 0; i < wordLength *maxGuesses; i++){
        const tile = document.createElement("div");
        tile.className = "tile";

        if (tileShape === "circle") tile.style.borderRadius = "50%";
        if (tileShape === "heart") tile.textContent = "💙";
        /*if (tileShape === "diamond") tile.textContent = "🔷";*/
        if (tileShape === "triangle") tile.textContent = "△";
        if (tileShape === "diamond") {tile.classList.add("rhomb");}
        if (tileShape === "dog") tile.textContent = "🐶";

        board.appendChild(tile);
    }

    buildKeyboard();
}

/**
 * @Author: Cecilia Raalas
 * @Section: buildKeyboard()
 */
function buildKeyboard(){
    keyboard.innerHTML = "";
    const keyboardRows = [];

    switch (language){
        case "swedish":
            keyboardRows.push("QWERTYUIOPÅ", "ASDFGHJKLÖÄ", "ZXCVBNM");
            break;
        case "german":
            keyboardRows.push("QWERTZUIOPÜ", "ASDFGHJKLÖÄ", "YXCVBNMß");
            break;
        case "english":
        default:
            keyboardRows.push("QWERTYUIOP", "ASDFGHJKL", "ZXCVBNM");
            break;
    }

    keyboardRows.forEach((keyboardRow, index) => {
        const rowDiv = document.createElement("div");
        rowDiv.className = "keyboardRow";

        if (index === 2) {
            const enter = document.createElement("button");
            enter.className = "key actionKey";
            enter.textContent = "ENTER";
            enter.dataset.actionKey = "enter";
            rowDiv.appendChild(enter);
        }

        keyboardRow.split("").forEach(letter => {
            const btn = document.createElement("button");
            btn.className = "key";
            btn.dataset.letter = letter;
            btn.textContent = letter;
            rowDiv.appendChild(btn);
        });

        if (index === 2) {
            const back = document.createElement("button");
            back.className = "key actionKey";
            back.textContent = "⌫";
            back.dataset.actionKey = "back";
            rowDiv.appendChild(back);
        }

        keyboard.appendChild(rowDiv);

    });

    document.querySelectorAll(".key").forEach(btn => {
        btn.addEventListener("click", () => {
            pressedKey(btn.textContent, btn.dataset.actionKey);
            btn.blur();
            
        });

    });
}

/**
 * @Author: Cecilia Raalas
 * @Section: pressedKey()
 */
function pressedKey(key, actionKey) {
    let languageString;
    if (gameOver) {
        return;
    }
    if (language === "german"){
        languageString = /^[A-ZÖÄÜẞa-zäößü]$/;
    }
    else if (language === "swedish"){
        languageString = /^[A-ZÅÄÖa-zåäö]$/;
    }
    else if (language === "english"){
        languageString = /^[A-Za-z]$/;
    }

    if (actionKey === "enter") {
        makeGuess();

    } else if (actionKey === "back") {
        deleteLetter();

    } else if (languageString.test(key)) addLetter(key);

}

/**
 * @Author: Cecilia Raalas
 * @Section: addLetter()
 */
function addLetter(letter){
    if (currentCol >= wordLength){
        return;
    }

    const tile = board.children[currentRow * wordLength + currentCol];

    if (tileShape === "diamond") {

        const span = document.createElement("span");
        span.textContent = letter;

        tile.innerHTML = "";
        tile.appendChild(span);

    }
    else {
        tile.textContent = letter;
    }

    currentCol++;
}

/**
 * @Author: Cecilia Raalas
 * @Section: deleteLetter()
 */
function deleteLetter() {
    if (currentCol === 0) return;
    currentCol--;

    const tile = board.children[currentRow * wordLength + currentCol];

    if (tileShape === "heart") tile.textContent = "💙";
    else if (tileShape === "dog") tile.textContent = "🐶";
    else tile.textContent = "";
}

/**
 * @Author: Cecilia Raalas
 * @Section: makeGuess()
 */
function makeGuess(){
    if (currentCol < wordLength) return;

    const guess = [];
    for (let i = 0; i < wordLength; i++) {
        guess.push(board.children[currentRow * wordLength + i].textContent);
    }

    const guessString = guess.join("");
    const answerArray = answer.split("");
    const guessArray = guess.slice();

    const result = Array(wordLength).fill("gray");

    for (let i = 0; i < wordLength; i++) {
        if (guessArray[i] === answerArray[i]) {
            result[i] = "green";
            answerArray[i] = null;
            guessArray[i] = null;
        }
    }

    for (let i = 0; i < wordLength; i++) {
        if (guessArray[i] === null) {
            continue;
        }

        const index = answerArray.indexOf(guessArray[i]);
        if (index !== -1) {
            result[i] = "yellow";
            answerArray[index] = null;
        }
    }

    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];

        setTimeout(() => {
            tile.classList.add("flip");

            setTimeout(() => {
                if (result[i] === "green") {
                    tile.classList.add("correctLetter");
                    keyboardGreenArray.push(guess[i]);
                } else if (result[i] === "yellow") {
                    tile.classList.add("existingLetter");
                    keyboardYellowArray.push(guess[i]);
                } else {
                    tile.classList.add("wrongLetter");
                    keyboardGrayArray.push(guess[i]);
                }
        }, 250);

        }, i * 200);

        setTimeout(() => {
            applyKeyboardColors();
        }, wordLength * 200 + 300);
    }

    if (guessString === answer) {
        gameOver = true;
        showResultWon(answer, currentRow+1);
        if (currentUser != null && currentUser !== "UNKNOWN") {
            saveResult(wordId, currentRow+1, maxGuesses, true);
        }
        return;
    }
    currentRow++;
    currentCol = 0;

    if (currentRow >= maxGuesses) {
        gameOver = true;

        showResultLost(answer);

        if (currentUser != null && currentUser !== "UNKNOWN") {
            saveResult(wordId, currentRow, maxGuesses, false);
        }
    }
}

document.addEventListener("keydown", e => {
    let languageString;

    if (e.target.tagName === 'INPUT') {
        return;
    }

    if (gameOver) {
        return;
    }

    if (language === "german"){
        languageString = /^[A-ZÖÄÜẞa-zäößü]$/;
    }
    else if (language === "swedish"){
        languageString = /^[A-ZÅÄÖa-zåäö]$/;
    }
    else if (language === "english"){
        languageString = /^[A-Za-z]$/;
    }

    if (e.key === "Enter") {
        makeGuess();
    }
    else if (e.key === "Backspace") {
        deleteLetter();
    }
    else if (languageString.test(e.key)) addLetter(e.key.toUpperCase());
});

/**
 * @Author: Elin Piho, Isabell Persson
 * @Section: showResultWon()
 */
function showResultWon(word, tries) {
    document.getElementById('fireworks-container').style.display = 'block';

    setTimeout(() => {
        document.getElementById('fireworks-container').style.display = 'none';
    }, 100000000);

    const wonMessage = document.getElementById('message-won');
    if (wonMessage) {
        wonMessage.textContent = `You guessed the right word "${word}" in ${tries} tries!`;
    }
    const modal = new bootstrap.Modal(document.getElementById('result-won'));
    modal.show()
}

/**
 * @Author: Elin Piho, Isabell Persson
 * @Section: showResultLost()
 */
function showResultLost(word) {
    startRain();
    const lostMessage = document.getElementById('message-lost');
    if (lostMessage) {
        lostMessage.textContent = `The correct word was: ${word}`;
    }
    const modal = new bootstrap.Modal(document.getElementById('result-lost'));
    modal.show();
}


function saveResult(wordId, madeGuesses, maxGuesses, gameWon) {
    const gameRecord = {
        made_guesses: madeGuesses,
        max_guesses: maxGuesses,
        game_won: gameWon
    };

    fetch("/game_record", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(gameRecord)
    })
        .then(response => response.json())
        .then(savedGameRecord => {

            const usedWord = {
                game_id: savedGameRecord.game_id,
                username: sessionStorage.getItem("currentUser"),
                word_id: wordId
            };

            fetch("/used_word", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(usedWord)
            })
    })
}

startGame();

function playAgain() {
    window.location.href = `/flexidle?bg=${encodeURIComponent(bgColor)}&shape=${tileShape}&language=${language}&length=${wordLength}&guesses=${maxGuesses}`;
}

function goToSettings() {
    window.location.href = `/?openSettings=true`;
}

/**
 * @Author: Elin Piho
 * @Section: showResultLost()
 */
function startRain() {
    const container = document.getElementById('rain-container');
    container.style.display = 'block';
    container.innerHTML = '';

    for (let i = 0; i < 80; i++) {
        const drop = document.createElement('div');
        drop.className = 'raindrop';
        drop.style.left = Math.random() * 100 + 'vw';
        drop.style.height = Math.random() * 60 + 40 + 'px';
        drop.style.animationDuration = Math.random() * 1 + 0.5 + 's';
        drop.style.animationDelay = Math.random() * 2 + 's';
        container.appendChild(drop);
    }

    setTimeout(() => {
        container.style.display = 'none';
        container.innerHTML = '';
    }, 10000000);
}

/**
 * @Author: Cecilia R
 * @Section: applyKeyboardColors()
 */
function applyKeyboardColors(){
    document.querySelectorAll(".key").forEach(btn => {

        if (keyboardGreenArray.includes(btn.textContent)){
            btn.classList.remove("existingGuessedKey");
            btn.classList.remove("wrongGuessedKey");
            btn.classList.add("correctGuessedKey");
        }
        else if (keyboardYellowArray.includes(btn.textContent)){
            btn.classList.remove("correctGuessedKey");
            btn.classList.remove("wrongGuessedKey");
            btn.classList.add("existingGuessedKey");
        }
        else if (keyboardGrayArray.includes(btn.textContent)){
            btn.classList.remove("existingGuessedKey");
            btn.classList.remove("correctGuessedKey");
            btn.classList.add("wrongGuessedKey");
        }
    });
}