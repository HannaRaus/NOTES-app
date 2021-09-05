//<!-- script to truncate note content and add url button for read more --!>
function truncate (selector, maxLength) {
    var td = document.getElementById(selector);
	var text = td.innerHTML;

    if(text.length > maxLength) {
        //get substring with maxLength limit
    	newText = text.substr(0,maxLength);
        //set new text in td
        td.innerHTML = newText;

        //create new a element
	    let a = document.createElement('a');
		var link = td.getAttribute('href');
	    a.setAttribute('href',link);
	    a.textContent = '...';
        //append a element to td
	    td.appendChild(a);
	}

}



