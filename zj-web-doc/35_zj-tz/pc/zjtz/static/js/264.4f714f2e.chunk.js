(window.webpackJsonp=window.webpackJsonp||[]).push([[264],{kZGT:function(e,a,t){"use strict";t.r(a);t("9U97"),t("fx2i");var o=t("cZi0"),l=t("UpC8"),n=t("Id3l"),r=t("RNsw"),s=t("OR/T"),i=t("bn5B"),p=t("PnTt"),d=(t("w9C0"),t("XW+2")),m=t("r0ML"),c=t.n(m),f=t("6eYn"),b=t("U8v4"),u=t("bNEa"),h=t.n(u),I=t("eBi6"),w=d.a.confirm,C={antd:{rowKey:function(e){return e.threeShareholderId},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!0,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},g=function(e){function a(e){var t;return Object(n.a)(this,a),(t=Object(s.a)(this,Object(i.a)(a).call(this,e))).handleCancelSend=function(){t.setState({visibleSend:!1,loadingSend:!1})},t.state={threeShareholderId:"",proNameId:e.loginAndLogoutInfo.loginInfo.userInfo.curCompany?e.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId:""},t}return Object(p.a)(a,e),Object(r.a)(a,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var e=this,a=this.state.proNameId;return c.a.createElement("div",{className:h.a.root},c.a.createElement(f.a,Object(l.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(a){e.table=a}},C,{fetchConfig:{apiName:"getZjTzThreeShareholderList",otherParams:{projectId:a}},formConfig:[{isInTable:!1,form:{field:"threeShareholderId",type:"string",hide:!0}},{table:{title:"\u9879\u76ee\u540d\u79f0",filter:!0,width:300,dataIndex:"projectId",key:"projectId",type:"select",fieldsConfig:{field:"projectId",type:"select",showSearch:!0,optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"}}},form:{field:"projectId",type:"select",showSearch:!0,addDisabled:!0,disabled:!0,editDisabled:!0,initialValue:function(){return a},optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},placeholder:"\u8bf7\u9009\u62e9",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{isInSearch:!1,table:{title:"\u671f\u6b21",onClick:"detail",width:160,dataIndex:"meetNumberName",key:"meetNumberName",fieldsConfig:{type:"string",field:"meetNumberName"}},isInForm:!1},{isInTable:!1,form:{label:"\u671f\u6b21",type:"year",required:!0,field:"yearDate",editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:6,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{isInTable:!1,form:{label:"\u5e74",field:"aa",spanForm:1,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:24}},wrapperCol:{xs:{span:0},sm:{span:0}}}}},{isInTable:!1,form:{label:"",type:"select",field:"meetNumberId",editDisabled:!0,required:!0,optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiQiCi"}},placeholder:"\u8bf7\u8f93\u5165",spanForm:5,formItemLayoutForm:{labelCol:{xs:{span:0},sm:{span:0}},wrapperCol:{xs:{span:24},sm:{span:24}}}}},{isInSearch:!1,table:{title:"\u4f1a\u8bae\u65f6\u95f4",filter:!0,format:"YYYY-MM-DD",dataIndex:"meetDate",key:"meetDate",fieldsConfig:{type:"rangeDate",field:"meetDateList"}},form:{type:"date",field:"meetDate",placeholder:"\u8bf7\u8f93\u5165",required:!0,spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u4f1a\u8bae\u7c7b\u578b",filter:!0,dataIndex:"meetTypeId",key:"meetTypeId",type:"select"},form:{label:"\u4f1a\u8bae\u7c7b\u578b",type:"select",field:"meetTypeId",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiLeiXing"}},spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u4f1a\u8bae\u8868\u51b3\u65b9\u5f0f",filter:!0,dataIndex:"meetVoteId",key:"meetVoteId",type:"select"},form:{label:"\u4f1a\u8bae\u8868\u51b3\u65b9\u5f0f",type:"select",field:"meetVoteId",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiBiaoJueFangShi"}},spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u539f\u4ef6\u662f\u5426\u5907\u6848",filter:!0,width:120,dataIndex:"originalId",key:"originalId",type:"select"},form:{type:"select",field:"originalId",optionData:[{label:"\u662f",value:"1"},{label:"\u5426",value:"0"}],spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u4f1a\u8bae\u5730\u70b9",width:150,tooltip:18,dataIndex:"meetPlace",key:"meetPlace"},form:{label:"\u4f1a\u8bae\u5730\u70b9",type:"textarea",field:"meetPlace",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{isInTable:!1,form:{label:"\u4f1a\u8bae\u901a\u77e5\u6587\u4ef6",field:"zjTzFileList1",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"}}},{isInTable:!1,form:{label:"\u8bae\u6848\u8d44\u6599",field:"zjTzFileList2",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"}}},{isInTable:!1,form:{label:"\u51b3\u8bae\u53ca\u4f1a\u8bae\u7eaa\u8981",field:"zjTzFileList3",type:"files",fetchConfig:{apiName:window.configs.domain+"upload"}}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:120,btns:[{name:"PolicyDetail",render:function(e){return"<a>\u660e\u7ec6</a>"},onClick:function(e){var a=e.props.myPublic.appInfo.mainModule,t=e.rowData,o=t.threeShareholderId,l=t.apih5FlowStatus,n="";n=""===l?"99":l,e.props.dispatch(Object(b.push)("".concat(a,"ThreeMeetingMangeDetail/").concat(o,"/").concat(n,"/",0)))}}]}}],componentsKey:{FlowByWorkPlan:function(a){e.table.clearSelectedRows();var t=a.selectedRows;return!t||!t.length||t.length>1?(a.btnCallbackFn.closeDrawer(),a.btnCallbackFn.msg.error("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e\uff01"),c.a.createElement("div",null)):""!=a.selectedRows[0].workId?(a.btnCallbackFn.closeDrawer(),a.btnCallbackFn.msg.error("\u5df2\u53d1\u8d77\u5ba1\u6279\u7684\u4e0d\u53ef\u518d\u53d1\u8d77\uff01"),c.a.createElement("div",null)):c.a.createElement(I.a,Object(l.a)({},a,{flowData:t[0]}))}},method:{addClick:function(e){"all"===a&&(e.btnCallbackFn.closeDrawer(),e.btnCallbackFn.clearSelectedRows(),o.b.warn("\u8bf7\u9009\u62e9\u53f3\u4e0a\u89d2\u9879\u76ee\uff01"))},editClick:function(a){e.table.clearSelectedRows(),"1"===a.selectedRows[0].apih5FlowStatus?(o.b.warn("\u5ba1\u6838\u4e2d\u7684\u6570\u636e\u4e0d\u80fd\u4fee\u6539\uff01"),a.btnCallbackFn.closeDrawer()):"2"===a.selectedRows[0].apih5FlowStatus&&(o.b.warn("\u5ba1\u6838\u7ed3\u675f\u7684\u6570\u636e\u4e0d\u80fd\u4fee\u6539\uff01"),a.btnCallbackFn.closeDrawer())},delClick:function(a){var t=a.props.myFetch,l=[];if(a.selectedRows.length>0){for(var n=0;n<a.selectedRows.length;n++)"1"!==a.selectedRows[n].apih5FlowStatus&&"2"!==a.selectedRows[n].apih5FlowStatus||l.push(a.selectedRows[n].apih5FlowStatus);l.length>0?(a.btnCallbackFn.closeDrawer(),o.b.warn("\u8bf7\u9009\u62e9\u672a\u5ba1\u6838\u7684\u6570\u636e!"),e.table.clearSelectedRows()):w({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){t("batchDeleteUpdateZjTzThreeShareholder",a.selectedRows).then(function(a){var t=a.success,l=a.message;t?(o.b.success(l),e.table.refresh(),e.table.clearSelectedRows()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var a=e.Pprops;return{menuParentId:a.routerInfo.routeData[a.routerInfo.curKey]._value,tableField:"projectInfo"}}}})))}}]),a}(m.Component);a.default=g}}]);