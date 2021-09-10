import React, { Component } from "react";
import {
  Button,
  Tree,
  message as Msg,
  Popover,
  Select,
  Icon,
  Input,
  Modal,
  Form,
  Table,
  List,
  Layout,
  Upload,
  Tooltip
} from "antd";
import PullPersion from "../modules/pullPersion";
import styles from "./index.less";
import { basic } from "../modules/layouts";
import { replace } from "connected-react-router";
const Option = Select.Option;
const FormItem = Form.Item;
const confirm = Modal.confirm;
const TreeNode = Tree.TreeNode;
// 兰州 "无", "技术员", "技术负责人", "驻地监理", "总承包", "项目公司"
// 东二环 "无", "技术员", "质检员", "质检负责人", "监理员", "监理组长"
const ext1SeletListDatas = [
  "无",
  "班组长",
  "施工单位",
  "监理",
  "总承包",
  "项目公司"
];

//最多能上传多少张身份证照片
const idLen = 1;
//最多能上传多少责任清单
const sysFilesResponsibilitylListLen = 1;

//文件上传后转换
const normFile = e => {
  const { fileList } = e;
  let newFileList = fileList.map((item, index) => {
    //当失败时弹出失败信息。但是有时候成功需要弹出信息时也可以将success设置为false
    if (item && item.response && !item.response.success) {
      if (index === fileList.length - 1) {
        Msg.info(item.response.message);
      }
    }
    if (item.response && item.response.success && item.response.data) {
      if (!item.response.data.uid) {
        item.response.data.uid = index;
      }
      return {
        ...item,
        ...item.response.data,
        status: "done",
        name: item.response.data.name,
        url: item.response.data.url,
        thumbUrl: item.response.data.thumbUrl
      };
    }
    return { ...item };
  });

  return newFileList;
};

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
    myFetch("getSysDepartmentAllTree").then(
      ({ success,  data }) => {
        Msg.destroy();
        if (success) {
          this.gData = [data];
          this.endKey = this.depId === "0" ? data.value : this.depId;
          const gDataSimple = this.initSimpleData(this.gData);
          const { keys } = this.getBreadcrumb(this.endKey, gDataSimple);
          this.loadList().then(({ mData, totalNumber }) => {
            this.setState(
              {
                rState: "0",
                loading: false,
                totalNumber,
                mData,
                selectedRowKeys: [],
                ...this.pageParams,
                gData: [...this.gData],
                expandedKeys: keys,
                treeSelectedKeys: [this.endKey]
              },
              () => {
                if (!this.state.tableHeight) {
                  this.setTreeContainerHeight();
                }
              }
            );
          });
        }
      }
    );
  };
  setSelectedRowKeys = selectedRowKeys => {
    this.setState({ selectedRowKeys });
  };
  setPageParams = newPageParams => {
    const { searchText } = this.state;
    this.pageParams = { ...newPageParams };
    this.loadList(searchText).then(({ mData, totalNumber }) => {
      this.setState({
        totalNumber,
        mData,
        rState: "0",
        loading: false,
        ...this.pageParams
      });
    });
  };
  loadList = searchText => {
    const { myFetch } = this.props;
    this.setState({ loading: true });
    let body = {
      departmentId: this.endKey,
      ...this.pageParams,
      search: searchText
    };
    return new Promise(resolve => {
      myFetch("getSysUserListByBg", body).then(
        ({ success, code, message, data, totalNumber }) => {
          if (success) {
            resolve({ mData: data, totalNumber });
          }
        }
      );
    });
  };

  setTreeContainerHeight = () => {
    let tableHeight = document.getElementsByTagName("table")[0].offsetHeight;
    // console.log(tableHeight);
    this.setState({
      tableHeight: tableHeight || ""
    });
  };

  refresh = searchText => {
    this.loadList(searchText).then(({ mData, totalNumber }) => {
      this.setState({
        totalNumber,
        mData,
        rState: "0",
        loading: false,
        ...this.pageParams
      });
    });
  };
  searchText = (searchText = "") => {
    this.setState({
      searchText
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
        const {  valuePid } = simpleData[endKey];
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
    const { searchText } = this.state;
    const {
      dispatch,
      routerInfo: { curKey, routeData }
    } = this.props;
    const { moduleName } = routeData[curKey];
    if (this.visibleValue !== node.props.eventKey && !this.state.editValue) {
      this.visibleValue = "";
      this.endKey = node.props.eventKey;
      dispatch(replace(`${moduleName}contacts/${this.endKey}`));
      this.loadList(searchText).then(({ mData, totalNumber }) => {
        this.setState({
          totalNumber,
          selectedRowKeys: [],
          treeSelectedKeys,
          mData,
          rState: "0",
          loading: false,
          ...this.pageParams
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
      this.loadList().then(({ mData, totalNumber }) => {
        this.setState({
          searchText: "",
          rState: "0",
          loading: false,
          totalNumber,
          mData,
          selectedRowKeys: [],
          ...this.pageParams
        });
      });
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
      mData,
      expandedKeys,
      selectedRowKeys,
      treeSelectedKeys,
      visibleValue,
      editValue,
      loading,
      totalNumber,
      page,
      limit,
      tableHeight = ""
    } = this.state;
    const loop = data =>
      data.map(item => {
        let body;
        const { children = [], value, label, type, valuePid } = item;
        const onClick = handleType => {
          this.visibleValue = value;
          switch (handleType) {
            case 0:
              // let body = {};
              myFetch("syncWeChatToSysInfo", body).then(
                ({ success, message}) => {
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
                  departmentId: value
                }
              ];
              myFetch("batchDeleteUpdateSysDepartment", body).then(
                ({ success, code, message, data }) => {
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
        //   <a onClick={() => {
        //      confirm({
        //          title: `您确定要同步微信数据么?`,
        //          content: ``,
        //          okText: "确认",
        //          cancelText: "取消",
        //          onOk: () => onClick(0),
        //      });
        //  }}>同步微信数据</a>
        const content = (
          <div>
            {valuePid === "0" ? <div /> : null}
            <div>
              <a onClick={() => onClick(1)}>新建子部门</a>
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
                  departmentParentId: valuePid,
                  departmentName: inputValue,
                  departmentFlag: 1
                };
                Msg.loading("正在新建...");
                myFetch("addSysDepartment", body).then(
                  ({ success, code, message, data }) => {
                    Msg.destroy();
                    if (success) {
                      const {
                        departmentId,
                        departmentName,
                        departmentParentId,
                        children = []
                      } = data;
                      this.getLoopMatch(
                        this.gData,
                        value,
                        (item, index, arr) => {
                          item.label = departmentName;
                          item.value = departmentId;
                          item.valuePid = departmentParentId;
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
                  departmentId: value,
                  departmentName: inputValue
                };
                myFetch("updateSysDepartment", body).then(
                  ({ success, code, message, data }) => {
                    if (!success) {
                      Msg.error(message, 1.2, () => {
                        this.loadTree();
                      });
                    }
                  }
                );
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
                <Tooltip title={label}>
                  <span>{label}</span>
                </Tooltip>
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
        <Layout.Sider
          className={styles.treeContainer}
          style={{ background: "transparent", maxHeight: tableHeight + 50 }}
        >
          <Tree
            draggable={true}
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
        <Layout.Content className={styles.container}>
          {(() => {
            const tableColumns = [
              {
                title: "姓名",
                dataIndex: "realName",
                render: (text, record) => (
                  <a
                    onClick={() => {
                      this.handle({ rState: "1", record });
                    }}
                  >
                    {text}
                  </a>
                )
              },
              {
                title: "账号",
                dataIndex: "userId"
              },
              {
                title: "手机",
                dataIndex: "mobile"
              },
              {
                title: "岗位",
                dataIndex: "ext1",
                render: index => ext1SeletListDatas[index || "0"]
              },
              {
                title: "部门",
                dataIndex: "sysDepartmentShowList",
                render: department => {
                  return department.map((item, index) => {
                    return <div key={index}>{item}</div>;
                  });
                }
              }
            ];
            const listColumns = [
              {
                label: "姓名",
                dataIndex: "realName",
                render: text => <a>{text}</a>
              },
              {
                label: "账号",
                dataIndex: "userId"
              },
              {
                label: "手机",
                dataIndex: "mobile"
              },
              {
                label: "岗位",
                dataIndex: "ext1",
                render: index => ext1SeletListDatas[index || "0"]
              },
              {
                label: "部门",
                dataIndex: "sysDepartmentShowList",
                render: department => {
                  return department.map((item, index) => {
                    return <div key={index}>{item}</div>;
                  });
                }
              },
              {
                label: "个人编码",
                required: false,
                dataIndex: "ext2"
              },
              {
                label: "身份证号",
                type: "string",
                dataIndex: "identityCard",
                required: false,
                render: data => {
                  if (data) {
                    return data;
                  } else {
                    return "无";
                  }
                }
              },
              {
                label: "身份证照片",
                type: "upload",
                dataIndex: "sysFilesList",
                maxLen: idLen,
                required: false,
                render: arr => {
                  if (!arr || !arr.length) {
                    return "暂无";
                  }
                  return arr.map((item, index) => {
                    return (
                      <a key={index} href={item.url} target="_block">
                        <img width="320px" height="180px" alt="img" src={item.url} />
                      </a>
                    );
                  });
                }
              },
              {
                label: "责任清单",
                type: "fileList",
                dataIndex: "sysFilesResponsibilitylList",
                maxLen: sysFilesResponsibilitylListLen,
                required: false,
                render: arr => {
                  if (!arr || !arr.length) {
                    return "暂无";
                  }
                  return arr.map((item, index) => {
                    return (
                      <div key={index}>
                        <a href={item.url} target="_block">
                          {item.name}
                        </a>
                      </div>
                    );
                  });
                }
              }
            ];
            const addColumns = [
              {
                label: "userType",
                type: "hidden",
                dataIndex: "userType"
              },
              {
                label: "userStatus",
                type: "hidden",
                dataIndex: "userStatus"
              },
              {
                label: "姓名",
                dataIndex: "realName"
              },
              {
                label: "账号",
                dataIndex: "userId"
              },
              {
                label: "手机",
                dataIndex: "mobile"
              },
              {
                label: "岗位",
                type: "select",
                dataIndex: "ext1",
                selectList: ext1SeletListDatas
              },
              {
                label: "部门",
                dataIndex: "sysDepartmentList",
                type: "PullPersion",
                treeData: gData,
                k: {
                  label: "departmentName",
                  value: "departmentId",
                  type: "type",
                  children: "children"
                }
              },
              {
                label: "个人编码",
                dataIndex: "ext2",
                required: false
              },
              {
                label: "身份证号",
                type: "string",
                dataIndex: "identityCard",
                required: false
              },
              {
                label: "身份证照片",
                type: "upload",
                dataIndex: "sysFilesList",
                maxLen: idLen,
                required: false
              },
              {
                label: "责任清单",
                type: "fileList",
                dataIndex: "sysFilesResponsibilitylList",
                maxLen: sysFilesResponsibilitylListLen,
                required: false
              }
            ];

            const editColumns = [
              {
                label: "userKey",
                type: "hidden",
                dataIndex: "userKey"
              },
              {
                label: "姓名",
                dataIndex: "realName"
              },
              {
                label: "账号",
                dataIndex: "userId",
                disabled: true
              },
              {
                label: "手机",
                dataIndex: "mobile"
              },
              {
                label: "岗位",
                type: "select",
                dataIndex: "ext1",
                selectList: ext1SeletListDatas
              },
              {
                label: "部门",
                dataIndex: "sysDepartmentList",
                type: "PullPersion",
                treeData: gData,
                k: {
                  label: "departmentName",
                  value: "departmentId",
                  type: "type",
                  children: "children"
                }
              },
              {
                label: "个人编码",
                dataIndex: "ext2",
                required: false
              },
              {
                label: "身份证号",
                type: "string",
                dataIndex: "identityCard",
                required: false
              },
              {
                label: "身份证照片",
                type: "upload",
                dataIndex: "sysFilesList",
                maxLen: idLen,
                required: false
              },
              {
                label: "责任清单",
                type: "fileList",
                dataIndex: "sysFilesResponsibilitylList",
                maxLen: sysFilesResponsibilitylListLen,
                required: false
              }
            ];
            let rCon;
            switch (rState) {
              case "3": //新增
                let _dataSource = {
                  sysDepartmentList: [
                    this.initSimpleData(this.gData)[this.endKey]
                  ].map(({ label, value, type }) => {
                    return { departmentName: label, departmentId: value, type };
                  }),
                  userType: "1",
                  userStatus: "1"
                };
                rCon = (
                  <WrapMemberAdd
                    {...this.props}
                    handle={this.handle}
                    columns={addColumns}
                    dataSource={_dataSource}
                  />
                );
                break;
              case "2": //编辑
                rCon = (
                  <WrapMemberEdit
                    {...this.props}
                    handle={this.handle}
                    columns={editColumns}
                    dataSource={record}
                  />
                );
                break;
              case "1":
                rCon = (
                  <MemberDetail
                    {...this.props}
                    handle={this.handle}
                    columns={listColumns}
                    dataSource={record}
                  />
                );
                break;
              case "0":
              default:
                rCon = (
                  <MemberTable
                    {...this.props}
                    refresh={this.refresh}
                    searchText={this.searchText}
                    handle={this.handle}
                    columns={tableColumns}
                    dataSource={mData}
                    setSelectedRowKeys={this.setSelectedRowKeys}
                    selectedRowKeys={selectedRowKeys}
                    setPageParams={this.setPageParams}
                    loading={loading}
                    totalNumber={totalNumber}
                    page={page}
                    limit={limit}
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
        //身份证照片和责任清单只让上传1张
        //因为编辑时设置值不是使用的rc-form的方法而是用的props so ...
        if (body.sysFilesList) {
          //身份证
          if (body.sysFilesList.length > idLen) {
            Msg.error(`身份张照片数量超限,最多只能上传${idLen}张`, 2);
            return;
          }
        }
        if (body.sysFilesResponsibilitylList) {
          //责任清单
          if (
            body.sysFilesResponsibilitylList.length >
            sysFilesResponsibilitylListLen
          ) {
            Msg.error(
              `责任清单数量超限,最多只能上传${sysFilesResponsibilitylListLen}`,
              2
            );
            return;
          }
        }

        myFetch("updateSysUserInfoByBg", body).then(
          ({ success, code, message, data }) => {
            if (success) {
              Msg.success(message, 1.2, () => {
                this.handle({ rState: "0" });
              });
            } else {
              Msg.error(message, 1.2);
            }
          }
        );
      }
    });
    event && event.preventDefault && event.preventDefault();
    return false;
  };
  del = () => {
    const {
      dataSource: { userKey, realName },
      myFetch
    } = this.props;
    confirm({
      title: `您确定要删除【${realName}】么?`,
      content: ``,
      okText: "确认",
      cancelText: "取消",
      onOk: () => {
        myFetch("deleteSysUserInfoByBg", { userKey }).then(
          ({ success, code, message, data }) => {
            if (success) {
              Msg.success(message, 1.2, () => {
                this.handle({ rState: "0" });
              });
            } else {
              Msg.error(message, 1.2);
            }
          }
        );
      }
    });
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
      form: { getFieldDecorator, getFieldValue },
      columns,
      dataSource,
      myFetch
    } = this.props;
    const formList = columns.map((item, index) => {
      const {
        dataIndex,
        disabled = false,
        label,
        type,
        treeData,
        k,
        selectList,
        required,
        maxLen
      } = item;
      let fileList = getFieldValue(dataIndex) || [];
      let _item;
      switch (type) {
        case "PullPersion":
          let loop = datas => {
            return datas.map(data => {
              return {
                departmentName: data.label,
                departmentId: data.value,
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
        case "select":
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                initialValue: dataSource[dataIndex] || "",
                rules: [{ required: true, whitespace: true, message: "必填" }]
              })(
                <Select placeholder={label}>
                  {selectList.map((item, index) => {
                    let text, value;
                    if (typeof item === "string") {
                      text = item;
                      value = index.toString();
                    } else {
                      text = item.text;
                      value = item.value;
                    }
                    return (
                      <Option key={index} value={value}>
                        {text}
                      </Option>
                    );
                  })}
                </Select>
              )}
            </FormItem>
          );
          break;
        case "upload":
          // fileList = dataSource[dataIndex];
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                valuePropName: "fileList",
                initialValue: dataSource[dataIndex] || [],
                getValueFromEvent: normFile,
                rules: [
                  {
                    required: required,
                    message: "必填"
                  }
                ]
              })(
                <Upload
                  name={"name"}
                  listType="picture-card"
                  headers={{
                    token: this.props.loginAndLogoutInfo
                      ? this.props.loginAndLogoutInfo.loginInfo.token
                      : ""
                  }}
                  action={this.props.myPublic.domain + "upload"}
                >
                  {fileList.length >= maxLen ? null : (
                    <div>
                      <Icon type="plus" />
                      <div className="ant-upload-text">上传</div>
                    </div>
                  )}
                </Upload>
              )}
            </FormItem>
          );
          break;
        case "fileList":
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                valuePropName: "fileList",
                initialValue: dataSource[dataIndex] || [],
                getValueFromEvent: normFile,
                rules: [
                  {
                    required: required,
                    message: "必填"
                  }
                ]
              })(
                <Upload.Dragger
                  disabled={fileList.length >= maxLen ? true : false}
                  name={"name"}
                  headers={{
                    token: this.props.loginAndLogoutInfo
                      ? this.props.loginAndLogoutInfo.loginInfo.token
                      : ""
                  }}
                  action={this.props.myPublic.domain + "upload"}
                >
                  <p className="ant-upload-drag-icon">
                    <Icon type="inbox" />
                  </p>
                  <p className="ant-upload-text">{"点击或者拖动上传"}</p>
                  {/* <p className="ant-upload-hint">{subdesc || ""}</p> */}
                </Upload.Dragger>
              )}
            </FormItem>
          );
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
                initialValue: dataSource[dataIndex] || "",
                rules: [{ required, whitespace: true, message: "必填" }]
              })(<Input disabled={disabled} placeholder={label} />)}
            </FormItem>
          );
          break;
      }
      return _item;
    });
    return (
      <Form onSubmit={this.handleSubmit}>
        <Button.Group style={{ marginBottom: "12px" }}>
          <Button
            onClick={() => {
              this.handle({ rState: "1" });
            }}
          >
            {"返回"}
          </Button>
          <Button htmlType="submit">{"保存"}</Button>
          <Button
            onClick={() => {
              this.del();
            }}
          >
            {"删除"}
          </Button>
        </Button.Group>
        {formList}
      </Form>
    );
  }
}
const WrapMemberEdit = Form.create()(MemberEdit);

class MemberAdd extends Component {
  handleSubmit = rState => {
    const { form, myFetch } = this.props;
    form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const body = { ...values };
        myFetch("addSysUserInfoByBg", body).then(
          ({ success, code, message, data }) => {
            if (success) {
              Msg.success(message, 1.2, () => {
                if (rState === "3") {
                  form.resetFields();
                } else {
                  this.handle({ rState: "0" });
                }
              });
            } else {
              Msg.error(message);
            }
          }
        );
      }
    });
    rState && rState.preventDefault && rState.preventDefault();
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
      form: { getFieldDecorator, getFieldValue },
      columns,
      dataSource = {},
      myFetch
    } = this.props;

    const formList = columns.map((item, index) => {
      const {
        dataIndex,
        label,
        type,
        treeData,
        k,
        // defaultValue,
        selectList,
        required = true,
        maxLen = 999
      } = item;
      let fileList = getFieldValue(dataIndex) || [];
      let _item;
      switch (type) {
        case "PullPersion":
          let loop = datas => {
            return datas.map(data => {
              return {
                departmentName: data.label,
                departmentId: data.value,
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
                  { required, type: "array", whitespace: true, message: "必填" }
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
        case "upload":
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                valuePropName: "fileList",
                getValueFromEvent: normFile,
                rules: [
                  {
                    required: required,
                    message: "必填"
                  }
                ]
              })(
                <Upload
                  name={"name"}
                  listType="picture-card"
                  headers={{
                    token: this.props.loginAndLogoutInfo
                      ? this.props.loginAndLogoutInfo.loginInfo.token
                      : ""
                  }}
                  action={this.props.myPublic.domain + "upload"}
                >
                  {fileList.length >= maxLen ? null : (
                    <div>
                      <Icon type="plus" />
                      <div className="ant-upload-text">上传</div>
                    </div>
                  )}
                </Upload>
              )}
            </FormItem>
          );
          break;
        case "fileList":
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                valuePropName: "fileList",
                getValueFromEvent: normFile,
                rules: [
                  {
                    required: required,
                    message: "必填"
                  }
                ]
              })(
                <Upload.Dragger
                  disabled={fileList.length >= maxLen ? true : false}
                  name={"name"}
                  headers={{
                    token: this.props.loginAndLogoutInfo
                      ? this.props.loginAndLogoutInfo.loginInfo.token
                      : ""
                  }}
                  action={this.props.myPublic.domain + "upload"}
                >
                  <p className="ant-upload-drag-icon">
                    <Icon type="inbox" />
                  </p>
                  <p className="ant-upload-text">{"点击或者拖动上传"}</p>
                  {/* <p className="ant-upload-hint">{subdesc || ""}</p> */}
                </Upload.Dragger>
              )}
            </FormItem>
          );
          break;
        case "select":
          _item = (
            <FormItem
              key={index}
              label={label}
              {...formItemLayout}
              hasFeedback={true}
            >
              {getFieldDecorator(dataIndex, {
                initialValue: dataSource[dataIndex] || "",
                rules: [{ required: true, whitespace: true, message: "必填" }]
              })(
                <Select placeholder={label}>
                  {selectList.map((item, index) => {
                    let text, value;
                    if (typeof item === "string") {
                      text = item;
                      value = index.toString();
                    } else {
                      text = item.text;
                      value = item.value;
                    }
                    return (
                      <Option key={index} value={value}>
                        {text}
                      </Option>
                    );
                  })}
                </Select>
              )}
            </FormItem>
          );
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
                initialValue: dataSource[dataIndex] || "",
                rules: [{ required, whitespace: true, message: "必填" }]
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
          <Button
            onClick={() => {
              this.handle({ rState: "0" });
            }}
          >
            {"返回"}
          </Button>
          <Button htmlType="submit">{"保存"}</Button>
          <Button
            onClick={() => {
              this.handleSubmit("3");
            }}
          >
            {"保存并继续添加"}
          </Button>
        </Button.Group>
        {formList}
      </Form>
    );
  }
}

