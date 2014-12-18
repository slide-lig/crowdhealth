
function update_pie_chart(div_name, health_stats, chart_title) {
    var chart_data = [];
    for (i=0; i< health_stats.length; i++) {
        item = health_stats[i];
        chart_data.push([item.name, item.value]);
    }

    $(function () {

        $(div_name).highcharts({
            chart: {
                plotBackgroundColor: null,
                backgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
				height: '450',

				width: '450'
			}, 
            title: {
                text: null,
                style: { "fontSize": "22px" }
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
					size: '17%',
                    animation: false,
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: 'black',
                            fontSize: '15px'
                        }
                    }
                }
            },
            series: [{
                type: 'pie',
                name: 'Percentage',
                data: chart_data
            }],
	    exporting: { enabled: false }
        });
    });
}

function update_pieChart(){
       d3.select('td').remove();
       d3.select('td').remove();
       d3.select('td').remove();
       d3.select('td').remove();
  $(function() {


   var people = [];

   $.getJSON('histograms.json', function(data) {
       $.each(data.user_count, function(i, f) {
        if (f.name == "health"){
        var tblRowUH = "<td>" + f.value + "</td>"
           $(tblRowUH).appendTo("#hUsers health");
        }
        if (f.name == "nutrition"){
        var tblRowUN = "<td>" + f.value + "</td>"
           $(tblRowUN).appendTo("#nUsers nutrition");
        }
     });
       $.each(data.tweet_count, function(i, f) {
        if (f.name == "health"){
        var tblRowTH = "<td>" + f.value + "</td>"
           $(tblRowTH).appendTo("#hUsers healthTweets");
        }
        if (f.name == "nutrition"){
        var tblRowTN = "<td>" + f.value + "</td>"
           $(tblRowTN).appendTo("#nUsers nutritionTweets");
        }
     });
   });

});

    d3.json("histograms.json", function(json){
		var hName = "Health Profiles", nName = "Nutrition Profiles";
		//var hRes = hName.concat(tblRowH), nRes = nName.concat(tblRowN);
            update_pie_chart('#health_pie_container', json.user_health_pie, hName);
            update_pie_chart('#nutrition_pie_container', json.user_nutrition_pie,  nName);
    });
}
