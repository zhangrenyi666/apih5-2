import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Modal, Spin } from 'antd';
import s from "./style.less";
import Tree from "../../modules/tree";
import QnnForm from "../../modules/qnn-table/qnn-form";
const confirm = Modal.confirm;
const configs = {
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
      treeData: null,
      id: '',
      loading: false,
      formData: null,
      flag: false,
      defaultExpandedKeys: [],
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
          formData: data.length ? data[0] : {},
          id: data.length ? data[0].id : '',
          defaultExpandedKeys: data.length ? data[0].bsid.split(',') : [],
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
  const { treeData, loading, id, formData, flag, defaultExpandedKeys} = this.state;
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
                id: '',
                formData: null,
                flag: node.children.length ? true : false
              }, () => {
                this.setState({
                  id: node.id,
                  formData: {
                    catCode: node.catCode,
                    catName: node.catName,
                    remark: node.remark,
                  }
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
          <div style={{ height: '65px' }}>
            {formData ? <QnnForm
              fetch={this.props.myFetch}
              upload={this.props.myUpload} //必须返回一个promise
              //内部自带发送ajax的组件发送请求时会自动加到head头里面的数据 （上传会用到）
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
                  label: '编号',
                  field: 'catCode',
                  type: 'string',
                  disabled: true,
                  placeholder: '请输入',
                  span: 8
                },
                {
                  label: '资质名称',
                  field: 'catName',
                  type: 'string',
                  disabled: true,
                  placeholder: '请输入',
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
              apiName: 'getZxCcOrgResourceList',
              otherParams: {
                resID: id,
                resStyle:'zzbz'
              }
            }}
            desc={flag ? '只能在叶子节点才能新增具体资源' : null}
            {...configs}
            actionBtns={flag ? [] : [
              {
                name: 'add',//内置add del
                icon: 'plus',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '新增',
                formBtns: [
                  {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                  },
                  {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '保存',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                      apiName: 'addZxCcOrgResource',
                    }
                  }
                ]
              },
              {
                name: 'edit',//内置add del
                icon: 'edit',//icon
                type: 'primary',//类型  默认 primary  [primary dashed danger]
                label: '修改',
                formBtns: [
                  {
                    name: 'cancel', //关闭右边抽屉
                    type: 'dashed',//类型  默认 primary
                    label: '取消',
                  },
                  {
                    name: 'submit',//内置add del
                    type: 'primary',//类型  默认 primary
                    label: '保存',//提交数据并且关闭右边抽屉 
                    fetchConfig: {//ajax配置
                      apiName: 'updateZxCcOrgResource',
                    },
                    onClick: (obj) => {
                      obj.btnCallbackFn.clearSelectedRows();
                    }
                  }
                ]
              },
              {
                name: 'diydel',//内置add del
                icon: 'delete',//icon
                type: 'danger',//类型  默认 primary  [primary dashed danger]
                label: '删除',
                disabled: (obj) => {
                  if (obj.btnCallbackFn.getSelectedRows().length > 0) {
                    return false;
                  } else {
                    return true;
                  }
                },
                onClick: (obj) => {
                  confirm({
                    content: '确定删除此数据吗?',
                    onOk: () => {
                      this.props.myFetch('batchDeleteUpdateZxCcOrgResource', obj.selectedRows).then(({ success, message, data }) => {
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
                  field: 'resStyle',
                  type: 'string',
                  initialValue: 'zzbz',
                  hide: true,
                }
              },
              {
                isInTable: false,
                form: {
                  field: 'resID',
                  type: 'string',
                  initialValue: id,
                  hide: true,
                }
              },
              {
                table: {
                  title: '分类代码',
                  dataIndex: 'resCode',
                  key: 'resCode',
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
                  title: '等级',
                  dataIndex: 'resName',
                  key: 'resName',
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
                  title: '是否局下达',
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
                      label: "否",
                      value: "0"
                    },
                    {
                      label: "是",
                      value: "1"
                    }
                  ],
                  allowClear: false,
                  showSearch: true,
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
                  title: '备注',
                  dataIndex: 'remark',
                  key: 'remark',
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