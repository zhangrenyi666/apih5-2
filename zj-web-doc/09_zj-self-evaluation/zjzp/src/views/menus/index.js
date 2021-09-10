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
  modals = {};
  visibleValue = "";
  endKey = this.depId;
  pageParams = {
    page: 1,
    limit: 10
  };
  inputValue = "";
  state = {
    record: {},
    mData: [],
    gData: [],
    rState: "0",
    selectedRowKeys: [],
    treeSelectedKeys: [],
    expandedKeys: [],
    visibleValue: "",
    editValue: "",
    visible: false
  };
  componentDidMount() {
    this.loadTree();
  }
  loadTree = () => {
    //加载树形结构
    const { myFetch } = this.props;
    Msg.loading("加载部门列表", 0);
    myFetch("getSysMenuAllTree").then(({ success, code, message, data }) => {
      Msg.destroy();
      if (success) {
        this.gData = [data];
        this.endKey = this.depId === "0" ? data.value : this.depId;
        const gDataSimple = this.initSimpleData(this.gData);
        const { keys } = this.getBreadcrumb(this.endKey, gDataSimple);
        this.setState({
          record: {
            menuId: gDataSimple[this.endKey].value,
            menuUrl: gDataSimple[this.endKey].title
          },
          gData: [...this.gData],
          expandedKeys: keys,
          treeSelectedKeys: [this.endKey]
        });
      }
    });
  };
  setSelectedRowKeys = selectedRowKeys => {
    this.setState({ selectedRowKeys });
  };
  setPageParams = newPageParams => {
    this.pageParams = { ...newPageParams };
    this.loadList().then((mData, totalNumber) => {
      this.setState({
        totalNumber,
        mData,
        rState: "0",
        loading: false,
        ...this.pageParams
      });
    });
  };
  loadList = () => {
    const { myFetch } = this.props;
    this.setState({ loading: true });
    let body = { menuId: this.endKey, ...this.pageParams };
    return new Promise(resolve => {
      myFetch("getSysUserListByBg", body).then(
        ({ success, data, totalNumber }) => {
          if (success) {
            resolve(data, totalNumber);
          }
        }
      );
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
      dispatch(replace(`${moduleName}menus/${this.endKey}`));
      const gDataSimple = this.initSimpleData(this.gData);
      this.setState({
        record: {
          menuId: gDataSimple[this.endKey].value,
          menuUrl: gDataSimple[this.endKey].title
        },
        treeSelectedKeys
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
  onDrop = info => {
    const { myFetch } = this.props;
    let body = {};
    const dropKey = info.node.props.eventKey; //拖拽到的节点的value
    const dropPos = info.node.props.pos.split("-"); //拖拽到的节点位置
    const dragKey = info.dragNode.props.eventKey; //被拖拽的节点的value
    //1、计算被拖拽到目标节点的哪个方位（0子-1前1后）
    const dropPosition =
      info.dropPosition - Number(dropPos[dropPos.length - 1]);
    //2、把被拖拽的节点暂存
    let dragObj;
    this.getLoopMatch(this.gData, dragKey, (item, index, arr) => {
      dragObj = item;
      if (dragObj.valuePid !== "0") {
        //被拖拽节点不是根节点的话就从树形数据中移除它
        arr.splice(index, 1);
        body["departmentToMove"] = item.value;
      }
    });
    if (dragObj.valuePid === "0") {
      Msg.info("根节点不可拖拽", 1);
      return;
    }
    if (info.dropToGap) {
      //是否与目标节点同级
      let ar;
      let i;
      this.getLoopMatch(this.gData, dropKey, (item, index, arr) => {
        ar = arr;
        i = index;
      });
      if (ar[i].valuePid === "0") {
        //不能与根节点同级
        Msg.info("不能与根节点同级", 1);
        return;
      } else {
        body["departmentParent"] = ar[i].valuePid;
        if (dropPosition === -1) {
          if (i !== 0) {
            body["departmentBefore"] = ar[i - 1].value;
          }
          body["departmentAfter"] = ar[i].value;
          //3.1插入到目标节点之前
          ar.splice(i, 0, dragObj);
        } else {
          if (i !== ar.length - 1) {
            body["departmentAfter"] = ar[i + 1].value;
          }
          body["departmentBefore"] = ar[i].value;
          //3.2插入到目标节点之后
          ar.splice(i + 1, 0, dragObj);
        }
      }
    } else {
      //3.3插入到目标节点子集
      this.getLoopMatch(this.gData, dropKey, item => {
        item.children = item.children || [];
        item.children.push(dragObj);
        body["departmentParent"] = item.value;
      });
    }
    this.setState({
      gData: [...this.gData]
    });
    myFetch("moveUpdateSysDepartment", body).then(
      ({ success, code, message, data }) => {
        if (!success) {
          Msg.error(message, 1.2, () => {
            this.loadTree();
          });
        }
      }
    );
  };
  handle = newState => {
    if (newState.rState === "0") {
      this.loadTree();
    } else {
      this.setState(newState);
    }
  };
  render() {
    const { myFetch } = this.props;
    const {
      rState,
      record,
      gData,
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
          switch (handleType) {
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
              let body = [
                {
                  menuId: value
                }
              ];
              myFetch("batchDeleteUpdateSysMenu", body).then(
                ({ success, message }) => {
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
            <div>
              <a onClick={() => onClick(1)}>新建子菜单</a>
            </div>
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
                      title: `您确定要删除部门【${label}】么?`,
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
                  menuParentId: valuePid,
                  menuName: inputValue,
                  menuFlag: 1
                };
                Msg.loading("正在新建...");
                myFetch("addSysMenu", body).then(
                  ({ success, code, message, data }) => {
                    Msg.destroy();
                    if (success) {
                      const {
                        menuId,
                        menuName,
                        menuParentId,
                        children = []
                      } = data;
                      this.getLoopMatch(
                        this.gData,
                        value,
                        (item, index, arr) => {
                          item.label = menuName;
                          item.value = menuId;
                          item.valuePid = menuParentId;
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
                  menuId: value,
                  menuName: inputValue
                };
                myFetch("updateSysMenu", body).then(({ success, message }) => {
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
            const editColumns = [
              {
                label: "menuId",
                type: "hidden",
                dataIndex: "menuId"
              },
              {
                label: "JSON数据",
                dataIndex: "menuUrl"
              }
            ];
            let rCon;
            switch (rState) {
              case "0":
              default:
                rCon = (
                  <WrapMemberEdit
                    {...this.props}
                    handle={this.handle}
                    columns={editColumns}
                    dataSource={record}
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
        myFetch("updateSysMenuDetails", body).then(({ success, message }) => {
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
      const { dataIndex, label, type, treeData, k } = item;
      let _item;
      switch (type) {
        case "PullPersion":
          let loop = datas => {
            return datas.map(data => {
              return {
                menuName: data.label,
                menuId: data.value,
                type: data.type,
                children: loop(data.children)
              };
            });
          };
          let _treeData = loop(treeData);
          _item = (
            <FormItem key={index} label={label} {...formItemLayout}>
              {getFieldDecorator(dataIndex, {
                valuePropName: "defaultData",
                initialValue: dataSource[dataIndex] || [],
                rules: [
                  {
                    required: true,
                    type: "array",
                    whitespace: true,
                    message: "必填"
                  }
                ]
              })(
                <PullPersion
                  myFetch={myFetch}
                  edit={true} //是否是编辑模式 默认true   Boole
                  selectType={"1"} //0部门和人员都能选 1只能选部门 2只能选人员   默认0   string
                  treeData={_treeData} //树结构数据  {} || []   //根节点可以是多个可以是一个  array || object
                  k={k} // 默认就是用以下这些键值  （针对树结构的）
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
            <FormItem key={index} label={label} {...formItemLayout}>
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
          <Button htmlType="submit">{"保存"}</Button>
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

export default basic(Demo);
