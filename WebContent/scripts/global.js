var const_inputWarn = "InputWarn";

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
	
	if(valid && $(('#' + const_inputWarn), whichForm).length > 0) {
		valid = false;
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

function showWarn(whichInput, message) {
	if($(whichInput).siblings('#' + const_inputWarn).length == 0) {
		$(whichInput).after('<em id=' + const_inputWarn + '>' + message + '</em>');
	}
	else {
		$(whichInput).siblings('#' + const_inputWarn).empty().text(message);
	}	
}

function clearWarn(whichInput) {
	if($(whichInput).siblings('#' + const_inputWarn).length > 0) {
		$(whichInput).siblings('#' + const_inputWarn).remove();
	}
}

function getUsrnameOnServer(usrname, whichInput) {
	warnMessage = " the user name is in used;";
	$.get(
			'http://localhost:8080/CS320Starter/CheckUser?username=' + usrname, 
			function(responseJson) {
				$.each(responseJson, function(key, value) {
					if(key == usrname && value === 'true' ) {
						showWarn(whichInput, warnMessage);
					}
					else {
						clearWarn(whichInput);
					}
				});
			});
}

function validRegister(whichForm,whichInput) {
	var nameAttr = $(whichInput).attr('name');
	var inputVal = $(whichInput).val();
	var valLength = $.trim(inputVal.length);

	var outputStr = "";
	if(nameAttr == 'username') {
		if(valLength < 4) {
			outputStr += " username should >= 4;";
		}
		else {
			getUsrnameOnServer(inputVal,whichInput);
		}
	}
	else if(nameAttr == 'password') {
		if(valLength < 4) {
			outputStr += " password should >= 4;";
		}
		else if(inputVal != $('input[name=retypePassword]',whichForm).val()) {
			outputStr += " the password is not the same as retype input;";
		}
		else {
			clearWarn($('input[name=retypePassword]',whichForm)); //clear the other one
		}		
	}
	else if(nameAttr == 'retypePassword') {
		if(valLength < 4) {
			outputStr += " password should >= 4;";
		}
		else if(inputVal != $('input[name=password]',whichForm).val()) {
			outputStr += " the password is not the same as password input;";
		}
		else {
			clearWarn($('input[name=password]',whichForm)); //clear the other one
		}
	}
	else if(nameAttr == 'firstName') {
		if(valLength <= 0) {
			outputStr += " first name can not be empty;";
		}
	}
	else if(nameAttr == 'lastName') {
		if(valLength <= 0) {
			outputStr += " last name can not be empty;";
		}
	}

	if($.trim(outputStr).length > 0) {
		showWarn(whichInput,outputStr);
	}
	else {
		clearWarn(whichInput);
	}
}

function validSponsor(whichForm, whichInput) {
	var amount = Number($(':input[name=amount]', whichForm).val());
	var reward = Number($(':radio[name=reward]:checked', whichForm).val());
	var target = $('label[for=amount]', whichForm);
	if (reward > amount) {
		if(target.text().length > 0) {
			target.attr('selfbackup',target.text());
			target.empty();
		}
		var outputStr = "Sorry, you must pledge at least $" + reward + " to select that reward.";
		showWarn(target, outputStr);
	}
	else {
		clearWarn(target);
		if(target.attr('selfbackup').length > 0){
			target.text(target.attr('selfbackup'));
		}
	}
}

function prepareBlurInput(whichForm) {

	if($(whichForm).attr("action") == "Register") {
		$(':input', whichForm).not('submit').not('button').each(function() {
			$(this).blur(function() {
				validRegister(whichForm,this);
			});
		});
	}
	else if($(whichForm).attr("action") == "Sponsor") {
		$(':radio', whichForm).each(function() {
			$(this).change(function() {
				validSponsor(whichForm,this);
			});
		});
	}
}

function prepareForms() {
	$('form').each(
			function() {
				prepareBlurInput(this);

				resetFields(this);

				var trigger = false;
				$(':submit', this).click(
						function() {
							trigger = $(this);
						});

				$(this).submit(
						function() {
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