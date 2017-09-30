
function selectedWords(p){
	var words = getSelectedValues("selectedWordsChk");
	if(words.length > 0) {
		$.ajax({
			url : "selectedWords.html",
			type : "post",
			data : {
				words : words,
				_csrf : $("#csrf").val()
			},
			success : function(data) {
				var msg = JSON.parse(data);
				if(msg.success){
					location.href = "keyWordsManage.html?p=" + p;
				} else {
					promiseCommonModal("操作失败");
				}
			}
		});
	}
}

function searchWords(){
	var word = $("#searchedWords").val();
	if(word != ""){
		location.href = "keyWordsManage.html?word=" + word;
	}
}

function deleteWord(id, word, p) {
	$.ajax({
		url : "deleteWord.html",
		type : "post",
		data : {
			id : id,
			word : word,
			_csrf : $("#csrf").val()
		},
		success : function(data) {
			var msg = JSON.parse(data);
			if(msg.success){
				location.href = "annotateWords.html?p=" + p;
			} else {
				promiseCommonModal("删除失败");
			}
		}
	});
}

function synchronousWords() {
	$.ajax({
		url : "synchronousWords.html",
		type : "post",
		data : {
			_csrf : $("#csrf").val()
		},
		success : function(data) {
//			var msg = JSON.parse(data);
//			if(msg.success){
//				alert("正在执行...");
//			}
		}
	});
}

