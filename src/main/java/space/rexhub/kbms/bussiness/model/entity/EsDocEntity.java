package space.rexhub.kbms.bussiness.model.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

/**
 * Description: Es中doc实体
 *
 * @author Rex
 * @date 2024-04-20
 */
@Data
@Document(indexName = "kbms_doc", createIndex = false)
@Setting(useServerConfiguration = true)
public class EsDocEntity {

    @Id
    private Long id;

    @Field(name = "proj_id", type = FieldType.Long)
    private Long projId;

    @Field(name = "doc_id", type = FieldType.Long)
    private Long docId;

    @Field(name = "user_id", type = FieldType.Long)
    private Long userId;

    @Field(name = "proj", type = FieldType.Text)
    private String proj;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String summary;

    @Field(name="content_text", type = FieldType.Text, analyzer = "ik_max_word")
    private String contentText;

    @Field(name="is_upload", type = FieldType.Byte)
    private Byte isUpload;

    @Field(name = "deleted", type = FieldType.Byte)
    private Byte deleted;

    @Field(type = FieldType.Text)
    private String creator;

//    @Field(type = FieldType.Date, name = "create_time",format = {},
//            pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    @Field(name = "create_time", type = FieldType.Text, fielddata = true)
    private String createTime;
}
