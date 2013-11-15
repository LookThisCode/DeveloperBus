<?php
	$titulo="Estadisticas";
	$seccion="estadisticas";
?>

<div id="wrapper">
	<h1 style="margin-bottom: 20px;"><?php echo $titulo ?></h1>
	<?php 
		if (ISSET($_POST['deleteID'])) {
			$postId = $_POST['deleteID'];
			$respuesta = borrarProducto($postId);
			if ($respuesta != "ok"){
				echo '<ul class="log" id="log"><li>'. $respuesta .'</li></ul>';
			}
		}
	?>
	Desde:<b>01/01/2013</b>
	<br>
	Hasta:<b>30/08/2013</b>
	<br>
<!-- CHARTS -->
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">

      // Load the Visualization API and the piechart package.
      google.load('visualization', '1.0', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
          ['Concretadas', 138],
		  ['Ausentes', 12],
          ['Canceladas',44],
        ]);

        // Set chart options
        var options = {'title':'Estadisticas sobre las reservas: 194 totales',
                       'width':800,
                       'height':500};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
	 <div id="chart_div"></div>
</div>

