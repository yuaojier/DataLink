package com.ucar.datalink.writer.es.handle;

import com.ucar.datalink.contract.log.rdbms.RdbEventRecord;
import com.ucar.datalink.domain.media.MediaMappingInfo;
import com.ucar.datalink.worker.api.task.TaskWriterContext;
import com.ucar.datalink.worker.api.transform.BuiltInRdbEventRecordTransformer;
import com.ucar.datalink.writer.es.util.EsPrefixUtil;


/**
 * Created by lubiao on 2017/6/28.
 */
public class RdbEventRecordTransformer extends BuiltInRdbEventRecordTransformer {


    @Override
    protected RdbEventRecord transformOne(RdbEventRecord record, MediaMappingInfo mappingInfo, TaskWriterContext context) {
        //在执行父类的方法之前，先拿到原始的表名，然后构造列名前缀
        String tableName = record.getTableName();
        String fieldNamePrefix = EsPrefixUtil.getEsPrefixName(record.getTableName(), mappingInfo);

        RdbEventRecord result = super.transformOne(record, mappingInfo, context);
        if (result != null) {
            result.metaData().put(Constants.ORIGIN_TABLE_NAME, tableName);
            result.metaData().put(Constants.FIELD_NAME_PREFIX, fieldNamePrefix);
        }
        return result;
    }
}
