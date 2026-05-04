let name = document.querySelector('.edit-name');
let contact = document.querySelector('.edit-phone');
let userdob = document.querySelector('.edit-date');
let userbio = document.querySelector('.edit-bio');
let picnum = 1;
let btn = document.querySelector('.primary')
let mainprof = "http://localhost:8080/hst";
let getprof = "/profile/getprofile";
let editprof = "/profile/editdata";

getProfile();
let activeuser;
async function getProfile() {
    try {
        let profile = await fetch(mainprof + getprof, {credentials: 'include'})
        if (profile.ok) {
            console.log("Successfully fetched profile.");
            let data = await profile.json();
            console.log(data);
            name.value = data.nickname;
            contact.value = data.phone;
            userdob.value = data.dob;
            userbio.value = data.bio;
            picnum = data.propic;
            activeuser = data.user;
        }
        else {
            console.log("Error in fetching profile data.");
        }
    }
    catch(error){
        console.log("Error occured : ", error);
    }
}

async function setData(){
try{
    if(contact.value != "" && userdob.value != ""){
    let newnick = name.value;
    let newphone = contact.value;
    let newdob = userdob.value;
    let newbio = userbio.value;
        const editedData = {
            nickname: newnick,
            phone: newphone,
            dob: newdob,
            bio: newbio,
            propic: picnum
        }
        let sendResponse = await fetch(mainprof + editprof, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(editedData)
        });
        if(sendResponse.ok){
            console.log("Data Updated successfully.");
        }
        else{
            console.log("Error occured.");
        }
    }
    }
    catch(error){
        console.log("Error caught : ", error);
    }
}

btn.addEventListener("click", setData);