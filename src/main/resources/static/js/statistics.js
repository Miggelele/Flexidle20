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
    try {

        const userState = sessionStorage.getItem("currentUser");
        if (!userState || userState === "UNKNOWN") {
            throw new Error("NO USER");
        }
        currentUser = JSON.parse(userState);
        userLoggedIn = true;

    } catch (error) {
        userLoggedIn = false;
        currentUser = null;
    }
}

async function updateView(language) {

    const wordLengths = [4, 5, 6];

    await Promise.all(wordLengths.map(async (wordLength) => {

        // global stats
        const gStats = await fetchAllSlices(language, wordLength, "global");

        const gSegment = gStats.map((value, index) => ({
            value,
            color: getColor(index)
        }));

        updateCircleDiagram("g"+wordLength, gSegment);

        // personal stats
        if (userLoggedIn && currentUser?.username) {

            const pStats = await fetchAllSlices(
                language,
                wordLength,
                "personal",
                currentUser.username
            );

            const pSegments = pStats.map((value, index) => ({
                value,
                color: getColor(index)
            }));

            updateCircleDiagram("p"+wordLength, pSegments)

        } else {
            clearDiagram("p"+wordLength);
        }
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
                .then(result => {
                    if (!result.ok) {
                        throw new Error(result.status);
                    }
                    return result.json();
                })
                .then(slice => slice[0] ?? 0)
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
    return colors[index] || "#ccc";
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
