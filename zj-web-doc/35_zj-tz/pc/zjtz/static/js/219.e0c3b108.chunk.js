(window.webpackJsonp=window.webpackJsonp||[]).push([[219],{b2ri:function(e,a,t){"use strict";t.r(a);t("+kFb"),t("zIPO"),t("8Qi2"),t("IdsT"),t("l9AF"),t("pBsb"),t("Id6h");var n=t("UpC8"),o=(t("fx2i"),t("cZi0")),l=t("Id3l"),i=t("RNsw"),s=t("OR/T"),r=t("bn5B"),d=t("PnTt"),p=(t("w9C0"),t("XW+2")),c=t("r0ML"),m=t.n(c),u=t("6eYn"),b=t("Rvgi"),f=t.n(b),h=t("U8v4"),I=p.a.confirm,g={antd:{rowKey:function(e){return e.sizeControlRecordId},size:"small"},drawerConfig:{width:"1100px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!0,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},w=function(e){function a(e){var t;return Object(l.a)(this,a),(t=Object(s.a)(this,Object(r.a)(a).call(this,e))).state={sizeControlId:e.match.params.sizeControlId||"",subprojectInfoId:e.match.params.subprojectInfoId||"",proNameVal:e.match.params.projectName||"",proNameId:e.match.params.proManageId||"",addFlag:e.match.params.addFlag||"",zcbShare:"",investId:"",juShare:""},t}return Object(d.a)(a,e),Object(i.a)(a,[{key:"componentDidMount",value:function(){var e=this.props.myFetch,a=this.state.proNameId,t=this;e("getZjTzProManageList",{}).then(function(e){var n=e.data,o=e.success;e.message;o&&n.forEach(function(e,n){e.projectId===a&&t.setState({zcbShare:e.company8,investId:e.investPatten,juShare:e.company7})})})}},{key:"render",value:function(){var e=this,a=this.state,t=a.sizeControlId,l=a.proNameVal,i=a.proNameId,s=a.subprojectInfoId,r=a.zcbShare,d=a.investId,p=a.juShare;return m.a.createElement("div",{className:f.a.root},m.a.createElement(u.a,Object(n.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},fetchConfig:{apiName:"getZjTzSizeControlRecordList",otherParams:{sizeControlId:t,projectId:i},success:function(a){a.data&&a.data[0]&&a.data[0].addFlag&&e.setState({addFlag:a.data[0].addFlag})}},wrappedComponentRef:function(a){e.tableGM=a},method:{addClick:function(a){"false"===e.state.addFlag&&(a.btnCallbackFn.closeDrawer(),o.b.warn("\u5b58\u5728\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6570\u636e\uff0c\u4e0d\u5141\u8bb8\u65b0\u589e\u6570\u636e\uff01"))},editClick:function(a){var t=a.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;a.selectedRows[0].changeNumber===a.selectedRows[0].count-1?"1"===a.selectedRows[0].statusId&&("2"===t||"4"===t)?(a.btnCallbackFn.closeDrawer(),o.b.warn("\u53ea\u5141\u8bb8\u4fee\u6539\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6570\u636e!"),e.tableGM.clearSelectedRows()):e.tableGM.clearSelectedRows():(a.btnCallbackFn.closeDrawer(),o.b.warn("\u53ea\u5141\u8bb8\u4fee\u6539\u6700\u65b0\u53d8\u52a8\u6570\u636e!"),e.tableGM.clearSelectedRows())},delClick:function(a){var t=e.props,n=t.myFetch,l=t.myPublic.appInfo.mainModule;if(a.selectedRows.length>0){var i=a.selectedRows[0].count;1===a.selectedRows.length?"1"===a.selectedRows[0].statusId?o.b.warn("\u8bf7\u9009\u62e9\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6570\u636e\uff01"):a.selectedRows[0].changeNumber===a.selectedRows[0].count-1?I({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){n("batchDeleteUpdateZjTzSizeControlRecord",a.selectedRows).then(function(t){var n=t.success,s=t.message;n?(o.b.success(s),1===i?a.props.dispatch(Object(h.push)("".concat(l,"InvestmentScaleControl"))):(e.tableGM.refresh(),e.tableGM.clearSelectedRows())):o.b.error(s)})}}):o.b.warn("\u53ea\u5141\u8bb8\u5220\u9664\u6700\u65b0\u53d8\u52a8\u6570\u636e!"):o.b.warn("\u5220\u9664\u65f6\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u6570\u636e!")}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")},goBack:function(e){var a=e.props.myPublic.appInfo.mainModule;e.props.dispatch(Object(h.push)("".concat(a,"InvestmentScaleControl")))},shangBaoClick:function(a){e.tableGM.clearSelectedRows();var t=a.props.myFetch;if(1===a.selectedRows.length){for(var n=[],l=0;l<a.selectedRows.length;l++)"1"===a.selectedRows[l].statusId&&n.push(a.selectedRows[l].statusId);n.length>0?o.b.warn("\u5df2\u4e0a\u62a5\u7684\u6d88\u606f\u4e0d\u80fd\u4e0a\u62a5\uff01"):I({title:"\u786e\u5b9a\u4e0a\u62a5\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){t("singleReleaseZjTzSizeControlRecord",a.selectedRows[0]).then(function(a){a.data;var t=a.success,n=a.message;t?(o.b.success(n),e.tableGM.refresh()):o.b.error(n)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e\uff01")},tuiHuiClick:function(a){e.tableGM.clearSelectedRows();var t=a.props.myFetch;if(1===a.selectedRows.length){for(var n=[],l=0;l<a.selectedRows.length;l++)"0"!==a.selectedRows[l].statusId&&"2"!==a.selectedRows[l].statusId||n.push(a.selectedRows[l].statusId);n.length>0?o.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6d88\u606f\u4e0d\u80fd\u9000\u56de\uff01"):I({title:"\u786e\u5b9a\u9000\u56de\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){t("singleRecallZjTzSizeControlRecord",a.selectedRows[0]).then(function(a){var t=a.success,n=a.message;t?(o.b.success(n),e.tableGM.refresh()):o.b.error(n)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e\uff01")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var a=e.Pprops;return{menuParentId:a.routerInfo.routeData[a.routerInfo.curKey]._value,tableField:"projectInfo"}}}},g,{formConfig:[{isInTable:!1,form:{field:"sizeControlRecordId",type:"string",hide:!0}},{isInTable:!1,form:{field:"sizeControlId",type:"string",initialValue:t,hide:!0}},{table:{title:"\u9879\u76ee\u540d\u79f0",onClick:"detail",dataIndex:"projectName",key:"projectName",width:200,fixed:"left",willExecute:function(e){e.btnCallbackFn.setActiveKey("0")}},isInForm:!1,form:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{table:{title:"\u5b50\u9879\u76ee\u540d\u79f0",width:200,tooltip:23,dataIndex:"subprojectName",key:"subprojectName"},isInForm:!1,form:{type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u53d8\u52a8\u6b21\u6570",width:120,dataIndex:"changeNumber",key:"changeNumber",render:function(e){return e?0===e?"\u7b2c0\u6b21":"\u7b2c"+e+"\u6b21":"\u7b2c0\u6b21"}},isInForm:!1,form:{type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u53d8\u52a8\u5c5e\u6027",width:130,tooltip:23,dataIndex:"changePropertyName",key:"changePropertyName"},isInForm:!1,form:{type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u6295\u8d44\u89c4\u6a21<br>\uff08\u4e07\u5143\uff09</div>",width:120,dataIndex:"amount1",key:"amount1",render:"bind:_divide::10000::2"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u603b\u6295\u8d44\u53d8\u52a8\u5dee\u503c<br>\uff08\u4e07\u5143\uff09</div>",dataIndex:"dvalue1",width:140,key:"dvalue1",render:"bind:_divide::10000::2"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u5efa\u5b89\u8d39<br>\uff08\u4e07\u5143\uff09</div>",width:120,dataIndex:"amount2",key:"amount2",render:"bind:_divide::10000::2"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u5efa\u5b89\u8d39\u53d8\u52a8\u5dee\u503c<br>\uff08\u4e07\u5143\uff09</div>",dataIndex:"dvalue2",width:140,key:"dvalue2",render:"bind:_divide::10000::2"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u67e5\u7f3a\u8865\u6f0f<br>\uff08\u4e07\u5143\uff09</div>",width:120,dataIndex:"amount3",key:"amount3",render:"bind:_divide::10000::2"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u67e5\u7f3a\u8865\u6f0f\u5904\u7f6e\u65b9\u6848",dataIndex:"scheme",width:140,tooltip:23,key:"scheme"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u4e00\u516c\u5c40\u96c6\u56e2\u6279\u590d",width:130,dataIndex:"juName",key:"juName"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u4e2d\u56fd\u4ea4\u5efa<br>\u6279\u590d</div>",width:120,dataIndex:"chinaName",key:"chinaName"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"<div>\u9879\u76ee\u4e09\u4f1a<br>\u662f\u5426\u901a\u8fc7</div>",width:120,dataIndex:"thirdReplyName",key:"thirdReplyName"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u653f\u5e9c\u6279\u590d",width:120,dataIndex:"localGovName",key:"localGovName"},isInForm:!1,form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u72b6\u6001",filter:!0,width:120,fixed:"right",dataIndex:"statusId",key:"statusId",type:"select"},isInForm:!1,form:{type:"select",field:"statusId",optionData:[{label:"\u672a\u4e0a\u62a5",value:"0"},{label:"\u5df2\u4e0a\u62a5",value:"1"},{label:"\u88ab\u9000\u56de",value:"2"}]}}],tabs:[{field:"form2",name:"qnnForm",title:"\u6295\u8d44\u89c4\u6a21",content:{fetchConfig:function(e){return e.clickCb.rowData?{apiName:"getZjTzSizeControlRecordDetails",otherParams:{sizeControlRecordId:e.clickCb.rowData.sizeControlRecordId}}:e.clickCb.selectedRows.length>0?{apiName:"getZjTzSizeControlRecordDetails",otherParams:{sizeControlRecordId:e.clickCb.selectedRows[0].sizeControlRecordId}}:{}},formConfig:[{field:"sizeControlId",type:"string",initialValue:t,hide:!0},{field:"sizeControlRecordId",type:"string",hide:!0},{type:"string",label:"\u9879\u76ee\u540d\u79f0",field:"projectName",placeholder:"\u8bf7\u8f93\u5165",required:!0,addDisabled:!0,editDisabled:!0,span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:l},{type:"string",label:"",hide:!0,field:"projectId",placeholder:"\u8bf7\u8f93\u5165",initialValue:i,condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"select",label:"\u5b50\u9879\u76ee\u540d\u79f0",addDisabled:!0,editDisabled:!0,field:"subprojectInfoId",placeholder:"\u8bf7\u8f93\u5165",span:12,initialValue:s,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionConfig:{label:"subprojectName",value:"subprojectInfoId"},fetchConfig:{apiName:"getZjTzProSubprojectInfoList",otherParams:{projectId:i}}},{type:"number",label:"\u53d8\u52a8\u6b21\u6570",addDisabled:!0,editDisabled:!0,field:"changeNumber",required:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:0},{type:"select",required:!0,editDisabled:!0,label:"\u53d8\u52a8\u5c5e\u6027",field:"changePropertyId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"BianDongShuXing"}}},{type:"number",label:"\u6295\u8d44\u89c4\u6a21\uff08\u5143\uff09",required:!0,field:"amount1",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"number",label:"\u5efa\u5b89\u8d39\uff08\u5143\uff09",required:!0,field:"amount2",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"number",label:"\u67e5\u7f3a\u8865\u6f0f\uff08\u5143\uff09",field:"amount3",required:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"radio",label:"\u662f\u5426\u4e8c\u6b21\u8c08\u5224",field:"secondNegotiateId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"textarea",label:"\u67e5\u7f3a\u8865\u6f0f\u65b9\u6848",field:"scheme",required:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"schemeFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({schemeFileList:[]})},200))},span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"radio",label:"\u4e09\u4f1a\u6279\u590d",field:"thirdReplyId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"thirdReplyFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({thirdReplyFileList:[]})},200))},span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"radio",label:"\u5730\u65b9\u653f\u5e9c\u6279\u590d",field:"localGovId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"localGovFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({localGovFileList:[]})},200))},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"textarea",label:"\u5907\u6ce8",field:"remarks",placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:21},sm:{span:4}},wrapperCol:{xs:{span:21},sm:{span:20}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{field:"otherInput",type:"string",label:"\u5df2\u4e0a\u62a5\u3001\u6295\u8d44\u4e8b\u4e1a\u90e8\u767b\u5f55\u3001\u5176\u4ed6\u9879\u4e0d\u53ef\u7f16\u8f91",hide:!0,initialValue:function(e){return"\u8be6\u60c5"===e.Pstate.drawerDetailTitle?"noEdit":"\u7f16\u8f91"===e.Pstate.drawerDetailTitle?"1"===e.clickCb.selectedRows[0].statusId?"noEdit":"canEdit":"1"===e.props.loginAndLogoutInfo.loginInfo.userInfo.ext1?"noEdit":"canEdit"}},{field:"jurisdiction",type:"string",label:"\u6295\u8d44\u4e8b\u4e1a\u90e8\u6743\u9650",hide:!0,initialValue:function(e){var a=e.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;return"\u8be6\u60c5"===e.Pstate.drawerDetailTitle?a="99":"2"!==a&&"4"!==a||(a="99"),a}},{type:"component",field:"diyGSXX",Component:function(e){return m.a.createElement("div",{style:{width:"100%",backgroundColor:"#eeeded",height:"32px",lineHeight:"32px",borderRadius:"2px",paddingLeft:"10px"}},"\u6295\u8d44\u4e8b\u4e1a\u90e8\u4e0a\u4f20\u5185\u5bb9")}},{type:"radio",label:"\u4e00\u516c\u5c40\u96c6\u56e2\u6279\u590d",field:"juId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],condition:[{regex:{jurisdiction:"99"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"juFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({juFileList:[]})},200))},span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{jurisdiction:"99"},action:"disabled"}]},{type:"radio",label:"\u4e2d\u56fd\u4ea4\u5efa\u6279\u590d",field:"chinaId",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}],condition:[{regex:{jurisdiction:"99"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"chinaFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({chinaFileList:[]})},200))},span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{jurisdiction:"99"},action:"disabled"}]},{field:"registerDate",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:function(){return new Date}},{field:"registrant",type:"string",label:"\u767b\u8bb0\u7528\u6237",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:function(e){return e.loginAndLogoutInfo.loginInfo.userInfo.realName}}]}},{field:"form3",name:"qnnForm",title:"\u5408\u540c\u6761\u4ef6",content:{fetchConfig:function(e){return e.clickCb.rowData?{apiName:"getZjTzSizeControlRecordDetails",otherParams:{sizeControlRecordId:e.clickCb.rowData.sizeControlRecordId}}:e.clickCb.selectedRows.length>0?{apiName:"getZjTzSizeControlRecordDetails",otherParams:{sizeControlRecordId:e.clickCb.selectedRows[0].sizeControlRecordId}}:{}},formConfig:[{field:"sizeControlId",type:"string",initialValue:t,hide:!0},{field:"sizeControlRecordId",type:"string",hide:!0},{type:"string",label:"\u4e3b\u952eID",field:"contractConditionId",hide:!0},{type:"string",label:"\u9879\u76ee\u540d\u79f0",field:"projectName",placeholder:"\u8bf7\u8f93\u5165",required:!0,addDisabled:!0,editDisabled:!0,span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:l},{type:"string",label:"",hide:!0,field:"projectId",placeholder:"\u8bf7\u8f93\u5165",initialValue:i},{field:"registerDate1",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:function(){return new Date}},{field:"registrant1",type:"string",label:"\u767b\u8bb0\u7528\u6237",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},initialValue:function(e){return e.loginAndLogoutInfo.loginInfo.userInfo.realName}},{type:"string",label:"\u6295\u8d44\u6536\u76ca\u6a21\u5f0f",required:!0,field:"investId",initialValue:d,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"number",label:"\u4e00\u516c\u5c40\u96c6\u56e2\u80a1\u6bd4",required:!0,initialValue:p,field:"juShare",placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"select",label:"\u4e00\u516c\u5c40\u96c6\u56e2\u63a7\u5236\u6027\u5730\u4f4d",field:"juId1",placeholder:"\u8bf7\u8f93\u5165",required:!0,span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"yiGongJuJiTuanKongZhiXingDiWei"}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"select",label:"\u603b\u627f\u5305\u7ed3\u7b97\u6a21\u5f0f",field:"zcbId",required:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"ZongChengBaoJieSuanMoShi"}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"number",label:"\u65bd\u5de5\u603b\u627f\u5305\u6bd4\u4f8b",field:"zcbShare",initialValue:r,required:!0,placeholder:"\u8bf7\u8f93\u5165",span:12,formItemLayout:{labelCol:{xs:{span:20},sm:{span:8}},wrapperCol:{xs:{span:20},sm:{span:16}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"textarea",label:"\u8bbe\u8ba1\u7ba1\u7406\u60c5\u51b5",field:"ext1",required:!0,placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:20},sm:{span:4}},wrapperCol:{xs:{span:20},sm:{span:20}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"textarea",label:"\u5408\u540c\u5bf9\u6295\u8d44\u89c4\u6a21\u63a7\u5236\u7684\u8981\u6c42",field:"ext2",placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:20},sm:{span:4}},wrapperCol:{xs:{span:20},sm:{span:20}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{type:"textarea",label:"\u8bbe\u8ba1\u53d8\u66f4\u7279\u6b8a\u6761\u6b3e",field:"ext3",placeholder:"\u8bf7\u8f93\u5165",formItemLayout:{labelCol:{xs:{span:20},sm:{span:4}},wrapperCol:{xs:{span:20},sm:{span:20}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]},{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u89c4\u6a21\u63a7\u5236"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(a,t){a&&a[0]&&a[0].name&&("rar"===a[0].name.split(".")[1]||"zip"===a[0].name.split(".")[1]||"7z"===a[0].name.split(".")[1])&&(o.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({zjTzFileList:[]})},200))},formItemLayout:{labelCol:{xs:{span:20},sm:{span:4}},wrapperCol:{xs:{span:20},sm:{span:20}}},condition:[{regex:{otherInput:"noEdit"},action:"disabled"}]}]}}]})))}}]),a}(c.Component);a.default=w}}]);