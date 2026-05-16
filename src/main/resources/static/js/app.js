const paramsInput = new URLSearchParams(window.location.search);

const bgColor = paramsInput.get("bg");
const tileShape = paramsInput.get("shape");
const language = paramsInput.get("language");
const wordLength = parseInt(paramsInput.get("length"));
const maxGuesses = parseInt(paramsInput.get("guesses"));

document.body.style.background = bgColor;

//TODO: get the word from data base here. using above info.

//console.log(`/game_word/${wordLength}/${language}`);

fetch(`/game_word/${wordLength}/${language}`)
    .then(response => response.text())
    .then(data => {
        console.log(data);
    })
    .catch(error => {
        console.error("Fel:", error);
    });





let answer = "";
let currentRow = 0;
let currentCol = 0;
let gameOver = false;

const board = document.getElementById("board");
const keyboard = document.getElementById("keyboard");


function startGame(){
    //just for placeholder now:
    // Random integer between min and max (inclusive)

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
    const keyboardRows = ["QWERTYUIOPÅ", "ASDFGHJKLÖÄ", "ZXCVBNM"];

    keyboardRows.forEach((row, idx) => {
        const rowDiv = document.createElement("div");
        rowDiv.className = "keyboardRow";

        if (idx === 2) {
            const enter = document.createElement("button");
            enter.className = "key action";
            enter.textContent = "ENTER";
            enter.dataset.action = "enter";
            rowDiv.appendChild(enter);
        }

        row.split("").forEach(letter => {
            const btn = document.createElement("button");
            btn.className = "key";
            btn.textContent = letter;
            rowDiv.appendChild(btn);
        });

        if (idx === 2) {
            const back = document.createElement("button");
            back.className = "key action";
            back.textContent = "⌫";
            back.dataset.action = "back";
            rowDiv.appendChild(back);
        }

        keyboard.appendChild(rowDiv);

    });

    document.querySelectorAll(".key").forEach(btn => {
        btn.addEventListener("click", () => pressedKey(btn.textContent, btn.dataset.action));

    });
}

function pressedKey(key, action){
    if (gameOver) {
        return;
    }

    if (action === "enter") {
        makeGuess();

    } else if (action === "back") {
        deleteLetter();

    } else if (/^[A-Ö]$/.test(key)) addLetter(key);
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

    const guessStr = guess.join("");
    const answerArr = answer.split("");
    const guessArr = guess.slice();

    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];
        if (guessArr[i] === answerArr[i]) {
            tile.classList.add("correctLetter");
            answerArr[i] = null;
            guessArr[i] = null;
        }
    }

    for (let i = 0; i < wordLength; i++) {
        const tile = board.children[currentRow * wordLength + i];
        if (guessArr[i] && answerArr.includes(guessArr[i])) {
            tile.classList.add("existingLetter");
            answerArr[answerArr.indexOf(guessArr[i])] = null;
        } else if (guessArr[i]) {
            tile.classList.add("wrongLetter");
        }
    }

    if (guessStr === answer) {
        gameOver = true;
        //TODO: skapa popuppp
        return;
    }
    currentRow++;
    currentCol = 0;

    if (currentRow >= maxGuesses) {
        gameOver = true;
        //TODO: skapa popuppp
    }
}

document.addEventListener("keydown", e => {
    if (gameOver) return;

    if (e.key === "Enter") makeGuess();
    else if (e.key === "Backspace") deleteLetter();
    else if (/^[a-öA-Ö]$/.test(e.key)) addLetter(e.key.toUpperCase());
});

startGame();