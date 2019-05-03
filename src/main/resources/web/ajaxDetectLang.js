
$("#langDetector").submit(function(e) {
    e.preventDefault();
    var form = $(this);
    var url = "http://localhost:8080/v1/detectLanguageForText";
    $.ajax({
        type: "POST",
        uri: url,
        data: form.serialize(),
        success: function(data) {
            console.log(data);
            alert(data);
        }
    });
});