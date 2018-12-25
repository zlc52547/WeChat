package org.cs.weixin.message.model;

import java.util.List;

/**
 * Created by zlc on 2018/5/31.
 * 图文消息实体类
 */
public class NewsMessage extends BaseMessage {
        // 图文信息个数，限制为10条之内
        private int ArticleCount;
        // 多条图文消息信息，默认第一个item为大图
        private List<Article> Articles;

        public int getArticleCount() {
                return ArticleCount;
        }

        public void setArticleCount(int articleCount) {
                ArticleCount = articleCount;
        }

        public List<Article> getArticles() {
                return Articles;
        }

        public void setArticles(List<Article> articles) {
                Articles = articles;
        }
}
