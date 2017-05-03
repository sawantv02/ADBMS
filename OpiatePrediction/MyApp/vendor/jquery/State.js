/**
 * 
 */
$(function () {
	var c = [];
    var d = [];

    var options = {
            chart: {
                renderTo: 'container3',
                type:'bar'
            },
            title: {
                text: ''
            },
            xAxis: {
                title: {
                    text: 'States'
                },
                categories: c
            },
            yAxis: {
                title: {
                    text: 'Deaths'
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: d
            }]
    };
    console.log("in statebydeath");
    $.get('MyData/StatewiseAnalysis.csv', function(data) {
        var lines = data.split('\n');
        $.each(lines, function(lineNo, line) {
            var items = line.split('\t');
            c.push(items[0]);
            d.push(parseFloat(items[2]));
            
        });
        var chart = new Highcharts.Chart(options);   
    },'Text');
    


//    var chart = new Highcharts.Chart(options); 
    

});