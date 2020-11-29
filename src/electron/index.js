const { system, osInfo } = require('systeminformation');

module.exports = {
  getDeviceInfo: async () => {
    try {
      const { manufacturer, model, uuid } = await system();
      const { platform, distro, codename, build } = await osInfo();

      return {
        manufacturer,
        model,
        platform: platform === 'darwin' ? codename : distro,
        version: build,
        uuid,
        // cordova: ''
        isVirtual: false
      }
    } catch (e) {
      console.log(e)
    }
  }
};
