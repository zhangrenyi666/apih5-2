window.configs = {
  domain: {
    // api: "http://xm-oa.fheb.cn:88/apixiamengs/",//http://api.fheb.cn:8080/woa/     105
    api: 'http://192.168.1.123:8080/web/',
    file: null,//上传文件类的主域名，如果为null，与prod或dev相同
  },
  app: {
    name: "检查反馈",
    dev: false,
    type: "0",//0:password,3:企业号,4:服务号
    defaultToken: "0000000",
    id: "zj_qyh_woa_id",//app识别id  zj_qyh_woa_id wechat_fwh_test
    // id: "lanzhou_qyh_app_id",
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
    getSysDepartmentAllTree: "syncCacheSysDepartmentUserAllTreeByZj?noPersonnel=1", //通讯录-部门获取, //通讯录-部门获取
    getSysDepartmentUserAllTree: 'syncCacheSysDepartmentUserAllTreeByZj?noPersonnel=0',
    // getDepartment: "apiPostDepartment", // oa部门获取  apiPostDepartment  getSysDepartmentUserAllTree
    // getMember: "apiPostMember", // oa部门人员获取

    //业务相关
    addZjQuestionApproval: 'addZjQuestionApprovalWechat',//问题新增
    getZjQuestionApprovalListWechat: 'getZjQuestionApprovalListWechat',//问题列表查询
    getProjectList: 'getProjectList',//获取项目列表
    getPersonnelByProject: 'getPersonnelByProject',//人员列表
    getQuestionDetailWechat: 'getQuestionDetailWechat',//问题详情
    getQuestionAllListByClassId: 'getQuestionAllListByClassId',
    getQuestionAllListForWechatByClassId: 'getQuestionAllListForWechatByClassId',
    getQuestionAllListForWechat: 'getQuestionAllListForWechat',
    questionClose: 'questionClose',//关闭问题
    addZjQuestionAsk: 'addZjQuestionAsk', //追问
    leaderAgreeWechat: 'leaderAgreeWechat',//领导同意
    leaderRejectWechat: 'leaderRejectWechat', //领导驳回
    getAskAndAnswerDetail: 'getAskAndAnswerDetail',//回答详情列表
    addZjQuestionAnswerWechat: 'addZjQuestionAnswerWechat', //回答 
    getAskAndAnswerDetailWechat: 'getAskAndAnswerDetailWechat',//获取问答列表
    sendRemindForWechat: 'sendRemindForWechat',//提醒
    getQuestionSelectThreeAllList: "getQuestionSelectThreeAllList", //问题选择三级联动


    getQuestionOneGradeSelectList: 'getQuestionOneGradeSelectList',
    getQuestionTwoGradeSelectList: 'getQuestionTwoGradeSelectList',
    getQuestionThreeGradeSelectList: 'getQuestionThreeGradeSelectList',
    getQuestionFourGradeSelectList: 'getQuestionFourGradeSelectList',
    getQuestionAddJurisdictionForWechat: "getQuestionAddJurisdictionForWechat", //判断问题显示隐藏
    leaderAgreeWechat: "leaderAgreeWechat",
    getZjXmQuestionTitleSelectAllList: "getZjXmQuestionTitleSelectAllList",//标题
    getQuestionOneGradeSelectListForWechat: "getQuestionOneGradeSelectListForWechat",//标题关联一级
    getQuestionTwoGradeSelectListForWechat: "getQuestionTwoGradeSelectListForWechat",//标题关联二级
    getQuestionThreeGradeSelectLisForWechat: "getQuestionThreeGradeSelectLisForWechat",//标题关联三级
    getQuestionFourGradeSelectListForWechat: "getQuestionFourGradeSelectListForWechat",//标题关联四级
    zjQuestionApprovalChooseForwardUserWechat:"zjQuestionApprovalChooseForwardUserWechat",//转发

    getZjXmHasTitleQuestionList:"getZjXmHasTitleQuestionList",//列表
    getZjXmQuestionTitleSelectAllList:"getZjXmQuestionTitleSelectAllList",//下拉
    addQuestionCheckQuestion:"addQuestionCheckQuestion",//确认问题
    getZjQueApprovalListByInformationIdForWechat:"getZjQueApprovalListByInformationIdForWechat",//列表
    getZjQuestionCheckItemByProjectIdForWechat:"getZjQuestionCheckItemByProjectIdForWechat",//详情
    addZjQueRectificationAddInforIdForWechat:"addZjQueRectificationAddInforIdForWechat",//新增

    getZjQuestionPAllListByProjectForWechat:"getZjQuestionPAllListByProjectForWechat",//回复下拉
    getZjXmQuestionOfficeLeaderAllList:"getZjXmQuestionOfficeLeaderAllList",//同意下拉
    getQuestionLeaderDetailWechat:"getQuestionLeaderDetailWechat",

    addZjQueImprovementAddInforIdForWechat:"addZjQueImprovementAddInforIdForWechat",//改进
    updateQueImprovementItemByChangerForWechat:"updateQueImprovementItemByChangerForWechat",
  }
}
