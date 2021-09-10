import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
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
}
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      visible: false,
      treeData: null,
      loading: false,
      parentID: '',
      bsid: '',
      defaultExpandedKeys: []
    }
  }
  componentDidMount() {
    this.refresh(true);
  }
  refresh = (updataTree = false) => {
    this.setState({
      loading: true
    })
    this.props.myFetch('getZxCcResCategoryTree', {resStyle:'zzbz'}).then(
      ({ data, success, message }) => {
        if (success && updataTree) {
          this.setState({
            treeData: data,
            loading: false,
            parentID: data.length ? data[0].id : '',
            bsid: data.length ? data[0].bsid : '',
            defaultExpandedKeys: data.length ? data[0].bsid.split(',') : [],
          })
        } else if (success) {
          this.setState({
            treeData: data,
            loading: false,
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  render() {
    const { bsid, treeData, parentID, defaultExpandedKeys, loading } = this.state;
    return (
      <Spin spinning={loading}>
        <div className={s.root}>
          <div className={s.rootl}
            ref={(me) => {
              if (me) {
                this.leftDom = me;
              }
            }}>
            <div
              className={s.hr}
              ref={(me) => {
                if (me) {
                  let _this = this;
                  function moveFn(e) {
                    let conDomLeft = document.getElementsByClassName('ant-layout-content')[0].offsetLeft;
                    _this.leftDom.style['flex-basis'] = e.pageX - conDomLeft + 'px'
                  }
                  me.addEventListener('mousedown', (e) => {
                    this.onDragStartPos = e.pageX;
                    document.addEventListener('mousemove', moveFn, false)
                  }, false);
                  document.addEventListener('mouseup', (e) => {
                    document.removeEventListener('mousemove', moveFn, false)
                  }, false)
                }
              }}
            ></div>
            {treeData ? <Tree
              selectText={false}
              modalType="common" //common  drawer  抽屉出现方式或者普通的
              visible
              selectModal="0" //0不可选  1单选(默认)  2多选（暂不可用）
              myFetch={this.props.myFetch}
              upload={this.props.myUpload}
              btnShow={false} //是否显示底部按钮
              disabled={true}
              draggable={false}
              nodeRender={nodeData => {
                return (
                  <span>
                    {nodeData["catName"]}
                  </span>
                );
              }}
              treeProps={{
                showLine: true
              }}
              defaultExpandedKeys={defaultExpandedKeys}
              rightMenuOption={[]}
              nodeClick={(node) => {
                this.setState({
                  parentID: ''
                }, () => {
                  this.setState({
                    defaultExpandedKeys: node.bsid.split(','),
                    parentID: node.id,
                    bsid: node.bsid
                  })
                })
              }}
              data={treeData}
              //键值配置 默认{value:value,label:label,children:children}
              keys={{
                label: "catName",
                value: "id",
                children: "childrenList"
              }}
            /> : null}
          </div>
          <div className={s.rootr}>
            {parentID ? <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              wrappedComponentRef={(me) => {
                this.table = me;
              }}
              fetchConfig={{
                apiName: 'getZxCcResCategoryList',
                otherParams: {
                  parentID,
                  resStyle:'zzbz'
                }
              }}
              method={{
                cbForAdd: (obj) => {
                  if (obj.response.success) this.refresh()
                },
                //修改按钮禁用
                disabledFunEdit: (obj) => {
                  let data = obj.btnCallbackFn.getSelectedRows();
                  if (data.length !== 1) return true;
                  return false;
                },
              }}
              actionBtns={{
                apiName: "getSysMenuBtn",
                otherParams: (obj) => {
                  var props = obj.props;
                  let curRouteData = props.routerInfo.routeData[props.routerInfo.curKey];
                  return {
                    menuParentId: curRouteData._value,
                    tableField: "StatusStandard"
                  }
                }
              }}
              {...config}
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
                  isInTable: false,
                  form: {
                    field: 'parentID',
                    type: 'string',
                    initialValue: parentID,
                    hide: true,
                  }
                },
                {
                  isInTable: false,
                  form: {
                    field: 'bsid',
                    type: 'string',
                    initialValue: bsid,
                    hide: true,
                  }
                },
                {
                  isInTable: false,
                  form: {
                    field: 'isGroup',
                    type: 'string',
                    initialValue: '0',
                    hide: true,
                  }
                },
                {
                  isInTable: false,
                  form: {
                    field: 'resStyle',
                    type: 'string',
                    initialValue: 'zzbz',
                    hide: true,
                  }
                },
                {
                  table: {
                    title: '编号',
                    dataIndex: 'catCode',
                    key: 'catCode',
                    filter: true,
                    onClick: 'detail'
                  },
                  form: {
                    type: 'string',
                    required: true,
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
                    title: '资质名称',
                    dataIndex: 'catName',
                    key: 'catName',
                    filter: true,
                  },
                  form: {
                    type: 'string',
                    required: true,
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
                    title: '是否已下达',
                    dataIndex: 'isGroup',
                    key: 'isGroup',
                    render: (data) => {
                      if (data === '0') {
                        return '否';
                      } else {
                        return '是';
                      }
                    }
                  },
                  isInForm: false
                },
                {
                  table: {
                    title: '备注',
                    dataIndex: 'remarks',
                    key: 'remarks',
                  },
                  form: {
                    type: 'textarea',
                    placeholder: '请输入'
                  }
                }
              ]}
            /> : null}
          </div>
        </div>
      </Spin>
    );
  }
}

export default index;