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
        let notePlace = document.querySelector('.NotePlace');
              let url = "/note/formatted?id=" + getId();
              request.open("GET", url, true);
              request.setRequestHeader("Content-Type", "text/html");
              request.responseType='text';
              request.onreadystatechange = function () {
                    if (request.readyState === 4 && request.status === 200) {
                          console.log("response received");
                          var formattedNote = request.response;
                          console.log(formattedNote);
                          notePlace.innerHTML=formattedNote;
                          }};
              console.log("sending request");
              request.send();
    }