/*

    配置非qnn-form和qnn-table組件配置
    均为特殊配置

    内置关键词  
    selectList: "gwData",
    treeData: "gData",

*/
var language = window.configs.language || "zh_CN";
var isEn = language === "en_US"; //是否为英语

var lanObj = {
  edit: isEn ? "Edit" : "编辑",
  rename: isEn ? "Rename" : "重命名",
  goBack: isEn ? "go back" : "返回",
  confirm: isEn ? "Confirm" : "确认",
  cancel: isEn ? "Cancel" : "取消",
  add: isEn ? "Add" : "新增",
  save: isEn ? "Save" : "保存",
  placeholder: isEn ? "please enter..." : "请输入...",
  delete: isEn ? "Delete" : "批量删除",
  "无结果": isEn ? "no data" : "无结果",
  "保存并继续添加": isEn ? "Save and continue to add" : "保存并继续添加",
  "点击或者拖动上传": isEn ? "Click or drag to upload" : "点击或者拖动上传",
  "姓名": isEn ? "name" : "姓名",
  "账号": isEn ? "Account" : "账号",
  "手机": isEn ? "mobile" : "手机",
  "部门": isEn ? "department" : "部门",
  "岗位": isEn ? "post" : "岗位",
  "邮箱": isEn ? "email" : "邮箱",
  "证件类型": isEn ? "certificate type" : "证件类型",
  "证件编号": isEn ? "certificate number" : "证件编号",
  "性别": isEn ? "gender" : "性别",
  "出生日期": isEn ? "Birthdate" : "出生日期",
  "年龄": isEn ? "age" : "年龄",
  "国籍": isEn ? "nationality" : "国籍",
  "民族": isEn ? "National" : "民族",
  "职称": isEn ? "duty" : "职称",
  "最高职级": isEn ? "highest rank" : "最高职级",
  "用工类型": isEn ? "Types of employment" : "用工类型",
  "身份": isEn ? "other" : "身份",
  "排序": isEn ? "sort" : "排序"
};
window.contactsPage = {
  fetchConfig: {
    apiName: "getSysUserListByBg",
    otherParams: "bind:listOtherParams"
  },
  antd: {
    rowKey: "userKey",
    size: "small"
  },
  actionBtns: [{
    field: 'add',
    name: 'add',
    //【add, addRow,  del, edit, detail, Component, form】
    icon: 'plus',
    type: 'primary',
    label: '新增',
    drawerTitle: '新增',
    formBtns: [{
      name: 'cancel',
      //关闭右边抽屉
      type: 'dashed',
      //类型  默认 primary
      label: '取消',
      field: 'addCancelBtn'
    }, {
      name: 'submit',
      //内置add del
      type: 'primary',
      //类型  默认 primary
      label: '提交',
      //提交数据并且关闭右边抽屉
      //格式化数据
      //paramsFormat:({params, props, ...})=>{return {...}},
      //控制是否提交表单
      //isCanSubmit: ({params, props, ...}, callback)=>callback(true), //callback(true) || callback(false)
      fetchConfig: {
        //ajax配置
        apiName: 'addSysUserInfoByBg',
        otherParams: "bind:addOtherParams",
        //删除参数中的name、age字段
        delParams: []
      },
      //自定义按钮key值 必须配置 否则可能按钮点击后不会置为loading状态可能会导致重复提交问题
      field: 'addSubmitBtn'
    }]
  }, {
    field: 'unLock',
    name: 'unLock',
    label: '用户解锁',
    onClick: "bind:unLock",
    disabled: "bind:_actionBtnNoSelected"
  }, {
    field: 'resetBtn',
    name: 'resetPwd',
    //【add, addRow,  del, edit, detail, Component, form】 
    // type: 'danger',
    label: '重置密码',
    onClick: "bind:resetPwd",
    disabled: "bind:_actionBtnNoSelected"
  }, {
    field: 'delBtn',
    //willExecute:()=>void //点击前
    name: 'delByDiy',
    //【add, addRow,  del, edit, detail, Component, form】
    icon: 'delete',
    type: 'danger',
    label: '删除',
    onClick: "bind:delByDiy",
    disabled: "bind:_actionBtnNoSelected"
  }],
  formConfig: [{
    isInTable: false,
    form: {
      hide: true,
      type: "string",
      required: true,
      field: "userKey"
    }
  }, {
    isInTable: false,
    form: {
      hide: true,
      initialValue: "1",
      type: "string",
      required: true,
      field: "userType"
    }
  }, {
    isInTable: false,
    form: {
      hide: true,
      initialValue: "1",
      type: "string",
      required: true,
      field: "userStatus"
    }
  }, {
    table: {
      fixed: "left",
      title: lanObj["姓名"],
      dataIndex: "realName",
      width: 80,
      onClick: "detail",
      filter: true // render: "realNameRender"

    },
    form: {
      type: "string",
      required: true
    }
  }, {
    table: {
      width: 110,
      title: lanObj["账号"],
      dataIndex: "userId",
      filter: true
    },
    form: {
      type: "string",
      required: true
    }
  }, {
    table: {
      width: 110,
      title: lanObj["手机"],
      dataIndex: "mobile",
      filter: true
    },
    form: {
      type: "phone",
      required: true
    }
  }, // {
  //     table: {
  //         width: 170,
  //         title: lanObj["邮箱"],
  //         dataIndex: "email",
  //         filter: true,
  //     },
  //     form: {
  //         type: "email"
  //     }
  // },
  {
    table: {
      width: 80,
      title: lanObj["性别"],
      dataIndex: "gender",
      filter: true,
      render: "bind:gender"
    },
    form: {
      field: "gender",
      type: "radio",
      optionData: [{
        label: "男",
        value: "1"
      }, {
        label: "女",
        value: "2"
      }]
    }
  }, // {
  //     table: {
  //         width: 80,
  //         title: lanObj["年龄"],
  //         dataIndex: "age",
  //         filter: true,
  //     },
  //     form: {
  //         type: "number"
  //     }
  // },
  // {
  //     table: {
  //         title: lanObj["国籍"],
  //         dataIndex: "nationnalityName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "nationnality",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "nationnality"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         title: lanObj["民族"],
  //         dataIndex: "nationName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "nation",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "nation"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         width: 120,
  //         title: lanObj["出生日期"],
  //         dataIndex: "birthday",
  //         format: "YYYY-MM-DD",
  //         filter: true,
  //     },
  //     form: {
  //         type: "date"
  //     }
  // },
  // {
  //     table: {
  //         width: 120,
  //         title: lanObj["证件类型"],
  //         dataIndex: "certTypeName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "certType",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "certType"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         width: 120,
  //         title: lanObj["证件编号"],
  //         dataIndex: "identityCard",
  //         filter: true,
  //     },
  //     form: {
  //         type: "string"
  //     }
  // },
  // {
  //     table: {
  //         title: lanObj["岗位"],
  //         width: 80,
  //         dataIndex: "jobTypeName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "jobType",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "jobType"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         title: lanObj["职称"],
  //         dataIndex: "postionsName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "postions",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "postions"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         width: 120,
  //         title: lanObj["最高职级"],
  //         dataIndex: "positiongradeName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "positiongrade",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "positiongrade"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  // {
  //     table: {
  //         width: 120,
  //         title: lanObj["用工类型"],
  //         dataIndex: "empTypeName",
  //         filter: true,
  //     },
  //     form: {
  //         field: "empType",
  //         type: "select",
  //         fetchConfig: {
  //             apiName: "getBaseCodeSelect",
  //             otherParams: {
  //                 itemId: "empType"
  //             }
  //         },
  //         optionConfig: {
  //             label: "itemName",
  //             value: "itemId"
  //         }
  //     }
  // },
  {
    table: {
      width: 500,
      title: lanObj["部门"],
      dataIndex: "sysDepartmentShowList",
      render: "bind:sysDepartmentShowListRender",
      filter: true
    },
    form: {
      field: "sysDepartmentList",
      type: "treeSelect",
      treeSelectOption: {
        //配置参照oa拉人组件配置
        fetchConfig: {
          // apiName: 'getSysDepartmentCurrentTree',
          apiName: 'getSysDepartmentTree',
          otherParams: {},
          // paramsKey: 'departmentParentId'
        }
      }
    }
  }, {
    // isInTable:false,
    table: {
      width: 100,
      title: lanObj["身份"],
      dataIndex: "ext1Name",
      filter: true
    },
    form: {
      field: "ext1",
      type: "select",
      fetchConfig: {
        apiName: "getBaseCodeSelect",
        otherParams: {
          itemId: "ext1"
        }
      },
      optionConfig: {
        label: "itemName",
        value: "itemId"
      }
    }
  }, {
    table: {
      width: 110,
      title: lanObj["排序"],
      dataIndex: "sort",
      filter: true
    },
    form: {
      type: "number"
    }
  }, {
    isInForm: false,
    table: {
      showType: 'tile',
      //出来的样式 bubble（气泡）  tile（平铺） 默认bubble  （0.6.15版本中将该属性移动到table属性下，也可写到table同级）
      width: 80,
      align: "center",
      title: "操作",
      key: "action",
      //操作列名称
      fixed: 'right',
      //固定到右边
      btns: [{
        label: "编辑",
        name: 'edit',
        formBtns: [{
          name: 'cancel',
          //关闭右边抽屉
          type: 'dashed',
          //类型  默认 primary
          label: '取消'
        }, {
          //自定义按钮key值 必须配置
          field: "addCancelBtn",
          name: 'submit',
          //内置add del
          type: 'primary',
          //类型  默认 primary
          label: '保存',
          //提交数据并且关闭右边抽屉
          fetchConfig: {
            //ajax配置  ---可为func
            apiName: 'updateSysUserInfoByBg',
            otherParams: "bind:addOtherParams"
          }
        }]
      }]
    }
  }]
};