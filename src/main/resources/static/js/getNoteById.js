function getId(){
    var id = window
        .location
        .search
        .replace('?id=','');
    return id;
    }

function getNote(getId){
    let request = new XMLHttpRequest();
          let url = "/note/create";
          request.open("POST", url, true);
          request.setRequestHeader("Content-Type", "application/json");
          request.responseType='json';

}