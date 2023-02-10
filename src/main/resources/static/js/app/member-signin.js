// 아이디의 유효성 여부를 저장할 변수를 만들고 초기값 false 부여
let isIdValid=false;
// 패스워드의 유효성 여부를 저장할 변수를 만들고 초기값 false 부여
let isPassValid=false;
// 패스워드 확인의 유효성 여부를 저장할 변수를 만들고 초기값 false 부여
let isPassConfirmValid=false;

// id 가 id 인 input 요소에 input 이벤트가 일어났을때 실행할 함수 등록
$("#id").on("input", function(){
    // 1. 입력한 value 값을 읽어온다.
    let inputId=this.value;
    // 2. 유효성(4글자이상 50글자 이하)을 검증한다.
    isIdValid = inputId.length >= 4 && inputId.length <= 50;
    // 3. 표시할 메세지 설정
    $("#invalidId").text("4글자 이상 50글자 이하로 작성해 주세요.");
    // 4. 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
    if(isIdValid){
        this.classList.remove("is-invalid");
        this.classList.add("is-valid");
    }else{
        this.classList.remove("is-valid");
        this.classList.add("is-invalid");
    }

    // 등록버튼 상태 갱신
    saveBtnControl();
});

// id 가 pass 인 input 요소에 input 이벤트가 일어났을때 실행할 함수 등록
$("#pass").on("input", function(){
    // 영문 대문자 + 소문자 + 숫자 조합
    var reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$/;

    // 1. 입력한 value 값을 읽어온다.
    let inputPass=this.value;
    // 2. 정규표현식(1자리의 영문 대문자 + 영문 소문자 + 숫자 조합)을 통해 유효성을 검사
    isPassValid = reg.test(inputPass);
    // 3. 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
    if(isPassValid){
        this.classList.remove("is-invalid");
        this.classList.add("is-valid");
    }else{
        this.classList.remove("is-valid");
        this.classList.add("is-invalid");
    }
    // 패스워드를 변경하면 패스워드 확인에 입력한 값과 차이가 발생하기 때문에
    // 패스워드 확인의 유효성 검사를 실시
    passConfirmCheck();

    // 등록버튼 상태 갱신
    saveBtnControl();
});

// id 가 passConfirm 인 input 요소에 input 이벤트가 일어났을때 실행할 함수 등록
$("#passConfirm").on("input", function(){
    // 패스워드 확인의 유효성 검사를 실시
    passConfirmCheck();
});

// 패스워드 확인의 유효성 검사
function passConfirmCheck() {

    let passConfirm = $("#passConfirm");

    // 1. 입력한 value 값을 읽어온다.
    let inputPassConfirm=passConfirm.val();
    // 2. 유효성(패스워드의 유효성이 true이며 패스워드와 패스워드 확인의 입력값이 일치)을 검증한다.
    isPassConfirmValid = isPassValid == true && $("#pass").val() == inputPassConfirm;
    // 3. 유효하다면 input 요소에 is-valid 클래스 추가, 아니라면 is-invalid 클래스 추가
    if(isPassConfirmValid){
        passConfirm.removeClass("is-invalid");
        passConfirm.addClass("is-valid");
    }else{
        passConfirm.removeClass("is-valid");
        passConfirm.addClass("is-invalid");
    }

    // 등록버튼 상태 갱신
    saveBtnControl();
}

// 등록버튼 활성/비활성 제어
function saveBtnControl() {
    // 각 입력항목들의 유효검사 결과를 검사
    if (isIdValid && isPassValid && isPassConfirmValid) {
        // 등록 버튼 활성화
        $("#btn-save").attr("disabled", false);
    } else {
        // 등록 버튼 비활성화
        $("#btn-save").attr("disabled", true);
    }
}
