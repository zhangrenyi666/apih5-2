import React, { Component } from "react";
import {
  Button,
  Tree,
  message as Msg,
  Popover,
  Input,
  Modal,
  Form,
  Layout
} from "antd";
import PullPersion from "../modules/pullPersion";
// import styles from "./index.less";
import { basic } from "../modules/layouts";
import { replace } from "connected-react-router";

const FormItem = Form.Item;
const confirm = Modal.confirm;
const TreeNode = Tree.TreeNode;
class Demo extends Component {
  depId = this.props.match.params.depId || "0";
  gData = [];
  pData = [];
  tData = [];
  modals = {};
  visibleValue = "";
  endKey = this.depId;
  inputValue = "";
  state = {
    record: {},
    mData: [],
    nData: [],
    gData: [],
    pData: [],
    rState: "0",
    selectedRowKeys: [],
    treeSelectedKeys: [],
    expandedKeys: [],
    visibleValue: "",
    editValue: "",
    visible: false
  };
  componentDidMount() {
    const { myFetch } = this.props;
    myFetch("getSysDepartmentUserAllTree").then(({ success, data }) => {
      if (success) {
        this.pData = [data];
        myFetch("getSysMenuAllTree").then(({ success, data }) => {
          if (success) {
            this.tData = [data];
            this.loadTree();
          }
        });
      }
    });
  }
  loadTree = () => {
    //加载树形结构
    const { myFetch } = this.props;
    Msg.loading("加载部门列表", 0);
    myFetch("getSysRoleAllTree").then(({ success, data }) => {
      Msg.destroy();
      if (success) {
        this.gData = [data];
        this.endKey = this.depId === "0" ? data.value : this.depId;
        const gDataSimple = this.initSimpleData(this.gData);
        const { keys } = this.getBreadcrumb(this.endKey, gDataSimple);
        this.loadList().then(mData => {
          this.setState({
            rState: "0",
            loading: false,
            mData,
            selectedRowKeys: [],
            tData: [...this.tData],
            pData: [...this.pData],
            gData: [...this.gData],
            expandedKeys: keys,
            treeSelectedKeys: [this.endKey]
          });
        });
      }
    });
  };
  setSelectedRowKeys = selectedRowKeys => {
    this.setState({ selectedRowKeys });
  };
  loadList = () => {
    const { myFetch } = this.props;
    this.setState({ loading: true });
    let body = { roleId: this.endKey };
    return new Promise(resolve => {
      myFetch("getSysRoleUserList", body).then(({ success, data }) => {
        if (success) {
          resolve(data);
        }
      });
    });
  };
  loadList2 = () => {
    const { myFetch } = this.props;
    this.setState({ loading: true });
    let body = { roleId: this.endKey };
    return new Promise(resolve => {
      myFetch("getSysRoleMenuList", body).then(({ success, data }) => {
        if (success) {
          resolve(data);
        }
      });
    });
  };

