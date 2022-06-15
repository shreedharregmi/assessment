
use(function() {
   'use strict';
    var dropdownOptions = [];
    var asset = request.resourceResolver.getResource("/content/dam/anf-code-challenge/exercise-1/countries.json").adaptTo(com.day.cq.dam.api.Asset);
    var is = asset.getOriginal().adaptTo(java.io.InputStream);
    var jsonData = JSON.parse(org.apache.commons.io.IOUtils.toString(is, "UTF-8"));
    console.log("testsathish");
    console.log(jsonData);

    if(jsonData){
        for (var key in jsonData) {
           console.log(key);
           console.log(jsonData[key]);
            var opt = {};
            opt.key = key;
            opt.value = jsonData[key];
            dropdownOptions.push(opt);
       }
    }
    return {
        dropdownOptions: dropdownOptions
    }
});