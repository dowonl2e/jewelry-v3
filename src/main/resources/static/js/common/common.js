function checkNullVal(val){
	if(typeof val == "undefined" || val == null){
		return '';
	}
	else {
		return val+'';
	}
}

function checkNullValR(val, replaceStr){
	if(typeof val == "undefined" || val == null){
		return replaceStr;
	}
	else {
		return val+'';
	}
}

function checkSubstringNullVal(val, startidx, endidx){
	if(typeof val == "undefined" || val == null){
		return '';
	}
	else {
		if(val.length < endidx){
			return val;
		}
		else {
			return val.substring(startidx, endidx);			
		}
	}
}

function addZeroFront(val, len){
	var addzeronum = checkNullVal(val);
	if(typeof val == "undefined" || val == null){
		return addzeronum;
	}
	else {
    while (addzeronum.length < len) {
        addzeronum = '0' + addzeronum;
    }
	}
	return addzeronum;
}

//데이터 없으면 삭제
function checkListNullParams(jsonObj){
	for(key in jsonObj) {
		if(jsonObj[key] == '')
			delete jsonObj[key];
	}
}

function priceWithComma(val){
	try {
		if(typeof val == "undefined" || val == null){
			return '0';
		}
		return (Math.round(val).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
	}
	catch(e){
		console.log(e);
		return '0';
	}
}

//새로고침
function fncRefresh(){
	$("#adv-search").find("input").val('');
	$("#adv-search").find("select").val('');
	findAll(0);
}

//숫자 체크(1 ~ 100){
function fncCheckZero(obj){
	if($(obj).val() != ''){
		if(Number($(obj).val()) < minNumberLen){
			$(obj).val('1');
		}
		if(Number($(obj).val()) > maxNumberLen){
			$(obj).val('100');
		}
	}
}

function goSigninPage(){
	location.replace('/signin');
}

function goMainPage(){
	location.replace('/main');
}

function goPage(url){
	location.href = url;
}

function setSessionStorage(name, value){
	sessionStorage.setItem(name, value);
}

function getSessionStorage(name){
	return 'Bearer ' + sessionStorage.getItem(name);
}

function setLocalStorage(name, value){
	localStorage.setItem(name, value);
}

function getLocalStorage(name){
	return localStorage.getItem(name);
}

function initStorageValue(){
  localStorage.removeItem('access_token');
  localStorage.removeItem('access_token_expiores_in');
  goSigninPage();
}

function setReissueToken(header){
  const token = header.get('Authorization');
  if(token == null || token == ''){
    initStorageValue();
  }
  setLocalStorage("access_token", token);
  setLocalStorage("access_token_expiores_in", header.get('ExpioresIn'));
  console.log('토큰 재발급 완료');
}

function getToken(){
  return 'Bearer ' + getLocalStorage('access_token');
}

/*
 * 토큰 만료시간이 현시간보다 30초 밑으로 차이나면 토큰 갱신 필요
 */
function validateTokenExpioresIn(){
  const accessTokenExpioresIn = getLocalStorage('access_token_expiores_in');
  if(accessTokenExpioresIn == '' || accessTokenExpioresIn == 0 || accessTokenExpioresIn == 'undefined'){
    return false;
  }
  var remainExpioresIn = (accessTokenExpioresIn - new Date().getTime())/1000;
  if(remainExpioresIn <= 30){
    return false;
  }
  return true;
}

async function isValidToken(){
  if(validateTokenExpioresIn()){
    return true;
  }
  else {
    const response = await fetch('/api/jauth/reissue', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': getToken()
      }
    });

    if (!response.ok) {
      alert('로그인 후 이용해주세요.');
      initStorageValue();
      return false;
    }
    else {
      setReissueToken(response.headers);
      return true;
    }
  }
}

async function getJson(uri, params, isReissued) {
  if(isReissued != 'undefined' && isReissued){
    return getJsonOnly(uri, params);
  }
  else {
    if(await isValidToken()){
      return getJsonOnly(uri, params);
    }
  }
}

/**
 * 목록 API 호출
 */
async function getJsonOnly(uri, params){
  if (params) {
    uri = uri + '?' + new URLSearchParams(params).toString();
  }

  const response = await fetch(uri, {
    method: 'GET',
    headers: {
      'Authorization': getToken()
    }
  });

  if (!response.ok) {
    await response.json().then(error => {
      throw error;
    });
  }

  return await response.json();
}

function setQueryStringParams() {

    if ( !location.search ) {
        return false;
    }

    const form = document.getElementById('searchForm');

    new URLSearchParams(location.search).forEach((value, key) => {
        if (form[key]) {
            form[key].value = value;
        }
    });
}

//부모창 새로고침
function fncParentRefresh(){
  window.opener.refresh();
}

//부모창 새로고침
function goParentSigninPage(){
  window.opener.location.href = '/signin';
}

//팝업 닫기
function fncClose(){
  self.close();
}

function removeAuthButton(id){
  document.getElementById(id).innerHTML = '';
}

function altNoAuth(){
  alert('권한이 없습니다.');
}