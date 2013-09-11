
var firefoxos = require('cordova/platform');

var Device = {
    getDeviceInfo: function(success, error) {
        success({
            cordova: firefoxos.cordovaVersion,
            platform: 'firefoxos',
            model: null,
            version: null,
            uuid: null
        });
    }
};

firefoxos.registerPlugin('Device', Device);
