package q3df.mil;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import q3df.mil.entities.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForTests {


    public static void main(String[] args) {
        System.out.println("male".matches("male|female"));
        System.out.println("female".matches("male|female"));
        System.out.println("emale".matches("male|female"));
        System.out.println("le".matches("male|female"));
        System.out.println("[][][][][][][][][][]".matches("[0-9A-z_@^\\]\\[]{5,45}$"));
        System.out.println("mAle".matches("(?i)male|female"));
        System.out.println(0x7fffffffffffffffL);

        System.out.println(LocalDate.parse("2020-12-10"));

        System.out.println(LocalDate.now());

        System.out.println(new Date() + " Its Date");

        System.out.println(User.class.getSimpleName());


        BomboEntity bomboEntity = new BomboEntity();
        bomboEntity.setName(null);

//        CommentEntity commentDto0=new CommentEntity("privet");
//        CommentEntity commentDto1=new CommentEntity("bawd");
//        CommentEntity commentDto2=new CommentEntity("priabdawbvet");
//        CommentEntity commentDto3=new CommentEntity("bdawbadbawb");
//
//        List<CommentEntity> commentDtos=new ArrayList<>();
//        commentDtos.add(commentDto0);
//        commentDtos.add(commentDto1);
//        commentDtos.add(commentDto2);
//        commentDtos.add(commentDto3);
//
//
//        BomboEntity bomboEntity = new BomboEntity("Nick", commentDtos);
//        BomboEntity bomboEntity1= new BomboEntity("vadwv", commentDtos);
//        BomboEntity bomboEntity2 = new BomboEntity("vadwv", commentDtos);
//        BomboEntity bomboEntity3 = new BomboEntity("vadwvawdvdawvb", commentDtos);
//
//        ModelMapper modelMapper=new ModelMapper();
//        BomboDto map = modelMapper.map(bomboEntity, BomboDto.class);
//        System.out.println(map);








    }
}

@Data
@NoArgsConstructor
class BomboEntity{
    private String name;
    private List<CommentEntity> comment;

    public BomboEntity(String name, List<CommentEntity> commentEntities) {
        this.name = name;
        this.comment = commentEntities;
    }
}

@Data
@NoArgsConstructor
class BomboDto{
    private String name;
    private List<CommentDto> comment;

}

@Data
@NoArgsConstructor
class CommentEntity{
    private String text;

    public CommentEntity(String text) {
        this.text = text;
    }
}

@Data
@NoArgsConstructor
class CommentDto{
    private String text;

    public CommentDto(String text) {
        this.text = text;
    }
}
