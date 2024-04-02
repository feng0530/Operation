package tw.idv.frank.csv.sevice.impl;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.csv.model.dao.CsvListRepository;
import tw.idv.frank.csv.model.entity.CsvDetail;
import tw.idv.frank.csv.model.entity.CsvList;
import tw.idv.frank.csv.model.mapper.CsvDetailMapper;
import tw.idv.frank.csv.sevice.CsvService;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    @Autowired
    CsvListRepository csvListRepository;

    @Autowired
    CsvDetailMapper csvDetailMapper;

    @Override
    @Transactional
    public CommonResult upload(MultipartFile file, String name) {
        if (file.isEmpty()) {
            throw new BaseException(CommonCode.NOT_FOUND);
        }

        CsvList csvList = csvListRepository.findByFileName(name);

        if (csvList == null){
            csvList = csvListRepository.save(new CsvList(name, new Date(), new Date()));
        }else{
            csvList.setUpdateTime(new Date());
            csvList = csvListRepository.save(csvList);
            csvDetailMapper.deleteByCsvId(csvList.getId());
        }
        try(CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<CsvDetail> result = new ArrayList<>();
            List<String[]> list = reader.readAll();
            list.remove(0);  // 去掉header

            // PreparedStatement 在 PostgreSQL 中最多只能有 65,535 個參數
            int batchSize = 65000 / list.get(0).length;
            long loadSize = 0;

            for(String[] row : list){
                CsvDetail csvDetail = new CsvDetail();
                csvDetail.setCsvId(csvList.getId());
                csvDetail.setName(row[1]);
                csvDetail.setGender(row[2]);
                csvDetail.setAge(Integer.valueOf(row[3]));
                result.add(csvDetail);

                loadSize++;

                if(result.size() == batchSize){
                    csvDetailMapper.insertCsvDetail(result);
                    result.clear();
                }
            }

            if(!result.isEmpty()){
                csvDetailMapper.insertCsvDetail(result);
            }
            return new CommonResult(CommonCode.SUCCESS, "已匯入 " + loadSize + "筆資料!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(CommonCode.N999);
        }
    }
}
