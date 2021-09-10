/*
目前存在的问题
    1、props配置不能异步 不然会出现或者表单值时获取不到 导致一些条件字段或者按钮无法正常显隐或者disabled
    2、默认数据不能直接传入 而是得在父级用form设置 
    3、formatter 方法导致在验证失败时会出现数据重复 因为不是深度克隆数据而是直接还变传入属性造成的bug  
      
    4、table控制区域form类型的按钮弹出窗后只有关闭按钮,没有提交按钮
    5、action.js再循环中写了函数
*/

import React, { PureComponent } from "react";
import {
  message as Msg,
  message,
  Menu,
  Dropdown,
  Icon,
  Table,
  Divider,
  Drawer,
  Tooltip,
  Form,
  Alert,
  Row,
  Col,
  Button,
  Modal
} from "antd";
// import { LocaleProvider } from 'antd-mobile'
import { wran, help, action } from "./components";
import { getParams, getFromParams } from "./tool";
import QnnForm from "./qnn-form";
import moment from "moment";
import PullPerson from "./qnn-form/pullPersion";
import s from "./style.less";
const version = (window.SuperTable_version = "test");
const formItemLayout = {
  //布局
  labelCol: {
    xs: { span: 24 },
    sm: { span: 4 }
  },
  wrapperCol: {
    xs: { span: 24 },
    sm: { span: 18 }
  }
};

class index extends PureComponent {
  // static getDerivedStateFromProps (props, statec){}

