import React from "react";
import { Input,Select,DatePicker,message as Msg } from "antd";
import moment from "moment";
const { MonthPicker } = DatePicker;
const Option = Select.Option;
const index = function (
    type,
    title = "",
    dataIndex,
    optionData = [],
    fetchConfig={},
    optionConfig = {
        label:"label",
        value:"value"
    },
    colSearch,
    setSelectedKeys,
    selectedKeys,
    confirm,
    clearFilters,
    colSearchClear 
) {
    const { label,value } = optionConfig;
    switch (type) {
        case "string":
        case "number":
            return (
                <Input
                    type={type}
                    ref={node => {
                        this.searchInput = node;
                    }}
                    placeholder={`${title || "搜索..."}`}
                    value={selectedKeys}
                    onChange={e =>
                        setSelectedKeys(e.target.value ? e.target.value : "")
                    }
                    onPressEnter={() => {
                        colSearch(dataIndex,selectedKeys,confirm);
                    }}
                    style={{ width: "100%",marginBottom: 8,display: "block" }}
                />
            );

        case "datetime":
        case "date":
            if (Array.isArray(selectedKeys)) {
                selectedKeys = moment().valueOf();
            }
            return (
                <DatePicker
                    format="YYYY-MM-DD HH:mm:ss"
                    showTime={false}
                    allowClear
                    autoFocus
                    ref={node => {
                        this.searchInput = node;
                    }}
                    placeholder={`${title || "搜索..."}`}
                    value={moment(selectedKeys)}
                    onPressEnter={() => {
                        colSearch(dataIndex,selectedKeys,confirm);
                    }}
                    style={{ width: "100%",marginBottom: 8,display: "block" }}
                    onChange={data => {
                        if (data) {
                            setSelectedKeys(data.valueOf());
                        } else {
                            colSearchClear(dataIndex,clearFilters)
                        }
                    }}
                />
            );
        case "month":
            if (Array.isArray(selectedKeys)) {
                selectedKeys = null;
            } 
            return (
                <MonthPicker 
                    autoFocus
                    ref={node => {
                        this.searchInput = node;
                    }}
                    placeholder={`${title || "搜索..."}`}
                    value={selectedKeys ? moment(selectedKeys) : null}
                    onPressEnter={() => {
                        colSearch(dataIndex,selectedKeys,confirm);
                    }}
                    style={{ width: "100%",marginBottom: 8,display: "block" }}
                    onChange={data => {
                        if (data) {
                            setSelectedKeys(data.valueOf());
                        } else {
                            colSearchClear(dataIndex,clearFilters)
                        }
                    }}
                />
            );
        case "select":
            const _optionData = this.state[`${dataIndex}-optionData`] || optionData || [];
            // console.log(_optionData)
            // console.log(this.state)
            return (
                <Select
                    ref={node => {
                        this.searchInput = node;
                    }}
                    value={selectedKeys}
                    style={{ width: "100%",marginBottom: 8,display: "block" }}
                    placeholder={`${title || "搜索..."}`}
                    onPressEnter={() => {
                        colSearch(dataIndex,selectedKeys,confirm);
                    }}
                    onChange={value => {
                        // if (typeof value === "array") {
                        //   value = value.join();
                        // }
                        setSelectedKeys(value ? value : "");
                    }}
                    onFocus={() => {
                        const {
                            apiName,
                            params = {},
                            otherParams = {}
                        } = fetchConfig;
                        if (!apiName) {
                            this.setState({
                                [`${dataIndex}-optionData`]: optionData || []
                            });
                        } else {
                            if (!apiName) {
                                console.error(
                                    "select form属性的fetchConfig属性必须设置apiName"
                                );
                                return;
                            }
                            this.fetch(apiName,{
                                ...params,
                                ...otherParams
                            }).then(res => {
                                let { data,success,message } = res;
                                if (success) {
                                    this.setState({
                                        [`${dataIndex}-optionData`]: data
                                    });
                                } else {
                                    Msg.error(message,1);
                                }
                            });
                        }
                    }}
                >
                    {_optionData.map((item,index) => {
                        return (
                            <Option value={item[value]} key={index}>
                                {item[label]}
                            </Option>
                        );
                    })}
                </Select>
            );
        default:
            //类型错误将不渲染
            return <div />;
    }
};
export default index;
