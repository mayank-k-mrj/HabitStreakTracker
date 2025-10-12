let addhabit = document.querySelector('.add-button');
let url = "http://localhost:8080/hst/user";
let url2 = "http://localhost:8080/hst/habits/";
let url3 = "/userhabits";
let activeuser;
let habitNameElements = document.querySelectorAll('.habit-name');
let habitStreakElements = document.querySelectorAll('.habit-streak');
let habitDescElements = document.querySelectorAll('.habit-detail');
let url_getid = "http://localhost:8080/hst/habits/ids";
let url_streaks = "http://localhost:8080/hst/habits/";
let url_str = "/streaks";
let url_cur_str = "/currentStr";
const allHabitCards = document.querySelectorAll('.habit-card');
let habitTitle = document.querySelector('.habit-title');
let comp_url = "/compstreak";
let url_habitid = "/habitsbyid";
let compbtn = document.querySelector('.complete-button');
let streakday = document.querySelector('#streak-number');
let details = document.querySelector('.streak-details');
const interactiveCards = document.querySelectorAll('.card, .habit-card, .add-button');
let todayDay = document.querySelectorAll('.day');

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

addhabit.addEventListener("click", () => {
    window.open("HabitCreation.html");
})

user();

async function user() {
    let user = await fetch(url);
    if (user.ok) {
        let username = await user.text();
        activeuser = username;
        allhabits();
    }
    else {
        console.log("Something went wrong");
    }
}

async function allhabits() {
    let habits = await fetch(url2 + activeuser + url3);
    if (habits.ok) {
        console.log("Data fetched");
        let names = await habits.json();
        if (names.length > 0) {
            for (let i = 0; i < names.length; i++) {
                if (i >= habitNameElements.length) break;

                allHabitCards[i].dataset.habitId = names[i].id;

                habitNameElements[i].innerText = names[i].name;
                habitDescElements[i].innerText = names[i].description;

                let id = names[i].id;
                let streak = await fetch(url_streaks + id + url_str);

                if (streak.ok) {
                    console.log("Streak Fetched for habit ID:", id);
                    let streaks = await streak.json();
                    habitStreakElements[i].innerText = streaks.currentStreak + "- Day Streak";
                }
            }
        }
    }
    else {
        console.log("Something went wrong");
    }
}

allHabitCards.forEach(cards => {
    cards.addEventListener("click", async () => {
        const habitId = cards.dataset.habitId;
        if(habitId){
            window.open(`CompletionWeb.html?id=${habitId}`);
        }
    })
});

allHabitCards.forEach(cards => {
    cards.addEventListener("mouseover", async () => {
        const habitId = cards.dataset.habitId;
        let CResponse = await fetch(url_streaks + habitId + url_cur_str);
        if(CResponse.ok){
            let current = await CResponse.json();
            streakday.innerText = current;
        }
        let DResponse = await fetch(url_streaks + habitId + url_habitid);
        if(DResponse.ok){
            let detailsDesc = await DResponse.json();
            details.innerText = detailsDesc.description;
        }
    })
});

const today = new Date();
let day = today.getDay();
console.log(day);

todayDay[day].style.backgroundColor = "#3498DB";
todayDay[day].style.color = "white";