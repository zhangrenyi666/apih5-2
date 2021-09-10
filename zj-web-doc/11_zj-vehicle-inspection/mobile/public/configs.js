window.configs = {
  domain: {
    api: "http://192.168.1.104:8080/",//http://api.fheb.cn:8080/woa/
    file: null,//上传文件类的主域名，如果为null，与prod或dev相同
  },
  app: {
    name: "车检系统",
    dev: false,
    type: "3",//0:password,3:企业号,4:服务号
    defaultToken: "0000000",
    id: "zj_qyh_weoa_id",//app识别id  zj_qyh_weoa_id wechat_fwh_test
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
    getApplyList:'getApplyList',//申请列表
    getApprovalList:'getApprovalList',//审批列表
    addZjCjApplicationForApproval:"addZjCjApplicationForApproval",//申请新增
    getZjCjCarRemind:'getZjCjCarRemind',//获取车辆信息
    getZjCjCarRemindList:'getZjCjCarRemindList',//获取车辆列表
    getApplyDetailForWechat:'getApplyDetailForWechat',//获取申请详情 
    updateZjCjApplicationForApproval:'updateZjCjApplicationForApproval',//审批
    updateType:'updateType'
  }
}
