package com.bwd.nms.otrsrepository;

import com.bwd.nms.otrsdomain.ArticleData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ArticleRepository extends R2dbcRepository<ArticleData, Long> {

    @Query("""
        SELECT *
        FROM article_data_mime_attachment a
        JOIN users u ON a.create_by = u.id
        WHERE a.article_id = :articleid
        ORDER BY a.id DESC
        LIMIT 1
        """)
    Mono<ArticleData> findArticleDetails(@Param("articleid") String articleid);
}
