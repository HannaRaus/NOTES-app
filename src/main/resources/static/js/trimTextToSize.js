//<!-- script to truncate note content and add url button for read more --!>
    var maxLength = 500;

jQuery(document).ready(function($) {
    $(".content").click(function() {
        window.location = $(this).data("href");
    });
});

$(document).ready(function(){
    $(".content").each(function(){
        var myStr = $(this).text();
        if($.trim(myStr).length > maxLength){
            var newStr = myStr.substring(0, maxLength);
            $(this).empty().html(newStr + '...');
        }
    });
});



