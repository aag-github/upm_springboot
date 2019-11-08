var newUrl;
var modalConfirm = function(class_name){
  $(class_name).on("click", function(){
    newUrl = document.activeElement.value;
    $("#mi-modal").modal('show');
  });

  $("#modal-btn-si").on("click", function(){
    $("#mi-modal").modal('hide');
    window.location = newUrl;
    newUrl = "";
  });
  
  $("#modal-btn-no").on("click", function(){
    $("#mi-modal").modal('hide');
    newUrl = "";
  });
};

function setDialogClassName(name) {
	return name;
}

function setupDeleteOrderConfirmDialog() {
	modalConfirm(setDialogClassName(".delete_order_confirm_dialog"));
}