  initSimpleData = datasource => {
    let simpleData = {};
    const loop = (data = []) => {
      data.forEach(item => {
        const { value, children = [] } = item;
        simpleData[value] = item;
        if (children.length) {
          loop(children);
        }
      });
    };
    loop(datasource);
    return simpleData;
  };
  getLoopMatch = (data, key, callback) => {
    //获取递归匹配的数据
    data.forEach((item, index, arr) => {
      if (item.value === key) {
        return callback(item, index, arr);
      }
      if (item.children) {
        return this.getLoopMatch(item.children, key, callback);
      }
    });
  };
  getBreadcrumb = (endKey, simpleData) => {
    //获取面包屑数据
    let keys = [],
      datas = [];
    while (endKey) {
      if (simpleData[endKey]) {
        const { valuePid } = simpleData[endKey];
        keys.unshift(endKey);
        datas.unshift(simpleData[endKey]);
        endKey = valuePid;
      } else {
        endKey = null;
      }
    }
    return { keys, datas };
  };
  onSelect = (treeSelectedKeys, { node }) => {
    const {
      dispatch,
      routerInfo: { curKey, routeData }
    } = this.props;
    const { moduleName } = routeData[curKey];
    if (this.visibleValue !== node.props.eventKey && !this.state.editValue) {
      this.visibleValue = "";
      this.endKey = node.props.eventKey;
      dispatch(replace(`${moduleName}roles/${this.endKey}`));
      this.loadList().then(mData => {
        this.setState({
          selectedRowKeys: [],
          treeSelectedKeys,
          mData,
          rState: "0",
          loading: false
        });
      });
    }
  };
  onExpand = expandedKeys => {
    this.setState({
      expandedKeys
    });
  };
  onDragEnter = info => {
    this.setState({
      expandedKeys: info.expandedKeys
    });
  };
  onRightClick = info => {
    this.setState({ visibleValue: info.node.props.eventKey });
  };
  handle = newState => {
    if (newState.rState === "0") {
      this.loadList().then(mData => {
        this.setState({
          rState: "0",
          loading: false,
          mData,
          selectedRowKeys: []
        });
      });
    } else if (newState.rState === "1") {
      this.loadList2().then(nData => {
        this.setState({
          rState: "1",
          loading: false,
          nData,
          selectedRowKeys: []
        });
      });
    }
    this.setState(newState);
  };
  render() {
    const { myFetch } = this.props;
    const {
      rState,
      tData,
      gData,
      mData,
      nData,
      pData,
      expandedKeys,
      treeSelectedKeys,
      visibleValue,
      editValue
    } = this.state;
    const loop = data =>
      data.map(item => {
        const { children = [], value, label, type, valuePid } = item;
        const onClick = handleType => {
          this.visibleValue = value;
          let body
          switch (handleType) {
            case 0:
              myFetch("syncWeChatToSysInfo", body).then(
                ({ success, message }) => {
                  if (!success) {
                    Msg.error(message, 1.2, () => {
                      this.loadTree();
                    });
                  }
                }
              );
              break;
            case 1:
              let expandedKeys = [].concat([value], this.state.expandedKeys);
              let addKey = Math.random();
              let addData = {
                value: addKey,
                label: "",
                type: "1",
                valuePid: value,
                children: [],
                isAdd: true
              };
              this.getLoopMatch(this.gData, value, (item, index, arr) => {
                item.children = item.children || [];
                item.children.push(addData);
              });
              this.inputValue = "";
              this.setState({
                gData: [...this.gData],
                editValue: addKey,
                expandedKeys
              });
              break;
            case 2:
              this.inputValue = label;
              this.setState({ editValue: value });
              break;
            case 3:
              this.getLoopMatch(this.gData, value, (item, index, arr) => {
                arr.splice(index, 1);
              });
              this.setState({
                gData: [...this.gData]
              });
              body = [
                {
                  roleId: value
                }
              ];
              myFetch("batchDeleteUpdateSysRole", body).then(
                ({ success,   message}) => {
                  if (!success) {
                    Msg.error(message, 1.2, () => {
                      this.loadTree();
                    });
                  }
                }
              );
              break;
            default:
              break;
          }
        };
        const content = (
          <div>
            {valuePid === "0" ? (
              <div>
                <a onClick={() => onClick(1)}>新建角色</a>
              </div>
            ) : null}
            {valuePid !== "0" ? (
              <div>
                <a onClick={() => onClick(2)}>重命名</a>
              </div>
            ) : null}
            {valuePid !== "0" ? (
              <div>
                <a
                  onClick={() => {
                    confirm({
                      title: `您确定要删除角色【${label}】么?`,
                      content: ``,
                      okText: "确认",
                      cancelText: "取消",
                      onOk: () => onClick(3)
                    });
                  }}
                >
                  删除
                </a>
              </div>
            ) : null}
          </div>
        );
        const LabelCom = props => {
          const {
            data: { label, valuePid, value, isAdd = false },
            visible,
            edit
          } = props;
          const changeLabel = e => {
            let inputValue = e.target.value.toString().trim();
            if (inputValue) {
              if (isAdd) {
                //新政
                let body = {
                  roleParentId: valuePid,
                  roleName: inputValue,
                  roleFlag: 1
                };
                Msg.loading("正在新建...");
                myFetch("addSysRole", body).then(
                  ({ success, message, data }) => {
                    Msg.destroy();
                    if (success) {
                      const {
                        roleId,
                        roleName,
                        roleParentId,
                        children = []
                      } = data;
                      this.getLoopMatch(
                        this.gData,
                        value,
                        (item, index, arr) => {
                          item.label = roleName;
                          item.value = roleId;
                          item.valuePid = roleParentId;
                          item.children = children;
                          item.isAdd = false;
                        }
                      );
                      this.setState({
                        gData: [...this.gData],
                        editValue: ""
                      });
                    } else {
                      Msg.error(message, 1.2, () => {
                        this.loadTree();
                      });
                    }
                  }
                );
              } else {
                //修改
                this.getLoopMatch(this.gData, value, (item, index, arr) => {
                  item.label = inputValue;
                });
                let body = {
                  roleId: value,
                  roleName: inputValue
                };
                myFetch("updateSysRole", body).then(({ success, message }) => {
                  if (!success) {
                    Msg.error(message, 1.2, () => {
                      this.loadTree();
                    });
                  }
                });
                this.setState({
                  gData: [...this.gData],
                  editValue: ""
                });
              }
            } else {
              if (isAdd) {
                this.getLoopMatch(this.gData, value, (item, index, arr) => {
                  arr.splice(index, 1);
                });
              }
              this.setState({
                gData: [...this.gData],
                editValue: ""
              });
            }
          };
          if (edit) {
            return (
              <Input
                autoFocus
                defaultValue={this.inputValue}
                placeholder={"请输入部门名称"}
                onBlur={changeLabel}
                onPressEnter={changeLabel}
              />
            );
          } else {
            return (
              <Popover visible={visible} placement="rightTop" content={content}>
                {label}
              </Popover>
            );
          }
        };
        if (children.length) {
          return (
            <TreeNode
              key={value}
              title={
                <LabelCom
                  data={item}
                  edit={editValue === value}
                  visible={visibleValue === value}
                />
              }
            >
              {loop(children)}
            </TreeNode>
          );
        }
        return type !== "2" ? (
          <TreeNode
            key={value}
            title={
              <LabelCom
                data={item}
                edit={editValue === value}
                visible={visibleValue === value}
              />
            }
          />
        ) : null;
      });
    return (
      <Layout
        style={{ background: "transparent" }}
        onClick={e => {
          if (visibleValue) {
            this.setState({
              visibleValue: ""
            });
          }
          return false;
        }}
      >
        <Layout.Sider style={{ background: "transparent" }}>
          <Tree
            draggable={false}
            disabled={!!editValue}
            onExpand={this.onExpand}
            onSelect={this.onSelect}
            expandedKeys={expandedKeys}
            selectedKeys={treeSelectedKeys}
            onDragEnter={this.onDragEnter}
            onDrop={this.onDrop}
            onRightClick={this.onRightClick}
          >
            {loop(gData)}
          </Tree>
        </Layout.Sider>
        <Layout.Content>
          {(() => {
            const memberEditColumns = [
              {
                label: "roleId",
                type: "hidden",
                dataIndex: "roleId"
              },
              {
                label: "人员选择",
                dataIndex: "sysRoleUserList",
                type: "PullPersion",
                treeData: pData
              }
            ];
            const menuEditColumns = [
              {
                label: "roleId",
                type: "hidden",
                dataIndex: "roleId"
              },
              {
                label: "菜单选择",
                dataIndex: "sysRoleMenuList",
                type: "Menu",
                treeData: tData
              }
            ];
            let rCon;
            switch (rState) {
              case "1":
                rCon = (
                  <WrapMenuEdit
                    {...this.props}
                    handle={this.handle}
                    columns={menuEditColumns}
                    dataSource={{ sysRoleMenuList: nData, roleId: this.endKey }}
                  />
                );
                break;
              case "0":
              default:
                rCon = (
                  <WrapMemberEdit
                    {...this.props}
                    handle={this.handle}
                    columns={memberEditColumns}
                    dataSource={{ sysRoleUserList: mData, roleId: this.endKey }}
                  />
                );
                break;
            }
            return rCon;
          })()}
        </Layout.Content>
      </Layout>
    );
  }
}

