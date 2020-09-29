package org.shanzhaozhen.basicservice.schedule;

import org.springframework.stereotype.Service;

@Service("demoTask")
public class DemoTask {

    public void taskWithParams(String param1, int param2) {
        System.out.println("这是有参示例任务：" + param1 + param2);
    }

    public void taskNoParams() {
        System.out.println("这是无参示例任务");
    }

}
