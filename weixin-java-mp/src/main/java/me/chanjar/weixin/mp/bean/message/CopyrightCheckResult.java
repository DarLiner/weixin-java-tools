package me.chanjar.weixin.mp.bean.message;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Getter;
import lombok.Setter;
import me.chanjar.weixin.common.util.xml.XStreamCDataConverter;

/**
 * <pre>
 * 原创校验结果
 * Created by Binary Wang on 2018/6/27.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Getter
@Setter
public class CopyrightCheckResult implements Serializable {
  private static final long serialVersionUID = 4823438554039202102L;

  /**
   * 总数.
   */
  @XStreamAlias("Count")
  private Integer count;

  /**
   * 整体校验结果.
   */
  @XStreamAlias("CheckState")
  private Integer checkState;

  /**
   * 各个单图文校验结果.
   */
  @XStreamAlias("ResultList")
  private List<ResultItem> resultList;

  @Getter
  @Setter
  @XStreamAlias("item")
  public static class ResultItem implements Serializable {
    private static final long serialVersionUID = -197030991412465661L;

    /**
     * 群发文章的序号，从1开始.
     */
    @XStreamAlias("ArticleIdx")
    private Integer articleIdx;

    /**
     * 用户声明文章的状态.
     */
    @XStreamAlias("UserDeclareState")
    private Integer userDeclareState;

    /**
     * 系统校验的状态.
     */
    @XStreamAlias("AuditState")
    private Integer auditState;

    /**
     * 相似原创文的url.
     */
    @XStreamAlias("OriginalArticleUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String originalArticleUrl;

    /**
     * 相似原创文的类型.
     */
    @XStreamAlias("OriginalArticleType")
    private Integer originalArticleType;

    /**
     * 是否能转载.
     */
    @XStreamAlias("CanReprint")
    private Integer canReprint;

    /**
     * 是否需要替换成原创文内容.
     */
    @XStreamAlias("NeedReplaceContent")
    private Integer needReplaceContent;

    /**
     * 是否需要注明转载来源.
     */
    @XStreamAlias("NeedShowReprintSource")
    private Integer needShowReprintSource;

    /**
     * 1-未被判为转载，可以群发，2-被判为转载，可以群发，3-被判为转载，不能群发.
     */
    @XStreamAlias("CheckState")
    private Integer checkState;
  }
}
