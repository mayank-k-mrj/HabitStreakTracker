const compbtn = document.querySelector('.complete-button');
const url2 = "http://localhost:8080/hst/habits/";
const comp_url = "/compstreak";
const url3 = "/habitsbyid";
const url4 = "/streaks";
let habitTitle = document.querySelector('.habit-title');
let habitFreq = document.querySelector('.habit-frequency-badge');
let habitDesc = document.querySelector('.habit-description');
let current = document.querySelector('.streak-value');
let longest = document.querySelector('#streak_long');
let lastDate = document.querySelector('.info-value');

const urlParams = new URLSearchParams(window.location.search);
const habitId = urlParams.get('id');

getData();

async function getData(){
    let habits = await fetch(url2 + habitId + url3);
    if(habits.ok){
        console.log("Working fine");
        let habit = await habits.json();
        if(habit){
            let habitname = habit.name;
            habitTitle.innerText = habitname;
            let habitfreqency = habit.frequency;
            habitFreq.innerText = habitfreqency;
            let habitdescription = habit.description;
            habitDesc.innerText = habitdescription;
        }
    }
}

streakData();

async function streakData(){
    let streaks = await fetch(url2 + habitId + url4);
    if(streaks.ok){
        console.log("Streak Fetched");
        let streak = await streaks.json();
        if(streak){
            let streakcurrent = streak.currentStreak;
            current.innerText = streakcurrent + " Days";
            let streaklongest = streak.longestStreak;
            longest.innerText = streaklongest + " Days";
            let streaklastdate = streak.lastCompletedDate;
            lastDate.innerText = streaklastdate;
        }
    }
}

compbtn.addEventListener("click", async () => {
    if (!habitId) {
        alert("Error: No habit ID found!");
        return;
    }

    const response = await fetch(url2 + habitId + comp_url, {
        method: "POST"
    });

    if (response.ok) {
        alert("Habit marked as complete!");
        window.close();
    } else {
        alert("Failed to mark habit as complete.");
    }
});