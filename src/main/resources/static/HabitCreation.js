let submitbtn = document.querySelector('.submit-button');
let name = document.querySelector('#habit-name');
let desc = document.querySelector('#habit-description');
let freq = document.querySelector('#habit-frequency');
let stdate = document.querySelector('#start-date');
let stat = document.querySelector('.stat');
let url = "/hst/habits";
submitbtn.addEventListener("click", async (event) => {
    event.preventDefault();
    if(name.value != "" && desc.value != "" && stdate.value != ""){
        const habit = {
            name: name.value,
            description: desc.value,
            frequency: freq.value,
            startDate: stdate.value
        }
        let sendRsponse = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(habit)
        });
        if(sendRsponse.ok){
            console.log("Habit Created");
            stat.innerText = "Habit Created";
        }
        else{
            console.log("Something went wrong");
        }
    }
})