import React, { useState, useEffect } from 'react'
import { message as Msg, Modal } from 'antd';
import QnnTable from "../../modules/qnn-table";

const BatchEdit = (props) => {
    const [isModalVisible, setIsModalVisible] = useState(false)
    const [dataList, setDataList] = useState([])
    const [zgxc, setzgxc] = useState(null)
    // const [storageData, setStorageDataFunc] = useState(null)
    useEffect(() => {
        setIsModalVisible(props.show)
    }, [props.show])

    useEffect(() => {
        setDataList(props.dataList)
    }, [props.dataList])

    useEffect(() => {
        setzgxc(props.zgxc)
    }, [props.zgxc])

    const handleOk = async () => {
        const { success, message } = await props.myFetch('saveZxBuYgLiveFeeList', { zxBuYgjLiveFeeList: table.getTableData() })
        if (success) {
            props.updateComplete()
            Msg.success('保存成功!')
        } else {
            Msg.error(message)
        }
        props.setShowData()
        setIsModalVisible(false)
        props.updateComplete()

    }
    const handleCancel = () => {
        props.setShowData()
        setIsModalVisible(false)
    }

    const FloatMulTwo = (arg1, arg2) => {   // 加法
        let m = 0
        let r1 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        let r2 = arg1.toString().split(".")[1] ? arg1.toString().split(".")[1].length : 0
        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m

    }
    const floatAccMulFunc = (num1, num2) => {   // 乘法
        var m = 0, s1 = num1.toString(), s2 = num2.toString();
        try { m += s1.split(".")[1].length } catch (e) { };
        try { m += s2.split(".")[1].length } catch (e) { };
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }

    const moneyPlusjxtaxFunc = async (obj) => {
        // money = count + price
        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.money = floatAccMulFunc((rowData.count ? rowData.count : 0), (rowData.price ? rowData.price : 0))

        // jxtax = count + taxPrice
        rowData.jxtax = floatAccMulFunc((rowData.count ? rowData.count : 0), (rowData.taxPrice ? rowData.taxPrice : 0))

        return rowData
    }

    const moneyjxtaxcountpricetaxPriceFunc = async (obj) => {
        // money    lastcount    price   taxPrice
        // 金额(元) = 数量(人) * 工期(月) * 标准

        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.money = floatAccMulFunc(floatAccMulFunc((rowData.count ? rowData.count : 0), (rowData.price ? rowData.price : 0)), (rowData.taxPrice ? rowData.taxPrice : 0))
        return rowData
    }

    const moneyjxtaxcountpricetaxPriceFunc1 = async (obj) => {
        // lastmoney    lastcount       lastprice   lasttaxPrice
        // 金额(元) =        数量(人)    *   工期(月)  *   标准

        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.lastmoney = floatAccMulFunc(floatAccMulFunc((rowData.lastcount ? rowData.lastcount : 0), (rowData.lastprice ? rowData.lastprice : 0)), (rowData.lasttaxPrice ? rowData.lasttaxPrice : 0))
        return rowData
    }

    const countFunc = async (obj) => {

        //   money                 count       price
        // 已发生不含税金额（元）= 已发生数量 * 已发生不含税单价

        //   jxtax                 count       taxPrice
        // 已发生进项税金（元） = 已发生数量 * 已发生税金单价

        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.money = floatAccMulFunc((rowData.count ? rowData.count : 0), (rowData.price ? rowData.price : 0))
        rowData.jxtax = floatAccMulFunc((rowData.count ? rowData.count : 0), (rowData.taxPrice ? rowData.taxPrice : 0))
        return rowData
    }

    const lastcountFunc = async (obj) => {
        //   lastmoney          lastcount   lastprice
        // 后期不含税金额（元） = 后期数量  * 后期不含税单价

        //   lastjxtax          lastcount   lasttaxPrice
        // 后期进项税金（元） =   后期数量  *  后期税金单价
        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.lastmoney = floatAccMulFunc((rowData.lastcount ? rowData.lastcount : 0), (rowData.lastprice ? rowData.lastprice : 0))
        rowData.lastjxtax = floatAccMulFunc((rowData.lastcount ? rowData.lastcount : 0), (rowData.lasttaxPrice ? rowData.lasttaxPrice : 0))
        return rowData
    }

    const calculationEmployeeCompensationFunc = async (obj) => {
        let ecMoney = ''
        obj.btnCallbackFn.getTableData().map(item => {
            if (item.name === '职工薪酬') {
                ecMoney = item.money
            }
            return true
        })

        // money    ecMoney      taxPrice
        // 金额  =  职工薪酬   *   标准
        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.money = floatAccMulFunc((ecMoney ? ecMoney : zgxc ? zgxc : 0), floatAccMulFunc(rowData.taxPrice ? rowData.taxPrice : 0, 0.01))
        return rowData
    }

    const lastCalculationEmployeeCompensationFunc = async (obj) => {
        let ecMoney = ''
        obj.btnCallbackFn.getTableData().map(item => {
            if (item.name === '职工薪酬') {
                ecMoney = item.lastmoney
            }
            return true
        })

        // lastmoney    ecMoney     lasttaxPrice
        // 后期金额  =     职工薪酬   *   后期单价
        let rowData = await obj.qnnTableInstance.getEditedRowData()
        rowData.lastmoney = floatAccMulFunc((ecMoney ? ecMoney : zgxc ? zgxc : 0), floatAccMulFunc(rowData.lasttaxPrice ? rowData.lasttaxPrice : 0, 0.01))
        return rowData
    }

    const isEmployeeCompensationFunc = (obj) => {
        if (obj.name === '职工福利费' ||
            obj.name === '工会经费' ||
            obj.name === '职工教育经费' ||
            obj.name === '养老保险费' ||
            obj.name === '劳动保护费' ||
            obj.name === '医疗保险费' ||
            obj.name === '失业保险费' ||
            obj.name === '工伤保险费' ||
            obj.name === '生育保险费' ||
            obj.name === '商业保险费' ||
            obj.name === '住房公积金') {
            return true
        } else {
            return false
        }
    }

    const isSalaryLineFunc = (obj) => {
        const tableDataList = obj.btnCallbackFn.getTableData()
        let total = 0
        let rowId = ''
        let zgArrContent = []
        tableDataList.map(item => {
            const itemSplitArr = item.treeCode.split('-')
            if (itemSplitArr[0] === '0003' && itemSplitArr[1] === '0001' && itemSplitArr[2]) {
                total = FloatMulTwo(total, item[props.type === 'jlbgl_bh' ? 'money' : 'lastmoney'])
            }
            if (item.treeCode === '0003-0001') {
                rowId = item.zxBuYgjLiveFeeId
            }
            if (item.name === '职工福利费' ||
                item.name === '工会经费' ||
                item.name === '职工教育经费' ||
                item.name === '劳动保护费' ||
                item.name === '养老保险费' ||
                item.name === '医疗保险费' ||
                item.name === '失业保险费' ||
                item.name === '工伤保险费' ||
                item.name === '生育保险费' ||
                item.name === '商业保险费' ||
                item.name === '住房公积金') {
                zgArrContent.push(item)
            }
            return true
        })
        setzgxc(total)
        let rowData = obj.btnCallbackFn.getRowDataById(rowId)

        obj.btnCallbackFn.setRowDataById(rowId, {
            ...rowData,
            [props.type === 'jlbgl_bh' ? 'money' : 'lastmoney']: total
        })

        zgArrContent.map((item) => {
            return obj.btnCallbackFn.setRowDataById(item.zxBuYgjLiveFeeId, {
                ...item,
                [props.type === 'jlbgl_bh' ? 'money' : 'lastmoney']: floatAccMulFunc((total ? total : 0), floatAccMulFunc(item[props.type === 'jlbgl_bh' ? 'taxPrice' : 'lasttaxPrice'] ? item[props.type === 'jlbgl_bh' ? 'taxPrice' : 'lasttaxPrice'] : 0, 0.01))
            })
        })
    }


    let table = null
    const typeList = {
        xcwm_bh: {   // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                // zxBuYgjLiveFeeId
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveFeeId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'pp1',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'name',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        type: 'select',
                        align: 'center',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'unit',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '数量',
                        dataIndex: 'count',
                        key: 'count',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'count',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '不含税单价',
                        dataIndex: 'price',
                        key: 'price',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'price',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '不含税金额(元)',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '税金单价',
                        dataIndex: 'taxPrice',
                        key: 'taxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'taxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '进项税金(元)',
                        dataIndex: 'jxtax',
                        key: 'jxtax',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'remarks',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        },
        lsss_bh: {   // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveFeeId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'pp1',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'name',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        type: 'select',
                        align: 'center',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'unit',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '数量',
                        dataIndex: 'count',
                        key: 'count',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'count',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '不含税单价',
                        dataIndex: 'price',
                        key: 'price',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'price',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '不含税金额(元)',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '税金单价',
                        dataIndex: 'taxPrice',
                        key: 'taxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'taxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await moneyPlusjxtaxFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '进项税金(元)',
                        dataIndex: 'jxtax',
                        key: 'jxtax',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'remarks',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        },
        jlbgl_bh: {   // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveFeeId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center',
                        tdEdit: (obj) => {
                            // return obj.leaf !== 0
                            return false
                        },
                        fieldsConfig: {
                            field: 'pp1',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'name',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        type: 'select',
                        align: 'center',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'unit',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '数量(人)',
                        dataIndex: 'count',
                        key: 'count',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'count',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await calculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc(obj)
                                    })
                                }
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '工期(月)',
                        dataIndex: 'price',
                        key: 'price',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'price',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await calculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc(obj)
                                    })
                                }
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '标准',
                        dataIndex: 'taxPrice',
                        key: 'taxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'taxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await calculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc(obj)
                                    })
                                }
                            },
                            // formatter: (value, props) => {
                            //     const rowData = props.getCurRowData()
                            //     if (rowData.name === '职工福利费' ||
                            //         rowData.name === '工会经费' ||
                            //         rowData.name === '职工教育经费' ||
                            //         rowData.name === '养老保险费' ||
                            //         rowData.name === '劳动保护费' ||
                            //         rowData.name === '医疗保险费' ||
                            //         rowData.name === '失业保险费' ||
                            //         rowData.name === '工伤保险费' ||
                            //         rowData.name === '生育保险费' ||
                            //         rowData.name === '商业保险费' ||
                            //         rowData.name === '住房公积金') {
                            //         return value ? `${value.replace ? value.replace(/(%)/ig, '') : value}%` : value
                            //     } else {
                            //         return value
                            //     }

                            // }

                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '金额(元)',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'remarks',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        },
        xcwm_sg: {  // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'count',
                        type: 'number',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'pp1',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'name',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        type: 'select',
                        align: 'center',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'unit',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生不含税金额(元)',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'money',
                            type: 'number',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生进项税金(元)',
                        dataIndex: 'taxPrice',
                        key: 'taxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'taxPrice',
                            type: 'number',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期数量',
                        dataIndex: 'lastcount',
                        key: 'lastcount',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastcount',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期不含税单价',
                        dataIndex: 'lastprice',
                        key: 'lastprice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastprice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期不含税金额（元）',
                        dataIndex: 'lastmoney',
                        key: 'lastmoney',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期税金单价',
                        dataIndex: 'lasttaxPrice',
                        key: 'lasttaxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lasttaxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期进项税金（元）',
                        dataIndex: 'lastjxtax',
                        key: 'lastjxtax',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'remarks',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        },
        lsss_sg: {  // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveFeeId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center'
                    },
                    form: {
                        field: 'pp1',
                        type: 'string',
                        spanForm: 12,
                        formItemLayoutForm: {
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 6 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 18 }
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center'
                    },
                    form: {
                        field: 'name',
                        type: 'string',
                        spanForm: 12,
                        formItemLayoutForm: {
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 6 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 18 }
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        align: 'center',
                        type: 'select',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                        spanForm: 12,
                        formItemLayoutForm: {
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 6 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 18 }
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center'
                    },
                    form: {
                        field: 'unit',
                        type: 'string',
                        spanForm: 12,
                        formItemLayoutForm: {
                            labelCol: {
                                xs: { span: 24 },
                                sm: { span: 6 }
                            },
                            wrapperCol: {
                                xs: { span: 24 },
                                sm: { span: 18 }
                            }
                        }
                    }
                },
                {
                    table: {
                        title: '已发生数量',
                        dataIndex: 'count',
                        key: 'count',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'count',
                            type: 'number',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生不含税单价',
                        dataIndex: 'price',
                        key: 'price',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'price',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await countFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生不含税金额（元）',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生税金单价',
                        dataIndex: 'taxPrice',
                        key: 'taxPrice',
                        align: 'center',
                        tdEdit: true,
                        fieldsConfig: {
                            field: 'taxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await countFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生进项税金（元）',
                        dataIndex: 'jxtax',
                        key: 'jxtax',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期数量',
                        dataIndex: 'lastcount',
                        key: 'lastcount',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastcount',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期不含税单价',
                        dataIndex: 'lastprice',
                        key: 'lastprice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastprice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期不含税金额（元）',
                        dataIndex: 'lastmoney',
                        key: 'lastmoney',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期税金单价',
                        dataIndex: 'lasttaxPrice',
                        key: 'lasttaxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lasttaxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                obj.qnnTableInstance.setEditedRowData({
                                    ...await lastcountFunc(obj)
                                })
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期进项税金（元）',
                        dataIndex: 'lastjxtax',
                        key: 'lastjxtax',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        },
        jlbgl_sg: { // √
            key: 'zxBuYgjLiveFeeId',
            columns: [
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveStandardId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'zxBuYgjLiveFeeId',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'projType',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'parentID',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    isInTable: false,
                    form: {
                        field: 'treeCode',
                        type: 'string',
                        hide: true,
                    }
                },
                {
                    table: {
                        title: '编号',
                        dataIndex: 'pp1',
                        key: 'pp1',
                        align: 'center',
                        tdEdit: (obj) => {
                            return false
                        },
                        fieldsConfig: {
                            field: 'pp1',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '名称',
                        dataIndex: 'name',
                        key: 'name',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'name',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '节点类型',
                        dataIndex: 'nodeType',
                        key: 'nodeType',
                        type: 'select',
                        align: 'center',
                    },
                    form: {
                        field: 'nodeType',
                        type: 'select',
                        optionConfig: {
                            label: 'itemName',
                            value: 'itemId'
                        },
                        placeholder: "请输入",
                        fetchConfig: {
                            apiName: 'getBaseCodeSelect',
                            otherParams: {
                                itemId: 'jieDianLeiXing'
                            }
                        },
                    }
                },
                {
                    table: {
                        title: '单位',
                        dataIndex: 'unit',
                        key: 'unit',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'unit',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '已发生金额(元)',
                        dataIndex: 'money',
                        key: 'money',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'money',
                            type: 'number',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期数量(人)',
                        dataIndex: 'lastcount',
                        key: 'lastcount',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastcount',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await lastCalculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc1(obj)
                                    })
                                }
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期工期(月)',
                        dataIndex: 'lastprice',
                        key: 'lastprice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.name !== '职工福利费' &&
                                obj.name !== '工会经费' &&
                                obj.name !== '职工教育经费' &&
                                obj.name !== '养老保险费' &&
                                obj.name !== '医疗保险费' &&
                                obj.name !== '失业保险费' &&
                                obj.name !== '劳动保护费' &&
                                obj.name !== '工伤保险费' &&
                                obj.name !== '生育保险费' &&
                                obj.name !== '商业保险费' &&
                                obj.name !== '住房公积金' &&
                                obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lastprice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await lastCalculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc1(obj)
                                    })
                                }
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期单价',
                        dataIndex: 'lasttaxPrice',
                        key: 'lasttaxPrice',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'lasttaxPrice',
                            type: 'number',
                            onChange: async (val, obj) => {
                                if (isEmployeeCompensationFunc(obj.rowData)) {
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await lastCalculationEmployeeCompensationFunc(obj)
                                    })
                                } else {
                                    isSalaryLineFunc(obj)
                                    obj.qnnTableInstance.setEditedRowData({
                                        ...await moneyjxtaxcountpricetaxPriceFunc1(obj)
                                    })
                                }
                            }
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '后期金额(元)',
                        dataIndex: 'lastmoney',
                        key: 'lastmoney',
                        align: 'center',
                        fieldsConfig: {
                            field: 'lastmoney',
                            type: 'number',
                        }
                    },
                    isInForm: false
                },
                {
                    table: {
                        title: '备注',
                        dataIndex: 'remarks',
                        key: 'remarks',
                        align: 'center',
                        tdEdit: (obj) => {
                            return obj.leaf !== 0
                        },
                        fieldsConfig: {
                            field: 'remarks',
                            type: 'string',
                        }
                    },
                    isInForm: false
                },
                {
                    isInTable: false,
                    form: {
                        field: 'leaf',
                        type: 'number',
                        hide: true,
                    }
                }
            ]
        }
    }
    return (
        <div>
            <Modal
                title="批量修改"
                width={'80%'}
                visible={isModalVisible}
                okText={'保存'}
                onOk={handleOk}
                onCancel={handleCancel}
                destroyOnClose={true}
            >
                <QnnTable
                    fetch={props.myFetch}
                    myFetch={props.myFetch}
                    upload={props.myUpload}
                    antd={
                        { rowKey: typeList[props.type].key }
                    }
                    isShowRowSelect={false}
                    headers={{ token: props.loginAndLogoutInfo.loginInfo.token }}
                    wrappedComponentRef={(me) => { table = me }}
                    method={{}}
                    data={dataList}
                    formConfig={typeList[props.type].columns}
                // pagination={{
                //     onChange: (page) => {
                //         // 只有经理部需要特殊处理
                //         if (props.type === 'jlbgl_bh' || props.type === 'jlbgl_sg') {
                //             const tableData = table.getTableData()
                //             let total = null
                //             setTimeout(() => {
                //                 let employeeCompensationRef = null
                //                 tableData.map(item => {
                //                     if (item.treeCode === '0003-0001') {
                //                         employeeCompensationRef = table.getTdRef({
                //                             rowId: item.zxBuYgjLiveFeeId, //行id
                //                             field: props.type === 'jlbgl_bh' ? 'money' : 'lastmoney'
                //                         })
                //                         total = item[props.type === 'jlbgl_bh' ? 'money' : 'lastmoney']
                //                     }
                //                 })

                //                 if (employeeCompensationRef && +sessionStorage.getItem('storageData' + props.type)) {
                //                     employeeCompensationRef.setTdData(+sessionStorage.getItem('storageData' + props.type))
                //                 }

                //                 let zgArrContent = JSON.parse(sessionStorage.getItem('zgArrContent' + props.type))
                //                 let zgArrRefs = {}
                //                 Object.keys(zgArrContent).map(item => {
                //                     zgArrRefs[item] = table.getTdRef({
                //                         rowId: zgArrContent[item].zxBuYgjLiveFeeId, //行id
                //                         field: props.type === 'jlbgl_bh' ? 'money' : 'lastmoney'
                //                     })
                //                 })
                //                 Object.keys(zgArrRefs).map(item => {
                //                     if (zgArrRefs[item]) {
                //                         zgArrRefs[item].setTdData(floatAccMulFunc(total, zgArrContent[item][props.type === 'jlbgl_bh' ? 'taxPrice' : 'lasttaxPrice'] ? zgArrContent[item][props.type === 'jlbgl_bh' ? 'taxPrice' : 'lasttaxPrice'] : 0))
                //                     }
                //                 })
                //             }, 300);

                //         }
                //     }
                // }}
                />
            </Modal>
        </div>
    )
}




export default BatchEdit