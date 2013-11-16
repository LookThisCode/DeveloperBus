<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Yes worK- Home</title>
    <link rel="stylesheet" type="text/css" href="stylesheets/reset.css" />
    <link rel="stylesheet" type="text/css" href="stylesheets/main.css" />
    <!-- jQuery library (served from Google) -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
<!-- bxSlider Javascript file -->
<script src="/js/jquery.bxslider.min.js"></script>
<!-- bxSlider CSS file -->
<script>
$(document).ready(function(){
  $('.bxslider').bxSlider();
});

</script>
<link href="/lib/jquery.bxslider.css" rel="stylesheet" />


</head>
<body>
    
    <div id="header">
        
        <div class="container">
        
        <h1><a href="index.html">yes work</a></h1>

        
       <!-- <div id="main_menu">
        
            <ul>
                <li class="first_list"><a href="index.html" class="main_menu_first main_current">home</a></li>
                <li class="first_list"><a href="#" class="main_menu_first">Nosotros</a></li>
                <li class="first_list with_dropdown">
                    <a href="#"  class="main_menu_first">services</a>
                    <ul>
                        <li class="second_list second_list_border"><a href="#" class="main_menu_second">Soporte tecnico</a></li>
                       
                        
                    </ul>
                </li>
                
                
                <li class="first_list"><a href="contact.html" class="main_menu_first">contacto</a></li>
            </ul>
            
         
            
            
        
        </div> <!-- END #main_menu -->
        <div   id="login">
          <div  id="login" >
          <form>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user != null) {
%>
<p>Bienvenido, <%= user.getNickname() %>! (You can
<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
<button  >
 <b>Ver informacionn</b>

</button>
<%
    } else {
%>
<p>Hola!
<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
to include your name with greetings you post.</p>


<%
    }
%>
          
          </form>
          
          </div> 
        </div>
    
        </div> <!-- END .container -->
    
    </div> <!-- END #header -->
    
    <div id="main_content">
            
        <div id="slideshow_area">
        
        <div class="container">
            
            <div id="slideshow_container">
                
                <ul>
                    <li><img src="images/banner_image.jpg" alt="banner1" /></li>
                   <!--  <li><img src="images/banner_image2.jpg" alt="banner1" /></li>-->
                </ul>
                
                <div id="slideshow_pagination">
                    
                    <ul>
                        <li><a href="#"></a></li>
                        <li><a href="#"></a></li>
                        <li><a href="#" class="current"></a></li>
                        <li><a href="#"></a></li>
                        <li><a href="#"></a></li>
                    </ul>
                    
                </div> <!-- END #slideshow_pagination -->
            
            </div> <!-- END #slideshow_container -->
            
        </div> <!-- END .container -->
    
        </div> <!-- END #slideshow_area -->
            
        <div id="mid_content">
            
            <div class="container">
                
            <div class="mid_content_info mid_content_space">
                    
                <h2 id="clean">YesWork</h2>
                <p>YesWork es una herramienta para aprovechar las ventajas que tiene el teletrabajo, con el fin que se pueda desarrollar empresas donde la relaciÃ³n entre la empresa y sus colaboradores se fundamente en la confianza, la responsabilidad y el bienestar.</p>
                
                    
            </div>
                
            <div class="mid_content_info mid_content_space">

                <h2 id="responsive">Beneficios</h2>
                <p>Tus colaboradores serÃ¡n mÃ¡s productivos puesto que el tener mayor calidad de vida hace que trabajen motivados.Los rendimientos de la empresa se ven influenciados de forma positiva ya que hay una ahorro de Activos fijos, Instaciones y Servicios en un rango que se encuentra dentro del 20% al 25%. Ademas tienes beneficios del Gobierno por la generacion de empleos y el fomento al Teletrabajo.</p>
              
                    
            </div>
                
            <div class="mid_content_info mid_content_space">
                    
                <h2 id="fully">Como funciona</h2>
                <p>Phasellus lobortis metus non augue sodales volutpat. Vestibulum sit amet nibh eros, hendrerit venenatis est. In vitae nulla nec purus cursus
                pretium sed id magna.</p>
               
                    
            </div>
                
            <div class="mid_content_info">
                    
                <h2 id="ready">Hacia donde vamos</h2>
                <p>Queremos ser la plataforma lider para el uso del teletrabajo, con el fin de contribuir al crecimiento de las empresas y la economia, asi como al bienestar de las personas que prestan sus servicios personales</p>
                <a href="#"><img src="images/arrow_right.png" alt="arrow right" />Read More</a>
                    
            </div>
            
            </div> <!-- END .container -->
                
        </div> <!-- END #mid_content --><!-- END #latest_works -->
    
        <!--<div id="bottom_content">
    
            <div id="testimonials">
        
                <h3>TESTIMONIALS</h3>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ut nulla sapien, at aliquam erat. Sed vitae massa tellus. Aliquam commodo
                aliquam metus, sed iaculis nibh tempus id. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum ante ipsum primis in faucibus orci
                luctus et ultrices posuere cubilia Curae; Etiam nec nisi in nisl euismod fringilla.
                <br />
                <br />
                <span class="testimonial_name">John Travis, CEO, DomainName.com</span></p>
        
            </div> <!-- END #testimonials -->
    
           <!-- <div id="clients">
                
                <h3>OUR CLIENTS</h3>
        
                <ul>
                    <li><img src="images/client01.jpg" alt="Cox Communications" /></li>
                    <li><img src="images/client02.jpg" alt="CNN" /></li>
                    <li><img src="images/client03.jpg" alt="Apartment Finder" /></li>
                    <li><img src="images/client04.jpg" alt="John Deere" /></li>
                    <li><img src="images/client05.jpg" alt="Banana Boat" /></li>
                    <li><img src="images/client06.jpg" alt="Fuji Film" /></li>
                </ul>
        
            </div>--> <!-- END #clients -->
    
        </div> <!-- END #bottom_content --><!-- END #download -->
        
    </div> <!-- END #main_content -->
    
    <div id="footer">
        
        <div class="container">
            
            <div id="footer_about" class="footer_info">
                
                <h4>about us</h4>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi euismod placerat dui et tincidunt. Sed sollicitudin posuere auctor. Phasellus at
                ultricies nisl. Integer at leo eros. Ut nec lorem id orci convallis porta. Donec pharetra neque eu velit dictum molestie. </p>
                
            </div> <!-- END #footer_about -->
            
            <div id="footer_explore" class="footer_info">
                
                <h4>explore</h4>
                <ul>
                    <li><a href="index.html">home</a></li>
                    <li><a href="#">about us</a></li>
                    <li><a href="#">services</a></li>
                    <li><a href="portfolio.html">portfolio</a></li>
                    <li><a href="#">blog</a></li>
                </ul>
                
            </div> <!-- END #footer_about -->
            
            <div id="footer_browse" class="footer_info">
                
                <h4>browse</h4>
                <ul>
                    <li><a href="#">careers</a></li>
                    <li><a href="#">press &amp; media</a></li>
                    <li><a href="contact.html">contact us</a></li>
                    <li><a href="#">terms of service</a></li>
                    <li><a href="#">privacy policy</a></li>
                </ul>
                
            </div> <!-- END #footer_about -->
            
            <div id="footer_contact" class="footer_info">
                
                <h4>contact us</h4>
                <p><span class="bold_text">BisLite Inc.</span>
                <br />
                Always Street 265
                <br />
                0X - 125 - Canada
                <br />
                <br />
                Phone: 987-6543-210
                <br />
                Fax: 987-6543-210</p>
                
            </div> <!-- END #footer_about -->
            
            <div id="footer_connect" class="footer_info">
                
                <h4>connect with us</h4>
                
                <ul>
                    <li><a href="#" id="facebook" title="Facebook">Facebook</a></li>
                    <li><a href="#" id="dribbble" title="Dribbble">Dribbble</a></li>
                    <li><a href="#" id="pinterest" title="Pinterest">Pinterest</a></li>
                    <li><a href="#" id="linkedin" title="LinkedIn">LinkedIn</a></li>
                    <li><a href="#" id="skype" title="Skype">Skype</a></li>
                    <li><a href="#" id="sharethis" title="Share This">ShareThis</a></li>
                </ul>
                
            </div> <!-- END #footer_about -->
            
            <p id="copyright">&copy; Copyright 2012 - yeswork.com. All rights reserved. Some free icons used here are created byYeswork1979.com.
            <br />
            Client Logos are copyright and trademark of the respective owners / companies.</p>
            
            <a href="index.html" id="footer_logo">BisLite</a>
        
        </div> <!-- END .container -->
        
    </div> <!-- END #footer -->
    
</body>
</html>