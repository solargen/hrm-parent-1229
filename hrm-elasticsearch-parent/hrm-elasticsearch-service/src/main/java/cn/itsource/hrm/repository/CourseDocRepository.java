package cn.itsource.hrm.repository;

import cn.itsource.hrm.doc.CourseDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseDocRepository extends ElasticsearchRepository<CourseDoc,Long> {
}
