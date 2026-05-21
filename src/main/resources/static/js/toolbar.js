
document.getElementById("cogwheel-button")
    .addEventListener("click", () => settingsPressed());

document.getElementById("statistics-button")
    .addEventListener("click", () => statisticsPressed());

document.getElementById("rules-button")
    .addEventListener("click", () => rulesPressed());

document.getElementById("return-button")
    .addEventListener("click", () => returnPressed());

function settingsPressed() {
    console.log("SETTINGS PRESSED")
}

function statisticsPressed() {
    window.location.href = 'statistics';
}

function rulesPressed() {
    window.location.href = 'rules';
}

function returnPressed() {
    window.location.href = '/';
}