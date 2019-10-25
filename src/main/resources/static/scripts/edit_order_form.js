function addItem()
{
	var newInputNode = document.createElement("input");	
	newInputNode.setAttribute("type", "text");	
	newInputNode.setAttribute("name", "newitem");

    var newLi = document.createElement("li");	
	newLi.className = "list-group-item list-group-item-info";
	newLi.appendChild(newInputNode);
	newLi.innerHTML += '<label class="btn delete-node-to-hide" onClick="deleteItem(this);"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></label>';
	
	var itemList = document.getElementById("items");	
	itemList.appendChild(newLi);
	
	$("input").on("keyup", inputBoxKeyUpHandler);
	configSaveButton();
	configDeleteItemButtons();
	
	newLi.childNodes[0].focus();
	//newInputNode.focus();
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
	$("input").each(function(){
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
		if (deleteButtons.length == 1) {
			deleteButtons.addClass("hidden");
		} else {
			deleteButtons.removeClass("hidden");
		}
	}
}

function inputBoxKeyUpHandler() {
	configSaveButton();
}