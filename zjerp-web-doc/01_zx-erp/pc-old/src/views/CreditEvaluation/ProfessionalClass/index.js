import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
const confirm = Modal.confirm;
const configs = {
  antd: {
    rowKey: function (row) {
      return row.id
    },
    size: 'small'
  },
  drawerConfig: {
    width: '900px'
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
      treeData: null,
      parentID: '',
      loading: false,
      bsid: '',
      defaultExpandedKeys: [],
    }
  }
  componentDidMount() {
    this.refresh(true);
  }
  refresh = (updataTree = false) => {
    this.setState({
      treeData: null,
      loading: true
    })
    this.props.myFetch('getZxCrColCategoryTree', {}).then(
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
    const { treeData, loading, parentID, bsid, defaultExpandedKeys } = this.state;
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
              modalType="common" //common  drawer  ?????????????????????????????????
              visible
              selectModal="0" //0?????????  1??????(??????)  2????????????????????????
              myFetch={this.props.myFetch}
              upload={this.props.myUpload}
              btnShow={false} //????????????????????????
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
                  parentID: '',
                }, () => {
                  this.setState({
                    defaultExpandedKeys: node.bsid.split(','),
                    parentID: node.id,
                    bsid: node.bsid,
                  })
                })
              }}
              data={treeData}
              //???????????? ??????{value:value,label:label,children:children}
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
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              wrappedComponentRef={(me) => {
                this.table = me;
              }}
              fetchConfig={{
                apiName: 'getZxCrColCategoryList',
                otherParams: {
                  parentID,
                }
              }}
              {...configs}
              actionBtns={[
                {
                  name: 'add',//??????add del
                  icon: 'plus',//icon
                  type: 'primary',//??????  ?????? primary  [primary dashed danger]
                  label: '??????',
                  formBtns: [
                    {
                      name: 'cancel', //??????????????????
                      type: 'dashed',//??????  ?????? primary
                      label: '??????',
                    },
                    {
                      name: 'submit',//??????add del
                      type: 'primary',//??????  ?????? primary
                      label: '??????',//???????????????????????????????????? 
                      fetchConfig: {//ajax??????
                        apiName: 'addZxCrColCategory',
                      },
                      onClick: (obj) => { obj.response.success && this.refresh() }
                    }
                  ]
                },
                {
                  name: 'edit',//??????add del
                  icon: 'edit',//icon
                  type: 'primary',//??????  ?????? primary  [primary dashed danger]
                  label: '??????',
                  formBtns: [
                    {
                      name: 'cancel', //??????????????????
                      type: 'dashed',//??????  ?????? primary
                      label: '??????',
                    },
                    {
                      name: 'submit',//??????add del
                      type: 'primary',//??????  ?????? primary
                      label: '??????',//???????????????????????????????????? 
                      fetchConfig: {//ajax??????
                        apiName: 'updateZxCrColCategory',
                      },
                      onClick: (obj) => {
                        obj.btnCallbackFn.clearSelectedRows();
                        if (obj.response.success) this.refresh()
                      }
                    }
                  ]
                },
                {
                  name: 'diydel',//??????add del
                  icon: 'delete',//icon
                  type: 'danger',//??????  ?????? primary  [primary dashed danger]
                  label: '??????',
                  disabled: (obj) => {
                    if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                      return false;
                    } else {
                      return true;
                    }
                  },
                  onClick: (obj) => {
                    confirm({
                      content: '?????????????????????????',
                      onOk: () => {
                        this.props.myFetch('batchDeleteUpdateZxCrColCategory', obj.selectedRows).then(({ success, message, data }) => {
                          if (success) {
                            obj.btnCallbackFn.clearSelectedRows();
                            obj.btnCallbackFn.refresh();
                            this.refresh()
                          } else {
                            Msg.error(message);
                          }
                        });
                      }
                    });
                  }
                }
              ]}
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
                    initialValue: bsid,
                    type: 'string',
                    hide: true,
                  }
                },
                {
                  table: {
                    title: '??????????????????',
                    dataIndex: 'catCode',
                    key: 'catCode',
                    filter: true,
                    onClick: 'detail'
                  },
                  form: {
                    type: 'string',
                    required: true,
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
                    dataIndex: 'catName',
                    key: 'catName',
                    filter: true,
                  },
                  form: {
                    type: 'string',
                    required: true,
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
                    dataIndex: 'remark',
                    key: 'remark',
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
                },
                {
                  table: {
                    title: '????????????',
                    dataIndex: 'modifyTime',
                    key: 'modifyTime',
                    format: 'YYYY-MM-DD HH:mm:ss',
                    width: 100
                  },
                  isInForm: false
                },
              ]}
            /> : null}
          </div>
        </div>
      </Spin>
    );
  }
}

export default index;