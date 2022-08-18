import { PageContainer } from '@ant-design/pro-layout';
import React from "react";
import XFlowEditor from "@/components/XFlowEditor";


const FlowEditor: React.FC = () => {

  return (
    <PageContainer content="千言万语不如一张图，流程图是表示算法思路的好方法">
      <XFlowEditor />
    </PageContainer>
  );
}

export default FlowEditor;

