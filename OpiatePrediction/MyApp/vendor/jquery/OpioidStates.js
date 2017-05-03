/**
 * 
 */
$(function(){
	var c = [];
    var d = [];

    var options1 = {
            chart: {
                renderTo: 'container2',
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
                    text: 'Opioids Prescribed'
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: d
            }]
    };
    console.log("in statebyopioids");
    $.get('MyData/StatewiseAnalysis.csv', function(data) {
        var lines = data.split('\n');
        $.each(lines, function(lineNo, line) {
            var items = line.split('\t');
            c.push(items[0]);
            d.push(parseFloat(items[1]));
            
        });
        var chart = new Highcharts.Chart(options1);   
    },'Text');
});