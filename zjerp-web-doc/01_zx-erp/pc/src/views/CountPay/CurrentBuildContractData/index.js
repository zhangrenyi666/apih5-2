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
                title: '??????',
                dataIndex: 'subType',
                key: 'subType',
                fixed: 'left',
                width: 150,
                onClick: 'detail',
                filter: true,
                render:(h)=>{
                  if(h === '0'){
                    return '???????????????'
                  }else if(h === '1'){
                    return '???????????????'
                  }
                }
              },
              form: {
                required: true,
                type: 'select',
                optionConfig:{
                    label: 'label', //?????? label
                    value: 'value'
                 },
                optionData: [
                    {
                        label: '???????????????',
                        value: '0'
                    },
                    {
                        label: '???????????????',
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
                title: '????????????',
                dataIndex: 'subDetail',
                key: 'subDetail',
                width: 180,
                filter: true,
                tooltip:20
              },
              form: {
                type: 'string',
                placeholder: '?????????',
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
                title: '??????????????????',
                dataIndex: 'subDetail2',
                key: 'subDetail2',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '?????????',
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
                title: '??????',
                dataIndex: 'orderStr',
                key: 'orderStr',
                width: 150,
              },
              form: {
                required: true,
                type: 'number',
                placeholder: '?????????',
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
                title: '?????????',
                dataIndex: 'hangCode',
                key: 'hangCode',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '?????????',
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
                title: '?????????',
                dataIndex: 'isHuizong',
                key: 'isHuizong',
                width: 150,
                render: (data) => {
                  if (data === '0') {
                    return '?????????';
                  } else {
                    return '?????????';
                  }
                }
              },
              form: {
                type: 'select',
                optionConfig:{
                    label: 'label', //?????? label
                    value: 'value'
                 },
                optionData: [
                    {
                        label: '?????????',
                        value: '0'
                    },
                    {
                        label: '?????????',
                        value: '1'
                    }
                ],
                placeholder:'?????????',
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
                title: '??????????????????',
                dataIndex: 'huizongCode',
                key: 'huizongCode',
                width: 150,
              },
              form: {
                type: 'string',
                placeholder: '?????????',
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
                title: '????????????',
                dataIndex: 'isReduce',
                key: 'isReduce',
                width: 100,
                render: (data) => {
                  if (data === '1') {
                    return '???';
                  } else {
                    return '???';
                  }
                }
              },
              form: {
                type: 'radio',
                optionData: [
                  {
                    label: '???',
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
                title: '??????',
                dataIndex: 'remarks',
                key: 'remarks',
                width: 150,
              },
              form: {
                type: 'textarea',
                placeholder: '?????????',
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