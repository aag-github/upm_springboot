function addItem()
{
	var itemToClone = $(".new-item-to-clone");
	var clone = itemToClone.clone(true);
	clone.find("input").removeClass("hidden");
	
	var itemList = document.getElementById("items");	
	clone = itemList.appendChild(clone[0]);
	clone.classList.remove("new-item-to-clone", "hidden");
	
	$("input").on("keyup", inputBoxKeyUpHandler);	
	configSaveButton();
	configDeleteItemButtons();
    $(clone).find("input").focus();	
}

function deleteItem(element)
{
	var deleteInputBox = document.getElementById(element.htmlFor);
	if (deleteInputBox) {
		deleteInputBox.value = "true";
	}
	element.parentNode.parentNode.removeChild(element.parentNode);
	configSaveButton();
	configDeleteItemButtons();
}

function configSaveButton()
{
	var saveButton = document.getElementById("saveButton");

	var anyEmpty = false;	
	$("input").not(".hidden").each(function(){
		if (!($(this).val().trim())) {
			anyEmpty = true;
			return false;
		}
	})
	saveButton.disabled = anyEmpty;	
}

function configDeleteItemButtons()
{
	var deleteButtons = $(".delete-node-to-hide");
	if (deleteButtons.length >  0) {
		// There is one button hidden to clone, hence length == 2
		if (deleteButtons.length == 2) {
			deleteButtons.addClass("hidden");
		} else {
			deleteButtons.removeClass("hidden");
		}
	}
}

function inputBoxKeyUpHandler() {
	configSaveButton();
}