//<!-- script for copying url button --!>
function copyURI(evt) {
    evt.preventDefault();

    var baseURL = window.location.protocol + "//" + window.location.host // get base url
    var copyURL = baseURL + evt.target.getAttribute('href') // link forming

    navigator.clipboard.writeText(copyURL);
}