/**
 * author: Isabell Persson
 * section: Toolbar
 */
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

/**
 * author: Isabell Persson, Cecilia Raalas
 * section: Toolbar
 */
function settingsPressed() {
    const settingsCostumize = new bootstrap.Modal(document.getElementById('settingsCostumize'));
    settingsCostumize.show();
}

/**
 * author: Isabell Persson, Elin Piho
 * section: Toolbar
 */
function statisticsPressed() {
    const statsModal = new bootstrap.Modal(document.getElementById('stats'));
    statsModal.show();
}

/**
 * author: Isabell Persson, Elin Piho
 * section: Toolbar
 */
function rulesPressed() {
    const rulesModal = new bootstrap.Modal(document.getElementById('rules'));
    rulesModal.show();
}

/**
 * author: Isabell Persson
 * section: Toolbar
 */
function returnPressed() {
    window.location.href = '/';
}

/**
 * author: Isabell Persson, Elin Piho
 * section: Toolbar
 */
function accountPressed() {
    const accountModal = new bootstrap.Modal(document.getElementById('log-in'));
    accountModal.show();
}

const params = new URLSearchParams(window.location.search);
const currentLanguage = params.get('language');

function setLanguageFlag() {
    const flagBtn = document.getElementById('language-flag');
    if (!flagBtn) return;

    const flags = {
        'swedish': '/images/swe-flag.png',
        'english': '/images/uk-flag.png',
        'german': '/images/ger-flag.png'
    };

    const flagSrc = flags[currentLanguage] || flags['english'];
    flagBtn.src = flagSrc;
    flagBtn.alt = currentLanguage;
}

setLanguageFlag();