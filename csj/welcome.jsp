<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<title>CSP 595</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="script.js"></script>
</head>

<body onload="init()">

<div id="container">
    <header>
    	<h1><a href="/">Best<span>Deals</span></a></h1>
        <h2>Online Store!!!!</h2>
    </header>
    <nav>
    	<ul>
        	<li class="start selected"><a href="index.html">Home</a></li>
            <li class=""><a href="SmartPhones?category=SmartPhone">SmartPhones</a></li>
            <li class=""><a href="SmartPhones?category=Tablets">Tablets</a></li>
            <li class=""><a href="SmartPhones?category=Laptops">Laptop</a></li>
			<li class="end"><a href="SmartPhones?category=TV">TV</a></li>
			<li class=""><a href='testtrend'>Trending</a></li>
			<li class=""><a href='DataAnalytics.html'>DataAnalytics</a></li>
            <li class="order"><a href='index.html'>LogIN</a></li>
			
		</ul>
    </nav>

	<img class="header-image" src="main.jpg" width = "100%" height = "100%" alt="Index Page Image" />
	

    <div id="body">		

	<section id="content">

	    <article>
			
			<h2>Welcome to Best Deals Store</h2>
			
            <p>Get the best of the rest </p>	
            
            <p>Main manufacturer being samsung , LG , Apple and many more!!</p>
			
		</article>
































	
		
    </section>
        
    <aside class="sidebar">
	
            <ul>	
               <li>
                    <h4>Products</h4>
                    <ul>
                     <li class=""><a href="SmartPhones?category=SmartPhone">SmartPhones</a></li>
            <li class=""><a href="SmartPhones?category=Tablets">Tablets</a></li>
            <li class=""><a href="SmartPhones?category=Laptops">Laptop</a></li>
			<li class="end"><a href="SmartPhones?category=TV">TV</a></li>
                    
                    </ul>
                </li>
                
                <li>
                    <h4>About us</h4>
                    <ul>
                        <li class="text">
                        	<p style="margin: 0;">This is an Online Store.</p>
                        </li>
                    </ul>
                </li>
             
            <li>
                	<h4>Search site</h4>
                    <ul>    
                    <form name="autofillform" action="AutoCompleteServlet">
            		<div name="autofillform">
            			<p>
            			<input type="text" name="searchid" value="" class="input" id="complete-field" onkeyup="doCompletion()" placeholder="search..." style ="padding:6px;font-size: 16px;margin: 5px 0;"/>
            			</p>
            			<div id ="auto-row">
            				<table id="complete-table" class="gridtable" style="position: absolute;width:315px;">
            				</table>
            			</div>
            		</div>
                    </form>
          
            </ul>
            </li>


                <li>
                    <h4>Helpful Links</h4>
                    <ul>
                        <li><a href="https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=good%20laptop%20configuration%202016" title="premium templates">What you buy</a></li>
                        <li><a href="https://www.google.com/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=good%20laptop%20configuration%202016">What you buy</a></li>
                        
                    </ul>
                </li>
                
            </ul>
		
    </aside>
    
	<div class="clear"></div>
	</div>
    
	<footer>
	
        <div class="footer-content">
            <ul>
            	<li><h4>Dummy Link Section 1</h4></li>
                <li><a href="#">Dummy 1</a></li>
                <li><a href="#">Dummy 2</a></li>
                <li><a href="#">Dummy 3</a></li>
			</ul>
           
        <div class="clear"></div>
        </div>
		
        <div class="footer-bottom">
            <p>CSP 595 - Enterprise Web Application</p>
        </div>
		
    </footer>
</div>

</body>

</html>