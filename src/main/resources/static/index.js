let addhabit = document.querySelector('.add-button');
let url = "/hst/user";
let url2 = "/hst/habits/";
let url_getid = "/hst/habits/ids";
let url_streaks = "/hst/habits/";
let url_totalHabits = "/totalhabit";
let url3 = "/userhabits";
let activeuser;
let habitNameElements = document.querySelectorAll('.habit-name');
let habitStreakElements = document.querySelectorAll('.habit-streak');
let habitDescElements = document.querySelectorAll('.habit-detail');
let url_str = "/streaks";
let url_cur_str = "/currentStr";
let habitTitle = document.querySelector('.habit-title');
let comp_url = "/compstreak";
let url_habitid = "/habitsbyid";
let compbtn = document.querySelector('.complete-button');
let streakday = document.querySelector('#streak-number');
let details = document.querySelector('.streak-details');
let todayDay = document.querySelectorAll('.day');

// This makes the cards move a little when you touch them so they feel alive
function interactions() {
    const interactiveCards = document.querySelectorAll('.card, .habit-card, .add-button');
    interactiveCards.forEach(card => {
        card.onmouseenter = () => {
            card.style.transition = 'transform 0.2s ease-out';
            card.style.transform = 'translateY(-5px) scale(1.03)';
        };

        card.onmouseleave = () => {
            card.style.transform = 'translateY(0) scale(1)';
        };
        card.onmousedown = () => {
            card.style.transition = 'transform 0.1s ease-in';
            card.style.transform = 'translateY(0) scale(0.98)';
        };

        card.onmouseup = () => {
            card.style.transition = 'transform 0.1s ease-out';
            card.style.transform = 'translateY(-5px) scale(1.03)';
        };
    });
}

// When you click add, I load the new page in THIS tab instead of opening a new one
if (addhabit) {
    addhabit.addEventListener("click", () => {
        window.location.href = "HabitCreation.html";
    });
}

// Start everything here: First I find the user, then I load their stuff
user();

async function user() {
    try {
        let userRes = await fetch(url);
        if (userRes.ok) {
            let username = await userRes.text();
            activeuser = username;
            // Now that I know who the user is, I can check how many habits they have
            NoOfTotalHabits();
        } else {
            console.error("Failed to fetch user. Status:", userRes.status);
        }
    } catch (error) {
        console.error("Error fetching user:", error);
    }
}

// I check how many habits the user has. If they have a lot, I make more empty cards on the screen
async function NoOfTotalHabits() {
    if (!activeuser) return;

    try {
        let tlHabit = await fetch(url2 + activeuser + url_totalHabits);
        if (tlHabit.ok) {
            let totalNoText = await tlHabit.text();
            let totalNo = parseInt(totalNoText);
            console.log("Total Habits " + totalNo);

            let abc = "habit-card";
            let color = ".green";

            // If they have more than 6 habits, I need to copy the card design and make new ones
            for (let i = 6; i < totalNo; i++) {
                if (color === ".green") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".blue";
                } else if (color === ".blue") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".purple";
                } else if (color === ".purple") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".teal";
                } else if (color === ".teal") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".indigo";
                } else if (color === ".indigo") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".orange";
                } else if (color === ".orange") {
                    CopyDiv(abc + color, "habits-grid");
                    color = ".green";
                }
            }
            // Now that the empty cards are built, I can fill them with text
            allhabits();
        } else {
            console.log("Error finding total habits :(");
        }
    } catch (e) {
        console.error("Error in NoOfTotalHabits", e);
    }
}

