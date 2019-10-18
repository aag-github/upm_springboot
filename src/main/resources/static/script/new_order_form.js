function addItemBox(event)
{
	var items = $("input[name^='item-']")
	var itemList = document.getElementById("items");
	var newLi = document.createElement("li"); 
	var newInputNode = document.createElement("input");
	
	newInputNode.setAttribute("type", "text");	
	newInputNode.setAttribute("name", "item-" + (items.length + 1));
		
	newLi.className = "list-item";
	newLi.appendChild(newInputNode);	
	
	itemList.appendChild(newLi);
	
	$("input").on("keyup", enableSaveButton);
	enableSaveButton();
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
