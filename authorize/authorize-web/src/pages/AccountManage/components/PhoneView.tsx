import React from 'react';

import { Input } from 'antd';

type PhoneViewProps = {
  value?: string;
  onChange?: (value: string) => void;
};

const PhoneView: React.FC<PhoneViewProps> = (props) => {
  const { value, onChange } = props;
  let values = ['', ''];
  if (value) {
    values = value.split('-');
  }

  return (
    <>
      <Input
        value={values[0]}
        onChange={(e) => {
          if (onChange) {
            onChange(`${e.target.value}-${values[1]}`);
          }
        }}
      />
      <Input
        onChange={(e) => {
          if (onChange) {
            onChange(`${values[0]}-${e.target.value}`);
          }
        }}
        value={values[1]}
      />
    </>
  );
};

export default PhoneView;
