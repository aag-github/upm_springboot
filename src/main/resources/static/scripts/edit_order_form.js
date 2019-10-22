function addItem(event)
{
	var items = $("input[name^='item-']")
	var newInputNode = document.createElement("input");	
	newInputNode.setAttribute("type", "text");	
	newInputNode.setAttribute("name", "item-" + (items.length + 1));

    var newLi = document.createElement("li");	
	newLi.className = "list-group-item list-group-item-info";
	newLi.appendChild(newInputNode);
	newLi.innerHTML += '<label class="btn" onClick="deleteItem(this);"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span></label>';
	
	var itemList = document.getElementById("items");	
	itemList.appendChild(newLi);
	
	$("input").on("keyup", inputBoxKeyUpHandler);
	configSaveButton();
	configDeleteItemButtons();
	
	newLi.childNodes[0].focus();
	//newInputNode.focus();
}

function hideItem(element)
{
	var deleteInputBox = document.getElementById(element.htmlFor);
	deleteInputBox.value = "true";
	element.parentNode.classList.add("hidden");
	element.classList.add("hidden");
	configDeleteItemButtons();
}

function deleteItem(element)
{
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
	var deleteButtons = $("label .glyphicon-trash");
	var buttonContainers = $("label .glyphicon-trash").parent().not(".hidden");	
	if (buttonContainers.length == 1) {
		deleteButtons.addClass("hidden");
	} else {
		deleteButtons.removeClass("hidden");
	}
}

function inputBoxKeyUpHandler() {
	configSaveButton();
}