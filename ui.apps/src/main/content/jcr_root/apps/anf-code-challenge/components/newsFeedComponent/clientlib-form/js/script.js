$(document).ready(function(){
    $('#abc_register').on('submit', function(e){
        e.preventDefault();
        $("#errorMsg").text("");
        var firstName = $('input[name="abc-firstname"]').val();
        var lastName = $('input[name="abc_lastname"]').val();
        var age = $('input[name="abc_age"]').val();
        var contry = $('select[name="abc_contry"]').val();
        if (firstName && lastName && age && contry) {
            var payload = {firstName, lastName, age, contry};
            $.ajax({
                type: 'post',
                url: '/bin/saveUserDetails',
                data: JSON.stringify(payload),
                contentType: "application/json; charset=utf-8",
                traditional: true,
                success: function (data) {
                    console.log('success');
                    alert("Request completed successfully!");
					$('#abc_register').reset();
                },
                error:function (error) {
                    console.log('error');
                    alert(error);
                }
            });
        }else{
			$("#errorMsg").text("Mandatory fields missiing!");
        }
    });
});