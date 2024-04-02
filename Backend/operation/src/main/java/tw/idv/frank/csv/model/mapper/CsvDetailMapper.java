package tw.idv.frank.csv.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.csv.model.entity.CsvDetail;

import java.util.List;

@Mapper
public interface CsvDetailMapper {

    void deleteByCsvId(Integer csvId);

    void insertCsvDetail(List<CsvDetail> csvDetail);
}
