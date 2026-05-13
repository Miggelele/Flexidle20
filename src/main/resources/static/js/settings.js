let selectedShape = "square";
let selectedWordLength = 5;
let selectedNbrOfGuesses = 5;
let selectedCountry = "german";

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
    //TODO: actually randomise this later
    //so that random alternatives light up and you can choose to PLAY with random settings.

}