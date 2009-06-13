 $(document).ready(function() {
	 $("#lf_username").attr({ value: 'Usuario' }).focus(function(){
         if($(this).val()=="Usuario"){
            $(this).val("");
            $("#lf_password").val("");
         }
    }).blur(function(){
         if($(this).val()==""){
            $(this).val("Usuario");
         }
    });
 });


