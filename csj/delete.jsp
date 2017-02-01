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
    <h1>enter the Productid to delete a product</h1>

    <form method='Post' action="deleteProduct.jsp">
    <input type="text" name="productId" placeholder="productId"><br/>
  
    <input type="submit" value="Enter">
    </form>
    </div>
   </body>
   </html>