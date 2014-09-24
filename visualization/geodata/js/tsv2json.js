function tsv2JSON(filetsv){
	
	var line = tsv.split("\n");
	var res = [];
	var headers = line[0].split("\t");
	
	for(var i=1;i<line;i++){
		var obj = {};
		var currentline=line[i].split("\t");
		
		for (var j=0;j<headers.length;j++){
			obj[headers[j]] = currentline[j];
		}
		
		result.push(obj);
	}
	return JSON.stringify(result);

}