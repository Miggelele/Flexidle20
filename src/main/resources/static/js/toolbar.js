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
    //console.log("SETTINGS PRESSED")
    const settingsCostumize = new bootstrap.Modal(document.getElementById('settingsCostumize'));
    settingsCostumize.show();
}

function statisticsPressed() {
    const statsModal = new bootstrap.Modal(document.getElementById('stats'));
    statsModal.show();
}

function rulesPressed() {
    const rulesModal = new bootstrap.Modal(document.getElementById('rules'));
    rulesModal.show();
}

function returnPressed() {
    window.location.href = '/';
}

function accountPressed() {
    const accountModal = new bootstrap.Modal(document.getElementById('log-in'));
    accountModal.show();
}