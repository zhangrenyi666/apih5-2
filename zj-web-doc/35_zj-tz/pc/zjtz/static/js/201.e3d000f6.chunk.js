(window.webpackJsonp=window.webpackJsonp||[]).push([[201],{"8Pd+":function(e,t,a){"use strict";a.r(t);a("9U97"),a("zIPO");var o=a("UpC8"),s=(a("fx2i"),a("cZi0")),n=a("Id3l"),l=a("RNsw"),r=a("OR/T"),i=a("bn5B"),d=a("PnTt"),c=(a("w9C0"),a("XW+2")),p=a("r0ML"),m=a.n(p),u=a("6eYn"),f=a("U8v4"),b=a("f6Di"),I=a.n(b),h=c.a.confirm,w={antd:{rowKey:function(e){return e.pppTermId},size:"small",scroll:{y:.65*document.documentElement.clientHeight}},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!0,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},g=function(e){function t(e){var a;return Object(n.a)(this,t),(a=Object(r.a)(this,Object(i.a)(t).call(this,e))).state={pppTermId:"",projectId:e.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId,projectName:e.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectName},a}return Object(d.a)(t,e),Object(l.a)(t,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var e=this,t=this.state,a=t.projectId,n=t.projectName;return m.a.createElement("div",{className:I.a.root},m.a.createElement(u.a,Object(o.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t}},w,{fetchConfig:{apiName:"getZjTzPppTermList",otherParams:{projectId:a}},formConfig:[{isInTable:!1,form:{field:"pppTermId",type:"string",hide:!0}},{isInTable:!1,form:{field:"projectName",required:!0,addDisabled:!0,disabled:!0,editDisabled:!0,hide:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165",initialValue:function(){return n}}},{isInSearch:!1,table:{title:"\u9879\u76ee\u540d\u79f0",dataIndex:"projectId",key:"projectId",fixed:"left",type:"select",filter:!0,width:350,tooltip:23},form:{field:"projectId",type:"select",showSearch:!0,initialValue:a,editDisabled:!0,required:!0,addDisabled:!0,optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},placeholder:"\u8bf7\u8f93\u5165",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{table:{title:"\u5b50\u9879\u76ee\u540d\u79f0",filter:!0,width:200,dataIndex:"subprojectInfoName",key:"subprojectInfoName"},form:{type:"select",label:"\u5b50\u9879\u76ee\u540d\u79f0",field:"subprojectInfoId",placeholder:"\u8bf7\u8f93\u5165",disabled:!0,editDisabled:!0,optionConfig:{label:"subprojectName",value:"subprojectInfoId"},fetchConfig:{apiName:"getZjTzProSubprojectInfoList",otherParams:{projectId:a}},formItemLayout:{labelCol:{xs:{span:21},sm:{span:3}},wrapperCol:{xs:{span:21},sm:{span:21}}}}},{isInSearch:!1,table:{title:"\u5206\u6790\u671f\u6b21",width:120,dataIndex:"issueId",key:"issueId",type:"select"},form:{type:"select",field:"issueId",editDisabled:!0,optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"fenXiQiCi"}},placeholder:"\u8bf7\u8f93\u5165",required:!0,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{table:{title:"\u6761\u6b3e\u7f16\u53f7\u4e0e\u5185\u5bb9",width:200,tooltip:18,dataIndex:"numberContent",key:"numberContent"},isInForm:!1},{table:{title:"\u6761\u6b3e\u5206\u6790",dataIndex:"term",width:200,tooltip:18,key:"term"},isInForm:!1},{table:{title:"\u662f\u5426\u5b58\u5728\u98ce\u9669",dataIndex:"riskFlag",width:130,onClick:"detail",key:"riskFlag"},isInForm:!1},{table:{title:"\u5e94\u5bf9\u63aa\u65bd",dataIndex:"measure",width:200,tooltip:18,key:"measure"},isInForm:!1},{table:{title:"\u662f\u5426\u9700\u8981\u4e8c\u6b21\u8c08\u5224",dataIndex:"negotiateFlag",width:180,key:"negotiateFlag"},isInForm:!1},{table:{title:"\u8d23\u4efb\u90e8\u95e8\u4e0e\u8d23\u4efb\u4eba",dataIndex:"deptAndLeader",width:200,tooltip:18,key:"deptAndLeader"},isInForm:!1},{table:{title:"\u63aa\u65bd\u843d\u5b9e\u60c5\u51b5",dataIndex:"implement",width:200,tooltip:18,key:"implement"},isInForm:!1},{table:{title:"\u767b\u8bb0\u65e5\u671f",dataIndex:"registerDate",format:"YYYY-MM-DD",key:"registerDate",width:100},isInForm:!1},{isInTable:!1,form:{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"PPP\u5408\u540c\u5206\u6790"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl"}},{isInTable:!1,form:{field:"registerDate",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(){return new Date}}},{isInTable:!1,form:{field:"registerPerson",type:"string",label:"\u767b\u8bb0\u7528\u6237",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(e){return e.loginAndLogoutInfo.loginInfo.userInfo.realName}}},{table:{title:"\u72b6\u6001",filter:!0,width:120,dataIndex:"statusId",key:"statusId",type:"select"},isInForm:!1,form:{type:"select",field:"statusId",optionData:[{label:"\u672a\u4e0a\u62a5",value:"0"},{label:"\u5df2\u4e0a\u62a5",value:"1"},{label:"\u88ab\u9000\u56de",value:"2"}]}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:120,btns:[{name:"PolicyDetail",render:function(e){return"<a>\u660e\u7ec6</a>"},onClick:function(e){var t=e.props.myPublic.appInfo.mainModule,a=e.rowData,o=a.pppTermId,s=a.statusId,n=a.projectName;e.props.dispatch(Object(f.push)("".concat(t,"ContractAnalysisDetail/").concat(o,"/").concat(s,"/").concat(n,"/",0)))}}]}}],method:{addClick:function(e){"all"===a&&(e.btnCallbackFn.closeDrawer(),e.btnCallbackFn.clearSelectedRows(),s.b.warn("\u8bf7\u9009\u62e9\u53f3\u4e0a\u89d2\u9879\u76ee\uff01"))},editClick:function(t){"0"===t.selectedRows[0].statusId||"2"===t.selectedRows[0].statusId?e.table.clearSelectedRows():(t.btnCallbackFn.closeDrawer(),e.table.clearSelectedRows(),s.b.warn("\u8bf7\u9009\u62e9\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u6570\u636e\uff01"))},delClick:function(t){var a=t.props.myFetch,o=[];if(t.selectedRows.length>0){for(var n=0;n<t.selectedRows.length;n++)"0"===t.selectedRows[n].statusId||"2"===t.selectedRows[n].statusId||o.push(t.selectedRows[n].statusId);o.length>0?(t.btnCallbackFn.closeDrawer(),s.b.warn("\u8bf7\u9009\u62e9\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6570\u636e!"),e.table.clearSelectedRows()):h({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchDeleteUpdateZjTzPppTerm",t.selectedRows).then(function(t){var a=t.success,o=t.message;a?(s.b.success(o),e.table.refresh(),e.table.clearSelectedRows()):s.b.error(o)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")},shangBaoClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var o=[],n=0;n<t.selectedRows.length;n++)"1"===t.selectedRows[n].statusId&&o.push(t.selectedRows[n].statusId);o.length>0?s.b.warn("\u5df2\u4e0a\u62a5\u7684\u6d88\u606f\u4e0d\u80fd\u4e0a\u62a5\uff01"):h({title:"\u786e\u5b9a\u4e0a\u62a5\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchReleaseZjTzPppTerm",t.selectedRows).then(function(t){t.data;var a=t.success,o=t.message;a?(s.b.success(o),e.table.refresh()):s.b.error(o)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e\uff01")},tuiHuiClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var o=[],n=0;n<t.selectedRows.length;n++)"0"!==t.selectedRows[n].statusId&&"2"!==t.selectedRows[n].statusId||o.push(t.selectedRows[n].statusId);o.length>0?s.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6d88\u606f\u4e0d\u80fd\u9000\u56de\uff01"):h({title:"\u786e\u5b9a\u9000\u56de\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchRecallZjTzPppTerm",t.selectedRows).then(function(t){var a=t.success,o=t.message;a?(s.b.success(o),e.table.refresh()):s.b.error(o)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e\uff01")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}}})))}}]),t}(p.Component);t.default=g}}]);