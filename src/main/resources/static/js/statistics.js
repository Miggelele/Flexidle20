/*
author: Isabell Persson
section: Statistics
*/

// select country + logs in console
let selectedCountry = "swedish";

document.querySelectorAll(".statistics-flag").forEach(flag => {
    flag.addEventListener("click", () => {
        document.querySelectorAll(".statistics-flag")
            .forEach(f => f.classList.remove("selected"));

        flag.classList.add("selected");
        selectedCountry = flag.dataset.value;

        console.log("Selected country:", selectedCountry);
    });
});

// function to update values in circle diagram
function updateCircleDiagram(id, segments) {
    const circle = document.getElementById(id);

    if (!circle) {
        console.error(`Element with id "${id}" not found.`);
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

// updating diagrams
// (id = first letter in global or personal + wordlength) so personal data on wordlength 4 makes the id p4
updateCircleDiagram("g4", [
    { color: "#4caf50", value: 10 },
    { color: "#f0e130", value: 10 },
    { color: "#555", value: 25 },
    { color: "#ffc217", value: 35 },
    { color: "#8fbc8f", value: 15 },
    { color: "#eeeeee", value: 5 },
]);

updateCircleDiagram("g5", [
    { color: "red", value: 25 },
    { color: "blue", value: 15 },
    { color: "yellow", value: 20 },
    { color: "magenta", value: 15 },
    { color: "orange", value: 10 },
    { color: "cyan", value: 15 },
]);

updateCircleDiagram("g6", [
    { color: "#e9967a", value: 12 },
    { color: "#e75480", value: 15 },
    { color: "#ffbcd9", value: 22 },
    { color: "#f88379", value: 8 },
    { color: "#fbcce7", value: 16 },
    { color: "#de3163", value: 27 },
]);

updateCircleDiagram("p4", [
    { color: "#ff0000", value: 25 },
    { color: "#ffa500", value: 15 },
    { color: "#ffff00", value: 15 },
    { color: "#00ff00", value: 10 },
    { color: "#0000ff", value: 25 },
    { color: "#800080", value: 10 },
]);

updateCircleDiagram("p5", [
    { color: "#5d8aa8", value: 10 },
    { color: "#007fff", value: 15 },
    { color: "#a1caf1", value: 25 },
    { color: "#21abcd", value: 30 },
    { color: "#6699cc", value: 15 },
    { color: "#2a52be", value: 5 },
]);

updateCircleDiagram("p6", [
    { color: "#03c03c", value: 17 },
    { color: "#ffff31", value: 17 },
    { color: "#555", value: 17 },
    { color: "#ffa700", value: 17 },
    { color: "#7fff00", value: 17 },
    { color: "#eeeeee", value: 17 },
]);