/**
 * 
 */
$(function () {
	var c = [];
    var d = [];

    var options = {
            chart: {
                renderTo: 'container1',
                defaultSeriesType: 'column'
            },
            title: {
                text: ''
            },
            xAxis: {
                title: {
                    text: 'Opioids'
                },
                categories: c
            },
            yAxis: {
                title: {
                    text: 'Total Supply'
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: d
            }]
    };
    console.log("in myjs");
    $.get('MyData/Top10PrescribedOpioids.csv', function(data) {
        var lines = data.split('\n');
        $.each(lines, function(lineNo, line) {
            var items = line.split(',');
            c.push(items[0]);
            d.push(parseFloat(items[1]));
            
        });
        var chart = new Highcharts.Chart(options);   
    },'Text');
    


//    var chart = new Highcharts.Chart(options);   
});