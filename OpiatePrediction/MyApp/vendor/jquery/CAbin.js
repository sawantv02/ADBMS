/**
 * 
 */
/**
 * 
 */
$(function () {
	var c = [];
    var d = [];

    var options = {
    	    chart: {
    	        plotBackgroundColor: null,
    	        plotBorderWidth: null,
    	        plotShadow: false,
    	        type: 'pie',
    	        renderTo:'containerCA'
    	    },
    	    title: {
    	        text: 'Specialists in CA'
    	    },
    	    tooltip: {
    	        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    	    },
    	    plotOptions: {
    	        pie: {
    	            allowPointSelect: true,
    	            cursor: 'pointer',
    	            dataLabels: {
    	                enabled: true,
    	                format: '<b>{point.name}</b>: {point.percentage:.1f} %',
    	                style: {
    	                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
    	                }
    	            }
    	        }
    	    },
            series: [{
            	name:c,
                data:d
            }]
    };
    console.log("in CA");
    $.get('MyData/CA-m.csv', function(data) {
        var lines = data.split('\n');
        $.each(lines, function(lineNo, line) {
            var items = line.split('\t');
            c.push(items[1]);
            d.push(parseFloat(items[2]));
            
        });
        console.log(c);
        var chart = new Highcharts.Chart(options);   
    },'Text');
    


//    var chart = new Highcharts.Chart(options); 
    

});