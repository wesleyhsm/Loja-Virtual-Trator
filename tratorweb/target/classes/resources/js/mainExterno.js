$(document).ready(function() {
    
	/*
     MASK INPUT
	 */
	if($('.phonemask').length > 0){
		var SPMaskBehavior = function (val) {
			return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		},
		spOptions = {
				onKeyPress: function(val, e, field, options) {
					field.mask(SPMaskBehavior.apply({}, arguments), options);
				}
		};
		$('.phonemask').mask(SPMaskBehavior, spOptions);
	}

	if($('.numbermask').length > 0){
		$('.numbermask').mask('000000', {'translation': {
			0: {pattern: /[0123456789snSN]/}
		}
		});
	}
	
	$(".cpfmask").keydown(function(){
	    try {
	    	$(".cpfmask").unmask();
	    } catch (e) {}
	    
	    var tamanho = $(".cpfmask").val().length;
		
	    if(tamanho < 11){
	        $(".cpfmask").mask("999.999.999-99");
	    } else if(tamanho >= 11){
	        $(".cpfmask").mask("99.999.999/9999-99");
	    }                   
	});

});