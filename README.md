本插件暂提供两个方法以供android手机调用：
1.查询当前手机是否安装了百度地图或高德地图客户端；
example:
var successCallback = function(data){
 var appNames = data.appNames;//appNames表示当前android手机安装了哪些地图类应用，“baidu”表示百度地图，“amap”表示高德地图，暂时只提供此两种app的查找识别
}
OpenNativeApp.queryMapApp(successCallback,errorCallback);

2.根据所给参数打开手机上的百度地图或高德地图导航；
example：
var params = {//调用高德地图时可不传入nowLat、nowLng(高德地图默认使用当前位置进行导航)和type(高德地图Uri api无此参数设置)
    nowLat:'',//当前位置的纬度
    nowLng:'',//当前位置的经度
    sourceLng:'',//目标位置的经度
    sourceLat:'',//当前位置的纬度
    type:'',//导航方式，包含walking，driving，transit
    choice:''//所要打开的app，“baidu”代表百度地图，“amap”代表高德地图
};

OpenNativeApp.openMapApp(successCallback,errorCallback, params);

ps:调用相应地图类app时请务必将您的经纬度转换为相应的标准数据。