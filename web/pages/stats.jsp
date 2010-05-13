<?xml version="1.0" encoding="UTF-8" ?>
<jsp:root xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fmt="http://java.sun.com/jsp/jstl/fmt" version="2.0">
	<jsp:directive.page language="java"
		contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" />
	<jsp:text>
		<![CDATA[ <?xml version="1.0" encoding="UTF-8" ?> ]]>
	</jsp:text>
	<jsp:text>
		<![CDATA[ <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> ]]>
	</jsp:text>
	<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>${title}</title>
	<link type="text/css" rel="stylesheet" href="style.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>

	<body class="twoColLiqLtHdr">

	<div id="container" class="container">

	<div id="header">
	<h1>
    <a class="logo" href="Main.html">
    <img src="logo_speerker.png" width="334" height="94"
        alt="logo" border="0"/></a></h1>
	</div>

	<div id="sidebar1">
	<h3><a href="Formulario.html">Crear Cuenta</a></h3>
	<h3><a href="stats">Estadísticas</a></h3>
	</div>

	<div id="mainContent">
	<h2>${title}</h2>

	<!--  List errors if any --> <c:if test="${not empty errors}">
		<span>Errors</span>
		<ul>
			<c:forEach var="error" items="${errors}">
				<li class="error">${error}</li>
			</c:forEach>
		</ul>
	</c:if>

	<h3>${playCountText} ${playCount}</h3>

	<!-- List plays if any -->
	<h3>${lastPlayText}</h3>
	<c:if test="${not empty plays}">
		<table class="plays">
			<thead>
				<tr>
					<th>Title</th>
					<th>Artist</th>
					<th>Time</th>
					<th>Date</th>
				</tr>
			</thead>
			<c:forEach var="play" items="${plays}">

				<tr>
					<td class="title">${play.song.title}</td>
					<td class="artist">${play.song.artist}</td>
					<td class="date"><fmt:formatDate type="time"
						value="${play.timestamp}" pattern="HH:mm"></fmt:formatDate></td>
					<td class="date"><fmt:formatDate type="date"
						value="${play.timestamp}" pattern="dd/MM/yy"></fmt:formatDate></td>
				</tr>

			</c:forEach>
		</table>
	</c:if></div>
	<br class="clearfloat" />

	<div id="footer">
	<p></p>
	</div>
	</div>
	</body>
	</html>
</jsp:root>
