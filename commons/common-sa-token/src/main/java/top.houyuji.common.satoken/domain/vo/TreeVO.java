package top.houyuji.common.satoken.domain.vo;

import java.io.Serializable;
import java.util.List;


public interface TreeVO<T> extends Serializable {

    /**
     * 获取子类
     *
     * @return .
     */
    List<T> getChildren();

    /**
     * 设置子类
     *
     * @param children .
     */
    void setChildren(List<T> children);
}