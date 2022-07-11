import * as allIcon from '@ant-design/icons';
import React from 'react';
import type {SelectProps} from 'antd';
import {Space} from 'antd';

export const iconMap = { ...allIcon };

const IconOption: SelectProps<any>['options'] | string[] = (
  Object.keys(iconMap)
    .filter(
      (item) =>
        item !== 'IconProvider' &&
        item !== 'createFromIconfontCN' &&
        item !== 'default' &&
        item !== 'getTwoToneColor' &&
        item !== 'setTwoToneColor',
    )
    .map((item) => ({
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
