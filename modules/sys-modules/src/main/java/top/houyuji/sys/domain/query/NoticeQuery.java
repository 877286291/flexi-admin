package top.houyuji.sys.domain.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import top.houyuji.common.api.BaseQuery;
import top.houyuji.common.query.annotation.Equals;
import top.houyuji.common.query.annotation.GreaterThanEqual;
import top.houyuji.common.query.annotation.LessThanEqual;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class NoticeQuery extends BaseQuery {
    /**
     * 是否启用
     */
    @Equals(allowNull = false)
    private Boolean enabled;

    /**
     * 公告开始时间
     */
    @GreaterThanEqual
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date noticeTimeStart;

    /**
     * 公告结束时间
     */
    @LessThanEqual
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date noticeTimeEnd;
}
