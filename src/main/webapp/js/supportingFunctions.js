/**
 * On submit of the search button form sends a call to the server
 */
$('#searchInv').on('submit',function(e){
	e.preventDefault();
	var url = window.location.origin+'/Database/api/item/search';
	console.log(url);
	var content = getItemFormContent('searchItem');
	$.ajax({
		url: url,
		type: 'POST',
		dataType: 'json',
		data: JSON.stringify(content),
		contentType: "application/json; charset=utf-8",
		success:function(data){
			var jsonString = JSON.stringify(data);
			var parsedData =JSON.parse(jsonString);
			var i;
			var arrayLength = parsedData.length;
			document.getElementById('tablebody').innerHTML = "";
			for(i = 0; i < arrayLength; i++){
				var row = setItemRow(parsedData[i]);
				$('#tablebody').append(row);
			}
				
		},
		error:function(){
			window.alert("Failed To get data");
		}
	});
});

$('#addNewItemForm').on('submit',function(e){
	e.preventDefault();
	var url = window.location.origin+'/Database/api/item';
	var content = getItemFormContent('newItem');
	delete content['IdTag'];
	if(confirm("Confirm Submition of item: \n"+ JSON.stringify(content))){
		$.ajax({
		url: url,
		type: 'POST',
		data: JSON.stringify(content),
		contentType: "application/json; charset=utf-8",
		success:function(data){
			window.alert("Item succesfuly submited to database.")
		},
		error:function(){
			window.alert("Failed To get data");
		}
	});
	}
});

$('#deleteItemForm').on('submit',function(e){
	e.preventDefault();

	var content = $('#deleteItemID').val();
	var url = window.location.origin+'/Database/api/item?id='+content;
	if(confirm("Confirm Deletion of item: \n"+ content)){
		$.ajax({
		url: url,
		type: 'DELETE',
		success:function(){
			window.alert("Item succesfuly deleted from database.")
		},
		error:function(){
			window.alert("Failed to delete data");
		}
	});
	}
});

function onchangeDeleteId(e){
	var url = window.location.origin+'/Database/api/item';
		$.ajax({
		url: url,
		type: 'GET',
		dataType: 'json',
		data: {id:e},
		success:function(data){
			setItemFormContent(data,'deleteItem');
		},
		error:function(){
			window.alert("Failed to get data");
		}
	});
}

function getAllItems(){
	var url = window.location.origin+'/Database/api/item/all';
	$.ajax({
		url: url,
		type: 'GET',
		dataType: 'json',
		success:function(data){
			var jsonString = JSON.stringify(data);
			var parsedData =JSON.parse(jsonString);
			var i;
			var arrayLength = parsedData.length;
			document.getElementById('tablebody').innerHTML = "";
			for(i = 0; i < arrayLength; i++){
				var row = setItemRow(parsedData[i]);
				$('#tablebody').append(row);
			}
				
		},
		error:function(){
			window.alert("Failed To get data");
		}
	});
}
// Get all the elements from a form ex. newItemID
function getItemFormContent(textTag){
	var json = JSON.parse('{"IdTag":'+ getFormValue(textTag+'ID')
			+ ', "BinNum":' +getFormValue(textTag+'Bin')
			+ ', "Length":' +getFormValue(textTag+'Length')
			+ ', "Department":' +getFormValue(textTag+'Department')
			+ ', "Description":' +getFormValue(textTag+'Description')
			+ ', "ReplacementCost":' +getFormValue(textTag+'ReplacementCost')
			+ ', "Price":' +getFormValue(textTag+'Price')
			+ ', "Weight":' +getFormValue(textTag+'Weight')
			+ ', "Dimensions":' +getFormValue(textTag+'Dimensions')
			+ ', "ModelNumber":' +getFormValue(textTag+'ModelNumber')
			+ ', "WarrantyEndDate":' +getFormValue(textTag+'WarrantyEndDate')
			+ ', "PurchaseDate":' +getFormValue(textTag+'PurchaseDate')
			+ ', "Category":' +getFormValue(textTag+'Category')
			+'}');
	return json;
	
}

function setItemFormContent(jsonItem, textTag){
	document.getElementById(textTag+'Bin').textContent= jsonItem.BinNum;
	document.getElementById(textTag+'Length').textContent= jsonItem.Length;
	document.getElementById(textTag+'Department').textContent= jsonItem.Department;
	document.getElementById(textTag+'Description').textContent= jsonItem.Description;
	document.getElementById(textTag+'ReplacementCost').textContent= jsonItem.ReplacementCost;
	document.getElementById(textTag+'Price').textContent= jsonItem.Price;
	document.getElementById(textTag+'Weight').textContent= jsonItem.Weight;
	document.getElementById(textTag+'Dimension').textContent= jsonItem.Dimensions;
	document.getElementById(textTag+'ModelNumber').textContent= jsonItem.ModelNumber;
	document.getElementById(textTag+'WarrantyEndDate').textContent= jsonItem.WarrantyEndDate;
	document.getElementById(textTag+'Purchase').textContent= jsonItem.PurchaseDate;
	document.getElementById(textTag+'Category').textContent= jsonItem.Category;	
}


function getFormValue(id){
	return '"'+ $('#'+id).val()+'"';
}
function setItemRow(itemInfo){
	var row = 	'<tr><td>'+itemInfo.IdTag 
	+'</td><td>'+ itemInfo.BinNum
	+'</td><td>'+ itemInfo.Department
	+'</td><td>'+ itemInfo.ModelNumber 
	+'</td><td>'+ itemInfo.Description
	+'</td><td>'+ itemInfo.Price
	+'</td><td>'+ itemInfo.ReplacementCost
	+'</td><td>'+ itemInfo.PurchaseDate
	+'</td><td>'+ itemInfo.WarrantyEndDate 
	+'</td><td>'+ itemInfo.Weight
	+'</td><td>'+ itemInfo.Dimensions
	+'</td><td>'+ itemInfo.Length
	+'</td><td>'+ itemInfo.Category
	+'</td></tr>';
	return row;
}
function addOptionForBinSelect(BinElementID){
	var url = window.location.origin+'/Database/api/bins/getAllBinNum';
	$.ajax({
		url: url,
		type: 'GET',
		dataType: 'json',
		async: false,
		success:function(data){
			var jsonString = JSON.stringify(data);
			var parsedData =JSON.parse(jsonString);
			var i;
			var arrayLength = parsedData.length;
			document.getElementById(BinElementID).innerHTML = "";
			if(BinElementID !== "newItemBin")
				$('#'+BinElementID).append('<option value=""></option>');
			for(i = 0; i < arrayLength; i++){
				var option = setBinOption(parsedData[i]);
				$('#'+BinElementID).append(option);
			}
		},
		error:function(){
			window.alert("Failed To get data");
		}
	});
}
function setBinOption(BinNum){
	var option ='<option value="'+ BinNum+'">'+BinNum+'</option>';
	return option;
}

