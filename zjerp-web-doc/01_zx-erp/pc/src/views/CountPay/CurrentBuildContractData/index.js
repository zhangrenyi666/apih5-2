import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
const config = {
  antd: {
    rowKey: 'id',
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
      xs: { span: 3 },
      sm: { span: 3 }
    },
    wrapperCol: {
      xs: { span: 21 },
      sm: { span: 21 }
    }
  }
};
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {

    }
  }
  render() {
    return (
      <div>
        <QnnTable
          {...this.props}
          fetch={this.props.myFetch}
          wrappedComponentRef={(me) => {
            this.table = me;
          }}
          {...config}
          fetchConfig={{
            apiName: 'getZxCtContrJzItemBaseList',
            otherParams: {
              jzType:'2',
              mainID:'0'
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
                  jzType: '2'
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
                tableField: "CurrentBuildContractData"
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
                render:(h)=>{
                  if(h === '0'){
                    return '预计总收入'
                  }else if(h === '1'){
                    return '预计总成本'
                  }
                }
              },
              form: {
                required: true,
                type: 'select',
                optionConfig:{
                    label: 'label', //默认 label
                    value: 'value'
                 },
                optionData: [
                    {
                        label: '预计总收入',
                        value: '0'
                    },
                    {
                        label: '预计总成本',
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
                tooltip:20
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
                optionConfig:{
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
                placeholder:'请选择',
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
                  }
                ],
                spanForm: 12,
                initialValue: '0',
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