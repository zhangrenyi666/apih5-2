(window.webpackJsonp=window.webpackJsonp||[]).push([[205],{"QJJ+":function(e,t,a){"use strict";a.r(t);a("8Qi2"),a("IdsT"),a("l9AF"),a("pBsb"),a("3fBH");var n=a("g2mn"),i=(a("fx2i"),a("cZi0")),o=(a("9TUk"),a("46ec")),r=(a("kkQI"),a("6T3l")),l=a("UpC8"),d=a("Id3l"),s=a("RNsw"),c=a("OR/T"),g=a("bn5B"),h=a("PnTt"),u=(a("w9C0"),a("XW+2")),f=a("r0ML"),p=a.n(f),b=a("U8v4"),m=a("6K4T"),w=a.n(m),I=a("vcLm"),k=a("2Jyx"),F=u.a.confirm,M=function(e){function t(e){var a;return Object(d.a)(this,t),(a=Object(c.a)(this,Object(g.a)(t).call(this,e))).onChangeRadio=function(e,t,n){for(var i=a.tableGM.form.getFieldsValue().zjTzPartManageList,o=0;o<i.length;o++)t.partManageId===i[o].partManageId&&n.target.checked?i[o].bidPartId="1":i[o].bidPartId="0";a.tableGM.qnnSetState({data:i})},a.onChangeCheckBox=function(e,t,n){for(var i=a.tableGM.form.getFieldsValue().zjTzPartManageList,o=0;o<i.length;o++)t.partManageId===i[o].partManageId&&(n.target.checked?i[o].buId="1":i[o].buId="0");a.tableGM.qnnSetState({data:i})},a.state={actionBtnsVal:[],designFlowId:e.match.params.designFlowId||"",loadingOutSend:!1,partManageId:"",fujianLook:[],rowDataList:[]},a}return Object(h.a)(t,e),Object(s.a)(t,[{key:"componentDidMount",value:function(){var e=this,t=this.props,a=t.routerInfo.routeData[t.routerInfo.curKey];t.myFetch("getSysMenuBtn",{menuParentId:a._value,tableField:"projectInfo"}).then(function(t){var a=t.success,n=t.data;t.message;a&&e.setState({actionBtnsVal:n})})}},{key:"render",value:function(){var e=this,t=this.state,a=t.designFlowId,d=t.actionBtnsVal,s=this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;return p.a.createElement("div",{className:w.a.root},p.a.createElement(n.a,{spinning:this.state.loadingOutSend},p.a.createElement(I.a,Object(l.a)({},this.props,{fetchConfig:{apiName:"getZjTzPartManageList",otherParams:{designFlowId:a}},upload:this.props.myUpload,fetch:this.props.myFetch,wrappedComponentRef:function(t){e.tableGM=t},headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},formConfig:[{label:"\u4e3b\u952eid",field:"codePid",hide:!0},{label:"\u5916\u5c42\u4e3b\u952eid",field:"designFlowId",hide:!0},{type:"qnnTable",field:"zjTzPartManageList",incToForm:!0,qnnTableConfig:{actionBtnsPosition:"top",antd:{rowKey:"partManageId",size:"small",scroll:{y:.65*document.documentElement.clientHeight}},drawerConfig:{width:"1000px"},limit:999,curPage:1,paginationConfig:!1,firstRowIsSearch:!1,isShowRowSelect:!0,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}},componentsKey:{HasTicketMoneyForm:function(t){return p.a.createElement(k.a,Object(l.a)({refreshOut:e.tableGM.refresh},t,e.props,{designFlowId:a}))}},formConfig:[{isInTable:!1,form:{field:"partManageId",type:"string",hide:!0}},{isInTable:!1,form:{field:"designFlowId",type:"string",initialValue:a,hide:!0}},{table:{title:"\u5e8f\u53f7",dataIndex:"no",key:"no",width:60,align:"center",fixed:"left",render:function(e,t,a){return"white"===t.colorFlag?a+1:p.a.createElement(r.a,{style:{width:"100%",textAlign:"center"},color:t.colorFlag,key:a+1},a+1)}},isInForm:!1,form:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{table:{title:"\u8bbe\u8ba1\u73af\u8282\u540d\u79f0",onClick:"detail",dataIndex:"designPartName",key:"designPartName",tdEdit:!0,fieldsConfig:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0,disabled:function(e){var t=e.record;return"1"===s||"1"===t.lockFlag}}},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{table:{title:"\u4e2d\u6807\u65f6\u73af\u8282",dataIndex:"bidPartName",align:"center",width:120,key:"bidPartName",tdEdit:!1,render:function(t,a){return"1"===s?"0"===a.bidPartId?p.a.createElement(o.a,{disabled:!0,checked:!1}):p.a.createElement(o.a,{disabled:!0,checked:!0}):"1"===a.lockFlag?"0"===a.bidPartId?p.a.createElement(o.a,{disabled:!0,checked:!1}):p.a.createElement(o.a,{disabled:!0,checked:!0}):"0"===a.bidPartId?p.a.createElement(o.a,{checked:!1,onChange:e.onChangeRadio.bind(e,t,a)}):p.a.createElement(o.a,{checked:!0,onChange:e.onChangeRadio.bind(e,t,a)})}},isInForm:!1},{table:{title:"\u91cd\u70b9\u73af\u8282",dataIndex:"buName",key:"buName",width:120,align:"center",tdEdit:!1,render:function(t,a){return"1"===a.lockFlag?"0"===a.buId?p.a.createElement(o.a,{disabled:!0,checked:!1}):p.a.createElement(o.a,{disabled:!0,checked:!0}):"0"===a.buId?p.a.createElement(o.a,{checked:!1,onChange:e.onChangeCheckBox.bind(e,t,a)}):p.a.createElement(o.a,{checked:!0,onChange:e.onChangeCheckBox.bind(e,t,a)})}},isInForm:!1},{table:{title:"\u8ba1\u5212\u5b8c\u6210\u65e5\u671f",dataIndex:"planDate",key:"planDate",tdEdit:!0,width:140,format:"YYYY-MM-DD",fieldsConfig:{field:"planDate",type:"date",label:"\u521b\u5efa\u65e5\u671f",disabled:function(e){var t=e.record;return"1"===s||"1"===t.lockFlag}}},form:{type:"date",addDisabled:!0,editDisabled:!0,field:"planDate",placeholder:"\u8bf7\u9009\u62e9"}},{table:{title:"\u5b9e\u9645\u5b8c\u6210\u65e5\u671f",format:"YYYY-MM-DD",dataIndex:"actualDate",width:140,key:"actualDate",tdEdit:!0},form:{type:"date",field:"actualDate",placeholder:"\u8bf7\u9009\u62e9"}},{table:{title:"\u9501\u5b9a\u72b6\u6001",dataIndex:"lockFlag",width:100,key:"lockFlag",render:function(e){return"0"===e?"\u672a\u9501\u5b9a":"\u9501\u5b9a"}},isInForm:!1},{table:{title:"\u8bc1\u660e\u6750\u6599\u9644\u4ef6",dataIndex:"fileFlag",width:140,key:"fileFlag",tdEdit:!0},form:{field:"zjTzFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u8bbe\u8ba1\u6d41\u7a0b\u7ba1\u7406"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",onChange:function(t,a){t&&t[0]&&t[0].name&&("rar"===t[0].name.split(".")[1]||"zip"===t[0].name.split(".")[1]||"7z"===t[0].name.split(".")[1])&&(i.b.warn("\u4e0d\u5141\u8bb8\u4e0a\u4f20rar\u3001zip\u30017z\u683c\u5f0f\u7684\u6587\u4ef6\uff01"),setTimeout(function(){e.table.qnnForm.form.setFieldsValue({zjTzFileList:[]})},200))}}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:80,btns:[{name:"crDetail",render:function(e){return"<a>\u63d2\u5165</a>"},onClick:function(t){e.props.myFetch("insertZjTzPartManage",{partManageId:t.rowData.partManageId}).then(function(t){var a=t.success,n=t.message;a?(i.b.success(n),e.tableGM.refresh()):(i.b.error(n),e.tableGM.refresh())})}}]}}],method:{save:function(){for(var t="unOk",a=e.tableGM.form.getFieldsValue(),n=e.tableGM.form.getFieldsValue().zjTzPartManageList,o=[],r=0;r<n.length;r++)n[r].actualDate&&n[r].zjTzFileList.length<=0&&o.push(n[r]),"1"===n[r].buId&&(t="ok");o.length>0?i.b.warn("\u8bf7\u4e0a\u4f20\u9644\u4ef6"):"unOk"===t?i.b.warn("\u8bf7\u52fe\u9009\u91cd\u70b9\u73af\u8282\uff01"):(e.setState({loadingOutSend:!0}),e.props.myFetch("saveZjTzPartManageAllList",a).then(function(t){var a=t.success;t.data,t.message;a?(i.b.success("\u4fdd\u5b58\u6210\u529f"),e.setState({loadingOutSend:!1}),e.tableGM.refresh()):i.b.error("\u4fdd\u5b58\u5931\u8d25")}))},goBack:function(t){var a=e.props.myPublic.appInfo.mainModule;e.props.dispatch(Object(b.push)("".concat(a,"DesignProcessManagement")))},lockClick:function(t){var a=e.props.myFetch,n=[],o=[],r="",l="";if(t.selectedRows.length>0){for(var d=0;d<t.selectedRows.length;d++)"0"===t.selectedRows[d].lockFlag?n.push(t.selectedRows[d]):o.push(t.selectedRows[d]);n.length>0&&(r="\u786e\u5b9a\u9501\u5b9a\u4e48\uff1f",l="batchLockUpdateZjTzPartManage"),o.length>0&&(r="\u786e\u5b9a\u89e3\u9501\u4e48\uff1f",l="batchClearUpdateZjTzPartManage"),t.selectedRows.length===n.length||t.selectedRows.length===o.length?F({title:r,okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a(l,t.selectedRows).then(function(t){var a=t.success,n=t.message;a?(i.b.success(n),e.tableGM.refresh()):i.b.error(n)})}}):i.b.warn("\u53ea\u80fd\u9009\u62e9\u76f8\u540c\u7c7b\u578b\u6570\u636e!")}else i.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")},delClick:function(t){var a=e.props.myFetch;t.selectedRows.length>0?1===t.selectedRows.length?"0"===t.selectedRows[0].lockFlag?F({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchDeleteUpdateZjTzPartManage",t.selectedRows).then(function(t){var a=t.success,n=t.message;a?(i.b.success(n),e.tableGM.refresh()):i.b.error(n)})}}):i.b.warn("\u6570\u636e\u5df2\u9501\u5b9a\uff0c\u4e0d\u80fd\u5220\u9664!"):i.b.warn("\u53ea\u80fd\u9009\u62e9\u4e00\u6761\u6570\u636e!"):i.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")}},actionBtns:d}}],btns:[]}))))}}]),t}(f.Component);t.default=M}}]);