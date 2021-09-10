import React, { useState, useEffect } from 'react'
// import { message as Msg, Modal } from "antd";
import { PaperClipOutlined } from '@ant-design/icons';
const TableFileDownLoad = (props) => {
    return (
        <div>
            {
                props.info ? <div>
                    <PaperClipOutlined style={{ color: '#ccc', padding: '0 8px' }} />
                    <span style={{ color: '#1890ff', cursor: 'pointer' }} onClick={() => {
                        const { access_token } = props.loginAndLogoutInfo.loginInfo
                        const link = document.createElement("a")
                        link.target = '_blank'
                        link.href = props.info.url + '&access_token=' + access_token
                        link.download = props.info.name
                        link.click();
                    }}>{props.info.name}</span>
                </div> : <div></div>
            }
        </div>
    )
}

export default TableFileDownLoad
