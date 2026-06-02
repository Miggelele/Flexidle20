/*
author: Isabell Persson
section: Statistics
*/

let selCountry = "swedish";
let userLoggedIn = false;
let currentUser = null;

/*window.addEventListener("DOMContentLoaded", async () => {
    await checkUserLoggedIn();
    await updateView(selCountry)
});*/

document.querySelector('[data-bs-target="#stats"]')
    .addEventListener("click", async () => {
        await checkUserLoggedIn();
        await updateView(selCountry);
    });

document.querySelectorAll(".statistics-flag").forEach(flag => {

    flag.addEventListener("click", async () => {
        document.querySelectorAll(".statistics-flag")
            .forEach(f => f.classList.remove("selected"));

        flag.classList.add("selected");
        selCountry = flag.dataset.value;

        await updateView(selCountry);
    });
});

async function checkUserLoggedIn() {

    const userState = sessionStorage.getItem("currentUser");
    console.log("User: " + userState);
    if (!userState || userState === "UNKNOWN") {
        userLoggedIn = false;
        currentUser = null;
    } else {
        currentUser = userState;
        userLoggedIn = true;
    }

    console.log("currentUser:", currentUser);
    console.log("loggedIn:", userLoggedIn);
}

async function updateView(selLanguage) {

    const wordLengths = [4, 5, 6];

    const gStats = await fetchGlobalStatistics();
    //const pStats = null;

   /* if (userLoggedIn && currentUser) {
        const pStats = await fetchPersonalStatistics(currentUser)
    }*/

    await Promise.all(wordLengths.map(async (wordLength) => {

        /*const gStats = await fetchAllSlices(
            language,
            wordLength,
            "global"
        );*/

        //const gStats = await fetchGlobalStatistics();

        //const gSegment = buildSegments(gStats);

        let index;
        if (selLanguage === "swedish") {
            index =
                wordLength === 4 ? 0 :
                    wordLength === 5 ? 1 :
                        2;
        } else if (selLanguage === "english") {
            index =
                wordLength === 4 ? 3 :
                    wordLength === 5 ? 4 :
                        5;
        } else {
            index =
                wordLength === 4 ? 6 :
                    wordLength === 5 ? 7 :
                        8;
        }

       /* const index =
            wordLength === 4 ? 0 :
                wordLength === 5 ? 1 :
                    2;*/

        const gSegment = buildSegments(gStats[index], wordLength);

        updateCircleDiagram("g"+wordLength, gSegment);

        // personal stats
        if (userLoggedIn && currentUser) {
            const pStats = await fetchPersonalStatistics(currentUser)

            /*const index =
                wordLength === 4 ? 0 :
                    wordLength === 5 ? 1 :
                        2;*/

            let index;
            if (selLanguage === "swedish") {
                index =
                    wordLength === 4 ? 0 :
                        wordLength === 5 ? 1 :
                            2;
            } else if (selLanguage === "english") {
                index =
                    wordLength === 4 ? 3 :
                        wordLength === 5 ? 4 :
                            5;
            } else {
                index =
                    wordLength === 4 ? 6 :
                        wordLength === 5 ? 7 :
                            8;
            }

            const pSegments = buildSegments(pStats[index], wordLength);

            //const pSegments = buildSegments(pStats);

            updateCircleDiagram("p"+wordLength, pSegments);

        } else {
            clearDiagram("p"+wordLength);
        }

        //TODO lägg till förklaring av diagrammen
        //updateLegend("g6-legend", 6);
        updateLegend("statistics-legend", 6);
    }));
}

function buildSegments(stats, wordLength) {
    const clean = stats.map(value => {
        const number = Number(value);
        return Number.isFinite(number) && number > 0 ? number : 0;
    })

    const totalSegment = clean.reduce((sum, value) => sum + value, 0);

    if (totalSegment === 0) {
        return clean.map((_, index) => ({
            value: 0,
            color: getColor(index, wordLength)
        }));
    }

    return clean.map((value, index) => ({
        value: (value/totalSegment) * 100,
        color: getColor(index, wordLength)
    }));
}

