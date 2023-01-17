import * as icons from '@ant-design/icons';
import React from 'react';
import type {SelectProps} from 'antd';
import {Space} from 'antd';

export const iconMap: any = Object.keys(icons)
  // @ts-ignore
  .filter((key) => typeof icons[key] === 'object')
  // @ts-ignore
  .reduce((acc, key) => ({ ...acc, [key]: icons[key] }), {});


const IconOption: SelectProps['options'] | string[] = (
  Object.keys(iconMap).map((item) => ({
      value: item,
      label: (
        <Space>
          {iconMap[item] && React.createElement(iconMap[item])}
          {item}
        </Space>
      ),
    }))
)

export default IconOption;
