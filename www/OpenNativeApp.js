var argscheck = require('cordova/argscheck'),
    exec = require('cordova/exec');
var OpenNativeApp = {
    queryMapApp:function(scb,ecb){
    exec(scb,ecb,"OpenNativeApp","queryMapApp",[]);
    },
    openMapApp:function(scb,ecb,params){
    params = params || {};
    var getValue = argscheck.getValue;
    var nowLat = getValue(params.nowLat,0);
    var nowLng = getValue(params.nowLng,0);
    var sourceLat = getValue(params.sourceLat,0);
    var sourceLng = getValue(params.sourceLng,0);
    var type = getValue(params.type,"walking");
    var choice = getValue(params.choice,null);
    var args = [nowLat,nowLng,sourceLat,sourceLng,type,choice];
    console.log("plugin js");
    console.log(args);
    exec(scb,ecb,"OpenNativeApp","openMapApp",args);
    }
};

module.exports = OpenNativeApp;