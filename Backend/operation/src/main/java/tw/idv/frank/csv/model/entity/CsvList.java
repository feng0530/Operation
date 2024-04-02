package tw.idv.frank.csv.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "csv_list")
public class CsvList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "upload_time")
    private Date uploadTime;

    @Column(name = "update_time")
    private Date updateTime;

    public CsvList(String fileName, Date uploadTime, Date updateTime){
        this.fileName = fileName;
        this.uploadTime = uploadTime;
        this.updateTime = updateTime;
    }
}
