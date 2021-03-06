import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import ConstructionSchemeForm from './form';
import { message as Msg } from 'antd';
const config = {
  antd: {
    rowKey: 'zxScGroupSchemeId',
    size: 'small'
  },
  drawerConfig: {
    width: '70%'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 8 },
      sm: { span: 8 }
    },
    wrapperCol: {
      xs: { span: 18 },
      sm: { span: 18 }
    }
  },
};
class index extends Component {
  constructor(props) {
    super(props);
    const curCompany = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
    const lockProject = this.props.loginAndLogoutInfo.loginInfo.userInfo?.lockProject;
    this.state = {
      departmentId: ((curCompany?.ext1 === '1' || curCompany?.ext1 === '2') && lockProject?.projectId) ? (lockProject.projectId !== 'all' ? lockProject.projectId : curCompany?.companyId) : curCompany?.projectId,
    }
  }
  render() {
    console.log(this.props.loginAndLogoutInfo.loginInfo.userInfo);
    const { departmentId } = this.state;
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
            apiName: 'getZxScGroupSchemeList',
            otherParams: {
              orgID: departmentId,
            }
          }}
          drawerShowToggle={(obj) => {
            let { drawerIsShow } = obj;
            if (!drawerIsShow) {
              obj.btnCallbackFn.refresh();
              obj.btnCallbackFn.clearSelectedRows()
            }
          }}
          componentsKey={{
            ConstructionSchemeForm: (obj) => {
              this.table.clearSelectedRows();
              return <ConstructionSchemeForm {...this.props} btnCallbackFn={obj.btnCallbackFn} isInQnnTable={obj.isInQnnTable} flowData={obj.selectedRows[0]} />
            }
          }}
          method={{
            fetchConfigForAdd: () => {
              return {
                apiName: 'addZxScGroupScheme',
                otherParams: {
                  apih5FlowStatus: '-1'
                }
              }
            },
            //??????????????????
            disabledFunEdit: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus === '1' || data[0].apih5FlowStatus === '2') {
                return true;
              } else {
                return false;
              }
            },
            //??????????????????
            disabledForFlow: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              if (data.length !== 1 || data[0].apih5FlowStatus !== '-1') {
                return true;
              } else {
                return false;
              }
            },
            //??????????????????
            disabledForDelete: (obj) => {
              let data = obj.btnCallbackFn.getSelectedRows();
              let disabledLength = 0
              for (var i = 0; i < data.length; i++) {
                if (data[i].apih5FlowStatus === '1' || data[i].apih5FlowStatus === '2') {
                  disabledLength++
                }
              }
              if (disabledLength > 0 || data.length === 0) return true
            },
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "ConstructionScheme"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'zxScGroupSchemeId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'workId',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'apih5FlowStatus',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                field: 'comId',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'comName',
                key: 'comName',
                fixed: 'left',
                width: 150,
                filter: true,
              },
              form: {
                field: 'comName',
                type: 'string',
                spanForm: 12,
                editDisabled: true,
                addDisabled: true,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'projectName',
                key: 'projectName',
                width: 170,
                filter: true,
                onClick: 'detail',
              },
              form: {
                field: 'projectName',
                type: 'string',
                hide: true,
              }
            },
            {
              isInTable: false,
              form: {
                label:'????????????',
                type: 'select',
                showSearch: true,
                field: 'projectId',
                spanForm: 12,
                required: true,
                editDisabled: true,
                optionConfig: {
                  label: 'orgName',
                  value: 'orgID',
                  linkageFields: {
                    comName: 'companyName',
                    projectName: 'orgName',
                    province: 'provinceAbbreviation',
                    comId: 'companyId'
                  }
                },
                fetchConfig: () => {
                  return {
                    apiName: 'getZxCtContractListByStatus',
                    otherParams: { orgID: departmentId }
                  }
                },
                onChange: (val, obj) => {
                  if (val) {
                    let pageData = obj.form.getFieldsValue()
                    let params = {}
                    params.projectId = pageData.projectId
                    this.props.myFetch('getZxScGroupSchemeSequence', params).then(
                      ({ data, success, message }) => {
                        if (success) {
                          let provincePY = ''
                          this.props.myFetch('getBaseCodeSelect', { itemId: 'xingzhengquhuadaima' })
                            .then(({ data, success }) => {
                              if (success && data && data.length > 0) {
                                for (var i = 0; i < data.length; i++) {
                                  if (obj.itemData.provinceAbbreviation === data[i].itemAsName) {
                                    provincePY = data[i].ext1
                                  }
                                }
                              }
                            })
                            .then(() => {
                              obj.form.setFieldsValue({
                                schemeNo: `${obj.itemData.contractor}-${obj.itemData.biddingQualification}-${provincePY}-${obj.itemData.shortName}-SZ-${data}`
                              })
                            })

                        } else {
                          Msg.error(message);
                        }
                      }
                    );
                  } else {
                    obj.form.setFieldsValue({
                      schemeNo: '',
                    })
                  }
                },
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'schemeNo',
                key: 'schemeNo',
                width: 180,
                filter: true,
              },
              form: {
                field: 'schemeNo',
                editDisabled: true,
                addDisabled: true,
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'schemeName',
                key: 'schemeName',
                width: 150,
                filter: true,
              },
              form: {
                field: 'schemeName',
                required: true,
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'projectType',
                key: 'projectType',
                width: 150,
                filter: true,
                render: (item) => {
                  if (item === '0') {
                    return '????????????'
                  } else if (item === '1') {
                    return '????????????'
                  } else if (item === '2') {
                    return '????????????'
                  } else if (item === '3') {
                    return '????????????'
                  } else if (item === '4') {
                    return '????????????'
                  } else if (item === '5') {
                    return '????????????'
                  } else if (item === '6') {
                    return '???????????????'
                  } else if (item === '7') {
                    return '????????????'
                  } else if (item === '8') {
                    return '????????????'
                  } else if (item === '9') {
                    return '??????'
                  } else if (item === '10') {
                    return '????????????'
                  }
                }
              },
              form: {
                field: 'projectType',
                type: 'select',
                spanForm: 12,
                optionConfig: {
                  label: 'label', //?????? label
                  value: 'value'
                },
                optionData: [
                  { label: '????????????', value: '0' },
                  { label: '????????????', value: '1' },
                  { label: '????????????', value: '2' },
                  { label: '????????????', value: '3' },
                  { label: '????????????', value: '4' },
                  { label: '????????????', value: '5' },
                  { label: '???????????????', value: '6' },
                  { label: '????????????', value: '7' },
                  { label: '????????????', value: '8' },
                  { label: '??????', value: '9' },
                  { label: '????????????', value: '10' },

                ],
              }
            },
            {
              isInTable: false,
              form: {
                label: '????????????',
                field: 'province',
                type: 'string',
                editDisabled: true,
                addDisabled: true,
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'contrDuration',
                key: 'contrDuration',
                width: 150,
              },
              form: {
                field: 'contrDuration',
                required: true,
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                required: true,
                label: '??????????????????',
                field: 'planDate',
                width: 100,
                format: 'YYYY-MM-DD',
                type: 'date',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '????????????(??????)',
                dataIndex: 'contractAmt',
                key: 'contractAmt',
                width: 150,
              },
              form: {
                field: 'contractAmt',
                required: true,
                type: 'number',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                label: '????????????',
                field: 'projManager',
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                required: true,
                label: '????????????',
                field: 'projEngineer',
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '???????????????',
                dataIndex: 'schemeAppro',
                key: 'schemeAppro',
                width: 150,
              },
              form: {
                label: '???????????????',
                field: 'schemeAppro',
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
                initialValue: this.props.loginAndLogoutInfo.loginInfo.userInfo.realName
              }
            },
            {
              isInTable: false,
              form: {
                label: '????????????????????????',
                field: 'engineerTel',
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },

            {
              isInTable: false,
              form: {
                required: true,
                label: '???????????????????????????',
                field: 'approTel',
                type: 'string',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              isInTable: false,
              form: {
                editDisabled: true,
                addDisabled: true,
                label: '????????????',
                field: 'bizDate',
                type: 'date',
                spanForm: 12,
                initialValue: new Date(),
              }
            },
            {
              isInTable: false,
              form: {
                label: '??????',
                field: 'remark',
                type: 'textarea',
                placeholder: '?????????',
                spanForm: 12,
              }
            },
            {
              table: {
                title: '??????????????????',
                dataIndex: 'passTime',
                key: 'passTime',
                width: 150,
                format: 'YYYY-MM-DD',
              },
              isInForm: false,
            },
            {
              table: {
                title: '????????????',
                dataIndex: 'apih5FlowStatus',
                key: 'apih5FlowStatus',
                width: 100,
                fixed: 'right',
                render: (data) => {
                  if (data === '0') {
                    return '?????????';
                  } else if (data === '1') {
                    return '?????????';
                  } else if (data === '2') {
                    return '????????????';
                  } else if (data === '3') {
                    return '??????';
                  } else if (data === '-1') {
                    return '?????????';
                  }
                }
              },
              isInForm: false,
            }
          ]}
        />
      </div>
    );
  }
}

export default index;