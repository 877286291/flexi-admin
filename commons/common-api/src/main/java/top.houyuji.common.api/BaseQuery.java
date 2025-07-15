package top.houyuji.common.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import top.houyuji.common.base.utils.StrUtil;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class BaseQuery implements Serializable {
    /**
     * 操作人
     */
    @Schema(description = "操作人", hidden = true)
    private String operator;
    /**
     * 分页size
     */
    @Schema(description = "分页size,只针对分页查询有效", example = "10")
    @Min(message = "最小值为1", value = 1)
    @NotNull(message = "不能为空", groups = {PageGroup.class})
    private Integer size;
    /**
     * 当前页码
     */
    @Schema(description = "当前页码,只针对分页查询有效", example = "1")
    @Min(message = "最小值为1", value = 1)
    @NotNull(message = "不能为空", groups = {PageGroup.class})
    private Integer current;

    /**
     * 排序字段,格式 filed1 asc,field2 desc
     */
    @Schema(description = "排序字段,格式 filed1 asc,field2 desc")
    private String sorts;

    /**
     * 获取排序
     *
     * @return 排序
     */
    @Nullable
    public List<SortMate> getSorts() {
        if (StrUtil.isBlank(sorts)) {
            return null;
        }
        return StrUtil.split(sorts, ",").stream().map(sort -> {
            List<String> fileSort = StrUtil.split(sort, " ");
            if (fileSort.size() == 1) {
                fileSort.add("asc");
            }
            String filed = fileSort.get(0);
            String order = fileSort.get(1);
            return new SortMate(filed, order);
        }).toList();
    }


    public interface PageGroup {
    }

    /**
     * 排序
     */
    @Data
    public static class SortMate implements Serializable {
        /**
         * 排序字段
         */
        private String field;
        /**
         * 排序方式,[asc,desc]
         */
        private String order;

        public SortMate(String field, String order) {
            this.field = field;
            this.order = StrUtil.isBlank(order) ? "asc" : order;
        }

        public SortMate() {
        }
    }
}