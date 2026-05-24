const cog = document.getElementById("cogwheel-button");
if (cog) cog.addEventListener("click", settingsPressed);

const stats = document.getElementById("statistics-button");
if (stats) stats.addEventListener("click", statisticsPressed);

const rules = document.getElementById("rules-button");
if (rules) rules.addEventListener("click", rulesPressed);

const ret = document.getElementById("return-button");
if (ret) ret.addEventListener("click", returnPressed);

const acc = document.getElementById("account-button");
if (acc) acc.addEventListener("click", accountPressed);

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

function accountPressed() {
    console.log("ACCOUNT PRESSED")
}