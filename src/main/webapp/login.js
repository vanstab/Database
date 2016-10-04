/**
 * 
 */

$('#loginForm').on('submit',function(e){
	e.preventDefault();
	var content = '';
	var url = window.location.origin+'/Database/api/login';
	content = JSON.parse('{"username":"' + $('#username').val() + '", "password":"' + $('#password').val()+ '"}');
	$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(content),
		contentType: "application/json; charset=utf-8",
		success:function(){
			$('body').load("HTML/page.html")
		},
		error:function(){
			window.alert("fail");
		}
	});
});