const WrapMemberAdd = Form.create()(MemberAdd);

class MemberDetail extends Component {
  handle = newState => {
    this.props.handle(newState);
  };
  render() {
    const { columns, dataSource } = this.props;

    let listData = columns.map(item => {
      const { render, dataIndex, label } = item;
      let renderData = dataIndex ? dataSource[dataIndex] : dataSource;
      let value = render ? render(renderData) : renderData;
      return { label, value };
    });
    return (
      <div>
        <Button.Group style={{ marginBottom: "12px" }}>
          <Button
            onClick={() => {
              this.handle({ rState: "0" });
            }}
          >
            {"返回"}
          </Button>
          <Button
            onClick={() => {
              this.handle({ rState: "2" });
            }}
          >
            {"编辑"}
          </Button>
        </Button.Group>
        <List
          size="small"
          dataSource={listData}
          renderItem={item => {
            const { label, value } = item;
            return (
              <List.Item>
                {`${label}：`}
                <div>{value}</div>
              </List.Item>
            );
          }}
        />
      </div>
    );
  }
}
class MemberTable extends Component {
  handle = newState => {
    this.props.handle(newState);
  };
  del = () => {
    const { selectedRowKeys, dataSource, myFetch } = this.props;
    if (selectedRowKeys.length) {
      let loop = keys => {
        if (keys.length) {
          myFetch("deleteSysUserInfoByBg", { userKey: keys.pop() }).then(
            ({ success, code, message, data }) => {
              if (success) {
                loop(keys);
              } else {
                Msg.error(message, 1.2);
              }
            }
          );
        } else {
          Msg.success("删除完毕！", 1.2, () => {
            this.handle({ rState: "0" });
          });
        }
      };

      let _selectedRowKeys = [...selectedRowKeys];
      let realNames = _selectedRowKeys
        .map(userKey => {
          let realName = "";
          for (let index = 0; index < dataSource.length; index++) {
            const item = dataSource[index];
            if (item.userKey === userKey) {
              realName = item.realName;
              break;
            }
          }
          return realName;
        })
        .join("、");
      confirm({
        title: "您确定要删除以下人员么?",
        content: `${realNames}，共计${_selectedRowKeys.length}人`,
        okText: "确认",
        cancelText: "取消",
        onOk: () => {
          loop(_selectedRowKeys);
        }
      });
    }
  };
  render() {
    const {
      columns,
      dataSource,
      loading,
      totalNumber,
      page,
      limit,
      selectedRowKeys
    } = this.props;
    const rowSelection = {
      selectedRowKeys,
      onChange: (selectedRowKeys, selectedRows) => {
        this.props.setSelectedRowKeys(selectedRowKeys);
      },
      getCheckboxProps: record => ({
        disabled: record.name === "Disabled User", // Column configuration not to be checked
        name: record.name
      })
    };
    const Search = Input.Search;
    // console.log(totalNumber)
    return (
      <div className={styles.nestedTable}>
        <Button.Group style={{ marginBottom: "12px" }}>
          <Button
            onClick={() => {
              this.handle({ rState: "3" });
            }}
          >
            {"添加"}
          </Button>
          <Button
            onClick={() => {
              this.del();
            }}
          >
            {"批量删除"}
          </Button>
        </Button.Group>

        <div
          style={{
            display: "inline-block",
            width: "50%",
            marginLeft: "8px"
          }}
        >
          <Search
            placeholder="账号、姓名、电话"
            onSearch={value => {
              //设置搜索词在点击翻页时需要用到
              this.props.searchText(value);
              this.props.refresh(value);
            }}
            enterButton
          />
        </div>

        <Table
          bordered
          size={"middle"}
          // onRow={(record) => {
          //     return {
          //         onClick: () => {
          //             this.handle({ rState: "1", record })
          //         },       // 点击行
          //     };
          // }}
          rowKey={"userKey"}
          rowSelection={rowSelection}
          columns={columns}
          dataSource={dataSource}
          loading={loading}
          // scroll={{y:500}}
          pagination={{
            total: totalNumber,
            current: page,
            defaultPageSize: limit,
            pageSize: limit,

            pageSizeOptions: ["10", "20", "40"],
            showSizeChanger: true,
            onChange: (page, limit) => {
              this.props.setPageParams({ page, limit });
            },
            onShowSizeChange: (_, limit) => {
              this.props.setPageParams({ page: 0, limit });
            }
          }}
        />
      </div>
    );
  }
}

export default basic(Demo);
