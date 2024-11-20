package space.rexhub.kbms.bussiness.es_dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import space.rexhub.kbms.bussiness.model.entity.EsDocEntity;

/**
 * Description: ESDoc的dao
 *
 */
public interface EsDocDao extends ElasticsearchRepository<EsDocEntity, Long> {

}
