(window.webpackJsonp=window.webpackJsonp||[]).push([[165],{IcsW:function(e,a,l){},OLeP:function(e,a,l){"use strict";l.r(a);l("zIPO");var t=l("UpC8"),s=(l("fx2i"),l("cZi0")),o=l("Id3l"),r=l("RNsw"),n=l("OR/T"),i=l("bn5B"),d=l("PnTt"),c=(l("w9C0"),l("XW+2")),p=l("r0ML"),m=l.n(p),f=l("6eYn"),b=(l("vcLm"),l("ID3L"),l("IcsW")),u=l.n(b),h=c.a.confirm,w=function(e){function a(e){var l;return Object(o.a)(this,a),(l=Object(n.a)(this,Object(i.a)(a).call(this,e))).state={visibleSend:!1,loadingSend:!1,rulesId:""},l}return Object(d.a)(a,e),Object(r.a)(a,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var e=this,a=this.state,l=(a.visibleSend,a.loadingSend,a.rulesId,this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1);return m.a.createElement("div",{className:u.a.root},m.a.createElement(f.a,Object(t.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(a){e.table=a}},window.RulesRegulationsPage,{desc:"1"===l?"\u53d1\u5e03\uff1a\u4f7f\u6570\u636e\u5168\u5e73\u53f0\u7528\u6237\u53ef\u89c1\uff1b\u5e7f\u800c\u544a\u4e4b\uff1a\u5c06\u5df2\u53d1\u5e03\u7684\u6570\u636e\u5c55\u793a\u81f3\u9996\u9875":null,formConfig:[{isInTable:!1,form:{field:"rulesId",type:"string",hide:!0}},{isInSearch:!1,table:{title:"\u6807\u9898",dataIndex:"title",filter:!0,onClick:"detail",tooltip:25,width:300,key:"title"},form:{type:"string",field:"title",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{isInSearch:!1,isInTable:!1,form:{field:"symbolNo",type:"string",label:"\u6587\u53f7",required:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u53d1\u6587\u5c42\u7ea7",dataIndex:"releaseRankName",width:100,key:"releaseRankName",filter:!0},form:{field:"releaseRankId",type:"select",required:!0,optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"faWenCengJi"}},placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u662f\u5426\u6709\u6548\u6587\u4ef6",dataIndex:"effectiveId",key:"effectiveId",width:125,filter:!0,render:function(e){return"0"===e?"\u5426":"\u662f"}},isInForm:!1,form:{type:"radio",required:!0,label:"\u662f\u5426\u6709\u6548",field:"effectiveId",initialValue:"1",optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}]}},{table:{title:"\u53d1\u6587\u90e8\u95e8",dataIndex:"departmentName",width:100,tooltip:23,key:"departmentName",filter:!0},isInForm:!1,form:{field:"departmentName",type:"string",label:"\u53d1\u6587\u90e8\u95e8",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{field:"departmentName",type:"string",label:"\u53d1\u6587\u90e8\u95e8",required:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{isInTable:!1,form:{type:"radio",required:!0,label:"\u662f\u5426\u6709\u6548",field:"effectiveId",initialValue:"1",optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u53d1\u5e03\u65e5\u671f",dataIndex:"releaseDate",key:"releaseDate",width:100,format:"YYYY-MM-DD",filter:!0},isInForm:!1,form:{field:"releaseDate",type:"date",required:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u7531\u6b64\u5e9f\u9664\u7684\u76f8\u5173\u6587\u4ef6\u53f7",dataIndex:"abolishSymbolNo",key:"abolishSymbolNo",width:180,tooltip:23,filter:!0},isInForm:!1,form:{field:"abolishSymbolNo",type:"string",placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:10}},wrapperCol:{xs:{span:24},sm:{span:14}}}}},{isInTable:!1,form:{field:"abolishSymbolNo",label:"\u7531\u6b64\u5e9f\u9664\u7684\u76f8\u5173\u6587\u4ef6\u53f7",type:"string",placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:10}},wrapperCol:{xs:{span:24},sm:{span:14}}}}},{isInTable:!1,form:{field:"releaseDate",label:"\u53d1\u6587\u65e5\u671f",type:"date",required:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{isInTable:!1,form:{label:"\u5907\u6ce8",field:"remarks",type:"textarea",placeholder:"\u8bf7\u8f93\u5165",autoSize:{minRows:2}}},{isInTable:!1,form:{field:"registeredTime",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(){return new Date}}},{isInTable:!1,form:{field:"registeredUser",type:"string",label:"\u767b\u8bb0\u7528\u6237",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(e){return e.loginAndLogoutInfo.loginInfo.userInfo.realName}}},{table:{title:"\u72b6\u6001",dataIndex:"releaseName",key:"releaseName",filter:!0,width:100},isInForm:!1,form:{type:"select",field:"releaseId",hide:!0,optionData:[{label:"\u672a\u53d1\u5e03",value:"0"},{label:"\u53d1\u5e03",value:"1"}]}},{table:{title:"\u662f\u5426\u5728\u9996\u9875\u5e7f\u800c\u544a\u4e4b",dataIndex:"homeShow",filter:!0,width:160,key:"homeShow",render:function(e){return"0"===e?"\u5426":"\u662f"}},isInForm:!1,form:{type:"select",field:"homeShow",hide:!0,optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}]}},{isInTable:!1,form:{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u89c4\u7ae0\u5236\u5ea6"}}}}],method:{editClick:function(a){"1"===a.selectedRows[0].releaseId?(a.btnCallbackFn.closeDrawer(),s.b.warn("\u5df2\u53d1\u5e03\u7684\u4e0d\u80fd\u4fee\u6539!"),e.table.clearSelectedRows()):e.table.clearSelectedRows()},faBuClick:function(a){e.table.clearSelectedRows();var l=a.props.myFetch;if(a.selectedRows.length>0){for(var t=[],o=0;o<a.selectedRows.length;o++)"1"===a.selectedRows[o].releaseId&&t.push(a.selectedRows[o].releaseId);t.length>0?s.b.warn("\u5df2\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u53d1\u5e03\uff01"):h({title:"\u786e\u5b9a\u53d1\u5e03\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){l("batchDeleteReleaseZjTzRules",a.selectedRows).then(function(a){var l=a.success,t=a.message;l?(s.b.success(t),e.table.refresh()):s.b.error(t)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},cheHuiClick:function(a){e.table.clearSelectedRows();var l=a.props.myFetch;if(a.selectedRows.length>0){for(var t=[],o=0;o<a.selectedRows.length;o++)"0"===a.selectedRows[o].releaseId&&t.push(a.selectedRows[o].releaseId);t.length>0?s.b.warn("\u672a\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u64a4\u56de\uff01"):h({title:"\u786e\u5b9a\u64a4\u56de\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){l("batchDeleteRecallZjTzRules",a.selectedRows).then(function(a){var l=a.success,t=a.message;l?(s.b.success(t),e.table.refresh()):s.b.error(t)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},guangErGaoZhi:function(a){e.table.clearSelectedRows(),a.selectedRows.length>0?1===a.selectedRows.length?"1"===a.selectedRows[0].releaseId?h({title:"\u786e\u5b9a\u5e7f\u800c\u544a\u4e4b\u5230\u9996\u9875\u4e48\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){e.props.myFetch("toHomeShowZjTzRules",a.selectedRows[0]).then(function(a){var l=a.success,t=a.message;a.data;l?(s.b.success(t),e.table.refresh()):s.b.error(t)})}}):s.b.warn("\u672a\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u5e7f\u800c\u544a\u4e4b\uff01"):s.b.warn("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e"):s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},delClick:function(a){var l=a.props.myFetch,t=[];if(a.selectedRows.length>0){for(var o=0;o<a.selectedRows.length;o++)"1"===a.selectedRows[o].releaseId&&t.push(a.selectedRows[o].releaseId);t.length>0?(a.btnCallbackFn.closeDrawer(),s.b.warn("\u5df2\u53d1\u5e03\u7684\u4e0d\u80fd\u5220\u9664!"),e.table.clearSelectedRows()):h({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){l("batchDeleteUpdateZjTzRules",a.selectedRows).then(function(a){var l=a.success,t=a.message;l?(s.b.success(t),e.table.refresh(),e.table.clearSelectedRows()):s.b.error(t)})}})}else s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")},filestExport:function(a){e.table.clearSelectedRows();var l=a.props.myFetch;a.selectedRows.length>0?h({title:"\u786e\u5b9a\u5bfc\u51fa\u9644\u4ef6\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){l("batchExportZjTzRulesFile",a.selectedRows).then(function(a){var l=a.success,t=a.message,o=a.data;l?(s.b.success(t),window.location.href=o,e.table.refresh(),e.table.clearSelectedRows()):s.b.error(t)})}}):s.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var a=e.Pprops;return{menuParentId:a.routerInfo.routeData[a.routerInfo.curKey]._value,tableField:"projectInfo"}}}})))}}]),a}(p.Component);a.default=w}}]);