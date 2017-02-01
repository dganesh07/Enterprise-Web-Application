<!doctype html>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>BestDeals</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<div id="container">
    <header>
    	<h1><a href="http://localhost/csj/index.html"><span>BestDeals</span></a></h1>
        <h2>Best Online Retail Store</h2>
		<a href="index.jsp" style='font-weight: bold;float:right;margin-right:20px;'>Home</a>
    </header>
    <h1>Please give the product details to be added</h1>

    <form method='Post' action="addProducts.jsp">
    <input type="text" name="productId" placeholder="productId"><br/>
    <input type="text" name="productName" placeholder="productName"><br/>
        <input type="text" name="image" placeholder="image"><br/>
<input type="text" name="retailer" placeholder="retailer"><br/>
    <input type="text" name="condition" placeholder="condition"><br/>
    <input type="text" name="type" placeholder="type"><br/>
    <input type="text" name="price" placeholder="price" required><br/><br/>

    <input type="submit" value="Add">
    </form>
    </div>
   </body>
   </html>