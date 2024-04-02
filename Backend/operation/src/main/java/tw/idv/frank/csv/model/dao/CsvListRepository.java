package tw.idv.frank.csv.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.frank.csv.model.entity.CsvList;

public interface CsvListRepository extends JpaRepository<CsvList, Integer> {

    CsvList findByFileName(String name);
}
