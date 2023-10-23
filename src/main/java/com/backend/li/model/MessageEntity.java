    package com.backend.li.model;

    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;

    import javax.persistence.*;
    import java.util.Objects;

    @Entity
    @Table(name = "mesaje", schema = "lipoo", catalog = "")
    @Getter
    @Setter
    @NoArgsConstructor
    public class MessageEntity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "id_utilizator", nullable = false)
        private Long id_utilizator;

        @Column(name = "mesaj", nullable = false)
        private String mesaj;

        public MessageEntity(Long id, Long id_utilizator, String mesaj) {
            this.id = id;
            this.id_utilizator = id_utilizator;
            this.mesaj = mesaj;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
           MessageEntity that = (MessageEntity) o;
            return Objects.equals(id, that.id) && Objects.equals(id_utilizator, that.id_utilizator) && Objects.equals(mesaj, that.mesaj);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, id_utilizator, mesaj);
        }


    }
