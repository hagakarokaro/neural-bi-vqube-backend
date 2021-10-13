package nti.vqb.visual_query_builder.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "connection")
public class Connection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name =  "name")
    private String name;
    @Column(name =  "hostname")
    private String hostname;
    @Column(name =  "username")
    private String username;
    @Column(name =  "password")
    private String password;
    @Column(name =  "database")
    private String database;
    @Column(name =  "schema")
    private String schema;
    @Column(name =  "port")
    private Integer port;
    @Column(name =  "driver")
    private String driver;

    @ColumnDefault("True")
    private Integer enabled;

    @Column(name = "created_at", updatable = false, nullable = false)
    private String created_at;

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