  constructor(...args) {
    super(...args);
    //帮助文档
    help.bind(this)(version);
    //执行警告
    wran.bind(this)();

    //操作按钮可为func返回数据即可
    let { actionBtns = [], fetchConfig, formConfig } = this.props;
    if (typeof (actionBtns) === 'function') {
      actionBtns = actionBtns({ ...this.props })
    }
    //fetchConfig可为function
    if (fetchConfig && typeof (fetchConfig) === 'function') {
      fetchConfig = fetchConfig({ ...this.props })
    }
    //fetchConfig可为function
    if (formConfig && typeof (formConfig) === 'function') {
      formConfig = fetchConfig({ ...this.props })
    }

    this.state = {
      version: version,
      //表格请求配置
      fetchConfig: fetchConfig,
      //所有字段配置
      formConfig: formConfig,
      //表格是否加载中
      loading: true,
      //table数据
      data:
        this.props.data && this.props.data.length > 0 ? this.props.data : [],
      //总数据条数
      totalNumber: 0,
      //分页数据
      limit: this.props.pageSize || 10, //每页显示条数
      curPage: this.props.curPage || 1, //当前页

      //分页配置
      paginationConfig: this.props.paginationConfig || {},

      //antd样式配置
      antd: this.props.antd || {},

      //检索条件
      searchParams: {},

      //内置ajax请求时需要带上的
      headers: this.props.headers || {},

      //表头
      columns: [],
      //表单配置
      forms: [],
      //搜索配置
      searchFroms: [],

      //操作按钮
      actionBtns: actionBtns,

      //抽屉的配置
      drawerConfig: this.props.drawerConfig || {},

      //table行选择的配置 暂无使用
      rowSelection: this.props.rowSelection || {},

      //是否显示选择框
      isShowRowSelect: this.props.isShowRowSelect === false ? false : true,

      //右边抽屉是否出来状态
      DrawerShow: false,

      //全局文字配置
      labelConfig: this.props.labelConfig || {},
      drawerDetailTitle: "操作", //抽屉左上角文字

      selectedRows: [], //已经选择的行

      //每个表单项的布局 -- 表单
      formItemLayout: this.props.formItemLayout || formItemLayout,
      //每个表单项的布局 -- 搜索区域
      formItemLayoutSearch: this.props.formItemLayoutSearch || formItemLayout,

      //表格的宽
      tableContentWidth: "100%",

      //全局提示
      infoAlert: this.props.infoAlert,

      //表格上面的描述
      desc: this.props.desc,

      //操作区form类型的form配置
      qnnFormOption: {},
      modalOption: {},

      //抽屉中的自定义组件
      MyComponent: null
    };

    //内置方法
    this.fetch = this.props.fetch;
    this.myFetch = (apiName, params, success) => {
      this.props.fetch(apiName, params).then(data => {
        success(data);
      });
    };

    //form表单自带的方法
    this.sFormatData = QnnForm.sFormatData;
  }
  selectKey = field => `${field}_optionData`;
  componentDidMount() {
    //给一些方法绑定this
    this.getParams = getParams.bind(this);
    this.getFromParams = getFromParams.bind(this);
    this.action = action.bind(this);
    //绑定给某些按钮回调的
    this.btnCallbackFn = {
      setState: this.setState,
      fetch: this.myFetch,
      history: this.props.history,
      match: this.props.match,
      openTree: this.openTree, //打开树结构窗口
      msg: message,
      refresh: this.refresh,
      closeDrawer: this.closeDrawer, //打开/关闭右边抽屉的方法 传入true/false
      /*
        rowInfo{
          name,
          onClick,
          fetchConfig = {},
          formBtns = [],
          formTitle,
          qnnFormOption,
          modalOption
        },
        rowInfo:{}
      */
      btnAction: this.action //fn (rowInfo, rowData)
    };

    //请求默认数据
    const { fetchConfig, labelConfig = {} } = this.state;
    if (fetchConfig) {
      const { apiName } = fetchConfig;
      if (apiName && typeof apiName === "string") {
        //请求数据渲染表格 接口名存在才会请求数据渲染
        this.refresh();
      }
    } else {
      this.setState({
        loading: false
      });
    }

    const { formConfig = [], columns = [], forms, searchFroms } = this.state;
    let _tableContentWidth = 0;
    //将数据分类
    for (let i = 0; i < formConfig.length; i++) {
      let _item = { ...formConfig[i] };
      let {
        isInSearch,
        isInTable = true,
        isInForm = true,
        table = {},
        form = {},
        showType = "bubble"
      } = _item;
      let {
        tooltip = false,
        dataIndex,
        defaultValue = "",
        type = "",
        imgStyle = {},
        render,
        onClick,
        fetchConfig,
        optionData = [],
        optionConfig
      } = table;
      form.field = form.field ? form.field : table.dataIndex;
      form.label = form.label ? form.label : table.title;
      //主要给table使用的变量
      let _type = type;
      let _fetchConfig = fetchConfig || form.fetchConfig || {};
      let _field = dataIndex || form.field;
      let _optionData = form.optionData || optionData;
      let _optionConfig = optionConfig ||
        form.optionConfig || {
          value: "value",
          label: "label",
          children: "children"
        };

      //需要将render里面的字符串html取出来然后用html渲染
      if (render) {
        table.render = (data, rows) => {
          data = data ? data : defaultValue;
          const _realData = render(data, rows);
          if (_realData) {
            if (typeof _realData !== "object") {
              return <span dangerouslySetInnerHTML={{ __html: _realData }} />;
            } else {
              let _str = _realData.props.dangerouslySetInnerHTML.__html;
              return <span dangerouslySetInnerHTML={{ __html: _str }} />;
            }
          }
        };
      }
      if (isInTable && typeof isInTable === "function") {
        isInTable = isInTable({ ...this.props });
      }
      //所有在table中显示的字段
      if (isInTable) {
        if (table.btns) {
          //按钮
          //btns类型需要特殊处理
          table.render = (data, rows) => {
            let row = rows;
            // let row = QnnForm.sFormatData(rows, this.state.forms);
            let domArr = [];
            if (showType === "tile") {
              for (let i = 0; i < table.btns.length; i++) {
                let { render } = table.btns[i];
                domArr.push(
                  <span
                    onClick={() => {
                      this.action(table.btns[i], row);
                    }}
                    key={i}
                  >
                    {i > 0 ? <Divider type="vertical" /> : null}
                    <a dangerouslySetInnerHTML={{ __html: render(row) }} />
                  </span>
                );
              }
              return <div style={{ textAlign: "center" }}>{domArr}</div>;
            } else if (showType === "bubble") {
              for (let i = 0; i < table.btns.length; i++) {
                let { render } = table.btns[i];
                domArr.push(
                  <Menu.Item
                    style={{ minWidth: "80px", textAlign: "center" }}
                    onClick={() => {
                      this.action(table.btns[i], row);
                    }}
                    key={i}
                  >
                    {" "}
                    <a dangerouslySetInnerHTML={{ __html: render(row) }} />
                  </Menu.Item>
                );
              }
              return (
                <Dropdown overlay={<Menu>{domArr}</Menu>}>
                  <center>
                    <a>
                      {labelConfig.actionBtn || "操作"}
                      <Icon type="down" />
                    </a>
                  </center>
                </Dropdown>
              );
            }
          };
        }
        //处理别的render方法
        // if (
        //   tooltip &&
        //   (type !== "string" || type !== "number" || type !== "textarea")
        // ) {
        //   console.error(`${form.field}有tooltip属性但是不是string类型的`);
        // }
        if (tooltip) {
          table.render = data => {
            return (
              <Tooltip title={data}>
                <span>
                  {data && data.substr(0, tooltip)}
                  {data && data.length > tooltip ? "..." : null}
                </span>
              </Tooltip>
            );
          };
        }

        //特殊类型
        if (type === "images") {
          table.render = data => {
            let url = "";
            if (Array.isArray(data) && data[0]) {
              url = data[0].url;
            } else {
              url = data;
            }
            return <img width="50px" {...imgStyle} src={url} alt="" />;
          };
        } else if (_type === "select") {
          //类型为下拉时自动去匹配下拉选项里的值  暂时select类型的table不支持render属性
          const _sk = this.selectKey(_field);
          if (render) {
            console.error(
              "暂不支持select类型的table单元格设置render属性  ---来自qnn-table的警告"
            );
          }
          if (onClick) {
            console.error(
              "暂不支持select类型的table单元格设置onClock属性  ---来自qnn-table的警告"
            );
          }
          if (_fetchConfig.apiName) {
            _fetchConfig.otherParams = _fetchConfig.otherParams || {};
            _fetchConfig.params = _fetchConfig.params || {};
            //需要去请求下拉选项
            let _params = {};
            const urlParams = this.props.match.params;
            for (const key in _fetchConfig.params) {
              _params[key] = urlParams[_fetchConfig.params[key]];
            }
            table.render = tdData => {
              if (!this.state[_sk]) {
                this.fetch(_fetchConfig.apiName, {
                  ..._fetchConfig.otherParams,
                  ..._params
                }).then(({ success, data }) => {
                  if (success) {
                    this.setState({
                      [_sk]: data
                    });
                  }
                });
              }

              let _arr = this.state[_sk];
              let _val =
                _arr &&
                _arr.filter(item => {
                  return item[_optionConfig.value] === tdData;
                });
              let _v =
                _val && _val[0] ? _val[0][_optionConfig.label] : defaultValue;
              return _v;
            };
          } else {
            table.render = data => {
              let _val = _optionData.filter(item => {
                return item[_optionConfig.value] === data;
              });
              let _v = _val[0] ? _val[0][_optionConfig.label] : defaultValue;
              return _v;
            };

            // if (render) {
            //   //自定义了render
            //   table.render = (data, rowData) => {
            //     if (typeof data === "object") {
            //       let _str = data.props.dangerouslySetInnerHTML.__html;
            //       data = _str;
            //     }
            //     let _val = _optionData.filter((item) => {
            //       return item[_optionConfig.value] === data;
            //     });
            //     if (_val.length) {
            //       let _v = _val[0] ? _val[0][_optionConfig.label] : '';
            //       const _realData = render(_v, rowData);
            //       if (_realData) {
            //         if (typeof _realData !== "object") {
            //           return <span dangerouslySetInnerHTML={{ __html: _realData }} />;
            //         } else {
            //           let _str = _realData.props.dangerouslySetInnerHTML.__html;
            //           return <span dangerouslySetInnerHTML={{ __html: _str }} />;
            //         }
            //       }
            //     } else {
            //       return data;
            //     }

            //   }

            // } else {
            //   //未自定义render
            //   table.render = (data) => {
            //     let _val = _optionData.filter((item) => {
            //       return item[_optionConfig.value] === data;
            //     });
            //     let _v = _val[0] ? _val[0][_optionConfig.label] : '';
            //     return _v
            //   }
            // }
          }
        }

        //只格式化时间
        if (table.format) {
          table.render = data => {
            if (data) {
              return moment(data).format(table.format);
            } else {
              return defaultValue;
            }
          };
        }

        //点击事件
        if (onClick) {
          if (render) {
            console.error(
              "onClick属性和render属性只能存在一个  ---来自qnn-table的警告"
            );
          }

          table.render = (data, rows) => {
            return (
              <a
                onClick={() => {
                  if (typeof onClick === "function") {
                    onClick({
                      btnCallbackFn: this.btnCallbackFn,
                      data,
                      rows,
                      formConfig
                    });
                  } else {
                    switch (onClick) {
                      case "add":
                        this.action(
                          {
                            name: "add", //内置add del
                            formBtns: [
                              {
                                name: "cancel", //关闭右边抽屉
                                type: "dashed", //类型  默认 primary
                                label: "取消"
                              },
                              {
                                name: "submit", //内置add del
                                type: "primary", //类型  默认 primary
                                label: "提交", //提交数据并且关闭右边抽屉
                                fetchConfig
                              }
                            ]
                          },
                          {}
                        );
                        break;
                      case "edit":
                        this.action(
                          {
                            name: "edit",
                            formBtns: [
                              {
                                name: "cancel", //关闭右边抽屉
                                type: "dashed", //类型  默认 primary
                                label: "取消"
                              },
                              {
                                name: "submit", //内置add del
                                type: "primary", //类型  默认 primary
                                label: "提交", //提交数据并且关闭右边抽屉
                                fetchConfig
                              }
                            ]
                          },
                          rows
                        );
                        break;
                      case "detail":
                        this.action(
                          {
                            name: "detail"
                          },
                          rows
                        );
                        break;
                      case "plus":
                        this.action(
                          {
                            name: "del",
                            fetchConfig
                          },
                          rows
                        );
                        break;
                      case "Component":
                        this.action(
                          {
                            name: "Component",
                            Component: table.Component,
                            btnCallbackFn: this.btnCallbackFn,
                            data,
                            rows,
                            formConfig
                          },
                          rows
                        );
                        break;
                      default:
                        console.error(
                          "onClick属性和name只能为内置按钮name 或者为function"
                        );
                        break;
                    }
                  }
                }}
              >
                {data}
              </a>
            );
          };
        }

        //需要将表格内容的宽算出来
        _tableContentWidth += table.width ? table.width : 100;
        //将表头使用html渲染
        if (table.title) {
          if (typeof table.title === "string") {
            table.title = (
              <span dangerouslySetInnerHTML={{ __html: table.title }} />
            );
          } else {
            let _str = table.title.props.dangerouslySetInnerHTML.__html;
            table.title = <span dangerouslySetInnerHTML={{ __html: _str }} />;
          }
        }
        columns.push(table);
      }

      if (isInForm && typeof isInForm === "function") {
        isInForm = isInForm({ ...this.props });
      }

      if (isInForm) {
        let _form = { ...form };
        _form.span = form.spanForm ? form.spanForm : "24";
        forms.push(_form);
      }

      if (isInSearch && typeof isInSearch === "function") {
        isInSearch = isInSearch({ ...this.props });
      }

      if (isInSearch) {
        let _f = { ...form };
        _f.span = form.spanSearch ? form.spanSearch : "8";
        _f.required = form.searchRequied ? form.searchRequied : false;
        _f.field = `search__${form.field}`;
        _f.help = ""; //删除出掉帮助信息
        searchFroms.push(_f);
      }
    }

    this.setState({
      columns, //这是isInTable
      forms,
      searchFroms,
      tableContentWidth: columns.length > 7 ? _tableContentWidth : "100%"
    });
  }

