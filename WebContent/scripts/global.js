function validSponsor(whichForm) {
    var amount = Number($(':input[name=amount]', whichForm).val());
    var reward = Number($(':radio[name=reward]:checked', whichForm).val());
    if (reward > amount) {
        
        $('#notice', whichForm).empty()
        .attr('style', 'color:red;')
        .append('<em>Sorry, you must pledge at least $' + reward + ' to select that reward.</em>');
        
        return false;
    }
    
    return true;
}

function validateForm(whichForm, trigger) {
    var triggerAttr = trigger.attr("formnovalidate");
    if (typeof triggerAttr !== 'undefined' && triggerAttr !== false) {
       return true;
    }
    
    var valid = true;
        
    if (!Modernizr.input.required) {
        $(':input[required]', whichForm).each(function() {
            if ((!$(this).val()) || ($(this).val() == $(this).attr('placeholder'))) {
                valid = false;
                return valid;
            }
        });
    }
    
    if (!Modernizr.inputtypes.number) {
        if (valid) {
            $(':input[type=number]', whichForm).each(function() {
                if (!$.isNumeric($(this).val())) {
                    valid = false;
                    return valid;
                }
            });
        }
    }
    
    if (valid && $(whichForm).attr("action") == "Sponsor") {
        valid = validSponsor(whichForm);
    }
    
    return valid;
}

function resetFields(whichForm) {
	if (Modernizr.input.placeholder) 
		return;
	
	$(whichForm).find('*').each(function(){
		if($(this).is(':submit')) {
			return true;
		}
		var placeholder = $(this).attr('placeholder');
	    if (typeof placeholder === 'undefined' || placeholder === false) {
	        return true;
	    }
		$(this).focus(function(){
			if($(this).val() == placeholder) {
				$(this).val("");
			}
		});
		$(this).blur(function(){
			if(!$(this).val()) {
				$(this).val(placeholder);
			}
		});
		$(this).blur();
	});
}

function prepareForms() {
    $('form').each(
    function() {
    	//resetFields(this);
    	
        var trigger = false;
        $(':submit', this).click(
        function() {
            trigger = $(this);
        });
        
        $(this).submit(
        function(e) {
            if (!validateForm(this, trigger)) {
                return false;
            }
        });
    });
}

function focusLabels() {
	if(!Modernizr.label) {
		$('label').each(
				function() {
					var TargetId = $(this).attr('for');
					if(TargetId) {
						$(this).click(
								function(){
									$('#TargetId').focus();
								});
					}
				});
	}
}

$(prepareForms);
$(focusLabels);