$(document).ready( function() {
	addCalendar("#datepicker", function(input) {
		$("#whenForm").addClass("hidden");
	}, function(date) {
		getHours();
	});
	$("#whenForm").addClass("hidden");
});

function getHours() {

	var id = $("#id").attr("value");
	var date = $("#datepicker").attr("value");

	$("#whenError").addClass("hidden");

	if (date == '') {
		return;
	}

	$.getJSON('/tp-pod/field/getavailablehours', {
		id : id,
		date : date
	}, showHours);

	function showHours(data) {
		if (data['success']) {

			//Array comes unsorted

			var arr = data['availability'];

			if (arr == undefined || arr.length == 0) {
				$("#whenError").removeClass("hidden");
				return;
			}

			var keys = new Array();

			for (k in arr) {
				keys.push(k);
			}

			keys.sort( function(a, b) {
				return a - b;
			});

			$("#when").empty();
			for ( var i = 0; i < keys.length; i++) {
				var hours = arr[i].split(" - ");
				$("#when").append(
						'<option value="' + hours[0] + " " + hours[1] + '">'
								+ arr[i] + '</option>');
			}

			$("#whenForm").removeClass("hidden");

		}
	}

}