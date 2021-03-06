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
      //??????
      shaJiangData: [],
      shaJiangDataid: '',
      shaJiangMixType: '25',
      //????????????
      lqhhlData: [],
      lqhhlId: '',
      lqhhlMixType: '22',
      //??????????????????
      jplHhlData: [],
      jplHhlid: '',
      jplHhlMixType: '23',
      //?????????
      htlData: [],
      htlid: '',
      htlMixType: '24',
      //?????????
      hntData: [],
      hntid: '',
      hntMixType: '21',
      propsNew: props
    }
  }
  render() {
    let drawerTitile = this.props.drawerDetailTitle;
    let {orgID,orgName} = this.props;
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
            //?????????????????????
            {
              field: "tableqd",
              name: "tableqd",
              title: "?????????????????????",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>???????????? :</span>
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
                      otherParams: {
                        orgID: orgID,
                        mixType: this.state.hntMixType
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
                    // isShowRowSelect={true}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '??????id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '?????????',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 200,
                          align: 'center',
                          type: 'select',
                          fixed: 'left',
                          tdEdit: drawerTitile === '??????' ? false : true,
                        },
                        form: {
                          allowClear: false,
                          type: 'select',
                          field: 'resID',
                          optionConfig: {
                            label: 'itemName',
                            value: 'itemId',
                            // linkageFields: {
                            //   resName: 'resName'
                            // }
                          },
                          fetchConfig: {
                            apiName: "getBaseCodeSelect",
                            otherParams: {
                              itemId: 'hunNingTuPeiHeBiBiao'
                            }
                          },
                          placeholder: '?????????',
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
                          title: '??????52.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '??????',
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
                          title: '??????42.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????32.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '??????',
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
                          title: '??????(??????)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '??????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                              tdEditCb: () => {

                              }
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty9',
                              key: 'qty9',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty9',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(newRowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price9',
                              key: 'price9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price9',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(newRowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty10',
                              key: 'qty10',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty10',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(newRowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price10',
                              key: 'price10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price10',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(newRowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty11',
                              key: 'qty11',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty11',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(newRowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price11',
                              key: 'price11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price11',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(newRowData.amt11)
                                    + Number(rowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty12',
                              key: 'qty12',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty12',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(newRowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price12',
                              key: 'price12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price12',
                                onBlur: async () => {
                                  const rowData = await this.tableHNT.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0;
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(newRowData.amt12);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                                  const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                                  newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                                  await this.tableHNT.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(???)',
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
                          title: '?????????',
                          dataIndex: 'bhAmt',
                          key: 'bhAmt',
                          tdEdit: drawerTitile === '??????' ? false : true
                        },
                        form: {
                          type: 'number'
                        },
                        onBlur: async () => {
                          const rowData = await this.tableHNT.getEditedRowData(false);
                          const newRowData = {};
                          //?????? = ??????????????? * ????????? + ???????????????
                          const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                          const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                          newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                          await this.tableHNT.setEditedRowData({
                            ...newRowData
                          });
                        }
                      },
                      {
                        table: {
                          title: '???????????????(???/m??)',
                          dataIndex: 'ysAmt',
                          width: 200,
                          key: 'ysAmt',
                          tdEdit: drawerTitile === '??????' ? false : true,
                        },
                        form: {
                          type: 'number'
                        },
                        onBlur: async () => {
                          const rowData = await this.tableHNT.getEditedRowData(false);
                          const newRowData = {};
                          //?????? = ??????????????? * ????????? + ???????????????
                          const bhAmt = rowData.bhAmt ? rowData.bhAmt : 0;
                          const ysAmt = rowData.ysAmt ? rowData.ysAmt : 0;
                          newRowData.scenePrice = this.FloatMulTwo(newRowData.sumAmt, bhAmt) + ysAmt;
                          await this.tableHNT.setEditedRowData({
                            ...newRowData
                          });
                        }
                      },
                      {
                        table: {
                          title: '??????',
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
                        label: "??????",
                        onClick: (obj) => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", {
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
                          if (drawerTitile === '??????') {
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  this.tableHNT.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        hide: (val) => {
                          if (drawerTitile === '??????') {
                            return true
                          } else {
                            return false
                          }
                        }
                      }
                    ]}
                  />
                </div>
              }
            },
            //??????????????????
            {
              field: "tableqd",
              name: "tableqd",
              title: "??????????????????",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>???????????? :</span>
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
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: {
                        orgID: orgID,
                        mixType: this.state.shaJiangMixType
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
                          label: '??????id',
                          field: 'shaJiangDataid',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '?????????',
                          dataIndex: 'resID',
                          key: 'resID',
                          type: 'select',
                          width: 200,
                          align: 'center',
                          fixed: 'left',
                          tdEdit: drawerTitile === '??????' ? false : true
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
                          placeholder: '?????????',
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
                          title: '??????52.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????42.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????32.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(??????)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                              tdEditCb: () => {

                              }
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onBlur: async () => {
                                  const rowData = await this.tableshaJiang.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.sumAmt = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableshaJiang.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(???)',
                          dataIndex: 'sumAmt',
                          width: 150,
                          key: 'sumAmt'
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
                        label: "??????",
                        onClick: () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.shaJiangMixType }).then(
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
                          if (drawerTitile === '??????') {
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  this.tableshaJiang.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        hide: (val) => {
                          if (drawerTitile === '??????') {
                            return true
                          } else {
                            return false
                          }
                        }
                      }
                    ]}
                  />
                </div>
              }
            },
            //???????????????????????????
            {
              field: "tableqd",
              name: "tableqd",
              title: "???????????????????????????",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>???????????? :</span>
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
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: {
                        orgID: orgID,
                        mixType: this.state.lqhhlMixType
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
                          label: '??????id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '???????????????',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 200,
                          align: 'center',
                          fixed: 'left',
                          tdEdit: drawerTitile === '??????' ? false : true
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
                          placeholder: '?????????'
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
                          title: '???????????????(0-0.3cm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(0.3-0.5cm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(0.5-1cm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(1-2cm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(1-3cm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty9',
                              key: 'qty9',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty9',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(newRowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price9',
                              key: 'price9',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price9',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt9 = rowData.qty9 && rowData.price9
                                    ? (this.FloatMulTwo(rowData.qty9, rowData.price9)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(newRowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty10',
                              key: 'qty10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty10',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(newRowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price10',
                              key: 'price10',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price10',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt10 = rowData.qty10 && rowData.price10
                                    ? (this.FloatMulTwo(rowData.qty10, rowData.price10)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(newRowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty11',
                              key: 'qty11',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty11',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(newRowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price11',
                              key: 'price11',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price11',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt11 = rowData.qty11 && rowData.price11
                                    ? (this.FloatMulTwo(rowData.qty11, rowData.price11)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(newRowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty12',
                              key: 'qty12',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty12',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(newRowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price12',
                              key: 'price12',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price12',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt12 = rowData.qty12 && rowData.price12
                                    ? (this.FloatMulTwo(rowData.qty12, rowData.price12)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(newRowData.amt12)
                                    + Number(rowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty13',
                              key: 'qty13',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty13',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt13 = rowData.qty13 && rowData.price13
                                    ? (this.FloatMulTwo(rowData.qty13, rowData.price13)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(newRowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price13',
                              key: 'price13',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price13',
                                onBlur: async () => {
                                  const rowData = await this.tableLQHHL.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt13 = rowData.qty13 && rowData.price13
                                    ? (this.FloatMulTwo(rowData.qty13, rowData.price13)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8)
                                    + Number(rowData.amt9)
                                    + Number(rowData.amt10)
                                    + Number(rowData.amt11)
                                    + Number(rowData.amt12)
                                    + Number(newRowData.amt13);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableLQHHL.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(???)',
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
                        label: "??????",
                        onClick: () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.lqhhlMixType }).then(
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
                          if (drawerTitile === '??????') {
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  this.tableLQHHL.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        hide: (val) => {
                          if (drawerTitile === '??????') {
                            return true
                          } else {
                            return false
                          }
                        }
                      }
                    ]}
                  />
                </div>
              }
            },
            //??????????????????????????????
            {
              field: "tableqd",
              name: "tableqd",
              title: "??????????????????????????????",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>???????????? :</span>
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
                    fetchConfig={{
                      apiName: "getZxBuStockPriceItemList",
                      otherParams: {
                        orgID: orgID,
                        mixType: this.state.jplHhlMixType
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
                          label: '??????id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '???????????????',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 200,
                          align: 'center',
                          fixed: 'left',
                          type: 'select',
                          tdEdit: drawerTitile === '??????' ? false : true,
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
                          placeholder: '?????????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(19-31.5mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(9.5-19mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(4.75-9.5mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(19-31.5mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty6',
                              key: 'qty6',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty6',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price6',
                              key: 'price6',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price6',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt6 = rowData.qty6 && rowData.price6
                                    ? (this.FloatMulTwo(rowData.qty6, rowData.price6)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(newRowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(9.5-19mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty7',
                              key: 'qty7',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty7',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price7',
                              key: 'price7',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price7',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt7 = rowData.qty7 && rowData.price7
                                    ? (this.FloatMulTwo(rowData.qty7, rowData.price7)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(newRowData.amt7)
                                    + Number(rowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????(4.75-9.5mm)',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty8',
                              key: 'qty8',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty8',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price8',
                              key: 'price8',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price8',
                                onBlur: async () => {
                                  const rowData = await this.tableJplhhl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt8 = rowData.qty8 && rowData.price8
                                    ? (this.FloatMulTwo(rowData.qty8, rowData.price8)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5)
                                    + Number(rowData.amt6)
                                    + Number(rowData.amt7)
                                    + Number(newRowData.amt8);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tableJplhhl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(???)',
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
                        label: "??????",
                        onClick: () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.jplHhlMixType }).then(
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
                          if (drawerTitile === '??????') {
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  this.tableJplhhl.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        hide: (val) => {
                          if (drawerTitile === '??????') {
                            return true
                          } else {
                            return false
                          }
                        }
                      }
                    ]}
                  />
                </div>
              }
            },
            //??????????????????????????????
            {
              field: "tableqd",
              name: "tableqd",
              title: "??????????????????????????????",
              content: props => {
                return <div style={{ padding: '10px' }}>
                  <Row>
                    <Col span={8}>
                      <span style={{ margin: '10px', fontSize: "15px" }}>???????????? :</span>
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
                      otherParams: {
                        orgID: orgID,
                        mixType: this.state.htlMixType
                      }
                    }}
                    // isShowRowSelect={false}
                    formConfig={[
                      {
                        isInTable: false,
                        form: {
                          label: '??????id',
                          field: 'zxBuStockPriceItemId',
                          hide: true
                        }
                      },
                      {
                        table: {
                          title: '???????????????',
                          dataIndex: 'resID',
                          key: 'resID',
                          width: 200,
                          align: 'center',
                          type: 'select',
                          fixed: 'left',
                          tdEdit: drawerTitile === '??????' ? false : true,
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
                          placeholder: '?????????',
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
                          title: '??????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty1',
                              key: 'qty1',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty1',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true,
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price1',
                              key: 'price1',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price1',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt1 = rowData.qty1 && rowData.price1
                                    ? (this.FloatMulTwo(rowData.qty1, rowData.price1)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(newRowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '?????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty2',
                              key: 'qty2',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty2',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price2',
                              key: 'price2',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price2',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt2 = rowData.qty2 && rowData.price2
                                    ? (this.FloatMulTwo(rowData.qty2, rowData.price2)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(newRowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '????????????',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty3',
                              key: 'qty3',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty3',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price3',
                              key: 'price3',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price3',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt3 = rowData.qty3 && rowData.price3
                                    ? (this.FloatMulTwo(rowData.qty3, rowData.price3)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(newRowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '??????32.5',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty4',
                              key: 'qty4',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty4',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price4',
                              key: 'price4',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price4',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt4 = rowData.qty4 && rowData.price4
                                    ? (this.FloatMulTwo(rowData.qty4, rowData.price4)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(newRowData.amt4)
                                    + Number(rowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???',
                          children: [
                            {
                              title: '?????????????????????t',
                              dataIndex: 'qty5',
                              key: 'qty5',
                              width: 130,
                              fieldsConfig: {
                                type: 'number',
                                field: 'qty5',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '?????????????????????',
                              dataIndex: 'price5',
                              key: 'price5',
                              width: 120,
                              fieldsConfig: {
                                type: 'number',
                                field: 'price5',
                                onBlur: async () => {
                                  const rowData = await this.tablehtl.getEditedRowData(false);
                                  const newRowData = {};
                                  //??????1
                                  newRowData.amt5 = rowData.qty5 && rowData.price5
                                    ? (this.FloatMulTwo(rowData.qty5, rowData.price5)).toFixed(2) : 0
                                  //???????????????(???)
                                  newRowData.scenePrice = Number(rowData.amt1)
                                    + Number(rowData.amt2)
                                    + Number(rowData.amt3)
                                    + Number(rowData.amt4)
                                    + Number(newRowData.amt5);
                                  //?????? = ??????????????? * ????????? + ???????????????
                                  await this.tablehtl.setEditedRowData({
                                    ...newRowData
                                  });
                                }
                              },
                              tdEdit: drawerTitile === '??????' ? false : true
                            },
                            {
                              title: '??????',
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
                          title: '???????????????(???)',
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
                        label: "??????",
                        onClick: () => {
                          const { myFetch } = this.props;
                          myFetch("addZxBuStockPriceItem", { orgID: orgID, mixType: this.state.htlMixType }).then(
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
                          if (drawerTitile === '??????') {
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
                              this.props.myFetch('batchDeleteUpdateZxBuStockPriceItem', obj.selectedRows).then(({ success, message, data }) => {
                                if (success) {
                                  this.tablehtl.refresh();
                                } else {
                                  Msg.error(message);
                                }
                              });
                            }
                          });
                        },
                        hide: (val) => {
                          if (drawerTitile === '??????') {
                            return true
                          } else {
                            return false
                          }
                        }
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