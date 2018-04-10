$(document).ready(function(){
	
	$("tr").on("click", function() {
		if ($(this).hasClass("selected")){
			$(this).removeAttr("class");
		} else {
			$("tr").removeAttr("class");
			$(this).attr("class","selected");
		}
	});
	
});

function remove() {
	$.ajax({
		url: "investmentRule/delete",
		data: "investment-rule-id=" + $(".selected").children().children()[0].innerHTML
	});
	location.reload();
}

function loadForm() {
	$("#investment-rule-id").val($(".selected").children().children()[0].innerHTML);
	$("#investment-rule-account").val($(".selected").children().children()[5].innerHTML);
	$("#investment-rule-company").val($(".selected").children().children()[6].innerHTML);
	$("#investment-rule-purchase-price").val($(".selected").children().children()[3].innerHTML);
	$("#investment-rule-sale-price").val($(".selected").children().children()[4].innerHTML);
}

function emptyForm() {
	$("#investment-rule-id").val("");
	$("#investment-rule-account").val(1);
	$("#investment-rule-company").val(2);
	$("#investment-rule-purchase-price").val("");
	$("#investment-rule-sale-price").val("");
}