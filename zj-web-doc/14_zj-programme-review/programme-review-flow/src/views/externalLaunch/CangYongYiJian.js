import React,{ useState,useEffect } from "react";
import {
    Form,
    Icon,
    // Row,
    // Col,
    Checkbox,
    message as Msg,
    Input
} from 'antd';
// import moment from "moment";

const cangYongYiJian = (props) => {
    // console.log("props",props)
    const _dataList = [
        // {
        //     id: "0",
        //     text: "这是第一条常用数据"
        // } 
    ];

    const [dataList,setDataList] = useState(_dataList);
    const [isChecked,setIsChecked] = useState(false);
    const [editData,setEditData] = useState(null);
    const [isGetDataEd,setIsGetDataEd] = useState(false);
    const [isFetching,setIsFetching] = useState(false);

    const isDisabled = dataList.filter((item) => {
        return item.text === props.form.getFieldValue("apiBody.opinionContent")
    })[0];


    //获取数据
    useEffect(() => {
        if (!isGetDataEd && !isFetching) {
            setIsFetching(true)
            props.myFetch("getZjPrProgrammeCommonOpinionsList").then(({ success,data,message }) => {
                setIsFetching(false)
                if (success) {
                    setDataList(data.map(item => {
                        item.id = item.optionsId;
                        item.text = item.commonOption;
                        return item
                    }))
                    setIsGetDataEd(true);
                } else {
                    Msg.error(message)
                }
            })
        }
    })


    const delFn = (delItem) => { 
        props.myFetch("batchDeleteUpdateZjPrProgrammeCommonOpinions",[delItem]).then(({ success,data,message }) => {
            if (success) {
                setDataList(dataList.filter(item => {
                    return item.id !== delItem.id
                }))
                Msg.success(message);
            } else {
                Msg.error(message)
            }
        })
    }

    const editFn = (item) => {
        setEditData(item);
    }

    const saveEditFn = (val) => {
        setEditData(null);
        props.myFetch("updateZjPrProgrammeCommonOpinions",{
            optionsId: editData.id,//主键ID
            commonOption: val//常用信息 
        }).then(({ success,message }) => {
            if (success) {
                setDataList(dataList.map(item => {
                    if (item.id == editData.id) {
                        item.text = val;
                    }
                    return item;
                }))
                setEditData(null);
                Msg.success(message);
            } else {
                Msg.error(message)
            }
        })
    }


    return <div>
        <Form.Item
            wrapperCol={{
                sm: { span: 20 },
            }}
            labelCol={{
                sm: { span: 3 }
            }}
            label="常用意见"
        >
            <div
                style={{
                    // border: "1px solid red",
                    padding: "0px 12px",
                    boxSizing: "border-box"
                }}
            >
                {
                    dataList && dataList.map((item) => {
                        const { text,id } = item;
                        return <div key={id} style={{
                            borderBottom: "1px solid rgba(227, 219, 219, 0.69)",
                            lineHeight: "22px",padding: "6px 0px",display: "flex",justifyContent: "space-between"
                        }}>
                            <a>
                                {
                                    editData && editData.id === id ? (<Input
                                        autoFocus
                                        onBlur={(e) => { saveEditFn(e.target.value) }}
                                        onPressEnter={(e) => { saveEditFn(e.target.value) }}
                                        defaultValue={text} placeholder="请输入..." />) : (<span
                                            onClick={() => {
                                                props.form.setFieldsValue({
                                                    "apiBody.opinionContent": text,
                                                    "apiBody.usedFlag": "1"
                                                })
                                                setIsChecked(false)
                                            }}
                                        >{text}</span>)
                                }
                                {
                                    !editData ? <span
                                        onClick={() => { editFn(item) }}
                                        style={{ marginLeft: "8px" }}>
                                        <Icon type="edit" />
                                    </span> : null
                                }

                            </a>

                            {
                                !editData ? <a
                                    onClick={() => { delFn(item) }}
                                    style={{ color: "orangered" }}><Icon type="delete" /></a> : null
                            }

                        </div>
                    })
                }

                <Checkbox
                    checked={isChecked}
                    disabled={isDisabled ? true : false}
                    onChange={(e) => {
                        if (e.target.checked) {
                            props.form.setFieldsValue({
                                "apiBody.usedFlag": "0"
                            })
                        } else {
                            props.form.setFieldsValue({
                                "apiBody.usedFlag": "1"
                            })
                        }
                        setIsChecked(e.target.checked)
                    }}>
                    是否将当前意见加入到常用意见
                </Checkbox>
            </div>
        </Form.Item>
    </div>
}
export default cangYongYiJian;