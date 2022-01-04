// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault(); // form태그 액션을 막아주는 것
	//form태그의 id를 잡아와서 serialize하면 해당 form에 포함된 값이 data에 전부 담기게 됨
	let data = $("#profileUpdate").serialize();
	
	console.log(data);
	
	$.ajax({
		type : "put",
		url : `/api/user/${userId}`,
		data : data,
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		dataType : "json"
	}).done(res=>{ //HttpStatus 코드가 200번대일때
		console.log("성공",res);
		location.href=`/user/${userId}`;
	}).fail(error=>{ //HttpStatus 코드가 200번대가 아닐때
		if(error.data ==null){
			alert(error.responseJSON.message);
		}else{
			alert(JSON.stringify(error.responseJSON.data));
		}
	});
}