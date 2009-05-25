$(document).ready(function() {
	addCalendar("#datepicker",
			function(){
				getHours();
			}
	);
	$("#whenForm").addClass("hidden");
});

function getHours() {

	var id = $("#id").attr("value");
	var date = $("#datepicker").attr("value");

	if( date == '' ) {
		return;
	}
	
	$.getJSON('/tp-pod/field/getattendinghours',{id:id, date:date},showHours);
	
	function showHours(data)
	{
		if( data['success'] ) {
			$("#whenForm").removeClass("hidden");
			
			$("#when").append('<option value="08:00 09:00">08:00 - 09:00</option>');
			$("#when").append('<option value="09:00 10:00">09:00 - 10:00</option>');
			$("#when").append('<option value="10:00 11:00">10:00 - 11:00</option>');		

		}
	}


}