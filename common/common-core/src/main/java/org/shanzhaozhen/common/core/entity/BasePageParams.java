package org.shanzhaozhen.common.core.entity;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.utils.SqlInjectionUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BasePageParams", description = "基础分页列表查询前端传入参数")
public class BasePageParams<T> {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 生成mybatis的分页实体
     * @return mybatis的分页实体
     */
    public Page<T> getPage() {
        Page<T> page = new Page<>(this.current, this.size);
        // 检查排序字段有没有SQL注入风险
        if (!CollectionUtils.isEmpty(this.orders)) {
//            this.orders.forEach(orderItem -> {
//                SqlInjectionUtil.filterContent(orderItem);
//                page.addOrder(orderItem);
//            });

            for (OrderItem orderItem : this.orders) {
                SqlInjectionUtil.filterContent(orderItem);
                page.addOrder(orderItem);
            }
        }

        return page;
    }

}
