
function postWithoutMessageBack(toURL, toData) {

    $.ajax({
        type: "POST",
        url: toURL,
        data: toData,
        dataType: "json",
        async: false,
        success: function(data) {
        },
        error: function(){
			alert("Error in POST to URL: " + toURL + "- with Data: " + toData);
        }
    }); 
	
}

function postWithMessageBack(toURL, toData) {
	
	var message;
	
    $.ajax({
        type: "POST",
        url: toURL,
        data: toData,
        dataType: "json",
        async: false,
        success: function(data) {
        	alert("Success");
			message = data.msgapi;
        },
        error: function(){
			alert("Error in POST to URL: " + toURL + " - with Data: " + toData);
        }
    }); 
	
    return message;
}