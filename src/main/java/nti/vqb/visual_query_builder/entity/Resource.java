package nti.vqb.visual_query_builder.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "resource")
public class Resource extends Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String group_name;
    private Integer connection_id;
    private String connection_name;
    private String query;
    private String type;
    private String date_field;

    @ColumnDefault("True")
    private Integer enabled;

    @Column(name = "created_at", updatable = false, nullable = false)
    private String created_at;

    public String[] getGroup_name(){
        return group_name.split(";");
    }

    public void getCurrentTime(Date created_at) {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedDate=dateFormat.format(date);
        this.created_at = formattedDate;
    }

    @ColumnDefault("Unknown")
    @Column(name = "created_by", updatable = false, nullable = false)
    private String created_by;

}
