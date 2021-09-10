window.configs = {
  domain: {
    api: "http://192.168.1.106:8080/", // http://192.168.1.104:8080/  http://weixin.fheb.cn:8080/woa/
    file: null,//上传文件类的主域名，如果为null，与prod或dev相同
  },
  app: {
    name: "签字统计",
    dev: false,
    type: "3",//0:password,3:企业号,4:服务号
    defaultToken: "0000000",
    id: "zj_qyh_woa_id",//app识别id  zj_qyh_woa_id wechat_fwh_test
  },
  wxConfigs: {
    enabled: true,
    debug: false,
    jsApiList: [
      'downloadVoice',
      'downloadImage',
      'startRecord',
      'stopRecord',
      'uploadVoice',
      'playVoice',
      'stopVoice',
      'translateVoice',
      'chooseImage',
      'uploadImage',
      'closeWindow'
    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
  },
  timeout: 7 * 24 * 60 * 60 * 1000,
  apis: {
    login: "user/login",//登录接口
    queryAll: "user/queryAll",//获取全部用户
    getCorpInfo: "getCorpInfo",//获取公司信息（微信oauth2地址和jssdk配置）
    //业务相关
    getFileListForWechatByUserId:'getFileListForWechatByUserId',//获取列表 
    getFileDetailForWeChat:'getFileDetailForWeChat',//详情页
    addSignPictureToWord:'addSignPictureToWord',//提交签名
  }
}
