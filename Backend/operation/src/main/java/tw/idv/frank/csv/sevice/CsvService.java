package tw.idv.frank.csv.sevice;

import org.springframework.web.multipart.MultipartFile;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.csv.model.entity.CsvDetail;

import java.util.List;

public interface CsvService {

    CommonResult<List<CsvDetail>> upload(MultipartFile multipartFile, String name);
}
