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

//Debug. Prints the selected settings in console (F12)
console.log(`Wordlength:   ${wordLength}      Language:   ${language}`);

//fetches a word from backend with the given settings, saves it, then prints the word in the console F12
fetch(`/game_word/${wordLength}/${language}`)
    .then(response => response.text())
    .then(data => {
        answer = data;
        console.log(data);
    })
    .catch(error => {
        console.error("Fel:", error);
    });


function startGame(){
    //just for placeholder now:
    // Random integer between min and max (inclusive)
/*
    const randomNumber = Math.floor(Math.random() * 3); //borde vara 0-2

    if (wordLength === 4){
        if (randomNumber === 0){
            answer = "GIRL";
        }
        else if (randomNumber === 1){
            answer = "LOVE";
        }
        else{
            answer = "LINK";
        }

    } else if (wordLength === 5){
        if (randomNumber === 0){
            answer = "HANDY";
        }
        else if (randomNumber === 1){
            answer = "APPLE";
        }
        else{
            answer = "ZELDA";
        }
    }else {
        if (randomNumber === 0){
            answer = "GOLDEN";
        }
        else if (randomNumber === 1){
            answer = "HANDLA";
        }
        else{
            answer = "HYRULE";
        }
    }
*/

    board.innerHTML = "";
    board.style.gridTemplateColumns = `repeat(${wordLength}, 48px)`;
    board.style.gridTemplateRows = `repeat(${maxGuesses}, 48px)`;

    for (let i = 0; i < wordLength *maxGuesses; i++){
        const tile = document.createElement("div");
        tile.className = "tile";

        if (tileShape === "circle") tile.style.borderRadius = "50%";
        if (tileShape === "heart") tile.textContent = "💙";
        if (tileShape === "diamond") tile.textContent = "🔷";
        if (tileShape === "triangle") tile.textContent = "△";
        if (tileShape === "dog") tile.textContent = "🐶";

        board.appendChild(tile);
    }

    buildKeyboard();
}

function buildKeyboard(){
    keyboard.innerHTML = "";

    //TODO: beroende på vilket språk, olika keyboards!?
    const keyboardRows = ["QWERTYUIOPÅ", "ASDFGHJKLÖÄ", "ZXCVBNMẞ"];

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
        btn.addEventListener("click", () => pressedKey(btn.textContent, btn.dataset.actionKey));

    });
}

function pressedKey(key, actionKey){
    if (gameOver) {
        return;
    }

    if (actionKey === "enter") {
        makeGuess();

    } else if (actionKey === "back") {
        deleteLetter();

    } else if (/^[A-Öẞ]$/.test(key)) addLetter(key);
}


function addLetter(letter){
    if (currentCol >= wordLength){
        return;
    }

    const tile = board.children[currentRow * wordLength + currentCol];
    tile.textContent = letter;

    currentCol++;
}

function deleteLetter() {
    if (currentCol === 0) return;
    currentCol--;

    const tile = board.children[currentRow * wordLength + currentCol];

    if (tileShape === "heart") tile.textContent = "💙";
    else if (tileShape === "dog") tile.textContent = "🐶";
    else if (tileShape === "diamond") tile.textContent = "🔷";
    else if (tileShape === "triangle") tile.textContent = "△";
    else tile.textContent = "";
}

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
            answerArray[i] = null;
            guessArray[i] = null;
        }
    }

    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];
        if (guessArray[i] && answerArray.includes(guessArray[i])) {
            tile.classList.add("existingLetter");
            answerArray[answerArray.indexOf(guessArray[i])] = null;
        } else if (guessArray[i]) {
            tile.classList.add("wrongLetter");
        }
    }

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
    if (gameOver) return;

    if (e.key === "Enter") makeGuess();
    else if (e.key === "Backspace") deleteLetter();
    else if (/^[a-öA-Ö]$/.test(e.key)) addLetter(e.key.toUpperCase());
});

function showResultWon(word, tries) {
    window.location.href = `result-pop-up-won?word=${encodeURIComponent(word)}&tries=${tries}`
}

function showResultLost(word) {
    window.location.href = `result-pop-up-lost?word=${encodeURIComponent(word)}` 
}

startGame();