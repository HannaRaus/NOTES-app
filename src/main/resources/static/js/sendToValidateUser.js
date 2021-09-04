function sendToValidateAndCreateUser() {

    const bcrypt = require('bcrypt')
    let password = document.querySelector("#password");
    let result = document.querySelector('.result');
    result.innerHTML = "in work";
    let name = document.querySelector('#name');
    let passwordHash = bcrypt.hashSync(password, 10);
    console.log(passwordHash);
    let request = new XMLHttpRequest();
    console.log(request);



    let url = "http://localhost:9999/user/registration";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json'
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            const operationStatus = request.response;
            result.innerHTML = operationStatus;
            if (operationStatus.success === true) {
                window.location.href = '/login';
            } else {
                result.innerHTML = operationStatus.errors;
                let errors = operationStatus.errors;
            }
        } else {
            result.innerHTML = "something gone wrong"
        }
    };
    const data = JSON.stringify({"name": name.value, "password": passwordHash.value});
    result.innerHTML = data;
    request.send(data);
}