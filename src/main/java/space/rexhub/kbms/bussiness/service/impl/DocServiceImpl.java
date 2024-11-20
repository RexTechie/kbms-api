package space.rexhub.kbms.bussiness.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import space.rexhub.kbms.bussiness.dao.DocMapper;
import space.rexhub.kbms.bussiness.dao.UserProjMapper;
import space.rexhub.kbms.bussiness.model.entity.DocEntity;
import space.rexhub.kbms.bussiness.model.entity.EsDocEntity;
import space.rexhub.kbms.bussiness.model.entity.UserProjEntity;
import space.rexhub.kbms.bussiness.model.form.DocPageForm;
import space.rexhub.kbms.bussiness.model.form.FullTextSearchForm;
import space.rexhub.kbms.bussiness.model.vo.DocDetailVO;
import space.rexhub.kbms.bussiness.model.vo.DocSearchVO;
import space.rexhub.kbms.bussiness.model.vo.DocVO;
import space.rexhub.kbms.bussiness.model.vo.DocSearchPageVO;
import space.rexhub.kbms.bussiness.service.DocService;
import space.rexhub.kbms.bussiness.service.UserService;
import space.rexhub.kbms.common.constant.CommonStatus;
import space.rexhub.kbms.common.constant.Constant;
import space.rexhub.kbms.common.exception.CommonException;
import space.rexhub.kbms.common.utils.ConvertUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description: 文档服务实现类
 *
 */
@Service
@Log4j2
public class DocServiceImpl extends ServiceImpl<DocMapper, DocEntity> implements DocService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProjMapper userProjMapper;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    private void verifyPermissions(Long projId){
        // 判断当前成员是否有权限
        Long userId = userService.getCurrentUser().getId();
        QueryWrapper<UserProjEntity> userProjEntityQueryWrapper = new QueryWrapper<UserProjEntity>()
                .eq("proj_id", projId);
        UserProjEntity userProjEntity = userProjMapper.selectOne(userProjEntityQueryWrapper);
        if (userProjEntity == null) {
            throw new CommonException(CommonStatus.NOT_ALLOWED);
        }
    }
    /**
     * 获取文档分页数据
     *
     * @param form 文档分页表单
     * @return 分页数据结果
     */
    @Override
    public IPage<DocVO> getDocPage(DocPageForm form) {
        verifyPermissions(form.getProjId());
        // 获取项目成员信息
        QueryWrapper<DocVO> docVoQueryWrapper = new QueryWrapper<DocVO>()
                .eq("proj_id", form.getProjId())
                .eq("deleted", Constant.SoftDelete.EXIST)
                .like(form.getTitle() != null, "title", form.getTitle());
        return baseMapper.getDocPage(form.toPage(), docVoQueryWrapper);
    }

    /**
     * 根据id获取文档
     *
     * @param id 文档id
     * @return 文档信息
     */
    @Override
    public DocDetailVO getDocDetailVoPage(Long id) {
        return baseMapper.getDocDetailVoPage(id);
    }

    /**
     * 删除文档
     *
     * @param docId 文档id
     */
    @Override
    public void deleteDoc(Long docId, Long projId) {
        verifyPermissions(projId);
        DocEntity docEntity = getById(docId);
        docEntity.setDeleted(Constant.SoftDelete.DELETED);
        updateById(docEntity);
        removeById(docId);
    }

    /**
     * 全文检索
     *
     * @param form 全文检索表单
     * @return 检索得到的文档
     */
    @Override
    public DocSearchPageVO searchByEs(FullTextSearchForm form) {
        StopWatch watch = new StopWatch();
        watch.start();
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        BoolQueryBuilder filterShould = QueryBuilders.boolQuery();
        String keyword = form.getKeyword();
        if(StrUtil.isNotBlank(keyword)) {
            filterShould.should(QueryBuilders.matchPhraseQuery("title", keyword));
            filterShould.should(QueryBuilders.matchPhraseQuery("summary", keyword));
            filterShould.should(QueryBuilders.matchPhraseQuery("content_text", keyword));
        }
        BoolQueryBuilder filterMust = QueryBuilders.boolQuery();
        filterMust.must(QueryBuilders.termsQuery("user_id", Arrays.asList(form.getUserId().longValue())));
        filterMust.must(QueryBuilders.termsQuery("deleted", Arrays.asList(0)));
        filter.must(filterShould);
        filter.must(filterMust);

        log.info("QueryBuilder: " + filter.toString());
        queryBuilder.withFilter(filter);
        queryBuilder.withSort(SortBuilders.fieldSort("doc_id").order(SortOrder.DESC));
        queryBuilder.withPageable(PageRequest.of(form.getPage()-1, form.getSize()));

        NativeSearchQuery nativeSearchQuery = queryBuilder.build();
        // 使用ElasticsearchRestTemplate进行复杂查询
        SearchHits<EsDocEntity> search = elasticsearchRestTemplate.search(nativeSearchQuery, EsDocEntity.class);
        DocSearchPageVO page  = new DocSearchPageVO();
        page.setCurrent(form.getPage());
        page.setSize(form.getSize());
        List<SearchHit<EsDocEntity>> searchHits = search.toList();
        if (search.getTotalHits() > 0){
            List<DocSearchVO> list = searchHits.stream().map((esDocEntitySearchHit)-> ConvertUtil.copyProperties(esDocEntitySearchHit.getContent(), DocSearchVO.class)).collect(Collectors.toList());
            page.setTotal(search.getTotalHits());
            page.setPages((int) Math.ceil((double) page.getTotal() / page.getSize()));
            page.setRecords(list);
        }else{
            page.setTotal(0);
            page.setPages(0);
            page.setRecords(new ArrayList<>());
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        page.setDuration(totalTimeMillis);
        return page;
    }

    /**
     * 通过MySql全文检索
     *
     * @param form 全文检索表单
     * @return 检索得到的文档
     */
    @Override
    public DocSearchPageVO searchByMySql(FullTextSearchForm form) {
        StopWatch watch = new StopWatch();
        watch.start();
        IPage<DocSearchVO> pageInfo = form.toPage();
        DocSearchPageVO page = new DocSearchPageVO();
        QueryWrapper<DocSearchVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", form.getUserId()).eq("d.deleted", Constant.SoftDelete.EXIST);
        String keyword = form.getKeyword();
        if (StrUtil.isNotBlank(keyword)){
            queryWrapper.and((q)->{
                        q.like("d.title", keyword)
                        .or().like("summary", keyword)
                        .or().like("content_text", keyword);
                    });
        }
        List<DocSearchVO> list = baseMapper.search(pageInfo, queryWrapper);
        if (list.size() > 0){
            page.setTotal(list.size());
            page.setPages((int) Math.ceil((double) page.getTotal() / page.getSize()));
            page.setRecords(list);
        }else{
            page.setTotal(0);
            page.setPages(0);
            page.setRecords(new ArrayList<>());
        }
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        page.setDuration(totalTimeMillis);
        return page;
    }
}
