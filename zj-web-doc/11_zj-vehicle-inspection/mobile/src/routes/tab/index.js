import React from 'react';
const Tab = ({ dispatch, tab, getUserInfo }) => {
    const { companyList } = getUserInfo()
    return (
        <div style={{ fontSize: "36px", fontWeight: "bold" }}>
            {companyList.map((item, index) => {
                const { companyId, companyName } = item
                return (
                    <div
                        key={companyId}
                        onClick={() => {
                            tab(companyId).then(({ action, message }) => {
                                dispatch(action)
                            })
                        }} > {companyName} </div >
                )
            })}
        </div>
    )
}
export default Tab