  //打开选人的方法
  openTree = (obj = {}) => {
    this.setState({
      pullpersonField: obj.field || "pullPerson",
      pullpersonChangeFn: obj.pullpersonChangeFn || function () { },
      openTree: obj.openTree === false ? obj.openTree : true,
      treeOption: obj
    });
  };

  //选择 当点击第二页时第一页选择的数据不见了
  rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      this.setState({
        selectedRows
      });
    }
  };

  //刷新表格数据
  refresh = () => {
    let {
      fetchConfig: { apiName, params = {}, otherParams },
      limit, //每页显示条数
      curPage, //当前页
      searchParams = {} //搜索参数
    } = this.state;
    // let _params = this.getParams(params, otherParams);
    //每次请求除了带上用户搜索的参数为还会带上配置里的参数【params】
    // params 将自动从网址中取值
    let _params = {};
    const urlParams = this.props.match.params;
    for (const key in params) {
      _params[key] = urlParams[params[key]];
    }

    if (typeof otherParams === "function") {
      otherParams = otherParams({
        ...this.props,
        fetch: this.fetch
      });
    }

    if (typeof apiName === "function") {
      apiName = apiName({
        ...this.props,
        fetch: this.fetch
      });
    }

    const defaultSearchParams = {
      limit, //每页显示条数
      page: curPage, //当前页
      ...otherParams,
      ..._params,
      ...searchParams
    };
    this.setState({ loading: true });
    this.fetch(apiName, defaultSearchParams).then(res => {
      let { data, totalNumber, success, message } = res;
      if (success) {
        this.setState({
          totalNumber,
          data,
          loading: false
        });
      } else {
        Msg.error(message, 1, () => {
          this.setState({
            loading: false
          });
        });
      }
    });
  };

  //分页点击
  paginationChange = (page, pageSize) => {
    this.setState(
      {
        limit: pageSize,
        curPage: page
      },
      () => {
        this.refresh();
      }
    );
  };

  //关闭右边抽屉
  closeDrawer = (show = false, cb = () => { }) => {
    this.setState(
      {
        DrawerShow: show,
        MyComponent: null
      },
      cb
    );
  };

  //当搜索条件多的时候添加的展开状态
  toggle = () => {
    const { expand } = this.state;
    this.setState({ expand: !expand });
  };

  //搜索
  search = e => {
    this.getFromParams("search", values => {
      this.setState(
        {
          searchParams: values
        },
        () => {
          this.refresh();
        }
      );
    });
  };

  //重置搜索条件
  handleReset = () => {
    this.props.form.resetFields();
    this.setState(
      {
        searchParams: {}
      },
      () => {
        this.refresh();
      }
    );
  };

  //弹窗
  handleCancel = e => {
    this.setState({
      modalVisible: false
    });
  };

  handleOk = e => {
    this.setState({
      modalVisible: false
    });
  };

  modalBtnClick = obj => {
    //未写提交
    let { name } = obj;
    if (name === "close") {
      this.handleCancel();
    }
  };

  render() {
    const {
      data,
      loading,
      paginationConfig,
      totalNumber,
      curPage,
      limit,
      antd,
      columns,
      DrawerShow,
      drawerConfig,
      drawerDetailTitle,
      searchFroms,
      expand,
      isShowRowSelect,
      tableContentWidth,
      forms,
      formItemLayoutSearch,
      pullpersonField,
      openTree,
      pullpersonChangeFn,
      treeOption,
      formItemLayout,
      selectedRows,
      infoAlert,
      actionBtns,
      drawerBtns = [],
      modalVisible,
      modalTitle,
      qnnFormOption,
      modalBtns = [],
      modalOption = {},
      MyComponent,
      desc,
      titleConDomHeight = 0, //标题容器高
    } = this.state;
    const { getFieldDecorator } = this.props.form;

    let _searchFroms = [...searchFroms];
    if (expand) {
      //设置所有_searchFroms的form hide为false
      _searchFroms = _searchFroms.map((item, i) => {
        item.hide = false;
        return item;
      });
    } else {
      //将索引大于2的_searchFroms的form hide为true
      _searchFroms = _searchFroms.map((item, i) => {
        if (i > 2) {
          item.hide = true;
        }
        return item;
      });
    }
    //搜索框表单配置  不使用qnn-form的按钮
    const filterConfig = {
      form: this.props.form,
      fetch: this.props.fetch,
      headers: this.props.headers,
      formConfig: _searchFroms,
      formItemLayout: formItemLayoutSearch,
      style: { height: 'auto', overflow: 'hidden' }
    };

    //右抽屉表单配置  不使用qnn-form的按钮
    const formConfig = {
      form: this.props.form,
      fetch: this.props.fetch,
      headers: this.props.headers,
      formConfig: forms,
      refresh: this.refresh,
      formItemLayout: formItemLayout,
      style: { height: window.innerHeight - titleConDomHeight, overflow: 'scroll' }
    };

    //弹出窗的form配置
    const qnnFormModalConfig = {
      form: this.props.form,
      fetch: this.props.fetch,
      headers: this.props.headers,
      formItemLayout: formItemLayout,
      refresh: this.refresh,
      ...qnnFormOption
    };

    //设置滚动条
    if (tableContentWidth !== "100%" && tableContentWidth) {
      antd.scroll = {
        x: tableContentWidth
      };
    }

    return (
      <div className={s.superTable}>
        {/* 检索 */}
        {searchFroms.length > 0 ? (
          <div className={s.searchFromContainer}>
            <QnnForm {...filterConfig} />
            <Row>
              <Col span={24} style={{ textAlign: "right" }}>
                <Button type="primary" onClick={this.search}>
                  <Icon type="search" theme="outlined" />
                  搜索
                </Button>
                <Button style={{ marginLeft: 8 }} onClick={this.handleReset}>
                  <Icon type="reload" theme="outlined" /> 重置
                </Button>
                {searchFroms.length > 3 ? (
                  <a
                    style={{ marginLeft: 8, fontSize: 12 }}
                    onClick={this.toggle}
                  >
                    {expand ? "收起" : "展开"}{" "}
                    <Icon type={expand ? "up" : "down"} />
                  </a>
                ) : null}
              </Col>
            </Row>
          </div>
        ) : null}

        {/* 操作按钮 */}
        <Row style={{ padding: "10px 0px 10px 0px" }}>
          <Col span={24}>
            {actionBtns.map((item, index) => {
              let { label, type, icon, name } = item;
              let needHide = name === "del" || name === "edit"; //是否需要隐藏

              return (
                <Button
                  type={type || "primary"}
                  key={index}
                  onClick={() => {
                    this.action(item);
                  }}
                  style={{
                    marginLeft: index === 0 ? 0 : 8,
                    display: needHide && selectedRows.length === 0 ? "none" : ""
                  }}
                  icon={icon}
                >
                  {label}
                </Button>
              );
            })}
          </Col>
        </Row>
        {
          <div
            style={{ marginBottom: "8px", color: "#bbb", fontSize: "13px" }}
            dangerouslySetInnerHTML={{ __html: desc }}
          />
        }
        {/* 信息弹窗 */}
        {infoAlert ? (
          <Alert
            style={{ marginBottom: "10px" }}
            message={<div>{infoAlert(selectedRows)}</div>}
            type="info"
            showIcon
          />
        ) : null}

        {/* 表格 */}
        <Table
          loading={loading}
          dataSource={data}
          columns={columns}
          bordered={true}
          rowSelection={isShowRowSelect ? this.rowSelection : null}
          {...antd}
          pagination={{
            position: "both",
            current: curPage,
            defaultCurrent: 1,
            defaultPageSize: 10,
            pageSize: limit,
            total: totalNumber,
            showTotal: total => {
              return `共查询到 ${total} 条数据`;
            },
            onChange: this.paginationChange,
            showSizeChanger: true,
            onShowSizeChange: (current, pageSize) => {
              this.setState(
                {
                  limit: pageSize
                },
                () => {
                  this.refresh();
                }
              );
            },
            ...paginationConfig
          }}
        />

        {/* 右边抽屉 */}
        <div>
          <Drawer
            className={s.myDrawer}
            title={drawerDetailTitle}
            width="45%"
            placement="right"
            ref={(me) => {
              if (me) {
                let titleConDom = document.getElementsByClassName('ant-drawer-header');
                let titleConDomHeight = 0;
                if (titleConDom && titleConDom[0]) {
                  titleConDomHeight = titleConDom[0].offsetHeight;
                  this.setState({
                    titleConDomHeight
                  })
                }
              }
            }}
            onClose={() => {
              this.closeDrawer(false);
            }}
            visible={DrawerShow}
            destroyOnClose={true}
            {...drawerConfig}
          >
            {//自定义组件存在将使用自定义组件
              MyComponent ? (
                MyComponent
              ) : (
                  <div style={{ paddingBottom: "30px" }}>
                    <QnnForm {...formConfig} />
                  </div>
                )}

            {drawerBtns.length > 0 ? (
              <div
                style={{
                  position: "absolute",
                  bottom: 0,
                  width: "100%",
                  borderTop: "1px solid #e8e8e8",
                  padding: "10px 16px",
                  textAlign: "right",
                  left: 0,
                  background: "#fff",
                  borderRadius: "0 0 4px 4px"
                }}
              >
                {drawerBtns.map((item, index) => {
                  let { label, type } = item;
                  return (
                    <Button
                      key={index}
                      onClick={() => {
                        this.action(item);
                      }}
                      style={{
                        marginLeft: "8px"
                      }}
                      type={type || "primary"}
                    >
                      {label}
                    </Button>
                  );
                })}
              </div>
            ) : null}
          </Drawer>
        </div>

        {/* 选人插件 */}
        {openTree ? (
          <div>
            {getFieldDecorator(pullpersonField, {
              valuePropName: "defaultValue",
              onChange: pullpersonChangeFn
            })(
              <PullPerson
                visible
                label={<a>{null}</a>}
                myFetch={this.fetch}
                {...treeOption}
              />
            )}
          </div>
        ) : null}

        <Modal
          destroyOnClose
          title={modalTitle}
          visible={modalVisible}
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          footer={
            modalBtns.length
              ? modalBtns.map((item, index) => {
                let { label, icon, type = "primary" } = item;
                return (
                  <Button
                    key={index}
                    icon={icon}
                    onClick={() => {
                      this.modalBtnClick(item);
                    }}
                    type={type}
                  >
                    {label}
                  </Button>
                );
              })
              : null
          }
          {...modalOption}
        >
          <QnnForm {...qnnFormModalConfig} />
        </Modal>
      </div>
    );
  }
}

const w = Form.create()(index);
export default w; 