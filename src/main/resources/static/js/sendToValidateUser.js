function sendToValidateAndCreateUser() {

    let result = document.querySelector('.result');
    let name = document.querySelector('#username');
    let password = document.querySelector("#password");
    console.log(name, password);
    let request = new XMLHttpRequest();
    console.log(request);

    let url = "/user/registration";
    request.open("POST", url, true);
    request.setRequestHeader("Content-Type", "application/json");
    request.responseType = 'json'
    request.onreadystatechange = function () {
        if (/*request.readyState === 4 && */request.status === 200) {
            var operationStatus = request.response;
            result.innerHTML = operationStatus;
            if (operationStatus.success === true) {
                window.location.href = '/login';
            } else {
                result.innerHTML = operationStatus.errors;
            }
        } else {
            result.innerHTML = request.statusText;
        }
    };
    const data = JSON.stringify({"name": name.value, "password": password.value});
    result.innerHTML = data;
    request.send(data);
}