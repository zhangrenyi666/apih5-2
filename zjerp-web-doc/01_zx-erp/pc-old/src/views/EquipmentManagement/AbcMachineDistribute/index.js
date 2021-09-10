import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin, Button, Row, Col, } from 'antd';
import QnnForm from "../../modules/qnn-table/qnn-form";
import ReactEcharts from 'echarts-for-react';
import { DownOutlined, LeftOutlined } from '@ant-design/icons';
import moment from 'moment';
const configs = {
  antd: {
    rowKey: 'departmentId',
    size: 'small'
  },
  paginationConfig: {
    position: 'bottom'
  },
  drawerConfig: {
    width: '900px'
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
  },
  isShowRowSelect: false
}
const pieInitialValue = [{ value: 0, name: 'A类' }, { value: 0, name: 'B类' }, { value: 0, name: 'C类' }, { value: 0, name: 'D类' }]
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      bsid: '',
      showReactEcharts: true,
      pieOneData: pieInitialValue,
      pieTwoData: pieInitialValue,
      pieThreeData: pieInitialValue,
      purDate: null
    }
  }
  getDataOption = (name, data) => {
    var option = {
      title: {
        text: name,
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        data: ['A类', 'B类', 'C类', 'D类']
      },
      color: ['rgb(26,170,240)', 'rgb(50,200,240)', 'rgb(160,230,180)', 'rgb(250,80,150)'],
      series: [
        {
          name,
          type: 'pie',
          radius: '55%',
          center: ['50%', '60%'],
          data,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
    return option;
  }

  render() {
    const { loading, showReactEcharts, purDate } = this.state;
    return (
      <Spin spinning={loading}>
        <div>
          <div style={{ height: '40%', marginBottom: 10 }}>
            <div style={{ textAlign: 'right' }}>
              <Button
                type="primary"
                icon={showReactEcharts ? <DownOutlined /> : <LeftOutlined />}
                onClick={() => {
                  this.setState({ showReactEcharts: !showReactEcharts })
                }}
                size='small'
              />
            </div>
            {showReactEcharts ? <Row justify="space-around">
              <Col span={8}><ReactEcharts
                option={this.getDataOption('台数', this.state.pieOneData)}
                notMerge={true}
                lazyUpdate={true}
                theme={"theme_name"}
              /></Col>
              <Col span={8}><ReactEcharts
                option={this.getDataOption('原值', this.state.pieTwoData)}
                notMerge={true}
                lazyUpdate={true}
                theme={"theme_name"}
              /></Col>
              <Col span={8}><ReactEcharts
                option={this.getDataOption('净值', this.state.pieThreeData)}
                notMerge={true}
                lazyUpdate={true}
                theme={"theme_name"}
              /></Col>
            </Row> : null}
          </div>
          <div>
            <QnnForm
              wrappedComponentRef={(me) => {
                this.formHasTicket = me;
              }}
              formItemLayout={{
                labelCol: {
                  xs: { span: 6 },
                  sm: { span: 6 }
                },
                wrapperCol: {
                  xs: { span: 18 },
                  sm: { span: 18 }
                }
              }}
              formConfig={[
                {
                  type: 'year',
                  label: '年度',
                  field: 'purDate',
                  span: 6
                },
                {
                  type: 'component',
                  field: 'aa',
                  Component: obj => {
                    return (
                      <div style={{ textAlign: 'center', padding: '10px' }}><Button type="primary" onClick={() => {
                        let value = this.formHasTicket.form.getFieldsValue();
                        this.setState({
                          purDate: null,
                        }, () => {
                          this.setState({
                            purDate: value.purDate ? moment(value.purDate).endOf('year').valueOf() : null,
                          }, () => {
                            this.table.refresh();
                          })
                        })
                      }}>查询</Button></div>
                    );
                  },
                  span: 2
                }
              ]}
            />
            <QnnTable
              {...this.props}
              fetch={this.props.myFetch}
              upload={this.props.myUpload}
              headers={{ token: this.props.loginAndLogoutInfo.loginInfo.token }}
              wrappedComponentRef={(me) => {
                this.table = me;
              }}
              fetchConfig={{
                apiName: 'getZxEqEquipListForABCDDistribute',
                otherParams: { departmentPath: '9999999999', purDate },
                success: ({ data, success, message }) => {
                  if (success && data.length > 0) {
                    let pieOneData = [{ value: data[0].aa, name: 'A类' }, { value: data[0].bb, name: 'B类' }, { value: data[0].cc, name: 'C类' }, { value: data[0].dd, name: 'D类' }]
                    let pieTwoData = [{ value: data[0].orginalValue1, name: 'A类' }, { value: data[0].orginalValue2, name: 'B类' }, { value: data[0].orginalValue3, name: 'C类' }, { value: data[0].orginalValue4, name: 'D类' }]
                    let pieThreeData = [{ value: data[0].leftValue1, name: 'A类' }, { value: data[0].leftValue2, name: 'B类' }, { value: data[0].leftValue3, name: 'C类' }, { value: data[0].leftValue4, name: 'D类' }]
                    this.setState({ pieOneData, pieTwoData, pieThreeData })
                  } else {
                    Msg.error(message)
                  }
                }
              }}
              {...configs}
              formConfig={[
                {
                  table: {
                    title: '机构',
                    dataIndex: 'departmentName',
                    key: 'departmentName',
                    width: 360,
                    fixed: 'left',
                    onClick: (obj) => {
                      console.log(obj);
                      var rowData = obj.rowData;
                      var btnCallbackFn = obj.btnCallbackFn;
                      var setState = btnCallbackFn.setState;
                      var oldData = obj.state.data;
                      var expandedRowKeys = obj.state.expandedRowKeys;
                      var parentID = rowData.departmentId;
                      let pieOneData = [{ value: rowData.aa, name: 'A类' }, { value: rowData.bb, name: 'B类' }, { value: rowData.cc, name: 'C类' }, { value: rowData.dd, name: 'D类' }]
                      let pieTwoData = [{ value: rowData.orginalValue1, name: 'A类' }, { value: rowData.orginalValue2, name: 'B类' }, { value: rowData.orginalValue3, name: 'C类' }, { value: rowData.orginalValue4, name: 'D类' }]
                      let pieThreeData = [{ value: rowData.leftValue1, name: 'A类' }, { value: rowData.leftValue2, name: 'B类' }, { value: rowData.leftValue3, name: 'C类' }, { value: rowData.leftValue4, name: 'D类' }]
                      this.setState({ pieOneData, pieTwoData, pieThreeData })
                      if (rowData.departmentParentId === '9999999999') return
                      if (rowData.children && rowData.children.length) {
                        if (!expandedRowKeys.includes(parentID)) {
                          expandedRowKeys.push(parentID);
                          setState({
                            expandedRowKeys: [],
                          }, () => {
                            setState({
                              expandedRowKeys: expandedRowKeys,
                            })
                          })
                        } else {
                          var index = expandedRowKeys.indexOf(parentID);
                          expandedRowKeys.splice(index, 1)
                          setState({
                            expandedRowKeys: [],
                          }, () => {
                            setState({
                              expandedRowKeys: expandedRowKeys,
                            })
                          })
                        }
                        return;
                      }
                      this.props.myFetch('getZxEqEquipListForABCDDistribute', { departmentParentId: parentID, purDate })
                        .then((res) => {
                          var success = res.success;
                          var childrenData = res.data;
                          var message = res.message;
                          if (!childrenData.length) {
                            Msg.warn("该节点没有子集数据")
                            return;
                          }
                          if (success) {
                            var loopFn = function (data) {
                              for (var i = 0; i < data.length; i++) {
                                if (data[i].departmentId === parentID) {
                                  data[i].children = childrenData;
                                } else if (data[i].children) {
                                  data[i].children = loopFn(data[i].children)
                                }
                              }
                              return data;
                            }
                            oldData = loopFn(oldData);
                            if (!expandedRowKeys.includes(parentID)) {
                              expandedRowKeys.push(parentID);
                            }
                            setState({
                              expandedRowKeys: expandedRowKeys,
                              data: oldData,
                            }, () => {
                              setState({
                                expandedRowKeys: expandedRowKeys,
                              })
                            })
                          } else {
                            Msg.error(message)
                          }
                        })
                    }
                  },
                },
                {
                  table: {
                    title: 'A类',
                    dataIndex: 'title_1',
                    key: 'title_1',
                    width: 270,
                    children: [
                      {
                        title: '台数',
                        dataIndex: 'aa',
                        key: 'aa',
                        filter: true,
                      },
                      {
                        title: '原值',
                        dataIndex: 'orginalValue1',
                        key: 'orginalValue1',
                        filter: true,
                      },
                      {
                        title: '净值',
                        dataIndex: 'leftValue1',
                        key: 'leftValue1',
                        filter: true,
                      }
                    ]
                  },
                },
                {
                  table: {
                    title: 'B类',
                    dataIndex: 'title_2',
                    key: 'title_2',
                    width: 270,
                    children: [
                      {
                        title: '台数',
                        dataIndex: 'bb',
                        key: 'bb',
                        filter: true,
                      },
                      {
                        title: '原值',
                        dataIndex: 'orginalValue2',
                        key: 'orginalValue2',
                        filter: true,
                      },
                      {
                        title: '净值',
                        dataIndex: 'leftValue2',
                        key: 'leftValue2',
                        filter: true,
                      }
                    ]
                  },
                },
                {
                  table: {
                    title: 'C类',
                    dataIndex: 'title_3',
                    key: 'title_3',
                    width: 270,
                    children: [
                      {
                        title: '台数',
                        dataIndex: 'cc',
                        key: 'cc',
                        filter: true,
                      },
                      {
                        title: '原值',
                        dataIndex: 'orginalValue3',
                        key: 'orginalValue3',
                        filter: true,
                      },
                      {
                        title: '净值',
                        dataIndex: 'leftValue3',
                        key: 'leftValue3',
                        filter: true,
                      }
                    ]
                  },
                },
                {
                  table: {
                    title: 'D类',
                    dataIndex: 'title_4',
                    key: 'title_4',
                    width: 270,
                    children: [
                      {
                        title: '台数',
                        dataIndex: 'dd',
                        key: 'dd',
                        filter: true,
                      },
                      {
                        title: '原值',
                        dataIndex: 'orginalValue4',
                        key: 'orginalValue4',
                        filter: true,
                      },
                      {
                        title: '净值',
                        dataIndex: 'leftValue4',
                        key: 'leftValue4',
                        filter: true,
                      }
                    ]
                  },
                },
              ]}
            />
          </div>
        </div>
      </Spin>
    );
  }
}

export default index;