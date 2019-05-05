
$(document).ready(function(){
    $("#langDetector").submit(function(e) {
        e.preventDefault();
        var form = $(this);
        var url = "http://localhost:8080/v1/detectLanguageForText";
        $.ajax({
            type: "POST",
            uri: url,
            data: form.serialize()
        }).done(function(data) {
                console.log(data);
                alert(data);
                displayResults(data);
        }).fail(function(e){
            console.log("Error" + e);
        })

    });

    function displayResults(result){
        var message;
        if (result != undefined && result.length == 1) {
            message = "Language identified is :" + result[0];
        }
        else if(result.length > 1) {
            message="Languages identified are : "
            for(var i=0;i<result.length; i++) {
                message += result[i];
            }

        }
        else{
            message = "Sorry, language not found. Try something else."
        }
        $(".resultContainer > p").append(message);
    }
});