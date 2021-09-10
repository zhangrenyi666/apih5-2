import React, { useState, useEffect } from 'react'
import FlowFormByMaterialManagementContract from './form';

const WillExecute = (props) => {
    const [showStatus, setShowStatus] = useState()
    const [messageContent, setMessageContent] = useState()
    useEffect(async () => {
        const {  success, message } = await props.myFetch('checkRequiredTab', { extensionHistoryId: props.flowData.extensionHistoryId })
        if (success) {
            // flowData && flowData.contentLength > 0
            setShowStatus(true)
        } else {
            setShowStatus(false)
            setMessageContent(message)
        }
    }, [])
    return (
        <div>
            {
                <div>
                    {
                        showStatus ?
                            <FlowFormByMaterialManagementContract  {...props} type={'salary'} btnCallbackFn={props.btnCallbackFn} isInQnnTable={true} flowData={{ ...props.flowData }} /> :
                            <div style={{ fontSize: '20px', padding: '10px' }}>{messageContent}</div>
                    }
                </div>
            }

        </div>
    )
}
export default WillExecute