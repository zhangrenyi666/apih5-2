import React from 'react';
import { Icon } from 'antd-mobile'
new Icon()
const svgIcon = ({ type, className = '', size = 'md', ...restProps }) => {
    let svgItem = require(`./svgs/${type}.svg`)
    if (svgItem) {
        return (
            <svg
                className={`am-icon am-icon-${svgItem.default.id} am-icon-${size} ${className}`}
                {...restProps}
            >

                {/* <use xlinkHref={type} /> svg-sprite-loader@0.3.x */}
                <use xlinkHref={`#${svgItem.default.id}`} /> {/* svg-sprite-loader@lastest */}
            </svg>
        )
    } else {
        return ""
    }

};
export default svgIcon