function getId(){
    var id = window
        .location
        .search
        .replace('?id=','');
        console.log(id);
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
                if (request.readyState === 4 && request.status === 200) {
                      console.log("response received");
                      var editedNote = request.response;
                      console.log(editedNote);
                      title.value = editedNote.title;
                      text.textContent = editedNote.content;
                      accessType.val([editedNote.accessType]);
                      }};
          console.log("sending request");
          request.send();
}