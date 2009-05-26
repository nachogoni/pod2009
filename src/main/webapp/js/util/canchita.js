function Canchita() {
	
}

Canchita.prototype = {
		
		setTooltip: function(element,tooltipMsg) {
			
			$(element).tooltip({ 
			    track: true, 
			    delay: 0, 
			    showURL: false, 
			    opacity: 1, 
			    fixPNG: true, 
			    showBody: " - ", 
			    extraClass: "pretty fancy", 
			    top: -15, 
			    left: 5,
			    bodyHandler: function() { 
			        return tooltipMsg; 
			    }
			}); 
	
		}
}

canchita = new Canchita();
