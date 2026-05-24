/**
 * @author: Cecilia Raalas
 * @section: settings.js
 */
let selectedShape = "square";
let selectedWordLength = 5;
let selectedNbrOfGuesses = 5;
let selectedCountry = "english";

document.querySelectorAll(".shapeOption").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".shapeOption").forEach(b => b.classList.remove("selected"));
        btn.classList.add("selected");
        selectedShape = btn.dataset.value;
    });
});

document.querySelectorAll(".countryOption").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".countryOption").forEach(b => b.classList.remove("selected"));
        btn.classList.add("selected");
        selectedCountry = btn.dataset.value;
    });
});


document.querySelectorAll(".wordLengthOption").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".wordLengthOption").forEach(b => b.classList.remove("selected"));
        btn.classList.add("selected");
        selectedWordLength = parseInt(btn.dataset.len);
    });
});

document.querySelectorAll(".nbrOfGuessesOption").forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelectorAll(".nbrOfGuessesOption").forEach(b => b.classList.remove("selected"));
        btn.classList.add("selected");
        selectedNbrOfGuesses = parseInt(btn.dataset.len);
    });
});



function startGame() {
    const bg = encodeURIComponent(document.getElementById("bgColor").value);
    const shape = selectedShape;
    const length = selectedWordLength;
    const guesses = selectedNbrOfGuesses;
    const language = selectedCountry;

    window.location.href =
        `/flexidle?bg=${bg}&shape=${shape}&language=${language}&length=${length}&guesses=${guesses}`;
}

function chooseRandomSettings(){

    //language remove current highlight
    document.querySelectorAll(".countryOption").forEach(btn => btn.classList.remove("selected"));
    //language randomize one
    let languageLength = document.querySelectorAll(".countryOption").length;
    let randomLanguage = Math.floor(Math.random() * languageLength);
    let languageBtn = document.querySelectorAll(".countryOption").item(randomLanguage);
    languageBtn.classList.add("selected");
    selectedCountry = languageBtn.dataset.value;

    //wordLength remove current highlight
    document.querySelectorAll(".wordLengthOption").forEach(btn => btn.classList.remove("selected"));
    //randomize it
    let wordLengthLength = document.querySelectorAll(".wordLengthOption").length;
    let randomWordLength = Math.floor(Math.random() * wordLengthLength);
    let wordLengthBtn = document.querySelectorAll(".wordLengthOption").item(randomWordLength);
    wordLengthBtn.classList.add("selected");
    selectedWordLength = parseInt(wordLengthBtn.dataset.len);


    //nbrOfGuesses remove current highlight
    document.querySelectorAll(".nbrOfGuessesOption").forEach(btn => btn.classList.remove("selected"));
    //randomize it
    let nbrOfGuessesLength = document.querySelectorAll(".nbrOfGuessesOption").length;
    let randomNbrGuesses = Math.floor(Math.random() * nbrOfGuessesLength);
    let nbrGuessesBtn = document.querySelectorAll(".nbrOfGuessesOption").item(randomNbrGuesses);
    nbrGuessesBtn.classList.add("selected");
    selectedNbrOfGuesses = parseInt(nbrGuessesBtn.dataset.len);



}