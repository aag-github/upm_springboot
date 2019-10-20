function addItemBox(event)
{
	var items = $("input[name^='item-']")
	var newInputNode = document.createElement("input");	
	newInputNode.setAttribute("type", "text");	
	newInputNode.setAttribute("name", "item-" + (items.length + 1));

    var newLi = document.createElement("li");	
	newLi.className = "list-item";
	newLi.appendChild(newInputNode);
	
	var itemList = document.getElementById("items");	
	itemList.appendChild(newLi);
	
	$("input").on("keyup", enableSaveButton);
	enableSaveButton();
	
	newInputNode.focus();	
}

function enableSaveButton()
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
