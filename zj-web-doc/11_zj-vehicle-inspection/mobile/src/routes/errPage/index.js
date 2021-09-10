import React from 'react';
const ErrPage = ({ history, match: { params: { errCode } } }) => {
    let text = ""
    switch (errCode) {
        case "404":
            text = "你似乎来到了荒芜之地..."
            break;
        case "110":
            text = "你来到了不该来的的地方..."
            break;
        default:
            text = "Other error"
            break;
    }
    return (
        <div>
            {text}
            {`,`}
            <span onClick={() => {
                history.go(-1)
            }}>{`返回`}</span>
        </div>
    )
}
export default ErrPage
