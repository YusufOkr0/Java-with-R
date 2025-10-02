import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "R-values") // MongoDB collection adıyla aynı olmalı
public class RValue {

    @Id
    private String id;   // MongoDB otomatik oluşturuyor

    private double value; // CSV’den yüklediğin değerler

    // Getter ve Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
}
