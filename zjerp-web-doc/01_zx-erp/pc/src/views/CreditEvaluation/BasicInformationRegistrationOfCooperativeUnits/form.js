import React, {
  Component
} from "react";
import {
  Form
} from "../../modules/work-flow";
let config = {
  editDocCdnAddress: window.configs.ntkoAddress,
workFlowConfig: {
    title: ["协作单位准入评审表", "", ""],
    // apiNameByAdd: "addZxSkMonthPur",
    apiNameByAdd: "updateZxSkMonthPur",
    apiNameByUpdate: "updateZxSkMonthPur",
    apiNameByGet: "getZxSkMonthPurDetails",
    // apiTitle: "setZjkOaFlowTitle",//???
    flowId: "zxskmonthPurWorkId",
    todo: "TodoHasTo",
    hasTodo: "TodoHasToq",

  },
  formTailLayout: {
    wrapperCol: {
      sm: {
        span: 12,
        offset: 12
      }
    }
  },
  //qnn-form配置
  //在线编辑文档  使用此功能记住需要在cdn.js中引入ntkoofficecontrol.min.js
  //是否启用在线文档编辑、新增功能 默认false
  isHaveDoc: true,
  docFieldLable: "公文正文",
  docFieldName: "redFileList",
  docFieldIsRequired: false,
  docFormFormItemLayout: {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 3 }
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 21 }
    }
  },
  //最大创建文档的数量 默认99999999
  docMaxNumber: 99999999
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {}
  }
  render() {
    const {
      isInQnnTable, myPublic: { domain }, flowData
    } = this.props;
    return (<div style={
      {
        height: isInQnnTable ? "" : "100vh"
      }
    } >
      <Form
        {...this.props}
        {...config}
        {...this.props.workFlowConfig}
        {...config.workFlowConfig}
        //红头模板 默认[]
        //注意 id必須是1開頭的數字** id必须是number类型  最小值11  
        redModels={[
          {
            id: 11, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
            name: "函", //菜单按钮名字
            url: domain + "red/函.doc"
          },
          {
            id: 12, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
            name: "行政(上行)", //菜单按钮名字
            url: domain + "red/行政(上行).doc"
          },
          {
            id: 13, //用于文档页面菜单按钮排序和点击后区使用哪个模板使用
            name: "行政(下行)", //菜单按钮名字
            url: domain + "red/行政(下行).doc"
          }
        ]}
        formConfig={[
          {
            type: "string",
            label: "workId",
            field: "workId",
            hide: true,
            initialValue: function (obj) {
              return obj.match.params["workId"];
            }
          },
          {
            type: "string",
            label: "发文id",
            field: "id",
            initialValue: flowData && flowData.id ? flowData.id : '',
            hide: true
          },
          {
            type: "string",
            label: "组织机构代码证",
            qnnDisabled: true,
            initialValue: flowData && flowData.projectNumber ? flowData.projectNumber : '',
            field: "projectNumber",
            placeholder: "请输入",
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            label: "企业名称",
            required: true,
            qnnDisabled: true,
            initialValue: flowData && flowData.projectID ? flowData.projectID : '',
            field: 'projectID',
            type: 'select',
            optionConfig: {
              label: 'orgName',
              value: 'iecmOrgID'
            },
            fetchConfig: {
              apiName: 'getZxEqIecmOrgList'
            },
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            type: "date",
            label: "注册资本金(元)",
            required: true,
            qnnDisabled: true,
            field: "createDate",
            initialValue: flowData && flowData.createDate ? flowData.createDate : '',
            placeholder: "请输入",
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            type: "string",
            label: "纳税人类别",
            qnnDisabled: true,
            initialValue: flowData && flowData.aurhorizedPersonnel ? flowData.aurhorizedPersonnel : '',
            field: "aurhorizedPersonnel",
            placeholder: "请输入",
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            type: "string",
            label: "法定代表人",
            qnnDisabled: true,
            initialValue: flowData && flowData.aurhorizedPersonnel ? flowData.aurhorizedPersonnel : '',
            field: "aurhorizedPersonnel",
            placeholder: "请输入",
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            type: "phone",
            label: "法定代表人电话",
            qnnDisabled: true,
            initialValue: flowData && flowData.aurhorizedPersonnel ? flowData.aurhorizedPersonnel : '',
            field: "aurhorizedPersonnel",
            placeholder: "请输入",
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          {
            label: '企业详细地址',
            type: 'textarea',
            qnnDisabled: true,
            initialValue: flowData && flowData.remark ? flowData.remark : '',
            field: 'remark',
            autoSize: {
              minRows: 1,
              maxRows: 3
            },
            span: 12,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 6 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 18 }
              }
            },
          },
          // 信息查看没加
          
          {
            label: '公文附件',
            field: 'fileList',//???
            type: 'files',
            required:true,
            initialValue: flowData && flowData.fileList ? flowData.fileList : '',
            onPreview: "bind:_docPre",
            fetchConfig: {
              apiName:'upload',
            },
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
          },
          {
            type: "textarea",
            label: "发起人意见",
            field: "opinionField1",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "施工科意见",
            field: "opinionField2",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "经营科意见",
            field: "opinionField3",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "技术质量科意见",
            field: "opinionField3",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "安全科意见",
            field: "opinionField4",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "物设科意见",
            field: "opinionField5",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "财务科意见",
            field: "opinionField6",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "人力资源科意见",
            field: "opinionField7",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "法律部门意见",
            field: "opinionField8",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          },
          {
            type: "textarea",
            label: "主管领导审核",
            field: "opinionField9",
            opinionField: true,
            formItemLayout: {
              labelCol: {
                xs: { span: 24 },
                sm: { span: 3 }
              },
              wrapperCol: {
                xs: { span: 24 },
                sm: { span: 21 }
              }
            },
            addShow: false
          }
        ]}
        openFlowDataParms={(flowData, props) => {
          return flowData;
        }}
        actionParamsFormat={(params, props) => {
          // let list = eval('(' + params.apiBody + ')');
          let list = JSON.parse(params.apiData);
          list.zxSkMonthPurItemList = flowData.zxSkMonthPurItemList;
          params.apiBody = JSON.stringify(list);
          return params
        }}
      />
    </div>
    );
  }
}
export default index;