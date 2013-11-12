<html>
  <head>
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load('visualization', '1', {'packages':['motionchart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Frutas');
        data.addColumn('date', 'Fecha');
        data.addColumn('number', 'Ventas');
        data.addColumn('number', 'Gastos');
        data.addColumn('string', 'Estado');
        data.addRows([
          ['BONAFONT, SABORIZADA BOTELLA DE 1.5 LT',  new Date (1988,0,1), 1000, 300, 'DF'],
          ['Oranges', new Date (1988,0,1), 1150, 200, 'EDO MEX'],
          ['Bananas', new Date (1988,0,1), 300,  250, 'EDO MEX'],
          ['Apples',  new Date (1989,6,1), 1200, 400, 'DF'],
          ['Oranges', new Date (1989,6,1), 750,  150, 'DF'],
          ['Bananas', new Date (1989,6,1), 788,  617, 'EDO MEX']
        ]);
        var chart = new google.visualization.MotionChart(document.getElementById('chart_div'));
        chart.draw(data, {width: 600, height:300});
      }
    </script>
  </head>

  <body>
    <div id="chart_div" style="width: 600px; height: 300px;"></div>
  </body>
</html>