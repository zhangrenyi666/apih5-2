import React from 'react';
const blankLayout = (Component) => {
    return (props) => {
        return (
            <Component {...props} />
        )
    }
}
export default blankLayout