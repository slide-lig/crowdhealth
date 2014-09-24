<html>
	<head>
		<title>read file and proceed</title>
	</head>
	<body>
	<h1>Insert tsv File as Input Data</h1>
	TSV File  :
    <form action="processtsv.php" method="POST" enctype="multipart/form-data">
	<input type="file" name="filetsv" id="filetsv"/>
	<br>
	<input type="submit" name="submit" value="Convert TSV to JSON"/>
    </form>
</body>
</html>