const paramsInput = new URLSearchParams(window.location.search);

const bgColor = paramsInput.get("bg");
const tileShape = paramsInput.get("shape");
const language = paramsInput.get("language");
const wordLength = parseInt(paramsInput.get("length"));
const maxGuesses = parseInt(paramsInput.get("guesses"));

document.body.style.background = bgColor;

let answer = "";
let currentRow = 0;
let currentCol = 0;
let gameOver = false;

const board = document.getElementById("board");
const keyboard = document.getElementById("keyboard");

const keyboardGreenArray = [];
const keyboardYellowArray = [];
const keyboardGrayArray = [];

//Debug. Prints the selected settings in console (F12)
console.log(`Wordlength:   ${wordLength}      Language:   ${language}`);

/**
 * @Author Frida
 * @section fetch
 */
//fetches a word from backend with the given settings, saves it, then prints the word in the console F12
fetch(`/game_word/${wordLength}/${language}`)
    .then(response => response.text())
    .then(data => {

        //if parameters are invalid backend will send this message (possibly due to user meddling with url)
        //user will be sent back to settings if parameters were bad.
        if (data === "INVALID REQUEST") {
            window.location.href = "/settings";
            return;
        }

        answer = data;
        console.log(data);
    })
    .catch(error => {
        console.error("Fel:", error);
    });


/**
 * @Author: Cecilia Raalas
 * @Section: startGame()
 */
function startGame(){

    //checks if maxGuesses is invalid, possibly due to user interference in url.
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
            keyboardRows.push("QWERTZUIOPÜ", "ASDFGHJKLÖÄ", "YXCVBNMẞ");
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
    /*else if (tileShape === "diamond") tile.textContent = "🔷";
    else if (tileShape === "triangle") tile.textContent = "△";*/
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


    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];
        if (guessArray[i] === answerArray[i]) {
            tile.classList.add("correctLetter");

            keyboardGreenArray.push(guessArray[i]);
            answerArray[i] = null;
            guessArray[i] = null;
        }
    }

    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];
        if (guessArray[i] && answerArray.includes(guessArray[i])) {
            tile.classList.add("existingLetter");
            keyboardYellowArray.push(guessArray[i]);

            answerArray[answerArray.indexOf(guessArray[i])] = null;
        } else if (guessArray[i]) {
            tile.classList.add("wrongLetter");
            keyboardGrayArray.push(guessArray[i]);
        }
    }


    //Change colors of the on-screen-keyboard
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

    if (guessString === answer) {
        gameOver = true;
        //TODO: skapa popuppp av resultWon
        showResultWon(answer, currentRow+1);
        //ToDo: Skicka resultatet till backend
        return;
    }
    currentRow++;
    currentCol = 0;

    if (currentRow >= maxGuesses) {
        gameOver = true;
        //TODO: skapa popuppp av resultLost
        showResultLost(answer)
        //ToDo: Skicka resultatet till backend
    }
}

document.addEventListener("keydown", e => {
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

    if (e.key === "Enter") {
        makeGuess();
    }
    else if (e.key === "Backspace") {
        deleteLetter();
    }
    else if (languageString.test(e.key)) addLetter(e.key.toUpperCase());
});

/**
 * @Author:
 * @Section: showResultWon()
 */
function showResultWon(word, tries) {
    const wonMessage = document.getElementById('message-won');
    if (wonMessage) {
        wonMessage.textContent = `You guessed the right word "${word}" in ${tries} tries!`;
    }
    const modal = new bootstrap.Modal(document.getElementById('result-won'));
    modal.show()
}

/**
 * @Author:
 * @Section: showResultLost()
 */
function showResultLost(word) {
    const lostMessage = document.getElementById('message-lost');
    if (lostMessage) {
        lostMessage.textContent = `The correct word was: ${word}`;
    }
    const modal = new bootstrap.Modal(document.getElementById('result-lost'));
    modal.show();
}

startGame();