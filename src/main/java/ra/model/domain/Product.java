package ra.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "title")
    private String title;
    @Column(name = "price")
    private Float price;
    @Column(name = "stock")
    private int stock;
    @Column(name = "image",columnDefinition = "text")
    private String image;
    @Column(name = "description",columnDefinition = "text")
    private String description;
    @Column(name = "status")
    private boolean status;
    @Column(name = "create_at")
    private Date createAt = new Date();
    @OneToMany(fetch = FetchType.EAGER)
    private List<SubImage> subImages = new ArrayList<>();
}