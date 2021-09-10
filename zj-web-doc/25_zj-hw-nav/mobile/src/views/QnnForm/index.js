import React, { Component } from "react";
import QnnForm from "../modules/qnn-table/qnn-form";
import { Form } from "antd";
// import QnnForm from "qnn-form";

const config = {
  formConfig: [
    //网址取值
    {
      type: "string",
      label: "隐藏字段",
      field: "id", //唯一的字段名 ***必传
      hide: true,
      disabled: true,
      required: true //是否必填
    },
    //两字
    {
      type: "string",
      label: "两字",
      field: "tow", //唯一的字段名 ***必传
      placeholder: "非编辑状态", //占位符
      disabled: true,
      required: true //是否必填
    },
    //网址取值
    {
      type: "string",
      label: "三个字",
      field: "three", //唯一的字段名 ***必传
      placeholder: "非编辑状态", //占位符
      disabled: true,
      required: true //是否必填
    },
    //网址取值
    {
      type: "string",
      label: "网址取值",
      field: "flag", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      isUrlParams: true,
      disabled: true,
      required: true //是否必填
    },
    //必填字段
    {
      type: "string",
      label: "必填字段",
      field: "required", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      required: true //是否必填
    },
    //普通字段
    {
      type: "string",
      label: "普通字段",
      field: "string", //唯一的字段名 ***必传
      placeholder: "请输入" //占位符
    },
    //数字类型
    {
      type: "number",
      label: "数字字段",
      field: "number", //唯一的字段名 ***必传
      placeholder: "请输入",
      max: 99, //最大值
      min: 20, //最大值
      precision: 2 //数值精度
    },
    //条件显隐例子
    {
      type: "string",
      label: "条件显隐",
      field: "condition", //唯一的字段名 ***必传
      placeholder: "数字字段为1将隐藏", //占位符
      // hide:true,
      //array类型
      //条件存在权重 下面条件满足上面条件将会失效
      //匹配规则为&&匹配
      //内置三种action【disabled, show, hide】
      condition: [
        {
          //条件
          regex: {
            //匹配规则 正则或者字符串
            number: 1
          },
          action: "hide" //disabled,  show,  hide, function(){}
        }
      ]
    },
    //条件显隐例子
    {
      type: "string",
      label: "显隐二",
      field: "condition2", //唯一的字段名 ***必传
      placeholder: "数字字段为1将隐藏", //占位符
      // hide:true,
      //array类型
      //条件存在权重 下面条件满足上面条件将会失效
      //匹配规则为&&匹配
      //内置三种action【disabled, show, hide】
      condition: [
        {
          //条件
          regex: {
            //匹配规则 正则或者字符串
            number: 1
          },
          action: "hide" //disabled,  show,  hide, function(){}
        }
      ]
    },
    //邮箱
    {
      type: "email",
      label: "邮箱字段",
      field: "email", //唯一的字段名 ***必传
      placeholder: "请输入"
    },
    //格式化显示
    // {
    //   type: "string",
    //   label: "格式化",
    //   field: "formatter", //唯一的字段名 ***必传
    //   placeholder: "请输入",
    //   // required: true,
    //   initialValue: 123456,
    //   max: 99, //最大值
    //   min: 20, //最大值
    //   precision: 2, //数值精度
    //   formatter: function(value) {
    //     return value + "$";
    //   } //格式化显示
    // },
    //密码
    {
      type: "password",
      label: "密码字段",
      field: "password", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      // required: true,//是否必填
      prefix: "lock", //前缀图标   [string]
      prefixStyle: { color: "rgba(0,0,0,.25)" } //前缀图标样式
    },
    //前后填充
    {
      type: "string",
      label: "前后填充",
      field: "prefix", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      prefix: "lock", //前缀图标   [string]
      prefixStyle: { color: "rgba(0,0,0,.25)" }, //前缀图标样式
      prefix: "user", //前缀图标   [string]
      suffix: "link" //后缀图标   [string]
    },
    //前文本填充
    {
      type: "string",
      label: "前文本填充",
      field: "addonBefore", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      addonBefore: "Http://" //前填充 [string]
    },
    {
      type: "string",
      label: "后文本填充",
      field: "addonAfter", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      addonAfter: ".com" //后填充  [string]
    },
    //前后文本填充
    // {
    //   type: "string",
    //   label: "前后文本填充",
    //   field: "addon", //唯一的字段名 ***必传
    //   placeholder: "请输入", //占位符
    //   addonBefore: "Http://", //前填充 [string]
    //   addonAfter: ".com" //后填充  [string]
    // },
    {
      type: "item",
      field: "item",
      label: "选项类型"
    },
    {
      label: "无限联动",
      type: "linkage",
      //   fetchConfig: {
      //     apiName: "getFlowNameSelectList"
      //   },
      optionConfig: {
        // 暂时只有模式为0有意义 所有子集默认optionConfig
        value: "apih5FlowId",
        label: "apih5FlowName",
        children: "children"
      },
      children: {
        //所有配置见qnn-form
        isInTable: false,
        form: {
          editDisabled: true,
          label: "一级联动",
          field: "linkage1",
          type: "select",
          placeholder: "请选择"
          // fetchConfig: {//model为1时有用
          //     apiName: 'getFlowNameSelectList',
          //     parentKey:'XXX'
          // },
        },
        children: {
          form: {
            label: "二级联动",
            placeholder: "请选择",
            field: "linkage2",
            type: "select",
            optionData: [
              {
                apih5FlowId: "111",
                apih5FlowName: "111"
              }
            ]
          },
          children: {
            form: {
              label: "三级联动",
              placeholder: "请输入",
              field: "linkage3",
              type: "string"
            },
            children: {
              form: {
                label: "四级联动",
                placeholder: "请输入",
                field: "linkage4",
                type: "string"
              }
            }
          }
        }
      }
    },
    {
      //普通选择框
      type: "select",
      label: "选择字段",
      field: "select", //唯一的字段名 ***必传
      placeholder: "请选择",
      disabled: true,
      initialValue: "01id,01orgId",
      // required: true,
      multiple: false, //是否开启多选功能 开启后自动开启搜索功能
      showSearch: false, //是否开启搜索功能 (移动端不建议开启)
      optionData: [
        //默认选项数据
        {
          name: "01name",
          id: "01id",
          orgId: "01orgId"
        },
        {
          name: "02",
          id: "02id",
          orgId: "02orgId"
        }
      ],
      optionConfig: {
        //下拉选项配置
        label: "name", //默认 label
        value: ["id", "orgId"] //最终的值使用逗号连接 默认值使用valueName 默认['value']
      }
    },
    {
      //dateTime yyyy-mm-dd hh:mm:ss
      type: "datetime",
      label: "日期时间",
      field: "datetime", //唯一的字段名 ***必传
      placeholder: "请选择",
      // required: true,
      initialValue: new Date(),
      disabled: true,
      is24: true //是否是24小时制 默认true
    },
    //date yyyy-mm-dd
    {
      type: "date",
      label: "日期选择",
      field: "date", //唯一的字段名 ***必传
      placeholder: "请选择",
      // required: true,
      format: "YYYY-MM-DD"
      // showTime: false, //不显示时间
    },
    //time yyyy-mm-dd
    {
      type: "time",
      label: "时间选择",
      field: "time", //唯一的字段名 ***必传
      placeholder: "请选择",
      // required: true,
      is24: true //是否是24小时制 默认true2
    },
    {
      //整数
      type: "integer",
      label: "整数字段",
      field: "integer", //唯一的字段名 ***必传
      placeholder: "请输入"
      // required: true,
    },

    //url
    {
      type: "url",
      label: "网址字段",
      field: "url", //唯一的字段名 ***必传
      placeholder: "请输入"
      // required: true,
    },

    //textarea类型
    {
      type: "textarea",
      label: "多行文本",
      field: "textarea", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      // required: true,//是否必填
      rows: 4 //行高 默认4
    },
    {
      //层叠联动
      type: "cascader",
      label: "层叠联动",
      field: "cascader", //唯一的字段名 ***必传
      placeholder: "请选择",
      // required: true,
      showSearch: false, //是否开启搜索功能 (移动端不建议开启)
      optionData: [
        //默认选项数据
        {
          name: "01name",
          id: "01id",
          children: [
            {
              name: "01name",
              id: "01id"
            }
          ]
        },
        {
          name: "02",
          id: "02id"
        }
      ],
      optionConfig: {
        //下拉选项配置
        label: "name", //默认 label
        value: "id", //
        children: "children"
      }
      // fetchConfig: {//配置后将会去请求下拉选项数据
      //     apiName: 'getSysDepartmentList',
      //     otherParams: {}
      // }
    },
    //树选择
    {
      label: "人员/部门",
      field: "treeSelect",
      type: "treeSelect",
      initialValue: [],
      treeSelectOption: {
        // fetchConfig: {//配置后将会去请求下拉选项数据
        //     apiName: 'getSysDepartmentUserAllTree',
        // }
        selectType: "0",
        treeData: {
          label: "测试1",
          value: "测试1",
          children: [
            {
              label: "测试2",
              value: "测试2"
            }
          ]
        }
      }
    },
    //files文件上传
    {
      type: "files",
      label: "文件上传",
      field: "files", //唯一的字段名 ***必传
      // required: true,//是否必填
      desc: "点击或者拖动上传", //默认 点击或者拖动上传
      subdesc: "只支持单个上传", //默认空
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
        // name:'123', //上传文件的name 默认空
      },
      accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
      max: 5 //最大上传数量
    },
    //图片上传
    {
      type: "images",
      label: "图片上传",
      field: "images", //唯一的字段名 ***必传
      // required: true,//是否必填
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
        // name:'123', //上传文件的name 默认空
      },
      accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
      max: 5 //最大上传数量
    },
    //可选相机的文件上传
    {
      type: "camera",
      label: "拍摄照片",
      field: "camera", //唯一的字段名 ***必传
      // required: true,//是否必填
      fetchConfig: {
        //配置后将会去请求下拉选项数据
        apiName: window.configs.domain + "upload"
        // name:'123', //上传文件的name 默认空
      },
      accept: "image/jpeg", //支持上传的类型 默认都支持  格式"image/gif, image/jpeg"
      max: 5 //最大上传数量
    },
    {
      type: "richText",
      label: "富文本描述",
      field: "richText", //唯一的字段名 ***必传
      // disabled:true,
      fetchConfig:{
        uploadUrl:window.configs.domain + 'upload'
      }
    },
    {
      //自定义组件
      type: "Component",
      // label: '自定义组件',
      field: "diy", //唯一的字段名 ***必传
      placeholder: "请输入", //占位符
      Component: obj => {
        return (
          <div
            style={{ width: "100%", border: "1px solid #ccc" }}
            onClick={() => {
              var history = obj.props.history;
              var push = history.push;
              push("0");
            }}
          >
            自定义组件
          </div>
        );
      }
    }
  ],
  btns: [
    {
      label: "重置",
      isValidate: false, //是否验证表单 默认true
      onClick: function(obj) {
        const resetFields = obj.props.form.resetFields;
        resetFields();
      }
    },
    {
      label: "提交",
      type: "primary",
      // isValidate: false,//是否验证表单 默认true
      onClick: function(obj) {
        console.log(obj);
      }
    }
  ]
};

class idnex extends Component {
  render() {
    return (
      <div>
        <QnnForm
          form={this.props.form} //使用QnnForm的页面必须使用rc-form插件包裹，并且将form传递给props
          fetch={this.props.myFetch} //必须返回一个promise
          //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          {...config}
        />
      </div>
    );
  }
}
export default Form.create()(idnex);
