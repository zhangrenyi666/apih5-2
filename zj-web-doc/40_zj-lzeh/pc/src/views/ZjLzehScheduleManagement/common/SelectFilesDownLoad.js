import React, { Component } from "react";
import { PaperClipOutlined, DownloadOutlined } from '@ant-design/icons';
import { Button, message as Msg, Row, Col, Select } from "antd";
const { Option } = Select;
class SelectFilesDownLoad extends Component {
    constructor(props) {
        super(props);
        this.state = {
            changeUrl: this.props.dataList[0].url
        }
    }
    render() {
        const { changeUrl } = this.state
        return (
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <Select style={{ width: 120 }} defaultValue={this.props.dataList[0].url} onChange={(value) => {
                    this.setState({
                        changeUrl:value
                    })
                }}>
                    {
                        this.props.dataList.map(item => {
                            return (<Option value={item.url} key={item.uid}>
                                <span>
                                    <PaperClipOutlined />
                                    <span style={{ marginLeft: 6 }}>{item.name}</span>
                                </span>
                            </Option>)
                        })
                    }

                </Select>
                <Button type="primary" icon={<DownloadOutlined />} onClick={() => {
                    window.open(changeUrl)
                }} size={'middle'} />
            </div>
        )
    }
}
export default SelectFilesDownLoad;