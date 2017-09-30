
function searchDoc(tagState, type, country) {
	var kw = $("#kw").val();
	docSearchPage(tagState, type, country, kw, 1);
}

function docSearchPage(tagState, type, country, kw, p){
	if(country != ""){
		country = encodeURIComponent(encodeURIComponent(country));
	}
	if(kw != ""){
		kw = encodeURIComponent(encodeURIComponent(kw));
	}
	location.href = "docSearch.html?tagState="+ tagState + "&type=" + type + "&country=" + country + "&kw=" + kw + "&p=" + p;
}

function factorSearch(type, country, kw, p){
	if(kw != ""){
		kw = encodeURIComponent(encodeURIComponent(kw));
	}
	if(country != ""){
		country = encodeURIComponent(encodeURIComponent(country));
	}
	location.href = "factorSearch.html?type=" + type + "&country=" + country + "&kw=" + kw + "&p=" + p;
}

function factorSearch2(type , country){
	var kw = $("#kw").val();
	factorSearch(type, country, kw, 1);
}