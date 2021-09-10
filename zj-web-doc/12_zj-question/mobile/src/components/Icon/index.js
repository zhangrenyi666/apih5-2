import React from 'react';
import { Icon } from 'antd-mobile';
const MyIcon = ({ type, className = '', size = 'md', ...restProps }) => {
    new Icon()
    return (
        <svg 
            className={`am-icon am-icon-${type} am-icon-${size} ${className}`}
            {...restProps}
        >
            {/* <use xlinkHref={type} /> */} {/* svg-sprite-loader@0.3.x */}
            <use xlinkHref={`#${type}`} /> {/* svg-sprite-loader@lastest */}
        </svg>
    )
};

export default MyIcon