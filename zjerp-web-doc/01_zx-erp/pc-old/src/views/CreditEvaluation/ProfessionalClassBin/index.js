import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
import QnnForm from "../../modules/qnn-table/qnn-form";
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
      id: '',
      loading: false,
      bsid: '',
      defaultExpandedKeys: [],
      formData: null,
      flag: false
    }
  }
  componentDidMount() {
    this.refresh(true);
  }
  refresh = (updataTree = false) => {
    this.setState({
      loading: true
    })
    this.props.myFetch('getZxCrColCategoryTree', {}).then(
      ({ data, success, message }) => {
        if (success && updataTree) {
          this.setState({
            treeData: data,
            loading: false,
            id: data.length ? data[0].id : '',
            defaultExpandedKeys: data.length ? data[0].bsid.split(',') : [],
            formData: data.length ? data[0] : {},
          })
        } else if (success) {
          this.setState({
            treeData: data,
            loading: false,
            id: data.length ? data[0].id : '',
            formData: data.length ? data[0] : {},
          })
        } else {
          Msg.error(message)
        }
      }
    );
  }
  render() {
    const { treeData, loading, id, defaultExpandedKeys, formData, flag } = this.state;
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
                  id: '',
                  formData: null,
                }, () => {
                  this.setState({
                    id: node.id,
                    flag: node.children.length ? true : false,
                    formData: {
                      catCode: node.catCode,
                      catName: node.catName,
                      remark: node.spec,
                    }
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
            <div>
              {formData ? <QnnForm
                fetch={this.props.myFetch}
                upload={this.props.myUpload} //??????????????????promise
                //??????????????????ajax???????????????????????????????????????head?????????????????? ?????????????????????
                headers={{
                  token: this.props.loginAndLogoutInfo.loginInfo.token
                }}
                wrappedComponentRef={(me) => this.qnnForm = me}
                data={formData}
                formItemLayout={{
                  labelCol: {
                    xs: { span: 7 },
                    sm: { span: 7 }
                  },
                  wrapperCol: {
                    xs: { span: 17 },
                    sm: { span: 17 }
                  }
                }}
                formConfig={[
                  {
                    label: '??????????????????',
                    field: 'catCode',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 8
                  },
                  {
                    label: '????????????',
                    field: 'catName',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 8
                  },
                  {
                    label: '??????',
                    field: 'remark',
                    type: 'string',
                    disabled: true,
                    placeholder: '?????????',
                    span: 8
                  },
                ]}
              /> : null}
            </div>
            {id ? <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              wrappedComponentRef={(me) => {
                this.table = me;
              }}
              fetchConfig={{
                apiName: 'getZxCrColResourceList',
                otherParams: {
                  categoryID: id,
                }
              }}
              desc={flag ? '?????????????????????????????????????????????' : null}
              {...configs}
              actionBtns={flag ? [] : [
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
                        apiName: 'addZxCrColResource',
                      }
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
                        apiName: 'updateZxCrColResource',
                      },
                      onClick: (obj) => {obj.btnCallbackFn.clearSelectedRows()}
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
                        this.props.myFetch('batchDeleteUpdateZxCrColResource', obj.selectedRows).then(({ success, message, data }) => {
                          if (success) {
                            obj.btnCallbackFn.clearSelectedRows();
                            obj.btnCallbackFn.refresh();
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
                    field: 'categoryID',
                    type: 'string',
                    initialValue: id,
                    hide: true,
                  }
                },
                {
                  table: {
                    title: '????????????',
                    dataIndex: 'resCode',
                    key: 'resCode',
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
                    dataIndex: 'resName',
                    key: 'resName',
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
                    title: '????????????',
                    dataIndex: 'enableFlag',
                    key: 'enableFlag',
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
                    title: '???????????????',
                    dataIndex: 'isGroup',
                    key: 'isGroup',
                    width: 100,
                    type: 'select'
                  },
                  form: {
                    required: true,
                    type: 'select',
                    optionConfig: {
                      label: 'label',
                      value: 'value',
                    },
                    optionData: [
                      {
                        label: "???",
                        value: "0"
                      },
                      {
                        label: "???",
                        value: "1"
                      }
                    ],
                    allowClear: false,
                    showSearch: true,
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
                    dataIndex: 'createTime',
                    key: 'createTime',
                    format: 'YYYY-MM-DD HH:mm:ss',
                    width: 100
                  },
                  form: {
                    label: '????????????',
                    field: 'createTime',
                    type: 'date',
                    initialValue: new Date(),
                    placeholder: '?????????',
                    spanForm: 12,
                    required: true,
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
              ]}
            /> : null}
          </div>
        </div>
      </Spin>
    );
  }
}

export default index;