import { Col, Row } from 'antd';

import { PageContainer } from '@ant-design/pro-layout';
import styles from './index.less';
import React, {useEffect} from "react";
import type { Graph } from '@antv/g6';
import G6 from '@antv/g6';


const data = {
  nodes: [
    {
      id: '0',
      label: 'Node',
      x: 55,
      y: 55,
    },
    {
      id: '1',
      label: 'Node',
      x: 55,
      y: 255,
    },
  ],
  edges: [
    {
      label: 'Label',
      source: '0',
      target: '1',
    },
  ],
};

const FlowEditor = () => {

  const ref = React.useRef(null);
  let graph: Graph | null = null;

  useEffect(() => {
    if (!graph) {
      graph = new G6.Graph({
        container: ref.current,
        width: 1200,
        height: 800,
        modes: {
          default: ['drag-canvas'],
        },
        layout: {
          type: 'dagre',
          direction: 'LR',
        },
        defaultNode: {
          type: 'node',
          labelCfg: {
            style: {
              fill: '#000000A6',
              fontSize: 10,
            },
          },
          style: {
            stroke: '#72CC4A',
            width: 150,
          },
        },
        defaultEdge: {
          type: 'polyline',
        },
      });
    }
    graph.data(data);
    graph.render();
  }, []);

  return (
    <PageContainer content="千言万语不如一张图，流程图是表示算法思路的好方法">
      <div ref={ref}></div>
      <div className={styles.editor}>
        <Row className={styles.editorHd}>
          <Col span={24}>
            {/*<FlowToolbar />*/}
          </Col>
        </Row>
        <Row className={styles.editorBd}>
          {/*<Col span={4} className={styles.editorSidebar}>
          <FlowItemPanel />
        </Col>*/}
          <Col span={16} className={styles.editorContent}>
            {/*<Flow className={styles.flow}  data={data}/>*/}
          </Col>
          {/*<Col span={4} className={styles.editorSidebar}>
          <FlowDetailPanel />
          <EditorMinimap />
        </Col>*/}
        </Row>
        {/*<FlowContextMenu />*/}
      </div>
    </PageContainer>
  );
}

export default FlowEditor;

