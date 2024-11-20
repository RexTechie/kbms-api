package space.rexhub.kbms.common.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import space.rexhub.kbms.common.constant.Constant;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页
 */
@Data
public class Pagination implements Serializable {
  private static final Logger log = LoggerFactory.getLogger(Pagination.class);

  private static final long serialVersionUID = 1002L;

  @Min(value = 1, message = "page不能小于1")
  private Integer page = 1;

  @Range(min = 2, max = 50, message = "size必须在2~50之间")
  private Integer size = 10;

  /**
   * 排序字段，默认id
   */
  private String orderField = "id";

  /**
   * 排序方式，可选值(asc、desc)
   */
  private String order = Constant.SortStrategy.ASC;


  /**
   * 转换为IPage
   */
  public <T> IPage<T> toPage(){
    Page<T> page = new Page<>(this.page, this.size);
    if (StringUtils.hasLength(this.orderField) || StringUtils.hasLength(this.order)) {
      if (Constant.SortStrategy.ASC.equalsIgnoreCase(this.order)) {
        page.addOrder(OrderItem.asc(this.orderField));
      } else {
        page.addOrder(OrderItem.desc(this.orderField));
      }
    }
    return page;
  }

}
