import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import QnnForm from "../../modules/qnn-form";
import { message as Msg, Modal, Input, Row, Col } from 'antd';
const confirm = Modal.confirm;
const configItem = {
  antd: {
    rowKey: 'zxBuStockPriceItemId',
    size: 'small'
  },
  drawerConfig: {
    width: window.innerWidth * 0.8
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    }
  }
};
const configItemSJ = {
  antd: {
    rowKey: 'zxBuStockPriceItemId',
    size: 'small'
  },
  drawerConfig: {
    width: window.innerWidth * 0.8
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    }
  }
};
const configItemLQHHL = {
  antd: {
    rowKey: 'zxBuStockPriceItemId',
    size: 'small'
  },
  drawerConfig: {
    width: window.innerWidth * 0.8
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    }
  }
};
const configItemJplHhl = {
  antd: {
    rowKey: 'zxBuStockPriceItemId',
    size: 'small'
  },
  drawerConfig: {
    width: window.innerWidth * 0.8
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    }
  }
};
const configItemhtl = {
  antd: {
    rowKey: 'zxBuStockPriceItemId',
    size: 'small'
  },
  drawerConfig: {
    width: window.innerWidth * 0.8
  },
  paginationConfig: {
    position: 'bottom'
  },
  formItemLayout: {
    labelCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    },
    wrapperCol: {
      xs: { span: 12 },
      sm: { span: 12 }
    }
  }
};
class formDone extends Component {
  constructor(props) {
    super(props);

    this.state = {
      zxBuStockPriceId: '',
      businessType: '2',
      QnnTableItemData: [],
      zjid: '',
      //砂浆
      shaJiangData: [],
      shaJiangDataid: '',
      shaJiangMixType: '49',
      //沥青混合
      lqhhlData: [],
      lqhhlId: '',
      lqhhlMixType: '42',
      //级配类混合料
      jplHhlData: [],
      jplHhlid: '',
      jplHhlMixType: '43',
      //灰土类
      htlData: [],
      htlid: '',
      htlMixType: '44',
      //混凝土
      hntData: [],
      hntid: '',
      hntMixType: '41',
      propsNew: props
    }
  }

