function getId(){
    var id = window
        .location
        .search
        .replace('?id=','');
    return id;
    }

    function getFormattedNote(){
        let id = getId();
        let request = new XMLHttpRequest();
        let noteTitlePlace = document.querySelector('.NoteTitlePlace')
        let notePlace = document.querySelector('.NotePlace');
              let url = "/note/formatted?id=" + getId();
              request.open("GET", url, true);
              request.setRequestHeader("Content-Type", "application/json");
              request.responseType='json';
              request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                          console.log("response received");
                          var formattedNote = request.response;
                          console.log(formattedNote);
                          noteTitlePlace.innerHTML=formattedNote.title;
                          notePlace.innerHTML=formattedNote.content;
                          }};
              console.log("sending request");
              request.send();
    }