// This is a helper tool. It takes one card and copies it so I don't have to write HTML for every single habit
function CopyDiv(HabitCard, ContainerClass) {
    let newGrid = document.querySelector(`.${ContainerClass}`);
    let newDiv = document.querySelector(`.${HabitCard}`);

    if (!newGrid || !newDiv) {
        console.log("Error: newGrid or newDiv not found.");
        return;
    }

    const cloneDiv = newDiv.cloneNode(true);
    let allCardsInContainer = newGrid.querySelectorAll('.habit-card');
    let newNum = allCardsInContainer.length + 1;

    let HabitNameInClone = cloneDiv.querySelector('.habit-name');
    let HabitDetailInClone = cloneDiv.querySelector('.habit-detail');
    let HabitStreakInClone = cloneDiv.querySelector('.habit-streak');

    // I need to give the new cards unique IDs or things might get messy
    if (HabitNameInClone) {
        HabitNameInClone.id = "habit-name" + newNum;
        HabitNameInClone.innerText = "Loading...";
    }
    if (HabitDetailInClone) HabitDetailInClone.id = "habit-detail" + newNum;
    if (HabitStreakInClone) HabitStreakInClone.id = "habit-streak" + newNum;

    newGrid.appendChild(cloneDiv);
    // Apply the movement animation to the new cards too
    interactions();
}

// Now I go get the actual habit names and numbers and write them onto the cards
async function allhabits() {
    if (!activeuser) {
        return;
    }

    try {
        let habits = await fetch(url2 + activeuser + url3);
        if (habits.ok) {
            console.log("Data fetched");
            // I need to look for the cards again because I just added new ones in the previous function
            let habitNameElement = document.querySelectorAll('.habit-name');
            let habitStreakElement = document.querySelectorAll('.habit-streak');
            let habitDescElement = document.querySelectorAll('.habit-detail');
            const allHabitCard = document.querySelectorAll('.habit-card');

            let names = await habits.json();

            if (names.length > 0) {
                for (let i = 0; i < names.length; i++) {
                    if (i >= allHabitCard.length) break;

                    // I'm saving the ID inside the card so I know which one is clicked later
                    allHabitCard[i].dataset.habitId = names[i].id;

                    if(habitNameElement[i]) habitNameElement[i].innerText = names[i].name;
                    if(habitDescElement[i]) habitDescElement[i].innerText = names[i].description;

                    // Getting the streak number for each specific habit
                    let id = names[i].id;
                    try {
                        let streak = await fetch(url_streaks + id + url_str);
                        if (streak.ok) {
                            let streaks = await streak.json();
                            if(habitStreakElement[i]) habitStreakElement[i].innerText = streaks.currentStreak + "- Day Streak";
                        }
                    } catch (error) {
                        console.error("Error fetching streak for habit ID:", id, error);
                    }
                }
            }
            // Once data is loaded, I turn on the click and hover features
            responsiveCards();
            attachHoverDetails();
        }
    } catch (error) {
        console.error("Error fetching all habits:", error);
    }
}

// This makes the cards clickable. I use location.href so it stays in the same tab.
function responsiveCards() {
    const allHabitCards = document.querySelectorAll('.habit-card');
    allHabitCards.forEach(cards => {
        cards.onclick = async () => {
            const habitId = cards.dataset.habitId;
            if (habitId) {
                window.location.href = `CompletionWeb.html?id=${habitId}`;
            }
        };
    });
}

// When you hover over a card, I show the specific streak info on the side
function attachHoverDetails() {
    const allHabitCards = document.querySelectorAll('.habit-card');
    allHabitCards.forEach(cards => {
        cards.onmouseover = async () => {
            const habitId = cards.dataset.habitId;
            if (!habitId) return;

            try {
                let CResponse = await fetch(url_streaks + habitId + url_cur_str);
                if (CResponse.ok) {
                    let current = await CResponse.json();
                    if(streakday) streakday.innerText = current;
                }

                let DResponse = await fetch(url_streaks + habitId + url_habitid);
                if (DResponse.ok) {
                    let detailsDesc = await DResponse.json();
                    if(details) details.innerText = detailsDesc.description;
                }
            } catch (error) {
                console.error("Error on mouseover data fetch:", error);
            }
        };
    });
}

// I check what day it is today and color that box blue
const today = new Date();
let day = today.getDay();
console.log("Current Day Index:", day);

if (todayDay && todayDay.length > day && todayDay[day]) {
    todayDay[day].style.backgroundColor = "#3498DB";
    todayDay[day].style.color = "white";
}

interactions();