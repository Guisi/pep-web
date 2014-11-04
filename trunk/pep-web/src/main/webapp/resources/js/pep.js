function phoneMaskHandler(target) {
	var phone, element;
	phone = target.value.replace(/\D/g, '');
	element = $(target);
	element.unmask();
	if (phone.length > 10) {
		element.mask("(99) 99999-999?9");
	} else if (phone.length == 0 || phone.length == 10) {
		element.mask("(99) 9999-9999?9");
	}
}

/*
 * Verifica se pode fechar o dialog modal 
 */
function handleModalDialogSubmit(dialogObject, status, args) {
    if (!args.validationFailed && !args.error) {
        dialogObject.hide();
    }
}