//försöker hämta all personlig statistik samtidigt
async function fetchPersonalStatistics(username) {
    try {
        const response = await fetch(
            `/game_record/allPersonalStatistics/${encodeURIComponent(username)}`
        );

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const statistics = await response.json(); // int[][] från backend

        console.log("PERSONAL STATS");
        console.log(statistics);

        return Promise.all(statistics);
    } catch (error) {
        console.error("Kunde inte hämta statistik:", error);
        return null;
    }
}

async function fetchGlobalStatistics() {
    try {
        const response = await fetch(
            `/game_record/allGlobalStatistics`
        );

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const statistics = await response.json(); // int[][] från backend

        console.log("GLOBAL STATS");
        console.log(statistics);

        return Promise.all(statistics);
    } catch (error) {
        console.error("Kunde inte hämta statistik:", error);
        return null;
    }
}


async function fetchAllSlices(language, wordLength, type, username = null) {

    const slices = [];

    for (let maxGuesses = 1; maxGuesses <= 6; maxGuesses++) {

        let url;

        if (type === "global") {
            url = `/game_record/global/${language}/${wordLength}/${maxGuesses}`;
        } else {
            url = `/game_record/personal/${username}/${language}/${wordLength}/${maxGuesses}`;
        }

        slices.push(
            fetch(url)
                .then(async (result) => {
                    if (!result.ok) {
                        throw new Error("HTTP "+result.status);
                    }

                    const data = await result.json();
                    console.log(`Data (${selCountry}): `+data);
                    return Array.isArray(data) ? (data[0] ?? 0) : 0;
                })
                .catch(() => 0)
        );
    }
    return Promise.all(slices);
}

function getColor(index, wordLength) {
    let colors;

    if (wordLength === 4) {
        colors = [
            "#4caf50",
            "#f0e130",
            "#555",
            "#ffc217",
            "#f44336",
        ];
    } else if (wordLength === 5) {
        colors = [
            "#4caf50",
            "#f0e130",
            "#555",
            "#ffc217",
            "#8fbc8f",
            "#f44336",
        ];
    } else {
        colors = [
            "#4caf50",
            "#f0e130",
            "#555",
            "#ffc217",
            "#8fbc8f",
            "#eeeeee",
            "#f44336",
        ];
    }
    return colors[index] || "#757575";
}

function updateCircleDiagram(id, segments) {
    const circle = document.getElementById(id);

    if (!circle) {
        return;
    }

    let currentPercent = 0;
    const gradientParts = [];

    segments.forEach(segment => {
        const start = currentPercent;
        const end = currentPercent + segment.value;

        gradientParts.push(
            `${segment.color} ${start}% ${end}%`
        );

        currentPercent = end;
    });
    circle.style.background = `conic-gradient(${gradientParts.join(', ')})`;
}

function clearDiagram(id) {
    const circle = document.getElementById(id);

    if (!circle) {
        return;
    }
    circle.style.background = "conic-gradient(#ddd 0% 100%)";
}

//färgförklaringen
function updateLegend(id, wordLength) {

    const legend = document.getElementById(id);

    if (!legend) {
        return;
    }

    legend.innerHTML = "";

    for (let i = 0; i < wordLength; i++) {

        const row = document.createElement("div");
        row.className = "legend-item";

        const colorBox = document.createElement("span");
        colorBox.className = "legend-color";
        colorBox.style.backgroundColor = getColor(i, wordLength);

        const text = document.createElement("span");
        text.textContent = `= ${i + 1} guess`;

        row.appendChild(colorBox);
        row.appendChild(text);

        legend.appendChild(row);
    }

    // röd = misslyckades
    const failRow = document.createElement("div");
    failRow.className = "legend-item";

    const failColor = document.createElement("span");
    failColor.className = "legend-color";
    failColor.style.backgroundColor = "#f44336";

    const failText = document.createElement("span");
    failText.textContent = "= Fail";

    failRow.appendChild(failColor);
    failRow.appendChild(failText);

    legend.appendChild(failRow);
}