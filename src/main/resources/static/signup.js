let url = "http://localhost:8080/hst/signup";
let submit = document.querySelector('.submit-button');
let name = document.querySelector('#fullname');
let email = document.querySelector('#email');
let password = document.querySelector('#password');

submit.addEventListener("click", async (event) => {
    event.preventDefault();
    if(name.value != ""  && email.value != "" && password.value != ""){
        const details = {
            username: name.value,
            password: password.value,
            email: email.value
        }
        let sendRsponse = await fetch(url, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(details)
        });
        if (sendRsponse.ok){
            console.log("Account Created");
            window.close();
        }
        else {
            console.log("Something went wrong");
        }
    }
    })