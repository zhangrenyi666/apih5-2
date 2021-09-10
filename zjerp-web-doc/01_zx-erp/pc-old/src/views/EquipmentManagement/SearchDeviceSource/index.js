import React, { Component } from "react";
import QnnTable from "../../modules/qnn-table";
import { message as Msg, Spin, Button } from 'antd';
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
const pieInitialValue = [{ value: 0, name: '租赁' }, { value: 0, name: '协作队伍自带' }, { value: 0, name: '自有' }, { value: 0, name: '总计' }]
class index extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loading: false,
      bsid: '',
      showReactEcharts: true,
      pieOneData: pieInitialValue,
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
        data: ['租赁', '协作队伍自带', '自有', '总计']
      },
      color: ['rgb(26,170,240)', 'rgb(50,200,240)', 'rgb(160,230,180)', 'rgb(250,80,150)'],
      series: [
        {
          name: '数量',
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
            {showReactEcharts ? <ReactEcharts
              option={this.getDataOption('台数', this.state.pieOneData)}
              notMerge={true}
              lazyUpdate={true}
              theme={"theme_name"}
            /> : null}
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
                  label: '设备分类',
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
                apiName: 'getZxEqEquipListForEquipSource',
                otherParams: { departmentPath: '9999999999', purDate, numberFlag: 'sysDepartment' },
                success: ({ data, success, message }) => {
                  if (success && data.length > 0) {
                    let pieOneData = [{ value: data[0].zlCount, name: '租赁' }, { value: data[0].xzCount, name: '协作队伍自带' }, { value: data[0].zyCount, name: '自有' }, { value: data[0].totalCount, name: '总计' }]
                    this.setState({ pieOneData })
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
                      var rowData = obj.rowData;
                      var btnCallbackFn = obj.btnCallbackFn;
                      var setState = btnCallbackFn.setState;
                      var oldData = obj.state.data;
                      var expandedRowKeys = obj.state.expandedRowKeys;
                      var parentID = rowData.departmentId;
                      let pieOneData = [{ value: rowData.zlCount, name: '租赁' }, { value: rowData.xzCount, name: '协作队伍自带' }, { value: rowData.zyCount, name: '自有' }, { value: rowData.totalCount, name: '总计' }]
                      this.setState({ pieOneData })
                      if (rowData.departmentParentId !== '9999999999' && rowData.departmentPath !== '9999999999') return
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
                      this.props.myFetch('getZxEqEquipListForEquipSource', {
                        departmentParentId: parentID,
                        purDate,
                        numberFlag: rowData.departmentPath === '9999999999' ? 'sysDepartment' : 'sysProject'
                      }).then((res) => {
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
                    title: '租赁',
                    dataIndex: 'title_1',
                    key: 'title_1',
                    width: 270,
                    children: [
                      {
                        title: '数量',
                        dataIndex: 'zlCount',
                        key: 'zlCount',
                        filter: true,
                      },
                      {
                        title: '金额',
                        dataIndex: 'zlAmount',
                        key: 'zlAmount',
                        filter: true,
                      },
                    ]
                  },
                },
                {
                  table: {
                    title: '协作队伍自带',
                    dataIndex: 'title_2',
                    key: 'title_2',
                    width: 270,
                    children: [
                      {
                        title: '数量',
                        dataIndex: 'xzCount',
                        key: 'xzCount',
                        filter: true,
                      },
                      {
                        title: '金额',
                        dataIndex: 'xzAmount',
                        key: 'xzAmount',
                        filter: true,
                      }
                    ]
                  },
                },
                {
                  table: {
                    title: '自有',
                    dataIndex: 'title_3',
                    key: 'title_3',
                    width: 270,
                    children: [
                      {
                        title: '数量',
                        dataIndex: 'zyCount',
                        key: 'zyCount',
                        filter: true,
                      },
                      {
                        title: '金额',
                        dataIndex: 'zyAmount',
                        key: 'zyAmount',
                        filter: true,
                      }
                    ]
                  },
                },
                {
                  table: {
                    title: '总计',
                    dataIndex: 'title_4',
                    key: 'title_4',
                    width: 270,
                    children: [
                      {
                        title: '数量',
                        dataIndex: 'totalCount',
                        key: 'totalCount',
                        filter: true,
                      },
                      {
                        title: '金额',
                        dataIndex: 'totalAmount',
                        key: 'totalAmount',
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