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
const allHabitCards = document.querySelectorAll('.habit-card');
let habitTitle = document.querySelector('.habit-title');
let comp_url = "/compstreak";
let url_habitid = "/habitsbyid";
let compbtn = document.querySelector('.complete-button');
let streakday = document.querySelector('#streak-number');
let details = document.querySelector('.streak-details');
let todayDay = document.querySelectorAll('.day');

function interactions(){
    const interactiveCards = document.querySelectorAll('.card, .habit-card, .add-button');
    interactiveCards.forEach(card => {
        card.addEventListener('mouseenter', () => {
            card.style.transition = 'transform 0.2s ease-out';
            card.style.transform = 'translateY(-5px) scale(1.03)';
        });

        card.addEventListener('mouseleave', () => {
            card.style.transform = 'translateY(0) scale(1)';
        });
        card.addEventListener('mousedown', () => {
            card.style.transition = 'transform 0.1s ease-in';
            card.style.transform = 'translateY(0) scale(0.98)';
        });

        card.addEventListener('mouseup', () => {
            card.style.transition = 'transform 0.1s ease-out';
            card.style.transform = 'translateY(-5px) scale(1.03)';
        });
    });
    }

addhabit.addEventListener("click", () => {
    window.close();
    window.open("HabitCreation.html");
})

user();

async function NoOfTotalHabits(){
    let tlHabit = await fetch(url2 + activeuser + url_totalHabits)
    if (tlHabit.ok){
        let totalNoText = await tlHabit.text();
        let totalNo = parseInt(totalNoText);
        console.log("Total Habits "+ totalNo);
        let abc = "habit-card";
        let color = ".green";
        for(let i = 6; i < totalNo; i++){
            console.log("Trying run new program");
            if(color === ".green"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".blue";
            }
            else if(color === ".blue"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".purple";
            }
            else if(color === ".purple"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".teal";
            }
            else if(color === ".teal"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".indigo";
            }
            else if(color === ".indigo"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".orange";
            }
            else if(color === ".orange"){
                let newCont = abc + color;
                CopyDiv(newCont, "habits-grid");
                color = ".green";
            }
        }
    }
    else{
        console.log("Error finding total habits :(");
    }
}

NoOfTotalHabits();

function CopyDiv(HabitCard, ContainerClass) {
    let newGrid = document.querySelector(`.${ContainerClass}`);
    let newDiv = document.querySelector(`.${HabitCard}`);

    if(!newGrid || !newDiv){
        console.log("Error: newGrid  or newDiv not found.");
    }

    //Here I am cloning the Node(Tag) entirely.
    const cloneDiv = newDiv.cloneNode(true);

    //Increasing the number if elements in the section.
    let allCardsInContainer = newGrid.querySelectorAll('.habit-card');
    let newNum = allCardsInContainer.length + 1;

    //Cloning the existing div.
    let HabitNameInClone = cloneDiv.querySelector('.habit-name');
    let HabitDetailInClone = cloneDiv.querySelector('.habit-detail');
    let HabitStreakInClone = cloneDiv.querySelector('.habit-streak');

    if(!HabitNameInClone || !HabitDetailInClone || !HabitStreakInClone){
        console.log("Error: HabitName or HabitDetail or HabitStreak not found.");
    }

    //Creating new id for all cloned elements.
    const HabitNameId = "habit-name" + newNum;
    const HabitDetailId = "habit-detail" + newNum;
    const HabitStreakId = "habit-streak" + newNum;

    //Removing old Ids.
    HabitNameInClone.removeAttribute('id');
    HabitDetailInClone.removeAttribute('id');
    HabitStreakInClone.removeAttribute('id');

    //Setting new Ids to the elements.
    HabitNameInClone.setAttribute('id', HabitNameId);
    HabitDetailInClone.setAttribute('id', HabitDetailId);
    HabitStreakInClone.setAttribute('id', HabitStreakId);

    newGrid.appendChild(cloneDiv);

    interactions();

}

async function user() {
    try {
        let user = await fetch(url);
        if (user.ok) {
            let username = await user.text();
            activeuser = username;
            allhabits();
        } else {
            console.error("Failed to fetch user. Status:", user.status);
        }
    } catch (error) {
        console.error("Error fetching user:", error);
    }
}

async function allhabits() {
    if (!activeuser) {
        console.error("No active user set. Cannot fetch habits.");
        return;
    }

    try {
        let habits = await fetch(url2 + activeuser + url3);
        if (habits.ok) {
            console.log("Data fetched");
            let habitNameElement = document.querySelectorAll('.habit-name');
            let habitStreakElement = document.querySelectorAll('.habit-streak');
            let habitDescElement = document.querySelectorAll('.habit-detail');
            const allHabitCard = document.querySelectorAll('.habit-card');
            let names = await habits.json();
            if (names.length > 0) {
                for (let i = 0; i < names.length; i++) {
                    if (i >= habitNameElement.length) break;

                    allHabitCard[i].dataset.habitId = names[i].id;

                    habitNameElement[i].innerText = names[i].name;
                    habitDescElement[i].innerText = names[i].description;

                    let id = names[i].id;
                    try {
                        let streak = await fetch(url_streaks + id + url_str);
                        if (streak.ok) {
                            console.log("Streak Fetched for habit ID:", id);
                            let streaks = await streak.json();
                            habitStreakElement[i].innerText = streaks.currentStreak + "- Day Streak";
                        } else {
                            console.error("Failed to fetch streak for habit ID:", id, "Status:", streak.status);
                        }
                    } catch (error) {
                        console.error("Error fetching streak for habit ID:", id, error);
                    }
                }
            }
        } else {
            console.error("Failed to fetch habits. Status:", habits.status);
        }
    } catch (error) {
        console.error("Error fetching all habits:", error);
    }
}

allHabitCards.forEach(cards => {
    cards.addEventListener("click", async () => {
        const habitId = cards.dataset.habitId;
        if (habitId) {
            window.close();
            window.open(`CompletionWeb.html?id=${habitId}`);
        }
    })
});

allHabitCards.forEach(cards => {
    cards.addEventListener("mouseover", async () => {
        const habitId = cards.dataset.habitId;
        if (!habitId) return; // Don't fetch if there's no habit ID

        try {
            // Fetch current streak
            let CResponse = await fetch(url_streaks + habitId + url_cur_str);
            if (CResponse.ok) {
                let current = await CResponse.json();
                streakday.innerText = current;
            } else {
                console.error("Failed to fetch current streak on mouseover. Status:", CResponse.status);
            }

            // Fetch habit details
            let DResponse = await fetch(url_streaks + habitId + url_habitid);
            if (DResponse.ok) {
                let detailsDesc = await DResponse.json();
                details.innerText = detailsDesc.description;
            } else {
                console.error("Failed to fetch habit details on mouseover. Status:", DResponse.status);
            }
        } catch (error) {
            console.error("Error on mouseover data fetch:", error);
        }
    })
});

const today = new Date();
let day = today.getDay();
console.log(day);

if (todayDay[day]) {
    todayDay[day].style.backgroundColor = "#3498DB";
    todayDay[day].style.color = "white";
}