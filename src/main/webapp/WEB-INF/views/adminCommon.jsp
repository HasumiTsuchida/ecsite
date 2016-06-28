<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/adminHeader.css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <sec:authentication property="principal.account" var="account" /> --%>

<!-- Bootstrapのために追加 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="../../css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->

<title>ECサイト管理者</title>

	<!-- Bootstrapの値段検索 -->
	<script type="text/javascript">
		function findByPrice(){
			document.searchCinemaPriceForm.submit();
	}
	
	<!-- Bootstrapのジャンル検索 -->
		function findByGenre(){
			document.searchCinemaGenreForm.submit();
		}

	<!-- Bootstrapのメディアタイプ検索	 -->
		function findByMediaType(){
			document.searchCinemaMediaTypeForm.submit();
		}
	</script>

</head>
<body>
<header>

		<div id="linkHeader" align="left">
		<h1 align ="left"><a href="/admin/displayList">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<img src="/img/rogo.png" width="50" height="50" alt="ロゴ画像">ECシネマショップ(管理者用ページ)
		</a></h1>
		</div>

 		<div id="userHeader" align="right">
			<sec:authorize access="hasRole('ROLE_ADMIN') and isAuthenticated()">
	 			<sec:authentication var="admin" property="principal.adminUser.name" />
				<p>こんにちは<c:out value="${admin}"/>さん</p>
				<p><a href="/admin/menu">メニュー画面</a></p>
				<p><a href="/admin/logout">ログアウト</a><p>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<p>こんにちは管理者さん</p>
			</sec:authorize>
		</div>
</header>

<!-- Bootstarapで使うために追加 -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="/js/bootstrap.min.js"></script>

<!-- ダブルサブミット対策 -->	
<script>
		function DisableButton(b){
			b.disabled = true;
			b.form.submit();
		}
</script>