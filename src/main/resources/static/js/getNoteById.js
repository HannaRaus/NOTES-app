function getId(){
    var id = window
        .location
        .search
        .replace('?id=','');
    return id;
    }

function getNote(){
    let id = getId();
    let request = new XMLHttpRequest();
    let title = document.querySelector('#title');
    let text = document.querySelector('#note');
    let accessType = document.querySelector('#accessType');
          let url = "/note?id=" + getId();
          request.open("GET", url, true);
          request.setRequestHeader("Content-Type", "application/json");
          request.responseType='json';
          request.onreadystatechange = function () {
                if (request.status === 200) {
                      var editedNote = request.response;
                      console.log(editedNote);
                      title.value = editedNote.title;
                      text.textContent = editedNote.content;
                      $("[name=accessType]").val([editedNote.accessType.toLowerCase()]);
                      }
                if(request.status === 404) {
                window.location.href = '/error';}};
          request.send();
}