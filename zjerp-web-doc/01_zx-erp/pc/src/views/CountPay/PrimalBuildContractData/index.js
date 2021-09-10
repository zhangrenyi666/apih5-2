import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  drawerConfig: {
    width: '1000px'
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 4 },
      sm: { span: 4 }
    },
    wrapperCol: {
      xs: { span: 20 },
      sm: { span: 20 }
    }
  }
};
class index extends Component {
  render() {
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
            apiName: 'getZxCtContrJzItemBaseList',
            otherParams: {
              jzType: '1',
              mainID: '0'
            }
          }}
          drawerShowToggle={(obj) => {
              let { drawerIsShow } = obj;
              if (!drawerIsShow) {
                  obj.btnCallbackFn.refresh();
                  obj.btnCallbackFn.clearSelectedRows()
              }
          }}
          method={{
            fetchConfigForAdd: () => {
              return {
                apiName: 'addZxCtContrJzItemBase',
                otherParams: {
                  jzType: '1'
                }
              }
            }
          }}
          actionBtns={{
            apiName: "getSysMenuBtn",
            otherParams: (obj) => {
              var props = obj.props;
              let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
              return {
                menuParentId: curRouteData._value,
                tableField: "PrimalBuildContractData"
              }
            }
          }}
          formConfig={[
            {
              isInTable: false,
              form: {
                field: 'id',
                type: 'string',
                hide: true,
              }
            },
            {
              table: {
                title: '项目',
                dataIndex: 'subType',
                key: 'subType',
                fixed: 'left',
                width: 150,
                onClick: 'detail',
                filter: true,
                render: (h) => {
                  if (h === '0') {
                    return '初始预计总收入'
                  } else if (h === '1') {
                    return '初始预计总成本'
                  }
                }
              },
              form: {
                required: true,
                type: 'select',
                optionConfig: {
                  label: 'label', //默认 label
                  value: 'value'
                },
                optionData: [
                  {
                    label: '初始预计总收入',
                    value: '0'
                  },
                  {
                    label: '初始预计总成本',
                    value: '1'
                  }
                ],
                placeholder: '请选择',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '项目子类',
                dataIndex: 'subType2',
                key: 'subType2',
                width: 150,
                render: (h) => {
                  if (h === '0') {
                    return '标后预算调整确认法'
                  } else if (h === '1') {
                    return '施工预算确认法'
                  }
                }
              },
              form: {
                type: 'select',
                optionConfig: {
                  label: 'label', //默认 label
                  value: 'value'
                },
                optionData: [
                  {
                    label: '标后预算调整确认法',
                    value: '0'
                  },
                  {
                    label: '施工预算确认法',
                    value: '1'
                  }
                ],
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '项目内容',
                dataIndex: 'subDetail',
                key: 'subDetail',
                width: 180,
                filter: true,
                tooltip: 19
              },
              form: {
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '项目内容明细',
                dataIndex: 'subDetail2',
                key: 'subDetail2',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '排序',
                dataIndex: 'orderStr',
                key: 'orderStr',
                width: 150,
              },
              form: {
                required: true,
                type: 'number',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  },
                }
              }
            },
            {
              table: {
                title: '行标识',
                dataIndex: 'hangCode',
                key: 'hangCode',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '行类型',
                dataIndex: 'isHuizong',
                key: 'isHuizong',
                width: 150,
                render: (data) => {
                  if (data === '0') {
                    return '汇总行';
                  } else {
                    return '动态行';
                  }
                }
              },
              form: {
                type: 'select',
                optionConfig: {
                  label: 'label', //默认 label
                  value: 'value'
                },
                optionData: [
                  {
                    label: '汇总行',
                    value: '0'
                  },
                  {
                    label: '动态行',
                    value: '1'
                  }
                ],
                placeholder: '请选择',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '汇总到哪一行',
                dataIndex: 'huizongCode',
                key: 'huizongCode',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '是否减项',
                dataIndex: 'isReduce',
                key: 'isReduce',
                width: 100,
                render: (data) => {
                  if (data === '1') {
                    return '是';
                  } else {
                    return '否';
                  }
                }
              },
              form: {
                type: 'radio',
                optionData: [
                  {
                    label: '是',
                    value: '1'
                  },
                  {
                    label: '否',
                    value: '0'
                  }
                ],
                initialValue: '0',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            },
            {
              table: {
                title: '说明',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 150,
              },
              form: {
                type: 'textarea',
                placeholder: '请输入',
                spanForm: 12,
                formItemLayout: {
                  labelCol: {
                    xs: { span: 6 },
                    sm: { span: 6 }
                  },
                  wrapperCol: {
                    xs: { span: 18 },
                    sm: { span: 18 }
                  }
                }
              }
            }
          ]}
        />
      </div>
    );
  }
}

export default index;