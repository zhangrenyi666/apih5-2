(window.webpackJsonp=window.webpackJsonp||[]).push([[198],{YhN3:function(e,a,t){"use strict";t.r(a);t("9U97"),t("Z5ek"),t("zIPO");var r=t("UpC8"),s=(t("8xN/"),t("QvOI")),n=(t("fx2i"),t("cZi0")),i=t("Id3l"),o=t("RNsw"),l=t("OR/T"),c=t("bn5B"),d=t("PnTt"),p=t("r0ML"),u=t.n(p),m=t("6eYn"),h=t("U8v4"),f=t("tHGw"),b=t.n(f),I={antd:{rowKey:function(e){return e.complianceDealId},size:"small",scroll:{y:.65*document.documentElement.clientHeight}},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!0},y=function(e){function a(e){var t;return Object(i.a)(this,a),(t=Object(l.a)(this,Object(c.a)(a).call(this,e))).state={establishDate:null},t}return Object(d.a)(a,e),Object(o.a)(a,[{key:"componentDidMount",value:function(){var e,a=this,t=this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId;this.props.myFetch("getZjTzProManageList",{}).then(function(r){var s=r.data,i=r.success,o=r.message;i?(s.map(function(a){return a.projectId===t&&(e=a.company2),a}),a.setState({establishDate:e})):n.b.error(o)})}},{key:"render",value:function(){var e=this,a=this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,i=this.props.loginAndLogoutInfo.loginInfo.userInfo.realName;return u.a.createElement("div",{className:b.a.root},u.a.createElement(m.a,Object(r.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(a){e.table=a},fetchConfig:{apiName:"getZjTzComplianceDealList",otherParams:{projectId:a}}},I,{formConfig:[{isInTable:!1,form:{field:"complianceDealId",type:"string",hide:!0}},{table:{title:"\u8003\u6838\u9884\u8b66",dataIndex:"warnFlag",key:"warnFlag",width:80,align:"center",fixed:"left",render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):""}},isInForm:!1},{table:{title:"\u9879\u76ee\u540d\u79f0",filter:!0,width:400,fixed:"left",dataIndex:"projectId",key:"projectId",type:"select"},form:{field:"projectId",type:"select",showSearch:!0,addDisabled:!0,editDisabled:!0,initialValue:function(){return a},optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},placeholder:"\u8bf7\u9009\u62e9",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:8},sm:{span:8}},wrapperCol:{xs:{span:16},sm:{span:16}}}}},{table:{title:"\u5b50\u9879\u76ee\u540d\u79f0",dataIndex:"subprojectName",key:"subprojectName",width:250,onClick:"detail",fixed:"left"},form:{field:"subprojectInfoId",type:"select",editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",optionConfig:{label:"subprojectName",value:"subprojectInfoId"},fetchConfig:{apiName:"getZjTzProSubprojectInfoList",otherParams:{projectId:a}},spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:8},sm:{span:8}},wrapperCol:{xs:{span:16},sm:{span:16}}}}},{table:{title:"\u7ba1\u7406\u5355\u4f4d",dataIndex:"companyName",key:"companyName",width:150,filter:!0},isInForm:!1,form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u9879\u76ee\u516c\u53f8\u6210\u7acb\u65e5\u671f",dataIndex:"establishDate",key:"establishDate",format:"YYYY-MM-DD",width:150},form:{type:"date",placeholder:"\u8bf7\u8f93\u5165",addDisabled:!0,editDisabled:!0,required:!0,diyRules:function(e){return[{required:e.required,message:"\u3010\u8bf7\u5728\u9879\u76ee\u4fe1\u606f\u4e2d\u586b\u5199\u9879\u76ee\u516c\u53f8\u6210\u7acb\u65f6\u95f4\u3011"}]},initialValue:function(a){if(e.state.establishDate)return e.state.establishDate}}},{table:{title:"\u5de5\u7a0b\u53ef\u884c\u6027\u7814\u7a76\u62a5\u544a",dataIndex:"base1",key:"base1",width:150,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u5de5\u53ef\u6279\u590d\u6216\u9879\u76ee\u6838\u51c6",dataIndex:"base2",key:"base2",width:150,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u521d\u8bbe\u6279\u590d",dataIndex:"base3",key:"base3",width:100,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u65bd\u5de5\u56fe\u8bbe\u8ba1\u6279\u590d",dataIndex:"base4",key:"base4",width:120,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u7528\u5730\u6279\u590d",dataIndex:"base5",key:"base5",width:100,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u65bd\u5de5\u8bb8\u53ef\u8bc1",dataIndex:"base6",key:"base6",width:100,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u878d\u8d44\u534f\u8bae",dataIndex:"base7",key:"base7",width:100,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{table:{title:"\u73af\u8bc4\u6279\u590d",dataIndex:"base8",key:"base8",width:100,render:function(e){return"green"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("sgKX")}):"yellow"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("xHv8")}):"red"===e?u.a.createElement(s.a,{shape:"square",size:20,src:t("y8v7")}):void 0}},isInForm:!1},{isInTable:!1,form:{label:"\u5907\u6ce8",field:"remarks",type:"textarea",autoSize:{minRows:2},placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{label:"\u521b\u5efa\u65e5\u671f",field:"createTime",type:"date",initialValue:new Date,placeholder:"\u8bf7\u9009\u62e9",addDisabled:!0,editDisabled:!0,spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:8},sm:{span:8}},wrapperCol:{xs:{span:16},sm:{span:16}}}}},{isInTable:!1,form:{label:"\u521b\u5efa\u7528\u6237",field:"createUserName",type:"string",initialValue:i,placeholder:"\u8bf7\u8f93\u5165",addDisabled:!0,editDisabled:!0,spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:8},sm:{span:8}},wrapperCol:{xs:{span:16},sm:{span:16}}}}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:80,btns:[{name:"complianceDetail",render:function(e){return"<a>\u5408\u89c4\u660e\u7ec6</a>"},onClick:function(e){var a=e.props.myPublic.appInfo.mainModule,t=e.rowData,r=t.complianceDealId,s=t.projectId;e.props.dispatch(Object(h.push)("".concat(a,"ComplianceToDealWithDetail/").concat(r,"/").concat(s)))}}]}}],method:{addClick:function(e){"all"===a&&(e.btnCallbackFn.closeDrawer(),e.btnCallbackFn.clearSelectedRows(),n.b.warn("\u8bf7\u9009\u62e9\u53f3\u4e0a\u89d2\u9879\u76ee\uff01"))},editClick:function(a){e.table.clearSelectedRows()}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var a=e.Pprops;return{menuParentId:a.routerInfo.routeData[a.routerInfo.curKey]._value,tableField:"projectInfo"}}}})))}}]),a}(p.Component);a.default=y}}]);