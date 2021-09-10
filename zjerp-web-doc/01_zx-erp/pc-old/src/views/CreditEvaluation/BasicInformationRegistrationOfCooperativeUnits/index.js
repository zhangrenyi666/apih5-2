import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from '../../modules/qnn-table/qnn-form';
import { message as Msg, Modal } from 'antd';
import BasicInForm from './form';//先不做了
import ProgressCheck from './addFile';//先不做了
const config = {
  antd: {
    rowKey: function (row) {
      return row.zxCrCustomerInfoId
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1300px'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 10 }
    },
    wrapperCol: {
      xs: { span: 16 },
      sm: { span: 14 }
    }
  },
  isShowRowSelect: false
};
const configItem = {
  drawerConfig: {
    width: '1100px'
  },
  paginationConfig: false,
  actionBtnsPosition: "top",
  firstRowIsSearch: false,
  isShowRowSelect: true
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dataTab1: [],
      id: '',
      dataTab2: [],
      zxCrCustomerInfoDatumId: '',
      dataTab3: [],
      zxCrCustomerHonorId: '',
      dataTab4: [],
      zxCrCustomerInfoDireId: '',
      visible: false,
      exportData: null,
    }
  }
  componentDidMount() {

  }
  // tab1数据
  getTab1Data(val) {
    const { myFetch } = this.props;
    myFetch('getZxCrCustomerInfoQaList', { zxCrCustomerInfoId: val }).then(
      ({ data, success, message }) => {
        if (success) {
          this.setState({
            dataTab1: data
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  // tab2数据
  getTab2Data(val) {
    const { myFetch } = this.props;
    myFetch('getZxCrCustomerInfoDatumList', { zxCrCustomerInfoId: val }).then(
      ({ data, success, message }) => {
        if (success) {
          this.setState({
            dataTab2: data
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  // tab3数据
  getTab3Data(val) {
    const { myFetch } = this.props;
    myFetch('getZxCrCustomerHonorList', { zxCrCustomerInfoId: val }).then(
      ({ data, success, message }) => {
        if (success) {
          // debugger
          this.setState({
            dataTab3: data
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  // tab4数据
  getTab4Data(val) {
    const { myFetch } = this.props;
    myFetch('getZxCrCustomerInfoDireList', { zxCrCustomerInfoId: val }).then(
      ({ data, success, message }) => {
        if (success) {
          this.setState({
            dataTab4: data
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  render() {
    const { visible, exportData } = this.state;
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCrCustomerInfoList'
          }}
          componentsKey={{
            BasicInForm: (obj) => {
              this.table.clearSelectedRows();
              //判断是否选中数据
              let selectedRows = obj.selectedRows;
              if (!selectedRows || !selectedRows.length || selectedRows.length > 1) {
                obj.btnCallbackFn.closeDrawer();
                obj.btnCallbackFn.msg.error('请选择一条数据！');
                return <div />
              }
              if (obj.selectedRows[0].apih5FlowStatus === "1" || obj.selectedRows[0].apih5FlowStatus === "2") {
                obj.btnCallbackFn.closeDrawer();
                obj.btnCallbackFn.msg.error('已发起审批的不可再发起！');
                return <div />
              }
              return <BasicInForm {...this.props} flowData={selectedRows[0]} />
            },
            ProgressCheck: (obj) => {
              return <ProgressCheck {...obj} flowData={obj.rowData} />
            }
          }}
          actionBtns={[
            // {
            //   Component: 'ProgressCheck',
            //   drawerTitle: "新增",
            //   icon: "plus",
            //   label: "新增",
            //   name: "Component",
            //   type: "primary"
            // },
            // {
            //   name: "edit",
            //   label: "修改",
            //   field: "edit",
            //   type: "primary",
            //   formBtns: [
            //     {
            //       name: 'cancel',
            //       type: 'dashed',
            //       label: '取消',
            //       hide: (obj) => {
            //         var index = obj.btnCallbackFn.getActiveKey();
            //         if (index === "0") {
            //           return false;
            //         } else {
            //           return true;
            //         }
            //       },
            //     },
            //     {
            //       name: 'diySubmit',
            //       field: 'diySubmit',
            //       type: 'primary',
            //       label: '保存',
            //       hide: (obj) => {
            //         var index = obj.btnCallbackFn.getActiveKey();
            //         if (index === "0") {
            //           return false;
            //         } else {
            //           return true;
            //         }
            //       },
            //       onClick: (obj) => {
            //         obj.btnCallbackFn.clearSelectedRows();
            //         obj.btnCallbackFn.setBtnsLoading('edit', 'diySubmit');
            //         this.props.myFetch('updateZxCrCustomerInfo', obj._formData).then(
            //           ({ data, success, message }) => {
            //             if (success) {
            //               obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
            //               Msg.success(message);
            //               obj.btnCallbackFn.refresh();
            //               obj.btnCallbackFn.setActiveKey('1');
            //             } else {
            //               obj.btnCallbackFn.setBtnsLoading('remove', 'diySubmit');
            //               Msg.error(message);
            //             }
            //           }
            //         );
            //       }
            //     }
            //   ]
            // },
            // {
            //   name: 'del',//内置add del
            //   icon: 'delete',//icon
            //   type: 'danger',//类型  默认 primary  [primary dashed danger]
            //   label: '删除',
            //   field: "del",
            //   fetchConfig: {//ajax配置
            //     apiName: 'batchDeleteUpdateZxCrCustomerInfo',
            //   },
            // },
            // {
            //   Component: "BasicInForm",
            //   disabled: "bind:_actionBtnNoSelected",
            //   drawerTitle: "评审申请",
            //   icon: "plus",
            //   label: "评审申请",
            //   name: "Component",
            //   type: "primary"
            // },
            // 复审
            // {
            //   name: 'diy',
            //   type: 'primary',
            //   label: '复审',
            //   disabled: "bind:_actionBtnNoSelected",
            //   onClick: (obj) => {

            //   }
            // },
            // 申请占号
            // {
            //   name: 'diy',
            //   type: 'primary',
            //   label: '申请占号',
            //   disabled: "bind:_actionBtnNoSelected",
            //   onClick: (obj) => {

            //   }
            // },
            // 提交
            // {
            //   name: 'diy',
            //   type: 'primary',
            //   label: '提交',
            //   disabled: "bind:_actionBtnNoSelected",
            //   onClick: (obj) => {

            //   }
            // },
          ]}
          formConfig={[
            {
              table: {
                title: '统一社会信用代码',
                dataIndex: 'orgCertificate',
                key: 'orgCertificate',
                fixed: 'left',
                width: 200,
                tooltip: 23,
              }
            },
            {
              table: {
                title: '是否战略供应商',
                dataIndex: 'strategicSupplier',
                key: 'strategicSupplier',
                width: 150,
                type: 'select',
              },
              isInForm: false,
              form: {
                type: 'select',
                optionConfig: {
                  label: 'label',
                  value: 'value'
                },
                optionData: [
                  {
                    label: '否',
                    value: '0'
                  },
                  {
                    label: '是',
                    value: '1'
                  }
                ]
              }
            },
            {
              table: {
                title: '协作单位名称',
                onClick: 'detail',
                dataIndex: 'customerName',
                key: 'customerName',
                width: 200,
                willExecute: (val) => {
                  if (val.rowData.zxCrCustomerInfoId) {
                    this.getTab1Data(val.rowData.zxCrCustomerInfoId);
                    this.getTab2Data(val.rowData.zxCrCustomerInfoId);
                    this.getTab3Data(val.rowData.zxCrCustomerInfoId);
                    this.getTab4Data(val.rowData.zxCrCustomerInfoId);
                  }
                }
              }
            },
            {
              table: {
                title: '状态',
                dataIndex: 'auditStatus',
                key: 'auditStatus',
                width: 150,
                render: (data) => {
                  if (data) {
                    if (data === '1') {
                      return '正在评审'
                    } else if (data === '2'){
                      return '评审通过'
                    } else if (data === '3') {
                      return '评审不通过'
                    } else if (data === '5') {
                      return '评审终止'
                    } else {
                      return ''
                    }
                  } else {
                    return ''
                  }
                  
                }
              }
            },
            {
              table: {
                title: '法定代表人',
                dataIndex: 'corparation',
                key: 'corparation',
                width: 150
              }
            },
            {
              table: {
                title: '法定代表人电话',
                dataIndex: 'pricinpalMobile',
                key: 'pricinpalMobile',
                width: 150
              }
            },
            {
              table: {
                title: '占号维护单位',
                dataIndex: 'comOrgName',
                key: 'comOrgName',
                width: 130,
              }
            },
            {
              table: {
                title: '是否已占号',
                dataIndex: 'useFlag',
                key: 'useFlag',
                width: 150,
              }
            },
            {
              table: {
                title: '证照是否过期',
                dataIndex: 'dateStatus',
                key: 'dateStatus',
                width: 150,
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                },
              }
            },
            {
              table: {
                title: '是否需要复审',
                dataIndex: 'isNeedfushen',
                key: 'isNeedfushen',
                width: 150,
                render: (h) => {
                  if (h === '1') {
                    return '是'
                  } else {
                    return '否'
                  }
                },
              }
            },
            {
              table: {
                title: '复核状态',
                dataIndex: 'fuheStatus',
                key: 'fuheStatus',
                width: 100,

              }
            },
            {
              table: {
                title: '复审状态',
                dataIndex: 'fushenStatus',
                key: 'fushenStatus',
                width: 100
              }
            },

            {
              table: {
                title: '备注',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 100
              }
            }
          ]}
          tabs={[
            {
              field: "form1",
              name: "qnnForm",
              title: "协作单位基本信息",
              content: {
                formConfig: [
                  {
                    field: 'zxCrCustomerInfoId',
                    type: 'string',
                    hide: true,
                  },
                  {
                    label: '统一社会信用代码',
                    field: "orgCertificate",
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '是否战略供应商',
                    field: "strategicSupplier",
                    type: 'select',
                    placeholder: '请输入',
                    span: 8,
                    required: true,
                    optionConfig: {
                      label: 'label', //默认 label
                      value: 'value'
                    },
                    optionData: [
                      {
                        label: '否',
                        value: '0'
                      },
                      {
                        label: '是',
                        value: '1'
                      }
                    ]
                  },
                  {
                    label: '协作单位名称',
                    field: "customerName",
                    required: true,
                    type: 'string',
                    span: 8,
                  },
                  {
                    label: '状态',
                    field: "auditStatus",
                    type: 'string',
                    span: 8,
                    disabled: true,
                    initialValue: ''
                  },
                  {
                    label: '注册资本金(元)',
                    field: "regMoney",
                    type: 'number',
                    span: 8,
                    disabled: true
                  },
                  {
                    label: '实缴资本金(元)',
                    field: "realRegMoney",
                    type: 'number',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '企业性质',//???
                    field: "businessType",
                    type: 'select',
                    placeholder: '请输入',
                    span: 8,
                    optionConfig: {
                      label: 'itemName',
                      value: 'itemId'
                    },
                    fetchConfig: {
                      apiName: 'getBaseCodeSelect',
                      otherParams: {
                        itemId: 'qiYeXingZhi'//问张启明--000004411
                      }
                    },
                  },
                  {
                    label: '法定代表人',
                    field: "corparation",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '法定代表人身份证号码',
                    field: "pricinpalIDCard",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '法定代表人电话',
                    field: "pricinpalMobile",
                    type: 'phone',
                    span: 8
                  },
                  {
                    label: '企业详细地址',
                    field: "pricinpalAddr",
                    type: 'textarea',
                    autoSize: {
                      minRows: 1,
                      maxRows: 3
                    },
                    span: 8
                  },
                  {
                    label: '营业执照注册号',
                    field: "licenceNO",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '营业执照有效期至',
                    field: "licenceDate",
                    type: 'date',
                    span: 8
                  },
                  {
                    label: '企业资质证书编号',
                    field: "qualificateNo",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '企业资质证书有效期至',
                    field: "qualificateDate",
                    type: 'date',
                    span: 8
                  },
                  {
                    label: '企业税务登记证号',
                    field: "taxRegNo",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '企业税务登记有效期至',
                    field: "taxRegDate",
                    type: 'date',
                    span: 8
                  },
                  {
                    label: '安全生产许可证编码',
                    field: "safeCode",
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '安全生产许可证有效期至',
                    field: 'safeBookDate',
                    type: 'date',
                    placeholder: '请选择',
                    span: 8,
                  },
                  {
                    label: '所在省份',
                    field: 'province',
                    type: 'select',
                    placeholder: '请选择',
                    span: 8,
                    optionConfig: {
                      label: 'itemName',
                      value: 'itemId'
                    },
                    fetchConfig: {
                      apiName: 'getBaseCodeSelect',
                      otherParams: {
                        itemId: 'yezhuhetongtaizhangshengfen'
                      }
                    },
                  },
                  {
                    label: '所在区域',
                    field: "area",
                    type: 'select',
                    placeholder: '请选择',
                    span: 8,
                    optionConfig: {
                      label: 'itemName', //默认 label
                      value: 'itemId'
                    },
                    fetchConfig: {
                      apiName: 'getBaseCodeSelect',
                      otherParams: {
                        itemId: 'suoZaiQuYu'
                      }
                    },
                  },
                  {
                    label: '纳税人类别',
                    field: 'taxpayerType',
                    type: 'select',
                    placeholder: '请输入',
                    span: 8,
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: "一般纳税人", value: "010" },
                      { label: "小规模纳税人", value: "020" },
                      { label: "其他纳税人", value: "030" }
                    ]
                  },
                  {
                    label: '纳税人识别号',
                    field: 'taxpayerNum',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '占号维护单位',
                    field: 'comOrgName',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '开户行名称',
                    field: 'bankName',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '开户行账号',
                    field: 'bankAccount',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '银行授信额度',
                    field: 'creditLineAmt',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '推荐单位',
                    field: 'referenceOrg',
                    type: 'string',
                    disabled: true,
                    span: 8
                  },
                  {
                    label: '证照是否过期',
                    field: 'dateStatus',
                    type: 'select',
                    disabled: true,
                    span: 8,
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: "未过期", value: "0" },
                      { label: "证照过期", value: "1" }
                    ],
                  },
                  {
                    label: '复核状态',
                    field: 'fuheStatus',
                    type: 'string',
                    disabled: true,
                    span: 8,
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: "审核通过", value: "1" },
                      { label: "审核不通过", value: "2" },
                      { label: "已注销", value: "3" }
                    ],
                  },
                  {
                    label: '复审状态',
                    field: 'fushenStatus',
                    type: 'string',
                    disabled: true,
                    span: 8,
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      { label: "开始复审", value: "0" },
                      { label: "复审中", value: "1" },
                      { label: "已复审完成", value: "2" }
                    ],
                  },
                  {
                    label: '推荐人姓名',
                    field: 'referenceName',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '推荐人职务',
                    field: 'referencePost',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '推荐人联系电话',
                    field: 'referencePhone',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '曾用名',
                    field: 'usedNames',
                    type: 'string',
                    span: 8
                  },
                  {
                    label: '黑名单',
                    field: 'isBlack',
                    type: 'select',
                    span: 8,
                    optionConfig: {
                      label: 'label',
                      value: 'value'
                    },
                    optionData: [
                      {
                        label: '否',
                        value: '0'
                      },
                      {
                        label: '是',
                        value: '1'
                      }
                    ]
                  }
                ]
              }
            },
            {
              field: "table1",
              name: "qnnForm",
              title: "协作单位资质",
              content: {
                formConfig: [
                  {
                    type: 'component',
                    field: 'table1',
                    Component: obj => {
                      // let drawerTitile = obj.Pstate.drawerDetailTitle;
                      return (
                        <div
                          style={{ width: "100%", padding: '10px' }}
                        >
                          <QnnTable
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                              token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}
                            antd={{
                              rowKey: '',
                              size: 'small'
                            }}
                            {...configItem}
                            wrappedComponentRef={(me) => {
                              this.tableSK = me;
                            }}
                            isShowRowSelect={false}
                            data={this.state.dataTab1}
                            formConfig={[
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'id',
                                  hide: true
                                }
                              },
                              {
                                table: {
                                  title: '资质名称',
                                  dataIndex: 'qaName',
                                  key: 'qaName'
                                },
                                form: {
                                  type: 'string',
                                  field: 'qaName',
                                  // optionConfig: {
                                  //   label: 'qaName',
                                  //   value: 'qaID'
                                  // },
                                  // fetchConfig: {
                                  //   apiName: 'getZxEqIecmOrgList'//???
                                  // },
                                }
                              },
                              {
                                table: {
                                  title: '等级',
                                  dataIndex: 'quLevel',
                                  key: 'quLevel'
                                },
                                form: {
                                  type: 'string',
                                  field: 'quLevel',
                                  // optionConfig: {
                                  //   label: 'quLevel',
                                  //   value: 'quID'
                                  // },
                                  // fetchConfig: {
                                  //   apiName: 'getZxEqIecmOrgList'//???
                                  // },
                                }
                              },
                              {
                                table: {
                                  title: '附件',
                                  dataIndex: 'fileList',
                                  key: 'fileList',
                                  render: (data, rowData) => {
                                    if (rowData.fileList && rowData.fileList.length > 0) {
                                      return '查看附件'
                                    } else {
                                      return ''
                                    }
                                  },
                                  onClick: (obj) => {
                                    this.setState({
                                      visible: true,
                                      exportData: obj.rowData.fileList
                                    })
                                  }
                                },
                                form: {
                                  type: 'files',
                                  field: 'fileList',
                                  fetchConfig: {
                                    apiName: 'upload'
                                  },
                                }
                              }
                            ]}
                            actionBtns={[
                              // {
                              //   name: "diyadd",
                              //   icon: "plus",
                              //   type: "primary",
                              //   label: "新增",
                              //   onClick: () => {
                              //     let datas = this.state.dataTab1;
                              //     this.setState({
                              //       id: this.state.id + 1
                              //     }, () => {
                              //       datas.push({ id: String(this.state.id) });
                              //       this.setState({
                              //         dataTab1: datas
                              //       })
                              //     })
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // },
                              // {
                              //   name: 'diydel',
                              //   icon: 'delete',
                              //   type: 'danger',
                              //   label: '删除',
                              //   disabled: (obj) => {
                              //     if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                              //       return false;
                              //     } else {
                              //       return true;
                              //     }
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   },
                              //   onClick: (obj) => {
                              //     let selectedRows = obj.selectedRows;
                              //     let mInDatas = this.state.dataTab1;
                              //     mInDatas = mInDatas.filter(item => {
                              //       let idList = selectedRows.map(v => v.id)
                              //       return !idList.includes(item.id)
                              //     })
                              //     this.setState({
                              //       dataTab1: mInDatas
                              //     })
                              //   }
                              // },
                              // {
                              //   name: 'diysave',
                              //   type: 'primary',
                              //   label: '保存',
                              //   onClick: () => {

                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // }
                            ]}
                          />
                        </div>
                      );
                    }
                  },
                ]
              }
            },
            {
              field: "table2",
              name: "qnnForm",
              title: "协作单位分类附件",
              content: {
                formConfig: [
                  {
                    type: 'component',
                    field: 'table2',
                    Component: obj => {
                      // let drawerTitile = obj.Pstate.drawerDetailTitle;
                      return (
                        <div
                          style={{ width: "100%", padding: '10px' }}
                        >
                          <QnnTable
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                              token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}

                            antd={{
                              rowKey: 'zxCrCustomerInfoDatumId',
                              size: 'small'
                            }}
                            {...configItem}
                            wrappedComponentRef={(me) => {
                              this.tableSK = me;
                            }}
                            isShowRowSelect={false}
                            data={this.state.dataTab2}
                            formConfig={[
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'zxCrCustomerInfoDatumId',
                                  hide: true
                                }
                              },
                              {
                                table: {
                                  title: '排序',
                                  dataIndex: 'orderStr',
                                  key: 'orderStr',
                                },
                                form: {
                                  type: 'string',
                                  field: 'orderStr',
                                }
                              },
                              {
                                table: {
                                  title: '名称',
                                  dataIndex: 'datumName',
                                  key: 'datumName'
                                },
                                form: {
                                  type: 'string',
                                  field: 'datumName'
                                }
                              },
                              {
                                table: {
                                  title: '编号',
                                  dataIndex: 'datumNo',
                                  key: 'datumNo'
                                },
                                form: {
                                  type: 'string',
                                  field: 'datumNo'
                                }
                              },
                              {
                                table: {
                                  title: '附件',
                                  dataIndex: 'fileList',
                                  key: 'fileList',
                                  render: (data, rowData) => {
                                    if (rowData.fileList && rowData.fileList.length > 0) {
                                      return '查看附件'
                                    } else {
                                      return ''
                                    }
                                  },
                                  onClick: (obj) => {
                                    this.setState({
                                      visible: true,
                                      exportData: obj.rowData.fileList
                                    })
                                  }
                                },
                                form: {
                                  type: 'files',
                                  field: 'fileList',
                                  fetchConfig: {
                                    apiName: 'upload'
                                  },
                                }
                              }
                            ]}
                            actionBtns={[
                              // {
                              //   name: "diyadd",
                              //   icon: "plus",
                              //   type: "primary",
                              //   label: "新增",
                              //   onClick: () => {
                              //     let datas = this.state.dataTab2;
                              //     this.setState({
                              //       zxCrCustomerInfoDatumId: this.state.zxCrCustomerInfoDatumId + 1
                              //     }, () => {
                              //       datas.push({ zxCrCustomerInfoDatumId: String(this.state.zxCrCustomerInfoDatumId) });
                              //       this.setState({
                              //         dataTab2: datas
                              //       })
                              //     })
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // },
                              // {
                              //   name: 'diydel',
                              //   icon: 'delete',
                              //   type: 'danger',
                              //   label: '删除',
                              //   disabled: (obj) => {
                              //     if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                              //       return false;
                              //     } else {
                              //       return true;
                              //     }
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   },
                              //   onClick: (obj) => {
                              //     let selectedRows = obj.selectedRows;
                              //     let mInDatas = this.state.dataTab2;
                              //     mInDatas = mInDatas.filter(item => {
                              //       let idList = selectedRows.map(v => v.zxCrCustomerInfoDatumId)
                              //       return !idList.includes(item.zxCrCustomerInfoDatumId)
                              //     })
                              //     this.setState({
                              //       dataTab2: mInDatas
                              //     })
                              //   }
                              // },
                              // {
                              //   name: 'diysave',
                              //   type: 'primary',
                              //   label: '保存',
                              //   onClick: () => {

                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // }
                            ]}
                          />
                        </div>
                      );
                    }
                  },
                ]
              }
            },
            {
              field: "table3",
              name: "qnnForm",
              title: "近三年业绩和荣誉",
              content: {
                formConfig: [
                  {
                    type: 'component',
                    field: 'table3',
                    Component: obj => {
                      // let drawerTitile = obj.Pstate.drawerDetailTitle;
                      return (
                        <div
                          style={{ width: "100%", padding: '10px' }}
                        >
                          <QnnTable
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                              token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}

                            antd={{
                              rowKey: 'zxCrCustomerHonorId',
                              size: 'small'
                            }}
                            {...configItem}
                            wrappedComponentRef={(me) => {
                              this.tableSK = me;
                            }}
                            isShowRowSelect={false}
                            data={this.state.dataTab3}
                            formConfig={[
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'zxCrCustomerHonorId',
                                  hide: true
                                }
                              },
                              {
                                table: {
                                  title: '序号',
                                  width: 100,
                                  dataIndex: 'orderStr',
                                  key: 'orderStr',
                                  align: 'center'
                                },
                                form: {
                                  type: 'string',
                                  field: 'orderStr'
                                }
                              },
                              {
                                table: {
                                  title: '业绩和荣誉',
                                  dataIndex: 'honor',
                                  key: 'honor'
                                },
                                form: {
                                  type: 'string',
                                  field: 'honor'
                                }
                              },
                              {
                                table: {
                                  title: '编辑日期',
                                  dataIndex: 'editTime',
                                  key: 'editTime',
                                  format: 'YYYY-MM-DD'
                                },
                                form: {
                                  type: 'date',
                                  field: 'editTime'
                                }
                              },
                              {
                                table: {
                                  title: '已建工程项目名称',
                                  dataIndex: 'projectName',
                                  key: 'projectName',
                                  width: 180,
                                },
                                form: {
                                  type: 'string',
                                  field: 'projectName'
                                }
                              },
                              {
                                table: {
                                  title: '合同开始时间',
                                  width: 140,
                                  dataIndex: 'contrDateS',
                                  key: 'contrDateS',
                                  format: 'YYYY-MM-DD'
                                },
                                form: {
                                  type: 'date',
                                  field: 'contrDateS'
                                }
                              },
                              {
                                table: {
                                  title: '合同结束时间',
                                  width: 140,
                                  dataIndex: 'contrDateE',
                                  key: 'contrDateE',
                                  format: 'YYYY-MM-DD'
                                },
                                form: {
                                  type: 'date',
                                  field: 'contrDateE'
                                }
                              },
                              {
                                table: {
                                  title: '工程类别',
                                  width: 140,
                                  dataIndex: 'projectType',
                                  key: 'projectType'
                                },
                                form: {
                                  type: 'string',//???
                                  field: 'projectType',
                                  // optionConfig: {
                                  //   label: 'quLevel',
                                  //   value: 'quID'
                                  // },
                                  // fetchConfig: {
                                  //   apiName: 'getZxEqIecmOrgList'//???
                                  // },
                                }
                              },
                              {
                                table: {
                                  title: '合同金额',
                                  dataIndex: 'contrAmount',
                                  key: 'contrAmount',
                                  // tdEdit: drawerTitile === '详情' ? false : true,
                                  // tdEditCb: (obj) => {

                                  // }
                                },
                                form: {
                                  type: 'number',
                                  field: 'contrAmount'
                                }
                              },
                              {
                                table: {
                                  title: '工程数量',
                                  dataIndex: 'workNum',
                                  key: 'workNum',
                                  // tdEdit: drawerTitile === '详情' ? false : true,
                                  // tdEditCb: (obj) => {

                                  // }
                                },
                                form: {
                                  type: 'number',
                                  field: 'workNum'
                                }
                              },
                              {
                                table: {
                                  title: '单位',
                                  width: 100,
                                  dataIndex: 'workUnit',
                                  key: 'workUnit',
                                  // tdEdit: drawerTitile === '详情' ? false : true,
                                  // tdEditCb: (obj) => {

                                  // }
                                },
                                form: {
                                  type: 'string',
                                  field: 'workUnit'
                                }
                              },
                              {
                                table: {
                                  title: '录入时间',
                                  dataIndex: 'inputDate',
                                  key: 'inputDate',
                                  format: 'YYYY-MM-DD'
                                },
                                form: {
                                  type: 'date',
                                  field: 'inputDate'
                                }
                              },
                              {
                                table: {
                                  title: '附件',
                                  dataIndex: 'fileList',
                                  key: 'fileList',
                                  render: (data, rowData) => {
                                    if (rowData.fileList && rowData.fileList.length > 0) {
                                      return '查看附件'
                                    } else {
                                      return ''
                                    }
                                  },
                                  onClick: (obj) => {
                                    this.setState({
                                      visible: true,
                                      exportData: obj.rowData.fileList
                                    })
                                  }
                                },
                                form: {
                                  type: 'files',
                                  field: 'fileList',
                                  fetchConfig: {
                                    apiName: 'upload'
                                  },
                                }
                              }
                            ]}
                            actionBtns={[
                              // {
                              //   name: "diyadd",
                              //   icon: "plus",
                              //   type: "primary",
                              //   label: "新增",
                              //   onClick: () => {
                              //     let datas = this.state.dataTab3;
                              //     this.setState({
                              //       zxCrCustomerHonorId: this.state.zxCrCustomerHonorId + 1
                              //     }, () => {
                              //       datas.push({ zxCrCustomerHonorId: String(this.state.zxCrCustomerHonorId) });
                              //       this.setState({
                              //         dataTab3: datas
                              //       })
                              //     })
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // },
                              // {
                              //   name: 'diydel',
                              //   icon: 'delete',
                              //   type: 'danger',
                              //   label: '删除',
                              //   disabled: (obj) => {
                              //     if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                              //       return false;
                              //     } else {
                              //       return true;
                              //     }
                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   },
                              //   onClick: (obj) => {
                              //     let selectedRows = obj.selectedRows;
                              //     let mInDatas = this.state.dataTab3;
                              //     mInDatas = mInDatas.filter(item => {
                              //       let idList = selectedRows.map(v => v.zxCrCustomerHonorId)
                              //       return !idList.includes(item.zxCrCustomerHonorId)
                              //     })
                              //     this.setState({
                              //       dataTab3: mInDatas
                              //     })
                              //   }
                              // },
                              // {
                              //   name: 'diysave',
                              //   type: 'primary',
                              //   label: '保存',
                              //   onClick: () => {

                              //   },
                              //   hide: (val) => {
                              //     if (drawerTitile === '详情') {
                              //       return true
                              //     } else {
                              //       return false
                              //     }
                              //   }
                              // }
                            ]}
                          />
                        </div>
                      );
                    }
                  },
                ]
              }
            },
            {
              field: "table4",
              name: "qnnForm",
              title: "协作单位专业类别",
              content: {
                formConfig: [
                  {
                    type: 'component',
                    field: 'table4',
                    Component: obj => {
                      let drawerTitile = obj.Pstate.drawerDetailTitle;
                      return (
                        <div
                          style={{ width: "100%", padding: '10px' }}
                        >
                          <QnnTable
                            history={this.props.history}
                            match={this.props.match}
                            fetch={this.props.myFetch}
                            myFetch={this.props.myFetch}
                            upload={this.props.myUpload}
                            headers={{
                              token: this.props.loginAndLogoutInfo.loginInfo.token
                            }}

                            antd={{
                              rowKey: 'zxCrCustomerInfoDireId',
                              size: 'small'
                            }}
                            {...configItem}
                            wrappedComponentRef={(me) => {
                              this.tableSK = me;
                            }}
                            isShowRowSelect={false}
                            data={this.state.dataTab4}
                            formConfig={[
                              {
                                isInTable: false,
                                form: {
                                  label: '主键id',
                                  field: 'zxCrCustomerInfoDireId',
                                  hide: true
                                }
                              },
                              {
                                table: {
                                  title: '单位工程代码',
                                  dataIndex: 'catCode',
                                  key: 'catCode'
                                },
                                form: {
                                  type: 'string',
                                  field: 'catCode'
                                }
                              },
                              {
                                table: {
                                  title: '单位工程',
                                  dataIndex: 'catName',
                                  key: 'catName'
                                },
                                form: {
                                  type: 'string',
                                  field: 'catName'
                                }
                              },
                              {
                                table: {
                                  title: '分类代码',
                                  dataIndex: 'resID',
                                  key: 'resID'
                                },
                                form: {
                                  type: 'string',
                                  field: 'resID'
                                }
                              },
                              {
                                table: {
                                  title: '分类名称',
                                  dataIndex: 'resName',
                                  key: 'resName',
                                },
                                form: {
                                  type: 'string',
                                  field: 'resName'
                                }
                              }
                            ]}
                            actionBtns={[
                              {
                                name: "diyadd",
                                icon: "plus",
                                type: "primary",
                                label: "新增",
                                onClick: () => {
                                  let datas = this.state.dataTab4;
                                  this.setState({
                                    zxCrCustomerInfoDireId: this.state.zxCrCustomerInfoDireId + 1
                                  }, () => {
                                    datas.push({ zxCrCustomerInfoDireId: String(this.state.zxCrCustomerInfoDireId) });
                                    this.setState({
                                      dataTab4: datas
                                    })
                                  })
                                },
                                hide: (val) => {
                                  if (drawerTitile === '详情') {
                                    return true
                                  } else {
                                    return false
                                  }
                                }
                              },
                              {
                                name: 'diydel',
                                icon: 'delete',
                                type: 'danger',
                                label: '删除',
                                disabled: (obj) => {
                                  if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                                    return false;
                                  } else {
                                    return true;
                                  }
                                },
                                hide: (val) => {
                                  if (drawerTitile === '详情') {
                                    return true
                                  } else {
                                    return false
                                  }
                                },
                                onClick: (obj) => {
                                  let selectedRows = obj.selectedRows;
                                  let mInDatas = this.state.dataTab4;
                                  mInDatas = mInDatas.filter(item => {
                                    let idList = selectedRows.map(v => v.zxCrCustomerInfoDireId)
                                    return !idList.includes(item.zxCrCustomerInfoDireId)
                                  })
                                  this.setState({
                                    dataTab4: mInDatas
                                  })
                                }
                              },
                              {
                                name: 'diysave',
                                type: 'primary',
                                label: '保存',
                                onClick: () => {

                                },
                                hide: (val) => {
                                  if (drawerTitile === '详情') {
                                    return true
                                  } else {
                                    return false
                                  }
                                }
                              }
                            ]}
                          />
                        </div>
                      );
                    }
                  },
                ]
              }
            }
          ]}

        />
        {visible ? <div>
          <Modal
            width={'500px'}
            style={{ paddingBottom: '0', top: '0' }}
            title="查看附件"
            visible={visible}
            footer={null}
            bodyStyle={{ padding: '10px', overflow: 'hidden' }}
            centered={true}
            closable={false}
            maskClosable={false}
            wrapClassName={'replyData'}
          >
            <QnnForm
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{
                token: this.props.loginAndLogoutInfo.loginInfo.token
              }}
              formConfig={[
                {
                  label: '附件',
                  field: 'attachment',
                  type: 'files',
                  initialValue: exportData,
                  disabled: true,
                  fetchConfig: {
                    apiName: 'upload'
                  },
                  formItemLayout: {
                    labelCol: {
                      xs: { span: 24 },
                      sm: { span: 5 }
                    },
                    wrapperCol: {
                      xs: { span: 24 },
                      sm: { span: 19 }
                    }
                  },
                }
              ]}
              btns={[
                {
                  name: "cancel",
                  type: "dashed",
                  label: "关闭",
                  field: 'cancel',
                  isValidate: false,
                  onClick: (obj) => {
                    this.setState({ visible: false });
                  }
                }
              ]}
              tailFormItemLayout={{
                wrapperCol: {
                  xs: {
                    span: 24,
                    offset: 0
                  },
                  sm: {
                    span: 24,
                    offset: 8
                  }
                }
              }}
            />
          </Modal>
        </div> : null}
      </div>
    );
  }
}

export default index;