class MemberEdit extends Component {
  handleSubmit = event => {
    const { form, myFetch } = this.props;
    form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const body = { ...values };

        myFetch("updateSysRoleUser", body).then(({ success, message }) => {
          if (success) {
            Msg.success(message, 1.2, () => {
              this.handle({ rState: "0" });
            });
          } else {
            Msg.error(message, 1.2);
          }
        });
      }
    });
    event && event.preventDefault && event.preventDefault();
    return false;
  };
  handle = newState => {
    this.props.handle(newState);
  };
  render() {
    const formItemLayout = {
      labelCol: { span: 4 },
      wrapperCol: { span: 12 }
    };
    const {
      form: { getFieldDecorator },
      columns,
      dataSource,
      myFetch
    } = this.props;
    const formList = columns.map((item, index) => {
      const { dataIndex, label, type, treeData } = item;
      let _item;
      switch (type) {
        case "PullPersion":
          // let loop = (datas) => {
          //     return datas.map((data) => {
          //         return {
          //             roleName: data.label,
          //             roleId: data.value,
          //             type: data.type,
          //             children: loop(data.children)
          //         }
          //     })
          // }
          // let _treeData = loop(treeData)
          _item = (
            <FormItem key={index} label={label} {...formItemLayout}>
              {getFieldDecorator(dataIndex, {
                valuePropName: "defaultData",
                initialValue: dataSource[dataIndex] || []
              })(
                <PullPersion
                  search
                  help
                  searchApi="getSysDepartmentAllTree"
                  searchParamsKey="search" //搜索文字的K 默认是'searchText'   [string]
                  myFetch={myFetch}
                  edit={true} //是否是编辑模式 默认true   Boole
                  treeData={treeData} //树结构数据  {} || []   //根节点可以是多个可以是一个  array || object
                />
              )}
            </FormItem>
          );
          break;
        case "hidden":
          getFieldDecorator(dataIndex, {
            initialValue: dataSource[dataIndex] || ""
          });
          break;
        default:
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                initialValue: dataSource[dataIndex] || ""
              })(<Input placeholder={label} />)}
            </FormItem>
          );
          break;
      }
      return _item;
    });
    return (
      <Form onSubmit={this.handleSubmit}>
        <Button.Group style={{ marginBottom: "12px" }}>
          <Button htmlType="submit">{"保存人员修改"}</Button>
          <Button
            onClick={() => {
              this.handle({ rState: "1" });
            }}
          >
            {"切换→菜单选择"}
          </Button>
        </Button.Group>
        {formList}
      </Form>
    );
  }
}
const WrapMemberEdit = Form.create({
  mapPropsToFields(props) {
    return {
      dataSource: Form.createFormField({
        ...props.dataSource
      })
    };
  }
})(MemberEdit);
class MenuEdit extends Component {
  state = {
    selectedKeys: []
  };
  handleSubmit = event => {
    const { form, myFetch } = this.props;
    form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const body = {
          ...values,
          sysRoleMenuList: values.sysRoleMenuList.map(value => {
            return { value };
          })
        };
        myFetch("updateSysRoleMenu", body).then(({ success, message }) => {
          if (success) {
            Msg.success(message, 1.2, () => {
              this.handle({ rState: "0" });
            });
          } else {
            Msg.error(message, 1.2);
          }
        });
      }
    });
    event && event.preventDefault && event.preventDefault();
    return false;
  };
  handle = newState => {
    this.props.handle(newState);
  };
  onCheck = checkedKeys => {
    const {
      form: { setFieldsValue }
    } = this.props;
    setFieldsValue({ sysRoleMenuList: checkedKeys });
  };
  onSelect = (selectedKeys, info) => {
    console.log("onSelect", info);
    this.setState({ selectedKeys });
  };
  renderTreeNodes = data => {
    return data.map(item => {
      if (item.children) {
        return (
          <TreeNode title={item.label} key={item.value} dataRef={item}>
            {this.renderTreeNodes(item.children)}
          </TreeNode>
        );
      }
      return <TreeNode {...item} />;
    });
  };
  render() {
    const {
      form: { getFieldDecorator },
      dataSource,
      columns
    } = this.props;
    const formList = columns.map((item, index) => {
      const { dataIndex, label, type, treeData } = item;
      let _item;
      switch (type) {
        case "Menu":
          _item = (
            <FormItem key={index} label={label}>
              {getFieldDecorator(dataIndex, {
                valuePropName: "checkedKeys",
                initialValue:
                  dataSource[dataIndex].map(valueObj => valueObj.value) || []
              })(
                <Tree
                  checkable
                  defaultExpandAll
                  onCheck={this.onCheck}
                  onSelect={this.onSelect}
                  selectedKeys={this.state.selectedKeys}
                >
                  {this.renderTreeNodes(treeData)}
                </Tree>
              )}
            </FormItem>
          );
          break;
        case "hidden":
          getFieldDecorator(dataIndex, {
            initialValue: dataSource[dataIndex] || ""
          });
          break;
        default:
          break;
      }
      return _item;
    });

    return (
      <Form onSubmit={this.handleSubmit}>
        <Button.Group style={{ marginBottom: "12px" }}>
          <Button htmlType="submit">{"保存菜单修改"}</Button>
          <Button
            onClick={() => {
              this.handle({ rState: "0" });
            }}
          >
            {"切换→人员选择"}
          </Button>
        </Button.Group>
        {formList}
      </Form>
    );
  }
}
const WrapMenuEdit = Form.create({
  mapPropsToFields(props) {
    return {
      dataSource: Form.createFormField({
        ...props.dataSource
      })
    };
  }
})(MenuEdit);

export default basic(Demo);
