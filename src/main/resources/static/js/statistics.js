/*
author: Isabell Persson
section: Statistics
*/

let selCountry = "swedish";
let userLoggedIn = false;
let currentUser = null;

window.addEventListener("DOMContentLoaded", async () => {
    await checkUserLoggedIn();
    await updateView(selCountry)
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

async function updateView(language) {

    const wordLengths = [4, 5, 6];

    await Promise.all(wordLengths.map(async (wordLength) => {

        const gStats = await fetchAllSlices(
            language,
            wordLength,
            "global"
        );

        const gSegment = buildSegments(gStats);

        updateCircleDiagram("g"+wordLength, gSegment);

        // personal stats
        if (userLoggedIn && currentUser) {
            const pStats = await fetchAllSlices(
                language,
                wordLength,
                "personal",
                currentUser
            );

            const pSegments = buildSegments(pStats);

            updateCircleDiagram("p"+wordLength, pSegments);

        } else {
            clearDiagram("p"+wordLength);
        }
    }));
}

function buildSegments(stats) {
    const clean = stats.map(value => {
        const number = Number(value);
        return Number.isFinite(number) && number > 0 ? number : 0;
    })

    const totalSegment = clean.reduce((sum, value) => sum + value, 0);

    if (totalSegment === 0) {
        return clean.map((_, index) => ({
            value: 0,
            color: getColor(index)
        }));
    }

    return clean.map((value, index) => ({
        value: (value/totalSegment) * 100,
        color: getColor(index)
    }));
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

function getColor(index) {
    const colors = [
        "#4caf50",
        "#f0e130",
        "#555",
        "#ffc217",
        "#8fbc8f",
        "#eeeeee",
    ];
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
