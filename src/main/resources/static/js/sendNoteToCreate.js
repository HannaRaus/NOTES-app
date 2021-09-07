        function sendNoteToCreate(){
            const WRONG_TITLE_LENGTH = "The title mast be at least 5 symbols, up to 100 symbols";
            const WRONG_NOTE_LENGTH = "The note must be at least 5 symbols, up to 10000 symbols";
            const WRONG_ACCESS_TYPE = "Please choose correct access type";
            const NOT_UNIQUE_TITLE = "Please use unique title"
            let titleErrorField = document.querySelector('.titleErrorField');
            titleErrorField.innerHTML = null;
            let contentErrorField = document.querySelector('.contentErrorField');
            contentErrorField.innerHTML = null;
            let accessTypeErrorField = document.querySelector('.accessTypeErrorField');
            accessTypeErrorField.innerHTML = null;
            let title = document.querySelector('#title');
            let text = document.querySelector('#note');
            let accessType = document.querySelector('input[name="accessType"]:checked');
            let request = new XMLHttpRequest();
            let url = "/note/create";
            request.open("POST", url, true);
            request.setRequestHeader("Content-Type", "application/json");
            request.responseType='json'
                request.onreadystatechange = function () {
                 if (request.readyState === 4 && request.status === 200) {
                    var operationStatus = request.response;
                    if (operationStatus.success===true){
                        window.location.href = '/';
                    } else{
                        operationStatus.errors.forEach(function(error) {
                            switch (error){
                            case 'WRONG_NOTE_TITLE_LENGTH' :
                                titleErrorField.innerHTML = WRONG_TITLE_LENGTH;
                                break;
                            case 'WRONG_NOTE_CONTENT_LENGTH':
                                contentErrorField.innerHTML = WRONG_NOTE_LENGTH;
                                break;
                            case 'NOTE_ACCESS_TYPE_IS_NOT_CHOSEN':
                                accessTypeErrorField.innerHTML = WRONG_ACCESS_TYPE;
                                break;
                            case 'WRONG_ACCESS_TYPE':
                                accessTypeErrorField.innerHTML = WRONG_ACCESS_TYPE;
                                break;
                            case 'NOTE_TITLE_NOT_UNIQUE_FOR_CURRENT_USER':
                            titleErrorField.innerHTML = NOT_UNIQUE_TITLE;
                            break;
                            }
                            })
                 }}
            };
            var data = JSON.stringify({ "title": title.value, "content": text.value, "accessType": accessType.value });
            request.send(data);
        }