  FloatMulTwo(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try { m += s1.split(".")[1].length } catch (e) { }
    try { m += s2.split(".")[1].length } catch (e) { }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
  }
  accAdd(arg1, arg2) {
    var r1, r2, m;
    try {
      r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
      r1 = 0;
    }
    try {
      r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
      r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (Number((arg1 * m).toFixed(0)) + Number((arg2 * m).toFixed(0))) / m;
  }
  plusFunc = (newRowData, rowData, i, field) => {
    let list = []
    let num = 0

    switch (field) {
      case 'hnt':
        list = ['amt1', 'amt2', 'amt3', 'amt4', 'amt5', 'amt6', 'amt7', 'amt8', 'amt9', 'amt10', 'amt11', 'amt12']
        break
      case 'sj':
        list = ['amt1', 'amt2', 'amt3', 'amt4', 'amt5', 'amt6', 'amt7']
        break
      case 'lq':
        list = ['amt1', 'amt2', 'amt3', 'amt4', 'amt5', 'amt6', 'amt7', 'amt8', 'amt9', 'amt10', 'amt11', 'amt12', 'amt13']
        break
      case 'jplhh':
        list = ['amt1', 'amt2', 'amt3', 'amt4', 'amt5', 'amt6', 'amt7', 'amt8']
        break
      case 'htlhh':
        list = ['amt1', 'amt2', 'amt3', 'amt4', 'amt5']
        break
      default:
        break
    }

    if (field === 'hnt') {
      list.map((item, index) => {
        if (index === i) {
          return num = this.accAdd(num, newRowData[item] ? +newRowData[item] : 0)
        } else {
          return num = this.accAdd(num, rowData[item] ? +rowData[item] : 0)
        }
      })

      newRowData.sumAmt = +num
      //总计 = 不含税小计 * 耗用率 + 拌合运输费
      const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
      const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
      // this.accAdd(+this.FloatMulTwo(newRowData.sumAmt, bhAmt),+ysAmt)
      newRowData.scenePrice = this.accAdd(+this.FloatMulTwo(newRowData.sumAmt, bhAmt), +ysAmt);
    } else {
      list.map((item, index) => {
        if (index === i) {
          return num = this.accAdd(num, newRowData[item] ? +newRowData[item] : 0)
        } else {
          return num = this.accAdd(num, rowData[item] ? +rowData[item] : 0)
        }
      })

      newRowData.scenePrice = +num
    }

    return newRowData
  }

  render() {
    let drawerTitile = this.props.drawerDetailTitle;
    let { orgID, orgName } = this.props;
    return (
      <div>
        <QnnForm
          fetch={this.props.myFetch}
          upload={this.props.myUpload}
          headers={{
            token: this.props.loginAndLogoutInfo.loginInfo.token
          }}
          wrappedComponentRef={(me) => {
            this.formDone = me;
          }}
          formItemLayout={{
            labelCol: {
              xs: { span: 24 },
              sm: { span: 12 }
            },
            wrapperCol: {
              xs: { span: 24 },
              sm: { span: 24 }
            }
          }}
          formConfig={[

          ]}
          tabs={[
            //混凝土配合比表
            {
              field: "tableqd",
              name: "tableqd",
              title: "混凝土配合比表",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                      <Input disabled='disabled' value={orgName} style={{ margin: '10px' }}></Input>
                    </Col>
                  </Row>
                  <QnnTable
                    {...this.props}
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                      token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    // data={this.state.QnnTableItemData}
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: async () => {
                        return {
                          orgID: orgID,
                          stockPriceID: await this.props.stockPriceID(),
                          mixType: this.state.hntMixType
                        }
                      }
                    }}
                    {...configItem}
                    wrappedComponentRef={(me) => {
                      this.tableHNT = me;
                    }}
                    method={{
                      tableTdEditSaveCb: (obj) => {
                        const { myFetch } = this.props;
                        myFetch("updateZxBuStockPriceItem", obj).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.tableHNT.refresh()
                            } else {
                            }
                          }
                        )
                      }
                    }}
                    tableTdEdit={true}
                    tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                    isShowRowSelect={true}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '主键id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '砼标号',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 200,
                          align: 'center',
                          type: 'select',
                          fixed: 'left',
                          tdEdit: drawerTitile === '详情' ? false : true,
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            linkageFields: {
                              resName: 'resName'
                            }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'hunNingTuPeiHeBiBiao'
                            }
                          },
                          onChange: (value, { itemData }, obj) => {
                            obj.qnnTableInstance.setEditedRowData({
                              resName: itemData.itemName
                            })
                          },
                          placeholder: '请选择',
                        }
                      },
                      {
                        isInTable: false,
                        form: {
                          type: 'string',
                          field: 'resName',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '水泥52.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt1',
                              key: 'amt1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt1'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥42.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt2',
                              key: 'amt2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt2'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥32.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt3',
                              key: 'amt3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt3'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '黄砂(河沙)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt4',
                              key: 'amt4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt4'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '机制砂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt5',
                              key: 'amt5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt5'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '碎石',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt6',
                              key: 'amt6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt6'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt7',
                              key: 'amt7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt7'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '粉煤灰',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                              tdEditCb: () => {

                              }
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt8',
                              key: 'amt8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt8'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '速凝剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty9',
                              key: 'qty9',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty9',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 8, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price9',
                              key: 'price9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price9',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 8, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt9',
                              key: 'amt9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt9'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '减水剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty10',
                              key: 'qty10',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty10',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 9, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price10',
                              key: 'price10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price10',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 9, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt10',
                              key: 'amt10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt10'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '防水剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty11',
                              key: 'qty11',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty11',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 10, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price11',
                              key: 'price11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price11',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 10, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt11',
                              key: 'amt11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt11'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '膨胀剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty12',
                              key: 'qty12',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty12',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 11, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price12',
                              key: 'price12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price12',
                                onChange: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0;
                                  //不含税小计(元)
                                  await this.tableHNT.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 11, 'hnt')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt12',
                              key: 'amt12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt12'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '不含税小计(元)',
                          dataIndex: 'sumAmt',
                          width: 130,
                          key: 'sumAmt',
                        },
                        form: {
                          type: 'number',
                        }
                      },
                      {
                        table: {
                          title: '耗用率',
                          dataIndex: 'bhAmt',
                          key: 'bhAmt',
                          tdEdit: drawerTitile === '详情' ? false : true,
                          fieldsConfig: {
                            onChange: async () => {
                              const rowData = await this.tableHNT.getEditedRowData(false);
                              const newRowData = {};
                              //总计 = 不含税小计 * 耗用率 + 拌合运输费
                              const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                              const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                              newRowData.scenePrice = this.FloatMulTwo(rowData.sumAmt, bhAmt) + ysAmt;
                              await this.tableHNT.setEditedRowData({
                                ...newRowData
                              });
                            }
                          }
                        },
                        form: {
                          type: 'number'
                        },

                      },
                      {
                        table: {
                          title: '拌合运输费(元/m³)',
                          dataIndex: 'ysAmt',
                          width: 200,
                          key: 'ysAmt',
                          tdEdit: drawerTitile === '详情' ? false : true,
                          fieldsConfig: {
                            onChange: async () => {
                              const rowData = await this.tableHNT.getEditedRowData(false);
                              const newRowData = {};
                              //总计 = 不含税小计 * 耗用率 + 拌合运输费
                              const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                              const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                              newRowData.scenePrice = this.FloatMulTwo(rowData.sumAmt, bhAmt) + ysAmt;
                              await this.tableHNT.setEditedRowData({
                                ...newRowData
                              });
                            }
                          }
                        },
                        form: {
                          type: 'number'
                        },
                      },
                      {
                        table: {
                          title: '总计',
                          dataIndex: 'scenePrice',
                          key: 'scenePrice',
                        },
                        form: {
                          type: 'number',
                        }
                      }
                    ]}
                    actionBtns={[
                      {
                        name: "diyadd",
                        icon: "plus",
                        type: "primary",
                        label: "新增",
                        onClick: async (obj) => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", {
                            stockPriceID: await this.props.stockPriceID(),
                            orgID: orgID,
                            mixType: this.state.hntMixType,
                            qty1: 0,
                            price1: 0,
                            amt1: 0,
                            qty2: 0,
                            price2: 0,
                            amt2: 0,
                            qty3: 0,
                            price3: 0,
                            amt3: 0,
                            qty4: 0,
                            price4: 0,
                            amt4: 0,
                            qty5: 0,
                            price5: 0,
                            amt5: 0,
                            qty6: 0,
                            price6: 0,
                            amt6: 0,
                            qty7: 0,
                            price7: 0,
                            amt7: 0,
                            qty8: 0,
                            price8: 0,
                            amt8: 0,
                            qty9: 0,
                            price9: 0,
                            amt9: 0,
                            qty10: 0,
                            price10: 0,
                            amt10: 0,
                            qty11: 0,
                            price11: 0,
                            amt11: 0,
                            qty12: 0,
                            price12: 0,
                            amt12: 0,
                            sumAmt: 0,
                            bhAmt: 0,
                            ysAmt: 0,
                            scenePrice: 0
                          }).then(
                            ({ data, success, message }) => {
                              if (success) {
                                this.tableHNT.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          )
                        },
                        hide: (val) => {
                          if (drawerTitile === '详情') {
                            return true
                          } else {
                            return false
                          }
                        }
                      },
                      {
                        name: 'diydel',
                        icon: 'delete',
                        type: 'danger',
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  Msg.success('删除成功！')
                                  this.tableHNT.refresh();
                                  this.tableHNT.clearSelectedRows();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        // hide: (val) => {
                        //   if (drawerTitile === '详情') {
                        //     return true
                        //   } else {
                        //     return false
                        //   }
                        // }
                      }
                    ]}
                  />
                </div>
              }
            },
            //砂浆配合比表
            {
              field: "tableqd",
              name: "tableqd",
              title: "砂浆配合比表",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                      <Input disabled='disabled' value={orgName} style={{ margin: '10px' }}></Input>
                    </Col>
                  </Row>
                  <QnnTable
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                      token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    // data={this.state.shaJiangData}
                    isShowRowSelect={true}
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: async () => {
                        return {
                          orgID: orgID,
                          stockPriceID: await this.props.stockPriceID(),
                          mixType: this.state.shaJiangMixType
                        }
                      }
                    }}
                    {...configItemSJ}
                    wrappedComponentRef={(me) => {
                      this.tableshaJiang = me;
                    }}
                    method={{
                      tableTdEditSaveCb: (obj) => {
                        const { myFetch } = this.props;
                        myFetch("updateZxBuStockPriceItem", obj).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.tableshaJiang.refresh()
                            } else {
                            }
                          }
                        )
                      }
                    }}
                    tableTdEdit={true}
                    tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                    // isShowRowSelect={false}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '主键id',
                          field: 'shaJiangDataid',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '砼标号',
                          dataIndex: 'resID',
                          key: 'resID',
                          type: 'select',
                          width: 200,
                          align: 'center',
                          fixed: 'left',
                          tdEdit: drawerTitile === '详情' ? false : true
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            linkageFields: {
                              resName: 'resName'
                            }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'shaJiangPeiHeBiBiao'
                            }
                          },
                          onChange: (value, { itemData }, obj) => {
                            obj.qnnTableInstance.setEditedRowData({
                              resName: itemData.itemName
                            })
                          },
                          placeholder: '请选择',
                        }
                      },
                      {
                        isInTable: false,
                        form: {
                          type: 'string',
                          field: 'resName',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '水泥52.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  //总计 = 不含税小计 * 耗用率 + 拌合运输费
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt1',
                              key: 'amt1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt1'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥42.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt2',
                              key: 'amt2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt2'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥32.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt3',
                              key: 'amt3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt3'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '黄砂(河沙)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                              tdEditCb: () => {

                              }
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt4',
                              key: 'amt4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt4'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '机制砂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt5',
                              key: 'amt5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt5'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt6',
                              key: 'amt6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt6'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '膨胀剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onChange: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableshaJiang.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'sj')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt7',
                              key: 'amt7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt7'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '不含税总价(元)',
                          dataIndex: 'scenePrice',
                          width: 150,
                          key: 'scenePrice'
                        },
                        form: {
                          type: 'number'
                        }
                      }
                    ]}

                    actionBtns={[
                      {
                        name: "diyadd",
                        icon: "plus",
                        type: "primary",
                        label: "新增",
                        onClick: async () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.shaJiangMixType, stockPriceID: await this.props.stockPriceID(), }).then(
                            ({ data, success, message }) => {
                              if (success) {
                                this.tableshaJiang.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          )
                        },
                        hide: (val) => {
                          if (drawerTitile === '详情') {
                            return true
                          } else {
                            return false
                          }
                        }
                      },
                      {
                        name: 'diydel',
                        icon: 'delete',
                        type: 'danger',
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  Msg.success('删除成功！')
                                  this.tableshaJiang.refresh();
                                  this.tableshaJiang.clearSelectedRows();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        // hide: (val) => {
                        //   if (drawerTitile === '详情') {
                        //     return true
                        //   } else {
                        //     return false
                        //   }
                        // }
                      }
                    ]}
                  />
                </div>
              }
            },
            //沥青混合料配合比表
            {
              field: "tableqd",
              name: "tableqd",
              title: "沥青混合料配合比表",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                      <Input disabled='disabled' value={orgName} style={{ margin: '10px' }}></Input>
                    </Col>
                  </Row>
                  <QnnTable
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                      token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    isShowRowSelect={true}
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: async () => {
                        return {
                          orgID: orgID,
                          stockPriceID: await this.props.stockPriceID(),
                          mixType: this.state.lqhhlMixType
                        }
                      }
                    }}
                    {...configItemLQHHL}
                    wrappedComponentRef={(me) => {
                      this.tableLQHHL = me;
                    }}
                    method={{
                      tableTdEditSaveCb: (obj) => {
                        const { myFetch } = this.props;
                        myFetch("updateZxBuStockPriceItem", obj).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.tableLQHHL.refresh()
                            } else {
                            }
                          }
                        )
                      }
                    }}
                    tableTdEdit={true}
                    tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                    // isShowRowSelect={false}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '主键id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '混合料类型',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 400,
                          align: 'center',
                          fixed: 'left',
                          type: 'select',
                          tdEdit: drawerTitile === '详情' ? false : true
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            linkageFields: {
                              resName: 'resName'
                            }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'liQingHunHeLiaoPeiHeBiBiao'
                            }
                          },
                          onChange: (value, { itemData }, obj) => {
                            obj.qnnTableInstance.setEditedRowData({
                              resName: itemData.itemName
                            })
                          },
                          placeholder: '请选择'
                        }
                      },
                      {
                        isInTable: false,
                        form: {
                          type: 'string',
                          field: 'resName',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '路面用石屑(0-0.3cm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt1',
                              key: 'amt1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt1'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '路面用碎石(0.3-0.5cm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt2',
                              key: 'amt2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt2'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '路面用碎石(0.5-1cm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt3',
                              key: 'amt3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt3'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '路面用碎石(1-2cm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt4',
                              key: 'amt4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt4'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '路面用碎石(1-3cm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt5',
                              key: 'amt5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt5'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '机制砂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt6',
                              key: 'amt6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt6'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '矿粉',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt7',
                              key: 'amt7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt7'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt8',
                              key: 'amt8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt8'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '普通沥青',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty9',
                              key: 'qty9',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty9',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 8, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price9',
                              key: 'price9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price9',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 8, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt9',
                              key: 'amt9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt9'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '改性沥青',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty10',
                              key: 'qty10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty10',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 9, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price10',
                              key: 'price10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price10',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 9, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt10',
                              key: 'amt10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt10'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '橡胶改性沥青',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty11',
                              key: 'qty11',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty11',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 10, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price11',
                              key: 'price11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price11',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 10, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt11',
                              key: 'amt11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt11'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '抗剥落剂',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty12',
                              key: 'qty12',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty12',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 11, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price12',
                              key: 'price12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price12',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 11, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt12',
                              key: 'amt12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt12'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '木质纤维',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty13',
                              key: 'qty13',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty13',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt13 = rowData.qty13 && rowData.price13
                                    ? (this.FloatMulTwo(rowData.qty13, rowData.price13)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 12, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price13',
                              key: 'price13',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price13',
                                onChange: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt13 = rowData.qty13 && rowData.price13
                                    ? (this.FloatMulTwo(rowData.qty13, rowData.price13)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableLQHHL.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 12, 'lq')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt13',
                              key: 'amt13',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt13'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '不含税总价(元)',
                          dataIndex: 'scenePrice',
                          key: 'scenePrice',
                          width: 150
                        },
                        form: {
                          type: 'number'
                        }
                      }
                    ]}
                    actionBtns={[
                      {
                        name: "diyadd",
                        icon: "plus",
                        type: "primary",
                        label: "新增",
                        onClick: async () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.lqhhlMixType, stockPriceID: await this.props.stockPriceID(), }).then(
                            ({ data, success, message }) => {
                              if (success) {
                                this.tableLQHHL.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          )
                        },
                        hide: (val) => {
                          if (drawerTitile === '详情') {
                            return true
                          } else {
                            return false
                          }
                        }
                      },
                      {
                        name: 'diydel',
                        icon: 'delete',
                        type: 'danger',
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  Msg.success('删除成功！')
                                  this.tableLQHHL.refresh();
                                  this.tableLQHHL.clearSelectedRows();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        // hide: (val) => {
                        //   if (drawerTitile === '详情') {
                        //     return true
                        //   } else {
                        //     return false
                        //   }
                        // }
                      }
                    ]}
                  />
                </div>
              }
            },
            //级配类混合料配合比表
            {
              field: "tableqd",
              name: "tableqd",
              title: "级配类混合料配合比表",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                      <Input disabled='disabled' value={orgName} style={{ margin: '10px' }}></Input>
                    </Col>
                  </Row>
                  <QnnTable
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                      token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    // data={this.state.jplHhlData}
                    isShowRowSelect={true}
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: async () => {
                        return {
                          orgID: orgID,
                          stockPriceID: await this.props.stockPriceID(),
                          mixType: this.state.jplHhlMixType
                        }
                      }
                    }}
                    {...configItemJplHhl}
                    wrappedComponentRef={(me) => {
                      this.tableJplhhl = me;
                    }}
                    method={{
                      tableTdEditSaveCb: (obj) => {
                        const { myFetch } = this.props;
                        myFetch("updateZxBuStockPriceItem", obj).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.tableJplhhl.refresh()
                            } else {
                            }
                          }
                        )
                      }
                    }}
                    tableTdEdit={true}
                    tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                    // isShowRowSelect={false}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '主键id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '混合料类型',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 400,
                          align: 'center',
                          fixed: 'left',
                          type: 'select',
                          tdEdit: drawerTitile === '详情' ? false : true,
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            linkageFields: {
                              resName: 'resName'
                            }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'jiPeiLeiHunHeLiaoPeiHeBiBiao'
                            }
                          },
                          onChange: (value, { itemData }, obj) => {
                            obj.qnnTableInstance.setEditedRowData({
                              resName: itemData.itemName
                            })
                          },
                          placeholder: '请选择',
                        }
                      },
                      {
                        isInTable: false,
                        form: {
                          type: 'string',
                          field: 'resName',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '水泥',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    // jplhh
                                    ...this.plusFunc(newRowData, rowData, 0, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt1',
                              key: 'amt1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt1'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '碎石(19-31.5mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)

                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt2',
                              key: 'amt2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt2'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '碎石(9.5-19mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt3',
                              key: 'amt3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt3'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '碎石(4.75-9.5mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt4',
                              key: 'amt4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt4'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '石屑',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt5',
                              key: 'amt5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt5'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '砾石(19-31.5mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 5, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt6',
                              key: 'amt6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt6'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '砾石(9.5-19mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 6, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt7',
                              key: 'amt7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt7'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '砾石(4.75-9.5mm)',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onChange: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tableJplhhl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 7, 'jplhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt8',
                              key: 'amt8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt8'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '不含税总价(元)',
                          dataIndex: 'scenePrice',
                          key: 'scenePrice',
                          width: 150
                        },
                        form: {
                          type: 'number'
                        }
                      }
                    ]}
                    actionBtns={[
                      {
                        name: "diyadd",
                        icon: "plus",
                        type: "primary",
                        label: "新增",
                        onClick: async () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.jplHhlMixType, stockPriceID: await this.props.stockPriceID(), }).then(
                            ({ data, success, message }) => {
                              if (success) {
                                this.tableJplhhl.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          )
                        },
                        hide: (val) => {
                          if (drawerTitile === '详情') {
                            return true
                          } else {
                            return false
                          }
                        }
                      },
                      {
                        name: 'diydel',
                        icon: 'delete',
                        type: 'danger',
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  Msg.success('删除成功！')
                                  this.tableJplhhl.refresh();
                                  this.tableJplhhl.clearSelectedRows();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        // hide: (val) => {
                        //   if (drawerTitile === '详情') {
                        //     return true
                        //   } else {
                        //     return false
                        //   }
                        // }
                      }
                    ]}
                  />
                </div>
              }
            },
            //灰土类混合料配合比表
            {
              field: "tableqd",
              name: "tableqd",
              title: "灰土类混合料配合比表",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>项目名称 :</span>
                      <Input disabled='disabled' value={orgName} style={{ margin: '10px' }}></Input>
                    </Col>
                  </Row>
                  <QnnTable
                    history={this.props.history}
                    match={this.props.match}
                    fetch={this.props.myFetch}
                    myFetch={this.props.myFetch}
                    upload={this.props.myUpload}
                    headers={{
                      token: this.props.loginAndLogoutInfo.loginInfo.token
                    }}
                    isShowRowSelect={true}
                    data={this.state.htlData}
                    {...configItemhtl}
                    wrappedComponentRef={(me) => {
                      this.tablehtl = me;
                    }}
                    method={{
                      tableTdEditSaveCb: (obj) => {
                        const { myFetch } = this.props;
                        myFetch("updateZxBuStockPriceItem", obj).then(
                          ({ data, success, message }) => {
                            if (success) {
                              this.tablehtl.refresh()
                            } else {
                            }
                          }
                        )
                      }
                    }}
                    tableTdEdit={true}
                    tableTdEditSaveCb={"bind:tableTdEditSaveCb"}
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: async () => {
                        return {
                          orgID: orgID,
                          stockPriceID: await this.props.stockPriceID(),
                          mixType: this.state.htlMixType
                        }
                      }
                    }}
                    // isShowRowSelect={false}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '主键id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '混合料类型',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 400,
                          align: 'center',
                          type: 'select',
                          fixed: 'left',
                          tdEdit: drawerTitile === '详情' ? false : true,
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            linkageFields: {
                              resName: 'resName'
                            }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'huiTuLeiHunHeLiaoPeiHeBiBiao'
                            }
                          },
                          onChange: (value, { itemData }, obj) => {
                            obj.qnnTableInstance.setEditedRowData({
                              resName: itemData.itemName
                            })
                          },
                          placeholder: '请选择',
                        }
                      },
                      {
                        isInTable: false,
                        form: {
                          type: 'string',
                          field: 'resName',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '石灰',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true,
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 0, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt1',
                              key: 'amt1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt1'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '粉煤灰',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 1, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt2',
                              key: 'amt2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt2'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '工业矿渣',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 2, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt3',
                              key: 'amt3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt3'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '水泥32.5',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 3, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt4',
                              key: 'amt4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt4'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '土',
                          children: [
                            {
                              title: '每方混合料用量t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)
                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '不含税材料单价',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onChange: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //小计1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //不含税总价(元)

                                  await this.tablehtl.setEditedRowData({
                                    ...this.plusFunc(newRowData, rowData, 4, 'htlhh')
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '详情' ? false : true
                            },
                            {
                              title: '小计',
                              dataIndex: 'amt5',
                              key: 'amt5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'amt5'
                              }
                            }
                          ]
                        }
                      },
                      {
                        table: {
                          title: '不含税总价(元)',
                          dataIndex: 'scenePrice',
                          key: 'scenePrice',
                          width: 150
                        },
                        form: {
                          type: 'number'
                        }
                      }
                    ]}
                    actionBtns={[
                      {
                        name: "diyadd",
                        icon: "plus",
                        type: "primary",
                        label: "新增",
                        onClick: async () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.htlMixType, stockPriceID: await this.props.stockPriceID(), }).then(
                            ({ data, success, message }) => {
                              if (success) {
                                this.tablehtl.refresh();
                              } else {
                                Msg.error(message);
                              }
                            }
                          )
                        },
                        hide: (val) => {
                          if (drawerTitile === '详情') {
                            return true
                          } else {
                            return false
                          }
                        }
                      },
                      {
                        name: 'diydel',
                        icon: 'delete',
                        type: 'danger',
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  Msg.success('删除成功！')
                                  this.tablehtl.refresh();
                                  this.tablehtl.clearSelectedRows();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        // hide: (val) => {
                        //   if (drawerTitile === '详情') {
                        //     return true
                        //   } else {
                        //     return false
                        //   }
                        // }
                      }
                    ]}
                  />
                </div>
              }
            }
          ]}
        />
      </div>
    )
  }



}
export default formDone;